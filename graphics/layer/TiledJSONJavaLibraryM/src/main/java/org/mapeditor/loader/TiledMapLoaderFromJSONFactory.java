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
package org.mapeditor.loader;

import java.io.InputStream;

import org.allbinary.logic.io.InputStreamUtil;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.LogFactory;
import org.allbinary.logic.communication.log.LogUtil;

import org.json.me.JSONObject;
import org.json.me.JSONTokener;
//import org.json.JSONObject;
//import org.json.JSONTokener;
import org.mapeditor.core.TiledMap;
import org.mapeditor.io.GDJSONMapReader;

public class TiledMapLoaderFromJSONFactory {
    protected final LogUtil logUtil = LogUtil.getInstance();


    private static final TiledMapLoaderFromJSONFactory instance = new TiledMapLoaderFromJSONFactory();

    /**
     * @return the instance
     */
    public static TiledMapLoaderFromJSONFactory getInstance() {
        return instance;
    }
    
    public TiledMap process(final GDJSONMapReader mapReader, final InputStream tileMapInputStream, final InputStream[] tileSetInputStreamArray, final int size, final int[] sizeArray2, final int[] tileSetImageHeightArray) {

        final CommonStrings commonStrings = CommonStrings.getInstance();

        TiledMap map = null;
        try {
            
            //logUtil.put("Loading Tiled Map available: " + inputStream.available(), this, commonStrings.PROCESS);
            final byte[] byteArray = new byte[262144];
            
            final JSONObject tileMapJSONObject = this.getJSONAsString(tileMapInputStream, byteArray, size);
            final JSONObject[] tileSetJSONObjectArray = new JSONObject[tileSetInputStreamArray.length];
            
            final int size3 = tileSetJSONObjectArray.length;
            for(int index = 0; index < size3; index++) {
                tileSetJSONObjectArray[index] = this.getJSONAsString(tileSetInputStreamArray[index], byteArray, sizeArray2[index]);
            }
            
            //logUtil.put("Loading Tiled Map JSON", this, commonStrings.PROCESS);
            
            map = mapReader.buildMap(tileMapJSONObject, tileSetJSONObjectArray, tileSetImageHeightArray);
            
            //logUtil.put("Loading Tiled Map BuildMap", this, commonStrings.PROCESS);
            
            return map;

        } catch (Exception e) {
            logUtil.put(commonStrings.EXCEPTION, this, commonStrings.PROCESS, e);
            return null;
        }

    }
    
    private JSONObject getJSONAsString(final InputStream inputStream, final byte[] byteArray, final int size) throws Exception {

        if(inputStream == null) {
            return null;
        }

        final InputStreamUtil streamUtil = InputStreamUtil.getInstance();
        final int len = streamUtil.get(inputStream, byteArray, size);
        final String jsonAsString = new String(byteArray, 0, len);

        //logUtil.put("Loading Tiled Map String: " + gameAsConfiguration.length(), this, commonStrings.PROCESS);
        final JSONTokener jsonTokener = new JSONTokener(jsonAsString);
        return (JSONObject) jsonTokener.nextValue();
    }
}
