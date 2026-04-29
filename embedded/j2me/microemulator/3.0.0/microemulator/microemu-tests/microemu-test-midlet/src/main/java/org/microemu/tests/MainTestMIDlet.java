/**
 *  MicroEmulator
 *  Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2006-2007 Vlad Skarzhevskyy
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
 *  @version $Id: MainTestMIDlet.java 1605 2008-02-25 21:07:14Z barteo $
 */
package org.microemu.tests;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class MainTestMIDlet extends MIDlet implements CommandListener, MIDletUnderTests {

	// static final Command exitCommand = MIDletUnderTests.exitCommand;

	List menuList = null;

	Vector testPanels;

	static {
		System.out.println("MainTestMIDlet static init");
		ThreadTestsForm.onMIDletInit();
	}

	public MainTestMIDlet() {

	}

	protected void startApp() throws MIDletStateChangeException {
		Manager.midletInstance = this;

		if (this.menuList == null) {
			this.testPanels = new Vector();
			this.testPanels.addElement(new ItemsOnForm());
			this.testPanels.addElement(new ErrorHandlingForm());
			this.testPanels.addElement(new ErrorHandlingCanvas());
			this.testPanels.addElement(new RecordStoreForm());
			this.testPanels.addElement(new ThreadTestsForm());
			if (OverrideNewJSRCanvas.enabled) {
				this.testPanels.addElement(new OverrideNewJSRCanvas());
			}
			if (OverrideNewJSR2Canvas.enabled) {
				this.testPanels.addElement(new OverrideNewJSR2Canvas());
			}
			if (OverrideNewJSR2Canvas.enabled) {
				this.testPanels.addElement(new OverrideNewJSR2Canvas());
			}
			if (PreporcessorTestCanvas.enabled) {
				this.testPanels.addElement(new PreporcessorTestCanvas());
			}

			this.menuList = new List("Manual Tests", List.IMPLICIT);

			for (Enumeration iter = this.testPanels.elements(); iter.hasMoreElements();) {
				this.menuList.append(((Displayable) iter.nextElement()).getTitle(), null);
			}
			this.menuList.addCommand(exitCommand);
			this.menuList.setCommandListener(this);
		}
		this.setCurrentDisplayable(this.menuList);
	}

	public void commandAction(Command c, Displayable d) {
		if (d == this.menuList) {
			if (c == List.SELECT_COMMAND) {
				this.setCurrentDisplayable((Displayable) this.testPanels.elementAt(this.menuList.getSelectedIndex()));
			} else if (c == exitCommand) {
				try {
					this.destroyApp(true);
				} catch (MIDletStateChangeException e) {
				}
				notifyDestroyed();
			}
		}
	}

	public void showMainPage() {
		this.setCurrentDisplayable(menuList);
	}

	public void setCurrentDisplayable(Displayable nextDisplayable) {
		Display display = Display.getDisplay(this);
		// Displayable current = display.getCurrent();
		display.setCurrent(nextDisplayable);
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

}
