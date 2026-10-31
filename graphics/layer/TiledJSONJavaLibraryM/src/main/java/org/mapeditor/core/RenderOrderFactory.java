package org.mapeditor.core;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class RenderOrderFactory {

    private static final RenderOrderFactory instance = new RenderOrderFactory();
    
    /**
     * @return the instance
     */
    public static RenderOrderFactory getInstance() {
        return RenderOrderFactory.instance;
    }

    public final BasicArrayList RENDER_ORDER_LIST = new BasicArrayListD();
    
    public RenderOrder fromValue(String v) {
        final int size = this.RENDER_ORDER_LIST.size();
        RenderOrder c;
        for (int index = 0; index < size; index++) {
            c = (RenderOrder) this.RENDER_ORDER_LIST.get(index);
            if (c.value().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
}
