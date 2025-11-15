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

/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.microedition.io;

import java.security.PrivilegedExceptionAction;

import org.allbinary.logic.NullUtil;

/**
 *
 * @author User
 */
public class PrivilegedExceptionActionImpl implements PrivilegedExceptionAction<Object> {
    
    @Override
    public Object run() throws Exception {
        return NullUtil.getInstance().NULL_OBJECT;
    }

}
