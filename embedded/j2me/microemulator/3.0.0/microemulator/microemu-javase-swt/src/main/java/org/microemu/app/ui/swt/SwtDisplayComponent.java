/*
 *  MicroEmulator
 *  Copyright (C) 2001-2003 Bartek Teodorczyk <barteo@barteo.net>
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
 
package org.microemu.app.ui.swt;

import javax.microedition.lcdui.Displayable;

import org.eclipse.swt.graphics.RGBA;
import org.eclipse.swt.widgets.Canvas;

import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.DisplayRepaintListener;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.MutableImage;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtDisplayGraphics;
import org.microemu.device.swt.SwtMutableImage;

import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.logic.communication.log.PreLogUtil;
import org.allbinary.string.CommonStrings;
import org.eclipse.swt.graphics.ImageData;

//TODO extends Canvas like in swing version
public class SwtDisplayComponent implements DisplayComponent
{
//    private static final String CREATE = "create: ";
//    private static final String DISPOSE_PREVIOUS = "dispose: ";
    
	private Canvas deviceCanvas;
	private SwtMutableImage displayImage = null;
	private DisplayRepaintListener displayRepaintListener;
	
	private Runnable redrawRunnable = new Runnable()
	{
		public void run() 
		{
			if (!deviceCanvas.isDisposed()) {
				deviceCanvas.redraw();
			}
		}
	};


	SwtDisplayComponent(Canvas deviceCanvas)
	{
		this.deviceCanvas = deviceCanvas;
	}
	
	
	public void addDisplayRepaintListener(DisplayRepaintListener l)
	{
		displayRepaintListener = l;
	}


	public void removeDisplayRepaintListener(DisplayRepaintListener l)
	{
		if (displayRepaintListener == l) {
			displayRepaintListener = null;
		}
	}
	
	
	public SwtMutableImage getDisplayImage()
	{
		return displayImage;
	}


        private SwtMutableImage getScaledImage(SwtMutableImage displayImage) {

            final Device device = DeviceFactory.getDevice();
            final SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) device.getDeviceDisplay();
            
            final ImageData imageData = displayImage.image.getImageData().scaledTo(deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
            final SwtMutableImage scaledImage = new SwtMutableImage(SwtDeviceComponent.createImage(imageData));
            
            return scaledImage;
        }

	public void paint(SwtGraphics gc) 
	{
		synchronized (this) {
			if (displayImage != null) {

                            final SwtMutableImage swtMutableImage = this.getScaledImage(displayImage);
                            
                            //final SwtGraphics gc2 = ((SwtDisplayGraphics) swtMutableImage.getGraphics()).g;
                            //gc2.setForeground(gc2.getColor(new RGBA(0, 255, 0, 255)));
                            //final int border = 10;
                            //gc2.drawRectangle(border, border, swtMutableImage.getWidth() - border, swtMutableImage.getHeight() - border);
                            
                            gc.drawImage(swtMutableImage.image, 0, 0);
                                                        
                            swtMutableImage.image.dispose();
			}
		}
	}

//        int lastWidth = -1;
//        int lastHeight = -1;
//        SwtMutableImage image = null;
  
	public void repaintRequest(int x, int y, int width, int height) 
	{
		if (!deviceCanvas.isDisposed()) {			
			final MIDletAccess ma = MIDletBridge.getMIDletAccess();
			if (ma == null) {
				return;
			}
			final DisplayAccess da = ma.getDisplayAccess();
			if (da == null) {
				return;
			}
			final Displayable current = da.getCurrent();
			if (current == null) {
				return;
			}

			final Device device = DeviceFactory.getDevice();
                        final SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) device.getDeviceDisplay();
                        final DisplayInfoSingleton displayInfoSingleton = DisplayInfoSingleton.getInstance();
                        final int lastWidth = displayInfoSingleton.getLastWidth();
                        final int lastHeight = displayInfoSingleton.getLastHeight();
                        
                        //final SwtMutableImage image = new SwtMutableImage(width, height);
                        final SwtMutableImage image = new SwtMutableImage(lastWidth, lastHeight);
//                        if(this.lastWidth != width || this.lastHeight != height) {
//                            image = new SwtMutableImage(deviceWidth, deviceHeight);
//                            displayImage = image;
//                        }
			
                        //System.out.println(CREATE + this);
						
			final SwtGraphics gc = ((SwtDisplayGraphics) image.getGraphics()).g;
			try {
                                //TWB - SwtDeviceDisplay - this is where the paint processing is called
				//deviceDisplay.paintDisplayable(gc, x, y, width, height);
                                deviceDisplay.paintDisplayable(gc, x, y, lastWidth, lastHeight);
				if (!deviceDisplay.isFullScreenMode()) {
					deviceDisplay.paintControls(gc);
				}
                                
                                //gc.setForeground(gc.getColor(new RGBA(255, 0, 0, 255)));
                                //final int border = 20;
                                //gc.drawRectangle(border, border, lastWidth - border, lastHeight - border);
                                //gc.drawRectangle(border, border, width - border, height - border);
                                
                        } catch(Exception e) {
                            final CommonStrings commonStrings = CommonStrings.getInstance();
                            PreLogUtil.put(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
			} finally {
				gc.dispose();
			}

			synchronized (this) {
				if (displayImage != null) {
                                    //System.out.println(DISPOSE_PREVIOUS + this);
					displayImage.image.dispose();
				}
                                                                
				displayImage = image;
			}
			
			//fireDisplayRepaint(displayImage);
	
                        deviceCanvas.getDisplay().syncExec(redrawRunnable);
			//deviceCanvas.getDisplay().asyncExec(redrawRunnable);
		}
	}
	
	
	private void fireDisplayRepaint(MutableImage image)
	{
		if (displayRepaintListener != null) {
			displayRepaintListener.repaintInvoked(image);
		}
	}
  
}
