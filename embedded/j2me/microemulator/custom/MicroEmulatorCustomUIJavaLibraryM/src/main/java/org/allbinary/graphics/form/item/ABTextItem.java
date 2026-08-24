/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import jsinterop.annotations.JsType;

import org.allbinary.graphics.color.BasicColor;
import org.microemu.graphics.form.item.TextItem;
import jsinterop.annotations.JsConstructor;


@JsType
public class ABTextItem extends TextItem
{
    @JsConstructor
    public ABTextItem(String label, int layout, String altText,
                      BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, layout, altText, backgroundBasicColor, foregroundBasicColor);
    }
}
