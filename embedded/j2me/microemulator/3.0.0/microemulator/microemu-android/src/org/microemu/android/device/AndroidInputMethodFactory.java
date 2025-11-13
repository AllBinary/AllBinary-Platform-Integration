/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2025
 * Created By: Travis Berthelot
 */
package org.microemu.android.device;

/**
 *
 * @author User
 */
public class AndroidInputMethodFactory {
    
    private static final AndroidInputMethodFactory instance = new AndroidInputMethodFactory();

    /**
     * @return the instance
     */
    public static AndroidInputMethodFactory getInstance() {
        return instance;
    }
    
    public final AndroidInputMethod ANDROID_INPUT_METHOD = new AndroidInputMethod();
    
}
