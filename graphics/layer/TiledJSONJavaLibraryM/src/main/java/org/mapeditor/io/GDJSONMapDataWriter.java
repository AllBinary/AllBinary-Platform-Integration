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

import org.allbinary.string.CommonStrings;

import org.allbinary.logic.communication.log.LogUtil;

/**
 *
 * @author User
 */
public class GDJSONMapDataWriter {
    protected final LogUtil logUtil = LogUtil.getInstance();

    
    private final int LAST_BYTE = 0x000000FF;
    
    public int write(final int width, final int height, final int[][] mapData, byte[] byteArray, final StringBuilder stringBuilder) {
        
        //final CommonStrings commonStrings = CommonStrings.getInstance();
        int index = 0;
        int gid;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                //stringBuilder.delete(0, stringBuilder.length());
                //logUtil.put(stringBuilder.append(x).append(commonSeps.COLON).append(y).toString(), this, commonStrings.PROCESS);
                
                gid = mapData[y][x];
                //gid |= tile.getFlagsAt(x, y);

                //outputStream.write(gid & LAST_BYTE);
                //outputStream.write(gid >> Byte.SIZE & LAST_BYTE);
                //outputStream.write(gid >> Byte.SIZE * 2 & LAST_BYTE);
                //outputStream.write(gid >> Byte.SIZE * 3 & LAST_BYTE);
                index = (((y * width) + x) * 4);
                byteArray[index] = (byte) (gid & this.LAST_BYTE);
                //byteArray[index++] = (byte) (gid & LAST_BYTE);
                //byteArray[index++] = (byte) (gid >> Byte.SIZE & LAST_BYTE);
                //byteArray[index++] = (byte) (gid >> Byte.SIZE * 2 & LAST_BYTE);
                //byteArray[index++] = (byte) (gid >> Byte.SIZE * 3 & LAST_BYTE);
            }
        }
        
        return index;
    }

    public int write(final byte[] mapData, byte[] byteArray) {

        int gid;
        final int size = mapData.length;
        for(int index = 0; index < size; index += 4) {
            gid = mapData[index];
            //gid |= tile.getFlagsAt(x, y);
            byteArray[index] = (byte) (gid & this.LAST_BYTE);
        }

        return size;
    }
    
}
