/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.image;

import java.io.*;

public class PngLzBlockReader {
	boolean isLastBlock;
	byte compressionType;
	int uncompressedBytesRemaining;
	PngDecodingDataStream stream;
	PngHuffmanTables huffmanTables;
	
	byte[] window;
	int windowIndex;
	int copyIndex;
	int copyBytesRemaining;
	
	static final int UNCOMPRESSED = 0;
	static final int COMPRESSED_FIXED = 1;
	static final int COMPRESSED_DYNAMIC = 2;

	static final int END_OF_COMPRESSED_BLOCK = 256;
	static final int FIRST_LENGTH_CODE = 257;
	static final int LAST_LENGTH_CODE = 285;
	static final int FIRST_DISTANCE_CODE = 1;
	static final int LAST_DISTANCE_CODE = 29;
	static final int FIRST_CODE_LENGTH_CODE = 4;
	static final int LAST_CODE_LENGTH_CODE = 19;

	static final int[] lengthBases = {
		3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 
		31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258
	} ;	
	static final int[] extraLengthBits = {
		0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 
		3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0,
	};
	static final int[] distanceBases = {
		1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129,
		193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097,
		6145, 8193, 12289, 16385, 24577,
	};
	static final int[] extraDistanceBits = {
		0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7,  7,
		8,  8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13,
	};	


PngLzBlockReader(PngDecodingDataStream stream) {
	this.stream = stream;
	this.isLastBlock = false;
}

void setWindowSize(int windowSize) {
	this.window = new byte[windowSize];
}	

void readNextBlockHeader() throws IOException {
	isLastBlock = stream.getNextIdatBit() != 0;
	this.compressionType = (byte) this.stream.getNextIdatBits(2);
	if (this.compressionType > 2) this.stream.error();	
	
	if (this.compressionType == UNCOMPRESSED) {
		byte b1 = this.stream.getNextIdatByte();
		byte b2 = this.stream.getNextIdatByte();
		byte b3 = this.stream.getNextIdatByte();
		byte b4 = this.stream.getNextIdatByte();
		if (b1 != ~b3 || b2 != ~b4) this.stream.error();
		this.uncompressedBytesRemaining = (b1 & 0xFF) | ((b2 & 0xFF) << 8);
	} else if (compressionType == COMPRESSED_DYNAMIC) {
		huffmanTables = PngHuffmanTables.getDynamicTables(stream);
	} else {
		this.huffmanTables = PngHuffmanTables.getFixedTables();
	}
}

byte getNextByte() throws IOException {
	if (this.compressionType == UNCOMPRESSED) {
		if (this.uncompressedBytesRemaining == 0) {
			readNextBlockHeader();
			return getNextByte();
		}
		uncompressedBytesRemaining--;
		return stream.getNextIdatByte();
	} else {
		return getNextCompressedByte();
	}
}

private void assertBlockAtEnd() throws IOException {
	if (this.compressionType == UNCOMPRESSED) {
		if (this.uncompressedBytesRemaining > 0) this.stream.error();
	} else if (copyBytesRemaining > 0 ||
		(huffmanTables.getNextLiteralValue(stream) != END_OF_COMPRESSED_BLOCK)) 
	{
		this.stream.error();		
	}
}
void assertCompressedDataAtEnd() throws IOException {
	assertBlockAtEnd();		
	while (!this.isLastBlock) {
		readNextBlockHeader();
		assertBlockAtEnd();
	}	
}

private byte getNextCompressedByte() throws IOException {
	if (this.copyBytesRemaining > 0) {
		byte value = this.window[this.copyIndex];
		this.window[this.windowIndex] = value;
		copyBytesRemaining--;
		
		copyIndex++;
		windowIndex++;		
		if (this.copyIndex == this.window.length) this.copyIndex = 0;
		if (this.windowIndex == this.window.length) this.windowIndex = 0;

		return value;		
	}
	
	int value = this.huffmanTables.getNextLiteralValue(this.stream);
	if (value < END_OF_COMPRESSED_BLOCK) {
		this.window[this.windowIndex] = (byte) value;
		windowIndex++;
		if (this.windowIndex >= this.window.length) this.windowIndex = 0;
		return (byte) value;		
	} else if (value == END_OF_COMPRESSED_BLOCK) {
		readNextBlockHeader();
		return getNextByte();
	} else if (value <= LAST_LENGTH_CODE) {
		int extraBits = extraLengthBits[value - FIRST_LENGTH_CODE];
		int length = lengthBases[value - FIRST_LENGTH_CODE];
		if (extraBits > 0) {
			length += this.stream.getNextIdatBits(extraBits);
		}
		
		value = this.huffmanTables.getNextDistanceValue(this.stream);
		if (value > LAST_DISTANCE_CODE) this.stream.error();
		extraBits = extraDistanceBits[value];
		int distance = distanceBases[value];
		if (extraBits > 0) {
			distance += this.stream.getNextIdatBits(extraBits);
		}
		
		this.copyIndex = this.windowIndex - distance;
		if (this.copyIndex < 0) this.copyIndex += this.window.length;

		this.copyBytesRemaining = length;
		return getNextCompressedByte();
	} else {
		this.stream.error();
		return 0;
	}
}
	
}
