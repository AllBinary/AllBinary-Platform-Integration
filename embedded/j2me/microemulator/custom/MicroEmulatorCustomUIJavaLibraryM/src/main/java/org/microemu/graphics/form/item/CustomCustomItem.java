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

package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Graphics;

import org.allbinary.graphics.form.item.CustomItem;

import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.ForcedLogUtil;
import org.allbinary.graphics.color.BasicColor;

public class CustomCustomItem extends CustomItem {
    //protected final LogUtil logUtil = LogUtil.getInstance();


    protected final CommonStrings commonStrings = CommonStrings.getInstance();
    
	protected static final int TRAVERSE_HORIZONTAL = 1;
	protected static final int TRAVERSE_VERTICAL = 2;
	protected static final int KEY_PRESS = 4;
	protected static final int KEY_RELEASE = 8;
	protected static final int KEY_REPEAT = 0x10;
	protected static final int POINTER_PRESS = 0x20;
	protected static final int POINTER_RELEASE = 0x40;
	protected static final int POINTER_DRAG = 0x80;
	protected static final int NONE = 0x00;

	protected CustomCustomItem(String label, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor) {
		super(label, backgroundBasicColor, foregroundBasicColor);
	}
	
	public int getGameAction(int keycode) {
		return 0;
	}
	
	protected final int getInteractionModes() {
		return NONE;
	}
	
	protected int getMinContentHeight()
        {
	    ForcedLogUtil.log(commonStrings.NOT_IMPLEMENTED, this);
            return 0;
        }
	
	protected int getMinContentWidth()
        {
            ForcedLogUtil.log(commonStrings.NOT_IMPLEMENTED, this);
            return 0;
        }

	protected int getPrefContentHeight(int width)
        {
            ForcedLogUtil.log(commonStrings.NOT_IMPLEMENTED, this);
            return 0;
        }

	protected int getPrefContentWidth(int height)
        {
            ForcedLogUtil.log(commonStrings.NOT_IMPLEMENTED, this);
            return 0;
        }

	protected void hideNotify() {
	}
	
	protected final void invalidate() {

	}

	public void keyPressed(int keyCode) {
	}
	
	public void keyReleased(int keyCode) {
	}

	protected void keyRepeated(int keyCode) {
	}
	
	public void paint(Graphics graphics, int x, int y)
	{
	    
	}
	
	/*
	protected void paint(Graphics graphics, int w, int h)
        {
	    ForcedLogUtil.log(commonStrings.NOT_IMPLEMENTED, this);
        }
    */

	protected void pointerDragged(int x, int y) {
	}

	protected void pointerPressed(int x, int y) {
	}
	
	protected void pointerReleased(int x, int y) {
	}
	
	protected final void repaint(int x, int y, int w, int h) {
		repaint();
	}
	
	protected void showNotify() {
	}

	protected void sizeChanged(int w, int h) {
	}
	
	protected boolean traverse(int dir, int viewportWidth,
            					int viewportHeight, int[] visRect_inout) {
		return false;
	}

	protected void traverseOut() {
	}
	
	public int paint(Graphics graphics) {
	    
	    //logUtil.put(commonStrings.START, this, "paint");
	    
		//super.paintContent(graphics);
		this.paint(graphics, 0, 0);
		return this.getHeight();
	}
}
