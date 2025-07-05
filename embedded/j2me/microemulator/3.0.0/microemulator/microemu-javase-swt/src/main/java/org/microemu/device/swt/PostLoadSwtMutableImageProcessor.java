/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.PostLoadImageProcessor;

import org.eclipse.swt.graphics.ImageData;

import org.microemu.app.ui.swt.SwtDeviceComponent;

/**
 *
 * @author User
 */
public class PostLoadSwtMutableImageProcessor extends PostLoadImageProcessor {
    
    private final SwtMutableImage originalMutableImage;
    
    public PostLoadSwtMutableImageProcessor(final SwtMutableImage originalMutableImage) {
        this.originalMutableImage = originalMutableImage;
    }
    
    public void process(final Image image) {
        
        final int width = originalMutableImage.getWidth();
        final int height = originalMutableImage.getHeight();
        final ImageData imageData = originalMutableImage.image.getImageData().scaledTo(width, height);
        ((SwtMutableImage) image).init(SwtDeviceComponent.createImage(imageData));
        
    }
    
}
