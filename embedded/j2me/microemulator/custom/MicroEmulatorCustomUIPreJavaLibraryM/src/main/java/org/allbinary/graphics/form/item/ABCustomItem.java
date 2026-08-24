/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import javax.microedition.lcdui.Graphics;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.color.BasicColorFactory;
import org.allbinary.logic.NullUtil;
import org.allbinary.logic.string.StringUtil;
import org.microemu.graphics.form.item.CustomItem;

@JsType
public class ABCustomItem extends CustomItem
{
    
    public static Object NULL_CUSTOM_ITEM = NullUtil.getInstance().NULL_OBJECT;
    
    @JsMethod
    public static ABCustomItem getNullInstance() {
        
        if(ABCustomItem.NULL_CUSTOM_ITEM == NullUtil.getInstance().NULL_OBJECT) {
            ABCustomItem.NULL_CUSTOM_ITEM = new ABCustomItem(StringUtil.getInstance().EMPTY_STRING, BasicColorFactory.getInstance().BLACK, BasicColorFactory.getInstance().WHITE);
        }
        
        return (ABCustomItem) ABCustomItem.NULL_CUSTOM_ITEM;
    }

    @JsProperty
    public static final int OUTOFITEM = Integer.MAX_VALUE;

    @JsConstructor
    protected ABCustomItem(String label, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);
    }

    @JsMethod
    public void paintXY(Graphics graphics, int x, int y)
    {

    }

    @JsMethod
    public void paintUnselected(Graphics graphics, int x, int y) {

    }

    @JsMethod
    public void preMeasurement(final Graphics graphics) {
        
    }
    
}
