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
 *
 *  Contributor(s):
 *    3GLab
 */
 
package javax.microedition.lcdui;

import jsinterop.annotations.JsType;

import org.allbinary.graphics.ScreenListenerHandler;

import org.microemu.device.DeviceFactory;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
public class Canvas extends Displayable
{

	@JsProperty
	public static final int UP = 1;
	@JsProperty
	public static final int DOWN = 6;
	@JsProperty
	public static final int LEFT = 2;
	@JsProperty
	public static final int RIGHT = 5;
	@JsProperty
	public static final int FIRE = 8;

	@JsProperty
	public static final int GAME_A = 9;
	@JsProperty
	public static final int GAME_B = 10;
	@JsProperty
	public static final int GAME_C = 11;
	@JsProperty
	public static final int GAME_D = 12;

	@JsProperty
	public static final int KEY_NUM0 = 48;
    @JsProperty
    public static final int KEY_NUM1 = 49;
    @JsProperty
    public static final int KEY_NUM2 = 50;
    @JsProperty
    public static final int KEY_NUM3 = 51;
    @JsProperty
    public static final int KEY_NUM4 = 52;
    @JsProperty
    public static final int KEY_NUM5 = 53;
    @JsProperty
    public static final int KEY_NUM6 = 54;
    @JsProperty
    public static final int KEY_NUM7 = 55;
    @JsProperty
    public static final int KEY_NUM8 = 56;
    @JsProperty
    public static final int KEY_NUM9 = 57;
    @JsProperty
    public static final int KEY_STAR = 42;
    @JsProperty
    public static final int KEY_POUND = 35;
    
    @JsProperty
    public static final int TYPE = 1;

	@JsConstructor
	protected Canvas()
	{
        super(null);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createCanvasUI(this));
	}


	@JsMethod
	public int getGameAction(int keyCode)
	{
		return Display.getGameAction(keyCode);
	}


	@JsMethod
	public int getKeyCode(int gameAction)
    {
        return Display.getKeyCode(gameAction);
    }

    
    @JsMethod
    public String getKeyName(int keyCode) throws IllegalArgumentException 
    {
    	return Display.getKeyName(keyCode);
    }

    
    @JsMethod
    public boolean hasPointerEvents()
    {
        return device.hasPointerEvents();
    }

    
    @JsMethod
    public boolean hasPointerMotionEvents()
    {
        return device.hasPointerMotionEvents();
    }

    
    @JsMethod
    public boolean hasRepeatEvents()
    {
        return device.hasRepeatEvents();
    }

    @Override
	   @JsMethod
	   protected void hideNotify()
	{
	}


	@JsMethod
	public boolean isDoubleBuffered()
	{
	    return true;
	}


        @Override
	       @JsMethod
	       public void keyPressed(int keyCode)
	{
	}


        @Override
	       @JsMethod
	       protected void keyRepeated(int keyCode)
	{
	}


        @Override
	       @JsMethod
	       public void keyReleased(int keyCode)
	{
	}


        @Override
	       @JsMethod
	       protected void paint(Graphics g)
        {
        }


        @Override
	       @JsMethod
	       protected void pointerPressed(int x, int y)
    {
    }

    @Override
    @JsMethod
    protected void pointerReleased(int x, int y)
    {
    }

    @Override
    @JsMethod
    protected void pointerDragged(int x, int y)
    {
    }

    @Override
    @JsMethod
    public final void repaint() {
        super.repaint();
    }

    @Override
    @JsMethod
    public final void repaint(int x, int y, int width, int height)
    {
        super.repaint(x, y, width, height);
    }

    
    @JsMethod
    public final void serviceRepaints()
    {
    	if (currentDisplay != null) {
    		currentDisplay.serviceRepaints();
    	}
    }
    
    @JsMethod
    public void waitOnNotify(int wait)
        throws Exception
    {
        
    }   

    @Override
    @JsMethod
    public void setFullScreenMode(boolean mode) {
    	if (this.fullScreenMode != mode) {
            super.setFullScreenMode(mode);

                /*
                //TWB - GameCanvas is not used
    		if (this instanceof GameCanvas) {
    			width = -1;
    			height = -1;
    		}
                */
                
    		if (currentDisplay != null) {
                    ScreenListenerHandler.getInstance().fireWithState(mode);
                    this.sizeChanged(currentDisplay);
    		}
    	}
    }
    
    @Override
    @JsMethod
    protected void sizeChanged(int w, int h)
    {
    }
    
    @Override
    protected void showNotify()
    {
    }

    @Override
    @JsMethod
    public int getTypeAsInt() {
        return Canvas.TYPE;
    }
    
}
