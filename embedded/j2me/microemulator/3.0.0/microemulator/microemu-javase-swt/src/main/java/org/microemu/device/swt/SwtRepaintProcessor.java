/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

import org.microemu.device.EmulatorContext;

/**
 *
 * @author User
 */
public class SwtRepaintProcessor extends RepaintProcessor {
    
    private static final SwtRepaintProcessor instance = new SwtRepaintProcessor();

    /**
     * @return the instance
     */
    public static SwtRepaintProcessor getInstance() {
        return instance;
    }
    
    public void repaint(final Object context, final int x, final int y, final int width, final int height) {
        ((EmulatorContext) context).getDisplayComponent().repaintRequest(x, y, width, height);
    }
    
}
