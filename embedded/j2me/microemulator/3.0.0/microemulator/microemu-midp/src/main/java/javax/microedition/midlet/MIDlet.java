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

import jsinterop.annotations.JsType;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;

import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;

//TWB - Changed to class for performance and added listeners

@JsType
public class MIDlet 
{

    @JsProperty
    public PlatformMIDletHelper midletHelper;
    
    private boolean destroyed;
    private boolean resized;

    class MIDletAccessor extends MIDletAccess
    {

        @JsConstructor
        public MIDletAccessor()
        {
            super(MIDlet.this);
            destroyed = false;
        }

        @Override
        @JsMethod
        public void startApp() throws MIDletStateChangeException
        {
            MIDletBridge.setCurrentMIDlet(midlet);
            midlet.startApp();
        }

        @Override
        @JsMethod
        public void pauseApp()
        {
            midlet.pauseApp();
        }

        @Override
        @JsMethod
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
            MIDletBridge.destroyMIDletContext(MIDletBridge.getMIDletContextForMIDlet(midlet));
        }
    }

    @JsConstructor
    protected MIDlet()
    {
        MIDletBridge.registerMIDletAccess(new MIDletAccessor());

        // Initialize Display
        Display.getDisplay(this);
    }

    @JsMethod
    protected void startApp() throws MIDletStateChangeException
    {
    }

    @JsMethod
    protected void pauseApp()
    {
    }

    @JsMethod
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException
    {
    }

    @JsMethod
    public final int checkPermission(String permission)
    {
        return MIDletBridge.checkPermission(permission);
    }

    @JsMethod
    public final String getAppProperty(String key)
    {
        return MIDletBridge.getAppProperty(key);
    }

    @JsMethod
    public final void notifyDestroyed()
    {
        this.destroyed = true;
        MIDletBridge.notifyDestroyed();
    }

    @JsMethod
    public final void notifyPaused()
    {
    }

    @JsMethod
    public final boolean platformRequest(String URL) throws ConnectionNotFoundException
    {
        return MIDletBridge.platformRequest(URL);
    }

    @JsMethod
    public final void resumeRequest()
    {
        // TODO implement
    }
    
    //TWB - Added mouse input support
    //public void mouseClicked(MouseEvent e)
    @JsMethod
    public void mouseClicked(final int x, final int y, final int button)
    {
    }

    //public void mousePressed(MouseEvent e)
    @JsMethod
    public void mousePressed(final int x, final int y, final int button)
    {
    }

    //public void mouseReleased(MouseEvent e)
    @JsMethod
    public void mouseReleased(final int x, final int y, final int button)
    {
    }

    //public void mouseEntered(MouseEvent e)
    @JsMethod
    public void mouseEntered(final int x, final int y, final int button)
    {
    }

    //public void mouseExited(MouseEvent e)
    @JsMethod
    public void mouseExited(final int x, final int y, final int button)
    {
    }

    //public void mouseDragged(MouseEvent e)
    @JsMethod
    public void mouseDragged(final int x, final int y, final int button)
    {
    }

    //public void mouseMoved(MouseEvent e)
    @JsMethod
    public void mouseMoved(final int x, final int y, final int button)
    {
    }

    //public void mouseWheelMoved(MouseWheelEvent e)
    @JsMethod
    public void mouseWheelMoved(final int x, final int y, final int button)
    {
    }
    
    //int lastHeight = 0;
    //int lastWidth = 0;
    @JsMethod
    public void componentResized()
    {
        /*
        Component c = e.getComponent();

        if(c.getWidth() != lastWidth || c.getHeight() != lastHeight)
        {
            StringMaker stringBuffer = new StringMaker();
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

    @JsMethod
    public void componentMoved()
    {
    }

    @JsMethod
    public void componentShown()
    {
    }

    @JsMethod
    public void componentHidden()
    {
    }
    
    /**
     * @return the resized
     */
    @JsMethod
    public boolean isResized()
    {
        return this.resized;
    }

    /**
     * @param resized the resized to set
     */
    @JsMethod
    public void setResized(boolean resized)
    {
        this.resized = resized;
    }

    @JsMethod
    public void register()
    {
        //TWB - Registered in constructor 2.0.2-SPECIAL vs 3.0.0
        //MIDletBridge.registerMIDletAccess(midletAccessor);
        //destroyed = false;
    }

    @JsMethod
    public void exit()
        throws Exception
    {
        MIDlet.this.midletHelper.exit();
    }
    
}
