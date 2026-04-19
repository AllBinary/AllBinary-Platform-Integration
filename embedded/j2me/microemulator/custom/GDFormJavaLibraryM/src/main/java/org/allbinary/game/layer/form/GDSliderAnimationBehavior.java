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
import org.allbinary.animation.compound.SliderAnimation;
import org.allbinary.game.input.GameKey;
import org.allbinary.game.input.Input;
import org.allbinary.game.input.PlatformKeyFactory;
import org.allbinary.game.layer.GDGameLayer;
import org.allbinary.game.layout.GDObject;
import org.allbinary.graphics.GPoint;
import org.allbinary.input.motion.gesture.MotionGestureInput;
import org.allbinary.input.motion.gesture.observer.MotionGestureEvent;

import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;
import org.allbinary.math.RectangleCollisionUtil;

/**
 *
 * @author User
 */
public class GDSliderAnimationBehavior extends GDItemAnimationBehavior 
implements GDGameLayerItemStateListener {
    protected final LogUtil logUtil = LogUtil.getInstance();


    private final RectangleCollisionUtil rectangleCollisionUtil = RectangleCollisionUtil.getInstance();
    
    private IndexedAnimation[] animationArray;
    private GDGameLayer gameLayer;
    private GPoint point;
    private int deltaFromPointToStartOfThumb;
    private boolean draggingThumb;
    
    public GDSliderAnimationBehavior() {
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
    
    public void setValue(final int value) {
        ((SliderAnimation) this.animationArray[0]).setValue(value);
    }

    public void setValue2(final int value) {
        ((SliderAnimation) this.animationArray[0]).setValue2(value);
    }
    
    public void add(final GDGameLayer gameLayer) {
        this.gameLayer = gameLayer;
        GDFormInputProcessor.gdForm.append(gameLayer);
        GDFormInputProcessor.getInstance().append(GDFormInputProcessor.gdForm);
    }

    public void itemStateChanged(GDGameLayer gameLayerAsItem) {
        
    }

    public void onMotionGestureEvent(final MotionGestureEvent motionGestureEvent) {

        final MotionGestureInput motionGestureInput = motionGestureEvent.getMotionGesture();
        final GPoint point = motionGestureEvent.getCurrentPoint();
        if(motionGestureInput == touchMotionGestureFactory.PRESSED) {
            final SliderAnimation sliderAnimation = ((SliderAnimation) this.animationArray[0]);
            
            if (this.rectangleCollisionUtil.isInside(this.gameLayer.getXP() + sliderAnimation.getThumbDx(), this.gameLayer.getYP() - 2, 
                gameLayer.getXP() + sliderAnimation.getThumbDx() + sliderAnimation.getThumbWidth(), gameLayer.getY2() + 2, point.getX(), point.getY())) {
                this.point = point;
                this.deltaFromPointToStartOfThumb = this.point.getX() - (this.gameLayer.getXP() + sliderAnimation.getThumbDx());
                //logUtil.putF("deltaFromPointToStartOfThumb: " + deltaFromPointToStartOfThumb, this, "onMotionGestureEvent");
                this.draggingThumb = true;
            } else if (rectangleCollisionUtil.isInside(gameLayer.getXP(), gameLayer.getYP() - 2, 
                gameLayer.getX2(), gameLayer.getY2() + 2, point.getX(), point.getY())) {
                final int value2 = point.getX() - this.gameLayer.getXP() - (sliderAnimation.getThumbWidth() / 2);
                //logUtil.putF("moveThumbTo: " + value2, this, "onMotionGestureEvent");
                this.setValue2(value2);
            }
        } else if(motionGestureInput == touchMotionGestureFactory.RELEASED) {
            this.draggingThumb = false;
        } else if((motionGestureInput == touchMotionGestureFactory.LEFT || motionGestureInput == touchMotionGestureFactory.RIGHT) && draggingThumb) {
            final int value2 = point.getX() - this.deltaFromPointToStartOfThumb - gameLayer.getXP();
            this.setValue2(value2);
        }
    }

    public int select(final GameKey gameKey, final int keyCode) {
        if(gameKey == gameKeyFactory.LEFT) {
            this.setValue(((SliderAnimation) this.animationArray[0]).getValue() - 1);
            return 1;
        } else if(gameKey == gameKeyFactory.RIGHT) {
            this.setValue(((SliderAnimation) this.animationArray[0]).getValue() + 1);
            return 1;
        }
        return 0;
    }

    public void keyPressed(final int keyCode) {

        final PlatformKeyFactory platformKeyFactory = PlatformKeyFactory.getInstance();
        final Input input = this.inputFactory.getInstance(keyCode);
        
        if(platformKeyFactory.isLeft(input)) {
            this.setValue(((SliderAnimation) this.animationArray[0]).getValue() - 1);
        } else if(platformKeyFactory.isRight(input)) {
            this.setValue(((SliderAnimation) this.animationArray[0]).getValue() + 1);
        }
    }

    public void setFocus(final boolean hasFocus) {    
        super.setFocus(hasFocus);
        
        ((SliderAnimation) this.animationArray[0]).setFocus(hasFocus);
    }
    
    public int Value() {
        return ((SliderAnimation) this.animationArray[0]).getValue();
    }

}
