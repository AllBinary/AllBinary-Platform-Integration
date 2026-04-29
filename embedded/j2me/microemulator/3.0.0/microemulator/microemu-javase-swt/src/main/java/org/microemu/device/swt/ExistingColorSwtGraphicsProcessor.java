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
public class ExistingColorSwtGraphicsProcessor extends SwtGraphicsProcessor {
    
    private final Color color;
    
    public ExistingColorSwtGraphicsProcessor(final Color color) {
        this.color = color;
    }
    
    public Color getColor(final SwtGraphics g, final RGBA rgba) {
         return this.color;
    }
    
}
