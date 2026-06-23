/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2005 Andres Navarro
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 *
 *  Other Contributor(s):
 *    Travis Berthelot
 *    Shane Harper
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroupImageFactory;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.NullImage;

import org.allbinary.graphics.form.item.ABChoiceItemInterface;
import org.allbinary.graphics.form.item.ABCustomItem;
import org.allbinary.graphics.form.item.ABImageStringItem;
import org.allbinary.graphics.opengles.OpenGLCapabilities;
import org.allbinary.graphics.opengles.OpenGLFeatureFactory;

import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.game.configuration.feature.Features;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.image.opengles.OpenGLESImage;
import org.allbinary.logic.string.StringUtil;

//
//import org.allbinary.logic.communication.log.LogUtil;

public class ChoiceGroupItem extends ABCustomItem implements ABChoiceItemInterface
{

    public static final List NULL_LIST = new List(StringUtil.getInstance().EMPTY_STRING, Choice.IMPLICIT, StringUtil.getInstance().getArrayInstance(), NullImage.NULL_IMAGE_ARRAY);

    static final int EXCLUSIVE = 1;
    static final int MULTIPLE = 2;
    static final int IMPLICIT = 3;
    static final int POPUP = 4;

    static final int TEXT_WRAP_ON = 1;
    static final int TEXT_WRAP_OFF = 2;
    static final int TEXT_WRAP_DEFAULT = 0;
    
    protected final LogUtil logUtil = LogUtil.getInstance();

    int choiceType;

    private ChoiceItem items[] = new ChoiceItem[4];

    private int numOfItems = 0;

    private int fitPolicy;

    private int highlightedItemIndex = -1;

    private List popupList;

    public ChoiceGroupItem(String label, int choiceType, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        this(label, choiceType, true, backgroundBasicColor, foregroundBasicColor);
    }

    public ChoiceGroupItem(String label, int choiceType, String[] stringElements,
            Image[] imageElements, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        this(label, choiceType, stringElements, imageElements, true, backgroundBasicColor,
                foregroundBasicColor);
    }

    ChoiceGroupItem(String label, int choiceType, boolean validateChoiceType,
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);

        if (validateChoiceType)
        {
            if (choiceType != ChoiceGroupItem.POPUP
                    && choiceType != ChoiceGroupItem.MULTIPLE
                    && choiceType != ChoiceGroupItem.EXCLUSIVE)
            {
                throw new IllegalArgumentException("Illegal choice type");
            }
        }
        this.choiceType = choiceType;
        List popupList;
        if (choiceType == ChoiceGroupItem.POPUP)
        {
            // POPUP has a hidden List to implement it's
            // behaviour
            popupList = new List(label, ChoiceGroupItem.IMPLICIT, StringUtil.getInstance().getArrayInstance(), NullImage.NULL_IMAGE_ARRAY);
            popupList.setCommandListener(new ImplicitListener());
        } else {
            popupList = ChoiceGroupItem.NULL_LIST;
        }
        this.popupList = popupList;
    }

    // XXX imageElements is ignored.
    ChoiceGroupItem(String label, int choiceType, String[] stringElements, Image[] imageElements,
            boolean validateChoiceType, BasicColor backgroundBasicColor,
            BasicColor foregroundBasicColor)
    {
        this(label, choiceType, validateChoiceType, backgroundBasicColor, foregroundBasicColor);

        for (int i = 0; i < stringElements.length; i++)
        {
            if (imageElements == null)
            {
                this.append(stringElements[i], NullImage.NULL_IMAGE);
            } else
            {
                this.append(stringElements[i], imageElements[i]);
            }
        }
    }

    @Override
    public int append(String stringPart, Image imagePart)
    {
        this.insert(this.numOfItems, stringPart, imagePart);

        return (this.numOfItems - 1);
    }

    @Override
    public void delete(int itemNum)
    {
        if (itemNum < 0 || itemNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }

        // Ensure that an item of an EXCLUSIVE list remains selected.
        if ((ChoiceGroupItem.EXCLUSIVE == this.choiceType || ChoiceGroupItem.POPUP == this.choiceType)
                && this.items[itemNum].isSelected())
        {
            if (this.numOfItems > 1)
            {
                this.items[itemNum != 0 ? 0 : 1].setSelectedState(true);
            }
        }

        // Delete item.
        if (itemNum != this.numOfItems - 1)
        {
            System.arraycopy(items, itemNum + 1, items, itemNum, numOfItems - itemNum - 1);
        }
        this.numOfItems--;
        // clear the slot to allow garbage collection
        this.items[this.numOfItems] = null;

        // Ensure highlighted item remains highlighted (if it wasn't just
        // deleted).
        if (this.highlightedItemIndex > itemNum)
        {
            --this.highlightedItemIndex;
        }

        // Ensure that an item remains highlighted.
        if (this.highlightedItemIndex >= this.numOfItems)
        {
            this.highlightedItemIndex = this.numOfItems - 1;
        }

        if (this.choiceType == ChoiceGroupItem.POPUP) {
            this.popupList.delete(itemNum);
        }
        repaint();
    }

    @Override
    public void deleteAll()
    {
        // clear the array to allow garbage collection
        for (int i = 0; i < this.numOfItems; i++)
            this.items[i] = null;
        this.numOfItems = 0;
        this.highlightedItemIndex = -1;
        if (this.choiceType == ChoiceGroupItem.POPUP)
            this.popupList.deleteAll();
        repaint();
    }

    @Override
    public int getFitPolicy()
    {
        return this.fitPolicy;
    }

    @Override
    public Font getFont(int itemNum)
    {
        if (itemNum < 0 || itemNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }
        return this.items[itemNum].getFont();
    }

    /*
    public Image getImage(int elementNum)
    {
    	if (elementNum < 0 || elementNum >= this.numOfItems) {
    		throw new IndexOutOfBoundsException();
    	}

      return items[elementNum].getImage();
    }
    */

    /**
     * Queries the state of a ChoiceGroup and returns the state of all elements
     * in the boolean array selectedArray_return. NOTE: this is a result
     * parameter. It must be at least as long as the size of the ChoiceGroup as
     * returned by size(). If the array is longer, the extra elements are set to
     * false.
     * 
     * For ChoiceGroup objects of type MULTIPLE, any number of elements may be
     * selected and set to true in the result array. For ChoiceGroup objects of
     * type EXCLUSIVE, exactly one element will be selected, unless there are
     * zero elements in the ChoiceGroup.
     */
    @Override
    public int getSelectedFlags(boolean[] selectedArray_return)
    {
        if (selectedArray_return == null)
        {
            throw new NullPointerException();
        }
        if (selectedArray_return.length < this.numOfItems)
        {
            throw new IllegalArgumentException();
        }

        // set selectedArray_return elements and count number of selected items
        int selectedItemsCount = 0;

        for (int i = 0; i < selectedArray_return.length; ++i)
        {
            selectedArray_return[i] = (i < this.numOfItems) ? this.items[i].isSelected() : false;
            if (selectedArray_return[i])
            {
                ++selectedItemsCount;
            }
        }

        return selectedItemsCount;
    }

    /**
     * Returns the index number of an element in the ChoiceGroup that is
     * selected. For ChoiceGroup objects of type EXCLUSIVE there is at most one
     * element selected, so this method is useful for determining the user's
     * choice. Returns -1 if there are no elements in the ChoiceGroup.
     * 
     * For ChoiceGroup objects of type MULTIPLE, this always returns -1 because
     * no single value can in general represent the state of such a ChoiceGroup.
     * To get the complete state of a MULTIPLE Choice, see getSelectedFlags.
     */
    @Override
    public int getSelectedIndex()
    {
        switch (this.choiceType)
        {
        case ChoiceGroupItem.EXCLUSIVE:
        case ChoiceGroupItem.POPUP:
            // XXX It'd be nice if the selected item index was stored, so it
            // isn't
            // necessary to search for it.
            for (int i = 0; i < this.numOfItems; ++i)
            {
                if (this.items[i].isSelected()) {
                    return i;
                }
            }
            break;
        case ChoiceGroupItem.IMPLICIT:
            return this.highlightedItemIndex;
        }
        return -1;
    }

    @Override
    public String getString(int elementNum)
    {
        if (elementNum < 0 || elementNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }

        return this.items[elementNum].getText();
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart)
    {
        if (elementNum < 0 || elementNum > this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }
        if (stringPart == null)
        {
            throw new NullPointerException();
        }

        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            this.popupList.insert(elementNum, stringPart, imagePart);
        }

        if (this.numOfItems == this.items.length /*no space left in item array*/)
        {
            ChoiceItem newItems[] = new ChoiceItem[this.numOfItems + 4];
            System.arraycopy(items, 0, newItems, 0, numOfItems);
            this.items = newItems;
        }

        System.arraycopy(items, elementNum, items, elementNum + 1, numOfItems - elementNum);

        this.items[elementNum] = new ChoiceItem(StringUtil.getInstance().EMPTY_STRING, imagePart, stringPart, this
                .getLabelStringComponent().getBackgroundBasicColor(), this
                .getLabelStringComponent().getForegroundBasicColor());

        ++this.numOfItems;

        if (this.numOfItems == 1)
        {
            this.highlightedItemIndex = 0;
            if (ChoiceGroupItem.EXCLUSIVE == this.choiceType
                    || ChoiceGroupItem.POPUP == this.choiceType)
            {
                this.setSelectedIndex(0, true);
            }
        }

        repaint();
    }

    @Override
    public boolean isSelected(int elementNum)
    {
        if (elementNum < 0 || elementNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }

        return this.items[elementNum].isSelected();
    }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart)
    {
        if (elementNum < 0 || elementNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }
        if (imagePart != null && imagePart.isMutable())
        {
            throw new IllegalArgumentException();
        }
        if (stringPart == null)
        {
            throw new NullPointerException();
        }

        this.items[elementNum].setText(stringPart);
        this.items[elementNum].setImage(imagePart);

        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            this.popupList.set(elementNum, stringPart, imagePart);
        }

        repaint();
    }

    @Override
    public void setFitPolicy(int policy)
    {
        if (policy != ChoiceGroupItem.TEXT_WRAP_DEFAULT
                && policy != ChoiceGroupItem.TEXT_WRAP_ON
                && policy != ChoiceGroupItem.TEXT_WRAP_OFF)
            throw new IllegalArgumentException("Bad Policy");
        this.fitPolicy = policy;
        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            this.popupList.setFitPolicy(policy);
        }
    }

    @Override
    public void setFont(int itemNum, Font font)
    {
        if (itemNum < 0 || itemNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }
        this.items[itemNum].setFont(font);
        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            this.popupList.setFont(itemNum, font);
        }
    }

    @Override
    public void setSelectedFlags(boolean[] selectedArray)
    {
        if (selectedArray == null)
        {
            throw new NullPointerException();
        }
        if (selectedArray.length < this.numOfItems)
        {
            throw new NullPointerException();
        }

        if (this.numOfItems == 0) {
            return;            
        }

        if (this.choiceType == ChoiceGroupItem.MULTIPLE)
        {
            for (int i = 0; i < this.numOfItems; i++)
            {
                this.setSelectedIndex(i, selectedArray[i]);
            }
        } else
        {
            int selectedItem = -1;
            for (int i = 0; i < this.numOfItems; i++)
            {
                if (selectedArray[i])
                {
                    this.setSelectedIndex(i, true);
                    selectedItem = i;
                    break;
                }
            }
            if (selectedItem == -1)
            {
                this.setSelectedIndex(0, true);
            }

            if (this.choiceType == ChoiceGroupItem.POPUP)
            {
                this.popupList.setSelectedFlags(selectedArray);
            }
        }

    }

    @Override
    public void setSelectedIndex(int elementNum, boolean selected)
    {
        if (elementNum < 0 || elementNum >= this.numOfItems)
        {
            throw new IndexOutOfBoundsException();
        }

        this.highlightedItemIndex = elementNum;
        if ((this.choiceType == ChoiceGroupItem.EXCLUSIVE || this.choiceType == ChoiceGroupItem.POPUP)
                && selected)
        {
            for (int i = 0; i < this.numOfItems; i++)
            {
                this.items[i].setSelectedState(elementNum == i);
            }
            if (this.choiceType == ChoiceGroupItem.POPUP)
            {
                this.popupList.setSelectedIndex(elementNum, true);
            }
            repaint();
        } else if (this.choiceType == ChoiceGroupItem.MULTIPLE)
        {
            this.items[elementNum].setSelectedState(selected);
            repaint();
        } else if (this.choiceType == ChoiceGroupItem.IMPLICIT)
        {
            if (selected)
            {
                this.items[elementNum].setSelectedState(selected);
                repaint();
            }
        }
    }

    @Override
    public int size()
    {
        return this.numOfItems;
    }

    @Override
    public boolean isFocusable()
    {
        return true;
    }

    @Override
    public int getHeight()
    {
        int height = 0;
        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            if (this.highlightedItemIndex != -1)
            {
                height += items[this.highlightedItemIndex].getHeight();
            }
        } else
        {
            for (int i = 0; i < this.numOfItems; i++)
            {
                height += items[i].getHeight();
            }
        }

        return super.getHeight() + height;
    }

    /*
     * Get item index from coordinates
     */
    int getItemIndexAt(int x, int y)
    {
        x -= super.getHeight();
        int testY = 0;
        for (int i = 0; i < this.numOfItems; i++)
        {
            testY += items[i].getHeight();
            if (y < testY)
            {
                return i;
            }
        }

        return -1;
    }

    int getHeightToItem(int itemIndex)
    {
        int height = 0;

        for (int i = 0; i < itemIndex; i++)
        {
            height += items[i].getHeight();
        }

        return height;
    }

    int getItemHeight(int itemIndex)
    {
        return this.items[itemIndex].getHeight();
    }

    @Override
    public int paint(Graphics g)
    {
        // super.paintContent(g);

        g.translate(0, super.getHeight());
        int translatedY = 0;

        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            int index = this.getSelectedIndex();
            if (index != -1)
            {
                this.items[index].invertPaint(hasFocus());
                this.items[index].paint(g);
            }
        } else
        {
            for (int i = 0; i < this.numOfItems; i++)
            {
                this.items[i].invertPaint(i == this.highlightedItemIndex && hasFocus());
                this.items[i].paint(g);
                // logUtil.putF("Painting: " + items[i].getLabel(), this, "paint");
                g.translate(0, items[i].getHeight());
                translatedY += items[i].getHeight();
            }
            g.translate(0, -translatedY);
        }

        g.translate(0, -super.getHeight());

        return this.getHeight();
    }

    @Override
    public boolean select()
    {
        if (this.numOfItems == 0)
        {
            return false;
        }

        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            // getOwner().currentDisplay.setCurrent(popupList);
        } else
        {
            // XXX What does the following statement do?

            // It is correct, in the case of multiple inverts the selected
            // state, in exclusive selects the highligthed
            // and in implicit it does nothing
            // Andres Navarro
            this.setSelectedIndex(this.highlightedItemIndex, !items[this.highlightedItemIndex].isSelected());
        }

        return true;
    }

    @Override
    public int traverse(int gameKeyCode, int top, int bottom, boolean action)
    {
        int OUTOFITEM = Integer.MAX_VALUE;
        //int OUTOFITEM = Item.OUTOFITEM;

        if (this.choiceType == ChoiceGroupItem.POPUP)
        {
            // POPUP has a totally different behaviour
            if (gameKeyCode == Canvas.UP)
            {
                if (top > 0)
                {
                    return -top;
                } else
                {
                    return OUTOFITEM;
                }
            } else if (gameKeyCode == Canvas.DOWN)
            {
                if (!action)
                {
                    int height = super.getHeight();
                    int index = this.getSelectedIndex();
                    if (index != -1)
                    {
                        height += items[index].getHeight();
                    }

                    if (height > bottom)
                    {
                        return height - bottom;
                    } else
                    {
                        repaint();
                    }
                } else
                {
                    return OUTOFITEM;
                }
            }
        } else
        {
            if (gameKeyCode == Canvas.UP)
            {
                if (this.highlightedItemIndex > 0)
                {
                    if (action)
                    {
                        this.highlightedItemIndex--;
                    }
                    int height = super.getHeight();
                    for (int i = 0; i < this.highlightedItemIndex; i++)
                    {
                        height += items[i].getHeight();
                    }
                    if (height < top)
                    {
                        return height - top;
                    } else
                    {
                        repaint();
                    }
                } else
                {
                    if (top > 0)
                    {
                        return -top;
                    } else
                    {
                        return OUTOFITEM;
                    }
                }
            }
            if (gameKeyCode == Canvas.DOWN)
            {
                if ((!action && this.highlightedItemIndex < this.numOfItems)
                        || (action && highlightedItemIndex < (this.numOfItems - 1)))
                {
                    if (action)
                    {
                        highlightedItemIndex++;
                    }
                    int height = super.getHeight();
                    for (int i = 0; i <= this.highlightedItemIndex; i++)
                    {
                        height += items[i].getHeight();
                    }
                    if (height > bottom)
                    {
                        return height - bottom;
                    } else
                    {
                        repaint();
                    }
                } else
                {
                    return OUTOFITEM;
                }
            }
        }

        return 0;
    }

    /*
    void repaint() {
      // the popup list should be repainted
      // in the case it is being shown
      if (this.choiceType == Choice.POPUP)
    	  this.popupList.repaint();
      super.repaint();
    }*/

    class ChoiceItem extends ABImageStringItem
    {
        private boolean selected;
        private Font font;
        Image box = NullImage.NULL_IMAGE;

        ChoiceItem(String label, Image image, String text, BasicColor backgroundBasicColor,
                BasicColor foregroundBasicColor)
        {
            super(label, image, text, backgroundBasicColor, foregroundBasicColor);
            this.setSelectedState(false);
            font = Font.getDefaultFont();
        }

        Font getFont()
        {
            return this.font;
        }

        @Override
        public void setImage(Image img)
        {
            super.setImage(img);

            int width = 0;
            if (this.box != null) {
                width += box.getWidth();
            }

            final Image image = this.getImage();
            if (!(image == NullImage.NULL_IMAGE || image == OpenGLESImage.NULL_OPENGL_IMAGE)) {
                width += img.getWidth();
            }

            if (width > 0) {
                width += 2;
            }

            this.getStringComponent().setWidthDecreaser(width);
        }

        @Override
        public int getHeight()
        {
            int height = 0;
            if (this.box != null)
            {
                height = box.getHeight();
            }
                
            final Image image = this.getImage();
            if (!(image == NullImage.NULL_IMAGE || image == OpenGLESImage.NULL_OPENGL_IMAGE) && this.getImage().getHeight() > height)
            {
                height = this.getImage().getHeight();
            }
            
            if (this.getStringComponent().getHeight() > height)
            {
                height = this.getStringComponent().getHeight();
            }
            return height;
        }

        @Override
        public int paint(Graphics g)
        {
            // OpenGL ES Hack
            OpenGLCapabilities openGLCapabilities = OpenGLCapabilities.getInstance();

            if (this.getStringComponent() == null)
            {
                return 0;
            }

            final Image image = this.getImage();
            
            int widthAddition = 0;
            if (this.box != null)
            {

                if (Features.getInstance().isDefault(OpenGLFeatureFactory.getInstance().OPENGL)
                        && openGLCapabilities.isGlExtensionDrawTexture())
                {
                    g.drawImage(box, g.getTranslateX(), g.getTranslateY(), Graphics.LEFT | Graphics.TOP);
                } else
                {
                    g.drawImage(box, 0, 0, Graphics.LEFT | Graphics.TOP);
                }

                if (!(image == NullImage.NULL_IMAGE || image == OpenGLESImage.NULL_OPENGL_IMAGE))
                {
                    widthAddition = box.getWidth();
                    g.translate(box.getWidth(), 0);
                } else
                {
                    widthAddition = box.getWidth() + 2;
                    g.translate(box.getWidth() + 2, 0);
                }
            }
            
            if (!(image == NullImage.NULL_IMAGE || image == OpenGLESImage.NULL_OPENGL_IMAGE))
            {

                widthAddition += image.getWidth() + 2;

                // OpenGL ES Hack
                if (Features.getInstance().isDefault(OpenGLFeatureFactory.getInstance().OPENGL)
                        && openGLCapabilities.isGlExtensionDrawTexture())
                {
                    g.drawImage(image, g.getTranslateX(), g.getTranslateY(), Graphics.LEFT | Graphics.TOP);
                } 
                else
                {
                    g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);
                }

                g.translate(image.getWidth() + 2, 0);
            }

            int y = this.getStringComponent().paint(g);

            if (widthAddition != 0)
            {
                g.translate(-widthAddition, 0);
            }

            return y;
        }

        boolean isSelected()
        {
            return this.selected;
        }

        void setFont(Font f)
        {
            if (f == null)
            {
                throw new NullPointerException();
            }
                
            // only allow fonts of the same height
            // for now (to simplify the layout)
            if (f.getHeight() == this.font.getHeight()) {
                this.font = f;
            }

        }

        void setSelectedState(boolean state)
        {
            selected = state;

            final Image[] imageArray = ChoiceGroupImageFactory.getInstance().getImageArray();
            if (choiceType != ChoiceGroupItem.IMPLICIT
                    && choiceType != ChoiceGroupItem.POPUP)
            {
                if(ChoiceGroupItem.EXCLUSIVE == choiceType) {
                    if(state) {
                        this.box = imageArray[3];
                    } else {
                        this.box = imageArray[2];
                    }
                } else {
                    if(state) {
                        this.box = imageArray[1];
                    } else {
                        this.box = imageArray[0];
                    }
                }
            }
        }
    }

    class ImplicitListener implements CommandListener
    {
        @Override
        public void commandAction(Command c, Displayable d)
        {
            List list = (List) d;
            setSelectedIndex(list.getSelectedIndex(), true);
            try
            {
                // getOwner().currentDisplay.setCurrent(getOwner());
                repaint();
            } catch (NullPointerException n)
            {
                // this happens if the item becomes an orphan
                // (ie not owned by a Form, shouldn't happen
                // if correct programming practices are used!!)
            }
        }
    }

    @Override
    public void setFocus(boolean state)
    {
        // logUtil.putF(commonStrings.START, this, "setFocus");

        if (state)
        {
            // TWB - Toggle selected choice hack
            this.logUtil.putF("Resetting Focus", this, "setFocus");

            int index = 0;
            this.setSelectedIndex(index, !this.isSelected(index));
        }

        super.setFocus(state);
    }
}