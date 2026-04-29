/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2005 Andres Navarro
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.NullCanvas;

import org.allbinary.graphics.form.item.CustomItem;

import org.allbinary.graphics.color.BasicColorFactory;

public class ImageItem extends CustomItem 
{

	public static final int LAYOUT_DEFAULT = 0;

	public static final int LAYOUT_LEFT = 1;

	public static final int LAYOUT_RIGHT = 2;

	public static final int LAYOUT_CENTER = 3;

	public static final int LAYOUT_NEWLINE_BEFORE = 0x100;

	public static final int LAYOUT_NEWLINE_AFTER = 0x200;

        public static final int PLAIN = 0;
        public static final int HYPERLINK = 1;
        public static final int BUTTON = 2;
        
	Image img = NullCanvas.NULL_IMAGE;

	String altTextP;

	private int appearanceMode;

	public ImageItem(String label, Image img, int layout, String altText) {
		this(label, img, layout, altText, ImageItem.PLAIN);
	}
	
	public ImageItem(String label, Image img, int layout, String altText, int appearanceMode) 
	{
		super(label, 
		        BasicColorFactory.getInstance().BLACK, 
		        BasicColorFactory.getInstance().WHITE);

		// may throw IllegalArgumentException
		// (that is the intentended behaviour)
		this.setLayout(layout);
		if (appearanceMode != ImageItem.PLAIN && appearanceMode != ImageItem.HYPERLINK
				&& appearanceMode != ImageItem.BUTTON) {
			throw new IllegalArgumentException();
		}

		this.setImage(img);
		this.altTextP = altText;
		this.appearanceMode = appearanceMode;
	}

	public String getAltText() {
		return this.altTextP;
	}

	public int getAppearanceMode() {
		return this.appearanceMode;
	}

	public Image getImage() {
		return this.img;
	}

        @Override
	public int getLayout() {
		return super.getLayout();
	}

	public void setAltText(String text) {
		this.altTextP = text;
	}
	
	public void setImage(Image img) {
		if (img != null && img.isMutable()) {
		  //TWB - I shouldn't have mutables here for OpenGL
			//img = Image.createImage(img);
		}
		this.img = img;
		repaint();
	}

        @Override
	public void setLayout(int layout) {
		super.setLayout(layout);
	}

        @Override
	public int getHeight() {
		if (this.img == null) {
			return super.getHeight();
		} else {
			return super.getHeight() + this.img.getHeight();
		}
	}

	/*
	public int paint(Graphics g) {
		super.paintContent(g);

		if (this.img != null) {
			g.translate(0, super.getHeight());
			if (layout == LAYOUT_DEFAULT || layout == LAYOUT_LEFT) {
				g.drawImage(this.img, 0, 0, Graphics.LEFT | Graphics.TOP);
			} else if (layout == LAYOUT_RIGHT) {
				g.drawImage(img, owner.getWidth(), 0, Graphics.RIGHT | Graphics.TOP);
			} else if (layout == LAYOUT_CENTER) {
				g.drawImage(img, (owner.getWidth() >> 1), 0, Graphics.HCENTER | Graphics.TOP);
			} else {
				g.drawImage(this.img, 0, 0, Graphics.LEFT | Graphics.TOP);
			}
			g.translate(0, -super.getHeight());
		}

		return getHeight();
	}
	*/

        @Override
	public int traverse(int gameKeyCode, int top, int bottom, boolean action) {
		Font f = Font.getDefaultFont();

		if (gameKeyCode == Canvas.UP) {
			if (top > 0) {
				if ((top % f.getHeight()) == 0) {
					return -f.getHeight();
				} else {
					return -(top % f.getHeight());
				}
			} else {
				return CustomItem.OUTOFITEM;
			}
		}
		if (gameKeyCode == Canvas.DOWN) {
			if (bottom < getHeight()) {
				if (getHeight() - bottom < f.getHeight()) {
					return this.getHeight() - bottom;
				} else {
					return f.getHeight();
				}
			} else {
				return CustomItem.OUTOFITEM;
			}
		}

		return 0;
	}

}
