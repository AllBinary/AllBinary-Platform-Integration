/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.custom;

import org.eclipse.swt.*;

/**
 * Instances of this class represent bullets in the <code>StyledText</code>.
 * <p>
 * The hashCode() method in this class uses the values of the public
 * fields to compute the hash value. When storing instances of the
 * class in hashed collections, do not modify these fields after the
 * object has been inserted.  
 * </p>
 * <p>
 * Application code does <em>not</em> need to explicitly release the
 * resources managed by each instance when those instances are no longer
 * required, and thus no <code>dispose()</code> method is provided.
 * </p>
 * 
 * @see StyledText#setLineBullet(int, int, Bullet)
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * 
 * @since 3.2
 */
public class Bullet {
	/**
	* The bullet type.  Possible values are:
	* <ul>
	* <li><code>ST.BULLET_DOT</code></li>
	* <li><code>ST.BULLET_NUMBER</code></li>
	* <li><code>ST.BULLET_LETTER_LOWER</code></li>
	* <li><code>ST.BULLET_LETTER_UPPER</code></li>
	* <li><code>ST.BULLET_TEXT</code></li>
	* <li><code>ST.BULLET_CUSTOM</code></li>
	* </ul>
	*/
	public int type;

	/**
	* The bullet style.
	*/
	public StyleRange style;

	/**
	* The bullet text.
	*/
	public String text;

	int[] linesIndices;
	int count;

/** 
 * Create a new bullet with the specified style, and type <code>ST.BULLET_DOT</code>. 
 * The style must have a glyph metrics set.
 *
 * @param style the style 
 * 
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when the style or the glyph metrics are null</li>
 * </ul> 
 */
public Bullet(StyleRange style) {
	this(ST.BULLET_DOT, style);
}
/** 
 * Create a new bullet the specified style and type. 
 * The style must have a glyph metrics set.
 *
 * @param type the bullet type
 * @param style the style 
 * 
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT when the style or the glyph metrics are null</li>
 * </ul> 
 */
public Bullet(int type, StyleRange style) {
	if (style == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	if (style.metrics == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	this.type = type;
	this.style = style;
}	
void addIndices (int startLine, int lineCount) {
	if (this.linesIndices == null) {
		this.linesIndices = new int[lineCount];
		this.count = lineCount;
		for (int i = 0; i < lineCount; i++) this.linesIndices[i] = startLine + i;
	} else {
		int modifyStart = 0;
		while (modifyStart < this.count) {
			if (startLine <= this.linesIndices[modifyStart]) break;
			modifyStart++;
		}
		int modifyEnd = modifyStart;
		while (modifyEnd < this.count) {
			if (startLine + lineCount <= this.linesIndices[modifyEnd]) break;
			modifyEnd++;
		}
		int newSize = modifyStart + lineCount + this.count - modifyEnd;
		if (newSize > this.linesIndices.length) {
			int[] newLinesIndices = new int[newSize];
			System.arraycopy(this.linesIndices, 0, newLinesIndices, 0, count);
			this.linesIndices = newLinesIndices;
		}
		System.arraycopy(linesIndices, modifyEnd, linesIndices, modifyStart + lineCount, count - modifyEnd);
		for (int i = 0; i < lineCount; i++) linesIndices[modifyStart + i] = startLine + i;
		count = newSize;
	}
}
int indexOf (int lineIndex) {
	for (int i = 0; i < this.count; i++) {
		if (this.linesIndices[i] == lineIndex) return i;
	}
	return -1;
}
@Override
public int hashCode() {
	return style.hashCode() ^ this.type;
}
int[] removeIndices (int startLine, int replaceLineCount, int newLineCount, boolean update) {
	if (this.count == 0) return null;
	if (startLine > this.linesIndices[this.count - 1]) return null;
	int endLine = startLine + replaceLineCount;
	int delta = newLineCount - replaceLineCount;
	for (int i = 0; i < this.count; i++) {
		int index = this.linesIndices[i];
		if (startLine <= index) {
			int j = i;
			while (j < this.count) {
				if (this.linesIndices[j] >= endLine) break;
				j++;
			}
			if (update) {
				for (int k = j; k < this.count; k++) this.linesIndices[k] += delta;
			}
			int[] redrawLines = new int[this.count - j];
			System.arraycopy(this.linesIndices, j, redrawLines, 0, count - j);
			System.arraycopy(linesIndices, j, linesIndices, i, count - j);
			count -= (j - i);
			return redrawLines;
		}
	}
	for (int i = 0; i < count; i++) linesIndices[i] += delta;
	return null;
}
int size() {
	return count;
}
}
