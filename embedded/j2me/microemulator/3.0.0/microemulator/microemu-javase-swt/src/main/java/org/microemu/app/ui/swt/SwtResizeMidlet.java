/*
 * AllBinary Open License Version 1
 * Copyright (c) 2026 AllBinary
 * 
 * By agreeing to this license you and any business entity you represent are
 * legally bound to the AllBinary Open License Version 1 legal agreement.
 * 
 * You may obtain the AllBinary Open License Version 1 legal agreement from
 * AllBinary or the root directory of AllBinary's AllBinary Platform repository.
 * 
 * Created By: Travis Berthelot
 * 
 */
package org.microemu.app.ui.swt;

import javax.microedition.lcdui.Display;

import org.allbinary.game.displayable.canvas.GameRunnable;
import org.allbinary.game.displayable.canvas.NullWaitGameRunnable;
import org.allbinary.logic.communication.log.PreLogUtil;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.logic.string.StringUtil;
import org.allbinary.string.CommonStrings;

import org.eclipse.swt.widgets.Shell;

import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.swt.SwtDeviceDisplay;

/**
 *
 * @author User
 */
public class SwtResizeMidlet {

    private static final SwtResizeMidlet instance = new SwtResizeMidlet();

    /**
     * @return the instance
     */
    public static SwtResizeMidlet getInstance() {
        return instance;
    }
    
    public void process(final Shell shell) {
        try {

            final GameRunnable gameRunnable = NullWaitGameRunnable.getInstance();
            gameRunnable.waitInMillis = gameRunnable.FAST;

            final SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay();
            //shell.getLocation().x, shell.getLocation().y
//                            System.out.println("handleEvent - shell.getBorderWidth(): " + shell.getBorderWidth());
//                            System.out.println("handleEvent - shell.getBounds(): " + shell.getBounds());
//                            System.out.println("handleEvent - shell.getClientArea(): " + shell.getClientArea());
//                            System.out.println("handleEvent - shell.getRegion(): " + shell.getRegion());
//                            System.out.println("handleEvent - getBounds: " + getBounds());
//                            System.out.println("handleEvent - getClientArea: " + getClientArea());
            //final Rectangle rectangle = new Rectangle(0, 0, shell.getClientArea().width - (shell.getBorderWidth() * 10), shell.getClientArea().height - (shell.getBorderWidth() * 10));
            //final Rectangle rectangle = new Rectangle(0, 0, shell.getClientArea().width + 2, shell.getClientArea().height + 2);
            //final Rectangle rectangle2 = new Rectangle(0, 0, (int) (shell.getClientArea().width / deviceDisplay.ratio), (int) (shell.getClientArea().height / deviceDisplay.ratio));

            final org.eclipse.swt.graphics.Rectangle swtRectangle = shell.getClientArea();
            final Rectangle rectangle = new Rectangle(swtRectangle.x, swtRectangle.y, swtRectangle.width, swtRectangle.height);
            final StringUtil stringUtil = StringUtil.getInstance();
            System.out.println(new StringMaker().append(stringUtil.toString(this)).append(":handleEvent - SWT.Resize: ").append(stringUtil.toString(rectangle)).toString());

            final MIDletAccess ma = MIDletBridge.getMIDletAccess();
            if (ma == null) {
                System.out.println("SWT.Resize:MIDletAccess was null");
                return;
            }

            final Display.DisplayAccessor da = (Display.DisplayAccessor) ma.getDisplayAccess();
            if (da == null) {
                System.out.println("SWT.Resize:DisplayAccess was null");
                return;
            }

            deviceDisplay.setDisplayRectangle(rectangle);
            deviceDisplay.setDisplayPaintable(rectangle);

            da.sizeChanged();

            //deviceDisplay.repaint(0, 0, deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
        } catch (Exception e) {
            final CommonStrings commonStrings = CommonStrings.getInstance();
            PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
        }

    }
}
