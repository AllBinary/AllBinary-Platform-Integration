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

import org.allbinary.animation.AnimationInterfaceFactoryInterface;
import org.allbinary.animation.IndexedAnimation;
import org.allbinary.animation.text.CustomTextBoxIndexedAnimation;
import org.allbinary.game.input.GameKey;
import org.allbinary.game.layer.GDGameLayer;
import org.allbinary.game.layout.GDObject;
import org.allbinary.graphics.GPoint;
import org.allbinary.input.event.VirtualKeyboardEventHandler;
import org.allbinary.input.motion.gesture.MotionGestureInput;
import org.allbinary.input.motion.gesture.observer.MotionGestureEvent;

import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;
import org.allbinary.math.RectangleCollisionUtil;

/**
 *
 * @author User
 */
public class GDTextInputAnimationBehavior extends GDItemAnimationBehavior 
    implements GDGameLayerItemStateListener {
    protected final LogUtil logUtil = LogUtil.getInstance();


    //private final CommonStrings commonStrings = CommonStrings.getInstance();
    
    private final RectangleCollisionUtil rectangleCollisionUtil = RectangleCollisionUtil.getInstance();
    
    private final VirtualKeyboardEventHandler virtualKeyboardEventHandler = 
                VirtualKeyboardEventHandler.getInstance();
    
    private IndexedAnimation[] animationArray;
    private GDGameLayer gameLayer;
    
    public GDTextInputAnimationBehavior() {
        this.virtualKeyboardEventHandler.open();
    }

    public IndexedAnimation[] init(final GDObject gdObject, final AnimationInterfaceFactoryInterface[] animationInterfaceFactoryInterfaceArray) {
        final IndexedAnimation[] indexedAnimation = super.init(gdObject, animationInterfaceFactoryInterfaceArray);
        this.animationArray = indexedAnimation;
        return indexedAnimation;
    }
    
    public void setAnimationArray(final IndexedAnimation[] animationArray)
    {
        this.animationArray = animationArray;
    }
    
    //Show softkeyboard on TextInput::TextInputObject inclusion
    public void add(final GDGameLayer gameLayer) {
        
        //logUtil.putF("InputProcessor for: " + gameLayer, this, commonStrings.ADD);
        this.gameLayer = gameLayer;
        GDFormInputProcessor.gdForm.append(gameLayer);
        GDFormInputProcessor.getInstance().append(GDFormInputProcessor.gdForm);
    }

    public void itemStateChanged(GDGameLayer gameLayerAsItem) {
        
    }

    public void onMotionGestureEvent(final MotionGestureEvent motionGestureEvent) {

        final MotionGestureInput motionGestureInput = motionGestureEvent.getMotionGesture();
        final GPoint point = motionGestureEvent.getCurrentPoint();
        //Show softkeyboard on TextInput::TextInputObject mouse press
        if(motionGestureInput == touchMotionGestureFactory.PRESSED) {
            
            if (this.rectangleCollisionUtil.isInside(this.gameLayer.getXP(), this.gameLayer.getYP() - 2, 
                this.gameLayer.getX2(), this.gameLayer.getY2() + 2, point.getX(), point.getY())) {
                this.virtualKeyboardEventHandler.open();
            }
        } else if(motionGestureInput == touchMotionGestureFactory.RELEASED) {
        } else if((motionGestureInput == touchMotionGestureFactory.LEFT || motionGestureInput == touchMotionGestureFactory.RIGHT)) {
        }
    }

    public int select(final GameKey gameKey, final int keyCode) {
        if(gameKey == gameKeyFactory.LEFT) {
            ((CustomTextBoxIndexedAnimation) this.animationArray[0]).onEvent(keyCode, 0, false);
            return 1;
        } else if(gameKey == gameKeyFactory.RIGHT) {
            ((CustomTextBoxIndexedAnimation) this.animationArray[0]).onEvent(keyCode, 0, false);
            return 1;
        }
        return 0;
    }

    public void keyPressed(final int keyCode) {
        ((CustomTextBoxIndexedAnimation) this.animationArray[0]).onEvent(keyCode, 0, false);
    }

    public void setFocus(final boolean hasFocus) {    
        super.setFocus(hasFocus);        
    }
        
}