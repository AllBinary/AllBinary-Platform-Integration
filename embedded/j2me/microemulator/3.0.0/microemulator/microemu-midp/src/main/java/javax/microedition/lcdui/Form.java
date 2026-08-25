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
 */

package javax.microedition.lcdui;

import jsinterop.annotations.JsType;

import java.util.Vector;

import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.FormUI;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;

//TODO implement pointer events

@JsType
public class Form extends Screen 
{
	Item items[] = new Item[4];
	int numOfItems = 0;
	int focusItemIndex;
	
	@JsConstructor
	public Form(String title) 
	{
		super(title);
		super.setUI(DeviceFactory.getDevice().getUIFactory().createFormUI(this));
		
		this.focusItemIndex = -1;
	}

	
	@JsConstructor
	public Form(String title, Item[] items) 
	{
		this(title);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				this.append(items[i]);
			}
		}
	}

	
	@JsMethod
	public int append(Item item) 
	{
		this.verifyItem(item);

		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
			((FormUI) ui).append(item.ui);
		}
		
		if (this.numOfItems + 1 >= this.items.length) {
			Item newitems[] = new Item[this.numOfItems + 4];
			System.arraycopy(items, 0, newitems, 0, numOfItems);
			this.items = newitems;
		}
		this.items[numOfItems] = item;
		numOfItems++;
		
		if (this.focusItemIndex == -1) {
			for (int i = 0; i < numOfItems; i++) {
				if (this.items[i].isFocusable()) {
					this.items[i].setFocus(true);
					this.focusItemIndex = i;
					break;
				}
			}
		}
		
		repaint();

		return (numOfItems - 1);
	}

	
	@JsMethod
	public int append(Image img) 
	{
		return this.append(new ImageItem(null, img, ImageItem.LAYOUT_DEFAULT, null));
	}

	
	@JsMethod
	public int append(String str) 
	{
		if (str == null) {
			throw new NullPointerException();
		}

		return this.append(new StringItem(null, str));
	} 

	
	@JsMethod
	public void delete(int itemNum) 
	{
		this.verifyItemNum(itemNum);

		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
			((FormUI) ui).delete(itemNum);
		}

		// TODO set focus to nearest item if deleted item is currently focused
		this.items[itemNum].setOwner(null);
		System.arraycopy(items, itemNum + 1, items, itemNum, numOfItems - itemNum - 1);
		this.numOfItems--;
		
		if (this.numOfItems == 0) {
			this.focusItemIndex = -1;
		}
		
		repaint();
	}
	
	
	@JsMethod
	public void deleteAll()
	{
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
			((FormUI) ui).deleteAll();
		}

		for (int i = 0; i < this.numOfItems; i++) {
			this.items[i].setOwner(null);
		}
		this.numOfItems = 0;
		this.focusItemIndex = -1;
		
		repaint();
	}

	
	@JsMethod
	public Item get(int itemNum) 
	{
		this.verifyItemNum(itemNum);

		return this.items[itemNum];
	}
	
	
        @Override
	       @JsMethod
	       public int getHeight()
	{
		return super.getHeight();
	}
	
	@Override
	@JsMethod
	public int getWidth()
	{
		return super.getWidth();
	}

	
	@JsMethod
	public void insert(int itemNum, Item item) 
	{
		if (itemNum != this.numOfItems) {
			this.verifyItemNum(itemNum);
		}
		this.verifyItem(item);

		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
			((FormUI) ui).insert(itemNum, item.ui);
		}

		if (this.numOfItems + 1 == this.items.length) {
			Item newitems[] = new Item[this.numOfItems + 4];
			System.arraycopy(items, 0, newitems, 0, numOfItems);
			this.items = newitems;
		}
		System.arraycopy(
			this.items,
			itemNum,
			this.items,
			itemNum + 1,
			numOfItems - itemNum);
		this.items[itemNum] = item;
		this.items[itemNum].setOwner(this);
		numOfItems++;
		
		repaint();
	}

	
	@JsMethod
	public void set(int itemNum, Item item) 
	{
		this.verifyItemNum(itemNum);
		this.verifyItem(item);

		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
			((FormUI) ui).set(itemNum, item.ui);
		}

		// TODO add this to MIDP1
		this.items[itemNum].setOwner(null);
		
		this.items[itemNum] = item;
		this.items[itemNum].setOwner(this);
		
		repaint();
	}

	
	@JsMethod
	public void setItemStateListener(ItemStateListener iListener) 
	{
		((FormUI) ui).setItemStateListener(iListener);
	}

	
	@JsMethod
	public int size() 
	{
		return this.numOfItems;
	}

	
        @Override
	       @JsMethod
	       int paintContent(Graphics g) 
	{
		int contentHeight = 0;
		int translateY;
		for (int i = 0; i < this.numOfItems; i++) {
			translateY = items[i].paint(g);
			g.translate(0, translateY);
			contentHeight += translateY;
		}
		g.translate(0, -contentHeight);

		return contentHeight;
	}
    
    
	@JsMethod
	void fireItemStateListener(Item item) {
		ItemStateListener listener = ((FormUI) ui).getItemStateListener();
        if (listener != null) {
        	listener.itemStateChanged(item);
        }
	}
	
	@JsMethod
	void fireItemStateListener()
    {
		if (this.focusItemIndex >= 0 && this.focusItemIndex < this.items.length)
			this.fireItemStateListener(items[this.focusItemIndex]);
    }
	
        @Override
	       @JsMethod
	       public void keyPressed(int keyCode) 
	{
		if (this.focusItemIndex != -1) {
			if (Display.getGameAction(keyCode) == Canvas.FIRE) {
				this.items[this.focusItemIndex].select();
				// why do we call this here?
				// Andres Navarro
                this.fireItemStateListener();
			} else {
				this.items[this.focusItemIndex].keyPressed(keyCode);
			}
		}

		super.keyPressed(keyCode);
	}

        @Override
	       void showNotify() 
	{
		super.showNotify();

		if (this.focusItemIndex < 0)
			return;
		int heightToItem = this.getHeightToItem(this.focusItemIndex);
		int heightAfterItem = heightToItem + items[this.focusItemIndex].getHeight();
		if (viewPortY > heightToItem) {
			viewPortY = heightToItem;
		} else if ((viewPortY + viewPortHeight) < heightAfterItem) {
			viewPortY = heightAfterItem - viewPortHeight;
		}
	}
	
        @Override
	       @JsMethod
	       void hideNotify()
	{
		super.hideNotify();

		if (this.focusItemIndex > -1) {
			this.items[this.focusItemIndex].setFocus(false);
		}
	}

        @Override
	       @JsMethod
	       int traverse(int gameKeyCode, int top, int bottom) 
	{
		int height, testItemIndex, traverse, i;
		int topItemIndex, bottomItemIndex;

		if (this.numOfItems == 0) {
			return 0;
		}

		if (gameKeyCode == Canvas.UP) {
			topItemIndex = this.getTopVisibleIndex(top);
			if (this.focusItemIndex == -1) {
				testItemIndex = topItemIndex;
				height = this.getHeightToItem(testItemIndex);
				traverse =
					this.items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						false);
			} else {
				testItemIndex = this.focusItemIndex;
				height = this.getHeightToItem(testItemIndex);
				traverse =
					this.items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						true);
			}
			if (traverse != Item.OUTOFITEM) {
				if (this.focusItemIndex == -1
					&& this.items[testItemIndex].isFocusable()) {
					this.items[testItemIndex].setFocus(true);
					this.focusItemIndex = testItemIndex;
				}
				return traverse;
			} else {
				if (testItemIndex > 0) {
					for (i = testItemIndex - 1; i >= topItemIndex; i--) {
						if (this.items[i].isFocusable()) {
							if (this.focusItemIndex != -1) {
								this.items[this.focusItemIndex].setFocus(false);
							}
							this.items[i].setFocus(true);
							this.focusItemIndex = i;
							height = this.getHeightToItem(i);
							traverse =
								this.items[i].traverse(
									gameKeyCode,
									top - height,
									bottom - height,
									false);
							if (traverse == Item.OUTOFITEM) {
								return 0;
							} else {
								return traverse;
							}
						}
					}
					height = this.getHeightToItem(topItemIndex);
					traverse =
						this.items[topItemIndex].traverse(
							gameKeyCode,
							top - height,
							bottom - height,
							false);
					if (traverse == Item.OUTOFITEM) {
					} else {
						bottomItemIndex = this.getTopVisibleIndex(bottom + traverse);
						if (this.focusItemIndex != -1
							&& this.focusItemIndex > bottomItemIndex) {
							this.items[this.focusItemIndex].setFocus(false);
							this.focusItemIndex = -1;
						}
						return traverse;
					}
				}
			}
		}
		if (gameKeyCode == Canvas.DOWN) {
			bottomItemIndex = this.getBottomVisibleIndex(bottom);
			if (this.focusItemIndex == -1) {
				testItemIndex = bottomItemIndex;
				height = this.getHeightToItem(testItemIndex);
				traverse =
					this.items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						false);
			} else {
				testItemIndex = this.focusItemIndex;
				height = this.getHeightToItem(testItemIndex);
				traverse =
					this.items[testItemIndex].traverse(
						gameKeyCode,
						top - height,
						bottom - height,
						true);
			}
			if (traverse != Item.OUTOFITEM) {
				if (this.focusItemIndex == -1
					&& this.items[testItemIndex].isFocusable()) {
					this.items[testItemIndex].setFocus(true);
					this.focusItemIndex = testItemIndex;
				}
				return traverse;
			} else {
				if (testItemIndex < this.numOfItems - 1) {
					for (i = testItemIndex + 1; i <= bottomItemIndex; i++) {
						if (this.items[i].isFocusable()) {
							if (this.focusItemIndex != -1) {
								this.items[this.focusItemIndex].setFocus(false);
							}
							this.items[i].setFocus(true);
							this.focusItemIndex = i;
							height = this.getHeightToItem(i);
							traverse =
								this.items[i].traverse(
									gameKeyCode,
									top - height,
									bottom - height,
									false);
							if (traverse == Item.OUTOFITEM) {
								return 0;
							} else {
								return traverse;
							}
						}
					}
					height = this.getHeightToItem(bottomItemIndex);
					traverse =
						this.items[bottomItemIndex].traverse(
							gameKeyCode,
							top - height,
							bottom - height,
							false);
					if (traverse == Item.OUTOFITEM) {
					} else {
						topItemIndex = this.getTopVisibleIndex(top + traverse);
						if (this.focusItemIndex != -1
							&& this.focusItemIndex < topItemIndex) {
							this.items[this.focusItemIndex].setFocus(false);
							this.focusItemIndex = -1;
						}
						return traverse;
					}
				}
			}
		}

		return 0;
	}

	
	@JsMethod
	private int getTopVisibleIndex(int top) 
	{
		int height = 0;

		for (int i = 0; i < this.numOfItems; i++) {
			height += items[i].getHeight();
			if (height >= top) {
				return i;
			}
		}

		return numOfItems - 1;
	}

	
	@JsMethod
	private int getBottomVisibleIndex(int bottom) 
	{
		int height = 0;

		for (int i = 0; i < this.numOfItems; i++) {
			height += items[i].getHeight();
			if (height > bottom) {
				return i;
			}
		}

		return numOfItems - 1;
	}

	
	@JsMethod
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
	@JsMethod
	private void verifyItem(Item item) 
	{
		// Check that we are being passed valid items
		if (item == null) {
			throw new NullPointerException("item is null");
		}
		if (item.getOwner() != null) {
			throw new IllegalStateException("item is already owned");
		}
		// All is ok make ourselves the owner
		item.setOwner(this);
	}

	/**
	 * Verify that the index passed in is valid for this form. ie within the
	 * range 0..size-1
	 * 
	 * @param itemNum the number of the item
	 * @throws IndexOutOfBoundsException
	 */
	@JsMethod
	private void verifyItemNum(int itemNum) 
	{
		if (itemNum < 0 || itemNum >= this.numOfItems) {
			throw new IndexOutOfBoundsException("item number is outside range of Form");
		}
	}

        @Override
	       @JsMethod
	       public Vector getCommands() {
		Vector formCommands = super.getCommands();
		if (this.focusItemIndex < 0)
			return formCommands;
		
		Item item = items[this.focusItemIndex];
		Vector itemCommands = item.commands;
		if (itemCommands.isEmpty())
			return formCommands;

		// if the focused item has commands we
		// need a new Vector with the special commands
		// for the items (see code at the end of class
		// Command and in CommandManager for more info)
		Vector allCommands = new Vector();
		for (int i = 0; i < formCommands.size(); i++)
			allCommands.add(formCommands.elementAt(i));
		
		for (int i = 0; i < itemCommands.size(); i++) {
			Command itemCommand = (Command)itemCommands.elementAt(i);
			itemCommand = itemCommand.getItemCommand(item);
			allCommands.add(itemCommand);
		}
		return allCommands;
	}
}
