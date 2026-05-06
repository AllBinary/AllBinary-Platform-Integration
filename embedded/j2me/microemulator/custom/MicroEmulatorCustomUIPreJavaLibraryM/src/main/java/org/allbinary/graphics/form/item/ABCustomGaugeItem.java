/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.color.BasicColorFactory;
import org.allbinary.logic.string.StringUtil;

import org.microemu.graphics.form.item.CustomGaugeItem;

public class ABCustomGaugeItem extends CustomGaugeItem
{
    public static final ABCustomGaugeItem NULL_GAUGE_ITEM = new ABCustomGaugeItem(
    StringUtil.getInstance().EMPTY_STRING, 1, 0, 
        BasicColorFactory.getInstance().BLACK, BasicColorFactory.getInstance().WHITE);
    
    public ABCustomGaugeItem(String label, int maxValue, int initialValue,
                             BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, maxValue, initialValue, backgroundBasicColor, foregroundBasicColor);
    }
}
