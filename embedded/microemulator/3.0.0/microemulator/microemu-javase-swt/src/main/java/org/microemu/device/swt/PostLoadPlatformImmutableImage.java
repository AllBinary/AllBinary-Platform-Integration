/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

import javax.microedition.lcdui.Image;

/**
 *
 * @author User
 */
public class PostLoadPlatformImmutableImage extends PostLoadSwtPlatformImage {

//    private static final PostLoadPlatformImmutableImage instance = new PostLoadPlatformImmutableImage();
//
//    public static PostLoadPlatformImmutableImage getInstance() {
//        return instance;
//    }
    
    private int height = -1;
    private int width = -1;
    
    @Override
    public int getWidth(final Object image, final int width) {
        
        if(this.width == -1) {
            this.width = ((org.eclipse.swt.graphics.Image) image).getBounds().width;
        }

        return this.width;
    }

    @Override
    public int getHeight(final Object image, final int height) {
        
        if(this.height == -1) {
            this.height = ((org.eclipse.swt.graphics.Image) image).getBounds().height;
        }

        return this.height;
    }

    @Override
    public void setRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height, final Image image) {
    
        image.setRGB2(argb, offset, scanlength, x, y, width, height);

    }
    
}
