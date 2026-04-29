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

/**
 *
 * @author User
 */
public class FontDebugFactory extends FontDebugFactoryBase {
    
    private static final FontDebugFactory instance = new FontDebugFactory(FontDebug.getInstance(), FontDebugBase.getInstance());

    /**
     * @return the instance
     */
    public static FontDebugFactory getInstance() {
        return FontDebugFactory.instance;
    }

    public FontDebugFactory(FontDebugBase debug, FontDebugBase noDebug) {
        super(debug, noDebug);
    }

}
