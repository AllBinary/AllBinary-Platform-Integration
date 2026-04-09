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

import org.allbinary.string.CommonSeps;
import org.allbinary.logic.string.StringUtil;

/**
 *
 * @author User
 */
public class TiledJSONUtil {
    //protected final LogUtil logUtil = LogUtil.getInstance();

    
    private static final TiledJSONUtil instance = new TiledJSONUtil();

    /**
     * @return the instance
     */
    public static TiledJSONUtil getInstance() {
        return instance;
    }
    
    private final String BEFORE_HEIGHT_0 = "{ \n\"compressionlevel\":-1,\n \"height\":";
    
    private final String BEFORE_DATA_1 = ",\n \"infinite\":false,\n \"layers\":[\n        {\n         \"compression\":\"\",\n         \"data\":\"";    
    
    private final String AFTER_HEIGHT_0 = "\",\n         \"encoding\":\"base64\",\n         \"height\":";

    private final String AFTER_WIDTH_1 = ",\n         \"id\":1,\n         \"name\":\"1\",\n         \"opacity\":1,\n         \"type\":\"tilelayer\",\n         \"visible\":true,\n         \"width\":";

    private final String AFTER_TILE_HEIGHT_2 = ",\n         \"x\":0,\n         \"y\":0\n        }],\n \"nextlayerid\":2,\n \"nextobjectid\":1,\n \"orientation\":\"orthogonal\",\n \"renderorder\":\"right-down\",\n \"tiledversion\":\"1.10.2\",\n \"tileheight\":";

    private final String AFTER_TILE_SET_3 = ",\n \"tilesets\":[\n        {\n         \"firstgid\":1,\n         \"source\":\"";

    private final String AFTER_TILE_WIDTH_4 = "\"\n        }],\n \"tilewidth\":";

    private final String AFTER_WIDTH_5 = ",\n \"type\":\"map\",\n \"version\":\"1.10\",\n \"width\":";

    private final String END = "\n}\n";

    public final String DEFAULT_TILE_SET = "atlas_tsj.tsj";

    public void append(final int width, final int height, final String tileSetFileName, final int tileHeight, final int tileWidth, final String dataAsString, final StringBuilder stringBuilder) {
        stringBuilder.append(this.BEFORE_HEIGHT_0);
        stringBuilder.append(height);
        stringBuilder.append(this.BEFORE_DATA_1);
        stringBuilder.append(dataAsString);
        stringBuilder.append(this.AFTER_HEIGHT_0);
        stringBuilder.append(height);
        stringBuilder.append(this.AFTER_WIDTH_1);
        stringBuilder.append(width);
        stringBuilder.append(this.AFTER_TILE_HEIGHT_2);
        stringBuilder.append(tileHeight);
        //stringBuilder.append(this.DEFAULT_TILE_HEIGHT);
        stringBuilder.append(this.AFTER_TILE_SET_3);
        //stringBuilder.append(this.DEFAULT_TILE_SET);
        stringBuilder.append(tileSetFileName);
        stringBuilder.append(this.AFTER_TILE_WIDTH_4);
        //stringBuilder.append(this.DEFAULT_TILE_WIDTH);
        stringBuilder.append(tileWidth);
        stringBuilder.append(this.AFTER_WIDTH_5);
        stringBuilder.append(width);
        stringBuilder.append(this.END);
    }

    public String generateJSONAsString(final int[][] mapData, final int tileWidth, final int tileHeight) {
        final StringBuilder stringBuilder = new StringBuilder();
        
        final int width = mapData[0].length;
        final int height = mapData.length;

        //final CommonStrings commonStrings = CommonStrings.getInstance();
        //logUtil.put(stringBuilder.append(width).append(commonSeps.COLON).append(height).toString(), this, commonStrings.PROCESS);
        
        final byte[] byteArray = new byte[width * height * 4];
        final int size = new GDJSONMapDataWriter().write(width, height, mapData, byteArray, stringBuilder);
        final byte[] encodeData = org.apache.xmlrpc.Base64.encode(byteArray);
        //stringBuilder.delete(0, stringBuilder.length());
        //logUtil.put(stringBuilder.append("size: ").append(size).toString(), this, commonStrings.PROCESS);

        final String dataAsString = new String(encodeData).replace(CommonSeps.getInstance().NEW_LINE, StringUtil.getInstance().EMPTY_STRING);
        stringBuilder.delete(0, stringBuilder.length());
        //public void append(final int width, final int height, final String tileSetFileName, final int tileHeight, final int tileWidth, final String dataAsString, final StringBuilder stringBuilder)
        this.append(width, height, this.DEFAULT_TILE_SET, tileWidth, tileHeight, dataAsString, stringBuilder);
        
        final String tiledAsString = stringBuilder.toString();
        //logUtil.put(tiledAsString, this, commonStrings.PROCESS);
        return tiledAsString;
    }
    
}
