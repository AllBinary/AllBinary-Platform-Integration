/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
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
        return instance;
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
        stringMaker.append(imageData.depth);
        stringMaker.append(" transparentPixel: ");
        stringMaker.append(imageData.transparentPixel);
        stringMaker.append(" redMask: ");
        stringMaker.append(Integer.toHexString(paletteData.redMask));
        stringMaker.append(" redShift: ");
        stringMaker.append(paletteData.redShift);
        stringMaker.append(" greenMask: ");
        stringMaker.append(Integer.toHexString(paletteData.greenMask));
        stringMaker.append(" greenShift: ");
        stringMaker.append(paletteData.greenShift);
        stringMaker.append(" blueMask: ");
        stringMaker.append(Integer.toHexString(paletteData.blueMask));
        stringMaker.append(" blueShift: ");
        stringMaker.append(paletteData.blueShift);
        stringMaker.append(" isDirect: ");
        stringMaker.append(paletteData.isDirect);
        final RGB[] rgbArray = paletteData.colors;
        if(rgbArray != null) {
            stringMaker.append(" colors size: ");
            stringMaker.append(rgbArray.length);
        }
                
        return stringMaker.toString();
    }
}
