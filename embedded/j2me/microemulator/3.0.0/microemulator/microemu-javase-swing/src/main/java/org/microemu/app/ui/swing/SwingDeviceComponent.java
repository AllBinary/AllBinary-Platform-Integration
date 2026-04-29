/*
 *  MicroEmulator
 *  Copyright (C) 2001,2002 Bartek Teodorczyk <barteo@barteo.net>
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
 *  @version $Id: SwingDeviceComponent.java 2388 2010-06-10 07:59:51Z barteo@gmail.com $ 
 */

package org.microemu.app.ui.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextHitInfo;
import java.awt.im.InputMethodRequests;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletBridge;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.j2se.J2SEButton;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEImmutableImage;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.device.j2se.J2SEMutableImage;
import org.microemu.log.Logger;

public class SwingDeviceComponent extends JPanel implements KeyListener, InputMethodListener, InputMethodRequests {

	private static final long serialVersionUID = 1L;

	SwingDisplayComponent dc;

	J2SEButton prevOverButton;

	J2SEButton overButton;

	J2SEButton pressedButton;

	private boolean mouseButtonDown = false;

	Image offi;

	Graphics offg;

	private boolean showMouseCoordinates = false;

	private int pressedX;

	private int pressedY;

	private static class MouseRepeatedTimerTask extends TimerTask {

		private static final int DELAY = 100;

		Timer timer;

		Component source;

		J2SEButton button;

		J2SEInputMethod inputMethod;

		static MouseRepeatedTimerTask task;

		static void schedule(Component source, J2SEButton button, J2SEInputMethod inputMethod) {
			if (MouseRepeatedTimerTask.task != null) {
				MouseRepeatedTimerTask.task.cancel();
			}
			MouseRepeatedTimerTask.task = new MouseRepeatedTimerTask();
			MouseRepeatedTimerTask.task.source = source;
			MouseRepeatedTimerTask.task.button = button;
			MouseRepeatedTimerTask.task.inputMethod = inputMethod;
			MouseRepeatedTimerTask.task.timer = new Timer();
			MouseRepeatedTimerTask.task.timer.scheduleAtFixedRate(task, 5 * DELAY, DELAY);
		}

		static void stop() {
			if (MouseRepeatedTimerTask.task != null) {
				MouseRepeatedTimerTask.task.inputMethod = null;
				if (MouseRepeatedTimerTask.task.timer != null) {
					MouseRepeatedTimerTask.task.timer.cancel();
				}
				MouseRepeatedTimerTask.task.cancel();
				MouseRepeatedTimerTask.task = null;
			}
		}

		public static void mouseReleased() {
			if ((MouseRepeatedTimerTask.task != null) && (MouseRepeatedTimerTask.task.inputMethod != null)) {
				MouseRepeatedTimerTask.task.inputMethod.buttonReleased(task.button, '\0', -1);
				MouseRepeatedTimerTask.stop();
			}

		}

		public void run() {
			if (this.inputMethod != null) {
				this.inputMethod.buttonPressed(this.button, '\0', -1);
			}
		}

	}

        
        /*
        //TWB - not needed for single apps
	private MouseAdapter mouseListener = new MouseAdapter() {

		public void mousePressed(MouseEvent e) {
			requestFocus();
			mouseButtonDown = true;
			pressedX = e.getX();
			pressedY = e.getY();

			MouseRepeatedTimerTask.stop();
			if (MIDletBridge.getCurrentMIDlet() == null) {
				return;
			}

			Device device = DeviceFactory.getDevice();
			J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
			// if the displayable is in full screen mode, we should not
			// invoke any associated commands, but send the raw key codes
			// instead
			boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();

			pressedButton = J2SEDeviceButtonsHelper.getSkinButton(e);
			if (pressedButton != null) {
				if (pressedButton instanceof SoftButton && !fullScreenMode) {
					Command cmd = ((SoftButton) pressedButton).getCommand();
					if (cmd != null) {
						MIDletAccess ma = MIDletBridge.getMIDletAccess();
						if (ma == null) {
							return;
						}
						DisplayAccess da = ma.getDisplayAccess();
						if (da == null) {
							return;
						}
						if (cmd.equals(CommandManager.CMD_MENU)) {
							CommandManager.getInstance().commandAction(cmd);
						} else {
							da.commandAction(cmd, da.getCurrent());
						}
					}
				} else {
					inputMethod.buttonPressed(pressedButton, '\0');
					MouseRepeatedTimerTask.schedule(SwingDeviceComponent.this, pressedButton, inputMethod);
				}
				// optimize for some video cards.
				repaint(pressedButton.getShape().getBounds());
			}
		}

		public void mouseReleased(MouseEvent e) {
			mouseButtonDown = false;
			MouseRepeatedTimerTask.stop();

			if (pressedButton == null) {
				return;
			}

			if (MIDletBridge.getCurrentMIDlet() == null) {
				return;
			}

			Device device = DeviceFactory.getDevice();
			J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
			J2SEButton prevOverButton = J2SEDeviceButtonsHelper.getSkinButton(e);
			if (prevOverButton != null) {
				inputMethod.buttonReleased(prevOverButton, '\0');
			}
			pressedButton = null;
			// optimize for some video cards.
			if (prevOverButton != null) {
				repaint(prevOverButton.getShape().getBounds());
			} else {
				repaint();
			}
		}

	};

	private MouseMotionListener mouseMotionListener = new MouseMotionListener() {

		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}

		public void mouseMoved(MouseEvent e) {
			if (showMouseCoordinates) {
				StringBuffer buf = new StringBuffer();
				if (mouseButtonDown) {
					int width = e.getX() - pressedX;
					int height = e.getY() - pressedY;
					buf.append(pressedX).append(",").append(pressedY).append(" ").append(width).append("x").append(
							height);
				} else {
					buf.append(e.getX()).append(",").append(e.getY());
				}
				Common.setStatusBar(buf.toString());
			}

			if (mouseButtonDown && pressedButton == null) {
				return;
			}

			prevOverButton = overButton;
			overButton = J2SEDeviceButtonsHelper.getSkinButton(e);
			if (overButton != prevOverButton) {
				// optimize for some video cards.
				if (prevOverButton != null) {
					MouseRepeatedTimerTask.mouseReleased();
					pressedButton = null;
					repaint(prevOverButton.getShape().getBounds());
				}
				if (overButton != null) {
					repaint(overButton.getShape().getBounds());
				}
			} else if (overButton == null) {
				MouseRepeatedTimerTask.mouseReleased();
				pressedButton = null;
				if (prevOverButton != null) {
					repaint(prevOverButton.getShape().getBounds());
				}
			}
		}

	};
        */
        
	public SwingDeviceComponent() {
		dc = new SwingDisplayComponent(this);
		setLayout(new XYLayout());

                //TWB - not needed for non emulator
		//addMouseListener(mouseListener);
		//addMouseMotionListener(mouseMotionListener);
		
 		//Input methods support begin
 		enableInputMethods(true);
 		addInputMethodListener(this);
 		//End
	}

	public DisplayComponent getDisplayComponent() {
		return this.dc;
	}

	public void init() {
		this.dc.init();

		remove(this.dc);

		Rectangle r = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getDisplayRectangle();
		add(this.dc, new XYConstraints(r.x, r.y, -1, -1));

		revalidate();
	}

	private void repaint(Rectangle r) {
		this.repaint(r.x, r.y, r.width, r.height);
	}

	public void switchShowMouseCoordinates() {
		// TODO skin editing mode.
		// showMouseCoordinates = !showMouseCoordinates;
		this.dc.switchShowMouseCoordinates();
	}
	
 	//Input method support begin
	
 	private static final AttributedCharacterIterator EMPTY_TEXT = new AttributedString("").getIterator();
 	
 	public void caretPositionChanged(InputMethodEvent event) {
 		this.repaint();
 	}
 	
 	public void inputMethodTextChanged(InputMethodEvent event) {
 		StringBuffer committedText = new StringBuffer();
 		AttributedCharacterIterator text = event.getText();
 		Device device = DeviceFactory.getDevice();
 		J2SEInputMethod inputMethod = (J2SEInputMethod)device.getInputMethod();
 		if (text != null) {
 			int toCopy = event.getCommittedCharacterCount();
 			char c = text.first();
 			while (toCopy-- > 0) {
 				committedText.append(c);
 				c = text.next();
 			}
 			if (committedText.length() > 0) {
 				inputMethod.clipboardPaste(committedText.toString());
 			}
 		}
 		this.repaint();
 	}
 	
 	public InputMethodRequests getInputMethodRequests() {
 		return this;
 	}
 	
 	public int getCommittedTextLength() {
 		return 0;
 	}
 	
 	public int getInsertPositionOffset() {
 		return this.getCommittedTextLength();
 	}
 	
 	public AttributedCharacterIterator getCommittedText(int beginIndex, int endIndex, AttributedCharacterIterator.Attribute[] attributes) {
 		return null;
 	}
 	
 	public java.awt.Rectangle getTextLocation(TextHitInfo offset) {
 		return null;
 	}
 	
 	public TextHitInfo getLocationOffset(int x, int y) {
 		return null;
 	}
 	
 	public AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] attributes) {
 		return SwingDeviceComponent.EMPTY_TEXT;
 	}
 	
 	public AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] attributes) {
 		return null;
 	}
 	
 	//Input method support end

	public void keyTyped(KeyEvent ev) {
		if (MIDletBridge.getCurrentMIDlet() == null) {
			return;
		}

		J2SEInputMethod inputMethod = ((J2SEInputMethod) DeviceFactory.getDevice().getInputMethod());
		J2SEButton button = inputMethod.getButton(ev);
		if (button != null) {
			inputMethod.buttonTyped(button);
		}
	}

        private long lastTime = 0;
        
	public void keyPressed(KeyEvent ev) {
		if (MIDletBridge.getCurrentMIDlet() == null) {
			return;
		}

                final int keyCode1 = ev.getKeyCode();
                //System.out.println(new StringBuilder().append("TWB keyPressed - KeyCode: ").append(Integer.toHexString((int) keyCode1)).append(" modifier: ").append(ev.getModifiers()).toString());
                //System.out.println("KeyCode1: " + KeyEvent.VK_ENTER + " modifier1: " + KeyEvent.VK_ALT);
                //System.out.println("KeyCode: " + KeyEvent.VK_ENTER + " modifier: " + (ev.getModifiers() & KeyEvent.ALT_MASK));

                //TWB - Added the ability to toggle fullscreen
                long currentTime = System.currentTimeMillis();

                if(keyCode1 == KeyEvent.VK_ENTER && (ev.getModifiers() & KeyEvent.ALT_MASK) != 0 && 
                        //TWB - This timer should be replaced with an event
                        currentTime - this.lastTime > 1200)
                {
                    this.lastTime = currentTime;
                    
                    final Command TOGGLE_FULLSCREEN  = new Command("Toggle FullScreen", Command.SCREEN, 1);
                    //GameCommandsFactory gameCommandsFactory
                      //      = GameCommandsFactory.getInstance();

                    DisplayAccess displayAccess = 
                            MIDletBridge.getMIDletAccess().getDisplayAccess();

                    displayAccess.commandAction(
                            TOGGLE_FULLSCREEN, displayAccess.getCurrent());
                }
                
		Device device = DeviceFactory.getDevice();
		J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();

		if (keyCode1 == KeyEvent.VK_V && (ev.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable transferable = clipboard.getContents(null);
			if (transferable != null) {
				try {
					Object data = transferable.getTransferData(DataFlavor.stringFlavor);
					if (data instanceof String) {
						inputMethod.clipboardPaste((String) data);
					}
				} catch (UnsupportedFlavorException ex) {
					Logger.error(ex);
				} catch (IOException ex) {
					Logger.error(ex);
				}
			}
			return;
		}

		switch (keyCode1) {
		case KeyEvent.VK_ALT:
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_SHIFT:
			return;
		case 0:
			// Don't know what is the case was intended for but this may be
			// national keyboard letter, so let it work
			if (ev.getKeyChar() == '\0') {
				return;
			}
		}

		char keyChar = '\0';
                //TWB - I want to pass lower key input specifically Enter and Escape
                //ev.getKeyChar() >= 32 && 
		if (ev.getKeyChar() > 0 && ev.getKeyChar() != 65535) {
			keyChar = ev.getKeyChar();
		}
		J2SEButton button = inputMethod.getButton(ev);
		if (button != null) {
			this.pressedButton = button;
			// numeric keypad functions as hot keys for buttons only
			if ((keyCode1 >= KeyEvent.VK_NUMPAD0) && (keyCode1 <= KeyEvent.VK_NUMPAD9)) {
				keyChar = '\0';
			}
			// soft buttons
			if ((keyCode1 >= KeyEvent.VK_F1) && (keyCode1 <= KeyEvent.VK_F12)) {
				keyChar = '\0';
			}
			org.microemu.device.impl.Shape shape = button.getShape();
			if (shape != null) {
				this.repaint(shape.getBounds());
			}
		} else {
			// Logger.debug0x("no button for KeyCode", keyCode1);
		}
                
		inputMethod.buttonPressed(button, keyChar, keyCode1);
	}

	public void keyReleased(KeyEvent ev) {
            
            //System.out.println("keyReleased - KeyCode: " + ev.getKeyCode() + " modifier: " + ev.getModifiers());
            
		if (MIDletBridge.getCurrentMIDlet() == null) {
			return;
		}

                final int keyCode1 = ev.getKeyCode();
		switch (keyCode1) {
		case KeyEvent.VK_ALT:
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_SHIFT:
                    System.out.println("return 0");
			return;
		case 0:
			// Don't know what is the case was intended for but this may be
			// national keyboard letter, so let it work
			if (ev.getKeyChar() == '\0') {
                            System.out.println("return 1");
				return;
			}
		}

		Device device = DeviceFactory.getDevice();
		J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();

		char keyChar = '\0';
		if (ev.getKeyChar() >= 32 && ev.getKeyChar() != 65535) {
			keyChar = ev.getKeyChar();
		}
		// numeric keypad functions as hot keys for buttons only
		if ((keyCode1 >= KeyEvent.VK_NUMPAD0) && (keyCode1 <= KeyEvent.VK_NUMPAD9)) {
			keyChar = '\0';
		}
		// soft buttons
		if ((keyCode1 >= KeyEvent.VK_F1) && (keyCode1 <= KeyEvent.VK_F12)) {
			keyChar = '\0';
		}
		// Logger.debug0x("keyReleased [" + keyChar + "]", keyChar);
		inputMethod.buttonReleased(inputMethod.getButton(ev), keyChar, keyCode1);

		this.prevOverButton = this.pressedButton;
		this.pressedButton = null;
		if (this.prevOverButton != null) {
			org.microemu.device.impl.Shape shape = this.prevOverButton.getShape();
			if (shape != null) {
				this.repaint(shape.getBounds());
			}
		}
	}

        /*
	public MouseListener getDefaultMouseListener() {
		return mouseListener;
	}

	public MouseMotionListener getDefaultMouseMotionListener() {
		return mouseMotionListener;
	}
        */

	protected void paintComponent(Graphics g) {
		if (this.offg == null || this.offi.getWidth(null) != getSize().width || this.offi.getHeight(null) != getSize().height) {
			this.offi = (java.awt.Image) new J2SEMutableImage(getSize().width, getSize().height, false, 0x000000).getImage();
			this.offg = this.offi.getGraphics();
		}

		Dimension size = getSize();
		this.offg.setColor(UIManager.getColor("text"));
		try {
			this.offg.fillRect(0, 0, size.width, size.height);
		} catch (NullPointerException ex) {
			// Fix for NPE in sun.java2d.pipe.SpanShapeRenderer.renderRect(..) on Mac platform
		}
		Device device = DeviceFactory.getDevice();
		if (device == null) {
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			return;
		}
		if (((DeviceDisplayImpl) device.getDeviceDisplay()).isResizable()) {
			return;
		}

		this.offg.drawImage((java.awt.Image) device.getNormalImage().getImage(), 0, 0, this);

		if (this.prevOverButton != null) {
			org.microemu.device.impl.Shape shape = this.prevOverButton.getShape();
			if (shape != null) {
				this.drawImageInShape(this.offg, (java.awt.Image) device.getNormalImage().getImage(), shape);
			}
			this.prevOverButton = null;
		}
		if (this.overButton != null) {
			org.microemu.device.impl.Shape shape = this.overButton.getShape();
			if (shape != null) {
				this.drawImageInShape(this.offg, (java.awt.Image) device.getOverImage().getImage(), shape);
			}
		}
		if (this.pressedButton != null) {
			org.microemu.device.impl.Shape shape = this.pressedButton.getShape();
			if (shape != null) {
				this.drawImageInShape(this.offg, (java.awt.Image) device.getPressedImage().getImage(), shape);
			}
		}

		g.drawImage(this.offi, 0, 0, null);
	}

	private void drawImageInShape(Graphics g, Image image, org.microemu.device.impl.Shape shape) {
		Shape clipSave = g.getClip();
		if (shape instanceof org.microemu.device.impl.Polygon) {
			Polygon poly = new Polygon(((org.microemu.device.impl.Polygon) shape).xpoints,
					((org.microemu.device.impl.Polygon) shape).ypoints,
					((org.microemu.device.impl.Polygon) shape).npoints);
			g.setClip(poly);
		}
		org.microemu.device.impl.Rectangle r = shape.getBounds();
		g.drawImage(image, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width, r.y + r.height, null);
		g.setClip(clipSave);
	}

	public Dimension getPreferredSize() {
		Device device = DeviceFactory.getDevice();
		if (device == null) {
			return new Dimension(0, 0);
		}

		DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
		if (deviceDisplay.isResizable()) {
			return new Dimension(deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
		} else {
			javax.microedition.lcdui.Image img = device.getNormalImage();
			return new Dimension(img.getWidth(), img.getHeight());
		}
	}

}
