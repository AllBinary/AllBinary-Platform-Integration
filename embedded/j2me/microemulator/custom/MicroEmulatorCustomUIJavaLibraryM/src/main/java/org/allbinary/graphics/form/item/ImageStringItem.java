/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import javax.microedition.lcdui.Image;

import org.allbinary.graphics.color.BasicColor;

public class ImageStringItem extends org.microemu.graphics.form.item.ImageStringItem
{
    public ImageStringItem(String label, Image img, String text, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, img, text, backgroundBasicColor, foregroundBasicColor);
    }
}
