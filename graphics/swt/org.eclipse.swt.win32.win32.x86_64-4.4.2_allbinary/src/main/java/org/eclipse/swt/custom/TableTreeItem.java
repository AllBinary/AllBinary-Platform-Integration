/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.custom;


import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * A TableTreeItem is a selectable user interface object
 * that represents an item in a hierarchy of items in a
 * TableTree.
 * 
 * @deprecated As of 3.1 use Tree, TreeItem and TreeColumn
 */
//@Deprecated
public class TableTreeItem extends Item {
	TableItem tableItem;
	TableTree parent;
	TableTreeItem parentItem;
	TableTreeItem [] items = TableTree.EMPTY_ITEMS;
	String[] texts = TableTree.EMPTY_TEXTS;
	Image[] images = TableTree.EMPTY_IMAGES;
	Color background;
	Color foreground;
	Font font;
	boolean expanded;
	boolean checked;
	boolean grayed;

/**
 * Constructs a new instance of this class given its parent
 * (which must be a <code>TableTree</code>)
 * and a style value describing its behavior and appearance.
 * The item is added to the end of the items maintained by its parent.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 * </ul>
 *
 * @see SWT
 * @see Widget#getStyle()
 */
public TableTreeItem(TableTree parent, int style) {
	this (parent, style, parent.getItemCount());
}

/**
 * Constructs a new instance of this class given its parent
 * (which must be a <code>TableTree</code>,
 * a style value describing its behavior and appearance, and the index
 * at which to place it in the items maintained by its parent.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 * @param index the index to store the receiver in its parent
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 * </ul>
 *
 * @see SWT
 * @see Widget#getStyle()
 */
public TableTreeItem(TableTree parent, int style, int index) {
	this (parent, null, style, index);
}

/**
 * Constructs a new instance of this class given its parent
 * (which must be a <code>TableTreeItem</code>)
 * and a style value describing its behavior and appearance.
 * The item is added to the end of the items maintained by its parent.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 * </ul>
 *
 * @see SWT
 * @see Widget#getStyle()
 */
public TableTreeItem(TableTreeItem parent, int style) {
	this (parent, style, parent.getItemCount());
}

/**
 * Constructs a new instance of this class given its parent
 * (which must be a <code>TableTreeItem</code>),
 * a style value describing its behavior and appearance, and the index
 * at which to place it in the items maintained by its parent.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together 
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 * @param index the index to store the receiver in its parent
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 * </ul>
 *
 * @see SWT
 * @see Widget#getStyle()
 */
public TableTreeItem(TableTreeItem parent, int style, int index) {
	this (parent.getParent(), parent, style, index);
}

TableTreeItem(TableTree parent, TableTreeItem parentItem, int style, int index) {
	super(parent, style);
	this.parent = parent;
	this.parentItem = parentItem;
	if (parentItem == null) {
		
		/* Root items are visible immediately */
		int tableIndex = parent.addItem(this, index);
		this.tableItem = new TableItem(parent.getTable(), style, tableIndex);
		this.tableItem.setData(TableTree.ITEMID, this);
		addCheck();
		/*
		* Feature in the Table.  The table uses the first image that
		* is inserted into the table to size the table rows.  If the
		* user is allowed to insert the first image, this will cause
		* the +/- images to be scaled.  The fix is to insert a dummy
		* image to force the size.
		*/
		if (parent.sizeImage == null) {
			int itemHeight = parent.getItemHeight();
			parent.sizeImage = new Image(parent.getDisplay(), itemHeight, itemHeight);
			GC gc = new GC (parent.sizeImage);
			gc.setBackground(parent.getBackground());
			gc.fillRectangle(0, 0, itemHeight, itemHeight);
			gc.dispose();
			this.tableItem.setImage(0, parent.sizeImage);
		}
	} else {
		parentItem.addItem(this, index);
	}
}
void addCheck() {
	Table table = parent.getTable();
	if ((table.getStyle() & SWT.CHECK) == 0) return;
	this.tableItem.setChecked(this.checked);
	this.tableItem.setGrayed(this.grayed);
}
void addItem(TableTreeItem item, int index) {
	if (item == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	if (index < 0 || index > this.items.length) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		
	/* Now that item has a sub-node it must indicate that it can be expanded */
	if (this.items.length == 0 && index == 0) {
		if (this.tableItem != null) {
			Image image = this.expanded ? this.parent.getMinusImage() : this.parent.getPlusImage();
			this.tableItem.setImage(0, image);
		}
	}
	
	/* Put the item in the items list */
	TableTreeItem[] newItems = new TableTreeItem[this.items.length + 1];
	System.arraycopy(this.items, 0, newItems, 0, index);
	newItems[index] = item;
	System.arraycopy(items, index, newItems, index + 1, items.length - index);
	items = newItems;
	if (this.expanded) item.setVisible(true);
}

/**
 * Returns the receiver's background color.
 *
 * @return the background color
 * 
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 2.0
 * 
 */
public Color getBackground () {
	checkWidget ();
	return (this.background == null) ? this.parent.getBackground() : this.background;
}

/**
 * Returns a rectangle describing the receiver's size and location
 * relative to its parent.
 *
 * @return the receiver's bounding rectangle
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Rectangle getBounds (int index) {
	checkWidget();
	if (this.tableItem != null) {
		return tableItem.getBounds(index);
	} else {
		return new Rectangle(0, 0, 0, 0);
	}
}
/**
 * Returns <code>true</code> if the receiver is checked,
 * and false otherwise.  When the parent does not have
 * the <code>CHECK style, return false.
 *
 * @return the checked state of the checkbox
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public boolean getChecked () {
	checkWidget();
	if (this.tableItem == null) return checked;
	return tableItem.getChecked();
}

/**
 * Returns <code>true</code> if the receiver is grayed,
 * and false otherwise. When the parent does not have
 * the <code>CHECK</code> style, return false.
 *
 * @return the grayed state of the checkbox
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 2.1
 */
public boolean getGrayed () {
	checkWidget();
	if (this.tableItem == null) return grayed;
	return tableItem.getGrayed();
}

/**
 * Returns <code>true</code> if the receiver is expanded,
 * and false otherwise.
 * <p>
 *
 * @return the expanded state
 */
public boolean getExpanded () {
	//checkWidget();
	return expanded;
}

/**
 * Returns the font that the receiver will use to paint textual information for this item.
 *
 * @return the receiver's font
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public Font getFont () {
	checkWidget ();
	return (this.font == null) ? this.parent.getFont() : this.font;
}
/**
 * Returns the foreground color that the receiver will use to draw.
 *
 * @return the receiver's foreground color
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 2.0
 * 
 */
public Color getForeground () {
	checkWidget ();
	return (this.foreground == null) ? this.parent.getForeground() : this.foreground;
}
/**
 * Gets the first image.
 * <p>
 * The image in column 0 is reserved for the [+] and [-]
 * images of the tree, therefore getImage(0) will return null.
 *
 * @return the image at index 0
 * 
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
@Override
public Image getImage () {
	checkWidget();
	return getImage(0);
}

/**
 * Gets the image at the specified index.
 * <p>
 * Indexing is zero based. The image can be null.
 * The image in column 0 is reserved for the [+] and [-]
 * images of the tree, therefore getImage(0) will return null.
 * Return null if the index is out of range.
 *
 * @param index the index of the image
 * @return the image at the specified index or null
 */
public Image getImage (int index) {
	//checkWidget();
	if (0 < index && index < images.length) return images[index];
	return null;
}

int getIndent() {
	if (this.parentItem == null) return 0;
	return parentItem.getIndent() + 1;
}

/**
 * Returns the item at the given, zero-relative index in the
 * receiver. Throws an exception if the index is out of range.
 *
 * @param index the index of the item to return
 * @return the item at the given index
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_RANGE - if the index is not between 0 and the number of elements in the list minus 1 (inclusive)</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 3.1
 */
public TableTreeItem getItem (int index) {
	checkWidget();
	int count = this.items.length;
	if (!(0 <= index && index < count)) SWT.error (SWT.ERROR_INVALID_RANGE);
	return items [index];
}

/**
 * Returns the number of items contained in the receiver
 * that are direct item children of the receiver.
 *
 * @return the number of items
 */
public int getItemCount () {
	//checkWidget();
	return items.length;
}

/**
 * Returns an array of <code>TableTreeItem</code>s which are the
 * direct item children of the receiver.
 * <p>
 * Note: This is not the structure used by the receiver
 * to maintain its list of items, so modifying the array will
 * not affect the receiver. 
 * </p>
 *
 * @return the receiver's items
 */
public TableTreeItem[] getItems () {
	//checkWidget();
	TableTreeItem[] newItems = new TableTreeItem[items.length];
	System.arraycopy(items, 0, newItems, 0, items.length);
	return newItems;
}

TableTreeItem getItem(TableItem tableItem) {
	if (tableItem == null) return null;
	if (this.tableItem == tableItem) return this;
	for (int i = 0; i < this.items.length; i++) {
		TableTreeItem item =  this.items[i].getItem(tableItem);
	    	if (item != null) return item;
	}
	return null;
}

/**
 * Returns the receiver's parent, which must be a <code>TableTree</code>.
 *
 * @return the receiver's parent
 */
public TableTree getParent () {
	//checkWidget();
	return parent;
}

/**
 * Returns the receiver's parent item, which must be a
 * <code>TableTreeItem</code> or null when the receiver is a
 * root.
 *
 * @return the receiver's parent item
 */
public TableTreeItem getParentItem () {
	//checkWidget();
	return parentItem;
}
@Override
public String getText () {
	checkWidget();
	return getText(0);
}

/**
 * Gets the item text at the specified index.
 * <p>
 * Indexing is zero based.
 *
 * This operation will fail when the index is out
 * of range or an item could not be queried from
 * the OS.
 *
 * @param index the index of the item
 * @return the item text at the specified index, which can be null
 */
public String getText(int index) {
	//checkWidget();
	if (0 <= index && index < texts.length) return texts[index];
	return null;
}

boolean getVisible () {
	return tableItem != null;
}

/**
 * Gets the index of the specified item.
 * 
 * <p>The widget is searched starting at 0 until an
 * item is found that is equal to the search item.
 * If no item is found, -1 is returned.  Indexing
 * is zero based.  This index is relative to the parent only.
 *
 * @param item the search item
 * @return the index of the item or -1 if the item is not found
 *
 */
public int indexOf (TableTreeItem item) {
	//checkWidget();	
	for (int i = 0; i < this.items.length; i++) {
		if (this.items[i] == item) return i;
	}
	return -1;
}

void expandAll(boolean notify) {
	if (this.items.length == 0) return;
	if (!this.expanded) {
		setExpanded(true);
		if (notify) {
			Event event = new Event();
			event.item = this;
			this.parent.notifyListeners(SWT.Expand, event);
		}
	}
	for (int i = 0; i < this.items.length; i++) {
		this.items[i].expandAll(notify);
	}
}
int expandedIndexOf (TableTreeItem item) {	
	int index = 0;
	for (int i = 0; i < this.items.length; i++) {
		if (this.items[i] == item) return index;
		if (this.items[i].expanded) index += this.items[i].visibleChildrenCount ();
		index++;
	}
	return -1;
}

int visibleChildrenCount () {
	int count = 0;
	for (int i = 0; i < this.items.length; i++) {
		if (this.items[i].getVisible ()) {
			count += 1 + this.items[i].visibleChildrenCount ();
		}
	}
	return count;
}

@Override
public void dispose () {
	if (isDisposed()) return;
	for (int i = this.items.length - 1; i >= 0; i--) {
		this.items[i].dispose();
	}
	super.dispose();
	if (!this.parent.inDispose) {
		if (this.parentItem != null) {
			this.parentItem.removeItem(this);
		} else {
			this.parent.removeItem(this);
		}
		if (this.tableItem != null) this.tableItem.dispose();
	}
	this.items = null;
	this.parentItem = null;
	this.parent = null;
	this.images = null;
	this.texts = null;
	this.tableItem = null;
	this.foreground = null;
	this.background = null;
	this.font = null;
}

void removeItem(TableTreeItem item) {
	int index = 0;
	while (index < this.items.length && this.items[index] != item) index++;
	if (index == this.items.length) return;
	TableTreeItem[] newItems = new TableTreeItem[this.items.length - 1];
	System.arraycopy(this.items, 0, newItems, 0, index);
	System.arraycopy(items, index + 1, newItems, index, items.length - index - 1);
	items = newItems;
	if (items.length == 0) {
		if (this.tableItem != null) this.tableItem.setImage(0, null);
	}
}

/**
 * Sets the receiver's background color to the color specified
 * by the argument, or to the default system color for the item
 * if the argument is null.
 *
 * @param color the new color (or null)
 * 
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li> 
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 2.0
 * 
 */
public void setBackground (Color color) {
	checkWidget ();
	if (color != null && color.isDisposed ()) {
		SWT.error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (this.tableItem != null) {
		this.tableItem.setBackground(color);
	}
	this.background = color;
}

/**
 * Sets the checked state of the checkbox for this item.  This state change 
 * only applies if the Table was created with the SWT.CHECK style.
 *
 * @param checked the new checked state of the checkbox
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setChecked (boolean checked) {
	checkWidget();
	Table table = this.parent.getTable();
	if ((table.getStyle() & SWT.CHECK) == 0) return;
	if (this.tableItem != null) {
		this.tableItem.setChecked(checked);
	}
	this.checked = checked;
}

/**
 * Sets the grayed state of the checkbox for this item.  This state change 
 * only applies if the Table was created with the SWT.CHECK style.
 *
 * @param grayed the new grayed state of the checkbox; 
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 2.1
 */
public void setGrayed (boolean grayed) {
	checkWidget();
	Table table = this.parent.getTable();
	if ((table.getStyle() & SWT.CHECK) == 0) return;
	if (this.tableItem != null) {
		this.tableItem.setGrayed(grayed);
	}
	this.grayed = grayed;
}

/**
 * Sets the expanded state.
 * <p>
 * @param expanded the new expanded state.
 *
 * @exception SWTException <ul>
 *		<li>ERROR_THREAD_INVALID_ACCESS when called from the wrong thread</li>
 *		<li>ERROR_WIDGET_DISPOSED when the widget has been disposed</li>
 *	</ul>
 */
public void setExpanded (boolean expanded) {
	checkWidget();
	if (this.items.length == 0) return;
	if (this.expanded == expanded) return;
	this.expanded = expanded;
	if (this.tableItem == null) return;
	this.parent.setRedraw(false);
	for (int i = 0; i < this.items.length; i++) {
		this.items[i].setVisible(expanded);
	}
	Image image = expanded ? this.parent.getMinusImage() : this.parent.getPlusImage();
	this.tableItem.setImage(0, image);
	this.parent.setRedraw(true);
}

/**
 * Sets the font that the receiver will use to paint textual information
 * for this item to the font specified by the argument, or to the default font
 * for that kind of control if the argument is null.
 *
 * @param font the new font (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li> 
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 3.0
 */
public void setFont (Font font){
	checkWidget ();
	if (font != null && font.isDisposed ()) {
		SWT.error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (this.tableItem != null) {
		this.tableItem.setFont(font);
	}
	this.font = font;
}
/**
 * Sets the receiver's foreground color to the color specified
 * by the argument, or to the default system color for the item
 * if the argument is null.
 *
 * @param color the new color (or null)
 *
 * @since 2.0
 * 
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li> 
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 * 
 * @since 2.0
 * 
 */
public void setForeground (Color color) {
	checkWidget ();
	if (color != null && color.isDisposed ()) {
		SWT.error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (this.tableItem != null) {
		this.tableItem.setForeground(color);
	}
	this.foreground = color;
}

/**
 * Sets the image at an index.
 * <p>
 * The image can be null.
 * The image in column 0 is reserved for the [+] and [-]
 * images of the tree, therefore do nothing if index is 0.
 *
 * @param image the new image or null
 *
 * @exception SWTException <ul>
 *		<li>ERROR_THREAD_INVALID_ACCESS when called from the wrong thread</li>
 *		<li>ERROR_WIDGET_DISPOSED when the widget has been disposed</li>
 *	</ul>
 */
public void setImage (int index, Image image) {
	checkWidget();
	int columnCount = Math.max(this.parent.getTable().getColumnCount(), 1);
	if (index <= 0 || index >= columnCount) return;
	if (this.images.length < columnCount) {
		Image[] newImages = new Image[columnCount];
		System.arraycopy(images, 0, newImages, 0, images.length);
		images = newImages;
	}
	images[index] = image;
	if (this.tableItem != null) this.tableItem.setImage(index, image);
}

/**
 * Sets the first image.
 * <p>
 * The image can be null.
 * The image in column 0 is reserved for the [+] and [-]
 * images of the tree, therefore do nothing.
 *
 * @param image the new image or null
 * 
 * @exception SWTException <ul>
 *		<li>ERROR_THREAD_INVALID_ACCESS when called from the wrong thread</li>
 *		<li>ERROR_WIDGET_DISPOSED when the widget has been disposed</li>
 *	</ul>
 */
@Override
public void setImage (Image image) {
	setImage(0, image);
}

/**
 * Sets the widget text.
 * <p>
 *
 * The widget text for an item is the label of the
 * item or the label of the text specified by a column
 * number.
 *
 * @param index the column number
 * @param text the new text
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the text is null</li>
 * </ul>
 * @exception SWTException <ul>
 *		<li>ERROR_THREAD_INVALID_ACCESS when called from the wrong thread</li>
 *		<li>ERROR_WIDGET_DISPOSED when the widget has been disposed</li>
 *	</ul>
 */
public void setText(int index, String text) {
	checkWidget();
	if (text == null) SWT.error (SWT.ERROR_NULL_ARGUMENT);
	int columnCount = Math.max(this.parent.getTable().getColumnCount(), 1);
	if (index < 0 || index >= columnCount) return;
	if (this.texts.length < columnCount) {
		String[] newTexts = new String[columnCount];
		System.arraycopy(texts, 0, newTexts, 0, texts.length);
		texts = newTexts;
	}
	texts[index] = text;
	if (this.tableItem != null) this.tableItem.setText(index, text);
}
@Override
public void setText (String string) {
	setText(0, string);
}

void setVisible (boolean show) {
	if (this.parentItem == null) return; // this is a root and can not be toggled between visible and hidden
	if (getVisible() == show) return;

	if (show) {
		if (!this.parentItem.getVisible()) return; // parentItem must already be visible
		// create underlying table item and set data in table item to stored data
		Table table = this.parent.getTable();
		int parentIndex = table.indexOf(this.parentItem.tableItem);
		int index = this.parentItem.expandedIndexOf(this) + parentIndex + 1;
		if (index < 0) return;
		this.tableItem = new TableItem(table, getStyle(), index);
		this.tableItem.setData(TableTree.ITEMID, this);
		this.tableItem.setImageIndent(getIndent());
		if (this.background != null) this.tableItem.setBackground(this.background);
		if (this.foreground != null) this.tableItem.setForeground(this.foreground);
		if (this.font != null) this.tableItem.setFont(this.font);
		addCheck();

		// restore fields to item
		// ignore any images in the first column
		int columnCount = Math.max(table.getColumnCount(), 1);
		for (int i = 0; i < columnCount; i++) {
			if (i < texts.length && texts[i] != null) setText(i, texts[i]);
			if (i < images.length && images[i] != null) setImage(i, images[i]);
		}

		// display the children and the appropriate [+]/[-] symbol as required
		if (this.items.length != 0) {
			if (this.expanded) {
				this.tableItem.setImage(0, parent.getMinusImage());
				for (int i = 0, length = this.items.length; i < length; i++) {
					this.items[i].setVisible(true);
				}
			} else {
				this.tableItem.setImage(0, parent.getPlusImage());
			}
		}
		
	} else {

		for (int i = 0, length = this.items.length; i < length; i++) {
			this.items[i].setVisible(false);
		}
		// remove row from table
		this.tableItem.dispose();
		this.tableItem = null;
	}
}
}
