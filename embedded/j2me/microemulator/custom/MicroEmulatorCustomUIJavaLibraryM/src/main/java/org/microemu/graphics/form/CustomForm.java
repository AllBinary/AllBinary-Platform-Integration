/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;

import jsinterop.annotations.JsType;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;

import org.allbinary.graphics.form.item.ABCustomItem;
import org.allbinary.graphics.form.item.ABCustomItemStateListener;
import org.allbinary.string.CommonSeps;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.game.input.GameKey;
import org.allbinary.game.input.GameKeyFactory;
import org.allbinary.game.input.Input;
import org.allbinary.game.input.InputFactory;
import org.allbinary.game.input.PlatformFormInputMappingFactory;
import org.allbinary.game.input.PlatformKeyFactory;
import org.allbinary.graphics.GPoint;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.color.BasicColorFactory;
import org.allbinary.graphics.form.item.CustomItemState;
import org.allbinary.layer.AllBinaryLayerManager;
import org.allbinary.logic.ABSystemWrapper;
import org.allbinary.logic.NullUtil;
import org.allbinary.logic.string.StringUtil;
import org.allbinary.string.CommonLabels;

@JsType
public class CustomForm extends CustomScreen {

    private static Object NULL_SCREEN = NullUtil.getInstance().NULL_OBJECT;

    @JsMethod
    public static Form getNullForm() {
        if (CustomForm.NULL_SCREEN == NullUtil.getInstance().NULL_OBJECT) {
            CustomForm.NULL_SCREEN = new Form(StringUtil.getInstance().EMPTY_STRING);
        }

        return (Form) CustomForm.NULL_SCREEN;
    }

    @JsProperty
    protected final LogUtil logUtil = LogUtil.getInstance();
    
    private final ABSystemWrapper systemWrapper = ABSystemWrapper.getInstance();

    private final GameKeyFactory gameKeyFactory = GameKeyFactory.getInstance();
    @JsProperty
    protected final InputFactory inputFactory = InputFactory.getInstance();
    
    private ABCustomItem[] items = new ABCustomItem[16];
    private int numOfItems = 0;
    private ABCustomItemStateListener itemStateListener = CustomItemState.NULL_CUSTOM_ITEM_STATE;
    private int selectedIndex;

    int viewPortY = 0;
    final int viewPortHeight = this.getHeight() - this.title.getHeight() - 1;
    
    @JsConstructor
    public CustomForm(final String title, final ABCustomItem[] items, final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor) {
        super(title, backgroundBasicColor, foregroundBasicColor);

        this.items = new ABCustomItem[items.length];
        systemWrapper.arraycopy(items, 0, this.items, 0, items.length);
        this.numOfItems = this.items.length;
        for (int i = 0; i < this.numOfItems; i++) {
            this.verifyItem(this.items[i]);
        }

        this.setSelectedIndex(-1);
    }

    @JsMethod
    public ABCustomItem[] getAllitems() {
        return this.items;
    }

    @JsMethod
    public void processInput(AllBinaryLayerManager layerManager) throws Exception {

    }

    @JsMethod
    public void initInputProcessors() {

    }

    @JsMethod
    public int append(ABCustomItem item) {
        this.verifyItem(item);

        if (this.numOfItems + 1 >= this.items.length) {
            ABCustomItem newitems[] = new ABCustomItem[this.numOfItems + 4];
            systemWrapper.arraycopy(this.items, 0, newitems, 0, this.numOfItems);
            this.items = newitems;
        }
        this.items[this.numOfItems] = item;
        this.numOfItems++;

        return (this.numOfItems - 1);
    }

    /*
	public int append(Image img) 
	{
		return append(new ImageItem(null, img, ImageItem.LAYOUT_DEFAULT, null));
	}
	
	public int append(String str) 
	{
		if (str == null) {
			throw new NullPointerException();
		}

		return append(new StringItem(null, str));
	}
     */
    @JsMethod
    public void delete(int itemNum) {
        this.verifyItemNum(itemNum);

        this.items[itemNum].setOwner(CustomForm.getNullForm());
        systemWrapper.arraycopy(this.items, itemNum + 1, this.items, itemNum, this.numOfItems - itemNum - 1);
        this.numOfItems--;
    }

    @JsMethod
    public void deleteAll() {
        for (int i = 0; i < this.numOfItems; i++) {
            this.items[i].setOwner(CustomForm.getNullForm());
        }
        this.numOfItems = 0;
    }

    @JsMethod
    public ABCustomItem get(int itemNum) {
        this.verifyItemNum(itemNum);

        return this.items[itemNum];
    }

    /*
	public int getHeight()
	{
		return super.getHeight();
	}
	
	
	public int getWidth()
	{
		return super.getWidth();
	}
     */
    @JsMethod
    public void insert(int itemNum, ABCustomItem item) {
        this.verifyItemNum(itemNum);
        this.verifyItem(item);

        if (this.numOfItems + 1 == this.items.length) {
            ABCustomItem[] newitems = new ABCustomItem[this.numOfItems + 4];
            systemWrapper.arraycopy(this.items, 0, newitems, 0, this.numOfItems);
            this.items = newitems;
        }
        systemWrapper.arraycopy(this.items, itemNum, this.items, itemNum + 1, this.numOfItems - itemNum);
        this.items[itemNum] = item;
        //items[itemNum].setOwner(this);
        this.numOfItems++;

    }

    @JsMethod
    public void set(int itemNum, ABCustomItem item) {
        this.verifyItemNum(itemNum);
        this.verifyItem(item);

        // TODO add this to MIDP1
        this.items[itemNum].setOwner(CustomForm.getNullForm());

        this.items[itemNum] = item;
        //items[itemNum].setOwner(this);
    }

    @JsMethod
    public void setItemStateListener(ABCustomItemStateListener iListener) {
        this.itemStateListener = iListener;
    }

    @JsMethod
    public int size() {
        return this.numOfItems;
    }

    @JsMethod
    protected int getItemTotalHeight(int index) {
        return this.items[index].getHeight();
    }

    @JsMethod
    protected int getItemIndexAt(GPoint point) {
        int beginY = 0;
        int endY = 0;
        for (int index = 0; index < this.numOfItems; index++) {
            //logUtil.putF("Painting: " + items[i].getLabel(), this, "paint");
            endY += this.getItemTotalHeight(index);

            //logUtil.putF(point.getY() + ">=" + beginY + " && " + point.getY() + "<" + endY, this, "getItemIndexAt");
            if (point.getY() >= beginY && point.getY() < endY) {
                return index;
            }

            beginY = endY;
        }
        return -1;
    }

    private final int LIGHT_GREY = BasicColorFactory.getInstance().LIGHT_GREY.intValue();

    @Override
    @JsMethod
    int paintContent(Graphics graphics) {
        //logUtil.putF(commonStrings.START_LABEL + numOfItems, this, "paintContent");

        int contentHeight = 0;
        int translateY = 0;

        for (int index = 0; index < this.numOfItems; index++) {
            //logUtil.putF("Painting: " + items[i].getLabel(), this, "paint");
            if (this.items[index].hasFocus()) {
                graphics.setColor(this.LIGHT_GREY);
                graphics.drawRect(0, 0, this.getWidth(), this.getItemTotalHeight(index));
            }

            translateY = this.items[index].paint(graphics);
            graphics.translate(0, translateY);
            contentHeight += translateY;
        }

        graphics.translate(0, -contentHeight);

        return contentHeight;
    }

    @JsMethod
    void fireItemStateListenerForCustomItem(final ABCustomItem item) {
        if (this.itemStateListener != null) {
            this.itemStateListener.itemStateChanged(item);
        }
    }

    @JsMethod
    void fireItemStateListener() {
        final int selectedIndex = this.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < this.items.length) {
            this.fireItemStateListenerForCustomItem(this.items[selectedIndex]);
        }
    }

    @Override
    void hideNotify() {
        super.hideNotify();
        // TODO eliminate this to
        // allow focus restoring
        for (int i = 0; i < this.numOfItems; i++) {
            if (this.items[i].isFocusable() && this.items[i].hasFocus()) {
                this.items[i].setFocus(false);
                this.setSelectedIndex(-1);
                break;
            }
        }
    }

    @JsMethod
    public void traverseFromKey(int keyCode) {
        this.viewPortY = 0;
        this.viewPortY += this.traverse(keyCode, this.viewPortY, this.viewPortY + this.viewPortHeight);
    }


    @Override
    @JsMethod
    public void keyPressed(final int keyCode) {
        try {
            GameKey gameKey =
                PlatformFormInputMappingFactory.getInstance().getOrCreate().getInstance(keyCode);
            //( (InputToGameKeyMapping) 
            //      PlatformInputMappingFactory.getInstance()).getInstance(keyCode);

            //PreLogUtil.put(commonStrings.START + gameKey, this, gameInputStrings.KEY_PRESSED);
            final Input input = this.inputFactory.getInstanceById(keyCode);
            final PlatformKeyFactory platformKeyFactory = PlatformKeyFactory.getInstance();

            if (platformKeyFactory.isEnter(input)) {
                gameKey = this.gameKeyFactory.DOWN;
            }

            if (gameKey == this.gameKeyFactory.UP || gameKey == this.gameKeyFactory.DOWN) {
                this.logUtil.putF(gameKey.toString(), this, this.gameInputStrings.KEY_PRESSED);

                this.traverseFromKey(gameKey.getId());
            } else {
                //logUtil.putF(commonStrings.START, this, gameInputStrings.KEY_PRESSED);

                if (this.getSelectedIndex() != -1) {
                    /*
	                //Display.getGameAction()
	                if (keyCode == Canvas.FIRE) {
	                    this.items[this.selectedIndex].select();
	                    // why do we call this here?
	                    // Andres Navarro
	                    fireItemStateListener();
	                } else {
                     */
                    this.items[this.getSelectedIndex()].keyPressed(keyCode);
                    //}
                }

                super.keyPressed(keyCode);
            }
        } catch (Exception e) {
            this.logUtil.put(this.commonStrings.EXCEPTION, this, this.gameInputStrings.KEY_PRESSED, e);
        }
    }

    /*
	void showNotify() 
	{
		if (focusItemIndex == -2) {
			focusItemIndex = -1;

			for (int i = 0; i < this.numOfItems; i++) {
				if (this.items[i].isFocusable()) {
					this.items[i].setFocus(true);
					focusItemIndex = i;
					break;
				}
			}
		}
		if (focusItemIndex < 0)
			return;
		int heightToItem = getHeightToItem(focusItemIndex);
		int heightAfterItem = heightToItem + this.items[focusItemIndex].getHeight();
		
		//if (viewPortY > heightToItem) {
//			viewPortY = heightToItem;
	//	} else if ((viewPortY + viewPortHeight) < heightAfterItem) {
		//	viewPortY = heightAfterItem - viewPortHeight;
		//}
	}
     */
    @JsMethod
    private int getTotalTraversable() {
        int total = 0;

        for (int index = this.items.length - 1; index >= 0; index--) {
            if (this.items[index].isFocusable()) {
                total++;
            }

        }

        return total;
    }

    @Override
    @JsMethod
    int traverse(int gameKeyCode, int top, int bottom) {
        //logUtil.put(commonStrings.START, this, "traverse");

        int height, testItemIndex, traverse, i;
        int topItemIndex, bottomItemIndex;

        if (this.numOfItems == 0) {
            return 0;
        }

        if (this.getTotalTraversable() == 0) {
            return 0;
        }

        if (gameKeyCode == Canvas.UP) {
            topItemIndex = this.getTopVisibleIndex(top);
            if (this.getSelectedIndex() == -1) {
                testItemIndex = topItemIndex;
                height = this.getHeightToItem(testItemIndex);
                traverse =
                    this.items[testItemIndex].traverse(
                        gameKeyCode,
                        top - height,
                        bottom - height,
                        false);
            } else {
                testItemIndex = this.getSelectedIndex();
                height = this.getHeightToItem(testItemIndex);
                traverse =
                    this.items[testItemIndex].traverse(
                        gameKeyCode,
                        top - height,
                        bottom - height,
                        true);
            }
            if (traverse == ABCustomItem.OUTOFITEM) {
                if (this.getSelectedIndex() == -1
                    && this.items[testItemIndex].isFocusable()) {
                    this.items[testItemIndex].setFocus(true);
                    this.setSelectedIndex(testItemIndex);
                }
                return traverse;
            } else {
                if (testItemIndex > 0) {
                    // Czy istnieje obiekt focusable powyzej testItemIndex
                    // widoczny na ekranie
                    // jesli tak to zrob na nim traverse(false) i return
                    // traverse
                    for (i = testItemIndex - 1; i >= topItemIndex; i--) {
                        if (this.items[i].isFocusable()) {
                            if (this.getSelectedIndex() != -1) {
                                this.items[this.getSelectedIndex()].setFocus(false);
                            }
                            this.items[i].setFocus(true);
                            this.setSelectedIndex(i);
                            height = this.getHeightToItem(i);
                            traverse =
                                this.items[i].traverse(
                                    gameKeyCode,
                                    top - height,
                                    bottom - height,
                                    false);
                            if (traverse == ABCustomItem.OUTOFITEM) {
                                return 0;
                            } else {
                                return traverse;
                            }
                        }
                    }
                    // Na najnizszym widocznym item zrob traverse(false)
                    height = this.getHeightToItem(topItemIndex);
                    traverse =
                        this.items[topItemIndex].traverse(
                            gameKeyCode,
                            top - height,
                            bottom - height,
                            false);
                    if (traverse == ABCustomItem.OUTOFITEM) {
                    } else {
                        // Sprawdzenie czy znajduje sie powyzej na ekranie
                        // focusable item
                        // jesli tak zrob co trzeba
                        bottomItemIndex = this.getTopVisibleIndex(bottom + traverse);
                        if (this.getSelectedIndex() != -1
                            && this.getSelectedIndex() > bottomItemIndex) {
                            this.items[this.getSelectedIndex()].setFocus(false);
                            this.setSelectedIndex(-1);
                        }
                        return traverse;
                    }
                }
            }
        }
        if (gameKeyCode == Canvas.DOWN) {

            bottomItemIndex = this.getBottomVisibleIndex(bottom);
            if (this.getSelectedIndex() == -1) {
                testItemIndex = bottomItemIndex;
                height = this.getHeightToItem(testItemIndex);
                traverse =
                    this.items[testItemIndex].traverse(
                        gameKeyCode,
                        top - height,
                        bottom - height,
                        false);
            } else {

                testItemIndex = this.getSelectedIndex();
                height = this.getHeightToItem(testItemIndex);
                traverse =
                    this.items[testItemIndex].traverse(
                        gameKeyCode,
                        top - height,
                        bottom - height,
                        true);
            }

            if (traverse == ABCustomItem.OUTOFITEM) {
                if (this.getSelectedIndex() == -1
                    && this.items[testItemIndex].isFocusable()) {
                    this.items[testItemIndex].setFocus(true);
                    this.setSelectedIndex(testItemIndex);
                }
                return traverse;
            } else {

                if (testItemIndex < this.numOfItems - 1) {
                    // Czy istnieje obiekt focusable ponizej testItemIndex
                    // widoczny na ekranie
                    // jesli tak to zrob na nim traverse(false) i return
                    // traverse
                    for (i = testItemIndex + 1; i <= bottomItemIndex; i++) {
                        if (this.items[i].isFocusable()) {
                            if (this.getSelectedIndex() != -1) {
                                this.items[this.getSelectedIndex()].setFocus(false);
                            }
                            this.items[i].setFocus(true);
                            this.setSelectedIndex(i);
                            height = this.getHeightToItem(i);
                            traverse =
                                this.items[i].traverse(
                                    gameKeyCode,
                                    top - height,
                                    bottom - height,
                                    false);
                            if (traverse == ABCustomItem.OUTOFITEM) {
                                return 0;
                            } else {
                                return traverse;
                            }
                        }
                    }
                    // Na najnizszym widocznym item zrob traverse(false)
                    height = this.getHeightToItem(bottomItemIndex);
                    traverse =
                        this.items[bottomItemIndex].traverse(
                            gameKeyCode,
                            top - height,
                            bottom - height,
                            false);
                    if (traverse == ABCustomItem.OUTOFITEM) {
                    } else {
                        // Sprawdzenie czy znajduje sie powyzej na ekranie
                        // focusable item
                        // jesli tak zrob co trzeba
                        topItemIndex = this.getTopVisibleIndex(top + traverse);
                        if (this.getSelectedIndex() != -1
                            && this.getSelectedIndex() < topItemIndex) {
                            this.items[this.getSelectedIndex()].setFocus(false);
                            this.setSelectedIndex(-1);
                        }
                        return traverse;
                    }
                }
            }
        }

        return 0;
    }

    @JsMethod
    private int getTopVisibleIndex(int top) {
        int height = 0;

        for (int i = 0; i < this.numOfItems; i++) {
            height += this.items[i].getHeight();
            if (height >= top) {
                return i;
            }
        }

        return this.numOfItems - 1;
    }

    @JsMethod
    private int getBottomVisibleIndex(int bottom) {
        int height = 0;

        for (int i = 0; i < this.numOfItems; i++) {
            height += this.items[i].getHeight();
            if (height > bottom) {
                return i;
            }
        }

        return this.numOfItems - 1;
    }

    @JsMethod
    private int getHeightToItem(int itemIndex) {
        int height = 0;

        for (int i = 0; i < itemIndex; i++) {
            height += this.items[i].getHeight();
        }

        return height;
    }

    /**
     * Verify that the item is non null and is not owned by this form or anyone
     * else. If all is ok set the owner to this Form
     *
     * @param item the item to be verified
     * @throws IllegalStateException
     * @throws NullPointerException
     */
    @JsMethod
    private void verifyItem(ABCustomItem item) {
        // Check that we are being passed valid items
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        /*
		if (item.getOwner() != null) {
			throw new IllegalStateException("item is already owned");
		}

		item.setOwner(this);
         */
    }

    /**
     * Verify that the index passed in is valid for this form. ie within the
     * range 0..size-1
     *
     * @param itemNum the number of the item
     * @throws IndexOutOfBoundsException
     */
    @JsMethod
    private void verifyItemNum(int itemNum) {
        if (itemNum < 0 || itemNum >= this.numOfItems) {
            throw new IndexOutOfBoundsException("item number is outside range of Form");
        }
    }

    private static final String SET_SELECTED_INDEX = "setSelectedIndex";

    @JsMethod
    public void setSelectedIndex(int selectedIndex) {
        final CommonLabels commonLabels = CommonLabels.getInstance();
        final StringMaker stringBuffer = new StringMaker();

        stringBuffer.append(commonLabels.START_LABEL);
        stringBuffer.append(CommonSeps.getInstance().SPACE);
        stringBuffer.append(commonLabels.INDEX_LABEL);
        stringBuffer.appendint(selectedIndex);

        this.logUtil.putF(stringBuffer.toString(), this, CustomForm.SET_SELECTED_INDEX);

        this.selectedIndex = selectedIndex;
    }

    @JsMethod
    public int getSelectedIndex() {
        return this.selectedIndex;
    }
}
