
/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import javax.microedition.lcdui.Font;

import org.allbinary.logic.string.StringUtil;
import org.microemu.graphics.form.StringComponent;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.color.BasicColorFactory;

public class ABStringComponent extends StringComponent
{
    public static final ABStringComponent NULL_STRING_COMPONENT = new ABStringComponent(BasicColorFactory.getInstance().BLACK, BasicColorFactory.getInstance().WHITE);

    public ABStringComponent(final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor)
    {
        super(StringUtil.getInstance().EMPTY_STRING, Font.getDefaultFont(), backgroundBasicColor, foregroundBasicColor);
    }

}
