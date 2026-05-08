/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Screen;

import org.allbinary.AndroidUtil;
import org.allbinary.J2MEUtil;
import org.allbinary.game.configuration.feature.Features;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.MyFont;
import org.allbinary.graphics.form.item.ABCustomItemInterface;
import org.allbinary.graphics.opengles.OpenGLFeatureFactory;

public class TextItem extends CustomCustomItem 
implements ABCustomItemInterface
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
        final boolean isOpenGL = features.isDefault(OpenGLFeatureFactory.getInstance().OPENGL);

        int offsetX;
        int offsetWidth;
        final String labelSet = this.getLabel();
        if(J2MEUtil.isHTML() || (AndroidUtil.isAndroid() && isOpenGL)) {
            offsetX = 0;
            offsetWidth = myFont.stringWidth(labelSet) / 2;
        } else {
            offsetX = 2;
            offsetWidth = 2;
        }
        this.offsetX = offsetX;
        this.offsetWidth = offsetWidth;

        this.width = myFont.stringWidth(labelSet) + offsetWidth;
        
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
        graphics.drawString(this.getLabel(), x + this.offsetX, y, 0);
    }

    @Override
    public void paintUnselected(Graphics graphics, int x, int y)
    {
        graphics.drawString(this.getLabel(), x + this.offsetX, y, 0);
    }
}
