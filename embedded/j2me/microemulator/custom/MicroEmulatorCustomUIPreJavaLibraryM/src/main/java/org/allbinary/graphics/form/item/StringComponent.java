
/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import org.allbinary.graphics.color.BasicColor;

public class StringComponent extends org.microemu.graphics.form.StringComponent
{
    public StringComponent(final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor)
    {
        super(backgroundBasicColor, foregroundBasicColor);
    }

    public StringComponent(final String text, final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor)
    {
        super(text, backgroundBasicColor, foregroundBasicColor);
    }
}
