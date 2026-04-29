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
package org.allbinary.game.layer.form;

import org.allbinary.game.input.GameKey;
import org.allbinary.game.input.GameKeyFactory;
import org.allbinary.game.input.InputFactory;
import org.allbinary.game.layer.GDAnimationBehaviorBase;
import org.allbinary.input.motion.gesture.TouchMotionGestureFactory;
import org.allbinary.input.motion.gesture.observer.MotionGestureEvent;

public class GDItemAnimationBehavior extends GDAnimationBehaviorBase {
    
    protected final TouchMotionGestureFactory touchMotionGestureFactory = TouchMotionGestureFactory.getInstance();
    protected final InputFactory inputFactory = InputFactory.getInstance();
    protected final GameKeyFactory gameKeyFactory = GameKeyFactory.getInstance();
    
    protected boolean hasFocus;
    
    public int select(final GameKey gameKey, final int keyCode) {
        return 0;
    }
    
    public void onMotionGestureEvent(final MotionGestureEvent motionGestureEvent) {
        
    }

    public void keyPressed(final int keyCode) {
        
    }
    
    public boolean isFocusable() {
        return true;
    }
    
    public void setFocus(final boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public boolean hasFocus() {
        return this.hasFocus;
    }
    
    int traverse(int gameKeyCode, int top, int bottom, boolean action) {
        return 0;
    }
    
}
