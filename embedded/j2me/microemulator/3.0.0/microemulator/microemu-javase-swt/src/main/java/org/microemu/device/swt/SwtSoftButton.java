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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
//import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.RGBA;
import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.Shape;
import org.microemu.device.impl.SoftButton;

public class SwtSoftButton extends SwtButton implements SoftButton {

	public static int LEFT = 1;
	public static int RIGHT = 2;

	private int type;

	private Image normalImage;
	private Image pressedImage;

	private Vector commandTypes = new Vector();

	private Command command = null;

	private Rectangle paintable;

	private int alignment;

	private boolean visible;

	private boolean pressed;
	
	private Font font;

	/**
	 * @param name
	 * @param rectangle
	 * @param keyCode - Integer.MIN_VALUE when unspecified
	 * @param keyName
	 * @param paintable
	 * @param alignmentName
	 * @param commands
	 * @param font
	 */
	public SwtSoftButton(String name, Shape shape, int keyCode, String keyName,
			Rectangle paintable, String alignmentName, Vector commands, Font font) {
		super(name, shape, keyCode, keyName, new Hashtable());

		this.type = TYPE_COMMAND;

		this.paintable = paintable;
		this.visible = true;
		this.pressed = false;
		this.font = font;

		if (alignmentName != null) {
			try {
				this.alignment = SwtSoftButton.class.getField(alignmentName).getInt(null);
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}

		for (Enumeration e = commands.elements(); e.hasMoreElements();) {
			String tmp = (String) e.nextElement();
			try {
				this.addCommandType(Command.class.getField(tmp).getInt(null));
			} catch (Exception ex) {
				System.err.println("a3" + ex);
			}
		}
	}

	public SwtSoftButton(String name, Rectangle paintable, Image normalImage, Image pressedImage) {
		super(name, null, Integer.MIN_VALUE, null, null);
		
		this.type = TYPE_ICON;
		
		this.paintable = paintable;
		this.normalImage = normalImage;
		this.pressedImage = pressedImage;
		
		this.visible = true;
		this.pressed = false;
	}

	
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the command attribute of the SoftButton object
	 * 
	 * @param cmd
	 *            The new command value
	 */
	public void setCommand(Command cmd) {
		synchronized (this) {
			this.command = cmd;
		}
	}

	/**
	 * Gets the command attribute of the SoftButton object
	 * 
	 * @return The command value
	 */
	public Command getCommand() {
		return this.command;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean state) {
		this.visible = state;
	}

	public boolean isPressed() {
		return this.pressed;
	}

	public void setPressed(boolean state) {
		pressed = state;
	}

	public Rectangle getPaintable() {
		return this.paintable;
	}

	public void paint(SwtGraphics g) {
		if (!this.visible  || this.paintable == null) {
			return;
		}

		org.eclipse.swt.graphics.Rectangle clip = g.getClipping();
		
		g.setClipping(paintable.x, paintable.y, paintable.width, paintable.height);
		if (this.type == TYPE_COMMAND) {
			int xoffset = 0;
			Device device = DeviceFactory.getDevice();
			SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) device.getDeviceDisplay();
			if (this.pressed) {
				g.setForeground(g.getColor(new RGBA(deviceDisplay
						.getForegroundColor().getRed(), deviceDisplay
						.getForegroundColor().getGreen(), deviceDisplay
						.getForegroundColor().getBlue(), 255)));
			} else {
				g.setBackground(g.getColor(new RGBA(deviceDisplay
						.getBackgroundColor().getRed(), deviceDisplay
						.getBackgroundColor().getGreen(), deviceDisplay
						.getBackgroundColor().getBlue(), 255)));
			}
			g.fillRectangle(paintable.x, paintable.y, paintable.width,
					this.paintable.height);
			synchronized (this) {
				if (this.command != null) {
					if (this.font != null) {
						SwtFontManager fontManager = (SwtFontManager) device.getFontManager();
						SwtFont buttonFont = (SwtFont) fontManager.getFont(this.font);
						g.setFont(buttonFont.getFont());
					}
					if (this.alignment == SwtSoftButton.RIGHT) {
						xoffset = paintable.width
								- g.stringWidth(this.command.getLabel());
					}
					if (this.pressed) {
						g.setBackground(g.getColor(new RGBA(deviceDisplay
								.getBackgroundColor().getRed(), deviceDisplay
								.getBackgroundColor().getGreen(), deviceDisplay
								.getBackgroundColor().getBlue(), 255)));
					} else {
						g.setForeground(g.getColor(new RGBA(deviceDisplay
								.getForegroundColor().getRed(), deviceDisplay
								.getForegroundColor().getGreen(), deviceDisplay
								.getForegroundColor().getBlue(), 255)));
					}
					g.drawString(command.getLabel(), paintable.x + xoffset,
							paintable.y
									+ (paintable.height - g.getFontMetrics()
											.getHeight()), true);
				}
			}
		} else if (this.type == TYPE_ICON) {
                        //SwtImmutableImage
			if (this.pressed) {
				g.drawImage((org.eclipse.swt.graphics.Image) this.pressedImage.getImage(), paintable.x, paintable.y);
			} else {
				g.drawImage((org.eclipse.swt.graphics.Image) this.normalImage.getImage(), paintable.x, paintable.y);
			}
		}
		
		g.setClipping(clip);
	}

	public boolean preferredCommandType(Command cmd) {
		for (Enumeration ct = this.commandTypes.elements(); ct.hasMoreElements();) {
			if (cmd.getCommandType() == ((Integer) ct.nextElement()).intValue()) {
				return true;
			}
		}
		return false;
	}

	public void addCommandType(int commandType) {
		this.commandTypes.addElement(new Integer(commandType));
	}

}
