/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.PostLoadPlatformImage;
import javax.microedition.lcdui.game.Sprite;

import org.eclipse.swt.graphics.Transform;

import org.microemu.app.BareMain;
import org.microemu.app.ui.swt.SwtGraphics;

/**
 *
 * @author User
 */
public class PostLoadSwtPlatformImage extends PostLoadPlatformImage {
    //protected final LogUtil logUtil = LogUtil.getInstance();


    @Override
    public void drawImage(final Image image, final int x, final int y, int anchor, final javax.microedition.lcdui.Graphics g2, final Object g) {
        // logUtil.putF(commonStrings.START, this, "drawImage");
        
        int newx = x;
        int newy = y;

        if (anchor == 0) {
            anchor = Graphics.TOP | Graphics.LEFT;
        }

        if ((anchor & Graphics.RIGHT) != 0) {
            newx -= image.getWidth();
        } else if ((anchor & Graphics.HCENTER) != 0) {
            newx -= image.getWidth() / 2;
        }
        if ((anchor & Graphics.BOTTOM) != 0) {
            newy -= image.getHeight();
        } else if ((anchor & Graphics.VCENTER) != 0) {
            newy -= image.getHeight() / 2;
        }

        //J2SEMutableImage/J2SEImmutableImage
        final org.eclipse.swt.graphics.Image swtImage = (org.eclipse.swt.graphics.Image) image.getImage();
        ((SwtGraphics) g).drawImage(swtImage, newx, newy);
        
    }
    
    @Override
    public void drawRegion(final Image image, final int x_src, final int y_src, final int width,
        final int height, final int transform, int x_dst, int y_dst, int anchor, final javax.microedition.lcdui.Graphics g2) {

        // may throw NullPointerException, this is ok
        if (x_src + width > image.getWidth() || y_src + height > image.getHeight() || width < 0 || height < 0 || x_src < 0 || y_src < 0) {
            final String message = new StringBuffer().append("Area out of Image: ").append(x_src + width).append(">").append(image.getWidth()).append("||").append(y_src + height).append(">").append(image.getHeight()).append("||").append(width).append("<0 ||").append(height).append("< 0 ||").append(x_src).append("< 0 ||").append(y_src).append("< 0").toString();
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }

        // this cannot be done on the same image we are drawing
        // check this if the implementation of getGraphics change so
        // as to return different Graphic Objects on each call to
        // getGraphics
        if (image.isMutable() && image.getGraphics() == g2) {
            final String message = "Image is source and target";
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }

        org.eclipse.swt.graphics.Image img;
        if (image.isMutable()) {
            final SwtMutableImage swtImage = (SwtMutableImage) image;
            img = (org.eclipse.swt.graphics.Image) swtImage.getImage();
        } else {
            final SwtImmutableImage swtImage = (SwtImmutableImage) image;
            //X cannot be cast to class java.awt.image.org.eclipse.swt.graphics.Image
            img = (org.eclipse.swt.graphics.Image) swtImage.getImage();
        }

        //java.awt.geom.AffineTransform t = new java.awt.geom.AffineTransform();
        //final Transform t = new Transform(BareMain.gc.getDevice());
        final Transform t = new Transform(BareMain.shell.getDisplay());

        int dW = width, dH = height;
        switch (transform) {
            case Sprite.TRANS_NONE: {
                break;
            }
            case Sprite.TRANS_ROT90: {
                t.translate((float) height, 0);
                t.rotate((float) Math.PI / 2);
                dW = height;
                dH = width;
                break;
            }
            case Sprite.TRANS_ROT180: {
                t.translate(width, height);
                t.rotate((float) Math.PI);
                break;
            }
            case Sprite.TRANS_ROT270: {
                t.translate(0, width);
                t.rotate((float) Math.PI * 3 / 2);
                dW = height;
                dH = width;
                break;
            }
            case Sprite.TRANS_MIRROR: {
                t.translate(width, 0);
                t.scale(-1, 1);
                break;
            }
            case Sprite.TRANS_MIRROR_ROT90: {
                t.translate((float) height, 0);
                t.rotate((float) Math.PI / 2);
                t.translate((float) width, 0);
                t.scale(-1, 1);
                dW = height;
                dH = width;
                break;
            }
            case Sprite.TRANS_MIRROR_ROT180: {
                t.translate(width, 0);
                t.scale(-1, 1);
                t.translate(width, height);
                t.rotate((float) Math.PI);
                break;
            }
            case Sprite.TRANS_MIRROR_ROT270: {
                t.rotate((float) Math.PI * 3 / 2);
                t.scale(-1, 1);
                dW = height;
                dH = width;
                break;
            }
            default:
                throw new IllegalArgumentException("Bad transform");
        }

        // process anchor and correct x and y _dest
        // vertical
        boolean badAnchor = false;

        if (anchor == 0) {
            anchor = Graphics.TOP | Graphics.LEFT;
        }

        if ((anchor & 0x7f) != anchor || (anchor & Graphics.BASELINE) != 0) {
            badAnchor = true;
        }

        if ((anchor & Graphics.TOP) != 0) {
            if ((anchor & (Graphics.VCENTER | Graphics.BOTTOM)) != 0) {
                badAnchor = true;
            }
        } else if ((anchor & Graphics.BOTTOM) != 0) {
            if ((anchor & Graphics.VCENTER) != 0) {
                badAnchor = true;
            } else {
                y_dst -= dH - 1;
            }
        } else if ((anchor & Graphics.VCENTER) != 0) {
            y_dst -= (dH - 1) >>> 1;
        } else {
            // no vertical anchor
            badAnchor = true;
        }

        // horizontal
        if ((anchor & Graphics.LEFT) != 0) {
            if ((anchor & (Graphics.HCENTER | Graphics.RIGHT)) != 0) {
                badAnchor = true;
            }
        } else if ((anchor & Graphics.RIGHT) != 0) {
            if ((anchor & Graphics.HCENTER) != 0) {
                badAnchor = true;
            } else {
                x_dst -= dW - 1;
            }
        } else if ((anchor & Graphics.HCENTER) != 0) {
            x_dst -= (dW - 1) >>> 1;
        } else {
            // no horizontal anchor
            badAnchor = true;
        }

        if (badAnchor) {
            throw new IllegalArgumentException("Bad Anchor");
        }

        final SwtGraphics g = ((SwtDisplayGraphics) g2).g;
        g.drawImage(img, x_src, y_src, width, height, x_dst, y_dst, width, height);

    }

}
