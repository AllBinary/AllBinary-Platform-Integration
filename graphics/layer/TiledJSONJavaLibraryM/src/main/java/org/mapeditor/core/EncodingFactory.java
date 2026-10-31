package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class EncodingFactory {

    private static final EncodingFactory instance = new EncodingFactory();
    
    /**
     * @return the instance
     */
    public static EncodingFactory getInstance() {
        return EncodingFactory.instance;
    }

    public final BasicArrayList ENCODING_LIST = new BasicArrayListD();
    
    public Encoding fromValue(String v) {
        final int size = this.ENCODING_LIST.size();
        Encoding c;
        for (int index = 0; index < size; index++) {
            c = (Encoding) this.ENCODING_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
