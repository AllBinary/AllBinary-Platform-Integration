/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Created By: Travis Berthelot
 */
package org.microemu.device.platform;

import javax.microedition.lcdui.Graphics;

import org.microemu.app.ui.swt.SwtDisplayComponent;
import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.swt.SwtDisplayGraphics;

/**
 *
 * @author User
 */
public class DisplayGraphicsFactory {
    
    public static Graphics getInstance(Object repaintObject) {
        final SwtDisplayComponent dc = (SwtDisplayComponent) DeviceImpl.getEmulatorContext().getDisplayComponent();
        return new SwtDisplayGraphics((SwtGraphics) repaintObject, dc.getDisplayImage());
    }
}
