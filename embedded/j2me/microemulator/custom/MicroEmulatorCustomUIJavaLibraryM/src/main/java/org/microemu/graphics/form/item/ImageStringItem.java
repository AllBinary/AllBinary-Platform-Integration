/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.NullImage;

import org.allbinary.graphics.form.item.ABCustomItem;
import org.microemu.graphics.form.StringComponent;

import org.allbinary.graphics.color.BasicColor;

public class ImageStringItem extends ABCustomItem
{
    private Image img = NullImage.NULL_IMAGE;
    private final StringComponent stringComponent;

    public ImageStringItem(String label, Image img, String text, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, backgroundBasicColor, foregroundBasicColor);
        
        this.stringComponent = new StringComponent(text, Font.getDefaultFont(), backgroundBasicColor, foregroundBasicColor);
        this.setImage(img);
    }

    public Image getImage()
    {
        return this.img;
    }

    public void setImage(Image img)
    {
        this.img = img;
        if (this.img != null)
        {
            this.stringComponent.setWidthDecreaser(img.getWidth() + 2);
        }
    }

    public String getText()
    {
        return this.stringComponent.getText();
    }

    public void setText(String text)
    {
        this.stringComponent.setText(text);
    }

    @Override
    public int getHeight()
    {
        if (this.img != null && this.img.getHeight() > this.stringComponent.getHeight())
        {
            return this.img.getHeight();
        } else
        {
            return this.stringComponent.getHeight();
        }
    }

    public void invertPaint(boolean state)
    {
        this.stringComponent.invertPaint(state);
    }

    @Override
    public int paint(Graphics g)
    {
        if (this.stringComponent == null)
        {
            return 0;
        }

        if (this.img != null)
        {
            g.drawImage(this.img, 0, 0, Graphics.LEFT | Graphics.TOP);
            g.translate(this.img.getWidth() + 2, 0);
        }

        int y = this.stringComponent.paint(g);

        if (this.img != null)
        {
            g.translate(-this.img.getWidth() - 2, 0);
        }

        return y;
    }

    protected StringComponent getStringComponent()
    {
        return this.stringComponent;
    }

}
