/**
 * MicroEmulator 
 * Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
 * Copyright (C) 2007 Rushabh Doshi <radoshi@cs.stanford.edu> Pelago, Inc
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
 *   Andres Navarro
 *   
 *  @version $Id: Display.java 2496 2011-05-07 11:27:52Z barteo@gmail.com $
 */
package javax.microedition.lcdui;

//import java.util.TimerTask;
import javax.microedition.midlet.MIDlet;
import org.allbinary.thread.DisplayThreadPool;
import org.allbinary.thread.EmuThreadPool;
import org.allbinary.view.EmulatorViewInterface;
import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.DisplayableUI;
import org.microemu.device.ui.ItemUI;

public class Display {

	public static final int LIST_ELEMENT = 1;

	public static final int CHOICE_GROUP_ELEMENT = 2;

	public static final int ALERT = 3;

	public static final int COLOR_BACKGROUND = 0;

	public static final int COLOR_FOREGROUND = 1;

	public static final int COLOR_HIGHLIGHTED_BACKGROUND = 2;

	public static final int COLOR_HIGHLIGHTED_FOREGROUND = 3;

	public static final int COLOR_BORDER = 4;

	public static final int COLOR_HIGHLIGHTED_BORDER = 5;

        private final Displayable DISPLAYABLE = new Canvas();
	private Displayable current = DISPLAYABLE;

	private DisplayAccessor accessor = null;

        //TWB - no event dispatcher needed as the platform under the emulator already has one
	//private EventDispatcher eventDispatcher;

        //private boolean isCanvas = false;
        
//	private final class GaugePaintTask implements Runnable {
//
//		public void run() {
//				if (current instanceof Alert) {
//					Gauge gauge = ((Alert) current).indicator;
//					if (gauge != null && gauge.hasIndefiniteRange() && gauge.getValue() == Gauge.CONTINUOUS_RUNNING) {
//						gauge.updateIndefiniteFrame();
//					}
//				} else if (current instanceof Form) {
//					Item[] items = ((Form) current).items;
//					for (int i = 0; i < items.length; i++) {
//						Item it = items[i];
//						if (it != null && it instanceof Gauge) {
//							Gauge gauge = (Gauge) it;
//
//							if (gauge.hasIndefiniteRange() && gauge.getValue() == Gauge.CONTINUOUS_RUNNING) {
//								gauge.updateIndefiniteFrame();
//							}
//						}
//					}
//				}
//		}
//	}

	/**
	 * @author radoshi
	 * 
	 */
//	private final class TickerPaintTask implements Runnable {
//
//		public void run() {
//				Ticker ticker = current.getTicker();
//				if (ticker != null) {
//					synchronized (ticker) {
//						if (ticker.resetTextPosTo != -1) {
//							ticker.textPos = ticker.resetTextPosTo;
//							ticker.resetTextPosTo = -1;
//						}
//						ticker.textPos -= Ticker.PAINT_MOVE;
//					}
//					repaint(current, 0, 0, current.getWidth(), current.getHeight());
//				}
//		}
//	}

	/**
	 * Wrap a key event as a runnable so it can be thrown into the event
	 * processing queue. Note that this may be a bit buggy, since events are
	 * supposed to propogate to the head of the queue and not get tied behind
	 * other repaints or serial calls in the queue.
	 * 
	 * @author radoshi
	 * 
	 */
        /*
	private final class KeyEvent extends EventDispatcher.Event {

		static final short KEY_PRESSED = 0;

		static final short KEY_RELEASED = 1;

		static final short KEY_REPEATED = 2;

		private short type;

		private int keyCode;

		KeyEvent(short type, int keyCode) {
			eventDispatcher.super();
			this.type = type;
			this.keyCode = keyCode;
		}

		public void run() {
			switch (type) {
			case KEY_PRESSED:
				if (current != null) {
					current.keyPressed(keyCode);
				}
				break;

			case KEY_RELEASED:
				if (current != null) {
					current.keyReleased(keyCode);
				}
				break;

			case KEY_REPEATED:
				if (current != null) {
					current.keyRepeated(keyCode);
				}
				break;
			}
		}
	}
	
	private final class HideNotifyEvent extends EventDispatcher.RunnableEvent {

		public HideNotifyEvent(EventDispatcher eventDispatcher, Runnable runnable) {
			eventDispatcher.super(runnable);
		}

	}

	private final class ShowNotifyEvent extends EventDispatcher.RunnableEvent {

		public ShowNotifyEvent(EventDispatcher eventDispatcher, Runnable runnable) {
			eventDispatcher.super(runnable);
		}

	}
        */
        
	public class DisplayAccessor implements DisplayAccess {

		Display display;

		DisplayAccessor(Display d) {

			display = d;
		}

		public void commandAction(final Command c, final Displayable d) {

			if (c.isRegularCommand()) {
				if (d == null) {
					return;
				}
				final CommandListener listener = d.getCommandListener();
				if (listener == null) {
					return;
				}
                                /*
				eventDispatcher.put(new Runnable() {
                                    
					public void run() {
                                */
						listener.commandAction(c, d);			
					//}
					
				//});
			} else {
				// item contained command
				commandAction(c.getOriginalCommand(), c.getFocusedItem());
			}
		}

		public void commandAction(final Command c, final Item item) {
			final ItemCommandListener listener = item.getItemCommandListener();
			if (listener == null) {
				return;
			}
                        /*
			eventDispatcher.put(new Runnable() {

				public void run() {
                        */
					listener.commandAction(c, item);
			/*	}

			});
                        */
		}
		
		public Display getDisplay() {
			return display;
		}

		// Andres Navarro
                /*
		private void processGameCanvasKeyEvent(GameCanvas c, int k, boolean press) {
			// TODO Game Canvas keys need more work
			// and better integration with the microemulator
			// maybe actualKeyState in GameCanvas should be
			// global and should update even while no GameCanvas
			// is current
			GameCanvasKeyAccess access = MIDletBridge.getGameCanvasKeyAccess(c);
			int gameCode = c.getGameAction(k);
			boolean suppress = false;
			if (gameCode != 0) {
				// valid game key
				if (press)
					access.recordKeyPressed(c, gameCode);
				else
					access.recordKeyReleased(c, gameCode);
				suppress = access.suppressedKeyEvents(c);
			}
			if (!suppress) {
				if (press) {
					eventDispatcher.put(new KeyEvent(KeyEvent.KEY_PRESSED, k));
				} else {
					eventDispatcher.put(new KeyEvent(KeyEvent.KEY_RELEASED, k));
				}
			}
		}
                */

		// TODO according to the specification this should be
		// only between show and hide notify...
		// check later
		// Andres Navarro
		public void keyPressed(int keyCode) {
			// Andres Navarro
                    
//TWB - optimized
//            if (display.isCanvas)
//            {
                display.current.keyPressed(keyCode);
//            }
//            else
//            {
//                System.out.println("Trying to keyPress on EventDispatcher");
//                //eventDispatcher.put(new KeyEvent(KeyEvent.KEY_PRESSED, keyCode));
//            }
            /*
			if (current != null && current instanceof GameCanvas) {
				processGameCanvasKeyEvent((GameCanvas) current, keyCode, true);
			} else {
				eventDispatcher.put(new KeyEvent(KeyEvent.KEY_PRESSED, keyCode));
			}
                        * */
		}

		public void keyRepeated(int keyCode) {
                    
//TWB - optimized
//            if (display.isCanvas)
//            {
                display.current.keyRepeated(keyCode);
//            }
//            else
//            {
//                System.out.println("Trying to keyRepeat on EventDispatcher");
//                //eventDispatcher.put(new KeyEvent(KeyEvent.KEY_REPEATED, keyCode));
//            }
            
			//eventDispatcher.put(new KeyEvent(KeyEvent.KEY_REPEATED, keyCode));
		}

		public void keyReleased(int keyCode) {
			// Andres Navarro
//TWB - optimized
//            if (display.isCanvas)
//            {
                display.current.keyReleased(keyCode);
//            }
//            else
//            {
//                System.out.println("Trying to keyReleased on EventDispatcher");
//                //eventDispatcher.put(new KeyEvent(KeyEvent.KEY_REPEATED, keyCode));
//            }
            /*
			if (current != null && current instanceof GameCanvas) {
				processGameCanvasKeyEvent((GameCanvas) current, keyCode, false);
			} else {
				eventDispatcher.put(new KeyEvent(KeyEvent.KEY_RELEASED, keyCode));
			}
                        */
		}

		public void pointerPressed(final int x, final int y) {
                    /*
			if (current != null) {
				eventDispatcher.put(eventDispatcher.new PointerEvent(new Runnable() {
					
					public void run() {
						current.pointerPressed(x, y);
						
					}
					
				}, EventDispatcher.PointerEvent.POINTER_PRESSED, x, y));
			}
                    */
		}

		public void pointerReleased(final int x, final int y) {
                    /*
			if (current != null) {
				eventDispatcher.put(eventDispatcher.new PointerEvent(new Runnable() {
					
					public void run() {
						current.pointerReleased(x, y);
						
					}
					
				}, EventDispatcher.PointerEvent.POINTER_RELEASED, x, y));
			}
                    */
		}

		public void pointerDragged(final int x, final int y) {
                    /*
			if (current != null) {
				eventDispatcher.put(eventDispatcher.new PointerEvent(new Runnable() {
					
					public void run() {
						current.pointerDragged(x, y);
						
					}
					
				}, EventDispatcher.PointerEvent.POINTER_DRAGGED, x, y));
			}
                    */
		}

		public void paint(Graphics g) {
			// TODO consider removal of DisplayAccess::paint(..)
				try {
					current.paint(g);
				} catch (Throwable th) {
					th.printStackTrace();
				}
				g.translate(-g.getTranslateX(), -g.getTranslateY());
		}

		public Displayable getCurrent() {
			return display.getCurrent();
		}

		public DisplayableUI getDisplayableUI(Displayable displayable) {
			return displayable.ui;
		}

        public ItemUI getItemUI(Item item) {
        	return item.ui;
        }

		public boolean isFullScreenMode() {
			Displayable current = getCurrent();

//			if (current.getTypeAsInt() == Canvas.TYPE) {
				return ((Displayable) current).fullScreenMode;
//			} else {
//				return false;
//			}
		}

		public void hideNotify() {
            Displayable current = getCurrent();
            if (current != null) {
                current.hideNotify();
            }
		}

        public void setCurrent(Displayable d) {
			getDisplay().setCurrent(d);
		}

		public void sizeChanged() {
                            /*
	    		if (current instanceof GameCanvas) {
	    			current.width = -1;
	    			current.height = -1;
	    		}
                            */
				current.sizeChanged(Display.this);
		}

		public void repaint() {
			Displayable d = this.display.getCurrent();
			if (d != null) {
				this.display.repaint(d, 0, 0, d.getWidth(), d.getHeight());
			}
		}

		public void clean() {
                            /*
				eventDispatcher.put(new HideNotifyEvent(eventDispatcher, new Runnable() {
			*/		
					//private 
                                                Displayable displayable = current;

			//		public void run() {
                            
						displayable.hideNotify(Display.this);
			//		}
					
			//	}));

			//eventDispatcher.cancel();
			//timer.cancel();
		}
	}

//	private class AlertTimeout extends TimerTask {
//
//		private Alert alert;
//
//		AlertTimeout(Alert alert) {
//			this.alert = alert;
//		}
//
//		public void run() {
//            if (alert.isShown()) {
//            	MIDletBridge.getMIDletAccess().getDisplayAccess().commandAction(
//            			(Command) alert.getCommands().get(0), alert);
//            }
//        }
//	}

        //TWB - no timer needed
	//private final Timer timer = new Timer();

	/**
	 * Wrap any runnable as a timertask so that when the timer gets fired, the
	 * runnable gets run
	 * 
	 * @author radoshi
	 * 
	 */
        /*
	private final class RunnableWrapper extends TimerTask {

		private final Runnable runnable;

		RunnableWrapper(Runnable runnable) {
			this.runnable = runnable;
		}

		public void run() {
			eventDispatcher.put(runnable);
		}

	}*/

        private final EmuThreadPool displayThreadPool = DisplayThreadPool.getInstance();

	Display() {
		accessor = new DisplayAccessor(this);

		//eventDispatcher = DeviceFactory.getDevice().getUIFactory().createEventDispatcher(this);

//		timer.scheduleAtFixedRate(new RunnableWrapper(new TickerPaintTask()), 0, Ticker.PAINT_TIMEOUT);
//		timer.scheduleAtFixedRate(new RunnableWrapper(new GaugePaintTask()), 0, Ticker.PAINT_TIMEOUT);
	}

        /*
	public void callSerially(Runnable runnable) {
		eventDispatcher.put(runnable);
	}
        */

	public int numAlphaLevels() {
		return DeviceFactory.getDevice().getDeviceDisplay().numAlphaLevels();
	}

	public int numColors() {
		return DeviceFactory.getDevice().getDeviceDisplay().numColors();
	}

	public boolean flashBacklight(int duration) {
		return DeviceFactory.getDevice().getDeviceDisplay().flashBacklight(duration);
	}

	public static Display getDisplay(MIDlet m) {
		Display result;

                final MIDletAccess midletAccess = MIDletBridge.getMIDletAccess(m);
		if (midletAccess.getDisplayAccess() == null) {
			result = new Display();
			midletAccess.setDisplayAccess(result.accessor);
		} else {
			result = midletAccess.getDisplayAccess().getDisplay();
		}

		return result;
	}

	public int getColor(int colorSpecifier) {
		// TODO implement better
		switch (colorSpecifier) {
		case COLOR_BACKGROUND:
		case COLOR_HIGHLIGHTED_FOREGROUND:
		case COLOR_HIGHLIGHTED_BORDER:
			return 0xFFFFFF;
		default:
			return 0x000000;
		}
	}

	public int getBorderStyle(boolean highlighted) {
		// TODO implement better
		return highlighted ? Graphics.DOTTED : Graphics.SOLID;
	}

	public int getBestImageWidth(int imageType) {
		// TODO implement
		return 0;
	}

	public int getBestImageHeight(int imageType) {

		// TODO implement
		return 0;
	}

	public Displayable getCurrent() {
		return current;
	}

	public boolean isColor() {
		return DeviceFactory.getDevice().getDeviceDisplay().isColor();
	}

	private EmulatorViewInterface allBinaryMidletView = new EmulatorViewInterface() {

            public void setMidlet(final MIDlet midlet) {}
    
            public void onEmulatorInitComplete(final Object midletActivity) {}

            public void onSetDisplayable(Displayable displayable) {}

        };
	public void addListener(EmulatorViewInterface allBinaryMidletView)
	{
	    this.allBinaryMidletView = allBinaryMidletView;
	}
        
	public void setCurrent(final Displayable nextDisplayable) {
		if (nextDisplayable == current) {
			return;
		}
		if (nextDisplayable != null) {
                    /*
			eventDispatcher.put(new ShowNotifyEvent(eventDispatcher, new Runnable() {

				public void run() {
					if (current != null) {
						eventDispatcher.put(new HideNotifyEvent(eventDispatcher, new Runnable() {
							
							private Displayable displayable = current;

							public void run() {
								displayable.hideNotify(Display.this);
							}
							
						}));
					}
*/
                    /*
					if (nextDisplayable instanceof Alert) {
						nextDisplayable.showNotify(Display.this);
						nextDisplayable.repaint();
						if (Alert.nextDisplayable == null) { // setCurrent(Alert);
							Alert.nextDisplayable = current;
						}
						current = nextDisplayable;
						Alert alert = (Alert) nextDisplayable;
						if (alert.getTimeout() != Alert.FOREVER) {
							timer.schedule(new AlertTimeout(alert), alert.getTimeout());
						}
						return;
					}
*/
					// Andres Navarro
					// TODO uncomment and test with JBenchmark2
					/*
					 * if (nextDisplayable instanceof GameCanvas) { // clear the
					 * keys of the GameCanvas
					 * MIDletBridge.getMIDletAccess().getGameCanvasKeyAccess().setActualKeyState(
					 * (GameCanvas) nextDisplayable, 0); }
					 */
					// Andres Navarro
                    /*
					nextDisplayable.showNotify(Display.this);
					Display.this.current = nextDisplayable;

					setScrollUp(false);
					setScrollDown(false);
					nextDisplayable.repaint();
				}
												
			}));
                        */
                    
            /*
             * //TWB - Same as above but attempt to work better
                    nextDisplayable.showNotify(Display.this);

                    Display.this.isCanvas = false;

                    Display.this.current = nextDisplayable;

                    if (Display.this.current instanceof Canvas)
                    {
                        Display.this.isCanvas = true;
                    }
                    else
                    {
                        Display.this.isCanvas = false;
                    }

                    setScrollUp(false);
                    setScrollDown(false);

                    nextDisplayable.repaint();
                }
            }));
             */

            //TWB - more performant handling of setting current
            final Displayable tempDisplayable = this.current;
            //this.init();

                    nextDisplayable.showNotify(this);

                    //this.isCanvas = false;

                    this.current = nextDisplayable;
                    
                    this.allBinaryMidletView.onSetDisplayable(nextDisplayable);
                    //System.out.println(new StringBuffer().append(current).append(" setCurrent ").append(System.currentTimeMillis()).toString());

//                    if (this.current instanceof Canvas &&
//                        this.current.getTitle() == null)
//                    {
//                        this.isCanvas = true;
//                    }
//                    else
//                    {
//                        this.isCanvas = false;
//                    }

                    setScrollUp(false);
                    setScrollDown(false);

                    nextDisplayable.repaint();

            if(tempDisplayable != null)
            {
                tempDisplayable.hideNotify(this);
            }
            
            this.hackForSWT(nextDisplayable);            
                               
		}
	}

        //This keeps the Displayable from showing the prior Displayable.
        private void hackForSWT(final Displayable nextDisplayable) {

            System.out.println(new StringBuffer().append(current).append("hackForSWT").toString());

            this.displayThreadPool.runTask(
                //final Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            for (int index = 0; index < 1; index++) {
                                Thread.sleep(100);
                                if (nextDisplayable != null) {
                                    //System.out.println(new StringBuffer().append(current).append(" setCurrent - repaint").toString());
                                    nextDisplayable.repaint();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            );

            //thread.start();
        }
        
	public void setCurrent(Alert alert, Displayable nextDisplayable) {
		if (alert == null) {
			throw new NullPointerException("alert");
		}
		if (nextDisplayable == null) {
			throw new NullPointerException("nextDisplayable");
		}
		if (nextDisplayable instanceof Alert) {
			throw new IllegalArgumentException("nextDisplayable");
		}

		Alert.nextDisplayable = nextDisplayable;

		setCurrent(alert);
	}

	public void setCurrentItem(Item item) {
		if (item.owner != current) {
			setCurrent(item.owner);
		}
	}

	public boolean vibrate(int duration) {
		return DeviceFactory.getDevice().vibrate(duration);
	}

	static int getGameAction(int keyCode) {
		return DeviceFactory.getDevice().getInputMethod().getGameAction(keyCode);
	}

	static int getKeyCode(int gameAction) {
		return DeviceFactory.getDevice().getInputMethod().getKeyCode(gameAction);
	}

	static String getKeyName(int keyCode) throws IllegalArgumentException {
		return DeviceFactory.getDevice().getInputMethod().getKeyName(keyCode);
	}

	boolean isShown(Displayable d) {
		if (current != d) {
			return false;
		} else {
			return true;
		}
	}

//        class RepaintRunnable implements Runnable
//        {
//            public int x, y, width, height;
//
//            //private final String REPAINT = "TWB:RepaintRunnable";
//
//            public void run() {
//                //System.out.println(REPAINT + Thread.currentThread());
//                DeviceFactory.getDevice().getDeviceDisplay().repaint(x, y, width, height);
//            }
//        };
        
        //public final RepaintRunnable repaintRunnable = new RepaintRunnable();
        
        //DeviceFactory.getDevice().getDeviceDisplay().repaint(x, y, width, height);
	void repaint(final Displayable d, final int x, final int y, final int width, final int height) {

		if (current == d) {
                    
                    //System.out.println(new StringBuffer().append(current).append("TWB:repaint").toString());
                    DeviceFactory.getDevice().getDeviceDisplay().repaint(x, y, width, height);

//                    repaintRunnable.x = x;
//                    repaintRunnable.y = y;
//                    repaintRunnable.width = width;
//                    repaintRunnable.height = height;
//
//                    displayThreadPool.runTask(repaintRunnable);

//                    new Thread(repaintRunnable).start();
			//eventDispatcher.put(eventDispatcher.new PaintEvent(x, y, width, height));
		}
//                else {
//                    System.out.println(new StringMaker().append("repaint did not process as the current displayable is not what was given: ").append(current).append(" !=").append(d).toString());
//                }
	}

	void serviceRepaints() {
		//
		// If service repaints is being called from the event thread, then we
		// just execute an immediate repaint and call it a day. If it is being
		// called from another thread, then we setup a repaint barrier and wait
		// for that barrier to execute
		//
                /*
		if (EventDispatcher.EVENT_DISPATCHER_NAME.equals(Thread.currentThread().getName())) {
			if (current != null) {
				DeviceFactory.getDevice().getDeviceDisplay().repaint(0, 0, current.getWidth(), current.getHeight());
			}
			return;
		}

		eventDispatcher.serviceRepaints();
                */
                //TWB - Should not be called
                System.out.println("shouldn't call Display::serviceRepaints");
	}

	void setScrollDown(boolean state) {
		DeviceFactory.getDevice().getDeviceDisplay().setScrollDown(state);
	}

	void setScrollUp(boolean state) {
		DeviceFactory.getDevice().getDeviceDisplay().setScrollUp(state);
	}

//    public void init() {
//        DeviceFactory.getDevice().getFontManager().init();
//    }    
        
}
