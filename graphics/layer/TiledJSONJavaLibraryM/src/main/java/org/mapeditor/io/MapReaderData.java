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
public class MapReaderData {
    
    private static final MapReaderData instance = new MapReaderData();

    /**
     * @return the instance
     */
    public static MapReaderData getInstance() {
        return MapReaderData.instance;
    }
    
    public final long FLIPPED_HORIZONTALLY_FLAG =  0x0000000080000000L;
    public final long FLIPPED_VERTICALLY_FLAG =    0x0000000040000000L;
    public final long FLIPPED_DIAGONALLY_FLAG =    0x0000000020000000L;

    public final long ALL_FLAGS =
        this.FLIPPED_HORIZONTALLY_FLAG | this.FLIPPED_VERTICALLY_FLAG | this.FLIPPED_DIAGONALLY_FLAG;
    
    public final String LAYERS = "layers";
    
    public final String COMPRESSION = "compression";
    public final String DATA = "data";
    public final String COMPRESSION_ORIGINAL = "compression_original";
    public final String DATA_ORIGINAL = "data_original";
    public final String ENCODING = "encoding";
    public final String WIDTH = "width";
    public final String HEIGHT = "height";
    public final String ID = "id";
    public final String NAME = "name";
    public final String OPACITY = "opacity";
    public final String TYPE = "type";
    public final String VISIBILITY = "visible";
    public final String X = "x";
    public final String Y = "y";
    
    public final String NEXT_INT_0 = "nextInt0: ";
    public final String NEXT_INT_1 = "nextInt1: ";
    public final String NEXT_INT_2 = "nextInt2: ";
    public final String NEXT_INT_3 = "nextInt3: ";
    
}
