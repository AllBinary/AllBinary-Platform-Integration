/*
 *  MicroEmulator
 *  Copyright (C) 2001-2003 Bartek Teodorczyk <barteo@barteo.net>
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import javax.microedition.lcdui.Display.DisplayAccessor;
import org.allbinary.game.displayable.canvas.GameRunnable;
import org.allbinary.game.displayable.canvas.NullWaitGameRunnable;
import org.allbinary.image.opengles.SwtImageUtil;
import org.allbinary.logic.communication.log.PreLogUtil;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.logic.string.StringUtil;
import org.allbinary.string.CommonStrings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.RGBA;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.swt.SwtButton;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtImmutableImage;
import org.microemu.device.swt.SwtInputMethod;

public class SwtDeviceComponent extends Canvas {
	private static SwtDeviceComponent instance;

	private static HashMap colors = new HashMap();

	private SwtDisplayComponent dc;

	private Image fBuffer = null;

	private SwtButton prevOverButton;

	private SwtButton overButton;

	private SwtButton pressedButton;

	private SoftButton initialPressedSoftButton;

	private boolean mousePressed;

//        final KeyListener emulatorButtonKeyListener = new KeyListener() {
//		public void keyPressed(KeyEvent ev) {
//			if (MIDletBridge.getCurrentMIDlet() == null) {
//				return;
//			}
//
//			final Device device = DeviceFactory.getDevice();
//
//			for (Iterator it = device.getButtons().iterator(); it.hasNext();) {
//				SwtButton button = (SwtButton) it.next();
//				if (ev.keyCode == button.getKeyboardKey()) {
//					ev.keyCode = button.getKeyCode();
//					break;
//				}
//			}
//                        
//		}
//
//		public void keyReleased(KeyEvent ev) {
//			if (MIDletBridge.getCurrentMIDlet() == null) {
//				return;
//			}
//
//			final Device device = DeviceFactory.getDevice();
//
//			for (Iterator it = device.getButtons().iterator(); it.hasNext();) {
//				SwtButton button = (SwtButton) it.next();
//				if (ev.keyCode == button.getKeyboardKey()) {
//					ev.keyCode = button.getKeyCode();
//					break;
//				}
//			}
//
//		}            
//        };
//            
//        final KeyListener emulatorInputKeyListener = new KeyListener() {
//		public void keyPressed(KeyEvent ev) {
//
//                    final Device device = DeviceFactory.getDevice();
//                    
//			pressedButton = ((SwtInputMethod) device.getInputMethod()).getButton(ev);
//			if (pressedButton != null) {
//				org.microemu.device.impl.Shape shape = pressedButton.getShape();
//				if (shape != null) {
//					Rectangle r = shape.getBounds();
//					redraw(r.x, r.y, r.width, r.height, true);
//				}
//			} else {
//				redraw();
//			}
//                        
//		}
//
//		public void keyReleased(KeyEvent ev) {
//
//			prevOverButton = pressedButton;
//			pressedButton = null;
//			if (prevOverButton != null) {
//				org.microemu.device.impl.Shape shape = prevOverButton.getShape();
//				if (shape != null) {
//					Rectangle r = shape.getBounds();
//					redraw(r.x, r.y, r.width, r.height, true);
//				}
//			} else {
//				redraw();
//			}
//		}            
//        };
        
	final KeyListener keyListener = new KeyListener() {
		public void keyPressed(KeyEvent ev) {
			if (MIDletBridge.getCurrentMIDlet() == null) {
				return;
			}

			final Device device = DeviceFactory.getDevice();

                        //emulatorButtonKeyListener.keyPressed(ev);

                        //System.out.println("Swt::keyPressed " + ev.keyCode);
			((SwtInputMethod) device.getInputMethod()).keyPressed(ev);

                        //emulatorInputKeyListener.keyPressed(ev);

                    //65536 0x010000
                    //if (ev.keyCode == SWT.CR && ev.stateMask == 0x010000) {
                    if (ev.keyCode == SWT.F11) {
                        try {
                            
                            //System.out.println("ev.stateMask: " + ev.stateMask);
                            final Shell shell = SwtDeviceComponent.this.getShell();
                            shell.setFullScreen(!shell.getFullScreen());
                            final boolean isFullScreen = shell.getFullScreen();
                            shell.setMaximized(isFullScreen);

                            notifyListeners(SWT.Resize, new Event());
                        
                        } catch(Exception e) {
                            final CommonStrings commonStrings = CommonStrings.getInstance();
                            PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
                        }
                    }
                        
		}

		public void keyReleased(KeyEvent ev) {
			if (MIDletBridge.getCurrentMIDlet() == null) {
				return;
			}

			final Device device = DeviceFactory.getDevice();

                        //emulatorButtonKeyListener.keyReleased(ev);

                        //System.out.println("Swt::keyReleased " + ev.keyCode);
			((SwtInputMethod) device.getInputMethod()).keyReleased(ev);

                        //emulatorInputKeyListener.keyReleased(ev);
		}
	};

	final MouseAdapter mouseListener = new MouseAdapter() {
		public void mouseDown(MouseEvent e) {
			if (MIDletBridge.getCurrentMIDlet() == null) {
				return;
			}

			final Device device = DeviceFactory.getDevice();
			final org.microemu.device.impl.Rectangle rect = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
			final SwtInputMethod inputMethod = (SwtInputMethod) device.getInputMethod();
			// if the displayable is in full screen mode, we should not
			// invoke any associated commands, but send the raw key codes
			// instead
			final boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();

//			if (rect.x <= e.x && (rect.x + rect.width) > e.x && rect.y <= e.y && (rect.y + rect.height) > e.y) {
//				if (device.hasPointerEvents()) {
//					if (!fullScreenMode) {
//						Iterator it = device.getSoftButtons().iterator();
//						while (it.hasNext()) {
//							SoftButton button = (SoftButton) it.next();
//							if (button.isVisible()) {
//								org.microemu.device.impl.Rectangle pb = button.getPaintable();
//								if (pb != null && pb.contains(e.x - rect.x, e.y - rect.y)) {
//									initialPressedSoftButton = button;
//									button.setPressed(true);
//									dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
//									break;
//								}
//							}
//						}
//					}
					if (fullScreenMode) {
						inputMethod.pointerPressed(e.x - rect.x, e.y - rect.y);
					} else {
						org.microemu.device.impl.Rectangle pb = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayPaintable();
						inputMethod.pointerPressed(e.x - rect.x - pb.x, e.y - rect.y - pb.y);
					}
//				}
//			} else {
//				pressedButton = getButton(e.x, e.y);
//				if (pressedButton != null) {
//					if (pressedButton instanceof SoftButton && !fullScreenMode) {
//						Command cmd = ((SoftButton) pressedButton).getCommand();
//						if (cmd != null) {
//							MIDletAccess ma = MIDletBridge.getMIDletAccess();
//							if (ma == null) {
//								return;
//							}
//							DisplayAccess da = ma.getDisplayAccess();
//							if (da == null) {
//								return;
//							}
//							if (cmd.equals(CommandManager.CMD_MENU)) {
//								CommandManager.getInstance().commandAction(cmd);
//							} else {
//								da.commandAction(cmd, da.getCurrent());
//							}
//						}
//					} else {
//						Event event = new Event();
//						event.widget = e.widget;
//						KeyEvent ev = new KeyEvent(event);
//						ev.keyCode = pressedButton.getKeyCode();
//						inputMethod.mousePressed(ev);
//					}
//					// optimize for some video cards.
//					Rectangle r = pressedButton.getShape().getBounds();
//					redraw(r.x, r.y, r.width, r.height, true);
//				}
//			}

			mousePressed = true;
		}

		public void mouseUp(MouseEvent e) {
			if (MIDletBridge.getCurrentMIDlet() == null) {
				return;
			}

			final Device device = DeviceFactory.getDevice();
			final org.microemu.device.impl.Rectangle rect = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
			final SwtInputMethod inputMethod = (SwtInputMethod) device.getInputMethod();
			final boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
//			if (rect.x <= e.x && (rect.x + rect.width) > e.x && rect.y <= e.y && (rect.y + rect.height) > e.y) {
//				if (device.hasPointerEvents()) {
//					if (!fullScreenMode) {
//						if (initialPressedSoftButton != null && initialPressedSoftButton.isPressed()) {
//							initialPressedSoftButton.setPressed(false);
//							org.microemu.device.impl.Rectangle pb = initialPressedSoftButton.getPaintable();
//							if (pb != null) {
//								dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
//								if (pb.contains(e.x - rect.x, e.y - rect.y)) {
//									Command cmd = initialPressedSoftButton.getCommand();
//									if (cmd != null) {
//										MIDletAccess ma = MIDletBridge.getMIDletAccess();
//										if (ma == null) {
//											return;
//										}
//										DisplayAccess da = ma.getDisplayAccess();
//										if (da == null) {
//											return;
//										}
//										if (cmd.equals(CommandManager.CMD_MENU)) {
//											CommandManager.getInstance().commandAction(cmd);
//										} else {
//											da.commandAction(cmd, da.getCurrent());
//										}
//									}
//								}
//							}
//						}
//						initialPressedSoftButton = null;
//					}
					if (fullScreenMode) {
						inputMethod.pointerReleased(e.x - rect.x, e.y - rect.y);
					} else {
						final org.microemu.device.impl.Rectangle pb = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayPaintable();
						inputMethod.pointerReleased(e.x - rect.x - pb.x, e.y - rect.y - pb.y);
					}
//				}
//			} else {
//				SwtButton prevOverButton = getButton(e.x, e.y);
//				if (prevOverButton != null) {
//					inputMethod.mouseReleased(prevOverButton.getKeyCode());
//				}
//				pressedButton = null;
//				// optimize for some video cards.
//				if (prevOverButton != null) {
//					Rectangle r = prevOverButton.getShape().getBounds();
//					redraw(r.x, r.y, r.width, r.height, true);
//				} else {
//					redraw();
//				}
//			}

			mousePressed = false;
		}
                
            @Override
            public void mouseDoubleClick(MouseEvent event)
            {
//                final Shell shell = SwtDeviceComponent.this.getShell();
//
//                System.out.println("Full screen: " + shell.getFullScreen());
//                
//                shell.setFullScreen(! shell.getFullScreen());
//                shell.setMaximized(shell.getFullScreen());
//                
//                System.out.println("Full screen: " + shell.getFullScreen());                
            }
                
	};

	final MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		public void mouseMove(MouseEvent e) {
//			prevOverButton = overButton;
//			overButton = getButton(e.x, e.y);
//			if (overButton != prevOverButton) {
//				// optimize for some video cards.
//				if (prevOverButton != null) {
//					Rectangle r = prevOverButton.getShape().getBounds();
//					redraw(r.x, r.y, r.width, r.height, true);
//				}
//				if (overButton != null) {
//					Rectangle r = overButton.getShape().getBounds();
//					redraw(r.x, r.y, r.width, r.height, true);
//				}
//			}

			if (mousePressed) {
				Device device = DeviceFactory.getDevice();
				org.microemu.device.impl.Rectangle rect = ((SwtDeviceDisplay) device.getDeviceDisplay())
						.getDisplayRectangle();
				SwtInputMethod inputMethod = (SwtInputMethod) device.getInputMethod();
				boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
//				if (rect.x <= e.x && (rect.x + rect.width) > e.x && rect.y <= e.y && (rect.y + rect.height) > e.y) {
					if (device.hasPointerMotionEvents()) {
//						if (!fullScreenMode) {
//							if (initialPressedSoftButton != null) {
//								org.microemu.device.impl.Rectangle pb = initialPressedSoftButton.getPaintable();
//								if (pb != null) {
//									if (pb.contains(e.x - rect.x, e.y - rect.y)) {
//										if (!initialPressedSoftButton.isPressed()) {
//											initialPressedSoftButton.setPressed(true);
//											dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
//										}
//									} else {
//										if (initialPressedSoftButton.isPressed()) {
//											initialPressedSoftButton.setPressed(false);
//											dc.repaintRequest(pb.x, pb.y, pb.width, pb.height);
//										}
//									}
//								}
//							}
//						}
						if (fullScreenMode) {
							inputMethod.pointerDragged(e.x - rect.x, e.y - rect.y);
						} else {
							org.microemu.device.impl.Rectangle pb = ((SwtDeviceDisplay) device.getDeviceDisplay())
									.getDisplayPaintable();
							inputMethod.pointerDragged(e.x - rect.x - pb.x, e.y - rect.y - pb.y);
						}
//					}
				}
			}
		}
	};

	public SwtDeviceComponent(Composite parent) {
		super(parent, SWT.NO_BACKGROUND);
                
		instance = this;
		mousePressed = false;

		dc = new SwtDisplayComponent(this);

		this.initialPressedSoftButton = null;

		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		addMouseMoveListener(mouseMoveListener);
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				SwtDeviceComponent.this.paintControl(e);
			}
		});
                
                final Shell shell = this.getShell();
                
                shell.addListener(SWT.Close, new Listener() {
                    public void handleEvent (Event event) {
                        try {                            
                            System.out.println("handleEvent - SWT.Close");
                            
                            final MIDletAccess ma = MIDletBridge.getMIDletAccess();
                            if (ma == null) {
                                System.out.println("SWT.Close:MIDletAccess was null");
                                return;
                            }

                            final DisplayAccess da = ma.getDisplayAccess();
                            if (da == null) {
                                System.out.println("SWT.Close:DisplayAccess was null");
                                return;
                            }
                            
                            //ma.midlet.commandAction(EXIT_WITHOUT_PROGRESS_COMMAND);
                            //ma.destroyApp(true);
                            //shell.dispose();
                            System.exit(0);

                        } catch(Exception e) {
                            final CommonStrings commonStrings = CommonStrings.getInstance();
                            PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
                        }
                    }
                });

                shell.addListener(SWT.Iconify, new Listener() {
                    public void handleEvent (Event event) {
                        try {
                            final org.eclipse.swt.graphics.Rectangle swtRectangle = shell.getClientArea();
                            //final Rectangle rectangle = new Rectangle(0, 0, swtRectangle.width + 2, swtRectangle.height + 2);
                            final Rectangle rectangle = new Rectangle(swtRectangle.x, swtRectangle.y, swtRectangle.width, swtRectangle.height);
                            
                            System.out.println("handleEvent - SWT.Iconify: " + rectangle);
                            
                            final MIDletAccess ma = MIDletBridge.getMIDletAccess();
                            if (ma == null) {
                                System.out.println("SWT.Iconify:MIDletAccess was null");
                                return;
                            }

                            final DisplayAccess da = ma.getDisplayAccess();
                            if (da == null) {
                                System.out.println("SWT.Iconify:DisplayAccess was null");
                                return;
                            }
                            
                            ma.pauseApp();

                        } catch(Exception e) {
                            final CommonStrings commonStrings = CommonStrings.getInstance();
                            PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
                        }
                    }
                });

                shell.addListener(SWT.Deiconify, new Listener() {
                    public void handleEvent (Event event) {
                        try {
                            final org.eclipse.swt.graphics.Rectangle swtRectangle = shell.getClientArea();
                            //final Rectangle rectangle = new Rectangle(0, 0, shell.getClientArea().width + 2, shell.getClientArea().height + 2);
                            final Rectangle rectangle = new Rectangle(0, 0, swtRectangle.width, swtRectangle.height);
                            
                            System.out.println("handleEvent - SWT.Deiconify: " + rectangle);
                            
                            final MIDletAccess ma = MIDletBridge.getMIDletAccess();
                            if (ma == null) {
                                System.out.println("SWT.Deiconify:MIDletAccess was null");
                                return;
                            }

                            final DisplayAccess da = ma.getDisplayAccess();
                            if (da == null) {
                                System.out.println("SWT.Deiconify:DisplayAccess was null");
                                return;
                            }
                            
                            ma.startApp();

                        } catch(Exception e) {
                            final CommonStrings commonStrings = CommonStrings.getInstance();
                            PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
                        }
                    }
                });
                
                shell.addListener(SWT.Resize, new Listener() {
                    public void handleEvent (Event event) {
                        
                        SwtResizeMidlet.getInstance().process(shell);
                    }
                });

	}

	public DisplayComponent getDisplayComponent() {
		return this.dc;
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		javax.microedition.lcdui.Image tmp = DeviceFactory.getDevice().getNormalImage();

		return new Point(tmp.getWidth(), tmp.getHeight());
	}

	public void paintControl(PaintEvent pe) {
		Point size = getSize();

		if (size.x <= 0 || size.y <= 0)
			return;

		if (this.fBuffer != null) {
			org.eclipse.swt.graphics.Rectangle r = fBuffer.getBounds();
			if (r.width != size.x || r.height != size.y) {
				fBuffer.dispose();
				fBuffer = null;
			}
		}
		if (fBuffer == null) {
			fBuffer = new Image(getDisplay(), size.x, size.y);
		}

		SwtGraphics gc = new SwtGraphics(new GC(fBuffer));
		try {
			Device device = DeviceFactory.getDevice();

                        //SwtImmutableImage
			gc.drawImage((org.eclipse.swt.graphics.Image) device.getNormalImage().getImage(), 0, 0);

			org.microemu.device.impl.Rectangle displayRectangle = ((SwtDeviceDisplay) device.getDeviceDisplay()).getDisplayRectangle();
			gc.translate(displayRectangle.x, displayRectangle.y);
			this.dc.paint(gc);
			gc.translate(-displayRectangle.x, -displayRectangle.y);

			if (this.prevOverButton != null) {
				org.microemu.device.impl.Shape shape = prevOverButton.getShape();
				if (shape != null) {
                                        //SwtImmutableImage
					this.drawImageInShape(gc, (org.eclipse.swt.graphics.Image) device.getNormalImage().getImage(), shape);
				}
				prevOverButton = null;
			}
			if (this.overButton != null) {
				org.microemu.device.impl.Shape shape = overButton.getShape();
				if (shape != null) {
                                        //SwtImmutableImage
					this.drawImageInShape(gc, (org.eclipse.swt.graphics.Image) device.getOverImage().getImage(), shape);
				}
			}
			if (this.pressedButton != null) {
				org.microemu.device.impl.Shape shape = pressedButton.getShape();
				if (shape != null) {
                                        //SwtImmutableImage
					this.drawImageInShape(gc, (org.eclipse.swt.graphics.Image) device.getPressedImage().getImage(), shape);
				}
			}

			org.microemu.device.impl.Rectangle rect;
			if (prevOverButton != null) {
				rect = prevOverButton.getShape().getBounds();
				if (rect != null) {
                                        //SwtImmutableImage
					gc.drawImage((org.eclipse.swt.graphics.Image) DeviceFactory.getDevice().getNormalImage().getImage(), rect.x,
							rect.y, rect.width, rect.height, rect.x, rect.y, rect.width, rect.height);
				}
				prevOverButton = null;
			}
			if (overButton != null) {
				rect = overButton.getShape().getBounds();
				if (rect != null) {
                                        //SwtImmutableImage
					gc.drawImage((org.eclipse.swt.graphics.Image) DeviceFactory.getDevice().getOverImage().getImage(), rect.x,
							rect.y, rect.width, rect.height, rect.x, rect.y, rect.width, rect.height);
				}
			}
			if (pressedButton != null) {
                            if(pressedButton.getShape() != null) {
				rect = pressedButton.getShape().getBounds();
				if (rect != null) {
                                        //SwtImmutableImage
					gc.drawImage((org.eclipse.swt.graphics.Image) DeviceFactory.getDevice().getPressedImage().getImage(), rect.x,
							rect.y, rect.width, rect.height, rect.x, rect.y, rect.width, rect.height);
				}
                            }
			}
		} finally {
			gc.dispose();
		}

		pe.gc.drawImage(fBuffer, 0, 0);
	}

	private void drawImageInShape(SwtGraphics g, Image image, org.microemu.device.impl.Shape shape) {
		org.eclipse.swt.graphics.Rectangle clipSave = g.getClipping();
		if (shape instanceof org.microemu.device.impl.Polygon) {
			// TODO not implemented yet
			// g.setCliping(region);
		}
		org.microemu.device.impl.Rectangle r = shape.getBounds();
		g.drawImage(image, r.x, r.y, r.width, r.height, r.x, r.y, r.width, r.height);
		g.setClipping(clipSave);
	}

	private SwtButton getButton(int x, int y) {
		for (Enumeration e = DeviceFactory.getDevice().getButtons().elements(); e.hasMoreElements();) {
			SwtButton button = (SwtButton) e.nextElement();
			if (button.getShape() != null) {
				try {
					org.microemu.device.impl.Shape tmp = (org.microemu.device.impl.Shape) button.getShape().clone();
					if (tmp.contains(x, y)) {
						return button;
					}
				} catch (CloneNotSupportedException ex) {
                                    final CommonStrings commonStrings = CommonStrings.getInstance();
                                    PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
				}
			}
		}
		return null;
	}

	private class CreateImageRunnable implements Runnable {
		private ImageData data;

		private Image image;

		CreateImageRunnable(ImageData data) {
			this.data = data;
		}

		Image getImage() {
			return this.image;
		}

		public void run() {
//        		if (data.transparentPixel > 0) {
//                            image = new Image(instance.getParent().getDisplay(), data, data.getTransparencyMask());
//			} else {
                            image = new Image(instance.getParent().getDisplay(), data);
//                        }
		}
	}

	public static Image createImage(int width, int height) {
		return new Image(instance.getDisplay(), width, height);
	}

	public static Image createImage(Image image) {
		return new Image(instance.getDisplay(), image, SWT.IMAGE_COPY);	
        }

	public static Image createImage(ImageData data) {
		CreateImageRunnable createImageRunnable = instance.new CreateImageRunnable(data);
		SwtDeviceComponent.instance.getDisplay().syncExec(createImageRunnable);

		return createImageRunnable.getImage();
	}

	public static Image createImage(ImageData data, ImageFilter filter) {
            
            org.eclipse.swt.graphics.RGB[] rgbs = data.getRGBs();
            if (rgbs != null) {
                for (int i = 0; i < rgbs.length; i++) {
                    //rgbs[i] = 
                    filter.filterRGB(0, 0, new RGBA(rgbs[i].red, rgbs[i].green, rgbs[i].blue, 255));
                }
            } else {
                org.eclipse.swt.graphics.RGBA rgb;
                int pixel;
                for (int y = 0; y < data.height; y++) {
                    for (int x = 0; x < data.width; x++) {
                        pixel = data.getPixel(x, y);
                        rgb = new RGBA((pixel >> 16) & 255, (pixel >> 8) & 255, pixel & 255, 255);
                        rgb = filter.filterRGB(x, y, new RGBA(rgb.rgb.red, rgb.rgb.green, rgb.rgb.blue, 255));
                        data.setPixel(x, y, (rgb.rgb.red << 16) + (rgb.rgb.green << 8) + rgb.rgb.blue);
                    }
                }
            }
            
		CreateImageRunnable createImageRunnable = instance.new CreateImageRunnable(data);
		SwtDeviceComponent.instance.getDisplay().syncExec(createImageRunnable);

		return createImageRunnable.getImage();
	}
        
	public static Image createImage(InputStream is) {
		ImageData data = new ImageData(is);

		CreateImageRunnable createImageRunnable = instance.new CreateImageRunnable(data);
		SwtDeviceComponent.instance.getDisplay().syncExec(createImageRunnable);

		return createImageRunnable.getImage();
	}

	public static Image createImage(final InputStream is, final ImageFilter filter) throws IOException {
		try {
			final ImageData data = new ImageData(is);

//                        if(data.height > 130) {
//                            System.out.println(SwtImageUtil.getInstance().toString(data));
//                        }                        
                        
			org.eclipse.swt.graphics.RGB[] rgbs = data.getRGBs();
			if (rgbs != null) {
                            
                            if(data.depth == 32) {
				for (int i = 0; i < rgbs.length; i++) {
					//rgbs[i] = 
                                            filter.filterRGB(0, 0, new RGBA(rgbs[i].red, rgbs[i].green, rgbs[i].blue, data.alphaData[i]));
				}
                            } else if(data.depth == 24) {
                                
				for (int i = 0; i < rgbs.length; i++) {
					//rgbs[i] = 
                                            filter.filterRGB(0, 0, new RGBA(rgbs[i].red, rgbs[i].green, rgbs[i].blue, 255));
				}
                            } else if(data.depth == 8) {
                                
				for (int i = 0; i < rgbs.length; i++) {
					//rgbs[i] = 
                                            filter.filterRGB(0, 0, new RGBA(rgbs[i].red, rgbs[i].green, rgbs[i].blue, 255));
				}
                            } else if(data.depth == 2) {
				for (int i = 0; i < rgbs.length; i++) {
					//rgbs[i] = 
                                            filter.filterRGB(0, 0, new RGBA(rgbs[i].red, rgbs[i].green, rgbs[i].blue, 255));
				}
                            } else {
                                throw new RuntimeException("depth: " + data.depth);
                            }
			} else {
				org.eclipse.swt.graphics.RGBA rgb;
				int pixel;
                                int alpha;
                            if(data.depth == 32) {
				for (int y = 0; y < data.height; y++) {
					for (int x = 0; x < data.width; x++) {
						pixel = data.getPixel(x, y);
						rgb = new RGBA((pixel >> 24) & 255, (pixel >> 16) & 255, (pixel >> 8) & 255, (pixel) & 255);
						rgb = filter.filterRGB(x, y, new RGBA(rgb.rgb.red, rgb.rgb.green, rgb.rgb.blue, data.alphaData[x + (y * data.width)]));
						data.setPixel(x, y, (rgb.rgb.red << 16) + (rgb.rgb.green << 8) + rgb.rgb.blue);
					}
				}
                            } else if(data.depth == 24) {
				for (int y = 0; y < data.height; y++) {
					for (int x = 0; x < data.width; x++) {
						pixel = data.getPixel(x, y);
                                                alpha = data.getAlpha(x, y);
						rgb = new RGBA((pixel >> 16) & 255, (pixel >> 8) & 255, pixel & 255, alpha);
						rgb = filter.filterRGB(x, y, new RGBA(rgb.rgb.red, rgb.rgb.green, rgb.rgb.blue, alpha));
						data.setPixel(x, y, (rgb.rgb.red << 16) + (rgb.rgb.green << 8) + rgb.rgb.blue);
					}
				}
                            } else if(data.depth == 2) {
				for (int y = 0; y < data.height; y++) {
					for (int x = 0; x < data.width; x++) {
						pixel = data.getPixel(x, y);
						rgb = new RGBA((pixel >> 16) & 255, (pixel >> 8) & 255, pixel & 255, 255);
						rgb = filter.filterRGB(x, y, new RGBA(rgb.rgb.red, rgb.rgb.green, rgb.rgb.blue, 255));
						data.setPixel(x, y, (rgb.rgb.red << 16) + (rgb.rgb.green << 8) + rgb.rgb.blue);
					}
				}
                            } else {
                                throw new RuntimeException();
                            }
			}

			CreateImageRunnable createImageRunnable = instance.new CreateImageRunnable(data);
			SwtDeviceComponent.instance.getDisplay().syncExec(createImageRunnable);

			return createImageRunnable.getImage();
		} catch (SWTException ex) {
			throw new IOException(ex.toString());
		}
	}

//	private class CreateColorRunnable implements Runnable {
//		private RGB rgb;
//
//		private Color color;
//
//		CreateColorRunnable(RGB rgb) {
//			this.rgb = rgb;
//		}
//
//		Color getColor() {
//			return color;
//		}
//
//		public void run() {
//			color = new Color(instance.getParent().getDisplay(), rgb);
//		}
//	}

        //TWB - Required SWT 4.5
	private class CreateColorRunnableA implements Runnable {
		private RGBA rgb;

		private Color color;

		CreateColorRunnableA(RGBA rgb) {
			this.rgb = rgb;
		}

		Color getColor() {
			return this.color;
		}

		public void run() {
			color = new Color(instance.getParent().getDisplay(), rgb);
		}
	}
        
//	public static Color getColor(RGB rgb) {
//		Color result = (Color) colors.get(rgb);
//
//		if (result == null) {
//			CreateColorRunnable createColorRunnable = instance.new CreateColorRunnable(rgb);
//			instance.getDisplay().syncExec(createColorRunnable);
//			result = createColorRunnable.getColor();
//			colors.put(rgb, result);
//		}
//
//		return result;
//	}

        //TWB - Required SWT 4.5
	public static Color getColor(RGBA rgb) {
		Color result = (Color) colors.get(rgb);

		if (result == null) {
			CreateColorRunnableA createColorRunnable = instance.new CreateColorRunnableA(rgb);
			SwtDeviceComponent.instance.getDisplay().syncExec(createColorRunnable);
			result = createColorRunnable.getColor();
			SwtDeviceComponent.colors.put(rgb, result);
		}

		return result;
	}
        
	private class GetFontMetricsRunnable implements Runnable {
		private Font font;

		private FontMetrics fontMetrics;

		GetFontMetricsRunnable(Font font) {
			this.font = font;
		}

		FontMetrics getFontMetrics() {
			return this.fontMetrics;
		}

		public void run() {
			SwtGraphics gc = new SwtGraphics(instance.getParent().getDisplay());
			gc.setFont(font);
			fontMetrics = gc.getFontMetrics();
			gc.dispose();
		}
	}

	private class GetFontRunnable implements Runnable {
		private String name;

		private int size;

		private int style;

		private boolean antialiasing;

		private Font font;

		GetFontRunnable(String name, int size, int style, boolean antialiasing) {
			this.name = name;
			this.size = size;
			this.style = style;
		}

		Font getFont() {
			return this.font;
		}

		public void run() {
			SwtGraphics gc = new SwtGraphics(instance.getParent().getDisplay());
			gc.setAntialias(antialiasing);
			gc.setFont(new Font(instance.getParent().getDisplay(), name, size, style));
			font = gc.getFont();
			gc.dispose();
		}
	}

	private class StringWidthRunnable implements Runnable {
		private Font font;

		private String str;

		private int stringWidth;

		StringWidthRunnable(Font font, String str) {
			this.font = font;
			this.str = str;
		}

		int stringWidth() {
			return this.stringWidth;
		}

		public void run() {
			SwtGraphics gc = new SwtGraphics(instance.getParent().getDisplay());
			gc.setFont(font);
			stringWidth = gc.stringWidth(str);
			gc.dispose();
		}
	}

	public static Font getFont(String name, int size, int style, boolean antialiasing) {
		GetFontRunnable getFontRunnable = instance.new GetFontRunnable(name, size, style, antialiasing);

		SwtDeviceComponent.instance.getDisplay().syncExec(getFontRunnable);

		return getFontRunnable.getFont();
	}

	public static FontMetrics getFontMetrics(Font font) {
		GetFontMetricsRunnable getFontMetricsRunnable = instance.new GetFontMetricsRunnable(font);

		SwtDeviceComponent.instance.getDisplay().syncExec(getFontMetricsRunnable);

		return getFontMetricsRunnable.getFontMetrics();
	}

	public static int stringWidth(Font font, String str) {
		StringWidthRunnable stringWidthRunnable = instance.new StringWidthRunnable(font, str);

		SwtDeviceComponent.instance.getDisplay().syncExec(stringWidthRunnable);

		return stringWidthRunnable.stringWidth();
	}

}
