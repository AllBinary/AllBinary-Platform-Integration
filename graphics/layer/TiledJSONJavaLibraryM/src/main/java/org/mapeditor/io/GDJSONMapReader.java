/*-
 * #%L
 * This file is part of libtiled-java.
 * %%
 * Copyright (C) 2004 - 2020 Thorbjørn Lindeijer <thorbjorn@lindeijer.nl>
 * Copyright (C) 2004 - 2020 Adam Turk <aturk@biggeruniverse.com>
 * Copyright (C) 2016 - 2020 Mike Thomas <mikepthomas@outlook.com>
 * Copyright (C) 2020 Adam Hornacek <adam.hornacek@icloud.com>
 * Copyright (C) 2023 Travis Berthelot <travis.berthelot@allbinary.com>
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.mapeditor.io;

import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;
//import java.util.Base64;
//import java.util.Map.Entry;

import org.allbinary.string.CommonStrings;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.logic.string.StringUtil;

import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.math.PositionStrings;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.InflaterInputStream;
import org.json.me.JSONArray;
import org.json.me.JSONObject;
import org.mapeditor.core.Animation;
import org.mapeditor.core.Frame;

//import org.json.JSONArray;
//import org.json.JSONObject;

import org.mapeditor.core.ImageData;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.TiledMap;
import org.mapeditor.core.Orientation;
import org.mapeditor.core.RenderOrder;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.core.TileSet;
import org.mapeditor.core.WangCornerColor;
import org.mapeditor.core.WangSet;
import org.mapeditor.core.WangSets;
import org.mapeditor.core.WangTile;

/**
 * The standard map reader for TMX files. Supports reading .tmx, .tmx.gz and
 * *.tsx files.
 *
 * @version 1.4.2
 */
public class GDJSONMapReader {
    protected final LogUtil logUtil = LogUtil.getInstance();


    protected final CommonStrings commonStrings = CommonStrings.getInstance();
    protected final PositionStrings positionStrings = PositionStrings.getInstance();
    protected final MapReaderData mapReaderData = MapReaderData.getInstance();

    //private TiledMap map;
    protected Hashtable tilesetPerFirstGid;
    private final int[] tilesetFirstGid = new int[30];

    protected TilesetCache tilesetCache;

    /**
     * Constructor for TMXMapReader.
     */
    public GDJSONMapReader() {
    }

    private TileSet processTileset(final JSONObject jsonObject, final JSONObject[] tileSetJSONObjectArray, final JSONObject[] actualTileSetJSONObjectArray, final int tileSetImageHeight, final int tileSetIndex) throws Exception {
        
            final TileSet tileSet = new TileSet();
            final int firstGid = jsonObject.getInt(FIRST_GID);
            tileSet.setFirstgid(firstGid);
        
            //load .tsj instead
            if(jsonObject.has(SOURCE)) {
                final String source = jsonObject.getString(SOURCE);
                logUtil.put("Loading TileSet (source): " + source, this, commonStrings.PROCESS);                
                actualTileSetJSONObjectArray[tileSetIndex] = tileSetJSONObjectArray[tileSetIndex];
                return this.processTileset(tileSet, tileSetJSONObjectArray[tileSetIndex], tileSetImageHeight, tileSetIndex);
            } else {
                actualTileSetJSONObjectArray[tileSetIndex] = jsonObject;
                return this.processTileset(tileSet, jsonObject, tileSetImageHeight, tileSetIndex);
            }
    }
    
    private TileSet processTileset(final TileSet tileSet, final JSONObject jsonObject, final int tileSetImageHeight, final int tileSetIndex) throws Exception {

            final int firstGid = tileSet.getFirstgid();

            if(jsonObject.has(this.mapReaderData.NAME)) {
                final String name = jsonObject.getString(this.mapReaderData.NAME);
                tileSet.setName(name);
            } else {
                logUtil.put("TileSet without name:" + jsonObject, this, commonStrings.PROCESS);                
                tileSet.setName("Unamed_TileSet");
            }

            tileSet.setTileSetImageHeight(tileSetImageHeight);
            
            if(jsonObject.has(COLUMNS)) {
                tileSet.setColumns(jsonObject.getInt(COLUMNS));
            } else {
                logUtil.put("TileSet without columns:" + jsonObject, this, commonStrings.PROCESS);
            }
                        
            if(jsonObject.has(IMAGE)) { 
                this.addTileSet(tileSet, jsonObject);
            }
            
            logUtil.put("Adding TileSet firstGid:" + firstGid, this, commonStrings.PROCESS);
            tilesetPerFirstGid.put(firstGid, tileSet);
            tilesetFirstGid[tileSetIndex] = firstGid;
            
            tileSet.addTiles();
            
            this.processWangSets(tileSet, jsonObject, firstGid);
            
//        boolean hasTilesetImage = false;

//        for (int i = 0; i < children.getLength(); i++) {
//            Node child = children.item(i);
//
//            if (child.getNodeName().equalsIgnoreCase("image")) {
//                if (hasTilesetImage) {
//                    System.out.println("Ignoring illegal image element after tileset image.");
//                    continue;
//                }
//
//                String imgSource = getAttributeValue(child, "source");
//                String transStr = getAttributeValue(child, "trans");
//
//                if (imgSource != null) {
//                    // Not a shared image, but an entire set in one image
//                    // file. There should be only one image element in this
//                    // case.
//                    hasTilesetImage = true;
//
//                    URL sourcePath;
//                    if (!new File(imgSource).isAbsolute()) {
//                        imgSource = replacePathSeparator(imgSource);
//                        sourcePath = URLHelper.resolve(xmlPath, imgSource);
//                    } else {
//                        sourcePath = makeUrl(imgSource);
//                    }
//
//                    if (transStr != null) {
//                        if (transStr.startsWith("#")) {
//                            transStr = transStr.substring(1);
//                        }
//
//                        int colorInt = Integer.parseInt(transStr, 16);
//                        Color color = new Color(colorInt);
//                        tileSet.setTransparentColor(color);
//                    }
//
//                    tileSet.importTileBitmap(sourcePath, new BasicTileCutter(
//                        tileWidth, tileHeight, tileSpacing, tileMargin));
//                }
//            } else if (child.getNodeName().equalsIgnoreCase("tile")) {
//                Tile tile = unmarshalTile(set, child, xmlPath);
//                if (!hasTilesetImage || tile.getId() > tileSet.getMaxTileId()) {
//                    tileSet.addTile(tile);
//                } else {
//                    Tile myTile = tileSet.getTile(tile.getId());
//                    myTile.setProperties(tile.getProperties());
//                    //TODO: there is the possibility here of overlaying images,
//                    //      which some people may want
//                }
//            } else if (child.getNodeName().equalsIgnoreCase("tileoffset")) {
//                TileOffset tileoffset = new TileOffset();
//                tileoffset.setX(Integer.valueOf(getAttributeValue(child, "x")));
//                tileoffset.setY(Integer.valueOf(getAttributeValue(child, "y")));
//                tileSet.setTileoffset(tileoffset);
//            }
//        }

        return tileSet;
    }

    private void addTileSet(final TileSet tileSet, final JSONObject jsonObject) throws Exception {

        final String path = jsonObject.getString(IMAGE);

        final int imageWidth = jsonObject.getInt(IMAGE_WIDTH);
        final int imageHeight = jsonObject.getInt(IMAGE_HEIGHT);
        final ImageData imageData = new ImageData();
        //final Data data = new Data();
        //data.setCompression(Compression.ZLIB);
        //data.setEncoding(Encoding.BASE_64);
        //data.setValue();
        //imageData.setData(data);
        //imageData.setFormat(NAME);
        //imageData.setSource(NAME);
        //imageData.setTrans(NAME);
        imageData.path = path;
        imageData.setWidth(imageWidth);
        imageData.setHeight(imageHeight);
        tileSet.setImageData(imageData);

        tileSet.setTileWidth(jsonObject.getInt(TILE_WIDTH));
        tileSet.setTileHeight(jsonObject.getInt(TILE_HEIGHT));
        tileSet.setTileMargin(jsonObject.getInt(MARGIN));
        tileSet.setTileSpacing(jsonObject.getInt(SPACING));
        tileSet.setTilecount(jsonObject.getInt(TILE_COUNT));
    }

    private void processTileset(final JSONObject jsonObject) throws Exception {

        if(jsonObject.has(TILES)) {
            final JSONArray jsonArray = jsonObject.getJSONArray(TILES);

            //logUtil.put("Tiles JSON:" + jsonArray.toString(3), this, commonStrings.PROCESS);
            final int size = jsonArray.length();
            Tile tile;
            int tileId;
            for (int i = 0; i < size; i++) {
                //logUtil.put("Loading Tile JSON: " + i, this, commonStrings.PROCESS);
                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                tileId = jsonObject2.getInt(ID);
                tile = this.getTileForTileGID(tileId);
                this.processTile(tile, jsonObject2);
            }
        } else {
            logUtil.put("Found TileSet without tiles", this, commonStrings.PROCESS);
        }

    }

    public void processTilesets(final TiledMap map, final JSONArray jsonArray, final JSONObject[] tileSetJSONObjectArray, final JSONObject[] actualTileSetJSONObjectArray, final int[] tileSetImageHeightArray) throws Exception {
        logUtil.put("Tileset JSON:" + jsonArray.toString(3), this, commonStrings.PROCESS);

        final String LOADING_TILESET = "Loading Tileset: ";
        final int size = jsonArray.length();
        TileSet tileset;
        for (int i = 0; i < size; i++) {
            logUtil.put(LOADING_TILESET + i, this, commonStrings.PROCESS);
            final JSONObject jsonObject = jsonArray.getJSONObject(i);
            //If TileLayer ever supports more than 1 tileSet image then change index from 0 to the index.
            tileset = this.processTileset(jsonObject, tileSetJSONObjectArray, actualTileSetJSONObjectArray, tileSetImageHeightArray[0], i);
            map.addTileset(tileset);
        }

    }

    private void processWangSets(final TileSet tileSet, final JSONObject jsonObject, final int firstGid) throws Exception {
        if(jsonObject.has(this.WANG_SETS)) {

            final WangSets wangSets = new WangSets();
            tileSet.setWangsets(wangSets);
            
            logUtil.put("Found wangset", this, commonStrings.PROCESS);
            final JSONArray jsonArray = jsonObject.getJSONArray(this.WANG_SETS);

            final int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                logUtil.put("Loading wangset JSON: " + i, this, commonStrings.PROCESS);
                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                this.processWangSet(wangSets, jsonObject2);
            }
            
        }
    }
    
    private final String COLORS = "colors";

    private void processWangSet(final WangSets wangSets, final JSONObject jsonObject) throws Exception {

        final WangSet wangSet = new WangSet();
        wangSets.getWangset().add(wangSet);
        
        if (jsonObject.has(this.WANG_TILES)) {
            final String WANG_ID = "wangid";

            logUtil.put("Found wangtiles", this, commonStrings.PROCESS);
            final JSONArray jsonArray = jsonObject.getJSONArray(this.WANG_TILES);
            final int size = jsonArray.length();
            Tile tile;
            int tileId;
            for (int i = 0; i < size; i++) {
                logUtil.put("Loading wangtiles JSON: " + i, this, commonStrings.PROCESS);
                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                tileId = jsonObject2.getInt(TILE_ID);
                tile = this.getTileForTileGID(tileId);
                this.processTile(tile, jsonObject2);
                final WangTile wangTile = new WangTile();
                wangTile.setTileid(tileId);
                wangTile.setWangid(jsonObject2.getString(WANG_ID));
                wangSet.getWangtile().add(wangTile);
            }            
        }

        final String TILE = "tile";
        
        if (jsonObject.has(this.COLORS)) {
            
            final String PROBABILITY = "probability";
            final String COLOR = "color";

            final JSONArray jsonArray = jsonObject.getJSONArray(this.COLORS);
            final int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                logUtil.put("Loading color JSON: " + i, this, commonStrings.PROCESS);
                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                final WangCornerColor wangCornerColor = new WangCornerColor();
                
                wangCornerColor.setColor(jsonObject2.getString(COLOR));
                wangCornerColor.setName(jsonObject2.getString(this.mapReaderData.NAME));
                wangCornerColor.setProbability(jsonObject2.getInt(PROBABILITY));
                wangCornerColor.setTile(jsonObject2.getInt(TILE));

        //"type":"corner",
        //wangSet.getWangedgecolor()
                wangSet.getWangcornercolor().add(wangCornerColor);
            }
        }

        wangSet.setName(jsonObject.getString(this.mapReaderData.NAME));
        wangSet.setTile(jsonObject.getInt(TILE));
        
    }

    private void processTile(final Tile tile, final JSONObject jsonObject2) throws Exception {

        final ObjectGroup objectGroup = new ObjectGroup();
        objectGroup.setId(tile.getId());
            
        //logUtil.put("ObjectGroup id:" + objectGroup.getId(), this, commonStrings.PROCESS);

        if(jsonObject2.has(CLASS) || jsonObject2.has(TYPE)) {

            if(jsonObject2.has(CLASS)) {
                final String className = jsonObject2.getString(CLASS);
                objectGroup.setName(className);
            }

            if(jsonObject2.has(TYPE)) {
                final String type = jsonObject2.getString(TYPE);
                objectGroup.setName(type);
            }
            
            //logUtil.put("ObjectGroup name:" + objectGroup.getName(), this, commonStrings.PROCESS);
            
            tile.getObjectgroup().add(objectGroup);
            
            if (jsonObject2.has(OBJECT_GROUP)) {
                final JSONObject jsonObject = jsonObject2.getJSONObject(OBJECT_GROUP);
                //jsonObject.getString(this.mapReaderData.NAME)
                //objectGroup.setDraworder(jsonObject.getString(DRAW_ORDER));

//            final List<MapObjectData> mapObjectList = objectGroup.getObjects();
//            final JSONArray jsonArray = jsonObject.getJSONArray(OBJECTS);
//            final int size = jsonArray.length();
//            JSONObject mapObjectJSONObject;
//            for(int index = 0; index < size; index++) {
//                mapObjectJSONObject = jsonArray.getJSONObject(index);
//                final MapObjectData mapObject = new MapObjectData();
//                
//                mapObject.setId(mapObjectJSONObject.getInt(ID));
//                mapObject.setName(mapObjectJSONObject.getString(this.mapReaderData.NAME));
//                mapObject.setWidth(mapObjectJSONObject.getDouble(this.mapReaderData.WIDTH));
//                mapObject.setHeight(mapObjectJSONObject.getDouble(this.mapReaderData.HEIGHT));
//                
//                mapObject.setRotation(mapObjectJSONObject.getDouble(ROTATION));
//                //mapObject.setVisible(mapObjectJSONObject.getBoolean(this.mapReaderData.VISIBLE));
//                //mapObject.setX(mapObjectJSONObject.getInt(X));
//                //mapObject.setY(mapObjectJSONObject.getInt(Y));
//
//                mapObjectList.add(mapObject);
//            }
                objectGroup.setOpacity(Float.valueOf((float) jsonObject.getDouble(this.mapReaderData.OPACITY)));
                //objectGroup.setVisible(jsonObject.getBoolean(this.mapReaderData.VISIBLE));
                //objectGroup.setX();
                //objectGroup.setY();

            }
        }

        if (jsonObject2.has(ANIMATION)) {
            final Animation animation = new Animation();

            final JSONArray jsonArray = jsonObject2.getJSONArray(ANIMATION);

            //logUtil.put("Tiles JSON:" + jsonArray.toString(3), this, commonStrings.PROCESS);
            final int size = jsonArray.length();
            Frame frame;
            int tileId;
            int duration;
            for (int i = 0; i < size; i++) {
                //logUtil.put("Loading Frame JSON: " + i, this, commonStrings.PROCESS);
                final JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                duration = jsonObject3.getInt(DURATION);
                tileId = jsonObject3.getInt(TILE_ID);
                frame = new Frame();
                frame.setTileid(tileId + 1);
                frame.setDuration(duration / 4);
                animation.getFrame().add(frame);
            }

            tile.setAnimation(animation);
        }
    }

    /**
     * Loads a map layer from a layer node.
     *
     * @param t the node representing the "layer" element
     * @return the loaded map layer
     * @throws Exception
     */     
    public TileLayer readLayer(final TiledBehavior tiledBehavior, final JSONObject t) throws Exception {
        
        //logUtil.put("Loading TileLayer", this, commonStrings.PROCESS);
        
        final int layerId = t.getInt(this.mapReaderData.ID);
        final int layerWidth = t.getInt(this.mapReaderData.WIDTH);
        final int layerHeight = t.getInt(this.mapReaderData.HEIGHT);

        final TileLayer tiledTileLayer = new TileLayer(layerWidth, layerHeight);

        tiledTileLayer.setId(layerId);

        final int offsetX = t.getInt(this.mapReaderData.X);
        final int offsetY = t.getInt(this.mapReaderData.Y);
        final boolean visible = t.getBoolean(this.mapReaderData.VISIBILITY);

        tiledTileLayer.setName(t.getString(this.mapReaderData.NAME));
        tiledTileLayer.setOffsetX(offsetX);
        tiledTileLayer.setOffsetY(offsetY);

        //ml.setOpacity(t.getFloat(this.OPACITY));
        tiledTileLayer.setOpacity((float) t.getDouble(this.mapReaderData.OPACITY));

        //(t, ml.getProperties());

        //for (Node child = t.getFirstChild(); child != null;
                //child = child.getNextSibling()) {
            //String nodeName = child.getNodeName();
            if (t.has(this.mapReaderData.DATA)) {
                String encoding = t.getString(this.mapReaderData.ENCODING);
                String comp = t.has(this.mapReaderData.COMPRESSION) ? t.getString(this.mapReaderData.COMPRESSION) : null;

                if ("base64".equalsIgnoreCase(encoding)) {
                        final String enc = t.getString(this.mapReaderData.DATA);
                        final byte[] dec = 
                                org.apache.xmlrpc.Base64.decode(enc.getBytes());
                                //org.apache.commons.codec.binary.Base64.decodeBase64(enc); 
                                //Base64.getDecoder().decode(enc);
                                //DatatypeConverter.parseBase64Binary(enc);
                        final ByteArrayInputStream bais = new ByteArrayInputStream(dec);
                        InputStream is;

                        if ("gzip".equalsIgnoreCase(comp)) {
//                            final int len = layerWidth * layerHeight * 4;
//                            is = new GZIPInputStream(bais, len);
                              throw new RuntimeException();
                        } else if ("zlib".equalsIgnoreCase(comp)) {
//                            is = new InflaterInputStream(bais);
                              throw new RuntimeException();
                        } else if (comp != null && !comp.isEmpty()) {
//                            throw new IOException("Unrecognized compression method \"" + comp + "\" for map layer " + ml.getName());
                              throw new RuntimeException();
                        } else {
                            logUtil.put("Loading TileLayer - uncompressed", this, commonStrings.PROCESS);
                            is = bais;
                        }

                        //final int size = ml.getHeight() * ml.getWidth();
                        //final byte[] byteArray = new byte[size * 4];
                        //is.read(byteArray);
                        //final String encodedString = Base64.getEncoder().encodeToString(byteArray);
                        //logUtil.put(encodedString, this, "encoded");

                        //final CommonStrings commonStrings = CommonStrings.getInstance();
                        final StringMaker stringMaker = new StringMaker();

                        int nextInt = -1;
                        for (int y = 0; y < tiledTileLayer.getHeight(); y++) {
                            for (int x = 0; x < tiledTileLayer.getWidth(); x++) {
                                int tileId = 0;

                                stringMaker.delete(0, stringMaker.length());

                                nextInt = is.read();
                                //tileId |= nextInt;
                                tileId = tiledBehavior.getTileId0(tileId, nextInt, stringMaker);

                                nextInt = is.read();
                                //tileId |= (nextInt) << Byte.SIZE;
                                tileId = tiledBehavior.getTileId1(tileId, nextInt, stringMaker);

                                nextInt = is.read();
                                //tileId |= (nextInt) << Byte.SIZE * 2;
                                tileId = tiledBehavior.getTileId2(tileId, nextInt, stringMaker);

                                nextInt = is.read();
                                //tileId |= (nextInt) << Byte.SIZE * 3;
                                tileId = tiledBehavior.getTileId3(tileId, nextInt, stringMaker);

//                                if(stringMaker.length() > 0) {
//                                    logUtil.put(stringMaker.toString(), this, commonStrings.PROCESS);
//                                }
                                
                                setTileAtFromTileId(tiledTileLayer, y, x, tileId);
                            }
                        }
                        
                }
//                } else if ("csv".equalsIgnoreCase(encoding)) {
//                    String csvText = child.getTextContent();
//
//                    if (comp != null && !comp.isEmpty()) {
//                        throw new IOException("Unrecognized compression method \"" + comp + "\" for map layer " + ml.getName() + " and encoding " + encoding);
//                    }
//
//                    String[] csvTileIds = csvText
//                            .trim() // trim 'space', 'tab', 'newline'. pay attention to additional unicode chars like \u2028, \u2029, \u0085 if necessary
//                            .split("[\\s]*,[\\s]*");
//
//                    if (csvTileIds.length != ml.getHeight() * ml.getWidth()) {
//                        throw new IOException("Number of tiles does not match the layer's width and height");
//                    }
//
//                    for (int y = 0; y < ml.getHeight(); y++) {
//                        for (int x = 0; x < ml.getWidth(); x++) {
//                            String gid = csvTileIds[x + y * ml.getWidth()];
//                            long tileId = Long.parseLong(gid);
//
//                            setTileAtFromTileId(ml, y, x, (int) tileId);
//                        }
//                    }
//                } else {
//                    int x = 0, y = 0;
//                    for (Node dataChild = child.getFirstChild();
//                            dataChild != null;
//                            dataChild = dataChild.getNextSibling()) {
//                        if ("tile".equalsIgnoreCase(dataChild.getNodeName())) {
//                            int tileId = getAttribute(dataChild, "gid", -1);
//                            setTileAtFromTileId(ml, y, x, tileId);
//
//                            x++;
//                            if (x == ml.getWidth()) {
//                                x = 0;
//                                y++;
//                            }
//                            if (y == ml.getHeight()) {
//                                break;
//                            }
//                        }
//                    }
//                }

//            } else if ("tileproperties".equalsIgnoreCase(nodeName)) {
//                for (Node tpn = child.getFirstChild();
//                        tpn != null;
//                        tpn = tpn.getNextSibling()) {
//                    if ("tile".equalsIgnoreCase(tpn.getNodeName())) {
//                        int x = getAttribute(tpn, "x", -1);
//                        int y = getAttribute(tpn, "y", -1);
//
//                        Properties tip = new Properties();
//
//                        //readProperties(tpn.getChildNodes(), tip);
//                        ml.setTileInstancePropertiesAt(x, y, tip);
//                    }
//                }
//            }
        }

        // This is done at the end, otherwise the offset is applied during
        // the loading of the tiles.
        tiledTileLayer.setOffset(offsetX, offsetY);

        // Invisible layers are automatically locked, so it is important to
        // set the layer to potentially invisible _after_ the layer data is
        // loaded.
        // todo: Shouldn't this be just a user interface feature, rather than
        // todo: something to keep in mind at this level?
        //ml.setVisible(visible == 1);
        tiledTileLayer.setVisible(visible);

//        final int locked = getAttribute(t, "locked", 0);
//        if (locked != 0) {
//            ml.setLocked(1);
//        }

        return tiledTileLayer;
    }


    private final String TILE_LABEL = " tile: ";
    private final String SET_TILE_AT_FROM_TILE_ID = "setTileAtFromTileId";
    
    /**
     * Helper method to set the tile based on its global id.
     *
     * @param tileLayer tile layer
     * @param y y-coordinate
     * @param x x-coordinate
     * @param tileGid global id of the tile as read from the file
     */
    protected void setTileAtFromTileId(final TileLayer tileLayer, final int y, final int x, final int tileGid) {
        //(tileGid & (int)~this.mapReaderData.ALL_FLAGS)
        final Tile tile = this.getTileForTileGID(tileGid);

        final long flags = tileGid & this.mapReaderData.ALL_FLAGS;

        final String tileAsString = tile != null ? tile.toString() : StringUtil.getInstance().NULL_STRING;
        if(tile != null) {
//            if(tile.getId() > 1) {
//                logUtil.put(new StringMaker().append(this.TILE_ID).append(tileGid).append(positionStrings.X_LABEL).append(x).append(positionStrings.Y_LABEL).append(y).append(this.TILE_LABEL).append(tileAsString).toString(), this, SET_TILE_AT_FROM_TILE_ID);
//            }
        } else {
//            logUtil.put(new StringMaker().append(this.TILE_ID).append(tileGid).append(positionStrings.X_LABEL).append(x).append(positionStrings.Y_LABEL).append(y).append(this.TILE_LABEL).append(tileAsString).toString(), this, SET_TILE_AT_FROM_TILE_ID);
        }
        
        tileLayer.setTileAt(x, y, tile);
        tileLayer.setFlagsAt(x, y, (int) flags);
    }

    /**
     * Helper method to get the tile based on its global id.
     *
     * @param tileId global id of the tile
     * @return    <ul><li>{@link Tile} object corresponding to the global id, if
     * found</li><li><code>null</code>, otherwise</li></ul>
     */
    private Tile getTileForTileGID(final int tileId) {
        Tile tile = null;
        final java.util.Map.Entry<Integer, TileSet> ts = findTileSetForTileGID(tileId);
        if (ts != null) {
            final TileSet tileSet = ts.getValue();
            //logUtil.put("tileId: " + tileId, this, "getTile");
            //tile = tileSet.getTile(tileId - ts.getKey());
            tile = tileSet.getTile(tileId);
        } else {
            logUtil.put("tileIdToTileSet was null for tileId: " + tileId, this, "getTileForTileGID");
            throw new RuntimeException();
        }
        return tile;
    }

    private final String TILESETS = "tilesets";
    private final String COLUMNS = "columns";
    private final String FIRST_GID = "firstgid";
    private final String IMAGE = "image";
    private final String SOURCE = "source";
    private final String IMAGE_HEIGHT = "imageheight";
    private final String IMAGE_WIDTH = "imagewidth";
    private final String MARGIN = "margin";
    private final String SPACING = "spacing";
    private final String TILE_COUNT = "tilecount";     
    private final String TILE_HEIGHT = "tileheight";
    private final String TILE_WIDTH = "tilewidth";
    
    private final String TILES = "tiles";
    private final String WANG_SETS = "wangsets";
    private final String WANG_TILES = "wangtiles";
    private final String ID = "id";
    
    private final String OBJECT_GROUP = "objectgroup";
    private final String CLASS = "class";
    private final String TYPE = "type";
    private final String OBJECTS = "objects";
    private final String DRAW_ORDER = "draworder";
    private final String ROTATION = "rotation";
    
    private final String ANIMATION = "animation";
    private final String DURATION = "duration";
    private final String TILE_ID = "tileid";
    
    //private final String COMPRESSION_LEVEL = "compressionlevel";
    private final String INFINITE = "infinite";
    private final String NEXT_LAYER_ID = "nextlayerid";
    private final String NEXT_OBJECT_ID = "nextobjectid";
    private final String ORIENTATION = "orientation";
    private final String RENDER_ORDER = "renderorder";
    private final String TILED_VERSION = "tiledversion";
    private final String VERSION = "version";
    
    public TiledMap buildMap(final JSONObject mapJSONObject, final JSONObject[] tileSetJSONObjectArray, final int[] tileSetImageHeightArray) throws Exception {

        final TiledBehaviorFactory tiledBehaviorFactory = TiledBehaviorFactory.getInstance();
        final TiledMap map = new TiledMap(mapJSONObject.getInt(this.mapReaderData.WIDTH), mapJSONObject.getInt(this.mapReaderData.HEIGHT));
        
        map.setTileWidth(mapJSONObject.getInt(this.TILE_WIDTH));
        map.setTileHeight(mapJSONObject.getInt(this.TILE_HEIGHT));

        //map.setVersion(mapJSONObject.getBigDecimal(VERSION).toString());
        map.setVersion(tiledBehaviorFactory.get(Double.toString(mapJSONObject.getDouble(VERSION))));
        map.setInfinite(mapJSONObject.getBoolean(INFINITE) ? 1 : 0);
        map.setNextlayerid(mapJSONObject.getInt(this.NEXT_LAYER_ID));
        map.setNextobjectid(mapJSONObject.getInt(this.NEXT_OBJECT_ID));
        map.setOrientation(Orientation.fromValue(mapJSONObject.getString(this.ORIENTATION)));
        map.setRenderorder(RenderOrder.fromValue(mapJSONObject.getString(this.RENDER_ORDER)));
        map.setTiledversion(mapJSONObject.getString(TILED_VERSION));

        tilesetPerFirstGid = new Hashtable();

        final JSONArray jsonArray = mapJSONObject.getJSONArray(TILESETS);
        final int size = jsonArray.length();
        final JSONObject[] actualTileSetJSONObjectArray = new JSONObject[size];
        this.processTilesets(map, jsonArray, tileSetJSONObjectArray, actualTileSetJSONObjectArray, tileSetImageHeightArray);
        
//        for (Node sibs = mapNode.getFirstChild(); sibs != null;
//                sibs = sibs.getNextSibling()) {
//            if ("group".equals(sibs.getNodeName())) {
//                Group group = unmarshalGroup(sibs);
//                if (group != null) {
//                    map.addLayer(group);
//                }
//            }

            final String LOADING_LAYER = "Loading Layer at: ";
            
            final TiledBehavior tildeBehavior = tiledBehaviorFactory.getBehavior(map.getVersion());
            logUtil.put("tildeBehavior: " + tildeBehavior.getClass().getName(), this, commonStrings.PROCESS);
            
            final JSONArray layerJSONArray = mapJSONObject.getJSONArray(this.mapReaderData.LAYERS);
            final int size2 = layerJSONArray.length();
            TileLayer layer;
            for(int index = 0; index < size2; index++) {
                logUtil.put(LOADING_LAYER + index, this, commonStrings.PROCESS);
                layer = readLayer(tildeBehavior, layerJSONArray.getJSONObject(index));
                if (layer != null) {
                    map.addLayer(layer);
                }
            }
            
//            } else if ("objectgroup".equals(sibs.getNodeName())) {
//                ObjectGroup group = unmarshalObjectGroup(sibs);
//                if (group != null) {
//                    map.addLayer(group);
//                }
//            } else if ("imagelayer".equals(sibs.getNodeName())) {
//                ImageLayer imageLayer = unmarshalImageLayer(sibs);
//                if (imageLayer != null) {
//                    map.addLayer(imageLayer);
//                }
//            }
//        }

        final String PROCESS_TILE_SET = "processTileset at: ";
        for (int i = 0; i < size; i++) {
            //final JSONObject jsonObject = jsonArray.getJSONObject(i);
            final JSONObject jsonObject = actualTileSetJSONObjectArray[i];
            logUtil.put(PROCESS_TILE_SET + i, this, commonStrings.PROCESS);
            this.processTileset(jsonObject);
        }

        tilesetPerFirstGid = null;
        return map;
    }

    /**
     * This utility function will check the specified string to see if it starts
     * with one of the OS root designations. (Ex.: '/' on Unix, 'C:' on Windows)
     *
     * @param filename a filename to check for absolute or relative path
     * @return <code>true</code> if the specified filename starts with a
     * filesystem root, <code>false</code> otherwise.
     */
//    public static boolean checkRoot(String filename) {
//        File[] roots = File.listRoots();
//
//        for (File root : roots) {
//            try {
//                String canonicalRoot = root.getCanonicalPath().toLowerCase();
//                if (filename.toLowerCase().startsWith(canonicalRoot)) {
//                    return true;
//                }
//            } catch (IOException e) {
//                // Do we care?
//            }
//        }
//
//        return false;
//    }

    static final class Entry<K,V> implements java.util.Map.Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        //boolean color = BLACK;

        Entry(K key, V value, Entry<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof java.util.Map.Entry))
                return false;
            java.util.Map.Entry<?,?> e = (java.util.Map.Entry<?,?>)o;

            return valEquals(key,e.getKey()) && valEquals(value,e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    static final boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }
    
    /**
     * Get the tile set and its corresponding firstgid that matches the given
     * global tile id.
     *
     * @param tileId a global tile id
     * @return the tileset containing the tile with the given global tile id, or
     * <code>null</code> when no such tileset exists
     */
    private Entry<Integer, TileSet> findTileSetForTileGID(final int tileId) {
        final TileSet tileSet = (TileSet) tilesetPerFirstGid.get(this.getTileSetFirtTileIdForTileId(tileId));
        if(tileSet != null) {
            final Entry entry = new Entry(tileId, tileSet, null);
            return entry;
        } else {
            return null;
        }
        //return tilesetPerFirstGid.floorEntry(gid);
    }

    private int getTileSetFirtTileIdForTileId(final int tileId) {
        final int size = this.tilesetFirstGid.length;
        for(int index = 0; index < size; index++) {
            if(tileId + 1 >= this.tilesetFirstGid[index]) {
                return this.tilesetFirstGid[index];
            }
        }
        throw new RuntimeException();
    }
    
    /**
     * Tile map can be assembled on UNIX system, but read on Microsoft Windows system.
     * @param path path to imageSource, tileSet, etc.
     * @return path with the correct {@link File#separator}
     */
//    private String replacePathSeparator(String path) {
//        if (path == null)
//            throw new IllegalArgumentException("path cannot be null.");
//        if (path.isEmpty() || path.lastIndexOf(File.separatorChar) >= 0)
//            return path;
//        return path.replace("/", File.separator);
//    }

    public GDJSONMapReader setTilesetCache(TilesetCache tilesetCache) {
        this.tilesetCache = tilesetCache;
        return this;
    }
}
