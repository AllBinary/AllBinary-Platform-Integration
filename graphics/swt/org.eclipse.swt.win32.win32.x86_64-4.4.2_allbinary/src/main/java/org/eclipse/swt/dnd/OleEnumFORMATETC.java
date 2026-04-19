/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.internal.ole.win32.*;

final class OleEnumFORMATETC {

	private COMObject iUnknown;
	private COMObject iEnumFORMATETC;
	
	private int refCount;
	private int index;
	
	private FORMATETC[] formats;
	
OleEnumFORMATETC() {
	
	createCOMInterfaces();

}
int AddRef() {
	refCount++;
	return refCount;
}
private void createCOMInterfaces() {
	// register each of the interfaces that this object implements
	iUnknown = new COMObject(new int[] {2, 0, 0}){
		public long /*int*/ method0(long /*int*/[] args) {return QueryInterface(args[0], args[1]);}
		public long /*int*/ method1(long /*int*/[] args) {return AddRef();}
		public long /*int*/ method2(long /*int*/[] args) {return Release();}
	};
	iEnumFORMATETC = new COMObject(new int[] {2, 0, 0, 3, 1, 0, 1}){
		public long /*int*/ method0(long /*int*/[] args) {return QueryInterface(args[0], args[1]);}
		public long /*int*/ method1(long /*int*/[] args) {return AddRef();}
		public long /*int*/ method2(long /*int*/[] args) {return Release();}
		public long /*int*/ method3(long /*int*/[] args) {return Next((int)/*64*/args[0], args[1], args[2]);}
		public long /*int*/ method4(long /*int*/[] args) {return Skip((int)/*64*/args[0]);}
		public long /*int*/ method5(long /*int*/[] args) {return Reset();}
		// method6 Clone - not implemented
	};
}
private void disposeCOMInterfaces() {
	
	if (this.iUnknown != null)
		this.iUnknown.dispose();
	this.iUnknown = null;
	
	if (this.iEnumFORMATETC != null)
		this.iEnumFORMATETC.dispose();
	this.iEnumFORMATETC = null;
}
long /*int*/ getAddress() {
	return iEnumFORMATETC.getAddress();
}
private FORMATETC[] getNextItems(int numItems){

	if (this.formats == null || numItems < 1) return null;

	int endIndex = this.index + numItems - 1;
	if (endIndex > (this.formats.length - 1)) endIndex = this.formats.length - 1;
	if (this.index > endIndex) return null;
	
	FORMATETC[] items =  new FORMATETC[endIndex - this.index + 1];
	for (int i = 0; i < items.length; i++){
		items[i] = this.formats[this.index];
		index++;
	}

	return items;
}
private int Next(int celt, long /*int*/ rgelt, long /*int*/ pceltFetched) {
	/* Retrieves the next celt items in the enumeration sequence. 
	   If there are fewer than the requested number of elements left in the sequence, 
	   it retrieves the remaining elements. 
	   The number of elements actually retrieved is returned through pceltFetched 
	   (unless the caller passed in NULL for that parameter).
	*/

	if (rgelt == 0)	return COM.E_INVALIDARG;
	if (pceltFetched == 0 && celt != 1) return COM.E_INVALIDARG;
		
	FORMATETC[] nextItems = getNextItems(celt);
	if (nextItems != null) {
		for (int i = 0; i < nextItems.length; i++) {
			COM.MoveMemory(rgelt + i*FORMATETC.sizeof, nextItems[i], FORMATETC.sizeof);
		}
		
		if (pceltFetched != 0)
			COM.MoveMemory(pceltFetched, new int[] {nextItems.length}, 4);
			
		if (nextItems.length == celt) return COM.S_OK;
			
	} else {
		if (pceltFetched != 0)
			COM.MoveMemory(pceltFetched, new int[] {0}, 4);
		COM.MoveMemory(rgelt, new FORMATETC(), FORMATETC.sizeof);
			
	}
	return COM.S_FALSE;
}
private int QueryInterface(long /*int*/ riid, long /*int*/ ppvObject) {
	
	if (riid == 0 || ppvObject == 0) return COM.E_NOINTERFACE;
	
	GUID guid = new GUID();
	COM.MoveMemory(guid, riid, GUID.sizeof);

	if (COM.IsEqualGUID(guid, COM.IIDIUnknown)) {
		COM.MoveMemory(ppvObject, new long /*int*/[] {this.iUnknown.getAddress()}, OS.PTR_SIZEOF);
		AddRef();
		return COM.S_OK;
	}
	if (COM.IsEqualGUID(guid, COM.IIDIEnumFORMATETC)) {
		COM.MoveMemory(ppvObject, new long /*int*/[] {this.iEnumFORMATETC.getAddress()}, OS.PTR_SIZEOF);
		AddRef();
		return COM.S_OK;
	}
	COM.MoveMemory(ppvObject, new long /*int*/[] {0}, OS.PTR_SIZEOF);
	return COM.E_NOINTERFACE;
}
int Release() {
	refCount--;
	
	if (this.refCount == 0) {
		disposeCOMInterfaces();
		if (COM.FreeUnusedLibraries) {
			COM.CoFreeUnusedLibraries();
		}
	}
	
	return refCount;
}
private int Reset() {
	//Resets the enumeration sequence to the beginning.
	this.index = 0;
	return COM.S_OK;
}
void setFormats(FORMATETC[] newFormats) {
	this.formats = newFormats;
	this.index = 0;
}
private int Skip(int celt) {
	//Skips over the next specified number of elements in the enumeration sequence.
	if (celt < 1 ) return COM.E_INVALIDARG;
	
	this.index += celt;
	if (this.index > (this.formats.length - 1)){
		this.index = this.formats.length - 1;
		return COM.S_FALSE;
	}
	return COM.S_OK;
}
}
