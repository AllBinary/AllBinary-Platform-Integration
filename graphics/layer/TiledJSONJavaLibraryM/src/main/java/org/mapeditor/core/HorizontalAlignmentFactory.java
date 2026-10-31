package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class HorizontalAlignmentFactory {

    private static final HorizontalAlignmentFactory instance = new HorizontalAlignmentFactory();
    
    /**
     * @return the instance
     */
    public static HorizontalAlignmentFactory getInstance() {
        return HorizontalAlignmentFactory.instance;
    }

    public final BasicArrayList HORIZONTAL_ALIGNMENT_LIST = new BasicArrayListD();
    
    public HorizontalAlignment fromValue(String v) {
        final int size = this.HORIZONTAL_ALIGNMENT_LIST.size();
        HorizontalAlignment c;
        for (int index = 0; index < size; index++) {
            c = (HorizontalAlignment) this.HORIZONTAL_ALIGNMENT_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
