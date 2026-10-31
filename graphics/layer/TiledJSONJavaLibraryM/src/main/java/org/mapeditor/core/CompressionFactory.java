/*
 * AllBinary Open License Version 1
 * Copyright (c) 2026 AllBinary
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
package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class CompressionFactory {

    private static final CompressionFactory instance = new CompressionFactory();
    
    /**
     * @return the instance
     */
    public static CompressionFactory getInstance() {
        return CompressionFactory.instance;
    }

    public final BasicArrayList COMPRESSION_LIST = new BasicArrayListD();
    
    public Compression fromValue(String v) {
        final int size = this.COMPRESSION_LIST.size();
        Compression c;
        for (int index = 0; index < size; index++) {
            c = (Compression) this.COMPRESSION_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
