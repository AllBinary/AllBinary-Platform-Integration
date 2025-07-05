/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import javax.microedition.lcdui.Image;

import org.allbinary.graphics.color.BasicColor;

public class CustomImageItem extends org.microemu.graphics.form.item.CustomImageItem
{
    public CustomImageItem(String label, Image image, int layout, String altText, BasicColor basicColor)
            throws Exception
    {
        super(label, image, layout, altText, basicColor);
    }

    public CustomImageItem(String label, Image image, int layout, String altText, BasicColor basicColor, int yOffset) throws Exception
    {
        super(label, image, layout, altText, basicColor, yOffset);
    }
}
