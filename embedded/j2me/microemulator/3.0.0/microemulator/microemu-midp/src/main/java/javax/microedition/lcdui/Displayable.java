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

import jsinterop.annotations.JsType;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL;

import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.DisplayableUI;

import org.allbinary.device.OpenGLESGraphics;
import org.allbinary.device.OpenGLESGraphicsCompositeFactory;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
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
    @JsProperty
    public int viewPortY;
    // TODO make private
    @JsProperty
    public int viewPortHeight;
    
    DisplayableUI ui;
    
    private String title;
    
	private CommandListener listener = null;

    
    @JsConstructor
    Displayable(String title) 
    {
        this.device = DeviceFactory.getDevice();
        this.width = -1;
        this.height = -1;
        this.fullScreenMode = false;
        this.title = title;
    }
    
    
    @JsMethod
    void setUI(DisplayableUI ui) {
    	this.ui = ui;
    }
  

	@JsMethod
	public void addCommand(Command cmd) {
		this.ui.addCommandUI(cmd.ui);
	}


	@JsMethod
	public void removeCommand(Command cmd)
	{
		if (cmd != null) {
			this.ui.removeCommandUI(cmd.ui);
		}
	}
    
    
    @JsMethod
    public int getWidth()
    {
    	if (this.width == -1) {
    		this.updateWidthAndHeight();
    	}
    	
    	return this.width;
    }


    @JsMethod
    public int getHeight()
    {
    	if (this.height == -1) {
    		this.updateWidthAndHeight();
    	}
    	
    	return this.height;
    }


	@JsMethod
	public boolean isShown()
	{
		if (this.currentDisplay == null) {
			return false;
		}
		return this.currentDisplay.isShown(this);
	}

    
    @JsMethod
    public Ticker getTicker() 
    {
        return this.ticker;
    }

    
    @JsMethod
    public void setTicker(Ticker ticker) 
    {
        this.ticker = ticker;

        this.repaint();
    }

    
    @JsMethod
    public String getTitle() 
    {
        return this.title;
    }

    
    @JsMethod
    public void setTitle(String s) 
    {
        this.title = s;
        
        // TODO move to the native UI component
        this.ui.invalidate();
    }        
    

	@JsMethod
	public void setCommandListener(CommandListener l)
	{
		this.listener = l;
		
		this.ui.setCommandListener(l);
	}
	
	
	@JsMethod
	public CommandListener getCommandListener()
	{
		return this.listener;
	}


	@JsMethod
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


	@JsMethod
	void hideNotify()
	{
	}


	@JsMethod
	final void hideNotify(Display d)
	{		
		this.ui.hideNotify();

		this.hideNotify();
	}

	@JsMethod
	public void keyPressed(int keyCode)
	{
	}

	@JsMethod
	void keyRepeated(int keyCode)
	{
	}

	@JsMethod
	public void keyReleased(int keyCode)
	{
	}
    
	@JsMethod
	void pointerPressed(int x, int y) 
	{
	}

	
	@JsMethod
	void pointerReleased(int x, int y) 
	{
	}

	
	@JsMethod
	void pointerDragged(int x, int y) 
	{
	}


    @JsMethod
    public void draw(GL gl)
    {
        //PreLogUtil.put("AndroidToJ2ME", this, "draw");
        
        this.openGLESGraphics.updateGL(gl);
        this.paint(this.openGLESGraphics);
        this.openGLESGraphics.setCameraMode();
    }
    
    @JsMethod
    public void drawThreedGL(GL gl)
    {
        this.openGLESGraphics.set(gl);
        this.paintThreed(this.openGLESGraphics);
    }
     
    @JsMethod
    public void paintThreed(Graphics graphics)
    {

    }
    
    @JsMethod
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
    
        @JsMethod
        void paint(Graphics g)
        {
        }

        //Added public
        //public void repaint()
	@JsMethod
	void repaint()
	{
            this.repaint(0, 0, getWidth(), getHeight());
	}

        //Added public
        //public void repaint(int x, int y, int width, int height)
	@JsMethod
	void repaint(int x, int y, int width, int height)
    {
                final Display currentDisplay = this.currentDisplay;
                if (currentDisplay != null) {
			currentDisplay.repaint(this, x, y, width, height);
		}
    }
	
	@JsMethod
	protected void sizeChanged(int w, int h)
	{		
	}


	@JsMethod
	final void sizeChanged(Display d)
	{
		this.updateWidthAndHeight();
		this.sizeChanged(this.width, height);
	}
	
	
	@JsMethod
	void showNotify()
	{        
	}


	@JsMethod
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

	@JsMethod
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
    @JsMethod
    public void setFullScreenMode(boolean fullScreenMode)
    {
        this.fullScreenMode = fullScreenMode;
    }

    @JsMethod
    public boolean isFullScreenMode()
    {
        return this.fullScreenMode;
    }
    
    @JsMethod
    public void translate(final int x, final int y) {
        
    }
    
    @JsMethod
    public int getTypeAsInt() {
        return 0;
    }
}
