/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form;

import jsinterop.annotations.JsType;

import org.allbinary.graphics.form.item.ABCustomItem;
import org.microemu.graphics.form.CustomForm;
import org.allbinary.graphics.color.BasicColor;
import jsinterop.annotations.JsConstructor;


@JsType
public class ABCustomForm extends CustomForm
{
    @JsConstructor
    public ABCustomForm(String title, ABCustomItem[] items, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(title, items, backgroundBasicColor, foregroundBasicColor);
    }
}
