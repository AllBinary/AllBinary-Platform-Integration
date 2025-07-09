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
package org.allbinary.media.graphics.geography.map.platform;

import java.util.Hashtable;
import org.allbinary.logic.communication.log.LogFactory;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonSeps;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.util.BasicArrayList;
import org.mapeditor.core.ObjectGroupData;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileSet;

/**
 *
 * @author User
 */
public class TileSetToGeographicMapUtil {
    protected final LogUtil logUtil = LogUtil.getInstance();

    
    private static final TileSetToGeographicMapUtil instance = new TileSetToGeographicMapUtil();

    /**
     * @return the instance
     */
    public static TileSetToGeographicMapUtil getInstance() {
        return instance;
    }

    private final String OTHER = "Other";
    
    public Hashtable convert(final TileSet tileSet) {
        final StringMaker stringMaker = 
                null;
                //new StringMaker();
        final Hashtable map = new Hashtable();
        final int tileCount = tileSet.getTilecount();
        Tile tile;
        BasicArrayList objectGroupList;
        ObjectGroupData objectGroupData;
        
        //stringMaker.delete(0, stringMaker.length());
        //logUtil.put(stringMaker.append("tileCount: ").append(tileCount).toString(), this, CommonStrings.getInstance().PROCESS);

        for(int index = 0; index < tileCount; index++) {
            
            //stringMaker.delete(0, stringMaker.length());
            //logUtil.put(stringMaker.append("tile index: ").append(index).toString(), this, CommonStrings.getInstance().PROCESS);
            
            tile = tileSet.getTile(index);
            objectGroupList = tile.getObjectgroup();
            final int size2 = objectGroupList.size();
            
            if(size2 > 0) {
                for (int index2 = 0; index2 < size2; index2++) {
                    objectGroupData = (ObjectGroupData) objectGroupList.get(index2);
                    this.add(map, objectGroupData.getName(), objectGroupData.getId() + 1, stringMaker);
                }
            } else {
                //logUtil.put(OTHER, this, CommonStrings.getInstance().PROCESS);
                this.add(map, OTHER, index + 1, stringMaker);
            }

        }
        
        return map;
    }
    
    protected void add(final Hashtable map, final String name, final int id, final StringMaker stringMaker) {
        
        //stringMaker.delete(0, stringMaker.length());
        //logUtil.put(stringMaker.append(name).append(CommonSeps.getInstance().EQUALS).append(id).toString(), this, CommonStrings.getInstance().PROCESS);

        BasicArrayList idsWithTypeList = (BasicArrayList) map.get(name);
        if (idsWithTypeList == null) {
            idsWithTypeList = new BasicArrayList();
            idsWithTypeList.add(id);
            map.put(name, idsWithTypeList);
        } else {
            idsWithTypeList.add(id);
        }
    }
}
