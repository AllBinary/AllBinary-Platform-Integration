/*
 * MicroEmulator 
 * Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2005 Andres Navarro
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Contributor(s): 
 *   3GLab
 *   AllBinary
 */
package org.allbinary.game.layer.form;

import javax.microedition.lcdui.Canvas;

import org.allbinary.game.input.GameInputStrings;
import org.allbinary.game.input.GameKey;
import org.allbinary.game.input.GameKeyUtil;
import org.allbinary.game.input.Input;
import org.allbinary.game.input.InputFactory;
import org.allbinary.game.input.PlatformInputMappingFactory;
import org.allbinary.game.input.PlatformKeyFactory;
import org.allbinary.game.input.event.GameKeyEvent;
import org.allbinary.game.input.mapping.InputToGameKeyMapping;
import org.allbinary.game.layer.GDGameLayer;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.input.motion.gesture.observer.MotionGestureEvent;

import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.logic.string.StringUtil;
import org.allbinary.logic.util.event.AllBinaryEventObject;
import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

public class GDForm extends GDFormInput
{
    protected final LogUtil logUtil = LogUtil.getInstance();

    public static int OUTOFITEM = Integer.MAX_VALUE;

    private final CommonStrings commonStrings = CommonStrings.getInstance();
    private final GameInputStrings gameInputStrings = GameInputStrings.getInstance();
    
    private final BasicArrayList list = new BasicArrayListD();

    private int focusItemIndex;
    private GDGameLayerItemStateListener itemStateListener = null;
    //private int viewPortY;
    //private int viewPortHeight;

    private final InputToGameKeyMapping inputToGameKeyMapping = 
        PlatformInputMappingFactory.getInstance().getPersistentInputMappingInstance().getInputMapping();
    
    public GDForm() {
        
        focusItemIndex = -2;
        
    }

    public void submit() {

    }

    public void open() {
        this.logUtil.putF(this.commonStrings.START, this, "open");
    }

    public void close() throws Exception {
        logUtil.putF(commonStrings.START, this, commonStrings.CLOSE);
    }

    @Override
    public void onEvent(final int keyCode, final int deviceId, final boolean repeated) {
        logUtil.putF(Integer.toString(keyCode), this, "onEvent");
        this.keyPressed(keyCode);
    }
    
    public void onEvent(AllBinaryEventObject eventObject) {
        //ForcedLogUtil.log(EventStrings.getInstance().PERFORMANCE_MESSAGE, this);
        this.onPressGameKeyEvent((GameKeyEvent) eventObject);
        
    }
        
    public void onMotionGestureEvent(final MotionGestureEvent motionGestureEvent) {
        final int size = this.list.size();
        for(int index = 0; index < size; index++) {
            final GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
            final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
            animationBehaviorBase.onMotionGestureEvent(motionGestureEvent);
        }
    }
    
    public void onPressGameKeyEvent(GameKeyEvent gameKeyEvent) {
        logUtil.putF(new StringMaker().append("KeyCode: ").appendint(gameKeyEvent.getKey()).toString(), this, gameInputStrings.ON_PRESS_GAME_KEY);
        this.keyPressed(gameKeyEvent.getKey());

        BasicArrayList list = this.inputToGameKeyMapping.getReverseInstance(gameKeyEvent.getKey());
        this.logUtil.putF(new StringMaker().append("getReverseInstance list size: ").appendint(list.size()).toString(), this, gameInputStrings.ON_PRESS_GAME_KEY);
        
//        for (int index = 0; index < list.size(); index++) {
//            Input input = (Input) list.objectArray[index];
//
//            if (input instanceof GameKey
//                || input instanceof MotionGestureInput
//                || input instanceof TouchButtonInput) {
//
//            } else {
//                this.keyPressed(input.getId());
//            }
//        }
    }

    public void onDownKeyEvent(GameKeyEvent gameKeyEvent) {
        this.onPressGameKeyEvent(gameKeyEvent);
    }
    
    public void onDownGameKeyEvent(GameKeyEvent gameKeyEvent) {
        this.onPressGameKeyEvent(gameKeyEvent);
    }

    public void onUpGameKeyEvent(GameKeyEvent gameKeyEvent) {
        //this.keyReleased(gameKeyEvent.getKey());
    }

    public int append(GDGameLayer gameLayerAsItem) {
        
        this.list.add(gameLayerAsItem);

        return (this.list.size() - 1);
    }

    public void delete(int itemNum) {
        GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.removeAt(itemNum);
    }

    public void deleteAll() {
        this.list.clear();
    }

    public GDGameLayer get(int itemNum) {
        return (GDGameLayer) this.list.objectArray[itemNum];
    }

    public int getHeight() {
        return DisplayInfoSingleton.getInstance().getLastHeight();
        //return super.getHeight();
    }

    public int getWidth() {
        return DisplayInfoSingleton.getInstance().getLastWidth();
        //return super.getWidth();
    }

    public void insert(int itemNum, GDGameLayer gameLayerAsItem) {
        list.addAt(itemNum, gameLayerAsItem);

    }

    public void set(int itemNum, GDGameLayer gameLayerAsItem) {
        GDGameLayer currentItem = (GDGameLayer) this.list.objectArray[itemNum];

        this.list.addAt(itemNum, gameLayerAsItem);
    }

    public void setItemStateListener(GDGameLayerItemStateListener iListener) {
        this.itemStateListener = iListener;
    }

    public int size() {
        return this.list.size();
    }

    void fireItemStateListener(GDGameLayer gameLayerAsItem) {
        if (this.itemStateListener != null) {
            this.itemStateListener.itemStateChanged(gameLayerAsItem);
        }
    }

    void fireItemStateListener() {
        int size = this.size();
        if (this.focusItemIndex >= 0 && this.focusItemIndex < size) {
            fireItemStateListener((GDGameLayer) this.list.objectArray[this.focusItemIndex]);
        }
    }

    public void hideNotify() {

        int size = this.size();
        for (int i = 0; i < size; i++) {
            GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[i];
            final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
            if (animationBehaviorBase.isFocusable() && animationBehaviorBase.hasFocus()) {
                animationBehaviorBase.setFocus(false);
                this.focusItemIndex = -2;
                break;
            }
        }
    }

    //int artificialIndex = 0;
    //private final GameKeyFactory gameKeyFactory = GameKeyFactory.getInstance();

    private final InputFactory inputFactory = InputFactory.getInstance();

    private final GameKeyUtil gameKeyUtil = GameKeyUtil.getInstance();

    public void keyPressed(int keyCode) {
        this.keyPressed(keyCode, 0);
    }

    public void keyReleased(int keyCode) {
        //this.keyReleased(keyCode, 0);
    }

//    public void keyRepeated(int keyCode) {
//        this.keyRepeated(keyCode, 0);
//    }

    public void keyPressed(int keyCode, int deviceId) {
        
        showNotify();

        final GameKey gameKey = this.inputToGameKeyMapping.getInstance(keyCode);

        this.logUtil.putF(new StringMaker().append("GameKey: ").append(gameKey.toString()).append(" KeyCode: ").appendint(keyCode).toString(), this, gameInputStrings.KEY_PRESSED);

        /*
         * if(focusItemIndex == this.size() - 1 && gameKey == gameKeyFactory.UP &&
         * this.artificialIndex == 1) { this.artificialIndex = 0; } else
         * if(this.artificialIndex > 0) { if(gameKey == gameKeyFactory.UP)
         * this.artificialIndex--; else if(gameKey == gameKeyFactory.DOWN &&
         * this.artificialIndex < 3) this.artificialIndex++;
         * 
         * super.keyPressed(keyCode); } else
         */
        if (this.focusItemIndex != -1) {
            final GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[this.focusItemIndex];

            final PlatformKeyFactory platformKeyFactory = PlatformKeyFactory.getInstance();
            final Input input = this.inputFactory.getInstanceById(keyCode);
            final String name = platformKeyFactory.getString(keyCode);

            boolean traverse = false;

            // Don't process as traversal if letter or number
            if (name.length() >= 2) {
                final int beforeFocusItemIndex = focusItemIndex;
                if (this.gameKeyUtil.isDirectionKey(keyCode)) {
                    //logUtil.putF("d focusItemIndex: " + focusItemIndex + " KeyCode: " + keyCode, this, gameInputStrings.KEY_PRESSED);
                    this.traverse(gameKey.getId(), 0, this.getHeight());
                } else if (platformKeyFactory.isUp(input)) {
                    //logUtil.put("up focusItemIndex: " + focusItemIndex + " KeyCode: " + keyCode, this, gameInputStrings.KEY_PRESSED);
                    this.traverse(Canvas.UP, 0, this.getHeight());
                } else if (platformKeyFactory.isDown(input)) {
                    //logUtil.put("down focusItemIndex: " + focusItemIndex + " KeyCode: " + keyCode, this, gameInputStrings.KEY_PRESSED);
                    this.traverse(Canvas.DOWN, 0, this.getHeight());
                }
                if(beforeFocusItemIndex != this.focusItemIndex) {
                    traverse = true;
                }
            }

            this.logUtil.putF(new StringMaker().append("Traversal Value: ").appendboolean(traverse).toString(), this, gameInputStrings.KEY_PRESSED);

            /*
             * if(traverseValue == GDGameLayer.OUTOFITEM && focusItemIndex ==
             * this.size() - 1 && gameKey == gameKeyFactory.DOWN) {
             * this.artificialIndex = 1; super.keyPressed(keyCode); } else
             */
            if (!traverse) {
                //gameKey == gameKeyFactory.FIRE || gameKey == gameKeyFactory.LEFT || gameKey == gameKeyFactory.RIGHT
                if (platformKeyFactory.isEnter(input) || platformKeyFactory.isLeft(input) || platformKeyFactory.isRight(input)) {
                    this.logUtil.putF(new StringMaker().append("Select: GameKey: ").append(StringUtil.getInstance().toString(gameKey)).toString(), this, gameInputStrings.KEY_PRESSED);
                    ((GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior()).select(gameKey, keyCode);
                    fireItemStateListener();
                } else {
                    this.logUtil.putF(new StringMaker().append("keyPressed: keyCode: ").appendint(keyCode).toString(), this, gameInputStrings.KEY_PRESSED);
                    // gameLayerAsItem.keyPressed(gameKey.getId().intValue());
                    ((GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior()).keyPressed(keyCode);
                }
            }
        }
    }
    
    public void keyReleased(int keyCode, int deviceId) {
        // logUtil.putF(commonStrings.START, this, gameInputStrings.KEY_RELEASED);
    }

    public void showNotify() {

        if (this.focusItemIndex == -2) {
            this.focusItemIndex = -1;

            final int size = this.size();
            GDGameLayer gameLayerAsItem;
            for (int index = 0; index < size; index++) {
                gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                if (animationBehaviorBase.isFocusable()) {
                    animationBehaviorBase.setFocus(true);
                    this.focusItemIndex = index;
                    this.logUtil.putF("first item that is focusable: " + this.focusItemIndex, this, "showNotify");
                    break;
                }
            }
        }
        if (this.focusItemIndex < 0) {
            return;
        }

        int heightToItem = getHeightToItem(this.focusItemIndex);

//        final int heightAfterItem = heightToItem + ((GDGameLayer) list.objectArray[focusItemIndex]).getHeight();
//
//        if (viewPortY > heightToItem) {
//            viewPortY = heightToItem;
//        } else if ((viewPortY + viewPortHeight) < heightAfterItem) {
//            viewPortY = heightAfterItem - viewPortHeight;
//        }
    }

    int traverse(int keyCode, int top, int bottom) {

        //logUtil.put("traverse: keyCode: " + keyCode, this, gameInputStrings.KEY_PRESSED);
        
        int height, testItemIndex, traverse, index;
        int topItemIndex, bottomItemIndex;

        if (this.size() == 0) {
            return 0;
        }

        if (keyCode == Canvas.UP) {
            //logUtil.putF("traverse: Canvas.UP", this, gameInputStrings.KEY_PRESSED);
            topItemIndex = getTopVisibleIndex(top);
            if (this.focusItemIndex == -1) {
                testItemIndex = topItemIndex;
                height = getHeightToItem(testItemIndex);
                final GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[testItemIndex];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, false);
            } else {
                testItemIndex = this.focusItemIndex;
                this.logUtil.putF("traverse up: " + testItemIndex, this, gameInputStrings.KEY_PRESSED);
                height = getHeightToItem(testItemIndex);
                final GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[testItemIndex];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, true);
            }
            if (traverse == GDForm.OUTOFITEM) {
                GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[testItemIndex];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();

                if (this.focusItemIndex == -1 && animationBehaviorBase.isFocusable()) {
                    animationBehaviorBase.setFocus(true);
                    this.focusItemIndex = testItemIndex;
                }
                return traverse;
            } else {
                if (testItemIndex > 0) {
                    for (index = testItemIndex - 1; index >= topItemIndex; index--) {
                        GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
                        final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();

                        if (animationBehaviorBase.isFocusable()) {
                            if (this.focusItemIndex != -1) {
                                GDGameLayer gameLayerAsItem2 = (GDGameLayer) this.list.objectArray[this.focusItemIndex];
                                final GDItemAnimationBehavior animationBehaviorBase2 = (GDItemAnimationBehavior) gameLayerAsItem2.getDimensionalBehavior().getAnimationBehavior();
                                animationBehaviorBase2.setFocus(false);
                            }
                            animationBehaviorBase.setFocus(true);
                            this.focusItemIndex = index;
                            height = getHeightToItem(index);
                            traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, false);
                            if (traverse == GDForm.OUTOFITEM) {
                                return 0;
                            } else {
                                return traverse;
                            }
                        }
                    }

                    height = getHeightToItem(topItemIndex);
                    GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[topItemIndex];
                    final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                    traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, false);
                    if (traverse == GDForm.OUTOFITEM) {
                    } else {
                        bottomItemIndex = getTopVisibleIndex(bottom + traverse);
                        if (this.focusItemIndex != -1 && this.focusItemIndex > bottomItemIndex) {
                            GDGameLayer gameLayerAsItem2 = (GDGameLayer) this.list.objectArray[this.focusItemIndex];
                            final GDItemAnimationBehavior animationBehaviorBase2 = (GDItemAnimationBehavior) gameLayerAsItem2.getDimensionalBehavior().getAnimationBehavior();
                            animationBehaviorBase2.setFocus(false);
                            this.focusItemIndex = -1;
                        }
                        return traverse;
                    }
                }
            }
        }
        if (keyCode == Canvas.DOWN) {
            bottomItemIndex = getBottomVisibleIndex(bottom);
            //logUtil.putF("traverse: Canvas.DOWN bottomItemIndex: " + bottomItemIndex, this, gameInputStrings.KEY_PRESSED);
            if (this.focusItemIndex == -1) {
                testItemIndex = bottomItemIndex;
                height = getHeightToItem(testItemIndex);
                GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[testItemIndex];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, false);
            } else {
                testItemIndex = this.focusItemIndex;
                this.logUtil.putF("traverse down: " + testItemIndex, this, gameInputStrings.KEY_PRESSED);
                height = getHeightToItem(testItemIndex);
                GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[testItemIndex];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, true);
            }
            //logUtil.putF("traverse: Canvas.DOWN testItemIndex: " + testItemIndex, this, gameInputStrings.KEY_PRESSED);
            if (traverse == GDForm.OUTOFITEM) {
                GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[testItemIndex];
                final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();

                if (this.focusItemIndex == -1 && animationBehaviorBase.isFocusable()) {

                    animationBehaviorBase.setFocus(true);
                    this.focusItemIndex = testItemIndex;
                }
                return traverse;
            } else {
                //logUtil.putF("traverse: Canvas.DOWN else", this, gameInputStrings.KEY_PRESSED);
                if (testItemIndex < this.size() - 1) {
                    //logUtil.putF("traverse: Canvas.DOWN if", this, gameInputStrings.KEY_PRESSED);
                    for (index = testItemIndex + 1; index <= bottomItemIndex; index++) {
                        GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
                        final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                        if (animationBehaviorBase.isFocusable()) {
                            if (this.focusItemIndex != -1) {
                                GDGameLayer gameLayerAsItem2 = (GDGameLayer) this.list.objectArray[this.focusItemIndex];
                                final GDItemAnimationBehavior animationBehaviorBase2 = (GDItemAnimationBehavior) gameLayerAsItem2.getDimensionalBehavior().getAnimationBehavior();
                                animationBehaviorBase2.setFocus(false);
                            }
                            animationBehaviorBase.setFocus(true);
                            this.focusItemIndex = index;
                            height = getHeightToItem(index);
                            traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, false);
                            if (traverse == GDForm.OUTOFITEM) {
                                return 0;
                            } else {
                                return traverse;
                            }
                        }
                    }
                    height = getHeightToItem(bottomItemIndex);

                    GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[bottomItemIndex];
                    final GDItemAnimationBehavior animationBehaviorBase = (GDItemAnimationBehavior) gameLayerAsItem.getDimensionalBehavior().getAnimationBehavior();
                    traverse = animationBehaviorBase.traverse(keyCode, top - height, bottom - height, false);
                    if (traverse == GDForm.OUTOFITEM) {
                    } else {
                        topItemIndex = getTopVisibleIndex(top + traverse);
                        if (this.focusItemIndex != -1 && this.focusItemIndex < topItemIndex) {
                            GDGameLayer gameLayerAsItem2 = (GDGameLayer) this.list.objectArray[this.focusItemIndex];
                            final GDItemAnimationBehavior animationBehaviorBase2 = (GDItemAnimationBehavior) gameLayerAsItem2.getDimensionalBehavior().getAnimationBehavior();
                            animationBehaviorBase2.setFocus(false);
                            this.focusItemIndex = -1;
                        }
                        return traverse;
                    }
                }
            }
        }

        return 0;
    }

    private int getTopVisibleIndex(int top) {
        int height = 0;

        int size = this.size();
        for (int index = 0; index < size; index++) {
            GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
            height += gameLayerAsItem.getHeight();
            if (height >= top) {
                return index;
            }
        }

        return this.size() - 1;
    }

    private int getBottomVisibleIndex(int bottom) {
        int height = 0;

        int size = this.size();
        for (int index = 0; index < size; index++) {
            GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
            height += gameLayerAsItem.getHeight();
            if (height > bottom) {
                return index;
            }
        }

        return this.size() - 1;
    }

    private int getHeightToItem(int itemIndex) {
        int height = 0;

        for (int index = 0; index < itemIndex; index++) {
            GDGameLayer gameLayerAsItem = (GDGameLayer) this.list.objectArray[index];
            height += gameLayerAsItem.getHeight();
        }

        return height;
    }

    public void reset() {
        this.list.clear();
    }

}
