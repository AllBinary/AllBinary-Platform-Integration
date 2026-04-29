/*
 *  MicroEmulator
 *  Copyright (C) 2006 Bartek Teodorczyk <barteo@barteo.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *  
 *  Modification: Hack For Android by Travis Berthelot 11/12/07 - 01/06/07
 */
package org.microemu.device.swt;

import java.io.InputStream;

import javax.microedition.io.ConnectionNotFoundException;
import org.microemu.DisplayComponent;

import org.microemu.device.DeviceDisplay;
import org.microemu.device.EmulatorContext;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtFontManager;
import org.microemu.device.swt.SwtFontManager;
import org.microemu.device.swt.SwtInputMethod;
import org.microemu.device.swt.SwtInputMethod;

public class SwtEmulatorContext implements EmulatorContext {

    private final static SwtEmulatorContext androidEmulatorContext = new SwtEmulatorContext();

    private final InputMethod inputMethod;

    private final FontManager fontManager;

    private SwtEmulatorContext() {

        inputMethod = new SwtInputMethod();

        fontManager = new SwtFontManager();

    }

    public static SwtEmulatorContext getInstance() {
        return SwtEmulatorContext.androidEmulatorContext;
    }

    public DeviceDisplay getDeviceDisplay() {
        return SwtDeviceDisplay.getInstance();
    }

    public InputMethod getDeviceInputMethod() {
        return this.inputMethod;
    }

    public FontManager getDeviceFontManager() {
        return this.fontManager;
    }

    public DisplayComponent getDisplayComponent() {
        throw new RuntimeException();
    }
    
    public InputStream getResourceAsStream(Class origClass, String name) {
        throw new RuntimeException();
    }
    
    public boolean platformRequest(final String URL) throws ConnectionNotFoundException {
        throw new RuntimeException();
    }
}
