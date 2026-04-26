/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

import javax.microedition.lcdui.Image;

import org.eclipse.swt.graphics.GC;

import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.MutableImage;

/**
 *
 * @author User
 */
public class PostLoadPlatformMutableImage extends PostLoadSwtPlatformImage {

    private static final PostLoadPlatformMutableImage instance = new PostLoadPlatformMutableImage();

    /**
     * @return the instance
     */
    public static PostLoadPlatformMutableImage getInstance() {
        return instance;
    }

    @Override
    public int getSurfaceWidth(final Object image, final int width) {
        return ((org.eclipse.swt.graphics.Image) image).getBounds().width;
    }

    @Override
    public int getSurfaceHeight(final Object image, final int height) {
        return ((org.eclipse.swt.graphics.Image) image).getBounds().height;
    }

    @Override
    public javax.microedition.lcdui.Graphics getGraphics(final Object gc, final int width, final int height, final Image image) {
        final SwtDisplayGraphics displayGraphics = new SwtDisplayGraphics(new SwtGraphics((GC) gc), (MutableImage) image);
//		displayGraphics.setColor(0x00000000);
        displayGraphics.setClip(0, 0, width, height);
        displayGraphics.translate(-displayGraphics.getTranslateX(), -displayGraphics.getTranslateY());

        return displayGraphics;        
    }
    
    @Override
    public void setRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height, final Image image) {
    
        image.setRGB2(argb, offset, scanlength, x, y, width, height);

    }
    
}
