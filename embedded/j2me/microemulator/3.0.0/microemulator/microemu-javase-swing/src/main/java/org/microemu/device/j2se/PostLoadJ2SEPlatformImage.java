/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.j2se;

import java.awt.Graphics2D;
import static javax.microedition.lcdui.Graphics.BASELINE;
import static javax.microedition.lcdui.Graphics.BOTTOM;
import static javax.microedition.lcdui.Graphics.HCENTER;
import static javax.microedition.lcdui.Graphics.LEFT;
import static javax.microedition.lcdui.Graphics.RIGHT;
import static javax.microedition.lcdui.Graphics.TOP;
import static javax.microedition.lcdui.Graphics.VCENTER;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.PostLoadPlatformImage;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author User
 */
public class PostLoadJ2SEPlatformImage extends PostLoadPlatformImage {
    //protected final LogUtil logUtil = LogUtil.getInstance();


    @Override
    public void drawImage(final Image image, final int x, final int y, int anchor, final javax.microedition.lcdui.Graphics g2, final Object g) {
        // logUtil.putF(commonStrings.START, this, "drawImage");
        
        int newx = x;
        int newy = y;

        if (anchor == 0) {
            anchor = javax.microedition.lcdui.Graphics.TOP | javax.microedition.lcdui.Graphics.LEFT;
        }

        if ((anchor & javax.microedition.lcdui.Graphics.RIGHT) != 0) {
            newx -= image.getWidth();
        } else if ((anchor & javax.microedition.lcdui.Graphics.HCENTER) != 0) {
            newx -= image.getWidth() / 2;
        }
        if ((anchor & javax.microedition.lcdui.Graphics.BOTTOM) != 0) {
            newy -= image.getHeight();
        } else if ((anchor & javax.microedition.lcdui.Graphics.VCENTER) != 0) {
            newy -= image.getHeight() / 2;
        }

        //J2SEMutableImage/J2SEImmutableImage
        final java.awt.Image awtImage = (java.awt.Image) image.getImage();
        ((Graphics2D) g).drawImage(awtImage, newx, newy, null);
        
    }

    @Override
    public void drawRegion(final Image image, final int x_src, final int y_src, final int width,
            final int height, final int transform, int x_dst, int y_dst, int anchor, final javax.microedition.lcdui.Graphics g2)
    {
        // may throw NullPointerException, this is ok
        if (x_src + width > image.getWidth() || y_src + height > image.getHeight() || width < 0 || height < 0 || x_src < 0 || y_src < 0)
            throw new IllegalArgumentException("Area out of Image");

        // this cannot be done on the same image we are drawing
        // check this if the implementation of getGraphics change so
        // as to return different Graphic Objects on each call to
        // getGraphics
        if (image.isMutable() && image.getGraphics() == g2)
            throw new IllegalArgumentException("Image is source and target");

        final java.awt.Image img = (java.awt.Image) image.getImage();

        java.awt.geom.AffineTransform t = new java.awt.geom.AffineTransform();

        int dW = width, dH = height;
        switch (transform) {
        case Sprite.TRANS_NONE: {
            break;
        }
        case Sprite.TRANS_ROT90: {
            t.translate((double) height, 0);
            t.rotate(Math.PI / 2);
            dW = height;
            dH = width;
            break;
        }
        case Sprite.TRANS_ROT180: {
            t.translate(width, height);
            t.rotate(Math.PI);
            break;
        }
        case Sprite.TRANS_ROT270: {
            t.translate(0, width);
            t.rotate(Math.PI * 3 / 2);
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
            t.translate((double) height, 0);
            t.rotate(Math.PI / 2);
            t.translate((double) width, 0);
            t.scale(-1, 1);
            dW = height;
            dH = width;
            break;
        }
        case Sprite.TRANS_MIRROR_ROT180: {
            t.translate(width, 0);
            t.scale(-1, 1);
            t.translate(width, height);
            t.rotate(Math.PI);
            break;
        }
        case Sprite.TRANS_MIRROR_ROT270: {
            t.rotate(Math.PI * 3 / 2);
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
            anchor = TOP | LEFT;
        }

        if ((anchor & 0x7f) != anchor || (anchor & BASELINE) != 0)
            badAnchor = true;

        if ((anchor & TOP) != 0) {
            if ((anchor & (VCENTER | BOTTOM)) != 0)
                badAnchor = true;
        } else if ((anchor & BOTTOM) != 0) {
            if ((anchor & VCENTER) != 0)
                badAnchor = true;
            else {
                y_dst -= dH - 1;
            }
        } else if ((anchor & VCENTER) != 0) {
            y_dst -= (dH - 1) >>> 1;
        } else {
            // no vertical anchor
            badAnchor = true;
        }

        // horizontal
        if ((anchor & LEFT) != 0) {
            if ((anchor & (HCENTER | RIGHT)) != 0)
                badAnchor = true;
        } else if ((anchor & RIGHT) != 0) {
            if ((anchor & HCENTER) != 0)
                badAnchor = true;
            else {
                x_dst -= dW - 1;
            }
        } else if ((anchor & HCENTER) != 0) {
            x_dst -= (dW - 1) >>> 1;
        } else {
            // no horizontal anchor
            badAnchor = true;
        }

        if (badAnchor)
            throw new IllegalArgumentException("Bad Anchor");

        final java.awt.Graphics2D g = ((J2SEDisplayGraphics) g2).g;
        
        final java.awt.geom.AffineTransform savedT = g.getTransform();
        
        g.translate(x_dst, y_dst);
        g.transform(t);

        g.drawImage(img, 0, 0, width, height, x_src, y_src, x_src + width, y_src + height, null);

        // return to saved
        g.setTransform(savedT);        
    }
    
}
