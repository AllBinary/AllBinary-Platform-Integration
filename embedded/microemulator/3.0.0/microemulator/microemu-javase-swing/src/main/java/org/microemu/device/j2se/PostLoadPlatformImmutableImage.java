/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Created By: Travis Berthelot
 */
package org.microemu.device.j2se;

import javax.microedition.lcdui.Image;

/**
 *
 * @author User
 */
public class PostLoadPlatformImmutableImage extends PostLoadJ2SEPlatformImage {

    private static final PostLoadPlatformImmutableImage instance = new PostLoadPlatformImmutableImage();

    /**
     * @return the instance
     */
    public static PostLoadPlatformImmutableImage getInstance() {
        return instance;
    }

    @Override
    public int getWidth(final Image image, final int width) {
        //LogUtil.put(LogFactory.getInstance("image.getWidth2()" + image.getWidth2(), this, "image.getWidth2()"));
        return image.getWidth2();
    }
    
    @Override
    public int getHeight(final Image image, final int height) {
        //LogUtil.put(LogFactory.getInstance("image.getHeight2()" + image.getHeight2(), this, "image.getHeight2()"));
        return image.getHeight2();
    }

    @Override
    public void getRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height, final Image image) {
    
        image.getRGB2(argb, offset, scanlength, x, y, width, height);

    }
    
}
