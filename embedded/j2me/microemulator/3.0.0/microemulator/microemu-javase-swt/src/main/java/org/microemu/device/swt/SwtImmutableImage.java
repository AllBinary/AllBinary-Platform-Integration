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
 */
package org.microemu.device.swt;

import javax.microedition.lcdui.PostLoadImageProcessor;
import org.allbinary.graphics.displayable.CanvasStrings;

import org.eclipse.swt.graphics.ImageData;

import org.microemu.app.ui.swt.SwtDeviceComponent;

public class SwtImmutableImage extends javax.microedition.lcdui.Image {

    private final String FONT_ATLAS = CanvasStrings.getInstance().FONT_ATLAS;

    public org.eclipse.swt.graphics.Image image = null;

    private int width;
    private int height;
    
    public SwtImmutableImage(final String name, final int width, final int height) {
        super(name, null);
        
        this.width = width;
        this.height = height;
    }

    public SwtImmutableImage(final String name, final PostLoadImageProcessor postLoadImageProcessor) {
        super(name, postLoadImageProcessor);
    }
    
    //private Transform transform = new Transform(null);
    public SwtImmutableImage(final String name, final org.eclipse.swt.graphics.Image image) {
        super(name, null);
        this.init(image);
    }

    public SwtImmutableImage(final SwtMutableImage image) {
        super(image.getName(), null);
        this.init(SwtDeviceComponent.createImage((org.eclipse.swt.graphics.Image) image.getImage()));
    }

    @Override
    public void init(final Object image2) {
        if(image2 == null) throw new RuntimeException();
        final org.eclipse.swt.graphics.Image image = (org.eclipse.swt.graphics.Image) image2;
        this.image = image;
        this.platformImage = new PostLoadPlatformImmutableImage(); //PostLoadPlatformImmutableImage.getInstance();
    }
    
    
//    public Transform getTransform() {
//        return transform;
//    }
//
//    public void setTransform(Transform transform) {
//        this.transform = transform;
//    }

    //org.eclipse.swt.graphics.Image
    public Object getImage() {
        return this.image;
    }

    public int getWidth() {
//        final int aWidth = this.platformImage.getWidth(this.image, this.width);
//        System.out.println(this.getName() + "2width: " + aWidth);
//        return aWidth;
          return this.platformImage.getSurfaceWidth(this.image, this.width);
    }

    public int getHeight() {
//        final int aHeight = this.platformImage.getHeight(this.image, this.height);
//        System.out.println(this.getName() + "2height: " + aHeight);
//        return aHeight;
          return this.platformImage.getSurfaceHeight(this.image, this.height);
    }
    
    private int depth;
    public int getDepth() {
        return depth;
    }
    
    public void getRGB(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
        this.platformImage.getRGB(argb, offset, scanlength, x, y, width, height, this);
    }
    
    public void getRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (x < 0 || y < 0 || x + width > getWidth() || y + height > getHeight()) {
            throw new IllegalArgumentException("Specified area exceeds bounds of image");
        }
        if ((scanlength < 0 ? -scanlength : scanlength) < width) {
            throw new IllegalArgumentException("abs value of scanlength is less than width");
        }
        if (argb == null) {
            throw new NullPointerException("null rgbData");
        }
        if (offset < 0 || offset + width > argb.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (scanlength < 0) {
            if (offset + scanlength * (height - 1) < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
        } else {
            if (offset + scanlength * (height - 1) + width > argb.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

//        System.out.println("TWB immutable id: " + img);
//        //final byte[] alphas = new byte[width];
        final ImageData imageData = this.image.getImageData();
        this.depth = imageData.depth;
        //System.out.println("TWB mutable id: " + img);
        final byte[] alphas = new byte[width * height];
        for (int i = 0; i < height; i++) {
            imageData.getPixels(x, y + i, scanlength, argb, offset + (i * width));
            imageData.getAlphas(x, y + i, scanlength, alphas, offset + (i * width));
        }

        //TWB - This hack gets the alpha values
        if(this.depth == 24) {
            final int size = argb.length;
            for (int i = 0; i < size; i++) {
                if ((alphas[i] & 0xFF) == 0) {
                    if(this.getName() == this.FONT_ATLAS) {
                        argb[i] = 0x00000000;  
                    } else {
                        argb[i] = (alphas[i] << 24) | argb[i];
                    }
                } else if (argb[i] == 0xd7d0d0) {
                    if(this.getName() == this.FONT_ATLAS) {
                        argb[i] = 0x00000000;  
                    } else {
                        argb[i] = (alphas[i] << 24) | argb[i];
                    }
                } else {
                    //for the font atlas with white text on transparent background.
                    //argb[i] = 0xFF000000 | argb[i];
                    argb[i] = (alphas[i] << 24) | argb[i];
                }
            }
        }

//        if (width > 130 && height > 130) {
//            final StringMaker stringMaker = new StringMaker();
//            for (int i = 0; i < size; i++) {
//                stringMaker.append(Integer.toHexString(argb[i])).append(CommonSeps.getInstance().SPACE);
//            }
//            System.out.println(stringMaker.toString());
//        }
        
    }

    public void setRGB2(final int[] argb, final int offset, final int scanlength, final int x, final int y, final int width, final int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (x < 0 || y < 0 || x + width > getWidth() || y + height > getHeight()) {
            throw new IllegalArgumentException("Specified area exceeds bounds of image");
        }
        if ((scanlength < 0 ? -scanlength : scanlength) < width) {
            throw new IllegalArgumentException("abs value of scanlength is less than width");
        }
        if (argb == null) {
            throw new NullPointerException("null rgbData");
        }
        if (offset < 0 || offset + width > argb.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (scanlength < 0) {
            if (offset + scanlength * (height - 1) < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
        } else {
            if (offset + scanlength * (height - 1) + width > argb.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        final ImageData imageData = this.image.getImageData();
        this.depth = imageData.depth;
        for (int i = 0; i < height; i++) {
            imageData.setPixels(x, y + i, width, argb, offset + i * scanlength);
        }
    }

    public static final int TYPE = 1;

    public int getType() {
        return TYPE;
    }
    
}
