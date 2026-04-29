/*
 *  MicroEmulator
 *  Copyright (C) 2006 Bartek Teodorczyk <barteo@barteo.net>
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

package org.microemu.device.j2se;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class J2SESystemFont implements J2SEFont {
	
	private final static Graphics2D graphics = (Graphics2D) new BufferedImage(1, 1,
			BufferedImage.TYPE_INT_ARGB).getGraphics();

	private String name;
	
	private String style;
	
	private int size;
	
	private boolean antialiasing;
	
	private boolean initialized;
	
	private FontMetrics fontMetrics;

	public J2SESystemFont(String name, String style, int size, boolean antialiasing) {
		this.name = name;
		this.style = style.toLowerCase();
		this.size = size;
		this.antialiasing = antialiasing;
		
		this.initialized = false;
	}

	public void setAntialiasing(boolean antialiasing) {
		if (this.antialiasing != antialiasing) {
			this.antialiasing = antialiasing;
			this.initialized = false;
		}
	}
	
	public int charWidth(char ch) {
		this.checkInitialized();

		return this.fontMetrics.charWidth(ch);
	}

	public int charsWidth(char[] ch, int offset, int length) {
		this.checkInitialized();

		return this.fontMetrics.charsWidth(ch, offset, length);
	}

	public int getBaselinePosition() {
		this.checkInitialized();

		return this.fontMetrics.getAscent();
	}

	public int getHeight() {
		this.checkInitialized();

		return this.fontMetrics.getHeight();
	}

	public int stringWidth(String str) {
		this.checkInitialized();

		return this.fontMetrics.stringWidth(str);
	}

	public Font getFont() {
		this.checkInitialized();

		return this.fontMetrics.getFont();
	}
	
	private synchronized void checkInitialized() {
		if (!this.initialized) {
			int awtStyle = 0;
			if (this.style.indexOf("plain") != -1) {
				awtStyle |= Font.PLAIN;
			}
			if (this.style.indexOf("bold") != -1) {
				awtStyle |= Font.BOLD;
			}
			if (this.style.indexOf("italic") != -1) {
				awtStyle |= Font.ITALIC;
			}
			if (this.style.indexOf("underlined") != -1) {
				// TODO underlined style not implemented
			}
			if (this.antialiasing) {
				J2SESystemFont.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			} else {
				J2SESystemFont.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			}
			this.fontMetrics = J2SESystemFont.graphics.getFontMetrics(new Font(this.name, awtStyle, this.size));
			this.initialized = true;
		}
	}

}
