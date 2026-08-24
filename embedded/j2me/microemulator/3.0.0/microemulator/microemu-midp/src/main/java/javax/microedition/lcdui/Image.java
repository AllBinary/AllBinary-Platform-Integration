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

import jsinterop.annotations.JsType;

import java.io.IOException;

import org.allbinary.logic.string.StringUtil;

import org.microemu.device.DeviceFactory;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
public class Image
{
    @JsProperty
    protected PlatformImage platformImage = PreLoadPlatformImage.getInstance();
    
    private String name;
    private final PostLoadImageProcessor postLoadImageProcessor;
        
	@JsMethod
	public static Image createImage(final int width, final int height)
	{
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException();
		}
                //TWB - This breaks from the J2ME spec that does not allow alpha in mutable images.
                //I changed it so I can have mutable images with alpha so I won't need sprite resources
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(width, height, true, 0x00FFFFFF);
	}

	@JsMethod
	public static Image createImage(final String name) throws IOException
	{
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(name);
	}

	@JsMethod
	public static Image createImage(final Image source)
	{
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(source);
	}

	@JsMethod
	public static Image createImage(final byte[] imageData, final int imageOffset, final int imageLength)
	{
		return DeviceFactory.getDevice().getDeviceDisplay().createImage(imageData, imageOffset, imageLength);
	}

        @JsMethod
        public static Image createImageLater(final String name, final int width, final int height) throws IOException {
            return DeviceFactory.getDevice().getDeviceDisplay().createImageLater(name, width, height);
        }
        
        @JsConstructor
        protected Image() {
            this.name = StringUtil.getInstance().NULL_STRING;
            this.postLoadImageProcessor = PostLoadImageProcessor.NULL_POST_LOAD_IMAGE_PROCESSOR;
        }
        
        @JsConstructor
        public Image(final String name, final PostLoadImageProcessor postLoadImageProcessor) {
            this.name = name;
            this.postLoadImageProcessor = postLoadImageProcessor;
        }
        
        @JsConstructor
        public Image(final Image image) {
            throw new RuntimeException();
        }
        
	@JsMethod
	public Graphics getGraphics()
	{
		throw new IllegalStateException("Image is Immutable or OpenGLESImage");
	}
        
        @JsMethod
        public int getHeight()
	{
		return 0;
	}

	@JsMethod
	public int getWidth()
	{
		return 0;
	}

        @JsMethod
        public int getHeight2()
	{
		throw new RuntimeException();
	}

	@JsMethod
	public int getWidth2()
	{
		throw new RuntimeException();
	}
                
	@JsMethod
	public boolean isMutable()
	{
		return false;
	}
        
        @JsProperty
        public static final int TYPE = 0;
        @JsMethod
        public int getType() {
            return Image.TYPE;
        }
        
	// Andres Navarro
	// MIDP2 Methods

        @JsMethod
        public void getRGB(final int[] argb, final int offset, final int scanlenght, final int x, final int y, final int width, final int height) {
		// Implemented in Immutable and Mutable image
	}

        @JsMethod
        public void getRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
            
        }

        @JsMethod
        public void setRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
            
        }

        @JsMethod
        public int[] getData2() {
            return null;
        }
        
        @JsMethod
        public static Image createImage(final java.io.InputStream stream) throws IOException {
            return DeviceFactory.getDevice().getDeviceDisplay().createImage(stream);
        }
        
        @JsMethod
        public static Image createImage(final Image image, final int x, final int y, final int width, final int height, final int transform) {
            return DeviceFactory.getDevice().getDeviceDisplay().createImage(image, x, y, width, height, transform);
        }
        
        @JsMethod
        public static Image createRGBImage(final int[] rgb, final int width, final int height, final boolean processAlpha) {
            return DeviceFactory.getDevice().getDeviceDisplay().createRGBImage(rgb, width, height, processAlpha);
        }
	// Andres Navarro
        
    /**
     * @return the platformImage
     */
    @JsMethod
    public PlatformImage getPlatformImage() {
        return this.platformImage;
    }

    /**
     * @param platformImage the platformImage to set
     */
    @JsMethod
    public void setPlatformImage(final PlatformImage platformImage) {
        this.platformImage = platformImage;
    }

    @JsMethod
    public void init(final Object image2) {
        
    }
    
    @JsMethod
    public Object getImage() {
        return null;
    }

    @JsMethod
    public void setImage(final Object object) {
        
    }

    /**
     * @return the name
     */
    @JsMethod
    public String getName() {
        return this.name;
    }

    @JsMethod
    public void setName(final String name) {
        this.name = name;
    }
    
    @JsMethod
    public boolean isReady() {
        if(this.getImage() != null) {
            return true;
        }
        return false;
    }

    @JsMethod
    public boolean setReady() {
        throw new RuntimeException();
    }
    
}
