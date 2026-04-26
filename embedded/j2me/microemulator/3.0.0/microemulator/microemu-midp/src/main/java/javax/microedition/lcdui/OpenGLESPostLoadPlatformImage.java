/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package javax.microedition.lcdui;

/**
 *
 * @author User
 */
public class OpenGLESPostLoadPlatformImage extends PlatformImage {
    //protected final LogUtil logUtil = LogUtil.getInstance();


    private static final OpenGLESPostLoadPlatformImage instance = new OpenGLESPostLoadPlatformImage();

    /**
     * @return the instance
     */
    public static OpenGLESPostLoadPlatformImage getInstance() {
        return instance;
    }
    
    @Override
    public Object getImage(final Object graphicsSurface2) {
        throw new RuntimeException();
    }
    
    @Override
    public int getSurfaceWidth(final Object graphicsSurface2, final int width) {
        throw new RuntimeException();
    }

    @Override
    public int getSurfaceHeight(final Object graphicsSurface2, final int height) {
        throw new RuntimeException();
    }
    
    @Override
    public int getWidth(final Image image, final int width) {
        throw new RuntimeException();
    }

    @Override
    public int getHeight(final Image image, final int height) {
        throw new RuntimeException();
    }
    
    @Override
    public void drawImage(final Image image, final int x, final int y, int anchor, final javax.microedition.lcdui.Graphics g2, final Object g) {
        // logUtil.putF(commonStrings.START, this, "drawImage");
        g2.drawImage(image, x, y, anchor);
    }

    @Override
    public void drawRegion(final Image image, final int x_src, final int y_src, final int width,
            final int height, final int transform, final int x_dst, final int y_dst, final int anchor, final javax.microedition.lcdui.Graphics g)
    {
        g.drawRegion(image, x_src, y_src, width, height, transform, x_dst, y_dst, anchor);
    }

    @Override
    public void getRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height, final Image image) {
    
        throw new RuntimeException();
    }
    
}
