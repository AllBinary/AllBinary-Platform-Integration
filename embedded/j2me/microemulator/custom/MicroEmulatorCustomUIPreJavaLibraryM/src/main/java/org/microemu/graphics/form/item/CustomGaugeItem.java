/*
 *  MicroEmulator
 *  Copyright (C) 2005 Andres Navarro
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Graphics;

import org.allbinary.J2MEUtil;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.graphics.font.MyFont;

public class CustomGaugeItem extends CustomItem
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    
    private int height = 30;

    private float value;
    private float maxValue;
    
    //private final int currentOuterColor = 0xff000000;
    private final long currentRed = 0xffff0000;
    private final long currentGreen = 0xff00ff00;
    private final long currentBlue = 0xff0000ff;
    private final long START_INNER_COLOR = this.currentRed;
    private long currentInnerColor = 0xffff0000;

    private final DisplayInfoSingleton displayInfoSingleton = 
            DisplayInfoSingleton.getInstance();
    
    public CustomGaugeItem(String label, int maxValue, int initialValue, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);

        this.setMaxValue(maxValue);
        this.setValue((float) initialValue);
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
    
    public void setValue(float value)
    {

        if (value < 0.0f)
        {
            value = 0.0f;
        }

        if (value > this.maxValue)
        {
            value = this.maxValue;
        }

        this.value = value;

        int percent = (int) ((this.value * 100) / this.getMaxValue());
        if(percent < 25)
        {
            this.currentInnerColor = this.START_INNER_COLOR;
        }
        else
            if(percent < 66)
        {
            this.currentInnerColor = this.currentGreen | this.currentBlue; 
        }
            else
            {
                this.currentInnerColor = this.currentGreen; 
            }

        if(!J2MEUtil.isJ2ME())
        {
            this.repaint();
        }
    }

    public float getValue()
    {
        return this.value;
    }

    public void setMaxValue(int maxValue)
    {
        if (maxValue > 0)
        {
            this.maxValue = (float) maxValue;
            this.setValue(getValue());
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public float getMaxValue()
    {
        return this.maxValue;
    }

    public int getMinContentWidth()
    {
        return 0;
    }

    public int getPrefContentWidth(int width)
    {
        return super.getPreferredWidth();
    }

    public int getMinContentHeight()
    {
        return 0;
    }

    public int getPrefContentHeight(int height)
    {
        return super.getPreferredHeight();
    }

    public void paint(Graphics graphics, int unused, int hunused)
    {
        graphics.setColor(this.getLabelStringComponent().getBackgroundBasicColor().intValue());
        //g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
        //graphics.fillRect(0, 0, graphics.getClipWidth(), height);
        graphics.fillRect(0, 0, this.displayInfoSingleton.getLastWidth(), height);

        // Don't use WHITE.intValue() because of static load time
        graphics.setColor(this.getCurrentInnerColor());
        graphics.drawString(this.getLabel(), 4, 0, 0);

        final MyFont myFont = MyFont.getInstance();

        if(this.height == 30)
        {
            //int width = (int) ((graphics.getClipWidth() - 8) * value / maxValue);
            int width = (int) ((this.displayInfoSingleton.getLastWidth() - 8) * this.value / this.maxValue);
            //logUtil.put("Rect1: " + width + "," + 7, this, "paint");
            graphics.fillRect(4, 4 + myFont.DEFAULT_CHAR_HEIGHT, width, 7);
        }
        else
            if(this.height == myFont.DEFAULT_CHAR_HEIGHT + 2)
        {
                int stringWidth = graphics.getFont().stringWidth(this.getLabel());
                
                final int ADJUST_X = 26;
                //int width = (int) ((graphics.getClipWidth() - stringWidth - 8) * value / maxValue);
                int width = (int) ((this.displayInfoSingleton.getLastWidth() - stringWidth - ADJUST_X) * this.value / this.maxValue);

                graphics.fillRect(ADJUST_X + stringWidth, 4, width, graphics.getFont().getHeight() / 2);
        }
    }

    private int getCurrentInnerColor()
    {
        return (int) this.currentInnerColor;
    }
}