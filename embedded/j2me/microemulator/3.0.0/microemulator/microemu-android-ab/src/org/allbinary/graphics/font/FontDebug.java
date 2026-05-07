/*
* AllBinary Open License Version 1
* Copyright (c) 2011 AllBinary
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
package org.allbinary.graphics.font;

import javax.microedition.lcdui.Font;

import android.util.DisplayMetrics;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.logic.string.StringMaker;

/**
 *
 * @author User
 */
//microemu-android-ab
public class FontDebug extends FontDebugBase {
    protected final LogUtil logUtil = LogUtil.getInstance();


    /**
     * @return the instance
     */
    public static FontDebug getInstance() {
        return FontDebug.instance;
    }

    private static final FontDebug instance = new FontDebug();
    
    @Override
    public void debugMetrics(final Font currentFont, final float convertedTextSize, final Object displayMetrics2, final StringMaker stringBuffer) {

        final DisplayMetrics displayMetrics = (DisplayMetrics) displayMetrics2;
        final String message = stringBuffer.append("Font size: ").appendint(currentFont.getSize())
                .append(" scaledDensity: ").appendfloat(displayMetrics.scaledDensity)
                .append(" xpdi: ").appendfloat(displayMetrics.xdpi)
                .append(" ypdi: ").appendfloat(displayMetrics.ydpi)
                .append(" textSize Before: ").appendfloat(convertedTextSize).toString();
        this.logUtil.putF(message, this, this.SET_FONT);
    }

    @Override
    public void debugDpi(final int densityDpi, final StringMaker stringBuffer) {
        final String message = stringBuffer.append("densityDpi: ")
                .appendint(densityDpi)
                .toString();
        this.logUtil.putF(message, this, this.SET_FONT);
    }

    @Override
    public void debugDimension(final Font currentFont, final float convertedTextSize, final int longestDimensionTotalPixels, final StringMaker stringBuffer) {
        stringBuffer.delete(0, stringBuffer.length());
        final String message = stringBuffer.append("Font size: ").appendint(currentFont.getSize())
                .append(" converted: ").appendfloat(convertedTextSize)
                .append(" longwaysPixels width/height: ").appendint(longestDimensionTotalPixels).toString();
        this.logUtil.putF(message, this, this.SET_FONT);
    }

}
