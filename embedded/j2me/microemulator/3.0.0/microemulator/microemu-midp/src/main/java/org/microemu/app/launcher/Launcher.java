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

package org.microemu.app.launcher;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.NullImage;
import javax.microedition.midlet.MIDlet;

import org.microemu.MIDletEntry;
import org.microemu.app.CommonInterface;

public class Launcher extends MIDlet implements CommandListener {

	protected static final Command CMD_LAUNCH = new Command("Start", Command.ITEM, 0);;

	protected static final String NOMIDLETS = "[no midlets]";

	protected CommonInterface common;

	protected List menuList;

	protected static Vector midletEntries = new Vector();

	public Launcher(CommonInterface common) {
		this.common = common;
	}

	public static void addMIDletEntry(MIDletEntry entry) {
		Launcher.midletEntries.addElement(entry);
	}

	public static void removeMIDletEntries() {
		Launcher.midletEntries.removeAllElements();
	}

	public MIDletEntry getSelectedMidletEntry() {
		if (this.menuList != null) {
			int idx = this.menuList.getSelectedIndex();
			if (!this.menuList.getString(idx).equals(Launcher.NOMIDLETS)) {
				return (MIDletEntry) Launcher.midletEntries.elementAt(idx);
			}
		}

		return null;
	}

	public void destroyApp(boolean unconditional) {
	}

	public void pauseApp() {
	}

	public void startApp() {
		menuList = new List("Launcher", List.IMPLICIT);
		this.menuList.addCommand(CMD_LAUNCH);
		this.menuList.setCommandListener(this);

		if (Launcher.midletEntries.size() == 0) {
			this.menuList.append(NOMIDLETS, NullImage.NULL_IMAGE);
		} else {
			for (int i = 0; i < midletEntries.size(); i++) {
				this.menuList.append(((MIDletEntry) midletEntries.elementAt(i)).getName(), null);
			}
		}

		Display.getDisplay(this).setCurrent(this.menuList);
	}

	public void commandAction(Command c, Displayable d) {
		if (d == this.menuList) {
			if (c == List.SELECT_COMMAND || c == Launcher.CMD_LAUNCH) {
				MIDletEntry entry = this.getSelectedMidletEntry();
				if (entry != null) {
					this.common.initMIDlet(true, entry);
				}
			}
		}
	}

}
