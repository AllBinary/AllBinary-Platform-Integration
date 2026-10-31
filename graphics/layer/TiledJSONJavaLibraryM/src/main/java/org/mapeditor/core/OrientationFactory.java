package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class OrientationFactory {

    private static final OrientationFactory instance = new OrientationFactory();
    
    /**
     * @return the instance
     */
    public static OrientationFactory getInstance() {
        return OrientationFactory.instance;
    }

    public final BasicArrayList ORIENTATION_LIST = new BasicArrayListD();
    
    public Orientation fromValue(String v) {
        final int size = this.ORIENTATION_LIST.size();
        Orientation c;
        for (int index = 0; index < size; index++) {
            c = (Orientation) this.ORIENTATION_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
