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
 * Contributor(s):
 *   3GLab
 *   Andres Navarro
 */

package javax.microedition.lcdui;


import java.io.IOException;

import org.allbinary.logic.string.StringUtil;

import org.microemu.device.DeviceFactory;


public class Image
{
    protected PlatformImage platformImage = PreLoadPlatformImage.getInstance();
    
    private String name;
    private final PostLoadImageProcessor postLoadImageProcessor;
        
	public static Image createImage(final int width, final int height)
	{
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException();
		}
                //TWB - This breaks from the J2ME spec that does not allow alpha in mutable images.
                //I changed it so I can have mutable images with alpha so I won't need sprite resources
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(width, height, true, 0x00FFFFFF);
	}

	public static Image createImage(final String name) throws IOException
	{
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(name);
	}

	public static Image createImage(final Image source)
	{
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(source);
	}

	public static Image createImage(final byte[] imageData, final int imageOffset, final int imageLength)
	{
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(imageData, imageOffset, imageLength);
	}

        public static Image createImageLater(final String name, final int width, final int height) throws IOException {
            return DeviceFactory.getDevice().getDeviceDisplay().createImageLater(name, width, height);
        }
        
        protected Image() {
            this.name = StringUtil.getInstance().NULL_STRING;
            this.postLoadImageProcessor = PostLoadImageProcessor.NULL_POST_LOAD_IMAGE_PROCESSOR;
        }
        
        public Image(final String name, final PostLoadImageProcessor postLoadImageProcessor) {
            this.name = name;
            this.postLoadImageProcessor = postLoadImageProcessor;
        }
        
        public Image(final Image image) {
            throw new RuntimeException();
        }
        
	public Graphics getGraphics()
	{
		throw new IllegalStateException("Image is Immutable or OpenGLESImage");
	}
        
        public int getHeight()
	{
		return 0;
	}

	public int getWidth()
	{
		return 0;
	}

        public int getHeight2()
	{
		throw new RuntimeException();
	}

	public int getWidth2()
	{
		throw new RuntimeException();
	}
                
	public boolean isMutable()
	{
		return false;
	}
        
        public static final int TYPE = 0;
        public int getType() {
            return Image.TYPE;
        }
        
	// Andres Navarro
	// MIDP2 Methods

        public void getRGB(final int[] argb, final int offset, final int scanlenght, final int x, final int y, final int width, final int height) {
		// Implemented in Immutable and Mutable image
	}

        public void getRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
            
        }

        public void setRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
            
        }

        public int[] getData2() {
            return null;
        }
        
        public static Image createImage(final java.io.InputStream stream) throws IOException {
            return DeviceFactory.getDevice().getDeviceDisplay().createImage(stream);
        }
        
        public static Image createImage(final Image image, final int x, final int y, final int width, final int height, final int transform) {
            return DeviceFactory.getDevice().getDeviceDisplay().createImage(image, x, y, width, height, transform);
        }
        
        public static Image createRGBImage(final int[] rgb, final int width, final int height, final boolean processAlpha) {
            return DeviceFactory.getDevice().getDeviceDisplay().createRGBImage(rgb, width, height, processAlpha);
        }
	// Andres Navarro
        
    /**
     * @return the platformImage
     */
    public PlatformImage getPlatformImage() {
        return this.platformImage;
    }

    /**
     * @param platformImage the platformImage to set
     */
    public void setPlatformImage(final PlatformImage platformImage) {
        this.platformImage = platformImage;
    }

    public void init(final Object image2) {
        
    }
    
    public Object getImage() {
        return null;
    }

    public void setImage(final Object object) {
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public boolean isReady() {
        if(this.getImage() != null) {
            return true;
        }
        return false;
    }

    public boolean setReady() {
        throw new RuntimeException();
    }
    
}
