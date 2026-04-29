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



public final class RGBImageFilter implements ImageFilter
{

    private static final RGBImageFilter instance = new RGBImageFilter();

    /**
     * @return the instance
     */
    public static RGBImageFilter getInstance() {
        return RGBImageFilter.instance;
    }
    
  private final double Rr, Rg, Rb;
  private final Color backgroundColor;
  private final Color foregroundColor;
  

  private RGBImageFilter()
	{
    this.backgroundColor = SwtDeviceComponent.getColor(new RGBA(
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getRed(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getGreen(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getBlue(), 255));
    this.foregroundColor = SwtDeviceComponent.getColor(new RGBA(
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getRed(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getGreen(),
    		((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getBlue(), 255));
    Rr = this.foregroundColor.getRed() - this.backgroundColor.getRed();
    Rg = this.foregroundColor.getGreen() - this.backgroundColor.getGreen();
    Rb = this.foregroundColor.getBlue() - this.backgroundColor.getBlue();
  }


//  public RGB filterRGB (int x, int y, RGB rgb)
//	{
//    int r, g, b;
//
//    if (Rr > 0) {
//      r = (int) (rgb.red * Rr) / 255 + backgroundColor.getRed();
//    } else {
//      r = (int) (rgb.red * -Rr) / 255 + foregroundColor.getRed();
//    }
//    if (Rr > 0) {
//      g = (int) (rgb.green * Rg) / 255 + backgroundColor.getGreen();
//    } else {
//      g = (int) (rgb.green * -Rg) / 255 + foregroundColor.getGreen();
//    }
//    if (Rr > 0) {
//      b = (int) (rgb.blue * Rb) / 255 + backgroundColor.getBlue();
//    } else {
//      b = (int) (rgb.blue * -Rb) / 255 + foregroundColor.getBlue();
//    }
//
//    return new RGB(r, g, b);
//  }

  //TWB - Required SWT 4.5
  public RGBA filterRGB (int x, int y, RGBA rgb)
	{
    int r, g, b;

    if (Rr > 0) {
      r = (int) (rgb.rgb.red * Rr) / 255 + this.backgroundColor.getRed();
    } else {
      r = (int) (rgb.rgb.red * -Rr) / 255 + this.foregroundColor.getRed();
    }
    if (Rr > 0) {
      g = (int) (rgb.rgb.green * Rg) / 255 + this.backgroundColor.getGreen();
    } else {
      g = (int) (rgb.rgb.green * -Rg) / 255 + this.foregroundColor.getGreen();
    }
    if (Rr > 0) {
      b = (int) (rgb.rgb.blue * Rb) / 255 + this.backgroundColor.getBlue();
    } else {
      b = (int) (rgb.rgb.blue * -Rb) / 255 + this.foregroundColor.getBlue();
    }

    return new RGBA(r, g, b, rgb.alpha);
  }

}
