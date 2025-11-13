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
public class FontDebug extends FontDebugBase {
    protected final LogUtil logUtil = LogUtil.getInstance();


    /**
     * @return the instance
     */
    public static FontDebug getInstance() {
        return instance;
    }

    private static final FontDebug instance = new FontDebug();
    
    @Override
    public void debugMetrics(final Font currentFont, final float convertedTextSize, final Object displayMetrics2, final StringMaker stringBuffer) {

        final DisplayMetrics displayMetrics = (DisplayMetrics) displayMetrics2;
        final String message = stringBuffer.append("Font size: ").append(currentFont.getSize())
                .append(" scaledDensity: ").append(displayMetrics.scaledDensity)
                .append(" xpdi: ").append(displayMetrics.xdpi)
                .append(" ypdi: ").append(displayMetrics.ydpi)
                .append(" textSize Before: ").append(convertedTextSize).toString();
        logUtil.put(message, this, SET_FONT);
    }

    @Override
    public void debugDpi(final int densityDpi, final StringMaker stringBuffer) {
        final String message = stringBuffer.append("densityDpi: ")
                .append(densityDpi)
                .toString();
        logUtil.put(message, this, SET_FONT);
    }

    @Override
    public void debugDimension(final Font currentFont, final float convertedTextSize, final int longestDimensionTotalPixels, final StringMaker stringBuffer) {
        stringBuffer.delete(0, stringBuffer.length());
        final String message = stringBuffer.append("Font size: ").append(currentFont.getSize())
                .append(" converted: ").append(convertedTextSize)
                .append(" longwaysPixels width/height: ").append(longestDimensionTotalPixels).toString();
        logUtil.put(message, this, SET_FONT);
    }

}
