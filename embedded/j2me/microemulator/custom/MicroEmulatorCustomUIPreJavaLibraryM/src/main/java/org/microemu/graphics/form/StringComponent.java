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
package org.microemu.graphics.form;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.logic.string.StringUtil;

public class StringComponent
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    private final StringComponentProperties paintComponentProperties = new StringComponentProperties();
    
    private Font font;
    
    private StringComponentProperties stringComponentProperties = new StringComponentProperties();
    private StringComponentProperties updatingComponentProperties = new StringComponentProperties();
    private StringComponentProperties tempComponentProperties = this.updatingComponentProperties;

    // 0xFFe07718;

    public StringComponent(String text, final Font font, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        this.font = font;
        
        this.setBackgroundBasicColor(backgroundBasicColor);
        this.setForegroundBasicColor(foregroundBasicColor);

        this.stringComponentProperties.width = -1;
        this.stringComponentProperties.widthDecreaser = 0;
        this.setText(text);
    }

    public int getCharHeight()
    {
        return this.font.getHeight();
    }

    public int getCharPositionX(int num)
    {
        //synchronize (this)
        //{
            if (this.stringComponentProperties.numOfBreaks == -1)
            {
                this.updateBreaks();
            }

            int i, prevIndex = 0;

            final int size = this.stringComponentProperties.numOfBreaks;
            for (i = 0; i < size; i++)
            {
                if (num < this.stringComponentProperties.breaks[i])
                {
                    break;
                }
                prevIndex = this.stringComponentProperties.breaks[i];
            }

            if (!this.stringComponentProperties.hasNotChanged[num]) {
                this.stringComponentProperties.lastWidth[num] = this.font.substringWidth(this.stringComponentProperties.text, prevIndex, num - prevIndex);
                this.stringComponentProperties.hasNotChanged[num] = true;
            }

            return this.stringComponentProperties.lastWidth[num];
        //}
    }

    public int getCharPositionY(int num)
    {
        int y = 0;
        //synchronize (this)
        //{
            if (this.stringComponentProperties.numOfBreaks == -1)
            {
                this.updateBreaks();
            }

            for (int i = 0; i < this.stringComponentProperties.numOfBreaks; i++)
            {
                if (num < this.stringComponentProperties.breaks[i])
                {
                    break;
                }
                y += this.font.getHeight();
            }
        //}

        return y;
    }

    public int getHeight()
    {
        int height;
        //synchronize (this)
        //{
            if (this.stringComponentProperties.numOfBreaks == -1)
            {
                this.updateBreaks();
            }

            if (this.stringComponentProperties.text == null)
            {
                return 0;
            }

            if (this.stringComponentProperties.numOfBreaks == 0)
            {
                return this.font.getHeight();
            }

            height = this.stringComponentProperties.numOfBreaks * this.font.getHeight();

            if (this.stringComponentProperties.breaks[this.stringComponentProperties.numOfBreaks - 1] == this.stringComponentProperties.text.length() - 1
                    && this.stringComponentProperties.text.charAt(this.stringComponentProperties.text.length() - 1) == '\n')
            {
            }
            else
            {
                height += this.font.getHeight();
            }
        //}

        return height;
    }

    public String getText()
    {
        return this.stringComponentProperties.text;
    }

    public void invertPaint(boolean state)
    {
        //synchronize (this)
        //{
            this.tempComponentProperties = this.stringComponentProperties;
            this.updatingComponentProperties.copy(this.stringComponentProperties);
            this.updatingComponentProperties.invertPaint = state;
            this.stringComponentProperties = this.updatingComponentProperties;
            this.updatingComponentProperties = this.tempComponentProperties;            
        //}
    }

    public int paint(Graphics g)
    {
        this.paintComponentProperties.copy(this.stringComponentProperties);

        if (this.paintComponentProperties.text == null)
        {
            return 0;
        }
        
        int y;
        //synchronize (this)
        //{
            if (this.paintComponentProperties.numOfBreaks == -1)
            {
                this.updateBreaks();
            }

            int prevIndex = 0;
            y = 0;

            final int size = this.paintComponentProperties.numOfBreaks;
            for (int i = 0; i < size; i++)
            {
                if (this.paintComponentProperties.invertPaint)
                {
                    g.setColor(this.getBackgroundBasicColor().intValue());
                }
                else
                {
                    g.setColor(this.getForegroundBasicColor().intValue());
                }
                g.fillRect(0, y, this.paintComponentProperties.width, this.font.getHeight());
                if (this.paintComponentProperties.invertPaint)
                {
                    g.setColor(this.getForegroundBasicColor().intValue());
                }
                else
                {
                    g.setColor(this.getBackgroundBasicColor().intValue());
                }
                // logUtil.put("text 1:" + text +
                // " commonLabels.INDEX_LABEL + prevIndex, this, "paint");
                g.drawSubstring(this.paintComponentProperties.text, prevIndex, this.paintComponentProperties.breaks[i] - prevIndex, 0, y, 0);
                prevIndex = this.paintComponentProperties.breaks[i];
                y += this.font.getHeight();
            }
            if (prevIndex != this.paintComponentProperties.text.length())
            {
                if (this.paintComponentProperties.invertPaint)
                {
                    g.setColor(this.getBackgroundBasicColor().intValue());
                }
                else
                {
                    // g.setColor(0xFFe07718);
                    g.setColor(this.getForegroundBasicColor().intValue());
                }
                g.fillRect(0, y, this.paintComponentProperties.width, this.font.getHeight());
                if (this.paintComponentProperties.invertPaint)
                {
                    g.setColor(this.getForegroundBasicColor().intValue());
                }
                else
                {
                    g.setColor(this.getBackgroundBasicColor().intValue());
                }
                // logUtil.put("text 2:" + text +
                // " commonLabels.INDEX_LABEL + prevIndex, this, "paint");
                // f.getHeight() +
                g.drawSubstring(this.paintComponentProperties.text, prevIndex, this.paintComponentProperties.text.length() - prevIndex, 0, y, 0);
                y += this.font.getHeight();
            }
        //}

        return y;
    }

    public void setText(String text)
    {
        //synchronize (this)
        //{
            this.tempComponentProperties = this.stringComponentProperties;
            this.updatingComponentProperties.copy(this.stringComponentProperties);
            this.updatingComponentProperties.text = text;
            if(text == null) {
                this.updatingComponentProperties.hasNotChanged = StringComponentProperties.HAS_NOT_CHANGED_ARRAY;
                this.updatingComponentProperties.lastWidth = StringComponentProperties.LAST_WIDTH_ARRAY;
            } else {
                final int size = text.length();
                this.updatingComponentProperties.hasNotChanged = new boolean[size + 1];
                this.updatingComponentProperties.lastWidth = new int[size + 1];
            }
            this.updatingComponentProperties.numOfBreaks = -1;
            this.stringComponentProperties = this.updatingComponentProperties;
            this.updatingComponentProperties = this.tempComponentProperties;
        
        //}
    }

    public void setWidthDecreaser(int widthDecreaser)
    {
        //synchronize (this)
        //{
            this.tempComponentProperties = this.stringComponentProperties;
            this.updatingComponentProperties.copy(this.stringComponentProperties);
            this.updatingComponentProperties.widthDecreaser = widthDecreaser;
            this.updatingComponentProperties.numOfBreaks = -1;
            this.stringComponentProperties = this.updatingComponentProperties;
            this.updatingComponentProperties = this.tempComponentProperties;        
        //}
    }

    private void insertBreak(int pos)
    {
        int i;

        final int size = this.stringComponentProperties.numOfBreaks;
        for (i = 0; i < size; i++)
        {
            if (pos < this.stringComponentProperties.breaks[i])
            {
                break;
            }
        }
        int[] breaks = this.stringComponentProperties.breaks;
        if (this.stringComponentProperties.numOfBreaks + 1 == breaks.length)
        {
            int newbreaks[] = new int[breaks.length + 4];
            System.arraycopy(breaks, 0, newbreaks, 0, this.stringComponentProperties.numOfBreaks);
            this.stringComponentProperties.breaks = newbreaks;
        }
        System.arraycopy(breaks, i, breaks, i + 1, this.stringComponentProperties.numOfBreaks - i);
        this.stringComponentProperties.breaks[i] = pos;
        this.stringComponentProperties.numOfBreaks++;
    }

    private void updateBreaks()
    {
        if (this.stringComponentProperties.text == null)
        {
            return;
        }

        // TODO use Displayable width
        this.stringComponentProperties.width = DisplayInfoSingleton.getInstance().getLastWidth() - this.stringComponentProperties.widthDecreaser;

        int prevIndex = 0;
        int canBreak = 0;
        this.stringComponentProperties.numOfBreaks = 0;

        final int size = this.stringComponentProperties.text.length();
        int i = 0;
        while(i < size)
        {
            if (this.stringComponentProperties.text.charAt(i) == ' ')
            {
                canBreak = i + 1;
            }
            if (this.stringComponentProperties.text.charAt(i) == '\n')
            {
                this.insertBreak(i);
                canBreak = 0;
                prevIndex = i + 1;
                continue;
            }
            if (this.font.substringWidth(this.stringComponentProperties.text, prevIndex, i - prevIndex + 1) > this.stringComponentProperties.width)
            {
                if (canBreak != 0)
                {
                    this.insertBreak(canBreak);
                    i = canBreak;
                    prevIndex = i;
                }
                else
                {
                    this.insertBreak(i);
                    prevIndex = i + 1;
                }
                canBreak = 0;
            }
            i++;
        }
    }

    public Font getFont() {
        return this.font;
    }
    
    public BasicColor getBackgroundBasicColor()
    {
        return this.stringComponentProperties.backgroundBasicColor;
    }

    public BasicColor getForegroundBasicColor()
    {
        return this.stringComponentProperties.foregroundBasicColor;
    }

    public void setBackgroundBasicColor(BasicColor backgroundBasicColor)
    {
        this.stringComponentProperties.backgroundBasicColor = backgroundBasicColor;
    }

    public void setForegroundBasicColor(BasicColor foregroundBasicColor)
    {
        this.stringComponentProperties.foregroundBasicColor = foregroundBasicColor;
    }
}