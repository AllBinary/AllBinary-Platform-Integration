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
import javax.microedition.lcdui.Graphics;

import org.allbinary.device.GraphicsInterface;

/**
 *
 * @author User
 */
public class FontDebugFactoryBase {

    public final FontDebugBase DEBUG;
    public final FontDebugBase NO_DEBUG;
    
    public FontDebugFactoryBase(FontDebugBase debug, FontDebugBase noDebug) {
        this.DEBUG = debug;
        this.NO_DEBUG = noDebug;
    }
    
    public void setFont(final Font font, final Graphics graphics) {
        final GraphicsInterface graphicsInterface = (GraphicsInterface) graphics;
        graphicsInterface.setFontDebug(font, NO_DEBUG);
    }
}