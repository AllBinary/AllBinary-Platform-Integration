/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGBA;
import org.microemu.app.ui.swt.SwtGraphics;

/**
 *
 * @author User
 */
public class NewColorSwtGraphicsProcessor extends SwtGraphicsProcessor {
    
    private final SwtDeviceDisplay swtDeviceDisplay;
    
    public NewColorSwtGraphicsProcessor(final SwtDeviceDisplay swtDeviceDisplay) {
        this.swtDeviceDisplay = swtDeviceDisplay;
    }
    
    public Color getColor(final SwtGraphics g, final RGBA rgba) {
        final Color color = g.getColor(rgba);
        this.swtDeviceDisplay.foregroundGraphicsProcessor = new ExistingColorSwtGraphicsProcessor(color);
        return color;
    }

}
