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

package org.microemu.device.swt;

import org.allbinary.graphics.threed.SWTJOGLProcessor;
import org.allbinary.logic.string.StringUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.microemu.app.ui.swt.SwtDeviceComponent;

public class SwtSystemFont implements SwtFont {

	private String name;
	
	private String style;
	
	private int size;
        private int halfSize;
        private float extraWidth;
        private float extraHeight;
	
	private boolean antialiasing;
	
	private boolean initialized;
	
	private Font font;

	public SwtSystemFont(String name, String style, int size, boolean antialiasing) {
		this.name = name;
		this.style = style.toLowerCase();
                if(size < 6) {
                    throw new RuntimeException();
                }
		this.size = size - 6;
                this.halfSize = this.size >> 1;
                this.extraWidth = SWTJOGLProcessor.getInstance().isJOGL() ? 2.0f : 1.0f;
                this.extraHeight = SWTJOGLProcessor.getInstance().isJOGL() ? 2.0f : 1.0f;
		this.antialiasing = antialiasing;
		
		this.initialized = false;
	}

	public void setAntialiasing(boolean antialiasing) {
		if (this.antialiasing != antialiasing) {
			this.antialiasing = antialiasing;
			this.initialized = false;
		}
	}

	public Font getFont() {
		this.checkInitialized();

		return this.font;
	}

	private synchronized void checkInitialized() {
		if (!this.initialized) {
			int swtStyle = 0;
			if (this.style.indexOf("plain") != -1) {
				swtStyle |= SWT.NORMAL;
			}
			if (this.style.indexOf("bold") != -1) {
				swtStyle |= SWT.BOLD;
			}
			if (this.style.indexOf("italic") != -1) {
				swtStyle |= SWT.ITALIC;
			}
			if (this.style.indexOf("underlined") != -1) {
				// TODO underlined style not implemented
			}
			this.font = SwtDeviceComponent.getFont(this.name, this.size, swtStyle, this.antialiasing);
			this.initialized = true;
		}
	}

	public int charWidth(char ch) {
		return charsWidth(new char[] {ch}, 0, 1);
	}

	public int charsWidth(char[] ch, int offset, int length) {
		checkInitialized();

                final String str = new String(ch, offset, length);
                return (int) ((SwtDeviceComponent.stringWidth(this.font, str) + (StringUtil.getInstance().count(str, ' ') * this.halfSize)) * this.extraWidth);
	}

	public int getBaselinePosition() {
		this.checkInitialized();
		
		return SwtDeviceComponent.getFontMetrics(this.font).getAscent();
	}

	public int getHeight() {
		this.checkInitialized();
		
		return (int) (SwtDeviceComponent.getFontMetrics(this.font).getHeight() * this.extraHeight);
	}

	public int stringWidth(String str) {
		this.checkInitialized();
		
		return (int) ((SwtDeviceComponent.stringWidth(this.font, str) + (StringUtil.getInstance().count(str, ' ') * this.halfSize)) * this.extraWidth);
	}

}
