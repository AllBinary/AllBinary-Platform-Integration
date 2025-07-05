/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.platform;

import org.microemu.device.DeviceDisplay;
import org.microemu.device.swt.SwtDeviceDisplay;

/**
 *
 * @author User
 */
public class PlatformDeviceDisplay {
    
    public static final DeviceDisplay INSTANCE = SwtDeviceDisplay.getInstance();
}
