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
 *  @version $Id: KeyCanvasPanel.java 1605 2008-02-25 21:07:14Z barteo $
 */
package org.microemu.midp.examples.simpledemo;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

public class KeyCanvasPanel extends BaseExamplesCanvas {

	static Hashtable actionNames = new Hashtable();
	
	static Hashtable shortNames = new Hashtable();
	
	int lastKeyCode = 0;

	int sameKeyCount = 0;
	
	int lastKeyRepeatedKeyCode = 0;
	
	int keyRepeatedCount = 0;
	
	long keyRepeatedTime = 0;
	
	long keyRepeatedInitialDellay = 0;
	
	String lastKeyEvent = null;
	
	Vector keysHistory = new Vector();
	
	Vector keysPressed = new Vector();
	
	boolean debug = true;
	
	static { 		
		KeyCanvasPanel.initActionNames();
	}
	
	public KeyCanvasPanel() {
		super("KeyCanvas");
	}

	protected void paint(Graphics g) {
		int width = getWidth();
        int height = getHeight();

		g.setGrayScale(255);
		g.fillRect(0, 0, width, height);
		
		g.setColor(0);
		int line = 0;
		writeln(g, line++, "Key Canvas - Press any key!");
		if (fullScreenMode) {
			writeln(g, line++, "Back - same key 3 times");
		}
		if (this.sameKeyCount > 0) {
			writeln(g, line++, "KeyCode: " + this.lastKeyCode); 
			writeln(g, line++, "As char: " +  (char) this.lastKeyCode);
			writeln(g, line++, "GameAction: " + gameActionName(getGameAction(this.lastKeyCode)));
			writeln(g, line++, "KeyName: " + getKeyName(this.lastKeyCode));
			StringBuffer pressed = new StringBuffer();
			for(Enumeration en = this.keysPressed.elements(); en.hasMoreElements(); ) {
			    pressed.append(en.nextElement());
			}
			writeln(g, line++, "Pressed: " + pressed.toString());
			writeln(g, line++, "Event: " + this.lastKeyEvent);
		}
		if (this.keysHistory.size() > 0) {
			writeln(g, line++, "- history -");
			for (int i = this.keysHistory.size() - 1; i >= 0; i--) {
				if (writeln(g, line++, (String) this.keysHistory.elementAt(i)) > height) {
					break;
				}
			}
		}
	}
	
	public String getKeyName(int keyCode) {
		try {
			return super.getKeyName(keyCode);
		} catch (IllegalArgumentException e) {
			return "not valid key code";
		}
	}
	
	public void keyPressed(int keyCode) {
		if (this.lastKeyCode == keyCode) {
			this.sameKeyCount ++;
			if ((fullScreenMode) && (this.sameKeyCount >= 3)) {
				setFullScreenMode(false);
				SimpleDemoMIDlet.showMenu();
			}
		} else {
			this.sameKeyCount = 1;
			this.logEvent(String.valueOf(keyCode) + " " + getKeyName(keyCode));
		}
		this.keyRepeatedTime = System.currentTimeMillis();
		this.lastKeyCode = keyCode;
		this.lastKeyEvent = "keyPressed";
		this.keysPressed.addElement(shortName(keyCode));
		if (this.debug) {
		    System.out.println(this.lastKeyEvent + " " + keyCode);
		}
		repaint();
	}
	
	public void keyReleased(int keyCode) {
		lastKeyEvent = "keyReleased";
		this.lastKeyCode = keyCode;
		if (this.debug) {
            System.out.println(this.lastKeyEvent + " " + keyCode);
        }
		this.keysPressed.removeElement(shortName(keyCode));
		this.keyRepeatedCount = 1;
        this.keyRepeatedTime = 0;
        this.keyRepeatedInitialDellay = 0;
		repaint();
	}

	public void keyRepeated(int keyCode) {
	    long keyRepeatedDellay = 0;
		if (this.lastKeyRepeatedKeyCode == keyCode) {
			this.keyRepeatedCount ++;
		} else {
			this.keyRepeatedCount = 1;
		}
		keyRepeatedDellay = System.currentTimeMillis() - this.keyRepeatedTime; 
        if (this.keyRepeatedInitialDellay == 0) {
            this.keyRepeatedInitialDellay = keyRepeatedDellay;
        }
        this.keyRepeatedTime = System.currentTimeMillis();
        
		this.lastKeyEvent = "keyRepeated (" + Utils.d00(this.keyRepeatedCount) + ")";
		if (keyRepeatedDellay != 0) {
		    this.lastKeyEvent += " " + this.keyRepeatedInitialDellay + "/" + keyRepeatedDellay + " ms";
		}
		this.lastKeyCode = keyCode;
		this.lastKeyRepeatedKeyCode = keyCode;
		if (this.debug) {
            System.out.println(this.lastKeyEvent + " " + keyCode);
        }
		repaint();
	}

	private void logEvent(String e) {
		StringBuffer sb = new StringBuffer();
        sb.append(Utils.when());
        sb.append("   ").append(e);
		this.keysHistory.addElement(sb.toString());
	}

	
	static String gameActionName(int gameAction) {
		return (String)KeyCanvasPanel.actionNames.get(new Integer(gameAction));
	}

	String shortName(int keyCode) {
	    int gameAction = getGameAction(keyCode);
        String n = (String)shortNames.get(new Integer(gameAction));
        if (n != null) {
            return n;
        } else {
            return ""+(char) keyCode;
        }
    }
	
	private static void actionName(int gameAction, String name, String shortName) {
		KeyCanvasPanel.actionNames.put(new Integer(gameAction), name);
		KeyCanvasPanel.shortNames.put(new Integer(gameAction), shortName);
	}
	
	private static void initActionNames() {
		KeyCanvasPanel.actionName(UP, "UP", "u");
		KeyCanvasPanel.actionName(DOWN, "DOWN", "d");
		KeyCanvasPanel.actionName(LEFT, "LEFT", "l");
		KeyCanvasPanel.actionName(RIGHT, "RIGHT", "r");
		KeyCanvasPanel.actionName(FIRE, "FIRE", "f");

		KeyCanvasPanel.actionName(GAME_A, "GAME_A", "a");
		KeyCanvasPanel.actionName(GAME_B, "GAME_B", "b");
		KeyCanvasPanel.actionName(GAME_C, "GAME_C", "c");
		KeyCanvasPanel.actionName(GAME_D, "GAME_D", "d");

		KeyCanvasPanel.actionName(KEY_NUM0, "KEY_NUM0", "0");
		KeyCanvasPanel.actionName(KEY_NUM1, "KEY_NUM1", "1");
		KeyCanvasPanel.actionName(KEY_NUM2, "KEY_NUM2", "2");
		KeyCanvasPanel.actionName(KEY_NUM3, "KEY_NUM3", "3");
		KeyCanvasPanel.actionName(KEY_NUM4, "KEY_NUM4", "4");
		KeyCanvasPanel.actionName(KEY_NUM5, "KEY_NUM5", "5");
		KeyCanvasPanel.actionName(KEY_NUM6, "KEY_NUM6", "6");
		KeyCanvasPanel.actionName(KEY_NUM7, "KEY_NUM7", "7");
		KeyCanvasPanel.actionName(KEY_NUM8, "KEY_NUM8", "8");
		KeyCanvasPanel.actionName(KEY_NUM9, "KEY_NUM9", "9");
		KeyCanvasPanel.actionName(KEY_STAR, "KEY_STAR", "*");
		KeyCanvasPanel.actionName(KEY_POUND, "KEY_POUND", "#");
	}

}
