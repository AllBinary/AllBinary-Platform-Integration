/*
 *  MicroEmulator
 *  Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2007-2007 Vlad Skarzhevskyy
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
 *  Contributor(s):
 *    3GLab
 *    
 *  @version $Id: MIDletBridge.java 2523 2012-01-09 13:59:48Z barteo@gmail.com $    
 */

package org.microemu;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;

import org.microemu.app.launcher.Launcher;

/**
 * 
 * Enables access to MIDlet and MIDletAccess by threadLocal
 *
 */
public class MIDletBridge {

	static MicroEmulator emulator = null;

	static ThreadLocal /*<MIDletContext>*/ threadMIDletContexts;
	
	static Map /*<MIDlet, MIDletContext>*/ midletContexts;

	static MIDlet currentMIDlet;

	public static void setMicroEmulator(MicroEmulator emulator) {
		MIDletBridge.emulator = emulator;
		
		threadMIDletContexts = new ThreadLocal();
		midletContexts = new WeakHashMap();
		currentMIDlet = null;
	}
	
	public static MicroEmulator getMicroEmulator() {
		return MIDletBridge.emulator;
	}
	
	public static void setThreadMIDletContext(MIDletContext midletContext) {
		MIDletBridge.threadMIDletContexts.set(midletContext);
	}
	
	public static void registerMIDletAccess(MIDletAccess accessor) {
		MIDletContext c = (MIDletContext)threadMIDletContexts.get();
		if (c == null) {
			//throw new Error("setThreadMIDletContext should be called");
			c = new MIDletContext();
			MIDletBridge.setThreadMIDletContext(c);
		}
		c.setMIDletAccess(accessor);
		MIDletBridge.registerMIDletContext(c);
	}
	
	public static void registerMIDletContext(MIDletContext midletContext) {
		MIDletBridge.midletContexts.put(midletContext.getMIDlet(), midletContext);
	}

	public static MIDletContext getMIDletContext(MIDlet midlet) {
		return (MIDletContext)MIDletBridge.midletContexts.get(midlet);
	}
	
	public static MIDletContext getMIDletContext() {
		MIDletContext c = (MIDletContext)threadMIDletContexts.get();
		if (c != null) {
			return c;
		}
		return MIDletBridge.getMIDletContext(currentMIDlet);
	}

	public static void setCurrentMIDlet(MIDlet midlet) {
		currentMIDlet = midlet;
	}

	public static MIDlet getCurrentMIDlet() {
		MIDletContext c = MIDletBridge.getMIDletContext();
		if (c == null) {
			return null;
		}
		return c.getMIDlet();
	}
	
	public static MIDletAccess getMIDletAccess() {
		MIDletContext c = MIDletBridge.getMIDletContext();
		if (c == null) {
			return null;
		}
		return c.getMIDletAccess();
	}

	public static MIDletAccess getMIDletAccess(MIDlet midlet) {
		return MIDletBridge.getMIDletContext(midlet).getMIDletAccess();
	}

	
	public static RecordStoreManager getRecordStoreManager() {
		return MIDletBridge.emulator.getRecordStoreManager();
	}

	public static String getAppProperty(String key) {
		return MIDletBridge.emulator.getAppProperty(key);
	}
	
	public static InputStream getResourceAsStream(Class origClass, String name) {
		return MIDletBridge.emulator.getResourceAsStream(origClass, name);
	}


	public static void notifyDestroyed() {
		MIDletContext midletContext = MIDletBridge.getMIDletContext();
		MIDletBridge.emulator.notifyDestroyed(midletContext);
		MIDletBridge.destroyMIDletContext(midletContext);
	}

	public static void destroyMIDletContext(MIDletContext midletContext) {
		if (midletContext == null) {
			return;
		}
		MIDletBridge.emulator.destroyMIDletContext(midletContext);
		if (MIDletBridge.midletContexts.containsValue(midletContext)) {
			for (Iterator i = MIDletBridge.midletContexts.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				if (entry.getValue() == midletContext) {
					MIDletBridge.midletContexts.remove(entry.getKey());
					break;
				}
			}
		}
	}
	
	public static int checkPermission(String permission) {
		return MIDletBridge.emulator.checkPermission(permission);
	}
	
	public static boolean platformRequest(String URL) throws ConnectionNotFoundException {
		return MIDletBridge.emulator.platformRequest(URL);
	}

	public static void clear() {
		
		currentMIDlet = null;
		
		// Preserve only Launcher Context
		for (Iterator i = MIDletBridge.midletContexts.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			MIDlet test = ((MIDletContext) entry.getValue()).getMIDlet();
			if (test instanceof Launcher) {
				MIDletBridge.midletContexts.clear();
				MIDletBridge.midletContexts.put(entry.getKey(), entry.getValue());
				return;
			}
		}
		// No Launcher found
		MIDletBridge.midletContexts.clear();
	}
	
	static Map /*<GameCanvas, GameCanvasKeyAccess>*/ gameCanvasAccesses = new WeakHashMap();	

	public static GameCanvasKeyAccess getGameCanvasKeyAccess(GameCanvas gameCanvas) {
		return (GameCanvasKeyAccess) MIDletBridge.gameCanvasAccesses.get(gameCanvas);
	}

	public static void registerGameCanvasKeyAccess(GameCanvas gameCanvas, GameCanvasKeyAccess access) {
		MIDletBridge.gameCanvasAccesses.put(gameCanvas, access);
	}



}
