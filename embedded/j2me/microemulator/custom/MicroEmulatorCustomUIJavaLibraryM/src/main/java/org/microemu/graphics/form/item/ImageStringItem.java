/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.NullImage;

import org.allbinary.graphics.form.item.ABCustomItem;
import org.allbinary.image.opengles.OpenGLESImage;
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
        if (!(this.img == NullImage.NULL_IMAGE || this.img == OpenGLESImage.NULL_OPENGL_IMAGE))
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
