/*
* AllBinary Open License Version 1
* Copyright (c) 2011
* 
* By agreeing to this license you and any business entity you represent are
* legally bound to the AllBinary Open License Version 1 legal agreement.
* 
* You may obtain the AllBinary Open License Version 1 legal agreement from
* AllBinary or the root directory of AllBinary's AllBinary Platform repository.
* 
* Created By: Travis Berthelot
* 
*/
package javax.microedition.lcdui;

/**
 *
 * @author User
 */
public class PostLoadPlatformImage extends PlatformImage {

    @Override
    public Object getImage(final Object graphicsSurface2) {
        throw new RuntimeException();
    }
    
    @Override
    public int getWidth(final Object graphicsSurface2, final int width) {
        throw new RuntimeException();
    }

    @Override
    public int getHeight(final Object graphicsSurface2, final int height) {
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
    public void drawRegion(final Image image, final int x_src, final int y_src, final int width,
            final int height, final int transform, int x_dst, int y_dst, int anchor, final javax.microedition.lcdui.Graphics g)
    {
        throw new RuntimeException();
    }

    @Override
    public void getRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height, final Image image) {
    
        image.getRGB2(argb, offset, scanlength, x, y, width, height);

    }
    
}
