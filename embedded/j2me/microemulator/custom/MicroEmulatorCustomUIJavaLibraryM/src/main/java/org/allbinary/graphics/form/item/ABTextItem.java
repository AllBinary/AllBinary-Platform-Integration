/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import jsinterop.annotations.JsType;
import jsinterop.annotations.JsConstructor;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.logic.communication.log.PreLogUtil;

import org.microemu.graphics.form.item.TextItem;


@JsType
public class ABTextItem extends TextItem
{
    @JsConstructor
    public ABTextItem(final String label, final int layout, final String altText,
        final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor)
    {
        super(label, layout, altText, backgroundBasicColor, foregroundBasicColor);
        
    }
}
