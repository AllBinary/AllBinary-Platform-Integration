/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form;

import org.microemu.graphics.form.CustomForm;

import org.allbinary.graphics.form.item.CustomItem;
import org.allbinary.graphics.color.BasicColor;

public class ABCustomForm extends CustomForm
{
    public ABCustomForm(String title, CustomItem[] items, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(title, items, backgroundBasicColor, foregroundBasicColor);
    }
}
