package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class VerticalAlignmentFactory {

    private static final VerticalAlignmentFactory instance = new VerticalAlignmentFactory();
    
    /**
     * @return the instance
     */
    public static VerticalAlignmentFactory getInstance() {
        return VerticalAlignmentFactory.instance;
    }

    public final BasicArrayList VERTICAL_ALIGNMENT_LIST = new BasicArrayListD();
    
    public VerticalAlignment fromValue(String v) {
        final int size = this.VERTICAL_ALIGNMENT_LIST.size();
        VerticalAlignment c;
        for (int index = 0; index < size; index++) {
            c = (VerticalAlignment) this.VERTICAL_ALIGNMENT_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
