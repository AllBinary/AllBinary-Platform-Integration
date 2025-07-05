/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.logic.util.visitor.Visitor;

public class TextFieldItem extends org.microemu.graphics.form.item.TextFieldItem
{
    
    public TextFieldItem(Canvas canvas, Visitor visitor, String label, String value, int maxSize, int layout, String altText, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor) {
        this(canvas, visitor, label, value, maxSize, layout, altText, Font.getDefaultFont(),
            backgroundBasicColor, foregroundBasicColor);
    }

    public TextFieldItem(Canvas canvas, Visitor visitor, String label, String value, int maxSize, int layout, String altText, 
            final Font font, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(canvas, visitor, label, value, maxSize, layout, altText, font, backgroundBasicColor, foregroundBasicColor);
    }
    
}
