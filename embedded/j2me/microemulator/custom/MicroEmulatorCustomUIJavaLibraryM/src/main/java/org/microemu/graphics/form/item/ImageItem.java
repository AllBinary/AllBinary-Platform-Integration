/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2005 Andres Navarro
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
 *  Other Contributor(s):
 *    Travis Berthelot
 */
package org.microemu.graphics.form.item;

import jsinterop.annotations.JsType;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.NullImage;

import org.allbinary.graphics.form.item.ABCustomItem;

import org.allbinary.graphics.color.BasicColorFactory;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
public class ImageItem extends ABCustomItem
{

	@JsProperty
	public static final int LAYOUT_DEFAULT = 0;

	@JsProperty
	public static final int LAYOUT_LEFT = 1;

	@JsProperty
	public static final int LAYOUT_RIGHT = 2;

	@JsProperty
	public static final int LAYOUT_CENTER = 3;

	@JsProperty
	public static final int LAYOUT_NEWLINE_BEFORE = 0x100;

	@JsProperty
	public static final int LAYOUT_NEWLINE_AFTER = 0x200;

        @JsProperty
        public static final int PLAIN = 0;
        @JsProperty
        public static final int HYPERLINK = 1;
        @JsProperty
        public static final int BUTTON = 2;
        
	Image img = NullImage.NULL_IMAGE;

	String altTextP;

	private int appearanceMode;

//	public ImageItem(String label, Image img, int layout, String altText) {
//		this(label, img, layout, altText, ImageItem.PLAIN);
//	}
	
	@JsConstructor
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

	@JsMethod
	public String getAltText() {
		return this.altTextP;
	}

	@JsMethod
	public int getAppearanceMode() {
		return this.appearanceMode;
	}

	@JsMethod
	public Image getImage() {
		return this.img;
	}

        @Override
	       @JsMethod
	       public int getLayout() {
		return super.getLayout();
	}

	@JsMethod
	public void setAltText(String text) {
		this.altTextP = text;
	}
	
	@JsMethod
	public void setImage(Image img) {
		if (img != null && img.isMutable()) {
		  //TWB - I shouldn't have mutables here for OpenGL
			//img = Image.createImage(img);
		}
		this.img = img;
		this.repaint();
	}

        @Override
	       @JsMethod
	       public void setLayout(int layout) {
		super.setLayout(layout);
	}

        @Override
	       @JsMethod
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
			if (this.layout == ImageItem.LAYOUT_DEFAULT || this.layout == ImageItem.LAYOUT_LEFT) {
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
	       @JsMethod
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
				return ABCustomItem.OUTOFITEM;
			}
		}
		if (gameKeyCode == Canvas.DOWN) {
			if (bottom < this.getHeight()) {
				if (this.getHeight() - bottom < f.getHeight()) {
					return this.getHeight() - bottom;
				} else {
					return f.getHeight();
				}
			} else {
				return ABCustomItem.OUTOFITEM;
			}
		}

		return 0;
	}

}
