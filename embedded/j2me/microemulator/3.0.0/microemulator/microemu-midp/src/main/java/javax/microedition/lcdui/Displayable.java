/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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
 */
 
package javax.microedition.lcdui;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL;

import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.DisplayableUI;

import org.allbinary.device.OpenGLESGraphics;
import org.allbinary.device.OpenGLESGraphicsCompositeFactory;

public class Displayable
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    private final OpenGLESGraphics openGLESGraphics = OpenGLESGraphicsCompositeFactory.getInstance().get().getOpenGLESGraphicsInstance();
    
	Device device;
	
	Display currentDisplay = null;
	
	int width;
	
	int height;
    
	boolean fullScreenMode;

    Ticker ticker;
    
    // TODO make private
    public int viewPortY;
    // TODO make private
    public int viewPortHeight;
    
    DisplayableUI ui;
    
    private String title;
    
	private CommandListener listener = null;

    
    Displayable(String title) 
    {
        this.device = DeviceFactory.getDevice();
        this.width = -1;
        this.height = -1;
        this.fullScreenMode = false;
        this.title = title;
    }
    
    
    void setUI(DisplayableUI ui) {
    	this.ui = ui;
    }
  

	public void addCommand(Command cmd) {
		this.ui.addCommandUI(cmd.ui);
	}


	public void removeCommand(Command cmd)
	{
		if (cmd != null) {
			this.ui.removeCommandUI(cmd.ui);
		}
	}
    
    
    public int getWidth()
    {
    	if (this.width == -1) {
    		this.updateWidthAndHeight();
    	}
    	
    	return this.width;
    }


    public int getHeight()
    {
    	if (this.height == -1) {
    		this.updateWidthAndHeight();
    	}
    	
    	return this.height;
    }


	public boolean isShown()
	{
		if (this.currentDisplay == null) {
			return false;
		}
		return this.currentDisplay.isShown(this);
	}

    
    public Ticker getTicker() 
    {
        return this.ticker;
    }

    
    public void setTicker(Ticker ticker) 
    {
        this.ticker = ticker;

        this.repaint();
    }

    
    public String getTitle() 
    {
        return this.title;
    }

    
    public void setTitle(String s) 
    {
        this.title = s;
        
        // TODO move to the native UI component
        this.ui.invalidate();
    }        
    

	public void setCommandListener(CommandListener l)
	{
		this.listener = l;
		
		this.ui.setCommandListener(l);
	}
	
	
	public CommandListener getCommandListener()
	{
		return this.listener;
	}


	public Vector getCommands()
	{
		// in Form this is overridden to allow for the inclusion of item contained commands 
		Vector result = new Vector();
		Vector commandsUI = this.ui.getCommandsUI();
		for (int i = 0; i < commandsUI.size(); i++) {
			result.addElement(((CommandUI) commandsUI.elementAt(i)).getCommand());
		}
		
		return result;
	}


	void hideNotify()
	{
	}


	final void hideNotify(Display d)
	{		
		this.ui.hideNotify();

		this.hideNotify();
	}

	public void keyPressed(int keyCode)
	{
	}

	void keyRepeated(int keyCode)
	{
	}

	public void keyReleased(int keyCode)
	{
	}
    
	void pointerPressed(int x, int y) 
	{
	}

	
	void pointerReleased(int x, int y) 
	{
	}

	
	void pointerDragged(int x, int y) 
	{
	}


    public void draw(GL gl)
    {
        //PreLogUtil.put("AndroidToJ2ME", this, "draw");
        
        this.openGLESGraphics.update(gl);
        this.paint(this.openGLESGraphics);
        this.openGLESGraphics.setCameraMode();
    }
    
    public void drawThreed(GL gl)
    {
        this.openGLESGraphics.set(gl);
        this.paintThreed(this.openGLESGraphics);
    }
     
    public void paintThreed(Graphics graphics)
    {

    }
    
    public void onDraw(Object canvas)
    {
        /*
         * if(this.getClass().getName().indexOf("Mini") == -1) {
         * logUtil.putF(commonStrings.START, this, "onDraw"); }
         */

        //Faster than static access
        //androidDisplayGraphics.setCanvas(canvas);
        //this.paint(androidDisplayGraphics);
        throw new RuntimeException();
        //this.paint(AndroidDisplayGraphics.getInstance(canvas));
    }
    
        void paint(Graphics g)
        {
        }

        //Added public
        //public void repaint()
	void repaint()
	{
            this.repaint(0, 0, getWidth(), getHeight());
	}

        //Added public
        //public void repaint(int x, int y, int width, int height)
	void repaint(int x, int y, int width, int height)
    {
                final Display currentDisplay = this.currentDisplay;
                if (currentDisplay != null) {
			currentDisplay.repaint(this, x, y, width, height);
		}
    }
	
	protected void sizeChanged(int w, int h)
	{		
	}


	final void sizeChanged(Display d)
	{
		this.updateWidthAndHeight();
		this.sizeChanged(this.width, height);
	}
	
	
	void showNotify()
	{        
	}


	final void showNotify(Display d)
	{
		this.currentDisplay = d;
        this.viewPortY = 0;
        // TODO remove this StringComponent object when native UI is completed
        StringComponent title = new StringComponent(getTitle());
        this.viewPortHeight = this.getHeight() - title.getHeight() - 1;
        if (this.ticker != null) {
        		this.viewPortHeight -= this.ticker.getHeight();
        }
        
        int w;
    	int h;
    	if (this.fullScreenMode) {
    		w = this.device.getDeviceDisplay().getFullWidth();
    	} else {
    		w = this.device.getDeviceDisplay().getWidth();
    	}
    	if (this.fullScreenMode) {
    		h = this.device.getDeviceDisplay().getFullHeight();
    	} else {
    		h = this.device.getDeviceDisplay().getHeight();
    	}
   	
        if (this.width != w || this.height != h) {
        	this.sizeChanged(d);
        }
		
		this.showNotify();

		this.ui.showNotify();
	}

	private void updateWidthAndHeight() 
	{
    	if (this.fullScreenMode) {
    		this.width = this.device.getDeviceDisplay().getFullWidth();
    		this.height = this.device.getDeviceDisplay().getFullHeight();
    	} else {
    		this.width = this.device.getDeviceDisplay().getWidth();
    		this.height= this.device.getDeviceDisplay().getHeight();
    	}
	}

    /**
     * @return the fullScreenMode
     */
    public void setFullScreenMode(boolean fullScreenMode)
    {
        this.fullScreenMode = fullScreenMode;
    }

    public boolean isFullScreenMode()
    {
        return this.fullScreenMode;
    }
    
    public void translate(final int x, final int y) {
        
    }
    
    public int getTypeAsInt() {
        return 0;
    }
}
