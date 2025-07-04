/*
 * MicroEmulator 
 * Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
 * Copyright (C) 2007-2007 Vlad Skarzhevskyy
 * 
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 * 
 * @version $Id: MIDlet.java 2220 2009-11-30 09:38:59Z barteo@gmail.com $  
 */
package javax.microedition.midlet;

import java.io.FileNotFoundException;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;

import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;

//TWB - Changed to class for performance and added listeners
public class MIDlet 
{

    public PlatformMIDletHelper midletHelper;
    
    private boolean destroyed;
    private boolean resized;

    class MIDletAccessor extends MIDletAccess
    {

        public MIDletAccessor()
        {
            super(MIDlet.this);
            destroyed = false;
        }

        public void startApp() throws MIDletStateChangeException
        {
            MIDletBridge.setCurrentMIDlet(midlet);
            midlet.startApp();
        }

        public void pauseApp()
        {
            midlet.pauseApp();
        }

        public void destroyApp(boolean unconditional) throws MIDletStateChangeException
        {
            if (!midlet.destroyed)
            {
                midlet.destroyApp(unconditional);
            }
            DisplayAccess da = getDisplayAccess();
            if (da != null)
            {
                da.clean();
                setDisplayAccess(null);
            }
            MIDletBridge.destroyMIDletContext(MIDletBridge.getMIDletContext(midlet));
        }
    }

    protected MIDlet()
    {
        MIDletBridge.registerMIDletAccess(new MIDletAccessor());

        // Initialize Display
        Display.getDisplay(this);
    }

    protected void startApp() throws MIDletStateChangeException
    {
    }

    protected void pauseApp()
    {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException
    {
    }

    public final int checkPermission(String permission)
    {
        return MIDletBridge.checkPermission(permission);
    }

    public final String getAppProperty(String key)
    {
        return MIDletBridge.getAppProperty(key);
    }

    public final void notifyDestroyed()
    {
        destroyed = true;
        MIDletBridge.notifyDestroyed();
    }

    public final void notifyPaused()
    {
    }

    public final boolean platformRequest(String URL) throws ConnectionNotFoundException
    {
        return MIDletBridge.platformRequest(URL);
    }

    public final void resumeRequest()
    {
        // TODO implement
    }
    
    //TWB - Added mouse input support
    //public void mouseClicked(MouseEvent e)
    public void mouseClicked(final int x, final int y, final int button)
    {
    }

    //public void mousePressed(MouseEvent e)
    public void mousePressed(final int x, final int y, final int button)
    {
    }

    //public void mouseReleased(MouseEvent e)
    public void mouseReleased(final int x, final int y, final int button)
    {
    }

    //public void mouseEntered(MouseEvent e)
    public void mouseEntered(final int x, final int y, final int button)
    {
    }

    //public void mouseExited(MouseEvent e)
    public void mouseExited(final int x, final int y, final int button)
    {
    }

    //public void mouseDragged(MouseEvent e)
    public void mouseDragged(final int x, final int y, final int button)
    {
    }

    //public void mouseMoved(MouseEvent e)
    public void mouseMoved(final int x, final int y, final int button)
    {
    }

    //public void mouseWheelMoved(MouseWheelEvent e)
    public void mouseWheelMoved(final int x, final int y, final int button)
    {
    }
    
    //int lastHeight = 0;
    //int lastWidth = 0;
    public void componentResized()
    {
        /*
        Component c = e.getComponent();

        if(c.getWidth() != lastWidth || c.getHeight() != lastHeight)
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("componentResized: ");
            stringBuffer.append("width: ");
            stringBuffer.append(this.lastWidth);
            stringBuffer.append(" <> ");
            stringBuffer.append(c.getWidth());
            stringBuffer.append(" height: ");
            stringBuffer.append(this.lastHeight);
            stringBuffer.append(" <> ");
            stringBuffer.append(c.getHeight());

            System.out.println(stringBuffer.toString());

            this.lastHeight = c.getHeight();
            this.lastWidth = c.getWidth();

            this.setResized(true);
        }
        */
        this.setResized(true);
    }

    public void componentMoved()
    {
    }

    public void componentShown()
    {
    }

    public void componentHidden()
    {
    }
    
    /**
     * @return the resized
     */
    public boolean isResized()
    {
        return resized;
    }

    /**
     * @param resized the resized to set
     */
    public void setResized(boolean resized)
    {
        this.resized = resized;
    }
    
    public void exit()
        throws Exception
    {
        MIDlet.this.midletHelper.exit();
    }
    
}
