package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class StaggerIndexFactory {

    private static final StaggerIndexFactory instance = new StaggerIndexFactory();
    
    /**
     * @return the instance
     */
    public static StaggerIndexFactory getInstance() {
        return StaggerIndexFactory.instance;
    }

    public final BasicArrayList STAGGER_INDEX_LIST = new BasicArrayListD();
    
    public StaggerIndex fromValue(String v) {
        final int size = this.STAGGER_INDEX_LIST.size();
        StaggerIndex c;
        for (int index = 0; index < size; index++) {
            c = (StaggerIndex) this.STAGGER_INDEX_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
