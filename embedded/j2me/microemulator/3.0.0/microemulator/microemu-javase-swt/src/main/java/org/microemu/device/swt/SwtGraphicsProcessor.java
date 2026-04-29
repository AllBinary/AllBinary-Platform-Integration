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
public class SwtGraphicsProcessor {
 
    private static final SwtGraphicsProcessor instance = new SwtGraphicsProcessor();

    /**
     * @return the instance
     */
    public static SwtGraphicsProcessor getInstance() {
        return SwtGraphicsProcessor.instance;
    }
    
    public Color getColor(final SwtGraphics g, final RGBA rgba) {
        return g.getColor(rgba);
    }

}
