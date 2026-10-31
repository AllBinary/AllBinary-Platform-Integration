package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class PropertyTypeFactory {

    private static final PropertyTypeFactory instance = new PropertyTypeFactory();
    
    /**
     * @return the instance
     */
    public static PropertyTypeFactory getInstance() {
        return PropertyTypeFactory.instance;
    }

    public final BasicArrayList PROPERTY_TYPE_LIST = new BasicArrayListD();
    
    public PropertyType fromValue(String v) {
        final int size = this.PROPERTY_TYPE_LIST.size();
        PropertyType c;
        for (int index = 0; index < size; index++) {
            c = (PropertyType) this.PROPERTY_TYPE_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
