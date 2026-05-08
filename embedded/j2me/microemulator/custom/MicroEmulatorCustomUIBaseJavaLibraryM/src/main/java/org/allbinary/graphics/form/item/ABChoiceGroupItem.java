/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.allbinary.graphics.form.item;

import javax.microedition.lcdui.Image;

import org.allbinary.graphics.color.BasicColor;
import org.microemu.graphics.form.item.ChoiceGroupItem;
public class ABChoiceGroupItem extends ChoiceGroupItem
{
    public ABChoiceGroupItem(String label, int choiceType,
                             BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, choiceType, backgroundBasicColor, foregroundBasicColor);
    }

//    public ABChoiceGroupItem(String label, int choiceType, String[] stringElements,
//                             Image[] imageElements, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
//    {
//        super(label, choiceType, stringElements, imageElements,
//                backgroundBasicColor, foregroundBasicColor);
//    }

}
