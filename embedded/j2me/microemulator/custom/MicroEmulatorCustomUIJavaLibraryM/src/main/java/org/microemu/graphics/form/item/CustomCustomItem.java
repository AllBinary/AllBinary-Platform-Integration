/*
 *  MicroEmulator
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
 */
package org.microemu.graphics.form.item;

import jsinterop.annotations.JsType;

import javax.microedition.lcdui.Graphics;

import org.allbinary.graphics.form.item.ABCustomItem;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.ForcedLogUtil;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.MyFontProcessor;
import org.allbinary.graphics.font.UpdateMyFontInterface;
import org.allbinary.graphics.font.UpdateMyFontProcessor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
public class CustomCustomItem extends ABCustomItem implements UpdateMyFontInterface {

    @JsProperty
    protected static final int TRAVERSE_HORIZONTAL = 1;
    @JsProperty
    protected static final int TRAVERSE_VERTICAL = 2;
    @JsProperty
    protected static final int KEY_PRESS = 4;
    @JsProperty
    protected static final int KEY_RELEASE = 8;
    @JsProperty
    protected static final int KEY_REPEAT = 0x10;
    @JsProperty
    protected static final int POINTER_PRESS = 0x20;
    @JsProperty
    protected static final int POINTER_RELEASE = 0x40;
    @JsProperty
    protected static final int POINTER_DRAG = 0x80;
    @JsProperty
    protected static final int NONE = 0x00;

    //protected final LogUtil logUtil = LogUtil.getInstance();

    @JsProperty
    protected final CommonStrings commonStrings = CommonStrings.getInstance();

    @JsProperty
    protected final MyFontProcessor updateMyFontProcessor = new UpdateMyFontProcessor(this);
    @JsProperty
    protected MyFontProcessor myFontProcessor = this.updateMyFontProcessor;
    
    @JsConstructor
    protected CustomCustomItem(String label, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor) {
        super(label, backgroundBasicColor, foregroundBasicColor);
    }

    @Override
    @JsMethod
    public void updateMeasurement(final Graphics graphics) {
    }
    
    @JsMethod
    public int getGameAction(int keycode) {
        return 0;
    }

    @JsMethod
    protected final int getInteractionModes() {
        return CustomCustomItem.NONE;
    }

    @JsMethod
    protected int getMinContentHeight() {
        ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
        return 0;
    }

    @JsMethod
    protected int getMinContentWidth() {
        ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
        return 0;
    }

    @JsMethod
    protected int getPrefContentHeight(int width) {
        ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
        return 0;
    }

    @JsMethod
    protected int getPrefContentWidth(int height) {
        ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
        return 0;
    }

    protected void hideNotify() {
    }

    @JsMethod
    protected final void invalidate() {

    }

    @Override
    @JsMethod
    public void keyPressed(int keyCode) {
    }

    @JsMethod
    public void keyReleased(int keyCode) {
    }

    @JsMethod
    protected void keyRepeated(int keyCode) {
    }

    @Override
    @JsMethod
    public void paintXY(Graphics graphics, int x, int y) {

    }

    /*
	protected void paint(Graphics graphics, int w, int h)
        {
	    ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
        }
     */
    @JsMethod
    protected void pointerDragged(int x, int y) {
    }

    @JsMethod
    protected void pointerPressed(int x, int y) {
    }

    @JsMethod
    protected void pointerReleased(int x, int y) {
    }

    @JsMethod
    protected final void repaintXYWH(int x, int y, int w, int h) {
        this.repaint();
    }

    @JsMethod
    protected void showNotify() {
    }

    @JsMethod
    protected void sizeChanged(int w, int h) {
    }

    @JsMethod
    protected boolean traverseViewport(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        return false;
    }

    @JsMethod
    protected void traverseOut() {
    }

    @Override
    @JsMethod
    public int paint(Graphics graphics) {

        //logUtil.putF(commonStrings.START, this, "paint");
        //super.paintContent(graphics);
        this.paintXY(graphics, 0, 0);
        return this.getHeight();
    }
    
    @Override
    @JsMethod
    public void preMeasurement(final Graphics graphics) {
        this.myFontProcessor.process(graphics);
    }
    
}
