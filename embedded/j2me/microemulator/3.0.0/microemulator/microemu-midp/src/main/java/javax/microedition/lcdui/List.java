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
 *    3GLab
 */

package javax.microedition.lcdui;


import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.ListUI;


public class List extends Screen implements Choice {

    public static final Command SELECT_COMMAND = new Command("", Command.SCREEN, 0);

    ChoiceGroup choiceGroup;

    private Command selCommand;

    private int initialPressedItem;

    public List(String title, int listType) {
        super(title);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createListUI(this));

        if (listType != Choice.IMPLICIT && listType != Choice.MULTIPLE && listType != Choice.EXCLUSIVE)
            throw new IllegalArgumentException("Illegal list type");

        if (listType == Choice.IMPLICIT) {
            this.choiceGroup = new ChoiceGroup(null, Choice.IMPLICIT, false);
        } else {
            this.choiceGroup = new ChoiceGroup(null, listType);
        }

        this.choiceGroup.setOwner(this);
        this.choiceGroup.setFocus(true);

        this.selCommand = List.SELECT_COMMAND;
        this.initialPressedItem = -1;
    }

    public List(String title, int listType, String[] stringElements, Image[] imageElements) {
        super(title);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createListUI(this));

		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
			for (int i = 0; i < stringElements.length; i++) {
				if (imageElements == null) {
					this.append(stringElements[i], null);
				} else {
					this.append(stringElements[i], imageElements[i]);
				}
			}
			this.choiceGroup = new ChoiceGroup(null, listType, stringElements, imageElements, false);
		} else {
	        if (listType == Choice.IMPLICIT) {
	            this.choiceGroup = new ChoiceGroup(null, Choice.IMPLICIT, stringElements, imageElements, false);
	            for (int i = 0; i < size(); i++) {
	                this.set(i, getString(i), null);
	            }
	        } else {
	            this.choiceGroup = new ChoiceGroup(null, listType, stringElements, imageElements);
	        }
		}
        this.choiceGroup.setOwner(this);
        this.choiceGroup.setFocus(true);

        this.selCommand = List.SELECT_COMMAND;
        this.initialPressedItem = -1;
    }

    @Override
    public int append(String stringPart, Image imagePart) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
			return ((ListUI) ui).append(stringPart, imagePart);
		} else {
			return this.choiceGroup.append(stringPart, imagePart);
		}
    }

    @Override
    public void delete(int elementNum) {
      if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
        ((ListUI) ui).delete(elementNum);
      } else {
        this.choiceGroup.delete(elementNum);
      }
    }

    @Override
    public void deleteAll() {
      if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
        ((ListUI) ui).deleteAll();
      } else {
        this.choiceGroup.deleteAll();
      }
    }

    @Override
    public int getFitPolicy() {
        return this.choiceGroup.getFitPolicy();
    }

    @Override
    public Font getFont(int elementNum) {
        return this.choiceGroup.getFont(elementNum);
    }

    @Override
    public Image getImage(int elementNum) {
        return this.choiceGroup.getImage(elementNum);
    }

    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) {
        return this.choiceGroup.getSelectedFlags(selectedArray_return);
    }

    @Override
    public int getSelectedIndex() {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
			return ((ListUI) ui).getSelectedIndex();
		} else {
			return this.choiceGroup.getSelectedIndex();
		}
    }

    @Override
    public String getString(int elementNum) {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
			return ((ListUI) ui).getString(elementNum);
		} else {
        	return this.choiceGroup.getString(elementNum);
		}
    }

    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) {
    	if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
    		((ListUI) ui).insert(elementNum, stringPart, imagePart);
    	} else {
    		this.choiceGroup.insert(elementNum, stringPart, imagePart);
    	}    
    }

    @Override
    public boolean isSelected(int elementNum) {
        return this.choiceGroup.isSelected(elementNum);
    }

    @Override
    public void removeCommand(Command cmd) {
        // TODO implement
        super.removeCommand(cmd);
    }

    @Override
    public void set(int elementNum, String stringPart, Image imagePart) {
    	if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
    		((ListUI) ui).set(elementNum, stringPart, imagePart);
    	} else {
    		this.choiceGroup.set(elementNum, stringPart, imagePart);
    	}
    }

    @Override
    public void setFitPolicy(int policy) {
        this.choiceGroup.setFitPolicy(policy);
    }

    @Override
    public void setFont(int elementNum, Font font) {
        this.choiceGroup.setFont(elementNum, font);
    }

    public void setSelectCommand(Command command) {
        this.selCommand = command;
        
        ((ListUI) ui).setSelectCommand(command);
    }

    @Override
    public void setSelectedFlags(boolean[] selectedArray) {
        this.choiceGroup.setSelectedFlags(selectedArray);
    }

    @Override
    public void setSelectedIndex(int elementNum, boolean selected) {
      if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
        ((ListUI) ui).setSelectedIndex(elementNum, selected);
      } else {
        this.choiceGroup.setSelectedIndex(elementNum, selected);
      }
    }

    @Override
    public void setTicker(Ticker ticker) {
        super.setTicker(ticker);
        // TODO size of changed probably
    }

    @Override
    public void setTitle(String s) {
        // TODO implement
        super.setTitle(s);
    }

    @Override
    public void keyPressed(int keyCode) {
        if (Display.getGameAction(keyCode) == Canvas.FIRE && this.choiceGroup.select() && super.getCommandListener() != null
                && this.choiceGroup.choiceType == Choice.IMPLICIT) {
        	MIDletBridge.getMIDletAccess().getDisplayAccess().commandAction(this.selCommand, this);
        } else {
            super.keyPressed(keyCode);
        }
    }

    @Override
    void pointerPressed(int x, int y) {
        Ticker ticker = getTicker();
        if (ticker != null) {
            y -= ticker.getHeight();
        }
        // TODO remove this StringComponent object when native UI is completed
        StringComponent title = new StringComponent(getTitle());
        y -= title.getHeight();
        y -= 1;
        if (y >= 0 && y < viewPortHeight) {
            int pressedItem = this.choiceGroup.getItemIndexAt(x, y + viewPortY);
            if (pressedItem != -1) {
                if (this.choiceGroup.choiceType == Choice.MULTIPLE) {
                    this.setSelectedIndex(pressedItem, !isSelected(pressedItem));
                } else {
                    this.setSelectedIndex(pressedItem, true);
                }
                this.initialPressedItem = pressedItem;
            }
        }
    }

    @Override
    void pointerReleased(int x, int y) {
        Ticker ticker = getTicker();
        if (ticker != null) {
            y -= ticker.getHeight();
        }
        // TODO remove this StringComponent object when native UI is completed
        StringComponent title = new StringComponent(getTitle());
        y -= title.getHeight();
        y -= 1;
        if (y >= 0 && y < viewPortHeight && this.choiceGroup.choiceType == Choice.IMPLICIT) {
            int releasedItem = this.choiceGroup.getItemIndexAt(x, y + viewPortY);
            if (releasedItem != -1) {
                if (releasedItem == this.initialPressedItem && super.getCommandListener() != null
                        && this.choiceGroup.choiceType == Choice.IMPLICIT) {
                	MIDletBridge.getMIDletAccess().getDisplayAccess().commandAction(List.SELECT_COMMAND, this);
                }
            }
        }
    }

    @Override
    int paintContent(Graphics g) {
        return this.choiceGroup.paint(g);
    }

    @Override
    public int size() {
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
			return ((ListUI) ui).size();
		} else {
			return this.choiceGroup.size();
		}
    }

    @Override
    void showNotify() {
        super.showNotify();

		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
		} else {
	        int selectedItemIndex = this.getSelectedIndex();
	        int heightToItem = this.choiceGroup.getHeightToItem(selectedItemIndex);
	        int heightAfterItem = heightToItem;
	        if (selectedItemIndex >= 0) {
	            heightAfterItem += this.choiceGroup.getItemHeight(selectedItemIndex);
	        }
	        if (viewPortY > heightToItem) {
	            viewPortY = heightToItem;
	        } else if ((viewPortY + viewPortHeight) < heightAfterItem) {
	            viewPortY = heightAfterItem - viewPortHeight;
	        }
		}
    }

    @Override
    int traverse(int gameKeyCode, int top, int bottom) {
        int traverse = choiceGroup.traverse(gameKeyCode, top, bottom, true);
        if (traverse == Item.OUTOFITEM) {
            return 0;
        } else {
            return traverse;
        }
    }

}
