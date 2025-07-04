/**
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

package org.microemu.app;

//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.midlet.MIDlet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import org.microemu.DisplayComponent;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.Message;
import org.microemu.app.ui.ResponseInterfaceListener;
import org.microemu.app.ui.StatusBarListener;
import org.microemu.app.ui.swt.SwtDeviceComponent;
//import org.microemu.app.ui.swt.SwtDialog;
import org.microemu.app.ui.swt.SwtErrorMessageDialogPanel;
//import org.microemu.app.ui.swt.SwtInputDialog;
import org.microemu.app.ui.swt.SwtSelectDeviceDialog;
import org.microemu.app.util.DeviceEntry;
//import org.microemu.app.util.IOUtils;
import org.microemu.device.Device;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.DeviceFactory;
import org.microemu.device.EmulatorContext;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.swt.SwtDevice;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtFontManager;
import org.microemu.device.swt.SwtInputMethod;
import org.microemu.log.Logger;

import org.allbinary.emulator.swt.SWTProcessorUtil;
import org.allbinary.graphics.ResizableListenerHandler;
import org.allbinary.graphics.ResizableListenerInterface;
import org.allbinary.graphics.ScreenListenerHandler;
import org.allbinary.graphics.ScreenListenerInterface;
import org.allbinary.graphics.threed.SWTJOGLProcessor;
import org.allbinary.logic.java.exception.ExceptionUtil;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;

public class BareMain extends Common 
    implements ScreenListenerInterface, ResizableListenerInterface
{
	public static Shell shell;
        //public static GC gc;

	protected static SwtDeviceComponent devicePanel;
        
	//protected MenuItem menuOpenJADFile;

	//protected MenuItem menuOpenJADURL;

	private SwtSelectDeviceDialog selectDeviceDialog;

	//private FileDialog fileDialog = null;

	//private MenuItem menuSelectDevice;

	private DeviceEntry deviceEntry;

	//private Label statusBar;

//	private KeyListener keyListener = new KeyListener() {
//		public void keyTyped(KeyEvent e) {
//		}
//
//		public void keyPressed(KeyEvent e) {
//			devicePanel.keyPressed(e);
//		}
//
//		public void keyReleased(KeyEvent e) {
//			devicePanel.keyReleased(e);
//		}
//	};

//	protected Listener menuOpenMIDletFileListener = new Listener() {
//		public void handleEvent(Event ev) {
//			if (fileDialog == null) {
//				fileDialog = new FileDialog(shell, SWT.OPEN);
//				fileDialog.setText("Open MIDlet File...");
//				fileDialog.setFilterNames(new String[] { "MIDlet files" });
//				fileDialog.setFilterExtensions(new String[] { "*.ja[dr]" });
//				fileDialog.setFilterPath(Config.getRecentDirectory("recentJadDirectory"));
//			}
//
//			fileDialog.open();
//
//			if (fileDialog.getFileName().length() > 0) {
//				File selectedFile;
//				if (fileDialog.getFilterPath() == null) {
//					selectedFile = new File(fileDialog.getFileName());
//				} else {
//					selectedFile = new File(fileDialog.getFilterPath(), fileDialog.getFileName());
//					Config.setRecentDirectory("recentJadDirectory", fileDialog.getFilterPath());
//				}
//				String url = IOUtils.getCanonicalFileURL(selectedFile);
//				Common.openMIDletUrlSafe(url);
//			}
//		}
//	};

//	protected Listener menuOpenMIDletURLListener = new Listener() {
//		public void handleEvent(Event ev) {
//			// TODO change to JadUrlPanel
//			SwtInputDialog inputDialog = new SwtInputDialog(shell, "Open...", "Enter MIDlet URL:");
//			if (inputDialog.open() == SwtDialog.OK) {
//				try {
//					openMIDletUrl(inputDialog.getValue());
//				} catch (IOException ex) {
//					System.err.println("Cannot load " + inputDialog.getValue());
//				}
//			}
//		}
//	};

	protected final Listener menuExitListener = new Listener() {
		public void handleEvent(Event unusedEvent) {
			Config.setWindow("main", new Rectangle(shell.getLocation().x, shell.getLocation().y, shell.getSize().x,
					shell.getSize().y), true);

			System.exit(0);
		}
	};

//	private Listener menuSelectDeviceListener = new Listener() {
//		public void handleEvent(Event e) {
//			if (selectDeviceDialog.open() == SwtDialog.OK) {
//				if (selectDeviceDialog.getSelectedDeviceEntry().equals(getDevice())) {
//					return;
//				}
//				if (MIDletBridge.getCurrentMIDlet() != getLauncher()) {
//					if (!SwtMessageDialog
//							.openQuestion(shell, "Question?",
//									"Changing device needs MIDlet to be restarted. All MIDlet data will be lost. Are you sure?")) {
//						return;
//					}
//				}
//				setDevice(selectDeviceDialog.getSelectedDeviceEntry());
//
//				if (MIDletBridge.getCurrentMIDlet() != getLauncher()) {
//					try {
//						initMIDlet(true);
//					} catch (Exception ex) {
//						System.err.println(ex);
//					}
//				} else {
//					startLauncher(MIDletBridge.getMIDletContext());
//				}
//			}
//		}
//	};

	private final StatusBarListener statusBarListener = new StatusBarListener() {
		public void statusBarChanged(final String text) {
			//shell.getDisplay().asyncExec(new Runnable() {
                        shell.getDisplay().syncExec(new Runnable() {
				public void run() {
					//statusBar.setText(text);
                                        System.out.println("status: " + text);
				}
			});
		}
	};

	private final ResponseInterfaceListener responseInterfaceListener = new ResponseInterfaceListener() {
		public void stateChanged(final boolean state) {
//			shell.getDisplay().asyncExec(new Runnable() {
//				public void run() {
//					menuOpenJADFile.setEnabled(state);
//					menuOpenJADURL.setEnabled(state);
//					menuSelectDevice.setEnabled(state);
//				}
//			});
		}
	};

	/*
	 * WindowAdapter windowListener = new WindowAdapter() { public void
	 * windowClosing(WindowEvent ev) { menuExitListener.actionPerformed(null); }
	 * 
	 * 
	 * public void windowIconified(WindowEvent ev) {
	 * MIDletBridge.getMIDletAccess
	 * (common.getLauncher().getCurrentMIDlet()).pauseApp(); }
	 * 
	 * public void windowDeiconified(WindowEvent ev) { try {
	 * MIDletBridge.getMIDletAccess
	 * (common.getLauncher().getCurrentMIDlet()).startApp(); } catch
	 * (MIDletStateChangeException ex) { System.err.println(ex); } } };
	 */

        private boolean isWindows = false;
        
	protected BareMain(final Shell shell) {
		this(shell, null);
	}

	protected BareMain(final Shell shell, final DeviceEntry defaultDevice) {
		super(new EmulatorContext() {
			private InputMethod inputMethod = new SwtInputMethod();

			private DeviceDisplay deviceDisplay = SwtDeviceDisplay.getInstance(this); //new SwtDeviceDisplay(this);

			private FontManager fontManager = new SwtFontManager();

			public DisplayComponent getDisplayComponent() {
				return devicePanel.getDisplayComponent();
			}

			public InputMethod getDeviceInputMethod() {
				return inputMethod;
			}

			public DeviceDisplay getDeviceDisplay() {
				return deviceDisplay;
			}

			public FontManager getDeviceFontManager() {
				return fontManager;
			}

			public InputStream getResourceAsStream(final String name) {
				return MIDletBridge.getCurrentMIDlet().getClass().getResourceAsStream(name);
			}

			public boolean platformRequest(final String URL) {
				new Thread(new Runnable() {
					public void run() {
						Message.info("MIDlet requests that the device handle the following URL: " + URL);
					}
				}).start();

				return false;
			}

			@Override
			public InputStream getResourceAsStream(Class origClass, String name) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		initInterface(shell);

		// addWindowListener(windowListener);

		Config.loadConfig(null, emulatorContext);
		//loadImplementationsFromConfig();

		//shell.addKeyListener(keyListener);

		selectDeviceDialog = new SwtSelectDeviceDialog(shell, emulatorContext);

		setStatusBarListener(statusBarListener);
		setResponseInterfaceListener(responseInterfaceListener);

		Message.addListener(new SwtErrorMessageDialogPanel(shell));
                
                ResizableListenerHandler.getInstance().setListener(this);
                ScreenListenerHandler.getInstance().setListener(this);
                
	}

	protected void initInterface(final Shell shell) {
		final GridLayout layout = new GridLayout(1, false);
                //Remove most of the border
                //layout.marginHeight = -2;
                //layout.marginWidth = -2;
                layout.marginBottom = 0;
                layout.marginHeight = 0;
                layout.marginLeft = 0;
                layout.marginRight = 0;
                layout.marginTop = 0;
                layout.marginWidth = 0;
                layout.horizontalSpacing = 0;
                layout.verticalSpacing = 0;
                
		shell.setLayout(layout);
		shell.setLayoutData(new GridData(GridData.FILL_BOTH));

//		Menu bar = new Menu(shell, SWT.BAR);
//		shell.setMenuBar(bar);
//
//		MenuItem menuFile = new MenuItem(bar, SWT.CASCADE);
//		menuFile.setText("File");
//
//		Menu fileSubmenu = new Menu(shell, SWT.DROP_DOWN);
//		menuFile.setMenu(fileSubmenu);

//		menuOpenJADFile = new MenuItem(fileSubmenu, SWT.PUSH);
//		menuOpenJADFile.setText("Open MIDlet File...");
//		menuOpenJADFile.addListener(SWT.Selection, menuOpenMIDletFileListener);
//
//		menuOpenJADURL = new MenuItem(fileSubmenu, 0);
//		menuOpenJADURL.setText("Open MIDlet URL...");
//		menuOpenJADURL.addListener(SWT.Selection, menuOpenMIDletURLListener);

//		new MenuItem(fileSubmenu, SWT.SEPARATOR);
//
//		MenuItem menuExit = new MenuItem(fileSubmenu, SWT.PUSH);
//		menuExit.setText("Exit");
//		menuExit.addListener(SWT.Selection, menuExitListener);
//
//		MenuItem menuOptions = new MenuItem(bar, SWT.CASCADE);
//		menuOptions.setText("Options");
//
//		Menu optionsSubmenu = new Menu(shell, SWT.DROP_DOWN);
//		menuOptions.setMenu(optionsSubmenu);

//		menuSelectDevice = new MenuItem(optionsSubmenu, SWT.PUSH);
//		menuSelectDevice.setText("Select device...");
//		menuSelectDevice.addListener(SWT.Selection, menuSelectDeviceListener);

//		shell.setText("MicroEmulator");

		devicePanel = new SwtDeviceComponent(shell);
                //GLCanvas works with FillLayout and does not with
                devicePanel.setLayout(new FillLayout());
		devicePanel.setLayoutData(new GridData(GridData.FILL_BOTH));

                final SWTJOGLProcessor swtjoglProcessor = SWTJOGLProcessor.getInstance();
                swtjoglProcessor.init(shell.getDisplay(), devicePanel);
                swtjoglProcessor.createSurface();
                swtjoglProcessor.addListener();

		//statusBar = new Label(shell, SWT.HORIZONTAL);
		//statusBar.setText("Status");
		//statusBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

    public void onResizable(final boolean isResizable)
    {
       //Logger.debug("", "onResizable");
//        if (isResizable)
//        {
//            this.setResizable(true);
//        }
//        else
//        {
//            this.setResizable(false);
//        }
    }
        
    public void onFullScreenDisplay()
    {
       //Logger.debug("", "onFullScreenDisplay");
    }
        
    public void onFullScreen(final boolean isFullScreen) 
    {
        try {
            if (isFullScreen) {
                //javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                  //  @Override
                    //public void run() {
                        this.startFullScreen();
                    //}
                //});

                //Logger.debug("onFullScreen", "Entering Full-screen mode: " + this.getInfo());
                Logger.debug("onFullScreen", "Entering Full-screen mode");
            } else {
                //javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                  //  @Override
                    //public void run() {
                        this.exitFullScreen();
                    //}
                //});

                Logger.debug("onFullScreen", "Exiting Full-screen mode");
            }

        } catch (Exception e) {
            Logger.error("onFullScreen", "Unable to change Full-screen mode", e);
        }
    }

    private int widthBeforeFullScreen, heightBeforeFullScreen;
    
    private void exitFullScreen()
    {
        try
        {
            //this.setVisible(false);

            //this.removeNotify();

            this.dispose();
            //this.setUndecorated(false);
            //this.pack();
            //this.setResizable(true);
            //this.graphicsDevice.setFullScreenWindow(null);

            //this.addNotify();

            if(isWindows)
            {
                //this.getExtendedState() |
                //this.setExtendedState(Frame.ICONIFIED);
                //this.setExtendedState(Frame.NORMAL);
            }

            if(widthBeforeFullScreen != 0 && heightBeforeFullScreen != 0)
            {
                //this.setSize(widthBeforeFullScreen, heightBeforeFullScreen);
            }
            
            //this.setup();
            //if(!isWindows)
            //this.setup();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.debug("", "Error Exiting Full-screen mode");
        }
    }

    public void startFullScreen()
    {
        try
        {
            Logger.debug("startFullScreen", "Trying to go Full-Screen");
            
            //widthBeforeFullScreen = this.getWidth();
            //heightBeforeFullScreen = this.getHeight();

            //this.setVisible(false);

            //this.removeNotify();

            //this.dispose();
            //this.setUndecorated(true);
            //this.pack();
            //this.setResizable(false);
            //this.graphicsDevice.setFullScreenWindow(this);
            //device.setDisplayMode(dispMode);

            //this.addNotify();

            if(isWindows)
            {
                //this.setExtendedState(this.getExtendedState() | Frame.ICONIFIED | Frame.MAXIMIZED_BOTH);
            //this.setExtendedState(Frame.ICONIFIED);
            //this.setExtendedState(Frame.MAXIMIZED_BOTH);
            }

            //this.setup();
            
            Logger.debug("startFullScreen", "Tried to go Full-Screen");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.debug("", "Error Entering Full-screen mode");
            //this.graphicsDevice.setFullScreenWindow(null);
        }
    }
    
	public void setDevice(final DeviceEntry entry) {
            System.out.println("setDevice");
		if (DeviceFactory.getDevice() != null) {
			// ((SwtDevice) DeviceFactory.getDevice()).dispose();
		}

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			if (entry.getFileName() != null) {
                            System.out.println("setDevice - URL");
//				URL[] urls = new URL[1];
//				urls[0] = new File(Config.getConfigPath(), entry.getFileName()).toURI().toURL();
//				classLoader = Common.createExtensionsClassLoader(urls);
			}

			// TODO font manager have to be moved from emulatorContext into
			// device
			emulatorContext.getDeviceFontManager().init();

			Device device = DeviceImpl.create(emulatorContext, classLoader, entry.getDescriptorLocation(),
					SwtDevice.class);
			this.deviceEntry = entry;
			setDevice(device);
			updateDevice();
//		} catch (MalformedURLException ex) {
//			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	protected void updateDevice() {
		shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT, true));
	}

        public static void main2(final String args[], final String name, final String iconPath) {
            BareMain.main2(args, name, iconPath, -1, -1, false, false);
        }
        
        public static void main2(final String args[], final String name, final String iconPath, final int width, final int height, final boolean fullScreen, final boolean maximized) {
            try {
                System.out.println("java.class.path: " + System.getProperty("java.class.path"));

//                final DeviceData data = new DeviceData();
//                data.tracking = true;
//                final Display display = new Display(data);
//                final Sleak sleak = new Sleak();
//                final Shell shell2 = new Shell(display);
//                shell2.setText("S-Leak");
//                final Point sizePoint = shell2.getSize();
//                shell2.setSize(sizePoint.x / 2, sizePoint.y / 2);
//                final GridLayout layout = new GridLayout(2, false);
//                layout.horizontalSpacing = 0;
//                layout.verticalSpacing = 0;
//                shell2.setLayout(layout);
//                sleak.create(shell2);
//                shell2.open();

                final Display display = new Display();
                
		//shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
                //shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE | SWT.BORDER | SWT.DOUBLE_BUFFERED);
                shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE);
                //sleak.create(shell);
                //gc = new GC(BareMain.shell);

		final List params = new ArrayList();
		for (int i = 0; i < args.length; i++) {
			params.add(args[i]);
		}

                final BareMain app = new BareMain(shell);
                
                //System.out.println("params.size(): " + params.size());
                boolean hasCommandLineParams = true;
                if(params.size() == 0) {
                    hasCommandLineParams = false;
                    String[] params2 = new String[]{
                        "--appclassloader",
                        //"strict",
                        //"relaxed",
                        //"delegating",
                        "system",
                        "-j2sefont", 
                        "--device", 
                        "org/allbinary/device/arcade/eighthundred_by_foureighty/device.xml", 
                        //".jad"
                        name
                    };
                                        
                    for (int i = 0; i < params2.length; i++) {
                        params.add(params2[i]);
                    }
                }
                
                if(!hasCommandLineParams) {
                    app.setSuiteName((String) params.get(params.size() - 1));
                }

                if (app.initParams(params, app.selectDeviceDialog.getSelectedDeviceEntry(), SwtDevice.class)) {
                    app.deviceEntry = app.selectDeviceDialog.getSelectedDeviceEntry();
                    final DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
                    if (deviceDisplay.isResizable()) {
                        final Rectangle size = Config.getDeviceEntryDisplaySize(app.deviceEntry);
                        if (size != null) {
                            //app.setDeviceSize(deviceDisplay, size.width, size.height);
                        }
                    }
                }
                
		//final Rectangle window = Config.getWindow("main", new Rectangle(0, 0, 160, 120));
		//shell.setLocation(window.x, window.y);
                
                //app.updateDevice();
                
		shell.pack();
		shell.open();

                app.updateDevice();

                if(true) {
                    
                    //final SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay();
                    //final Rectangle rectangle = new Rectangle(0, 0, width, height);
                    //deviceDisplay.setDisplayRectangle(rectangle);
                    
//                    String midletString;
//                    try {
//                        midletString = (String) params.iterator().next();
//                    } catch (NoSuchElementException ex) {
//                        midletString = null;
//                    }
//                    System.out.println("midletString: " + midletString);
                    final MIDlet midlet = app.initMIDlet(true);
                    //LogUtil.put(LogFactory.getInstance(new StringMaker().append("midlet: ").append(midlet).toString(), midlet, "main2"));
                    
                    final InputStream is = midlet.getClass().getResourceAsStream(iconPath);
                    final Image image = SwtDeviceComponent.createImage(is);
                    shell.setImage(image);
                    
                    midlet.midletHelper = new SwtMIDletHelper();
                    midlet.midletHelper.midlet = midlet;

                    final SwtMIDletHelper swtMIDletHelper = (SwtMIDletHelper) midlet.midletHelper;
                    ((MidletJOGLInterface) midlet).initView();
                    //LogUtil.put(LogFactory.getInstance("initView", midlet, "main2"));
                    devicePanel.addMouseListener(swtMIDletHelper);
                    devicePanel.addMouseMoveListener(swtMIDletHelper);
                    devicePanel.addDragDetectListener(swtMIDletHelper);
                    //devicePanel.addGestureListener(listener);
                    //devicePanel.addMouseTrackListener(listener);
                    //devicePanel.addMouseWheelListener(listener);
                    //devicePanel.addTouchListener(listener);
                    
                    //shell.addMouseListener(swtMIDletHelper);
                    //shell.addMouseMoveListener(swtMIDletHelper);

                    //TWB
                    //midlet.setAccessControlContext(AccessController.getContext());
                    midlet.midletHelper.addExitListener(app.menuExitListener);
                    
                    //LogUtil.put(LogFactory.getInstance("Finished MIDlet initialization", midlet, "main2"));
                }
                
                shell.setSize(shell.computeSize(width, height, true));
                
                if(fullScreen) shell.setFullScreen(true);
                if(maximized) shell.setMaximized(true);
                shell.getListeners(SWT.Resize)[0].handleEvent(null);
                
                //LogUtil.put(LogFactory.getInstance("Begin SWT Event Loop", shell, "main2"));
                
                //TestWavPlaybackMain testWavPlaybackMain = new TestWavPlaybackMain();
                //testWavPlaybackMain.create();
                
                final SWTProcessorUtil swtProcessorUtil = SWTProcessorUtil.getInstance();
                
                try {
                    //TWB - this will need to be the game thread to improve performance
                    while (!shell.isDisposed()) {
                        if (!display.readAndDispatch()) {
                            swtProcessorUtil.swtProcessor.process(display);
                            //display.sleep();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception: " + ExceptionUtil.getInstance().getStackTrace(e));
                }

                System.exit(0);
                
            } catch(Exception e) {
                Logger.error(e.getMessage(), e);
            }            
        }
        
	public static void main(final String args[]) {
                main2(args, "MiniSpaceWars", "/minispacewar_icon.ico");
	}

}
