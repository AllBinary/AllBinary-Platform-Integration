/*
 * AllBinary Open License Version 1
 * Copyright (c) 2025 AllBinary
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
package opengles;

/**
 *
 * @author User
 */
public class SemanticStrings {
    
    protected static final SemanticStrings instance = new SemanticStrings();

    /**
     * @return the instance
     */
    public static SemanticStrings getInstance() {
        return instance;
    }
    
    public final String POSITION = "position";
    public final String COLOR = "color";
    public final String NORMAL = "normal";
    public final String TEXCOORD = "texcoord";
}
