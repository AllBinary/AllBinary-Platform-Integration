/*
 *  MicroEmulator
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
 */

package javax.microedition.lcdui;

import org.microemu.CustomItemAccess;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.CustomItemUI;

public class CustomItem extends Item {
	protected static final int TRAVERSE_HORIZONTAL = 1;
	protected static final int TRAVERSE_VERTICAL = 2;
	protected static final int KEY_PRESS = 4;
	protected static final int KEY_RELEASE = 8;
	protected static final int KEY_REPEAT = 0x10;
	protected static final int POINTER_PRESS = 0x20;
	protected static final int POINTER_RELEASE = 0x40;
	protected static final int POINTER_DRAG = 0x80;
	protected static final int NONE = 0x00;

	protected CustomItem(String label) {
		super(label);
		super.setUI(DeviceFactory.getDevice().getUIFactory().createCustomItemUI(new CustomItemAccess() {

                    @Override
			public CustomItem getCustomItem() {
				return CustomItem.this;
			}

                        @Override
			public int getPrefContentWidth(int height) {
				return CustomItem.this.getPrefContentWidth(height);
			}
			
                        @Override
			public int getPrefContentHeight(int width) {
				return CustomItem.this.getPrefContentHeight(width);
			}

                        @Override
			public void paint(Graphics g, int w, int h) {
				CustomItem.this.paint(g, w, h);
			}

		}));
	}
	
	public int getGameAction(int keycode) {
		// TODO add support for keypress
		return 0;
	}
	
	protected final int getInteractionModes() {
		return CustomItem.KEY_PRESS | CustomItem.KEY_RELEASE | CustomItem.KEY_REPEAT | CustomItem.POINTER_PRESS | CustomItem.POINTER_RELEASE | CustomItem.POINTER_DRAG;
	}
	
	protected int getMinContentHeight() {
            return -1;
        }
	
	protected int getMinContentWidth() {
            return -1;
        }
	
	protected int getPrefContentHeight(int width) {
            return -1;
        }

	protected int getPrefContentWidth(int height) {
            return -1;
        }

	protected void hideNotify() {
		// the default implementation of this method
		// does nothing
	}
	
	protected final void invalidate() {
		repaintOwner();
	}

        //TWB - made public
        //protected
        @Override
	public void keyPressed(int keyCode) {
		// the default implementation of this method
		// does nothing
	}
	
	protected void keyReleased(int keyCode) {
		// the default implementation of this method
		// does nothing
	}

	protected void keyRepeated(int keyCode) {
		// the default implementation of this method
		// does nothing
	}
	
	protected void paint(Graphics g, int w, int h) {
            
        }

	protected void pointerDragged(int x, int y) {
		// the default implementation of this method
		// does nothing
	}

	protected void pointerPressed(int x, int y) {
		// the default implementation of this method
		// does nothing
	}
	
	protected void pointerReleased(int x, int y) {
		// the default implementation of this method
		// does nothing
	}
	
        @Override
	protected final void repaint() {
		if (ui == null) {
			// ui is not initialized yet
			return;
		}
		
		if (ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidCustomItemUI")) {
			((CustomItemUI) ui).repaint();
		} else {
			super.repaint();
		}
	}

	protected final void repaint(int x, int y, int w, int h) {
		// TODO add support for partial repaint
		this.repaint();
	}
	
	protected void showNotify() {
		// the default implementation of this method
		// does nothing
	}

	protected void sizeChanged(int w, int h) {
		// the default implementation of this method
		// does nothing
	}
	
	protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
		// the default implementation of this method
		// does nothing
		return false;
	}

	protected void traverseOut() {
		// the default implementation of this method
		// does nothing
	}

	
	// Item methods
	
    // TODO write overrides for getMinimumWidth, etc

	// Keep track of current height and width
	int width = 0, height = 0;

        //TWB - made public
        @Override
	public int paint(Graphics g) {
		// Get preferred width and
		width = this.getPrefContentWidth(-1);
		height = this.getPrefContentHeight(-1);
		// Paint label
		super.paintContent(g);
		// Move graphics context down
		g.translate(0, super.getHeight());
		// Paint custom item
		this.paint(g, width, height);
		// Return 'height' which is the amount the context must translated
		return height;
	}

	// If this method is not implemented, the height of the component is not counted
        //TWB - made public
        @Override
	public int getHeight() {
		return super.getHeight() + this.height;
	}

        //TWB - made public
        @Override
	public int traverse(int gameKeyCode, int top, int bottom, boolean action) {
		int[] inout = new int[4];
        inout[0] = 0;
        inout[1] = top;
        inout[2] = this.width;
        inout[3] = bottom - top;
		boolean result = this.traverse(gameKeyCode, width, bottom - top, inout);
		if (result == false) {
			return Item.OUTOFITEM;
		} else {
			return inout[1] - top;
		}
                
/*
		Font f = Font.getDefaultFont();
		if (gameKeyCode == Canvas.UP) {
			if (top > 0) {
				if ((top % f.getHeight()) == 0) {
					return -f.getHeight();
				} else {
					return -(top % f.getHeight());
				}
			} else {
				return Item.OUTOFITEM;
			}
		}
		if (gameKeyCode == Canvas.DOWN) {
			if (bottom < getHeight()) {
				if (getHeight() - bottom < f.getHeight()) {
					return getHeight() - bottom;
				} else {
					return f.getHeight();
				}
			} else {
				return Item.OUTOFITEM;
			}
		}

		return 0;
 */                
	}

        //TWB - made public
        @Override
	public boolean isFocusable() {
		return true;
	}

        //TWB - made public
        @Override
	public boolean select() {
		// send a FIRE keycode here, otherwise there does not appear
		// to be a way for a CustomItem to use that key
		this.keyPressed(-5);
		return super.select();
	}

}
