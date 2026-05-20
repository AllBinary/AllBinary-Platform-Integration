/*
 * AllBinary Open License Version 1
 * Copyright (c) 2025 AllBinary
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

import org.allbinary.logic.string.StringUtil;

/**
 *
 * @author User
 */
public class NullScreen extends Screen {
    
    public NullScreen() {
        super(StringUtil.getInstance().EMPTY_STRING);
    }

    @Override
    public int traverse(int gameKeyCode, int top, int bottom) {
        throw new RuntimeException();
    }
    
    @Override
    public int paintContent(Graphics g) {
        throw new RuntimeException();
    }
    
}
