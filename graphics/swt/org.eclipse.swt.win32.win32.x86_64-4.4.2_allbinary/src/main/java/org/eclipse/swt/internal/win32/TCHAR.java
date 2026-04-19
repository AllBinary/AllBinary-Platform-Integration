/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.win32;

import java.util.Arrays;

/**
 * This class implements the conversions between unicode characters
 * and the <em>platform supported</em> representation for characters.
 * <p>
 * Note that unicode characters which can not be found in the platform
 * encoding will be converted to an arbitrary platform specific character.
 * </p>
 *
 * @jniclass flags=no_gen
 */
public class TCHAR {
	int codePage;
	public char [] chars;
	public byte [] bytes;
	int byteCount;

public final static int sizeof = OS.IsUnicode ? 2 : 1;

public TCHAR (int codePage, int length) {
	this.codePage = codePage;
	if (OS.IsUnicode) {
		this.chars = new char [length];
	} else {
		this.bytes = new byte [this.byteCount = length];
	}
}

public TCHAR (int codePage, char ch, boolean terminate) {
	this (codePage, terminate ? new char [] {ch, '\0'} : new char [] {ch}, false);
}

public TCHAR (int codePage, char [] chars, boolean terminate) {
	this.codePage = codePage;
	int charCount = chars.length;
	if (OS.IsUnicode) {
		if (terminate) {
			if (charCount == 0 || (charCount > 0 && chars [charCount - 1] != 0)) {
				char [] newChars = new char [charCount + 1];
				System.arraycopy (chars, 0, newChars, 0, charCount);
				chars = newChars;
			}
		}
		this.chars = chars;
	} else {
		int cp = codePage != 0 ? codePage : OS.CP_ACP;
		this.bytes = new byte [this.byteCount = charCount * 2 + (terminate ? 1 : 0)];
		byteCount = OS.WideCharToMultiByte (cp, 0, chars, charCount, bytes, byteCount, null, null);
		if (terminate) byteCount++;
	}
}

public TCHAR (int codePage, String string, boolean terminate) {
	this (codePage, getChars (string, terminate), false);
}

static char [] getChars (String string, boolean terminate) {
	int length = string.length ();
	char [] chars = new char [length + (terminate ? 1 : 0)];
	string.getChars (0, length, chars, 0);
	return chars;
}

public void clear() {
	if (OS.IsUnicode) {
		Arrays.fill (this.chars, (char) 0);
	} else {
		Arrays.fill (this.bytes, (byte) 0);
	}
}

public int length () {
	if (OS.IsUnicode) {
		return chars.length;
	} else {
		return byteCount;
	}
}

public int strlen () {
	if (OS.IsUnicode) {
		for (int i=0; i<this.chars.length; i++) {
			if (this.chars [i] == '\0') return i;
		}
		return chars.length;
	} else {
		for (int i=0; i<this.byteCount; i++) {
			if (this.bytes [i] == '\0') return i;
		}
		return byteCount;
	}
}

public int tcharAt (int index) {
	if (OS.IsUnicode) {
		return chars [index];
	} else {
		int ch = this.bytes [index] & 0xFF;
		if (OS.IsDBCSLeadByte ((byte) ch)) {
			ch = ch << 8 | (this.bytes [index + 1] & 0xFF);
		}
		return ch;
	}
}

public String toString () {
	return toString (0, length ());
}

public String toString (int start, int length) {
	if (OS.IsUnicode) {
		return new String (this.chars, start, length);
	} else {
		byte [] bytes = this.bytes;
		if (start != 0) {
			bytes = new byte [length];
			System.arraycopy (this.bytes, start, bytes, 0, length);
		}
		char [] chars = new char [length];
		int cp = this.codePage != 0 ? this.codePage : OS.CP_ACP;
		int charCount = OS.MultiByteToWideChar (cp, OS.MB_PRECOMPOSED, bytes, length, chars, length);
		return new String (chars, 0, charCount);
	}
}

}

