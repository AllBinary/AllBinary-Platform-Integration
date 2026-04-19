/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.custom;
import org.allbinary.thread.ARunnable;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * A StyledTextRenderer renders the content of a StyledText widget.
 * This class can be used to render to the display or to a printer.
 */
class StyledTextRenderer {
	Device device;
	StyledText styledText;
	StyledTextContent content;

	/* Font info */
	Font regularFont, boldFont, italicFont, boldItalicFont;
	int tabWidth;
	int ascent, descent;
	int averageCharWidth;
	
	/* Line data */
	int topIndex = -1;
	TextLayout[] layouts;
	int lineCount;
	int[] lineWidth;
	int[] lineHeight;
	LineInfo[] lines;
	int maxWidth;
	int maxWidthLineIndex;
	boolean idleRunning;
	
	/* Bullet */
	Bullet[] bullets;
	int[] bulletsIndices;
	int[] redrawLines;
	
	/* Style data */
	int[] ranges;
	int styleCount;	
	StyleRange[] styles;
	StyleRange[] stylesSet;
	int stylesSetCount = 0;
	boolean hasLinks, fixedPitch;
	final static int BULLET_MARGIN = 8;
	
	final static boolean COMPACT_STYLES = true;
	final static boolean MERGE_STYLES = true;
	
	final static int GROW = 32;
	final static int IDLE_TIME = 50;
	final static int CACHE_SIZE = 128;
	
	final static int BACKGROUND = 1 << 0;
	final static int ALIGNMENT = 1 << 1;
	final static int INDENT = 1 << 2;
	final static int JUSTIFY = 1 << 3;
	final static int SEGMENTS = 1 << 5;
	final static int TABSTOPS = 1 << 6;
	final static int WRAP_INDENT = 1 << 7;
	final static int SEGMENT_CHARS = 1 << 8;
	
	static class LineInfo {
		int flags;
		Color background;
		int alignment;
		int indent;
		int wrapIndent;
		boolean justify;
		int[] segments;
		char[] segmentsChars;
		int[] tabStops;

		public LineInfo() {
		}
		public LineInfo(LineInfo info) {
			if (info != null) {
				flags = info.flags;
				background = info.background;
				alignment = info.alignment;
				indent = info.indent;
				wrapIndent = info.wrapIndent;
				justify = info.justify;
				segments = info.segments;
				segmentsChars = info.segmentsChars;
				tabStops = info.tabStops;
			}
		}
	}
	
StyledTextRenderer(Device device, StyledText styledText) {
	this.device = device;
	this.styledText = styledText;
}
int addMerge(int[] mergeRanges, StyleRange[] mergeStyles, int mergeCount, int modifyStart, int modifyEnd) {
	int rangeCount = styleCount << 1;
	StyleRange endStyle = null;
	int endStart = 0, endLength = 0;
	if (modifyEnd < rangeCount) {
		endStyle = this.styles[modifyEnd >> 1];
		endStart = this.ranges[modifyEnd];
		endLength = this.ranges[modifyEnd + 1];
	}
	int grow = mergeCount - (modifyEnd - modifyStart);
	if (rangeCount + grow >= this.ranges.length) {
		int[] tmpRanges = new int[this.ranges.length + grow + (GROW << 1)];
		System.arraycopy(this.ranges, 0, tmpRanges, 0, modifyStart);
		StyleRange[] tmpStyles = new StyleRange[this.styles.length + (grow >> 1) + GROW];
		System.arraycopy(this.styles, 0, tmpStyles, 0, modifyStart >> 1);
		if (rangeCount > modifyEnd) {
			System.arraycopy(this.ranges, modifyEnd, tmpRanges, modifyStart + mergeCount, rangeCount - modifyEnd);
			System.arraycopy(this.styles, modifyEnd >> 1, tmpStyles, (modifyStart + mergeCount) >> 1, styleCount - (modifyEnd >> 1));
		}
		this.ranges = tmpRanges;
		this.styles = tmpStyles;
	} else {
		if (rangeCount > modifyEnd) {
			System.arraycopy(ranges, modifyEnd, ranges, modifyStart + mergeCount, rangeCount - modifyEnd);
			System.arraycopy(styles, modifyEnd >> 1, styles, (modifyStart + mergeCount) >> 1, styleCount - (modifyEnd >> 1));
		}
	}
	if (MERGE_STYLES) {
		int j = modifyStart;	
		for (int i = 0; i < mergeCount; i += 2) {
			if (j > 0 && ranges[j - 2] + ranges[j - 1] == mergeRanges[i] && mergeStyles[i >> 1].similarTo(styles[(j - 2) >> 1])) {
				ranges[j - 1] += mergeRanges[i + 1];
			} else {
				styles[j >> 1] = mergeStyles[i >> 1];
				ranges[j++] = mergeRanges[i];
				ranges[j++] = mergeRanges[i + 1];
			}
		}
		if (endStyle != null && ranges[j - 2] + ranges[j - 1] == endStart && endStyle.similarTo(styles[(j - 2) >> 1])) {
			ranges[j - 1] += endLength;
			modifyEnd += 2;
			mergeCount += 2;
		}
		if (rangeCount > modifyEnd) {
			System.arraycopy(ranges, modifyStart + mergeCount, ranges, j, rangeCount - modifyEnd);
			System.arraycopy(styles, (modifyStart + mergeCount) >> 1, styles, j >> 1, styleCount - (modifyEnd >> 1));
		}
		grow = (j - modifyStart) - (modifyEnd - modifyStart);
	} else {
		System.arraycopy(mergeRanges, 0, ranges, modifyStart, mergeCount);
		System.arraycopy(mergeStyles, 0, styles, modifyStart >> 1, mergeCount >> 1);
	}
	styleCount += grow >> 1;
	return grow;
}
int addMerge(StyleRange[] mergeStyles, int mergeCount, int modifyStart, int modifyEnd) {
	int grow = mergeCount - (modifyEnd - modifyStart);
	StyleRange endStyle = null;
	if (modifyEnd < this.styleCount) endStyle = this.styles[modifyEnd];
	if (this.styleCount + grow >= this.styles.length) {
		StyleRange[] tmpStyles = new StyleRange[this.styles.length + grow + GROW];
		System.arraycopy(this.styles, 0, tmpStyles, 0, modifyStart);
		if (this.styleCount > modifyEnd) {
			System.arraycopy(this.styles, modifyEnd, tmpStyles, modifyStart + mergeCount, styleCount - modifyEnd);
		}
		this.styles = tmpStyles;
	} else {
		if (styleCount > modifyEnd) {
			System.arraycopy(styles, modifyEnd, styles, modifyStart + mergeCount, styleCount - modifyEnd);
		}
	}
	if (MERGE_STYLES) {
		int j = modifyStart;
		for (int i = 0; i < mergeCount; i++) {
			StyleRange newStyle = mergeStyles[i], style;
			if (j > 0 && (style = styles[j - 1]).start + style.length == newStyle.start && newStyle.similarTo(style)) {
				style.length += newStyle.length;
			} else {
				styles[j++] = newStyle;
			}
		}
		StyleRange style = styles[j - 1];
		if (endStyle != null && style.start + style.length == endStyle.start && endStyle.similarTo(style)) {
			style.length += endStyle.length;
			modifyEnd++;
			mergeCount++;
		}
		if (styleCount > modifyEnd) {
			System.arraycopy(styles, modifyStart + mergeCount, styles, j, styleCount - modifyEnd);
		}
		grow = (j - modifyStart) - (modifyEnd - modifyStart);
	} else {
		System.arraycopy(mergeStyles, 0, styles, modifyStart, mergeCount);
	}
	styleCount += grow;
	return grow;
}
void calculate(int startLine, int lineCount) {
	int endLine = startLine + lineCount;
	if (startLine < 0 || endLine > this.lineWidth.length) {
		return;
	}
	int hTrim = this.styledText.leftMargin + this.styledText.rightMargin + this.styledText.getCaretWidth();
	for (int i = startLine; i < endLine; i++) {
		if (this.lineWidth[i] == -1 || this.lineHeight[i] == -1) {
			TextLayout layout = getTextLayout(i);
			Rectangle rect = layout.getBounds();
			this.lineWidth[i] = rect.width + hTrim;
			this.lineHeight[i] = rect.height;
			disposeTextLayout(layout);
		}
		if (this.lineWidth[i] > this.maxWidth) {
			this.maxWidth = this.lineWidth[i];
			this.maxWidthLineIndex = i;
		}
	}
}
void calculateClientArea () {
	int index = styledText.getTopIndex();
	int lineCount = this.content.getLineCount();
	int height = this.styledText.getClientArea().height;
	int y = 0;
	while (height > y && lineCount > index) {
		calculate(index, 1);
		y += this.lineHeight[index++];
	}
}
void calculateIdle () {
	if (this.idleRunning) return;
	Runnable runnable = new ARunnable() {
		public void run() {
			if (styledText == null) return;
			int i;
			long start = System.currentTimeMillis();
			for (i = 0; i < lineCount; i++) {
				if (lineHeight[i] == -1 || lineWidth[i] == -1) {
					calculate(i, 1);
					if (System.currentTimeMillis() - start > IDLE_TIME) break;
				}
			}
			if (i < lineCount) {
				Display display = styledText.getDisplay();				
				display.asyncExec(this);
			} else {
				idleRunning = false;
				styledText.setScrollBars(true);
				ScrollBar bar = styledText.getVerticalBar();
				if (bar != null) {
					bar.setSelection(styledText.getVerticalScrollOffset());
				}					
			}
		}
	};		
	Display display = this.styledText.getDisplay();
	display.asyncExec(runnable);
	this.idleRunning = true;
}
void clearLineBackground(int startLine, int count) {
	if (this.lines == null) return;
	for (int i = startLine; i < startLine + count; i++) {
		LineInfo info = this.lines[i];
		if (info != null) {
			info.flags &= ~BACKGROUND;
			info.background = null;
			if (info.flags == 0) this.lines[i] = null;
		}
	}
}
void clearLineStyle(int startLine, int count) {
	if (this.lines == null) return;
	for (int i = startLine; i < startLine + count; i++) {
		LineInfo info = this.lines[i];
		if (info != null) {
			info.flags &= ~(ALIGNMENT | INDENT | WRAP_INDENT | JUSTIFY | TABSTOPS);
			if (info.flags == 0) this.lines[i] = null;
		}
	}
}
void copyInto(StyledTextRenderer renderer) {
	if (this.ranges != null) {
		int[] newRanges = renderer.ranges = new int[this.styleCount << 1];
		System.arraycopy(this.ranges, 0, newRanges, 0, newRanges.length);
	}
	if (this.styles != null) {
		StyleRange[] newStyles = renderer.styles = new StyleRange[this.styleCount];
		for (int i = 0; i < newStyles.length; i++) {
			newStyles[i] = (StyleRange)this.styles[i].clone();
		}
		renderer.styleCount = this.styleCount;
	}
	if (this.lines != null) {
		LineInfo[] newLines = renderer.lines = new LineInfo[this.lineCount];
		for (int i = 0; i < newLines.length; i++) {
			newLines[i] = new LineInfo(this.lines[i]);				
		}
		renderer.lineCount = this.lineCount;
	}
}
void dispose() {
	if (boldFont != null) boldFont.dispose();
	if (italicFont != null) italicFont.dispose();
	if (boldItalicFont != null) boldItalicFont.dispose();
	boldFont = italicFont = boldItalicFont = null;
	reset();
	this.content = null;
	this.device = null;
	this.styledText = null;
}
void disposeTextLayout (TextLayout layout) {
	if (this.layouts != null) {
		for (int i = 0; i < this.layouts.length; i++) {
			if (this.layouts[i] == layout) return;
		}
	}
	layout.dispose();
}
void drawBullet(Bullet bullet, GC gc, int paintX, int paintY, int index, int lineAscent, int lineDescent) {
	StyleRange style = bullet.style;
	GlyphMetrics metrics = style.metrics;
	Color color = style.foreground;
	if (color != null) gc.setForeground(color);
	if ((bullet.type & ST.BULLET_DOT) != 0 && StyledText.IS_MOTIF) {
		int size = Math.max(4, (lineAscent + lineDescent) / 4);
		if ((size & 1) == 0) size++;
		if (color == null) {
			Display display = this.styledText.getDisplay();
			color = display.getSystemColor(SWT.COLOR_BLACK);
		}
		gc.setBackground(color);
		int x = paintX + Math.max(0, metrics.width - size - BULLET_MARGIN);
		gc.fillArc(x, paintY + size, size + 1, size + 1, 0, 360);
		return;
	}
	Font font = style.font;
	if (font != null) gc.setFont(font);
	String string = "";
	int type = bullet.type & (ST.BULLET_DOT|ST.BULLET_NUMBER|ST.BULLET_LETTER_LOWER|ST.BULLET_LETTER_UPPER);
	switch (type) {
		case ST.BULLET_DOT: string = "\u2022"; break;
		case ST.BULLET_NUMBER: string = String.valueOf(index + 1); break;
		case ST.BULLET_LETTER_LOWER: string = String.valueOf((char) (index % 26 + 97)); break;
		case ST.BULLET_LETTER_UPPER: string = String.valueOf((char) (index % 26 + 65)); break;
	}
	if ((bullet.type & ST.BULLET_TEXT) != 0) string += bullet.text;
	Display display = this.styledText.getDisplay();
	TextLayout layout = new TextLayout(display);
	layout.setText(string);
	layout.setAscent(lineAscent);
	layout.setDescent(lineDescent);
	style = (StyleRange)style.clone();
	style.metrics = null;
	if (style.font == null) style.font = getFont(style.fontStyle);
	layout.setStyle(style, 0, string.length());	
	int x = paintX + Math.max(0, metrics.width - layout.getBounds().width - BULLET_MARGIN);
	layout.draw(gc, x, paintY);
	layout.dispose();
}
int drawLine(int lineIndex, int paintX, int paintY, GC gc, Color widgetBackground, Color widgetForeground) {
	TextLayout layout = getTextLayout(lineIndex);
	String line = this.content.getLine(lineIndex);
	int lineOffset = this.content.getOffsetAtLine(lineIndex);
	int lineLength = line.length();
	Point selection = this.styledText.getSelection();
	int selectionStart = selection.x - lineOffset;
	int selectionEnd = selection.y - lineOffset;
	if (this.styledText.getBlockSelection()) {
		selectionStart = selectionEnd = 0;
	}
	Rectangle client = this.styledText.getClientArea();  
	Color lineBackground = getLineBackground(lineIndex, null);
	StyledTextEvent event = this.styledText.getLineBackgroundData(lineOffset, line);
	if (event != null && event.lineBackground != null) lineBackground = event.lineBackground;
	int height = layout.getBounds().height;
	if (lineBackground != null) {
		gc.setBackground(lineBackground);
		gc.fillRectangle(client.x, paintY, client.width, height);
	} else {
		gc.setBackground(widgetBackground);
		this.styledText.drawBackground(gc, client.x, paintY, client.width, height);
	}
	gc.setForeground(widgetForeground);
	if (selectionStart == selectionEnd || (selectionEnd <= 0 && selectionStart > lineLength - 1)) {
		layout.draw(gc, paintX, paintY);
	} else {
		int start = Math.max(0, selectionStart);
		int end = Math.min(lineLength, selectionEnd);
		Color selectionFg = this.styledText.getSelectionForeground();
		Color selectionBg = this.styledText.getSelectionBackground();
		int flags;
		if ((this.styledText.getStyle() & SWT.FULL_SELECTION) != 0) {
			flags = SWT.FULL_SELECTION;
		} else {
			flags = SWT.DELIMITER_SELECTION;
		}
		if (selectionStart <= lineLength && lineLength < selectionEnd ) {
			flags |= SWT.LAST_LINE_SELECTION;
		}
		layout.draw(gc, paintX, paintY, start, end - 1, selectionFg, selectionBg, flags);
	}
	
	// draw objects
	Bullet bullet = null;
	int bulletIndex = -1;
	if (this.bullets != null) {
		if (this.bulletsIndices != null) {
			int index = lineIndex - this.topIndex;
			if (0 <= index && index < CACHE_SIZE) {
				bullet = this.bullets[index];
				bulletIndex = this.bulletsIndices[index];
			}
		} else {
			for (int i = 0; i < this.bullets.length; i++) {
				bullet = this.bullets[i];
				bulletIndex = bullet.indexOf(lineIndex);
				if (bulletIndex != -1) break;
			}
		}
	}
	if (bulletIndex != -1 && bullet != null) {
		FontMetrics metrics = layout.getLineMetrics(0);
		int lineAscent = metrics.getAscent() + metrics.getLeading();
		if (bullet.type == ST.BULLET_CUSTOM) {
			bullet.style.start = lineOffset;
			this.styledText.paintObject(gc, paintX, paintY, lineAscent, metrics.getDescent(), bullet.style, bullet, bulletIndex);
		} else {
			drawBullet(bullet, gc, paintX, paintY, bulletIndex, lineAscent, metrics.getDescent());
		}
	}
	TextStyle[] styles = layout.getStyles();
	int[] ranges = null;
	for (int i = 0; i < styles.length; i++) {
		if (styles[i].metrics != null) {
			if (ranges == null) ranges = layout.getRanges();
			int start = ranges[i << 1];
			int length = ranges[(i << 1) + 1] - start + 1;
			Point point = layout.getLocation(start, false);
			FontMetrics metrics = layout.getLineMetrics(layout.getLineIndex(start));
			StyleRange style = (StyleRange)((StyleRange)styles[i]).clone();
			style.start = start + lineOffset;
			style.length = length;
			int lineAscent = metrics.getAscent() + metrics.getLeading();
			this.styledText.paintObject(gc, point.x + paintX, point.y + paintY, lineAscent, metrics.getDescent(), style, null, 0);
		}
	}
	disposeTextLayout(layout);
	return height;
}
int getBaseline() {
	return ascent;
}
Font getFont(int style) {
	switch (style) {
		case SWT.BOLD:
			if (boldFont != null) return boldFont;
			return boldFont = new Font(this.device, getFontData(style));
		case SWT.ITALIC:
			if (italicFont != null) return italicFont;
			return italicFont = new Font(this.device, getFontData(style));
		case SWT.BOLD | SWT.ITALIC:
			if (boldItalicFont != null) return boldItalicFont;
			return boldItalicFont = new Font(this.device, getFontData(style));
		default:
			return regularFont;
	}
}
FontData[] getFontData(int style) {
	FontData[] fontDatas = regularFont.getFontData();
	for (int i = 0; i < fontDatas.length; i++) {
		fontDatas[i].setStyle(style);
	}
	return fontDatas;
}
int getHeight () {
	int defaultLineHeight = getLineHeight();
	if (this.styledText.isFixedLineHeight()) {
		return lineCount * defaultLineHeight + this.styledText.topMargin + this.styledText.bottomMargin;
	}
	int totalHeight = 0;
	int width = this.styledText.getWrapWidth();
	for (int i = 0; i < lineCount; i++) {
		int height = this.lineHeight[i];
		if (height == -1) {
			if (width > 0) {
				int length = this.content.getLine(i).length();
				height = ((length * this.averageCharWidth / width) + 1) * defaultLineHeight;
			} else {
				height = defaultLineHeight;
			}
		}
		totalHeight += height;
	}
	return totalHeight + this.styledText.topMargin + this.styledText.bottomMargin;
}
boolean hasLink(int offset) {
	if (offset == -1) return false;
	int lineIndex = this.content.getLineAtOffset(offset);
	int lineOffset = this.content.getOffsetAtLine(lineIndex);
	String line = this.content.getLine(lineIndex);
	StyledTextEvent event = this.styledText.getLineStyleData(lineOffset, line);
	if (event != null) {
		StyleRange[] styles = event.styles;
		if (styles != null) {
			int[] ranges = event.ranges; 
			if (ranges != null) {
				for (int i = 0; i < ranges.length; i+=2) {
					if (ranges[i] <= offset && offset < ranges[i] + ranges[i+1] && styles[i >> 1].underline && styles[i >> 1].underlineStyle == SWT.UNDERLINE_LINK) {
						return true;
					}
				}
			} else {
				for (int i = 0; i < styles.length; i++) {
					StyleRange style = styles[i]; 
					if (style.start <= offset && offset < style.start + style.length && style.underline && style.underlineStyle == SWT.UNDERLINE_LINK) {
						return true;
					}
				}
			}
		}
	}  else {
		if (ranges != null) {
			int rangeCount = this.styleCount << 1;
			int index = getRangeIndex(offset, -1, rangeCount);
			if (index >= rangeCount) return false;
			int rangeStart = ranges[index]; 
			int rangeLength = ranges[index + 1];
			StyleRange rangeStyle = styles[index >> 1];
			if (rangeStart <= offset && offset < rangeStart + rangeLength && rangeStyle.underline && rangeStyle.underlineStyle == SWT.UNDERLINE_LINK) {
				return true;
			}
		}
	}
	return false;
}
int getLineAlignment(int index, int defaultAlignment) {
	if (this.lines == null) return defaultAlignment;
	LineInfo info = this.lines[index];
	if (info != null && (info.flags & ALIGNMENT) != 0) {
		return info.alignment;
	}
	return defaultAlignment;
}
Color getLineBackground(int index, Color defaultBackground) {
	if (this.lines == null) return defaultBackground;
	LineInfo info = this.lines[index];
	if (info != null && (info.flags & BACKGROUND) != 0) {
		return info.background;
	}
	return defaultBackground;
}
Bullet getLineBullet (int index, Bullet defaultBullet) {
	if (this.bullets == null) return defaultBullet;
	if (this.bulletsIndices != null) return defaultBullet;
	for (int i = 0; i < this.bullets.length; i++) {
		Bullet bullet = this.bullets[i];
		if (bullet.indexOf(index) != -1) return bullet;
	}
	return defaultBullet;
}
int getLineHeight() {
	return ascent + descent;
}
int getLineHeight(int lineIndex) {
	if (this.lineHeight[lineIndex] == -1) {
		calculate(lineIndex, 1);
	}
	return lineHeight[lineIndex];
}
int getLineIndent(int index, int defaultIndent) {
	if (this.lines == null) return defaultIndent;
	LineInfo info = this.lines[index];
	if (info != null && (info.flags & INDENT) != 0) {
		return info.indent;
	}
	return defaultIndent;
}
int getLineWrapIndent(int index, int defaultWrapIndent) {
	if (this.lines == null) return defaultWrapIndent;
	LineInfo info = this.lines[index];
	if (info != null && (info.flags & WRAP_INDENT) != 0) {
		return info.wrapIndent;
	}
	return defaultWrapIndent;
}
boolean getLineJustify(int index, boolean defaultJustify) {
	if (this.lines == null) return defaultJustify;
	LineInfo info = this.lines[index];
	if (info != null && (info.flags & JUSTIFY) != 0) {
		return info.justify;
	}
	return defaultJustify;
}
int[] getLineTabStops(int index, int[] defaultTabStops) {
	if (this.lines == null) return defaultTabStops;
	LineInfo info = this.lines[index];
	if (info != null && (info.flags & TABSTOPS) != 0) {
		return info.tabStops;
	}
	return defaultTabStops;
}
int getRangeIndex(int offset, int low, int high) {
	if (this.styleCount == 0) return 0;
	if (this.ranges != null)  {
		while (high - low > 2) {
			int index = ((high + low) / 2) / 2 * 2;
			int end = this.ranges[index] + this.ranges[index + 1];
			if (end > offset) {
				high = index;
			} else {
				low = index;
			}
		}
	} else {
		while (high - low > 1) {
			int index = ((high + low) / 2);
			int end = this.styles[index].start + this.styles[index].length;
			if (end > offset) {
				high = index;
			} else {
				low = index;
			}
		}
	}
	return high;
}
int[] getRanges(int start, int length) {
	if (length == 0) return null;
	int[] newRanges;
	int end = start + length - 1;
	if (this.ranges != null) {
		int rangeCount = this.styleCount << 1;
		int rangeStart = getRangeIndex(start, -1, rangeCount);
		if (rangeStart >= rangeCount) return null;
		if (this.ranges[rangeStart] > end) return null;
		int rangeEnd = Math.min(rangeCount - 2, getRangeIndex(end, rangeStart - 1, rangeCount));
		if (this.ranges[rangeEnd] > end) rangeEnd = Math.max(rangeStart, rangeEnd - 2);
		newRanges = new int[rangeEnd - rangeStart + 2];
		System.arraycopy(this.ranges, rangeStart, newRanges, 0, newRanges.length);
	} else {
		int rangeStart = getRangeIndex(start, -1, styleCount);
		if (rangeStart >= styleCount) return null;
		if (this.styles[rangeStart].start > end) return null;
		int rangeEnd = Math.min(styleCount - 1, getRangeIndex(end, rangeStart - 1, styleCount));
		if (this.styles[rangeEnd].start > end) rangeEnd = Math.max(rangeStart, rangeEnd - 1);
		newRanges = new int[(rangeEnd - rangeStart + 1) << 1];
		for (int i = rangeStart, j = 0; i <= rangeEnd; i++, j += 2) {
			StyleRange style = this.styles[i];
			newRanges[j] = style.start;
			newRanges[j + 1] = style.length;
		}
	}
	if (start > newRanges[0]) {
		newRanges[1] = newRanges[0] + newRanges[1] - start;
		newRanges[0] = start;
	}
	if (end < newRanges[newRanges.length - 2] + newRanges[newRanges.length - 1] - 1) {
		newRanges[newRanges.length - 1] = end - newRanges[newRanges.length - 2] + 1;
	}
	return newRanges;
}
StyleRange[] getStyleRanges(int start, int length, boolean includeRanges) {
	if (length == 0) return null;
	StyleRange[] newStyles;
	int end = start + length - 1;
	if (this.ranges != null) {
		int rangeCount = this.styleCount << 1;
		int rangeStart = getRangeIndex(start, -1, rangeCount);
		if (rangeStart >= rangeCount) return null;
		if (this.ranges[rangeStart] > end) return null;
		int rangeEnd = Math.min(rangeCount - 2, getRangeIndex(end, rangeStart - 1, rangeCount));
		if (this.ranges[rangeEnd] > end) rangeEnd = Math.max(rangeStart, rangeEnd - 2);
		newStyles = new StyleRange[((rangeEnd - rangeStart) >> 1) + 1];
		if (includeRanges) {
			for (int i = rangeStart, j = 0; i <= rangeEnd; i += 2, j++) {
				newStyles[j] = (StyleRange)this.styles[i >> 1].clone();
				newStyles[j].start = this.ranges[i];
				newStyles[j].length = this.ranges[i + 1];
			}
		} else {
			System.arraycopy(this.styles, rangeStart >> 1, newStyles, 0, newStyles.length);
		}
	} else {
		int rangeStart = getRangeIndex(start, -1, styleCount);
		if (rangeStart >= styleCount) return null;
		if (this.styles[rangeStart].start > end) return null;
		int rangeEnd = Math.min(styleCount - 1, getRangeIndex(end, rangeStart - 1, styleCount));
		if (this.styles[rangeEnd].start > end) rangeEnd = Math.max(rangeStart, rangeEnd - 1);
		newStyles = new StyleRange[rangeEnd - rangeStart + 1];
		System.arraycopy(this.styles, rangeStart, newStyles, 0, newStyles.length);
	}
	if (includeRanges || this.ranges == null) {
		StyleRange style = newStyles[0];
		if (start > style.start) {
			newStyles[0] = style = (StyleRange)style.clone();
			style.length = style.start + style.length - start;
			style.start = start;
		}
		style = newStyles[newStyles.length - 1];
		if (end < style.start + style.length - 1) {
			newStyles[newStyles.length - 1] = style = (StyleRange)style.clone();
			style.length = end - style.start + 1;
		}
	}
	return newStyles;
}
StyleRange getStyleRange(StyleRange style) {
	if (style.underline && style.underlineStyle == SWT.UNDERLINE_LINK) hasLinks = true;
	if (style.start == 0 && style.length == 0 && style.fontStyle == SWT.NORMAL) return style;
	StyleRange clone = (StyleRange)style.clone();
	clone.start = clone.length = 0;
	clone.fontStyle = SWT.NORMAL;
	if (clone.font == null) clone.font = getFont(style.fontStyle);
	return clone;
}
TextLayout getTextLayout(int lineIndex) {
	return getTextLayout(lineIndex, styledText.getOrientation(), styledText.getWrapWidth(), styledText.lineSpacing);
}
TextLayout getTextLayout(int lineIndex, int orientation, int width, int lineSpacing) {
	TextLayout layout = null;
	if (this.styledText != null) {
		int topIndex = this.styledText.topIndex > 0 ? this.styledText.topIndex - 1 : 0;	
		if (this.layouts == null || topIndex != this.topIndex) {
			TextLayout[] newLayouts = new TextLayout[CACHE_SIZE];
			if (this.layouts != null) {
				for (int i = 0; i < this.layouts.length; i++) {
					if (this.layouts[i] != null) {
						int layoutIndex = (i + this.topIndex) - topIndex;
						if (0 <= layoutIndex && layoutIndex < newLayouts.length) {
							newLayouts[layoutIndex] = this.layouts[i];
						} else {
							this.layouts[i].dispose();
						}
					}
				}
			}
			if (this.bullets != null && this.bulletsIndices != null && topIndex != this.topIndex) {
				int delta = topIndex - this.topIndex;
				if (delta > 0) {
					if (delta < this.bullets.length) {
						System.arraycopy(bullets, delta, bullets, 0, bullets.length - delta);
						System.arraycopy(bulletsIndices, delta, bulletsIndices, 0, bulletsIndices.length - delta);
					}
					int startIndex = Math.max(0, bullets.length - delta);
					for (int i = startIndex; i < bullets.length; i++) bullets[i] = null;
				} else {
					if (-delta < bullets.length) {
						System.arraycopy(bullets, 0, bullets, -delta, bullets.length + delta);
						System.arraycopy(bulletsIndices, 0, bulletsIndices, -delta, bulletsIndices.length + delta);
					}
					int endIndex = Math.min(bullets.length, -delta);
					for (int i = 0; i < endIndex; i++) bullets[i] = null;
				}
			}
			this.topIndex = topIndex;
			this.layouts = newLayouts;
		}
		if (this.layouts != null) {
			int layoutIndex = lineIndex - topIndex;
			if (0 <= layoutIndex && layoutIndex < this.layouts.length) {
				layout = this.layouts[layoutIndex];
				if (layout != null) {
					if (this.lineWidth[lineIndex] != -1) return layout;
				} else {
					layout = this.layouts[layoutIndex] = new TextLayout(this.device);
				}
			}
		}
	}
	if (layout == null) layout = new TextLayout(this.device);
	String line = this.content.getLine(lineIndex);
	int lineOffset = this.content.getOffsetAtLine(lineIndex);
	int[] segments = null;
	char[] segmentChars = null;
	int indent = 0;
	int wrapIndent = 0;
	int alignment = SWT.LEFT;
	int textDirection = orientation;
	boolean justify = false;
	int[] tabs = {this.tabWidth};
	Bullet bullet = null;
	int[] ranges = null;
	StyleRange[] styles = null;
	int rangeStart = 0, styleCount = 0;
	StyledTextEvent event = null;
	if (this.styledText != null) {
		event = this.styledText.getBidiSegments(lineOffset, line);
		if (event != null) {
			segments = event.segments;
			segmentChars = event.segmentsChars;
		}
		event = this.styledText.getLineStyleData(lineOffset, line);
		indent = this.styledText.indent;
		wrapIndent = this.styledText.wrapIndent;
		alignment = this.styledText.alignment;
		if ((this.styledText.getStyle() & SWT.FLIP_TEXT_DIRECTION) != 0) {
			textDirection = orientation == SWT.RIGHT_TO_LEFT ? SWT.LEFT_TO_RIGHT : SWT.RIGHT_TO_LEFT;
		}
		justify = this.styledText.justify;
		if (this.styledText.tabs != null) tabs = this.styledText.tabs;
	}
	if (event != null) {
		indent = event.indent;
		wrapIndent = event.wrapIndent;
		alignment = event.alignment;
		justify = event.justify;
		bullet = event.bullet;
		ranges = event.ranges;
		styles = event.styles;
		if (event.tabStops != null) tabs = event.tabStops;
		if (styles != null) {
			styleCount = styles.length;
			if (this.styledText.isFixedLineHeight()) {
				for (int i = 0; i < styleCount; i++) {
					if (styles[i].isVariableHeight()) {
						this.styledText.verticalScrollOffset = -1;
						this.styledText.setVariableLineHeight();
						this.styledText.redraw();
						break;
					}
				}
			}
		}
		if (bullets == null || bulletsIndices == null) {
			bullets = new Bullet[CACHE_SIZE];
			bulletsIndices = new int[CACHE_SIZE];
		}
		int index = lineIndex - topIndex;
		if (0 <= index && index < CACHE_SIZE) {
			bullets[index] = bullet;
			bulletsIndices[index] = event.bulletIndex;
		}
	} else {
		if (this.lines != null) {
			LineInfo info = this.lines[lineIndex];
			if (info != null) {
				if ((info.flags & INDENT) != 0) indent = info.indent;
				if ((info.flags & WRAP_INDENT) != 0) wrapIndent = info.wrapIndent;
				if ((info.flags & ALIGNMENT) != 0) alignment = info.alignment;
				if ((info.flags & JUSTIFY) != 0) justify = info.justify;
				if ((info.flags & SEGMENTS) != 0) segments = info.segments;
				if ((info.flags & SEGMENT_CHARS) != 0) segmentChars = info.segmentsChars;
				if ((info.flags & TABSTOPS) != 0) tabs = info.tabStops;
			}
		}
		if (bulletsIndices != null) {
			bullets = null;
			bulletsIndices = null;
		}
		if (bullets != null) {
			for (int i = 0; i < bullets.length; i++) {
				if (bullets[i].indexOf(lineIndex) != -1) {
					bullet = bullets[i];
					break;
				}
			}
		}
		ranges = this.ranges;
		styles = this.styles;
		styleCount = this.styleCount;
		if (ranges != null) {
			rangeStart = getRangeIndex(lineOffset, -1, styleCount << 1);
		} else {
			rangeStart = getRangeIndex(lineOffset, -1, styleCount);
		}
	}
	if (bullet != null) {
		StyleRange style = bullet.style;
		GlyphMetrics metrics = style.metrics;
		indent += metrics.width;
	}
	layout.setFont(regularFont);
	layout.setAscent(ascent);
	layout.setDescent(descent);
	layout.setText(line);
	layout.setOrientation(orientation);
	layout.setSegments(segments);
	layout.setSegmentsChars(segmentChars);
	layout.setWidth(width);
	layout.setSpacing(lineSpacing);
	layout.setTabs(tabs);
	layout.setIndent(indent);
	layout.setWrapIndent(wrapIndent);
	layout.setAlignment(alignment);
	layout.setJustify(justify);
	layout.setTextDirection(textDirection);
	
	int lastOffset = 0;
	int length = line.length();
	if (styles != null) {
		if (ranges != null) {
			int rangeCount = styleCount << 1;
			for (int i = rangeStart; i < rangeCount; i += 2) {
				int start, end;
				if (lineOffset > ranges[i]) {
					start = 0;
					end = Math.min (length, ranges[i + 1] - lineOffset + ranges[i]);
				} else {
					start = ranges[i] - lineOffset;
					end = Math.min(length, start + ranges[i + 1]);
				}
				if (start >= length) break;
				if (lastOffset < start) {
					layout.setStyle(null, lastOffset, start - 1);
				}
				layout.setStyle(getStyleRange(styles[i >> 1]), start, end);
				lastOffset = Math.max(lastOffset, end);
			}
		} else {
			for (int i = rangeStart; i < styleCount; i++) {
				int start, end;
				if (lineOffset > styles[i].start) {
					start = 0;
					end = Math.min (length, styles[i].length - lineOffset + styles[i].start);
				} else {
					start = styles[i].start - lineOffset;
					end = Math.min(length, start + styles[i].length);
				}
				if (start >= length) break;
				if (lastOffset < start) {
					layout.setStyle(null, lastOffset, start - 1);
				}
				layout.setStyle(getStyleRange(styles[i]), start, end);
				lastOffset = Math.max(lastOffset, end);
			}
		}
	}
	if (lastOffset < length) layout.setStyle(null, lastOffset, length);
	if (this.styledText != null && this.styledText.ime != null) {
		IME ime = this.styledText.ime;
		int compositionOffset = ime.getCompositionOffset();
		if (compositionOffset != -1) {
			int commitCount = ime.getCommitCount();
			int compositionLength = ime.getText().length();
			if (compositionLength != commitCount) {
				int compositionLine = this.content.getLineAtOffset(compositionOffset);
				if (compositionLine == lineIndex) {
					int[] imeRanges = ime.getRanges();
					TextStyle[] imeStyles = ime.getStyles();
					if (imeRanges.length > 0) {
						for (int i = 0; i < imeStyles.length; i++) {
							int start = imeRanges[i*2] - lineOffset;
							int end = imeRanges[i*2+1] - lineOffset;
							TextStyle imeStyle = imeStyles[i], userStyle;
							for (int j = start; j <= end; j++) {
								if (!(0 <= j && j < length)) break;
								userStyle = layout.getStyle(j);
								if (userStyle == null && j > 0) userStyle = layout.getStyle(j - 1);
								if (userStyle == null && j + 1 < length) userStyle = layout.getStyle(j + 1);
								if (userStyle == null) {
									layout.setStyle(imeStyle, j, j);
								} else {
									TextStyle newStyle = new TextStyle(imeStyle);
									if (newStyle.font == null) newStyle.font = userStyle.font;
									if (newStyle.foreground == null) newStyle.foreground = userStyle.foreground;
									if (newStyle.background == null) newStyle.background = userStyle.background;
									layout.setStyle(newStyle, j, j);
								}
							}
						}
					} else {
						int start = compositionOffset - lineOffset;
						int end = start + compositionLength - 1;
						TextStyle userStyle = layout.getStyle(start);
						if (userStyle == null) {
							if (start > 0) userStyle = layout.getStyle(start - 1);
							if (userStyle == null && end + 1 < length) userStyle = layout.getStyle(end + 1);
							if (userStyle != null) {
								TextStyle newStyle = new TextStyle();
								newStyle.font = userStyle.font;
								newStyle.foreground = userStyle.foreground;
								newStyle.background = userStyle.background;
								layout.setStyle(newStyle, start, end);
							}
						}
					}
				}
			}
		}
	}
	
	if (this.styledText != null && this.styledText.isFixedLineHeight()) {
		int index = -1;
		int lineCount = layout.getLineCount();
		int height = getLineHeight();
		for (int i = 0; i < lineCount; i++) {
			int lineHeight = layout.getLineBounds(i).height;
			if (lineHeight > height) {
				height = lineHeight;
				index = i;
			}
		}
		if (index != -1) {
			FontMetrics metrics = layout.getLineMetrics(index);
			ascent = metrics.getAscent() + metrics.getLeading();
			descent = metrics.getDescent();
			if (this.layouts != null) {
				for (int i = 0; i < this.layouts.length; i++) {
					if (this.layouts[i] != null && this.layouts[i] != layout) {
						this.layouts[i].setAscent(ascent);
						this.layouts[i].setDescent(descent);
					}
				}
			}
			if (this.styledText.verticalScrollOffset != 0) {
				int topIndex = this.styledText.topIndex;
				int topIndexY = this.styledText.topIndexY;
				int lineHeight = getLineHeight();
				if (topIndexY >= 0) {
					this.styledText.verticalScrollOffset = (topIndex - 1) * lineHeight + lineHeight - topIndexY;
				} else {
					this.styledText.verticalScrollOffset = topIndex * lineHeight - topIndexY;
				}
			}
			this.styledText.calculateScrollBars();
			if (this.styledText.isBidiCaret()) this.styledText.createCaretBitmaps();
			this.styledText.caretDirection = SWT.NULL;
			this.styledText.setCaretLocation();
			this.styledText.redraw();
		}
	}
	return layout;
}
int getWidth() {
	return maxWidth;
}
void reset() {
	if (this.layouts != null) {
		for (int i = 0; i < this.layouts.length; i++) {
			TextLayout layout = this.layouts[i];
			if (layout != null) layout.dispose();
		}
		this.layouts = null;
	}
	this.topIndex = -1;
	this.stylesSetCount = this.styleCount = this.lineCount = 0;
	this.ranges = null;
	this.styles = null;
	this.stylesSet = null;
	this.lines = null;
	this.lineWidth = null;
	this.lineHeight = null;
	this.bullets = null;
	this.bulletsIndices = null;
	this.redrawLines = null;
	hasLinks = false;
}
void reset(int startLine, int lineCount) {
	int endLine = startLine + lineCount;
	if (startLine < 0 || endLine > this.lineWidth.length) return;
	for (int i = startLine; i < endLine; i++) {
		this.lineWidth[i] = -1;
		this.lineHeight[i] = -1;
	}
	if (startLine <= this.maxWidthLineIndex && this.maxWidthLineIndex < endLine) {
		this.maxWidth = 0;
		this.maxWidthLineIndex = -1;
		if (lineCount != this.lineCount) {
			for (int i = 0; i < this.lineCount; i++) {
				if (this.lineWidth[i] > this.maxWidth) {
					this.maxWidth = this.lineWidth[i];
					this.maxWidthLineIndex = i;
				}
			}
		}
	}
}
void setContent(StyledTextContent content) {
	reset();
	this.content = content;
	this.lineCount = content.getLineCount();
	this.lineWidth = new int[this.lineCount];
	this.lineHeight = new int[this.lineCount];
	this.maxWidth = 0;
	this.maxWidthLineIndex = -1;
	reset(0, lineCount);
}
void setFont(Font font, int tabs) {
	TextLayout layout = new TextLayout(device);
	layout.setFont(regularFont);
	if (font != null) {
		if (boldFont != null) boldFont.dispose();
		if (italicFont != null) italicFont.dispose();
		if (boldItalicFont != null) boldItalicFont.dispose();
		boldFont = italicFont = boldItalicFont = null;
		regularFont = font;
		layout.setText("    ");
		layout.setFont(font);
		layout.setStyle(new TextStyle(getFont(SWT.NORMAL), null, null), 0, 0);
		layout.setStyle(new TextStyle(getFont(SWT.BOLD), null, null), 1, 1);
		layout.setStyle(new TextStyle(getFont(SWT.ITALIC), null, null), 2, 2);
		layout.setStyle(new TextStyle(getFont(SWT.BOLD | SWT.ITALIC), null, null), 3, 3);
		FontMetrics metrics = layout.getLineMetrics(0);
		ascent = metrics.getAscent() + metrics.getLeading();
		descent = metrics.getDescent();
		boldFont.dispose();
		italicFont.dispose();
		boldItalicFont.dispose();
		boldFont = italicFont = boldItalicFont = null;
	}
	layout.dispose();
	layout = new TextLayout(this.device);
	layout.setFont(regularFont);
	StringBuffer tabBuffer = new StringBuffer(tabs);
	for (int i = 0; i < tabs; i++) {
		tabBuffer.append(' ');
	}
	layout.setText(tabBuffer.toString());
	this.tabWidth = layout.getBounds().width;
	layout.dispose();
	if (this.styledText != null) {
		GC gc = new GC(this.styledText);
		this.averageCharWidth = gc.getFontMetrics().getAverageCharWidth();
		fixedPitch = gc.stringExtent("l").x == gc.stringExtent("W").x; //$NON-NLS-1$ //$NON-NLS-2$
		gc.dispose();
	}
}
void setLineAlignment(int startLine, int count, int alignment) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= ALIGNMENT;
		this.lines[i].alignment = alignment;
	}
}
void setLineBackground(int startLine, int count, Color background) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= BACKGROUND;
		this.lines[i].background = background;
	}
}
void setLineBullet(int startLine, int count, Bullet bullet) {
	if (this.bulletsIndices != null) {
		this.bulletsIndices = null;
		this.bullets = null;
	}
	if (this.bullets == null) {
		if (bullet == null) return;
		this.bullets = new Bullet[1];
		this.bullets[0] = bullet;
	}
	int index = 0;
	while (index < this.bullets.length) {
		if (bullet == this.bullets[index]) break;
		index++;
	}
	if (bullet != null) {
		if (index == this.bullets.length) {
			Bullet[] newBulletsList = new Bullet[this.bullets.length + 1];
			System.arraycopy(bullets, 0, newBulletsList, 0, bullets.length);
			newBulletsList[index] = bullet;
			bullets = newBulletsList;
		}
		bullet.addIndices(startLine, count);
	} else {
		updateBullets(startLine, count, 0, false);
		this.styledText.redrawLinesBullet(this.redrawLines);
		this.redrawLines = null;
	}
}
void setLineIndent(int startLine, int count, int indent) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= INDENT;
		this.lines[i].indent = indent;
	}
}
void setLineWrapIndent(int startLine, int count, int wrapIndent) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= WRAP_INDENT;
		this.lines[i].wrapIndent = wrapIndent;
	}
}
void setLineJustify(int startLine, int count, boolean justify) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= JUSTIFY;
		this.lines[i].justify = justify;
	}
}
void setLineSegments(int startLine, int count, int[] segments) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= SEGMENTS;
		this.lines[i].segments = segments;
	}
}
void setLineSegmentChars(int startLine, int count, char[] segmentChars) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= SEGMENT_CHARS;
		this.lines[i].segmentsChars = segmentChars;
	}
}
void setLineTabStops(int startLine, int count, int[] tabStops) {
	if (this.lines == null) this.lines = new LineInfo[this.lineCount];
	for (int i = startLine; i < startLine + count; i++) {
		if (this.lines[i] == null) {
			this.lines[i] = new LineInfo();
		}
		this.lines[i].flags |= TABSTOPS;
		this.lines[i].tabStops = tabStops;
	}
}
void setStyleRanges (int[] newRanges, StyleRange[] newStyles) {
	if (newStyles == null) {
		this.stylesSetCount = this.styleCount = 0;
		this.ranges = null;
		this.styles = null;
		this.stylesSet = null;
		hasLinks = false;
		return;
	}
	if (newRanges == null && COMPACT_STYLES) {
		newRanges = new int[newStyles.length << 1];		
		StyleRange[] tmpStyles = new StyleRange[newStyles.length];
		if (this.stylesSet == null) this.stylesSet = new StyleRange[4];
		for (int i = 0, j = 0; i < newStyles.length; i++) {
			StyleRange newStyle = newStyles[i];
			newRanges[j++] = newStyle.start;
			newRanges[j++] = newStyle.length;
			int index = 0;
			while (index < this.stylesSetCount) {
				if (this.stylesSet[index].similarTo(newStyle)) break;
				index++;
			}
			if (index == this.stylesSetCount) {
				if (this.stylesSetCount == this.stylesSet.length) {
					StyleRange[] tmpStylesSet = new StyleRange[this.stylesSetCount + 4];
					System.arraycopy(this.stylesSet, 0, tmpStylesSet, 0, stylesSetCount);
					this.stylesSet = tmpStylesSet;
				}
				this.stylesSet[stylesSetCount++] = newStyle;
			}
			tmpStyles[i] = this.stylesSet[index];
		}
		newStyles = tmpStyles;
	}
	
	if (this.styleCount == 0) {
		if (newRanges != null) {
			this.ranges = new int[newRanges.length];
			System.arraycopy(newRanges, 0, ranges, 0, ranges.length);
		}
		this.styles = new StyleRange[newStyles.length];
		System.arraycopy(newStyles, 0, styles, 0, styles.length);
		this.styleCount = newStyles.length;
		return;
	}
	if (newRanges != null && ranges == null) {
		ranges = new int[styles.length << 1];
		for (int i = 0, j = 0; i < this.styleCount; i++) {
			ranges[j++] = styles[i].start;
			ranges[j++] = styles[i].length;
		}
	}
	if (newRanges == null && ranges != null) {
		newRanges = new int[newStyles.length << 1];
		for (int i = 0, j = 0; i < newStyles.length; i++) {
			newRanges[j++] = newStyles[i].start;
			newRanges[j++] = newStyles[i].length;
		}
	}
	if (ranges != null) {
		int rangeCount = this.styleCount << 1;
		int start = newRanges[0];
		int modifyStart = getRangeIndex(start, -1, rangeCount), modifyEnd;
		boolean insert = modifyStart == rangeCount;
		if (!insert) {
			int end = newRanges[newRanges.length - 2] + newRanges[newRanges.length - 1];
			modifyEnd = getRangeIndex(end, modifyStart - 1, rangeCount);
			insert = modifyStart == modifyEnd && ranges[modifyStart] >= end;
		}
		if (insert) {
			addMerge(newRanges, newStyles, newRanges.length, modifyStart, modifyStart);
			return;
		}
		modifyEnd = modifyStart;
		int[] mergeRanges = new int[6];
		StyleRange[] mergeStyles = new StyleRange[3];
		for (int i = 0; i < newRanges.length; i += 2) {
			int newStart = newRanges[i];
			int newEnd = newStart + newRanges[i + 1];
			if (newStart == newEnd) continue;
			int modifyLast = 0, mergeCount = 0;
			while (modifyEnd < rangeCount) {
				if (newStart >= ranges[modifyStart] + ranges[modifyStart + 1]) modifyStart += 2;
				if (ranges[modifyEnd] + ranges[modifyEnd + 1] > newEnd) break;
				modifyEnd += 2;
			}
			if (ranges[modifyStart] < newStart && newStart < ranges[modifyStart] + ranges[modifyStart + 1]) {
				mergeStyles[mergeCount >> 1] = styles[modifyStart >> 1];
				mergeRanges[mergeCount] = ranges[modifyStart];
				mergeRanges[mergeCount + 1] = newStart - ranges[modifyStart];				
				mergeCount += 2;
			}
			mergeStyles[mergeCount >> 1] = newStyles[i >> 1];
			mergeRanges[mergeCount] = newStart;
			mergeRanges[mergeCount + 1] = newRanges[i + 1];
			mergeCount += 2;
			if (modifyEnd < rangeCount && ranges[modifyEnd] < newEnd && newEnd < ranges[modifyEnd] + ranges[modifyEnd + 1]) {
				mergeStyles[mergeCount >> 1] = styles[modifyEnd >> 1];
				mergeRanges[mergeCount] = newEnd;
				mergeRanges[mergeCount + 1] = ranges[modifyEnd] + ranges[modifyEnd + 1] - newEnd;
				mergeCount += 2;
				modifyLast = 2;
			}
			int grow = addMerge(mergeRanges, mergeStyles, mergeCount, modifyStart, modifyEnd + modifyLast);
			rangeCount += grow;
			modifyStart = modifyEnd += grow;
		}
	} else {
		int start = newStyles[0].start;
		int modifyStart = getRangeIndex(start, -1, styleCount), modifyEnd;
		boolean insert = modifyStart == styleCount;
		if (!insert) {
			int end = newStyles[newStyles.length - 1].start + newStyles[newStyles.length - 1].length;
			modifyEnd = getRangeIndex(end, modifyStart - 1, styleCount);
			insert = modifyStart == modifyEnd && styles[modifyStart].start >= end;
		}
		if (insert) {
			addMerge(newStyles, newStyles.length, modifyStart, modifyStart);
			return;
		}
		modifyEnd = modifyStart;
		StyleRange[] mergeStyles = new StyleRange[3];
		for (int i = 0; i < newStyles.length; i++) {
			StyleRange newStyle = newStyles[i], style; 
			int newStart = newStyle.start;
			int newEnd = newStart + newStyle.length;
			if (newStart == newEnd) continue;
			int modifyLast = 0, mergeCount = 0;
			while (modifyEnd < styleCount) {
				if (newStart >= styles[modifyStart].start + styles[modifyStart].length) modifyStart++;
				if (styles[modifyEnd].start + styles[modifyEnd].length > newEnd) break;
				modifyEnd++;
			}
			style = styles[modifyStart];
			if (style.start < newStart && newStart < style.start + style.length) {
				style = mergeStyles[mergeCount++] = (StyleRange)style.clone();
				style.length = newStart - style.start;
			}
			mergeStyles[mergeCount++] = newStyle;
			if (modifyEnd < styleCount) {
				style = styles[modifyEnd];
				if (style.start < newEnd && newEnd < style.start + style.length) {
					style = mergeStyles[mergeCount++] = (StyleRange)style.clone();
					style.length += style.start - newEnd;
					style.start = newEnd;
					modifyLast = 1;
				}
			}
			int grow = addMerge(mergeStyles, mergeCount, modifyStart, modifyEnd + modifyLast);
			modifyStart = modifyEnd += grow;
		}
	}
}
void textChanging(TextChangingEvent event) {
	int start = event.start;
	int newCharCount = event.newCharCount, replaceCharCount = event.replaceCharCount;
	int newLineCount = event.newLineCount, replaceLineCount = event.replaceLineCount;
	
	updateRanges(start, replaceCharCount, newCharCount);	
	
	int startLine = this.content.getLineAtOffset(start);
	if (replaceCharCount == this.content.getCharCount()) this.lines = null;
	if (replaceLineCount == this.lineCount) {
		this.lineCount = newLineCount;
		this.lineWidth = new int[this.lineCount];
		this.lineHeight = new int[this.lineCount];
		reset(0, lineCount);
	} else {
		int delta = newLineCount - replaceLineCount;
		if (lineCount + delta > this.lineWidth.length) {
			int[] newWidths = new int[lineCount + delta + GROW];
			System.arraycopy(this.lineWidth, 0, newWidths, 0, lineCount);
			this.lineWidth = newWidths;			
			int[] newHeights = new int[lineCount + delta + GROW];
			System.arraycopy(this.lineHeight, 0, newHeights, 0, lineCount);
			this.lineHeight = newHeights;
		}
		if (this.lines != null) {
			if (lineCount + delta > this.lines.length) {
				LineInfo[] newLines = new LineInfo[lineCount + delta + GROW];
				System.arraycopy(this.lines, 0, newLines, 0, lineCount);
				this.lines = newLines;
			}
		}
		int startIndex = startLine + replaceLineCount + 1;
		int endIndex = startLine + newLineCount + 1;
		System.arraycopy(lineWidth, startIndex, lineWidth, endIndex, lineCount - startIndex);
		System.arraycopy(lineHeight, startIndex, lineHeight, endIndex, lineCount - startIndex);
		for (int i = startLine; i < endIndex; i++) {
			lineWidth[i] = lineHeight[i] = -1;
		}
		for (int i = lineCount + delta; i < lineCount; i++) {
			lineWidth[i] = lineHeight[i] = -1;
		}
		if (this.layouts != null) {
			int layoutStartLine = startLine - this.topIndex;
			int layoutEndLine = layoutStartLine + replaceLineCount + 1;
			for (int i = layoutStartLine; i < layoutEndLine; i++) {
				if (0 <= i && i < this.layouts.length) {
					if (this.layouts[i] != null) this.layouts[i].dispose();
					this.layouts[i] = null;
					if (this.bullets != null && this.bulletsIndices != null) this.bullets[i] = null;
				}
			}
			if (delta > 0) {
				for (int i = this.layouts.length - 1; i >= layoutEndLine; i--) {
					if (0 <= i && i < this.layouts.length) {
						endIndex = i + delta;
						if (0 <= endIndex && endIndex < this.layouts.length) {
							this.layouts[endIndex] = this.layouts[i];
							this.layouts[i] = null;
							if (this.bullets != null && this.bulletsIndices != null) {
								this.bullets[endIndex] = this.bullets[i];
								this.bulletsIndices[endIndex] = this.bulletsIndices[i];
								this.bullets[i] = null;
							}
						} else {
							if (this.layouts[i] != null) this.layouts[i].dispose();
							this.layouts[i] = null;
							if (this.bullets != null && this.bulletsIndices != null) this.bullets[i] = null;
						}
					}
				}
			} else if (delta < 0) {
				for (int i = layoutEndLine; i < this.layouts.length; i++) {
					if (0 <= i && i < this.layouts.length) {
						endIndex = i + delta;
						if (0 <= endIndex && endIndex < this.layouts.length) {
							this.layouts[endIndex] = this.layouts[i];
							this.layouts[i] = null;
							if (this.bullets != null && this.bulletsIndices != null) {
								this.bullets[endIndex] = this.bullets[i];
								this.bulletsIndices[endIndex] = this.bulletsIndices[i];
								this.bullets[i] = null;
							}
						} else {
							if (this.layouts[i] != null) this.layouts[i].dispose();
							this.layouts[i] = null;
							if (this.bullets != null && this.bulletsIndices != null) this.bullets[i] = null;
						}
					}
				}
			}
		}
		if (replaceLineCount != 0 || newLineCount != 0) {
			int startLineOffset = this.content.getOffsetAtLine(startLine);
			if (startLineOffset != start) startLine++;
			updateBullets(startLine, replaceLineCount, newLineCount, true);
			if (this.lines != null) {
				startIndex = startLine + replaceLineCount;
				endIndex = startLine + newLineCount;
				System.arraycopy(lines, startIndex, lines, endIndex, lineCount - startIndex);
				for (int i = startLine; i < endIndex; i++) {
					lines[i] = null;
				}
				for (int i = lineCount + delta; i < lineCount; i++) {
					lines[i] = null;
				}
			}
		}
		lineCount += delta;
		if (this.maxWidthLineIndex != -1 && startLine <= this.maxWidthLineIndex && this.maxWidthLineIndex <= startLine + replaceLineCount) {
			this.maxWidth = 0;
			this.maxWidthLineIndex = -1;
			for (int i = 0; i < lineCount; i++) {
				if (lineWidth[i] > this.maxWidth) {
					this.maxWidth = lineWidth[i];
					this.maxWidthLineIndex = i;
				}
			}
		}
	}
}
void updateBullets(int startLine, int replaceLineCount, int newLineCount, boolean update) {
	if (this.bullets == null) return;
	if (this.bulletsIndices != null) return;
	for (int i = 0; i < this.bullets.length; i++) {
		Bullet bullet = this.bullets[i];
		int[] lines = bullet.removeIndices(startLine, replaceLineCount, newLineCount, update);
		if (lines != null) {
			if (this.redrawLines == null) {
				this.redrawLines = lines;
			} else {
				int[] newRedrawBullets = new int[this.redrawLines.length + lines.length];
				System.arraycopy(redrawLines, 0, newRedrawBullets, 0, redrawLines.length);
				System.arraycopy(lines, 0, newRedrawBullets, redrawLines.length, lines.length);
				redrawLines = newRedrawBullets;
			}
		}
	}
	int removed = 0;
	for (int i = 0; i < this.bullets.length; i++) {
		if (this.bullets[i].size() == 0) removed++;
	}
	if (removed > 0) {
		if (removed == this.bullets.length) {
			this.bullets = null;
		} else {
			Bullet[] newBulletsList = new Bullet[this.bullets.length - removed];
			for (int i = 0, j = 0; i < this.bullets.length; i++) {
				Bullet bullet = this.bullets[i];
				if (bullet.size() > 0) newBulletsList[j++] = bullet;
			}
			this.bullets = newBulletsList;
		}
	}
}
void updateRanges(int start, int replaceCharCount, int newCharCount) {
	if (this.styleCount == 0 || (replaceCharCount == 0 && newCharCount == 0)) return;
	if (this.ranges != null) {
		int rangeCount = this.styleCount << 1;
		int modifyStart = getRangeIndex(start, -1, rangeCount);
		if (modifyStart == rangeCount) return;
		int end = start + replaceCharCount;
		int modifyEnd = getRangeIndex(end, modifyStart - 1, rangeCount);
		int offset = newCharCount - replaceCharCount;
		if (modifyStart == modifyEnd && this.ranges[modifyStart] < start && end < this.ranges[modifyEnd] + this.ranges[modifyEnd + 1]) {
			if (newCharCount == 0) {
				this.ranges[modifyStart + 1] -= replaceCharCount;
				modifyEnd += 2;
			} else {
				if (rangeCount + 2 > this.ranges.length) {
					int[] newRanges = new int[this.ranges.length + (GROW << 1)];
					System.arraycopy(this.ranges, 0, newRanges, 0, rangeCount);
					this.ranges = newRanges;
					StyleRange[] newStyles = new StyleRange[this.styles.length + GROW];
					System.arraycopy(this.styles, 0, newStyles, 0, styleCount);
					this.styles = newStyles;
				}
				System.arraycopy(ranges, modifyStart + 2, ranges, modifyStart + 4, rangeCount - (modifyStart + 2));
				System.arraycopy(styles, (modifyStart + 2) >> 1, styles, (modifyStart + 4) >> 1, styleCount - ((modifyStart + 2) >> 1));
				ranges[modifyStart + 3] = ranges[modifyStart] + ranges[modifyStart + 1] - end;
				ranges[modifyStart + 2] = start + newCharCount;
				ranges[modifyStart + 1] = start - ranges[modifyStart];
				styles[(modifyStart >> 1) + 1] = styles[modifyStart >> 1]; 
				rangeCount += 2;
				styleCount++;
				modifyEnd += 4;
			}
			if (offset != 0) {
				for (int i = modifyEnd; i < rangeCount; i += 2) {
					ranges[i] += offset;
				}
			}
		} else {
			if (ranges[modifyStart] < start && start < ranges[modifyStart] + ranges[modifyStart + 1]) {
				ranges[modifyStart + 1] = start - ranges[modifyStart];
				modifyStart += 2;
			}
			if (modifyEnd < rangeCount && ranges[modifyEnd] < end && end < ranges[modifyEnd] + ranges[modifyEnd + 1]) {
				ranges[modifyEnd + 1] = ranges[modifyEnd] + ranges[modifyEnd + 1] - end;
				ranges[modifyEnd] = end;
			}
			if (offset != 0) {
				for (int i = modifyEnd; i < rangeCount; i += 2) {
					ranges[i] += offset;
				}
			}
			System.arraycopy(ranges, modifyEnd, ranges, modifyStart, rangeCount - modifyEnd);
			System.arraycopy(styles, modifyEnd >> 1, styles, modifyStart >> 1, styleCount - (modifyEnd >> 1));
			styleCount -= (modifyEnd - modifyStart) >> 1;
		}
	} else {
		int modifyStart = getRangeIndex(start, -1, styleCount);
		if (modifyStart == styleCount) return;
		int end = start + replaceCharCount;
		int modifyEnd = getRangeIndex(end, modifyStart - 1, styleCount);
		int offset = newCharCount - replaceCharCount;
		if (modifyStart == modifyEnd && styles[modifyStart].start < start && end < styles[modifyEnd].start + styles[modifyEnd].length) {
			if (newCharCount == 0) {
				styles[modifyStart].length -= replaceCharCount;
				modifyEnd++;
			} else {
				if (styleCount + 1 > styles.length) {
					StyleRange[] newStyles = new StyleRange[styles.length + GROW];
					System.arraycopy(styles, 0, newStyles, 0, styleCount);
					styles = newStyles;
				}
				System.arraycopy(styles, modifyStart + 1, styles, modifyStart + 2, styleCount - (modifyStart + 1));
				styles[modifyStart + 1] = (StyleRange)styles[modifyStart].clone();
				styles[modifyStart + 1].length = styles[modifyStart].start + styles[modifyStart].length - end;
				styles[modifyStart + 1].start = start + newCharCount;
				styles[modifyStart].length = start - styles[modifyStart].start;
				styleCount++;
				modifyEnd += 2;
			}
			if (offset != 0) {
				for (int i = modifyEnd; i < styleCount; i++) {
					styles[i].start += offset;
				}
			}
		} else {
			if (styles[modifyStart].start < start && start < styles[modifyStart].start + styles[modifyStart].length) {
				styles[modifyStart].length = start - styles[modifyStart].start;
				modifyStart++;
			}
			if (modifyEnd < styleCount && styles[modifyEnd].start < end && end < styles[modifyEnd].start + styles[modifyEnd].length) {
				styles[modifyEnd].length = styles[modifyEnd].start + styles[modifyEnd].length - end;
				styles[modifyEnd].start = end;
			}
			if (offset != 0) {
				for (int i = modifyEnd; i < styleCount; i++) {
					styles[i].start += offset;
				}
			}
			System.arraycopy(styles, modifyEnd, styles, modifyStart, styleCount - modifyEnd);
			styleCount -= modifyEnd - modifyStart;
		}
	}
}
}
