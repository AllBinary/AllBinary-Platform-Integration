/*
 * AllBinary Open License Version 1
 * Copyright (c) 2026 AllBinary
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
package org.allbinary.game.layer.form;

import org.allbinary.game.layer.GDGameLayer;

/**
 *
 * @author User
 */
public class NullGDGameLayerItemStateListener 
    implements GDGameLayerItemStateListener {
 
    public static final NullGDGameLayerItemStateListener NULL_GDGAMELAYER_ITEMSTATE_LISTENER = new NullGDGameLayerItemStateListener();

    @Override
    public void itemStateChanged(GDGameLayer gameLayerAsItem) {
        
    }
    
}
