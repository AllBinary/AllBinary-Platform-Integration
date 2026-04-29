/*
 *  MicroEmulator
 *  Copyright (C) 2002-2003 Bartek Teodorczyk <barteo@barteo.net>
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

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.RGBA;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;


public class SwtGraphics 
{
	private Display display;
	private GC gc;
	private int transX = 0;
	private int transY = 0;
	private HashMap colors;
	

	public SwtGraphics(Display display) 
	{
		this.display = display;
		this.gc = new GC(display);
	}


	public SwtGraphics(GC gc)
	{
		this.gc = gc;
	}


	public void dispose()
	{
		this.gc.dispose();
		
		if (this.colors != null) {
			for (Iterator it = this.colors.values().iterator(); it.hasNext(); ) {
				((Color) it.next()).dispose();
			}
		}
	}


        public void setTransform(final Transform transform) {
            this.gc.setTransform(transform);
            this.gc.setAntialias(SWT.ON);
            this.gc.setInterpolation(SWT.HIGH);
        }

	public void drawImage(Image image, int srcX, int srcY, int srcWidth, int srcHeight,
			int destX, int destY, int destWidth, int destHeight) 
	{            
		this.gc.drawImage(image, srcX, srcY, srcWidth, srcHeight, destX + this.transX, destY + this.transY, destWidth, destHeight);
	}


	public void drawImage(Image image, int x, int y) 
	{
		this.gc.drawImage(image, x + this.transX, y + this.transY);
	}
	
	
	public void translate(int x, int y)
	{
		this.transX += x;
		this.transY += y;
	}
	
	
//	public Color getColor(RGB rgb)
//	{
//		if (colors == null) {
//			colors = new HashMap();
//		}
//		
//		Color result = (Color) colors.get(rgb);
//		if (result == null) {
//			result = new Color(display, rgb);
//			colors.put(rgb, result);
//		}
//		
//		return result;
//	}

        //TWB - Required SWT 4.5
	public Color getColor(final RGBA rgb)
	{
		if (this.colors == null) {
			this.colors = new HashMap();
		}
		
		Color result = (Color) this.colors.get(rgb);
		if (result == null) {
			result = new Color(this.display, rgb);
			this.colors.put(rgb, result);
		}
		
		return result;
	}
        

	public FontMetrics getFontMetrics() 
	{
		return this.gc.getFontMetrics();
	}


	public void setFont(Font font) 
	{
		this.gc.setFont(font);
	}


	public Color getBackground() 
	{
		return this.gc.getBackground();
	}


	public Color getForeground() 
	{
		return this.gc.getForeground();
	}


	public void setBackground(Color color) 
	{
		this.gc.setBackground(color);
	}


	public void setForeground(Color color) 
	{
		this.gc.setForeground(color);
                this.gc.setAlpha(color.getAlpha());
	}


	public Rectangle getClipping() 
	{
		return this.gc.getClipping();
	}


	public void setClipping(int x, int y, int width, int height) 
	{
		this.gc.setClipping(x + this.transX, y + this.transY, width, height);
	}


	public void drawArc(int x, int y, int width, int height, int startAngle, int endAngle) 
	{
		this.gc.drawArc(x + this.transX, y + this.transY, width, height, startAngle, endAngle);
	}


	public void drawLine(int x1, int y1, int x2, int y2) 
	{
		this.gc.drawLine(x1 + this.transX, y1 + this.transY, x2 + this.transX, y2 + this.transY);
	}

        public void drawRectangle(int x, int y, int width, int height) 
	{
		this.gc.drawRectangle(x + this.transX, y + this.transY, width - x, height - y);
	}

	public void drawRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) 
	{
		this.gc.drawRoundRectangle(x + this.transX, y + this.transY, width - x, height - y, arcWidth, arcHeight);
	}


	public void drawString(String string, int x, int y, boolean isTransparent) 
	{
		this.gc.drawString(string, x + this.transX, y + this.transY, isTransparent);
	}


	public void fillArc(int x, int y, int width, int height, int startAngle, int endAngle) 
	{
		this.gc.fillArc(x + this.transX, y + this.transY, width, height, startAngle, endAngle);
	}


	public void fillPolygon(int[] pointArray) 
	{
		this.gc.fillPolygon(pointArray);
	}


	public void fillRectangle(int x, int y, int width, int height) 
	{
		this.gc.fillRectangle(x + this.transX, y + this.transY, width, height);
	}


	public void fillRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) 
	{
		this.gc.fillRoundRectangle(x + this.transX, y + this.transY, width, height, arcWidth, arcHeight);
	}


	public int stringWidth(String string) 
	{
		return this.gc.stringExtent(string).x;
	}


	public Font getFont() 
	{
		return this.gc.getFont();
	}


	public void setClipping(Rectangle rect) 
	{
		Rectangle tmp = new Rectangle(rect.x + this.transX, rect.y + this.transY, rect.width, rect.height);
		this.gc.setClipping(tmp);
	}
	
	
	public boolean getAntialias()
	{
		if (this.gc.getAntialias() == SWT.ON) {
			return  true;
		} else {
			return false;
		}
	}
	
	
	public void setAntialias(boolean antialias)
	{
		if (antialias) {
			this.gc.setAntialias(SWT.ON);
		} else {
			this.gc.setAntialias(SWT.OFF);
		}
	}

}
