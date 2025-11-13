/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2025
 * Created By: Travis Berthelot
 */
package org.microemu.android.device;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 *
 * @author User
 */
public interface AndroidImageInterface {
    
    Bitmap getBitmap();
    Canvas getCanvas();
    
}
