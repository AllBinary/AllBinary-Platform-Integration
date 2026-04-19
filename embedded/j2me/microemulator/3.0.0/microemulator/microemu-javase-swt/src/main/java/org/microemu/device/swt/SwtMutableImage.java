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

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;

import org.allbinary.string.CommonStrings;

import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.device.MutableImage;

public class SwtMutableImage extends MutableImage {

    //private static final String CLOSE = "SwtMutableImage:" + CommonStrings.getInstance().CLOSE;

    public org.eclipse.swt.graphics.Image image;

    private org.eclipse.swt.graphics.GC gc;

    public SwtMutableImage(final String name) {
        super(name, null);
    }

    public SwtMutableImage(final String name, final PostLoadImageProcessor postLoadImageProcessor) {
        super(name, postLoadImageProcessor);
    }
    
//    public SwtMutableImage(final String name, final int width, final int height) {
//
//        super(name, PostLoadImageProcessor.NULL_POST_LOAD_IMAGE_PROCESSOR);
//        
//        this.init(SwtDeviceComponent.createImage(width, height));
//    }
    
    //private Transform transform = new Transform(null);
    public SwtMutableImage(final int width, final int height) {
        //System.out.println("SwtMutableImage:SwtMutableImage: " + this);
        
        this.init(SwtDeviceComponent.createImage(width, height));
        
//        final Image canvas = SwtDeviceComponent.createImage(width, height);

//        final GC gc = new GC(canvas);
//
//        gc.setAntialias(SWT.ON);
//        gc.setAlpha(0);
//        gc.fillRectangle(0, 0, width, height);
//        gc.dispose();

//        final ImageData canvasData = canvas.getImageData();
        
//        canvasData.transparentPixel = canvasData.getPixel(0, 0);
//        canvasData.alpha = 1;
//        
//        canvasData.alphaData = new byte[width * height];
//
//        //32 bit
//        for (int idx = 0; idx < (width * height); idx++) {
//            int coord = (idx * 4) + 3;
//            canvasData.alphaData[idx] = canvasData.data[coord];
//        }

//        this.img = SwtDeviceComponent.createImage(canvasData);

//        canvas.dispose();
        
        //ForcedLogUtil.log("SwtMutableImage:SwtMutableImage: " + this.img, this);

    }

//    public SwtMutableImage(final String name, final org.eclipse.swt.graphics.Image image) {
//        super(name, PostLoadImageProcessor.NULL_POST_LOAD_IMAGE_PROCESSOR);
//        
//        this.init(image);
//    }
    
    public SwtMutableImage(final org.eclipse.swt.graphics.Image image) {
        //System.out.println("SwtMutableImage:SwtMutableImage2: " + this);

        this.init(image);
        
        //ForcedLogUtil.log("SwtMutableImage:SwtMutableImage: " + this.img + " from: " + img, this);
        
    }

    @Override
    public void init(final Object image2) {
        final org.eclipse.swt.graphics.Image image = (org.eclipse.swt.graphics.Image) image2;
        this.image = image;
        
        this.gc = new GC(this.image);
//		SwtDisplayGraphics displayGraphics = new SwtDisplayGraphics(new SwtGraphics(gc), this);
//		displayGraphics.setColor(0x00ffffff);
//		displayGraphics.fillRect(0, 0, width, height);
        
        this.platformImage = PostLoadPlatformMutableImage.getInstance();
    }
    
    public javax.microedition.lcdui.Graphics getGraphics() {
        return this.platformImage.getGraphics(this.gc, getWidth(), getHeight(), this);
    }

    public boolean isMutable() {
        return true;
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
        return image;
    }

    public int getWidth() {
        return this.platformImage.getWidth(this.image, 0);
    }

    public int getHeight() {
        return this.platformImage.getHeight(this.image, 0);
    }

    public int[] getData() {
        byte[] tmp = image.getImageData().data;
        int[] result = new int[tmp.length];

        for (int i = 0; i < tmp.length; i++) {
            result[i] = tmp[i];
        }

        return result;
    }

    
    private int depth;
    public int getDepth() {
        return depth;
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
        
        final ImageData imageData = this.image.getImageData();
        this.depth = imageData.depth;
        //System.out.println("TWB mutable id: " + img);
        final byte[] alphas = new byte[width * height];
        for (int i = 0; i < height; i++) {
            imageData.getPixels(x, y + i, scanlength, argb, offset + (i * width));
            imageData.getAlphas(x, y + i, scanlength, alphas, offset + (i * width));
            
        }

//        final StringBuilder stringMaker = new StringBuilder();
//        for (int i = 0; i < 10; i++) {
//            stringMaker.append(Integer.toHexString(argb[i])).append(CommonSeps.getInstance().SPACE);
//        }
//        System.out.println(stringMaker.toString());
        
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
    
    public org.eclipse.swt.graphics.GC getGc() {
        return gc;
    }

    protected void finalize() throws Throwable {
        if (!this.image.isDisposed()) {
            //System.out.println(CLOSE);
            this.image.dispose();
        }
    }

    public static final int TYPE = 2;

    public int getType() {
        return TYPE;
    }
    
}
