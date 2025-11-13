/**
 *  MicroEmulator
 *  Copyright (C) 2008 Bartek Teodorczyk <barteo@barteo.net>
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
 *  @version $Id: AndroidDevice.java 2236 2009-12-07 09:49:31Z barteo@gmail.com $
 */
package org.microemu.android.device;

import android.content.ActivityNotFoundException;

import java.io.InputStream;

import javax.microedition.io.ConnectionNotFoundException;

import org.microemu.DisplayComponent;
import org.microemu.device.EmulatorContext;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;

public class AndroidEmulatorContext implements EmulatorContext {

    private final static AndroidEmulatorContext androidEmulatorContext = new AndroidEmulatorContext();

    private final InputMethod inputMethod = AndroidInputMethodFactory.getInstance().ANDROID_INPUT_METHOD;

    private final FontManager fontManager;

    public AndroidEmulatorContext() {

        throw new RuntimeException();
        //fontManager = (FontManager) new AndroidFontManager();

    }

    public static AndroidEmulatorContext getInstance() {
        return androidEmulatorContext;
    }

    @Override
    public DeviceDisplay getDeviceDisplay() {
        throw new RuntimeException();
        //return AndroidDeviceDisplay.getInstance();
    }

    @Override
    public InputMethod getDeviceInputMethod() {
        return inputMethod;
    }

    @Override
    public FontManager getDeviceFontManager() {
        return fontManager;
    }

    public DisplayComponent getDisplayComponent() {
        // TODO consider removal of EmulatorContext.getDisplayComponent()
        System.out.println("MicroEmulator.emulatorContext::getDisplayComponent()");
        return null;
    }

    public InputStream getResourceAsStream(Class origClass, String name) {
        throw new RuntimeException();
//                try {
//                    if (name.startsWith("/")) {
//                        return MicroEmulatorActivity.this.getAssets().open(name.substring(1));
//                    } else {
//                        Package p = origClass.getPackage();
//                        if (p == null) {
//                            return MicroEmulatorActivity.this.getAssets().open(name);
//                        } else {
//                        	String folder = origClass.getPackage().getName().replace('.', '/');
//                            return MicroEmulatorActivity.this.getAssets().open(folder + "/" + name);
//                        }
//                    }
//                } catch (IOException e) {
//                    Logger.debug(e);
//                    return null;
//                }
    }

    public boolean platformRequest(String url) throws ConnectionNotFoundException {
        try {
            throw new RuntimeException();
            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
            throw new ConnectionNotFoundException();
        }

        //return true;
    }

}
