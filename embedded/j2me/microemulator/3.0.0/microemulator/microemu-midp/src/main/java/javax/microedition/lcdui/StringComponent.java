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
 */

package javax.microedition.lcdui;

import org.allbinary.graphics.ItemColorFactory;
import org.microemu.device.DeviceFactory;

class StringComponent {
	private String text;

	private int breaks[] = new int[4];

	private boolean invertPaint = false;

	private int numOfBreaks;

	private int width;

	private int widthDecreaser;

	public StringComponent() {
		this(null);
	}

	public StringComponent(String text) {
		synchronized (this) {
			this.width = -1;
			this.widthDecreaser = 0;
			this.setText(text);
		}
	}

	public int getCharHeight() {
		return Font.getDefaultFont().getHeight();
	}

    private final boolean[] HAS_NOT_CHANGED_ARRAY = new boolean[0];
    private final int[] LAST_WIDTH_ARRAY = new int[0];
    private boolean[] hasNotChanged = this.HAS_NOT_CHANGED_ARRAY;
    private int[] lastWidth = this.LAST_WIDTH_ARRAY;
	public int getCharPositionX(int num) {
		synchronized (this) {
			if (this.numOfBreaks == -1) {
				updateBreaks();
			}
	
			int i, prevIndex = 0;
			Font f = Font.getDefaultFont();
	
			for (i = 0; i < this.numOfBreaks; i++) {
				if (num < breaks[i]) {
					break;
				}
				prevIndex = breaks[i];
			}
			
                        if(!this.hasNotChanged[num]) {
                            this.lastWidth[num] = f.substringWidth(this.text, prevIndex, num - prevIndex);
                            this.hasNotChanged[num] = true;
                        }
			return lastWidth[num];
                }
	}

	public int getCharPositionY(int num) {
		int y = 0;
		synchronized (this) {
			if (this.numOfBreaks == -1) {
				this.updateBreaks();
			}
	
			Font f = Font.getDefaultFont();
	
			for (int i = 0; i < this.numOfBreaks; i++) {
				if (num < this.breaks[i]) {
					break;
				}
				y += f.getHeight();
			}
		}

		return y;
	}

	public int getHeight() {
		int height;
		synchronized (this) {
			if (this.numOfBreaks == -1) {
				this.updateBreaks();
			}

			Font f = Font.getDefaultFont();

			if (this.text == null) {
				return 0;
			}

			if (this.numOfBreaks == 0) {
				return f.getHeight();
			}

			height = this.numOfBreaks * f.getHeight();

			if (this.breaks[this.numOfBreaks - 1] == this.text.length() - 1
					&& this.text.charAt(text.length() - 1) == '\n') {
			} else {
				height += f.getHeight();
			}
		}

		return height;
	}

	public String getText() {
		return this.text;
	}

	public void invertPaint(boolean state) {
		synchronized (this) {
			this.invertPaint = state;
		}
	}

	public int paint(Graphics g) {
		if (this.text == null) {
			return 0;
		}

		int y;
		synchronized (this) {
			if (this.numOfBreaks == -1) {
				this.updateBreaks();
			}
	
			int i, prevIndex;
			Font f = Font.getDefaultFont();
	
			ItemColorFactory itemColorFactory = ItemColorFactory.getInstance();
			
			for (i = prevIndex = y = 0; i < this.numOfBreaks; i++) {
				if (this.invertPaint) {
				    g.setColor(itemColorFactory.PAINT);
				} else {
				    g.setColor(itemColorFactory.INVERT_PAINT);
				}
				g.fillRect(0, y, width, f.getHeight());
				if (this.invertPaint) {
				    g.setColor(itemColorFactory.INVERT_PAINT);
				} else {
				    g.setColor(itemColorFactory.PAINT);
				}
				g.drawSubstring(this.text, prevIndex, breaks[i] - prevIndex, 0, y, 0);
				prevIndex = breaks[i];
				y += f.getHeight();
			}
			// By adding the OR clasuse (text length comparison) we make sure
			// that even if the current value of a ChoiceGroup is empty, there will
			// be some visual clue that the ChoiceGroup is there
			if (prevIndex != this.text.length() || this.text.length() == 0) {
				if (this.invertPaint) {
				    g.setColor(itemColorFactory.PAINT);
				} else {
				    g.setColor(itemColorFactory.INVERT_PAINT);
				}
				g.fillRect(0, y, width, f.getHeight());
				if (this.invertPaint) {
				    g.setColor(itemColorFactory.INVERT_PAINT);
				} else {
				    g.setColor(itemColorFactory.PAINT);
				}
				g.drawSubstring(text, prevIndex, text.length() - prevIndex, 0, y, 0);
				y += f.getHeight();
			}
		}

		return y;
	}

	public void setText(String text) {
		synchronized (this) {
			this.text = text;
                        
                    if (text == null) {
                        this.hasNotChanged = this.HAS_NOT_CHANGED_ARRAY;
                        this.lastWidth = this.LAST_WIDTH_ARRAY;
                    } else {
                        final int size = text.length();
                        this.hasNotChanged = new boolean[size];
                        this.lastWidth = new int[size];
                    }
                        
			this.numOfBreaks = -1;
		}
	}

	public void setWidthDecreaser(int widthDecreaser) {
		synchronized (this) {
			this.widthDecreaser = widthDecreaser;
			this.numOfBreaks = -1;
		}
	}

	private void insertBreak(int pos) {
		int i;

		for (i = 0; i < this.numOfBreaks; i++) {
			if (pos < this.breaks[i]) {
				break;
			}
		}
		if (this.numOfBreaks + 1 == this.breaks.length) {
			int newbreaks[] = new int[breaks.length + 4];
			System.arraycopy(breaks, 0, newbreaks, 0, numOfBreaks);
			breaks = newbreaks;
		}
		System.arraycopy(breaks, i, breaks, i + 1, numOfBreaks - i);
		breaks[i] = pos;
		numOfBreaks++;
	}

	private void updateBreaks() {
		if (this.text == null) {
			return;
		}

		// TODO use Displayable width
		this.width = DeviceFactory.getDevice().getDeviceDisplay().getWidth()
				- this.widthDecreaser;

		int prevIndex = 0;
		int canBreak = 0;
		this.numOfBreaks = 0;
		Font f = Font.getDefaultFont();

		for (int i = 0; i < this.text.length(); i++) {
			if (this.text.charAt(i) == ' ') {
				canBreak = i + 1;
			}
			if (this.text.charAt(i) == '\n') {
				this.insertBreak(i);
				canBreak = 0;
				prevIndex = i + 1;
				continue;
			}
			if (f.substringWidth(this.text, prevIndex, i - prevIndex + 1) > this.width) {
				if (canBreak != 0) {
					this.insertBreak(canBreak);
					i = canBreak;
					prevIndex = i;
				} else {
					this.insertBreak(i);
					prevIndex = i + 1;
				}
				canBreak = 0;
			}
		}
	}

}
