/*
 * MicroEmulator 
 * Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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
 * Contributor(s): 
 *   3GLab
 */

package org.microemu.device.swt;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.RGBA;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.microemu.DisplayAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.BareMain;
import org.microemu.app.ui.swt.ImageFilter;
import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
//import org.microemu.device.DisplayGraphics;
import org.microemu.device.MutableImage;


public class SwtDisplayGraphics extends javax.microedition.lcdui.Graphics //implements DisplayGraphics 
{
	public SwtGraphics g;

	protected MutableImage image;
	private int color = 0;
	private javax.microedition.lcdui.Font currentFont = javax.microedition.lcdui.Font.getDefaultFont();
	private ImageFilter filter;

	
	public SwtDisplayGraphics(SwtGraphics a_g, MutableImage a_image) 
	{
		this.image = a_image;
	
                this.initG(a_g);

                Device device = DeviceFactory.getDevice();
		if (device.getDeviceDisplay().isColor()) {
			this.filter = RGBImageFilter.getInstance();
		} else {
			if (device.getDeviceDisplay().numColors() == 2) {
				this.filter = BWImageFilter.getInstance();
			} else {
				this.filter = GrayImageFilter.getInstance();
			}
		}                            
	}

        public void initG(final SwtGraphics g) {
		
            this.g = g;

            Device device = DeviceFactory.getDevice();
            
                final org.microemu.device.impl.Color backgroundColor = ((SwtDeviceDisplay) device.getDeviceDisplay()).getBackgroundColor();
		g.setBackground(g.getColor(new RGBA(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 255)));
                
		SwtFont tmpFont = (SwtFont) ((SwtFontManager) device.getFontManager()).getFont(this.currentFont);
		g.setFont(tmpFont.getFont());		
        }
	
	public MutableImage getImage() 
	{
		return image;
	}

	
	public int getColor() 
	{
		return color;
	}

	
	public void setColor(int RGB) 
	{
		this.color = RGB;

                //g.setForeground(g.getColor(new RGBA((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff, 0)));
                //g.setForeground(g.getColor(new RGBA((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff, 255)));
		this.g.setForeground(this.g.getColor(this.filter.filterRGB(0, 0, new RGBA((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff, (color >> 24) & 0xff))));
                //g.setForeground(g.getColor(filter.filterRGB(0, 0, new RGB((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff))));
	}

	
	public javax.microedition.lcdui.Font getFont() 
	{
		return currentFont;
	}

	
	public void setFont(javax.microedition.lcdui.Font font) 
	{
		this.currentFont = font;
		SwtFont tmpFont = (SwtFont)((SwtFontManager) DeviceFactory.getDevice().getFontManager()).getFont(this.currentFont);
		this.g.setFont(tmpFont.getFont());
	}

	
	public void clipRect(int x, int y, int width, int height) 
	{
		Rectangle rect = new Rectangle(x, y, width, height);

		if (rect.x < getClipX()) {
			rect.x = getClipX();
		}

		if (rect.y < getClipY()) {
			rect.y = getClipY();
		}

		if (x + width > getClipX() + getClipWidth()) {
			rect.width = getClipX() + getClipWidth() - rect.x;
		} else {
			rect.width = x + width - rect.x;
		}

		if (y + height > getClipY() + getClipHeight()) {
			rect.height = getClipY() + getClipHeight() - rect.y;
		} else {
			rect.height = y + height - rect.y;
		}

		setClip(rect.x, rect.y, rect.width, rect.height);
	}

	
	public void setClip(int x, int y, int width, int height) 
	{
		this.g.setClipping(x, y, width, height);
	}

	
	public int getClipX() 
	{
		Rectangle rect = this.g.getClipping();
		if (rect == null) {
			return 0;
		} else {
			return rect.x;
		}
	}

	
	public int getClipY() 
	{
		Rectangle rect = this.g.getClipping();
		if (rect == null) {
			return 0;
		} else {
			return rect.y;
		}
	}

	
	public int getClipHeight() 
	{
		Rectangle rect = this.g.getClipping();
		if (rect == null) {
			DisplayAccess da = MIDletBridge.getMIDletAccess().getDisplayAccess();
			return da.getCurrent().getHeight();
		} else {
			return rect.height;
		}
	}

	
	public int getClipWidth() 
	{
		Rectangle rect = this.g.getClipping();
		if (rect == null) {
			DisplayAccess da = MIDletBridge.getMIDletAccess().getDisplayAccess();
			return da.getCurrent().getWidth();
		} else {
			return rect.width;
		}
	}

	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) 
	{
		this.g.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	
	public void drawImage(Image img, int x, int y, int anchor) 
	{
            img.getPlatformImage().drawImage(img, x, y, anchor, this, g);
	}

	
	public void drawLine(int x1, int y1, int x2, int y2) 
	{
		this.g.drawLine(x1, y1, x2, y2);
	}

	
	public void drawRect(int x, int y, int width, int height) 
	{
            //g.drawRectangle(x, y, width, height);
		drawLine(x, y, x + width, y);
		drawLine(x + width, y, x + width, y + height);
		drawLine(x + width, y + height, x, y + height);
		drawLine(x, y + height, x, y);
	}


	public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dst, int y_dst, int anchor) {
            src.getPlatformImage().drawRegion(src, x_src, y_src, width, height, transform, x_dst, y_dst, anchor, this);
	}


	public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) {
		// TODO implement drawRGB
		super.drawRGB(rgbData, offset, scanlength, x, y, width, height, processAlpha);
	}
	
	
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		int[] points = new int[6];
		points[0] = x1;
		points[1] = y1;
		points[2] = x2;
		points[3] = y2;
		points[4] = x3;
		points[5] = y3;

		this.g.fillPolygon(points);
	}


	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) 
	{
		this.g.drawRoundRectangle(x, y, width, height, arcWidth, arcHeight);
	}

	
	public void drawString(String str, int x, int y, int anchor) 
	{
		int newx = x;
		int newy = y;

		if (anchor == 0) {
			anchor = javax.microedition.lcdui.Graphics.TOP | javax.microedition.lcdui.Graphics.LEFT;
		}

		if ((anchor & javax.microedition.lcdui.Graphics.VCENTER) != 0) {
			newy -= this.g.getFontMetrics().getAscent();
		} else if ((anchor & javax.microedition.lcdui.Graphics.BOTTOM) != 0) {
			newy -= g.getFontMetrics().getHeight();
		}
		if ((anchor & javax.microedition.lcdui.Graphics.HCENTER) != 0) {
			newx -= this.g.stringWidth(str) / 2;
		} else if ((anchor & javax.microedition.lcdui.Graphics.RIGHT) != 0) {
			newx -= g.stringWidth(str);
		}

		boolean textAntialiasing = ((SwtFontManager) DeviceFactory.getDevice().getFontManager()).getAntialiasing();
		boolean graphicsAntialiasing = this.g.getAntialias();
		if (textAntialiasing != graphicsAntialiasing) {
			this.g.setAntialias(textAntialiasing);
		}
		
		this.g.drawString(str, newx, newy, true);
		
		if (textAntialiasing != graphicsAntialiasing) {
			this.g.setAntialias(graphicsAntialiasing);
		}

		if ((this.currentFont.getStyle() & javax.microedition.lcdui.Font.STYLE_UNDERLINED) != 0) {
			this.g.drawLine(newx, newy + 1, newx + this.g.stringWidth(str), newy + 1);
		}
	}

        public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {
            this.drawString(str.substring(offset, len), x, y, anchor);
        }
	
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) 
	{
        Color tmp = this.g.getBackground();
        this.g.setBackground(this.g.getForeground());
		this.g.fillArc(x, y, width, height, startAngle, arcAngle);
        this.g.setBackground(tmp);
	}

	
	public void fillRect(int x, int y, int width, int height) 
	{
		Color tmp = this.g.getBackground();
		this.g.setBackground(this.g.getForeground());
		this.g.fillRectangle(x, y, width, height);
		this.g.setBackground(tmp);
	}

	
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) 
	{
		this.g.fillRoundRectangle(x, y, width, height, arcWidth, arcHeight);
	}

	
	public void translate(int x, int y) 
	{
		super.translate(x, y);
		this.g.translate(x, y);
	}

}
