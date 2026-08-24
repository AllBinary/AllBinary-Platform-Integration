/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Screen;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import org.allbinary.AndroidUtil;
import org.allbinary.J2MEUtil;
import org.allbinary.game.configuration.feature.Features;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.MyFontProcessor;
import org.allbinary.graphics.form.item.ABCustomItemInterface;
import org.allbinary.graphics.opengles.OpenGLFeatureFactory;
import org.allbinary.graphics.threed.SWTJOGLProcessor;


@JsType
public class TextItem extends CustomCustomItem
        implements ABCustomItemInterface
{
    //private Screen owner;

    @JsProperty
    protected int fontHeightP;
    private int offsetX;
    private int offsetY;
    //private int offsetWidth;
    private int width;

    @JsConstructor
    public TextItem(String label, int layout, String altText,
                    BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);
    }

    @Override
    @JsMethod
    public void updateMeasurement(final Graphics graphics) {
        final Font font = graphics.getFont();
        this.fontHeightP = font.getHeight();

        final Features features = Features.getInstance();
        final boolean isOpenGL = features.isDefault(OpenGLFeatureFactory.getInstance().OPENGL);
        int offsetX;
        int offsetY;
        int offsetWidth;
        final String labelSet = this.getLabel();
        if(J2MEUtil.isHTML() || (SWTJOGLProcessor.getInstance().isJOGL() && isOpenGL)) {
            offsetX = 0;
            offsetY = 0;
            offsetWidth = font.stringWidth(labelSet) / 2;
        } else if(J2MEUtil.isJ2SE()) {
            offsetX = 0;
            offsetY = -4;
            offsetWidth = font.stringWidth(labelSet) / 3;
        } else if(AndroidUtil.isAndroid()) {
            offsetX = 0;
            offsetY = 0;
            offsetWidth = font.stringWidth(labelSet) / 4;
        } else {
            offsetX = 2;
            offsetY = 0;
            offsetWidth = 2;
        }
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        //this.offsetWidth = offsetWidth;

        this.width = font.stringWidth(labelSet) + offsetWidth;

        this.myFontProcessor = MyFontProcessor.getInstance();
    }

    @Override
    @JsMethod
    public void setOwner(Screen owner)
    {
        //this.owner = owner;
    }

    @Override
    @JsMethod
    public int getMinimumWidth()
    {
        return this.width;
    }

    @Override
    @JsMethod
    public int getMinimumHeight()
    {
        return this.fontHeightP;
    }

    @Override
    @JsMethod
    protected int getMinContentHeight()
    {
        return this.fontHeightP;
    }

    @Override
    @JsMethod
    protected int getMinContentWidth()
    {
        return this.width;
    }

    @Override
    @JsMethod
    protected int getPrefContentHeight(int width)
    {
        return this.fontHeightP;
    }

    @Override
    @JsMethod
    protected int getPrefContentWidth(int height)
    {
        return this.width;
    }

    @Override
    @JsMethod
    public void paintXY(Graphics graphics, int x, int y)
    {
        this.myFontProcessor.process(graphics);

        graphics.setColor(this.getLabelStringComponent().getForegroundBasicColor().intValue());
        graphics.drawString(this.getLabel(), x + this.offsetX, y + this.offsetY, 0);
    }

    @Override
    @JsMethod
    public void paintUnselected(Graphics graphics, int x, int y)
    {
        this.myFontProcessor.process(graphics);

        graphics.drawString(this.getLabel(), x + this.offsetX, y + this.offsetY, 0);
    }
}
