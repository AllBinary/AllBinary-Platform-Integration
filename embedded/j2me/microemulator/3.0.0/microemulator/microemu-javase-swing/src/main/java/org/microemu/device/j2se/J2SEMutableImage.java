/*
 * MicroEmulator
 * Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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
 *  Contributor(s):
 *    Andres Navarro
 */

package org.microemu.device.j2se;

import java.awt.image.PixelGrabber;
import javax.microedition.lcdui.PostLoadImageProcessor;

import org.microemu.device.MutableImage;
import org.microemu.log.Logger;


public class J2SEMutableImage extends MutableImage
{
	private J2SEGraphicsSurface graphicsSurface;
	private PixelGrabber grabber = null;
	private int[] pixels;

        public J2SEMutableImage(final String name) {
            super(name, null);
        }

        public J2SEMutableImage(final String name, final PostLoadImageProcessor postLoadImageProcessor) {
            super(name, postLoadImageProcessor);
        }
        
	public J2SEMutableImage(final int width, final int height, final boolean withAlpha, final int fillColor)
	{
		this.init(width, height, withAlpha, fillColor);
	}

	public javax.microedition.lcdui.Graphics getGraphics()
	{
            return this.platformImage.getGraphics(this.graphicsSurface, getWidth(), getHeight(), this);
	}

        public void init(final int width, final int height, final boolean withAlpha, final int fillColor) {
            this.graphicsSurface = new J2SEGraphicsSurface(width, height, withAlpha, fillColor);
            this.platformImage = PostLoadPlatformMutableImage.getInstance();
        }

	public boolean isMutable()
	{
		return true;
	}

        //java.awt.Image
	public Object getImage()
	{
            return this.platformImage.getImage(this.graphicsSurface);
	}
        
	public int getHeight()
	{
            return this.platformImage.getHeight(this.graphicsSurface, 0);
	}

	public int getWidth()
	{
            return this.platformImage.getWidth(this.graphicsSurface, 0);
	}

        public int[] getData() {
            return this.platformImage.getData(this);
        }

	public int[] getData2()
	{
		if (this.grabber == null) {
                        final int width = this.getWidth();
                        final int height = this.getHeight();
			this.pixels = new int[width * height];
			this.grabber = new PixelGrabber(this.graphicsSurface.getImage(), 0, 0, width, height, pixels, 0, width);
		}

		try {
			this.grabber.grabPixels();
		} catch (InterruptedException e) {
			Logger.error(e);
		}

		return pixels;
	}

        public void getRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
            this.platformImage.getRGB(argb, offset, scanlength, x, y, width, height, this);
        }
        
    public void getRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {

        if (width <= 0 || height <= 0)
            return;
        if (x < 0 || y < 0 || x + width > getWidth() || y + height > getHeight())
            throw new IllegalArgumentException("Specified area exceeds bounds of image");
        if ((scanlength < 0? -scanlength:scanlength) < width)
            throw new IllegalArgumentException("abs value of scanlength is less than width");
        if (argb == null)
            throw new NullPointerException("null rgbData");
        if (offset < 0 || offset + width > argb.length)
            throw new ArrayIndexOutOfBoundsException();
        if (scanlength < 0) {
            if (offset + scanlength*(height-1) < 0)
                throw new ArrayIndexOutOfBoundsException();
        } else {
            if (offset + scanlength*(height-1) + width > argb.length)
                throw new ArrayIndexOutOfBoundsException();
        }

        try {
            (new PixelGrabber(this.graphicsSurface.getImage(), x, y, width, height, argb, offset, scanlength)).grabPixels();
        } catch (InterruptedException e) {
            Logger.error(e);
        }
    }

}
