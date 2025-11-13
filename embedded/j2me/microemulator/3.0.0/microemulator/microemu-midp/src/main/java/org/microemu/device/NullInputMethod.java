/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2025
 * Created By: Travis Berthelot
 */
package org.microemu.device;

import org.allbinary.logic.string.StringUtil;

/**
 *
 * @author User
 */
public class NullInputMethod extends InputMethod {
    
    public static final NullInputMethod NULL_INPUT_METHOD = new NullInputMethod();
    
    @Override
    public void dispose() {
        
    }

    @Override
    public int getGameAction(int keyCode) {
        return -1;
    }

    @Override
    public int getKeyCode(int gameAction) {
        return -1;
    }

    @Override
    public String getKeyName(int keyCode) throws IllegalArgumentException {
        return StringUtil.getInstance().EMPTY_STRING;
    }
    
}
