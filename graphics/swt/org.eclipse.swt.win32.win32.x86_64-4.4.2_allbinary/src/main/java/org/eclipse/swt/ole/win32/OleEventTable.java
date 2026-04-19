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
package org.eclipse.swt.ole.win32;


/**
* The OleEventTable class implements a simple
* look up mechanism that maps an event type
* to a listener.  Multiple listeners for the
* same event type are supported.
*
*/

class OleEventTable {
	int [] types;
	OleListener [] handlers;
void hook (int eventType, OleListener handler) {
	if (this.types == null) this.types = new int [4];
	if (this.handlers == null) this.handlers = new OleListener [4];
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == 0) {
			this.types [i] = eventType;
			this.handlers [i] = handler;
			return;
		}
	}
	int size = this.types.length;
	int [] newTypes = new int [size + 4];
	OleListener [] newHandlers = new OleListener [size + 4];
	System.arraycopy (this.types, 0, newTypes, 0, size);
	System.arraycopy (this.handlers, 0, newHandlers, 0, size);
	this.types = newTypes;  this.handlers = newHandlers;
	this.types [size] = eventType;  this.handlers [size] = handler;
}
boolean hooks (int eventType) {
	if (this.handlers == null) return false;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == eventType) return true;
	}
	return false;
}
void sendEvent (OleEvent event) {
	if (this.handlers == null) return;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == event.type) {
			OleListener listener = this.handlers [i];
			if (listener != null) listener.handleEvent (event);
		}
	}
}
void unhook (int eventType, OleListener handler) {
	if (this.handlers == null) return;
	for (int i=0; i<this.types.length; i++) {
		if ((this.types [i] == eventType) && (this.handlers [i] == handler)) {
			this.types [i] = 0;
			this.handlers [i] = null;
			return;
		}
	}
}
boolean hasEntries() {
	for (int i=0; i<this.types.length; i++) {
		if (this.types[i] != 0) return true;
	}
	return false;
}
}
