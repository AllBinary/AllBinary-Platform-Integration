/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2005 Andres Navarro
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Contributor(s):
 *    Shane Harper
 */
 
package javax.microedition.lcdui;


import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.ChoiceGroupUI;


public class ChoiceGroup extends Item implements Choice
{
    int choiceType;

    private ChoiceItem items[] = new ChoiceItem[4];
    
    private int numOfItems = 0;
  
    private int fitPolicy;
    
    private int highlightedItemIndex = -1;  
    
    private List popupList;

	public ChoiceGroup(String label, int choiceType) 
	{
		this(label, choiceType, true);
	}


	public ChoiceGroup(String label, int choiceType, String[] stringElements, Image[] imageElements)
	{
		this(label, choiceType, stringElements, imageElements, true);
	}


	ChoiceGroup(String label, int choiceType, boolean validateChoiceType)
	{
		super(label);
		super.setUI(DeviceFactory.getDevice().getUIFactory().createChoiceGroupUI(this, choiceType));

		if (validateChoiceType) {
			if (choiceType != Choice.POPUP && choiceType != Choice.MULTIPLE && choiceType != Choice.EXCLUSIVE) {
				throw new IllegalArgumentException("Illegal choice type");
			}
		}
		this.choiceType = choiceType;
		if (choiceType == Choice.POPUP) {
			// POPUP has a hidden List to implement it's
			// behaviour
			this.popupList = new List(label, Choice.IMPLICIT);
			this.popupList.setCommandListener(new ImplicitListener());
		}
	}


	// XXX imageElements is ignored.
	ChoiceGroup(String label, int choiceType, String[] stringElements, Image[] imageElements, boolean validateChoiceType)
	{
		this(label, choiceType, validateChoiceType);

		for (int i = 0; i < stringElements.length; i++) {
			if (imageElements == null) {
				this.append(stringElements[i], null);
			} else {
				this.append(stringElements[i], imageElements[i]);
			}
		}
	}

	
        @Override
	       public int append(String stringPart, Image imagePart) {
		this.insert(size(), stringPart, imagePart);
	
		return (size() - 1);
	}

        @Override
	       public void delete(int itemNum) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			((ChoiceGroupUI) ui).delete(itemNum);
		} else {
			if (itemNum < 0 || itemNum >= this.numOfItems) {
				throw new IndexOutOfBoundsException();
			}
	
			// Ensure that an item of an EXCLUSIVE list remains selected.
			if ((Choice.EXCLUSIVE == this.choiceType || Choice.POPUP == this.choiceType)
					&& this.items[itemNum].isSelected()) {
				if (this.numOfItems > 1) {
					this.items[itemNum != 0 ? 0 : 1].setSelectedState(true);
				}
			}
	
			// Delete item.
			if (itemNum != this.numOfItems - 1) {
				System.arraycopy(items, itemNum + 1, items, itemNum, numOfItems
						- itemNum - 1);
			}
			this.numOfItems--;
			// clear the slot to allow garbage collection
			this.items[this.numOfItems] = null;
	
			// Ensure highlighted item remains highlighted (if it wasn't just
			// deleted).
			if (this.highlightedItemIndex > itemNum) {
				--this.highlightedItemIndex;
			}
	
			// Ensure that an item remains highlighted.
			if (this.highlightedItemIndex >= this.numOfItems) {
				this.highlightedItemIndex = this.numOfItems - 1;
			}
	
			if (this.choiceType == Choice.POPUP)
				this.popupList.delete(itemNum);
			this.repaint();
		}
	}
  
        @Override
	       public void deleteAll() {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			((ChoiceGroupUI) ui).deleteAll();
		} else {
			// clear the array to allow garbage collection
			for (int i = 0; i < this.numOfItems; i++)
				this.items[i] = null;
			this.numOfItems = 0;
			this.highlightedItemIndex = -1;
			if (this.choiceType == Choice.POPUP)
				this.popupList.deleteAll();
			this.repaint();
		}
	}


        @Override
        public int getFitPolicy() {
	  return this.fitPolicy;
  }
  
  @Override
  public Font getFont(int itemNum) {
		if (itemNum < 0 || itemNum >= this.numOfItems) {
			throw new IndexOutOfBoundsException();
		}
		return this.items[itemNum].getFont();
  }
  
  @Override
  public Image getImage(int elementNum)
  {
		if (elementNum < 0 || elementNum >= this.numOfItems) {
			throw new IndexOutOfBoundsException();
		}

    return this.items[elementNum].getImage();
  }


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
	 public int getSelectedFlags(boolean[] selectedArray) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			return ((ChoiceGroupUI) ui).getSelectedFlags(selectedArray);
		} else {
			if (selectedArray == null) {
				throw new NullPointerException();
			}
			if (selectedArray.length < this.numOfItems) {
				throw new IllegalArgumentException();
			}

			// set selectedArray elements and count number of selected items
			int selectedItemsCount = 0;

			for (int i = 0; i < selectedArray.length; ++i) {
				selectedArray[i] = (i < this.numOfItems) ? this.items[i].isSelected() : false;
				if (selectedArray[i]) {
					++selectedItemsCount;
				}
			}

			return selectedItemsCount;
		}
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
	       public int getSelectedIndex() {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			return ((ChoiceGroupUI) ui).getSelectedIndex();
		} else {
			switch (this.choiceType) {
			case Choice.EXCLUSIVE:
			case Choice.POPUP:
				// XXX It'd be nice if the selected item index was stored, so it
				// isn't
				// necessary to search for it.
				for (int i = 0; i < this.numOfItems; ++i) {
					if (this.items[i].isSelected())
						return i;
				}
				break;
			case Choice.IMPLICIT:
				return this.highlightedItemIndex;
			}
			return -1;
		}
	}


        @Override
        public String getString(int elementNum)
  {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			if (elementNum < 0 || elementNum >= ((ChoiceGroupUI) ui).size()) {
				throw new IndexOutOfBoundsException();
			}
      
			return ((ChoiceGroupUI) ui).getString(elementNum);

		} else {
			if (elementNum < 0 || elementNum >= this.numOfItems) {
				throw new IndexOutOfBoundsException();
			}

			return this.items[elementNum].getText();
		}
  }


  @Override
	 public void insert(int elementNum, String stringPart, Image imagePart) {
		if (ui.getClass().getName().equals( "org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			((ChoiceGroupUI) ui).insert(elementNum, stringPart, imagePart);
		} else {
			if (elementNum < 0 || elementNum > this.numOfItems) {
				throw new IndexOutOfBoundsException();
			}
			if (stringPart == null) {
				throw new NullPointerException();
			}

			if (this.choiceType == Choice.POPUP) {
				this.popupList.insert(elementNum, stringPart, imagePart);
			}

			if (this.numOfItems == this.items.length /* no space left in item array */) {
				ChoiceItem newItems[] = new ChoiceItem[this.numOfItems + 4];
				System.arraycopy(items, 0, newItems, 0, numOfItems);
				this.items = newItems;
			}

			System.arraycopy(items, elementNum, items, elementNum + 1, numOfItems - elementNum);

			this.items[elementNum] = new ChoiceItem(null, imagePart, stringPart);

			++numOfItems;

			if (numOfItems == 1) {
				this.highlightedItemIndex = 0;
				if (Choice.EXCLUSIVE == this.choiceType || Choice.POPUP == this.choiceType) {
					this.setSelectedIndex(0, true);
				}
			}

			this.repaint();
		}
	}

        @Override
  	     public boolean isSelected(int elementNum) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			return ((ChoiceGroupUI) ui).isSelected(elementNum);
		} else {
			if (elementNum < 0 || elementNum >= this.numOfItems) {
				throw new IndexOutOfBoundsException();
			}
	
			return this.items[elementNum].isSelected();
		}
	}

        @Override
  	     public void set(int elementNum, String stringPart, Image imagePart) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			((ChoiceGroupUI) ui).set(elementNum, stringPart, imagePart);
		} else {
			if (elementNum < 0 || elementNum >= this.numOfItems) {
				throw new IndexOutOfBoundsException();
			}
			if (imagePart != null && imagePart.isMutable()) {
				throw new IllegalArgumentException();
			}
			if (stringPart == null) {
				throw new NullPointerException();
			}
	
			this.items[elementNum].setText(stringPart);
			this.items[elementNum].setImage(imagePart);
	
			if (this.choiceType == Choice.POPUP) {
				this.popupList.set(elementNum, stringPart, imagePart);
			}
	
			this.repaint();
		}
	}

        @Override
        public void setFitPolicy(int policy) {
	  if (policy != Choice.TEXT_WRAP_DEFAULT &&
			  	policy != Choice.TEXT_WRAP_ON &&
			  	policy != Choice.TEXT_WRAP_OFF)
		  throw new IllegalArgumentException("Bad Policy");
	  this.fitPolicy = policy;
		if (this.choiceType == Choice.POPUP) {
			  this.popupList.setFitPolicy(policy);
		}
  }
  
  @Override
  public void setFont(int itemNum, Font font) {
		if (itemNum < 0 || itemNum >= this.numOfItems) {
			throw new IndexOutOfBoundsException();
		}
		if (font == null) {
			font = Font.getDefaultFont();
		}
		this.items[itemNum].setFont(font);
		if (this.choiceType == Choice.POPUP) {
			  this.popupList.setFont(itemNum, font);
		}
  }


        @Override
 	      public void setSelectedFlags(boolean[] selectedArray) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			((ChoiceGroupUI) ui).setSelectedFlags(selectedArray);
		} else {
			if (selectedArray == null) {
				throw new NullPointerException();
			}
			if (selectedArray.length < this.numOfItems) {
				throw new NullPointerException();
			}

			if (this.numOfItems == 0) {
				return;
			}
			if (this.choiceType == Choice.MULTIPLE) {
				for (int i = 0; i < this.numOfItems; i++) {
					this.setSelectedIndex(i, selectedArray[i]);
				}
			} else {
				int selectedItem = -1;
				for (int i = 0; i < this.numOfItems; i++) {
					if (selectedArray[i]) {
						this.setSelectedIndex(i, true);
						selectedItem = i;
						break;
					}
				}
				if (selectedItem == -1) {
					this.setSelectedIndex(0, true);
				}

				if (this.choiceType == Choice.POPUP) {
					this.popupList.setSelectedFlags(selectedArray);
				}
			}
		}
	}


        @Override
  	     public void setSelectedIndex(int elementNum, boolean selected) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			((ChoiceGroupUI) ui).setSelectedIndex(elementNum, selected);
		} else {
			if (elementNum < 0 || elementNum >= this.numOfItems) {
				throw new IndexOutOfBoundsException();
			}
	
	        this.highlightedItemIndex = elementNum;
			if ((this.choiceType == Choice.EXCLUSIVE ||
					this.choiceType == Choice.POPUP) && selected) {
				for (int i = 0; i < this.numOfItems; i++) {
					this.items[i].setSelectedState(elementNum == i);
				}
				if (this.choiceType == Choice.POPUP) {
				        this.popupList.setSelectedIndex(elementNum, true);
			    }
				 this.repaint();
			} else if (this.choiceType == Choice.MULTIPLE) {
				this.items[elementNum].setSelectedState(selected);
				this.repaint();
			} else if (this.choiceType == Choice.IMPLICIT) {
			    if (selected) {
					this.items[elementNum].setSelectedState(selected);
					this.repaint();
			    }
			}
		}
	}

        @Override
	       public int size() {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidChoiceGroupUI")) {
			return ((ChoiceGroupUI) ui).size();
		} else {
			return this.numOfItems;
		}
	}


        //TWB - made public
        @Override
	       public boolean isFocusable()
	{
		return true;
	}


        //TWB - made public
        @Override
	       public int getHeight()
	{
		int height = 0;
		if (this.choiceType == Choice.POPUP) {
			if (this.highlightedItemIndex != -1) {
				height += items[this.highlightedItemIndex].getHeight();
			}
		} else {
			for (int i = 0; i < this.numOfItems; i++) {
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
		for (int i = 0; i < this.numOfItems; i++) {
			testY += items[i].getHeight();
			if (y < testY) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	int getHeightToItem(int itemIndex) 
	{
		int height = 0;

		for (int i = 0; i < itemIndex; i++) {
			height += items[i].getHeight();
		}

		return height;
	}


	int getItemHeight(int itemIndex)
	{
		return this.items[itemIndex].getHeight();
	}
	

        //TWB - made public
        @Override
        public int paint(Graphics g)
  {
		super.paintContent(g);

		g.translate(0, super.getHeight());
		int translatedY = 0;
		
		if (this.choiceType == Choice.POPUP) {
			int index = this.getSelectedIndex();
			if (index != -1) {
				this.items[index].invertPaint(hasFocus());
				this.items[index].paint(g);
			}
			g.translate(0, -super.getHeight());
		} else {
			for (int i = 0; i < this.numOfItems; i++) {
				this.items[i].invertPaint(i == this.highlightedItemIndex && hasFocus());
				this.items[i].paint(g);
				g.translate(0, items[i].getHeight());
				translatedY += items[i].getHeight();
			}
			g.translate(0, -translatedY);
			g.translate(0, -super.getHeight());
		}

		return this.getHeight();
  }


  //TWB - made public
  @Override
  public boolean select()
  {
    if (this.numOfItems == 0) {
      return false;
    }

    if (this.choiceType == Choice.POPUP) {
        getOwner().currentDisplay.setCurrent(this.popupList);
    } else {
	    // XXX What does the following statement do?
    	
    	// It is correct, in the case of multiple inverts the selected
    	// state, in exclusive selects the highligthed
    	// and in implicit it does nothing
    	// Andres Navarro
	    this.setSelectedIndex(this.highlightedItemIndex, !items[this.highlightedItemIndex].isSelected());
    }

    return true;
  }

  
  //TWB - made public
  @Override
  public int traverse(int gameKeyCode, int top, int bottom, boolean action)
  {
	  
	if (this.choiceType == Choice.POPUP) {
		// POPUP has a totally different behaviour
	    if (gameKeyCode == Canvas.UP) {
			if (top > 0) {
				return -top;
			} else {
				return Item.OUTOFITEM;
			}
	    } else if (gameKeyCode == Canvas.DOWN) {
	    	if (!action) {
	    		int height = super.getHeight();
				int index = this.getSelectedIndex();
				if (index != -1) {
	    			height += items[index].getHeight();
				}

				if (height > bottom) {
	    			return height - bottom;
	    		} else {
	    			this.repaint();
	    		}
		     } else {
		    	 return Item.OUTOFITEM;
			}
	    }
	} else {
	    if (gameKeyCode == Canvas.UP) {
	      if (this.highlightedItemIndex > 0) {
					if (action) {
						this.highlightedItemIndex--;
					}
					int height = super.getHeight();
					for (int i = 0; i < this.highlightedItemIndex; i++) {
						height += items[i].getHeight();
					}
					if (height < top) {
						return height - top;
					} else {
						this.repaint();
					}
	      } else {
					if (top > 0) {
						return -top;
					} else {
						return Item.OUTOFITEM;
					}
				}
	    }
	    if (gameKeyCode == Canvas.DOWN) {
	      if ((!action && this.highlightedItemIndex < this.numOfItems)
	      		|| (action && highlightedItemIndex < (this.numOfItems - 1))) {
					if (action) {
						highlightedItemIndex++;
					}
					int height = super.getHeight();
					for (int i = 0; i <= this.highlightedItemIndex; i++) {
						height += items[i].getHeight();
					}
					if (height > bottom) {
						return height - bottom;
					} else {
						this.repaint();
					}
	      } else {
					return Item.OUTOFITEM;
		  }
	    }
	}

		return 0;
  }

  @Override
  void repaint() {
	  // the popup list should be repainted
	  // in the case it is being shown
	  if (this.choiceType == Choice.POPUP)
		  this.popupList.repaint();
	  super.repaint();
  }

  class ChoiceItem extends ImageStringItem
  {
    private boolean selected;
    private Font font;
    Image box;
           

    ChoiceItem(String label, Image image, String text)
    {
      super(label, image, text);
      this.setSelectedState(false);
      font = Font.getDefaultFont();
    }

    Font getFont() {
    	return this.font;
    }
    
    @Override
	   public void setImage(Image img)
	{
                this.img = img;
		
                int width = 0;
                if (this.box != null) width+=this.box.getWidth();
                if (this.img != NullImage.NULL_IMAGE) width+=img.getWidth();
                if (width > 0) width+=2;
                stringComponent.setWidthDecreaser(width);
	}

        //TWB - made public
        @Override
	       public int getHeight()
	{
                int height =  0;
                if (this.box != null) height = this.box.getHeight();                
		if (img != NullImage.NULL_IMAGE && img.getHeight() > height) {
			height = img.getHeight();
                }
		if (stringComponent.getHeight() > height) {
                            height = stringComponent.getHeight();                
		}
                return height;
	}

        //TWB - made public
        @Override
        public int paint(Graphics g)
  {
		if (stringComponent == null) {
			return 0;
		}

                int widthAddition = 0;
		if (this.box != null) {
			g.drawImage(box, 0, 0, Graphics.LEFT | Graphics.TOP);
			if (img != NullImage.NULL_IMAGE) {
                            widthAddition = box.getWidth();
                            g.translate(box.getWidth(), 0);
                        }
                        else {
                            widthAddition = box.getWidth() + 2;
                            g.translate(box.getWidth() + 2, 0);
                        }
		}
                
                
		if (img != NullImage.NULL_IMAGE) {
                        widthAddition += img.getWidth() + 2;
			g.drawImage(img, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.translate(img.getWidth() + 2, 0);
		}

		int y = stringComponent.paint(g);

		if (widthAddition != 0) {
			g.translate(-widthAddition, 0);
		}

		return y;
  }
        
        
    
    boolean isSelected()
    {
      return this.selected;
    }

    void setFont(Font f) {
		if (f == null)
			throw new NullPointerException();
    	// only allow fonts of the same height
    	// for now (to simplify the layout)
    	if (f.getHeight() == this.font.getHeight())
    		this.font = f;
    }
    
    void setSelectedState(boolean state)
    {
      selected = state;
      
      if (choiceType != Choice.IMPLICIT && choiceType != Choice.POPUP) {
          final ChoiceGroupImageFactory choiceGroupFactory = ChoiceGroupImageFactory.getInstance();
            this.box = (Choice.EXCLUSIVE  == choiceType ? (state? choiceGroupFactory.imgRadioOn:choiceGroupFactory.imgRadioOff) : (state? choiceGroupFactory.imgMultiOn:choiceGroupFactory.imgMultiOff));
      }
    }
  }

  class ImplicitListener implements CommandListener {
      @Override
		    public void commandAction(Command c, Displayable d) {
			List list = (List) d;
			setSelectedIndex(list.getSelectedIndex(), true);
			try {
				getOwner().currentDisplay.setCurrent(getOwner());
                ((Form)getOwner()).fireItemStateListener();
				repaint();
			} catch (NullPointerException n) {
				// this happens if the item becomes an orphan
				// (ie not owned by a Form, shouldn't happen
				// if correct programming practices are used!!)
			}
		}
	}
}
