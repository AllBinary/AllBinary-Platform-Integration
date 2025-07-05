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

import org.allbinary.logic.string.StringMaker;

/**
 *
 * @author User
 */
public class TiledBehavior_1_9 extends TiledBehavior {
    
    private static final TiledBehavior_1_9 instance = new TiledBehavior_1_9();

    /**
     * @return the instance
     */
    public static TiledBehavior_1_9 getInstance() {
        return instance;
    }

    @Override
    public int getTileId0(final int tileId, final int nextInt, final StringMaker stringBuilder) {
        
        if (nextInt != 0) {
            stringBuilder.append(this.mapReaderData.NEXT_INT_0).append(nextInt);
        }
        
        return nextInt | tileId;
    }

    @Override
    public int getTileId1(final int tileId, final int nextInt, final StringMaker stringBuilder) {

        if (nextInt != 0) {
            stringBuilder.append(this.mapReaderData.NEXT_INT_1).append(nextInt);
        }

        return tileId;
    }

    @Override
    public int getTileId2(final int tileId, final int nextInt, final StringMaker stringBuilder) {

        if (nextInt != 0) {
            stringBuilder.append(this.mapReaderData.NEXT_INT_2).append(nextInt);
        }

        return tileId;
    }

    @Override
    public int getTileId3(final int tileId, final int nextInt, final StringMaker stringBuilder) {

        if (nextInt != 0) {
            stringBuilder.append(this.mapReaderData.NEXT_INT_3).append(nextInt);
        }

        return tileId;
    }
    
}
