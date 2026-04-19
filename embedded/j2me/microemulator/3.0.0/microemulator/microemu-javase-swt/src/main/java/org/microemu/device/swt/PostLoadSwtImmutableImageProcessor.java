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
public class PostLoadSwtImmutableImageProcessor extends PostLoadImageProcessor {
    
    private final SwtImmutableImage originalImmutableImage;
    
    public PostLoadSwtImmutableImageProcessor(final SwtImmutableImage originalImmutableImage) {
        this.originalImmutableImage = originalImmutableImage;
    }
    
    public void process(final Image image) {
        
        final int width = originalImmutableImage.getWidth();
        final int height = this.originalImmutableImage.getHeight();
        final ImageData imageData = this.originalImmutableImage.image.getImageData().scaledTo(width, height);
        ((SwtImmutableImage) image).init(SwtDeviceComponent.createImage(imageData));
        
    }
    
}
