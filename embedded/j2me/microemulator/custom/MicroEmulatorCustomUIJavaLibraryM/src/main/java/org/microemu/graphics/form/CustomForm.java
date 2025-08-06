/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.NullCanvas;

import org.allbinary.graphics.form.item.CustomItem;
import org.allbinary.graphics.form.item.CustomItemStateListener;

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
import org.allbinary.string.CommonLabels;

public class CustomForm extends CustomScreen 
{
    protected final LogUtil logUtil = LogUtil.getInstance();
    
    private CustomItem[] items = new CustomItem[16];
	private int numOfItems = 0;
	private CustomItemStateListener itemStateListener = CustomItemState.NULL_CUSTOM_ITEM_STATE;
	private int selectedIndex;

	public CustomForm(String title, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor) 
	{
		super(title, backgroundBasicColor, foregroundBasicColor);
		
		this.setSelectedIndex(-1);
	}

    public CustomForm(String title, CustomItem[] items, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor) 
    {
        this(title, backgroundBasicColor, foregroundBasicColor);

        if (items != null) 
        {
            this.items = new CustomItem[items.length];
            System.arraycopy(items, 0, this.items, 0, items.length);
            numOfItems = this.items.length;
            for (int i = 0; i < numOfItems; i++) 
            {
                verifyItem(this.items[i]);
            }
        }
    }

    public void processInput(AllBinaryLayerManager layerManager) throws Exception
    {
        
    }
    
    public void initInputProcessors()
    {
        
    }
    
	public int append(CustomItem item) 
	{
		verifyItem(item);

		if (numOfItems + 1 >= items.length) {
			CustomItem newitems[] = new CustomItem[numOfItems + 4];
			System.arraycopy(items, 0, newitems, 0, numOfItems);
			items = newitems;
		}
		items[numOfItems] = item;
		numOfItems++;

		return (numOfItems - 1);
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
	
	public void delete(int itemNum) 
	{
		verifyItemNum(itemNum);

		items[itemNum].setOwner(NullCanvas.NULL_SCREEN);
		System.arraycopy(items, itemNum + 1, items, itemNum, numOfItems - itemNum - 1);
		numOfItems--;
	}
	
	
	public void deleteAll()
	{
		for (int i = 0; i < numOfItems; i++) {
			items[i].setOwner(NullCanvas.NULL_SCREEN);
		}
		numOfItems = 0;
	}

	
	public CustomItem get(int itemNum) 
	{
		verifyItemNum(itemNum);

		return items[itemNum];
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

	
	public void insert(int itemNum, CustomItem item) 
	{
		verifyItemNum(itemNum);
		verifyItem(item);

		if (numOfItems + 1 == items.length) {
			CustomItem newitems[] = new CustomItem[numOfItems + 4];
			System.arraycopy(items, 0, newitems, 0, numOfItems);
			items = newitems;
		}
		System.arraycopy(
			items,
			itemNum,
			items,
			itemNum + 1,
			numOfItems - itemNum);
		items[itemNum] = item;
		//items[itemNum].setOwner(this);
		numOfItems++;
		
	}

	
	public void set(int itemNum, CustomItem item) 
	{
		verifyItemNum(itemNum);
		verifyItem(item);

		// TODO add this to MIDP1
		items[itemNum].setOwner(NullCanvas.NULL_SCREEN);
		
		items[itemNum] = item;
		//items[itemNum].setOwner(this);
	}

	
	public void setItemStateListener(CustomItemStateListener iListener) 
	{
		itemStateListener = iListener;
	}

	
	public int size() 
	{
		return numOfItems;
	}
    
	protected int getItemTotalHeight(int index)
	{
	    return items[index].getHeight();
	}
	
    protected int getItemIndexAt(GPoint point)
    {
        int beginY = 0;
        int endY = 0;
        for (int index = 0; index < this.numOfItems; index++)
        {
            //logUtil.put("Painting: " + items[i].getLabel(), this, "paint");
            endY += this.getItemTotalHeight(index);
            
            //logUtil.put(point.getY() + ">=" + beginY + " && " + point.getY() + "<" + endY, this, "getItemIndexAt");
            
            if(point.getY() >= beginY && point.getY() < endY )
            {
                return index;
            }

            beginY = endY;
        }
        return -1;
    }

    private final int LIGHT_GREY = BasicColorFactory.getInstance().LIGHT_GREY.intValue();
            
    @Override
	int paintContent(Graphics graphics) 
	{
	    //logUtil.put(commonStrings.START_LABEL + numOfItems, this, "paintContent");

	    int contentHeight = 0;
		int translateY = 0;

		for (int index = 0; index < numOfItems; index++)
		{
			//logUtil.put("Painting: " + items[i].getLabel(), this, "paint");
		    if(items[index].hasFocus())
		    {
	            graphics.setColor(LIGHT_GREY);
	            graphics.drawRect(0, 0, this.getWidth(), this.getItemTotalHeight(index));
		    }

			translateY = items[index].paint(graphics);
			graphics.translate(0, translateY);
			contentHeight += translateY;
		}

		graphics.translate(0, -contentHeight);

		return contentHeight;
	}
    
	void fireItemStateListener(CustomItem item) {
        if (itemStateListener != null) {
            itemStateListener.itemStateChanged(item);
        }
	}
	
	void fireItemStateListener()
    {
		if (getSelectedIndex() >= 0 && getSelectedIndex() < items.length)
			fireItemStateListener(items[getSelectedIndex()]);
    }

	
        @Override
	void hideNotify() 
	{
		super.hideNotify();
		// TODO eliminate this to
		// allow focus restoring
		for (int i = 0; i < numOfItems; i++) {
			if (items[i].isFocusable() && items[i].hasFocus()) {
				items[i].setFocus(false);
				setSelectedIndex(-1);
				break;
			}
		}
	}


    int viewPortY = 0;
    final int viewPortHeight = getHeight() - this.title.getHeight() - 1;
    
    public void traverse(int keyCode)
    {
        viewPortY = 0;
        viewPortY += traverse(keyCode, viewPortY, viewPortY + viewPortHeight);        
    }
	
    private final GameKeyFactory gameKeyFactory = GameKeyFactory.getInstance();
    
    protected final InputFactory inputFactory = InputFactory.getInstance();
    
    @Override
	public void keyPressed(int keyCode) 
	{
		try
		{
            GameKey gameKey =
                PlatformFormInputMappingFactory.getInstance().getOrCreate().getInstance(keyCode);
                //( (InputToGameKeyMapping) 
                  //      PlatformInputMappingFactory.getInstance()).getInstance(keyCode);

            //PreLogUtil.put(commonStrings.START + gameKey, this, gameInputStrings.KEY_PRESSED);
            
            Input input = inputFactory.getInstance(keyCode);
            PlatformKeyFactory platformKeyFactory = PlatformKeyFactory.getInstance();
            
            if(platformKeyFactory.isEnter(input))
            {
                gameKey = gameKeyFactory.DOWN;
            }

		    if (gameKey == gameKeyFactory.UP || gameKey == gameKeyFactory.DOWN)
		    {
		        logUtil.put(gameKey.toString(), this, gameInputStrings.KEY_PRESSED);
		        
                this.traverse(gameKey.getId());
		    }
		    else
		    {
	            //logUtil.put(commonStrings.START, this, gameInputStrings.KEY_PRESSED);

	            if (getSelectedIndex() != -1) {
	                /*
	                //Display.getGameAction()
	                if (keyCode == Canvas.FIRE) {
	                    items[selectedIndex].select();
	                    // why do we call this here?
	                    // Andres Navarro
	                    fireItemStateListener();
	                } else {
	                */
	                    items[getSelectedIndex()].keyPressed(keyCode);
	                //}
	            }

	            super.keyPressed(keyCode);
		    }
		}
		catch(Exception e)
		{
		    logUtil.put(commonStrings.EXCEPTION, this, gameInputStrings.KEY_PRESSED, e);
		}
	}

	/*
	void showNotify() 
	{
		if (focusItemIndex == -2) {
			focusItemIndex = -1;

			for (int i = 0; i < numOfItems; i++) {
				if (items[i].isFocusable()) {
					items[i].setFocus(true);
					focusItemIndex = i;
					break;
				}
			}
		}
		if (focusItemIndex < 0)
			return;
		int heightToItem = getHeightToItem(focusItemIndex);
		int heightAfterItem = heightToItem + items[focusItemIndex].getHeight();
		
		//if (viewPortY > heightToItem) {
//			viewPortY = heightToItem;
	//	} else if ((viewPortY + viewPortHeight) < heightAfterItem) {
		//	viewPortY = heightAfterItem - viewPortHeight;
		//}
	}
	*/
	
	private int getTotalTraversable()
	{
	    int total = 0;
	    
	    for(int index = this.items.length - 1; index >= 0; index--)
	    {
	        if(this.items[index].isFocusable())
	        {
	            total++;
	        }
	        
	    }
	    
	    return total;
	}
	
        @Override
	int traverse(int gameKeyCode, int top, int bottom) 
	{
	    //logUtil.put(commonStrings.START, this, "traverse");

		int height, testItemIndex, traverse, i;
		int topItemIndex, bottomItemIndex;

		if (numOfItems == 0) {
			return 0;
		}
		
		if(this.getTotalTraversable() == 0)
		{
		    return 0;
		}
		
		if (gameKeyCode == Canvas.UP) {
			topItemIndex = getTopVisibleIndex(top);
			if (getSelectedIndex() == -1) {
				testItemIndex = topItemIndex;
				height = getHeightToItem(testItemIndex);
				traverse =
					items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						false);
			} else {
				testItemIndex = getSelectedIndex();
				height = getHeightToItem(testItemIndex);
				traverse =
					items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						true);
			}
			if (traverse == CustomItem.OUTOFITEM) {
				if (getSelectedIndex() == -1
					&& items[testItemIndex].isFocusable()) {
				    items[testItemIndex].setFocus(true);
					setSelectedIndex(testItemIndex);
				}
				return traverse;
			} else {
				if (testItemIndex > 0) {
					// Czy istnieje obiekt focusable powyzej testItemIndex
					// widoczny na ekranie
					// jesli tak to zrob na nim traverse(false) i return
					// traverse
					for (i = testItemIndex - 1; i >= topItemIndex; i--) {
						if (items[i].isFocusable()) {
							if (getSelectedIndex() != -1) {
								items[getSelectedIndex()].setFocus(false);
							}
							items[i].setFocus(true);
							setSelectedIndex(i);
							height = getHeightToItem(i);
							traverse =
								items[i].traverse(
									gameKeyCode,
									top - height,
									bottom - height,
									false);
							if (traverse == CustomItem.OUTOFITEM) {
								return 0;
							} else {
								return traverse;
							}
						}
					}
					// Na najnizszym widocznym item zrob traverse(false)
					height = getHeightToItem(topItemIndex);
					traverse =
						items[topItemIndex].traverse(
							gameKeyCode,
							top - height,
							bottom - height,
							false);
					if (traverse == CustomItem.OUTOFITEM) {
					} else {
						// Sprawdzenie czy znajduje sie powyzej na ekranie
						// focusable item
						// jesli tak zrob co trzeba
						bottomItemIndex = getTopVisibleIndex(bottom + traverse);
						if (getSelectedIndex() != -1
							&& getSelectedIndex() > bottomItemIndex) {
							items[getSelectedIndex()].setFocus(false);
							setSelectedIndex(-1);
						}
						return traverse;
					}
				}
			}
		}
		if (gameKeyCode == Canvas.DOWN) {
		    		    
			bottomItemIndex = getBottomVisibleIndex(bottom);
			if (getSelectedIndex() == -1) {
				testItemIndex = bottomItemIndex;
				height = getHeightToItem(testItemIndex);
				traverse =
					items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						false);
			} else {
			    
				testItemIndex = getSelectedIndex();
				height = getHeightToItem(testItemIndex);
				traverse =
					items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						true);
			}
			
			if (traverse == CustomItem.OUTOFITEM) {
				if (getSelectedIndex() == -1
					&& items[testItemIndex].isFocusable()) {
					items[testItemIndex].setFocus(true);
					setSelectedIndex(testItemIndex);
				}
				return traverse;
			} else {
			    
				if (testItemIndex < numOfItems - 1) {
					// Czy istnieje obiekt focusable ponizej testItemIndex
					// widoczny na ekranie
					// jesli tak to zrob na nim traverse(false) i return
					// traverse
					for (i = testItemIndex + 1; i <= bottomItemIndex; i++) {
						if (items[i].isFocusable()) {
							if (getSelectedIndex() != -1) {
								items[getSelectedIndex()].setFocus(false);
							}
							items[i].setFocus(true);
							setSelectedIndex(i);
							height = getHeightToItem(i);
							traverse =
								items[i].traverse(
									gameKeyCode,
									top - height,
									bottom - height,
									false);
							if (traverse == CustomItem.OUTOFITEM) {
								return 0;
							} else {
								return traverse;
							}
						}
					}
					// Na najnizszym widocznym item zrob traverse(false)
					height = getHeightToItem(bottomItemIndex);
					traverse =
						items[bottomItemIndex].traverse(
							gameKeyCode,
							top - height,
							bottom - height,
							false);
					if (traverse == CustomItem.OUTOFITEM) {
					} else {
						// Sprawdzenie czy znajduje sie powyzej na ekranie
						// focusable item
						// jesli tak zrob co trzeba
						topItemIndex = getTopVisibleIndex(top + traverse);
						if (getSelectedIndex() != -1
							&& getSelectedIndex() < topItemIndex) {
							items[getSelectedIndex()].setFocus(false);
							setSelectedIndex(-1);
						}
						return traverse;
					}
				}
			}
		}

		return 0;
	}

	
	private int getTopVisibleIndex(int top) 
	{
		int height = 0;

		for (int i = 0; i < numOfItems; i++) {
			height += items[i].getHeight();
			if (height >= top) {
				return i;
			}
		}

		return numOfItems - 1;
	}

	
	private int getBottomVisibleIndex(int bottom) 
	{
		int height = 0;

		for (int i = 0; i < numOfItems; i++) {
			height += items[i].getHeight();
			if (height > bottom) {
				return i;
			}
		}

		return numOfItems - 1;
	}

	
	private int getHeightToItem(int itemIndex) 
	{
		int height = 0;

		for (int i = 0; i < itemIndex; i++) {
			height += items[i].getHeight();
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
	private void verifyItem(CustomItem item) 
	{
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
	private void verifyItemNum(int itemNum) 
	{
		if (itemNum < 0 || itemNum >= numOfItems) {
			throw new IndexOutOfBoundsException("item number is outside range of Form");
		}
	}

	private static final String SET_SELECTED_INDEX = "setSelectedIndex";
	
    public void setSelectedIndex(int selectedIndex)
    {
        final StringMaker stringBuffer = new StringMaker();
        
        stringBuffer.append(CommonLabels.getInstance().START_LABEL);
        stringBuffer.append(CommonSeps.getInstance().SPACE);
        stringBuffer.append(CommonLabels.getInstance().INDEX_LABEL);
        stringBuffer.append(selectedIndex);
        
        logUtil.put(stringBuffer.toString(), this, SET_SELECTED_INDEX);
        
        this.selectedIndex = selectedIndex;
    }

    public int getSelectedIndex()
    {
        return selectedIndex;
    }
}
