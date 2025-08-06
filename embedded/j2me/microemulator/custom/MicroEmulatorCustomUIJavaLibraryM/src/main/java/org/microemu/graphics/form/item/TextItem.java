/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Screen;

import org.allbinary.AndroidUtil;
import org.allbinary.game.configuration.feature.Features;
import org.allbinary.game.configuration.feature.HTMLFeatureFactory;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.MyFont;
import org.allbinary.graphics.opengles.OpenGLFeatureFactory;

public class TextItem extends CustomCustomItem 
implements org.allbinary.graphics.form.item.CustomItemInterface
{
    //private Screen owner;

    private final int offsetX;
    private final int offsetWidth;
    private final int width;
    
    public TextItem(String label, int layout, String altText, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);

        final MyFont myFont = MyFont.getInstance();        
        final Features features = Features.getInstance();
        final boolean isHTML = features.isDefault(HTMLFeatureFactory.getInstance().HTML);
        final boolean isOpenGL = features.isDefault(OpenGLFeatureFactory.getInstance().OPENGL);

        int offsetX;
        int offsetWidth;
        if(isHTML || (AndroidUtil.isAndroid() && isOpenGL)) {
            offsetX = 0;
            offsetWidth = myFont.stringWidth(this.getLabel()) / 2;
        } else {
            offsetX = 2;
            offsetWidth = 2;
        }
        this.offsetX = offsetX;
        this.offsetWidth = offsetWidth;

        this.width = myFont.stringWidth(this.getLabel()) + offsetWidth;
        
    }

    @Override
    public void setOwner(Screen owner)
    {
        //this.owner = owner;
    }

    @Override
    public int getMinimumWidth()
    {
        return this.width;
    }

    @Override
    public int getMinimumHeight()
    {
        final MyFont myFont = MyFont.getInstance();
        return myFont.DEFAULT_CHAR_HEIGHT;
    }

    @Override
    protected int getMinContentHeight()
    {
        final MyFont myFont = MyFont.getInstance();
        return myFont.DEFAULT_CHAR_HEIGHT;
    }

    @Override
    protected int getMinContentWidth()
    {
        return this.width;
    }

    @Override
    protected int getPrefContentHeight(int width)
    {
        final MyFont myFont = MyFont.getInstance();
        return myFont.DEFAULT_CHAR_HEIGHT;
    }

    @Override
    protected int getPrefContentWidth(int height)
    {
        return this.width;
    }

    @Override
    public void paint(Graphics graphics, int x, int y)
    {
        graphics.setColor(this.getLabelStringComponent().getForegroundBasicColor().intValue());
        graphics.drawString(this.getLabel(), x + offsetX, y, 0);
    }

    @Override
    public void paintUnselected(Graphics graphics, int x, int y)
    {
        graphics.drawString(this.getLabel(), x + offsetX, y, 0);
    }
}
