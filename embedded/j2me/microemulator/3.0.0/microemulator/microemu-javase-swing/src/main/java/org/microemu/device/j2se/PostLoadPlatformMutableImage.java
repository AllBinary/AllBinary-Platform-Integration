/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.j2se;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.microedition.lcdui.Image;

/**
 *
 * @author User
 */
public class PostLoadPlatformMutableImage extends PostLoadJ2SEPlatformImage {
    //protected final LogUtil logUtil = LogUtil.getInstance();


    private static final PostLoadPlatformMutableImage instance = new PostLoadPlatformMutableImage();

    /**
     * @return the instance
     */
    public static PostLoadPlatformMutableImage getInstance() {
        return instance;
    }
    
    @Override
    public javax.microedition.lcdui.Graphics getGraphics(final Object graphicsSurface2, final int width, final int height, final Image image) {

        final J2SEGraphicsSurface graphicsSurface = (J2SEGraphicsSurface) graphicsSurface2;
        final Graphics2D g = graphicsSurface.getGraphics();
        g.setTransform(new AffineTransform());
        g.setClip(0, 0, width, height);
        final J2SEDisplayGraphics displayGraphics = new J2SEDisplayGraphics(graphicsSurface);
        displayGraphics.setColor(0x00000000);
        displayGraphics.translate(-displayGraphics.getTranslateX(), -displayGraphics.getTranslateY());

        return displayGraphics;

    }

        //java.awt.Image
        @Override
	public Object getImage(final Object graphicsSurface)
	{
		return ((J2SEGraphicsSurface) graphicsSurface).getImage();
	}

        @Override
	public int getWidth(final Object graphicsSurface, final int width) {
            
            //logUtil.putF("((J2SEGraphicsSurface) graphicsSurface).getImage().getWidth()" + ((J2SEGraphicsSurface) graphicsSurface).getImage().getWidth(), this, "((J2SEGraphicsSurface) graphicsSurface).getImage().getWidth()");
		return ((J2SEGraphicsSurface) graphicsSurface).getImage().getWidth();
	}

        @Override
	public int getHeight(final Object graphicsSurface, final int height) {
            
            //logUtil.putF("((J2SEGraphicsSurface) graphicsSurface).getImage().getHeight()" + ((J2SEGraphicsSurface) graphicsSurface).getImage().getHeight(), this, "((J2SEGraphicsSurface) graphicsSurface).getImage().getHeight()");
		return ((J2SEGraphicsSurface) graphicsSurface).getImage().getHeight();
	}
        
        @Override
	public int[] getData(final Image image)
	{
            return image.getData2();
	}

}
