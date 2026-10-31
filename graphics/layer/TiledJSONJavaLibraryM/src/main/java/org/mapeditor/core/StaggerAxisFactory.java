package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class StaggerAxisFactory {

    private static final StaggerAxisFactory instance = new StaggerAxisFactory();
    
    /**
     * @return the instance
     */
    public static StaggerAxisFactory getInstance() {
        return StaggerAxisFactory.instance;
    }

    public final BasicArrayList STAGGER_AXIS_LIST = new BasicArrayListD();
    
    public StaggerAxis fromValue(String v) {
        final int size = this.STAGGER_AXIS_LIST.size();
        StaggerAxis c;
        for (int index = 0; index < size; index++) {
            c = (StaggerAxis) this.STAGGER_AXIS_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
