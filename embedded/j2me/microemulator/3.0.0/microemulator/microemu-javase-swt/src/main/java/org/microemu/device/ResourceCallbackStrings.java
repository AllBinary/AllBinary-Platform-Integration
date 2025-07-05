/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device;

/**
 *
 * @author User
 */
public class ResourceCallbackStrings {
    
    protected static final ResourceCallbackStrings instance = new ResourceCallbackStrings();
    
    /**
     * @return the instance
     */
    public static ResourceCallbackStrings getInstance() {
        return instance;
    }
        
    public final String FROM_DATA = "FromData";
    public final String INPUT_STREAM = "InputStream";
    
}
