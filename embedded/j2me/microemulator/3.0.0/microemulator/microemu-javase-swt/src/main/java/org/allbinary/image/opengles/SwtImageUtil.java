/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.image.opengles;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;

import org.allbinary.logic.string.StringMaker;
import org.eclipse.swt.graphics.RGB;
import org.microemu.device.swt.SwtImmutableImage;
import org.microemu.device.swt.SwtMutableImage;

/**
 *
 * @author User
 */
public class SwtImageUtil {
 
    private static final SwtImageUtil instance = new SwtImageUtil();

    /**
     * @return the instance
     */
    public static SwtImageUtil getInstance() {
        return SwtImageUtil.instance;
    }
    
    public String toString(final javax.microedition.lcdui.Image image) {
        
        final Image swtImage;
        if(image.isMutable()) {
            final SwtMutableImage mutableImage = (SwtMutableImage) image;
            swtImage = mutableImage.image;
        } else {
            final SwtImmutableImage immutableImage = (SwtImmutableImage) image;
            swtImage = immutableImage.image;
        }
        
        return this.toString(swtImage);
    }

    public String toString(final Image swtImage) {
        final ImageData imageData = swtImage.getImageData();        
        return this.toString(imageData);
    }
    
    public String toString(final ImageData imageData) {
        final StringMaker stringMaker = new StringMaker();
        
        final PaletteData paletteData = imageData.palette;
        
        stringMaker.append("PaletteData - depth: ");
        stringMaker.appendint(imageData.depth);
        stringMaker.append(" transparentPixel: ");
        stringMaker.appendint(imageData.transparentPixel);
        stringMaker.append(" redMask: ");
        stringMaker.append(Integer.toHexString(paletteData.redMask));
        stringMaker.append(" redShift: ");
        stringMaker.appendint(paletteData.redShift);
        stringMaker.append(" greenMask: ");
        stringMaker.append(Integer.toHexString(paletteData.greenMask));
        stringMaker.append(" greenShift: ");
        stringMaker.appendint(paletteData.greenShift);
        stringMaker.append(" blueMask: ");
        stringMaker.append(Integer.toHexString(paletteData.blueMask));
        stringMaker.append(" blueShift: ");
        stringMaker.appendint(paletteData.blueShift);
        stringMaker.append(" isDirect: ");
        stringMaker.appendboolean(paletteData.isDirect);
        final RGB[] rgbArray = paletteData.colors;
        if(rgbArray != null) {
            stringMaker.append(" colors size: ");
            stringMaker.appendint(rgbArray.length);
        }
                
        return stringMaker.toString();
    }
}
