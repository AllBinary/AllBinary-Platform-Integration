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
 
package org.microemu.device.swt;

import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.RGBA;
import org.microemu.app.ui.swt.ImageFilter;
import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.device.DeviceFactory;



public final class GrayImageFilter implements ImageFilter
{

    private static final GrayImageFilter instance = new GrayImageFilter();

    /**
     * @return the instance
     */
    public static GrayImageFilter getInstance() {
        return instance;
    }
    
  private final double Yr, Yg, Yb;
  private final double Rr, Rg, Rb;
  private final Color foregroundColor;

  private GrayImageFilter ()
	{
    this(0.2126d, 0.7152d, 0.0722d);
  }


  public GrayImageFilter (double Yr, double Yg, double Yb)
	{
    this.Yr = Yr;
    this.Yg = Yg;
    this.Yb = Yb;
    Color backgroundColor = SwtDeviceComponent.getColor(new RGBA(
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getRed(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getGreen(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getBlue(), 255));
    foregroundColor = SwtDeviceComponent.getColor(new RGBA(
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getRed(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getGreen(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getBlue(), 255));
    Rr = (backgroundColor.getRed() - foregroundColor.getRed()) / 256d;
    Rg = (backgroundColor.getGreen() - foregroundColor.getGreen()) / 256d;
    Rb = (backgroundColor.getBlue() - foregroundColor.getBlue()) / 256d;
  }


//  public RGB filterRGB (int x, int y, RGB rgb)
//	{
//    int Y = (int)(Yr * rgb.red + Yg * rgb.green + Yb * rgb.blue) % 256;
//    if (Y > 255) {
//      Y = 255;
//    }
//    
//    return new RGB(    
//    		(int) (Rr * Y) + foregroundColor.getRed(),
//    		(int) (Rg * Y) + foregroundColor.getGreen(),
//    		(int) (Rb * Y) + foregroundColor.getBlue());
//  }

  //TWB - Required SWT 4.5
  public RGBA filterRGB (int x, int y, RGBA rgb)
	{
    int Y = (int)(Yr * rgb.rgb.red + Yg * rgb.rgb.green + Yb * rgb.rgb.blue) % 256;
    if (Y > 255) {
      Y = 255;
    }
    
    return new RGBA(    
    		(int) (Rr * Y) + foregroundColor.getRed(),
    		(int) (Rg * Y) + foregroundColor.getGreen(),
    		(int) (Rb * Y) + foregroundColor.getBlue(), rgb.alpha);
  }
  
}
