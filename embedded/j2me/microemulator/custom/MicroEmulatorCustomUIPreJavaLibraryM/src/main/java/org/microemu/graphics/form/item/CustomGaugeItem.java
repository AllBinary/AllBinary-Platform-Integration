/*
 *  MicroEmulator
 *  Copyright (C) 2005 Andres Navarro
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 *
 *  Other Contributor(s):
 *    Travis Berthelot
 */
package org.microemu.graphics.form.item;


import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import org.allbinary.J2MEUtil;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.graphics.font.MyFontProcessor;
import org.allbinary.graphics.font.UpdateMyFontInterface;
import org.allbinary.graphics.font.UpdateMyFontProcessor;
import org.allbinary.graphics.threed.SWTJOGLProcessor;


public class CustomGaugeItem extends CustomItem 
    implements UpdateMyFontInterface
{
    //protected final LogUtil logUtil = LogUtil.getInstance();
    
    //private final int currentOuterColor = 0xff000000;
    private final long currentRed = 0xffff0000;
    private final long currentGreen = 0xff00ff00;
    private final long currentBlue = 0xff0000ff;
    private final long START_INNER_COLOR = this.currentRed;
    
    private final DisplayInfoSingleton displayInfoSingleton = 
            DisplayInfoSingleton.getInstance();
    
    private final MyFontProcessor updateMyFontProcessor = new UpdateMyFontProcessor(this);
    private MyFontProcessor myFontProcessor = this.updateMyFontProcessor;

    private long currentInnerColor = 0xffff0000;
    
    private int height = 30;

    private float value;
    private float maxValue;
    
    private int fontHeight = 0;
    private int offsetY = 0;
    
    public CustomGaugeItem(String label, int maxValue, int initialValue, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);

        this.setMaxValue(maxValue);
        this.setValue((float) initialValue);
    }

    @Override
    public void updateMeasurement(final Graphics graphics) {
        final Font font = graphics.getFont();
        this.fontHeight = font.getHeight();
        this.offsetY = SWTJOGLProcessor.getInstance().isJOGL() ? this.fontHeight / 4 : 0;
        this.myFontProcessor = MyFontProcessor.getInstance();
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
            this.setValue(this.getValue());
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

    public void paintXY(Graphics graphics, int xunused, int yunused)
    {
        this.myFontProcessor.process(graphics);
        
        graphics.setColor(this.getLabelStringComponent().getBackgroundBasicColor().intValue());
        //g.fillRect(0, 0, g.getClipWidth(), g.getClipHeight());
        //graphics.fillRect(0, 0, graphics.getClipWidth(), this.height);
        graphics.fillRect(0, 0, this.displayInfoSingleton.getLastWidth(), this.height);

        // Don't use WHITE.intValue() because of static load time
        graphics.setColor(this.getCurrentInnerColor());
        graphics.drawString(this.getLabel(), 4, offsetY, 0);

        if(this.height == 30)
        {
            //int width = (int) ((graphics.getClipWidth() - 8) * value / maxValue);
            final int width = (int) ((this.displayInfoSingleton.getLastWidth() - 8) * this.value / this.maxValue);
            //logUtil.put("Rect1: " + width + "," + 7, this, "paint");
            graphics.fillRect(4, 4 + this.fontHeight, width, 7);
        }
        else
            if(this.height == this.fontHeight + 2)
        {
                final int nominator = SWTJOGLProcessor.getInstance().isJOGL() ? 100 : 100;
                final int denominator = SWTJOGLProcessor.getInstance().isJOGL() ? 60 : 88;
                final int stringWidth = graphics.getFont().stringWidth(this.getLabel()) * nominator / denominator;
                
                //int width = (int) ((graphics.getClipWidth() - stringWidth - 8) * value / maxValue);
                final int width = (int) ((this.displayInfoSingleton.getLastWidth() - stringWidth) * this.value / this.maxValue);

                graphics.fillRect(stringWidth, 4, width, graphics.getFont().getHeight() / 2);
        }
    }

    private int getCurrentInnerColor()
    {
        return (int) this.currentInnerColor;
    }
}