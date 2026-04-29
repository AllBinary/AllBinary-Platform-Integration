/*
 * AllBinary Open License Version 1
 * Copyright (c) 2022 AllBinary
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
package org.mapeditor.io;

/**
 *
 * @author User
 */
public class TiledBehaviorFactory {

    private static final TiledBehaviorFactory instance = new TiledBehaviorFactory();

    /**
     * @return the instance
     */
    public static TiledBehaviorFactory getInstance() {
        return TiledBehaviorFactory.instance;
    }
    
    private final String TILED_1_4 = "1.4";
    private final String TILED_1_9 = "1.9";
    private final String TILED_1_10 = "1.1";
    
    public String get(final String version) {
        if(version.compareTo(this.TILED_1_10) == 0) {
            return this.TILED_1_10;
        } else if(version.compareTo(this.TILED_1_9) == 0) {
            return this.TILED_1_9;
        } else if(version.compareTo(this.TILED_1_4) == 0) {
            return this.TILED_1_4;
        }
        throw new RuntimeException(version);
    }
    
    public TiledBehavior getBehavior(final String version) {
        if(version == this.TILED_1_10) {
            return TiledBehavior_1_1X.getInstance();
        } else if(version == this.TILED_1_9) {
            return TiledBehavior_1_9.getInstance();
        } else if(version == this.TILED_1_4) {
            return TiledBehavior_1_4_2.getInstance();
        }
        throw new RuntimeException(version);
    }
}
