/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

/**
 *
 * @author User
 */
public class RepaintProcessor {
 
    private static final RepaintProcessor instance = new RepaintProcessor();

    /**
     * @return the instance
     */
    public static RepaintProcessor getInstance() {
        return instance;
    }
    
    public void repaint(final Object context, final int x, final int y, final int width, final int height) {
        
    }
    
}
