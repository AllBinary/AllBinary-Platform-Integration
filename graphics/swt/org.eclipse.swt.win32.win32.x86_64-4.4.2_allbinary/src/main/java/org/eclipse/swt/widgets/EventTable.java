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
package org.eclipse.swt.widgets;


//import org.allbinary.AvianUtil;
import org.eclipse.swt.*;
import org.eclipse.swt.internal.SWTEventListener;

/**
 * Instances of this class implement a simple
 * look up mechanism that maps an event type
 * to a listener.  Multiple listeners for the
 * same event type are supported.
 */

class EventTable {
	int [] types;
	Listener [] listeners;
	int level;
	static final int GROW_SIZE = 4;
	
public Listener [] getListeners (int eventType) {
	if (this.types == null) return new Listener [0];
	int count = 0;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == eventType) count++;
	}
	if (count == 0) return new Listener [0];
	Listener [] result = new Listener [count];
	count = 0;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == eventType) {
			result [count++] = this.listeners [i];
		}
	}
	return result;
}

public void hook (int eventType, Listener listener) {
	if (this.types == null) this.types = new int [GROW_SIZE];
	if (this.listeners == null) this.listeners = new Listener [GROW_SIZE];
	int length = this.types.length, index = length - 1;
	while (index >= 0) {
		if (this.types [index] != 0) break;
		--index;
	}
	index++;
	if (index == length) {
		int [] newTypes = new int [length + GROW_SIZE];
		System.arraycopy (this.types, 0, newTypes, 0, length);
		this.types = newTypes;
		Listener [] newListeners = new Listener [length + GROW_SIZE];
		System.arraycopy (this.listeners, 0, newListeners, 0, length);
		this.listeners = newListeners;
	}
	this.types [index] = eventType;
	this.listeners [index] = listener;
}

public boolean hooks (int eventType) {
	if (this.types == null) return false;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == eventType) return true;
	}
	return false;
}

private boolean[] duplicateArray = new boolean[6];

//final String UPDATE_DISPLAYETDSE = "Thread - readAndDispatchETDSE::";
//final String UPDATE_DISPLAYETDSE2 = "Thread - readAndDispatchETDSE2:";
//final String UPDATE_DISPLAYETDSE3 = "Thread - readAndDispatchETDSE3:";
//final String UPDATE_DISPLAYETDSE4 = "Thread - readAndDispatchETDSE4:";
//final String UPDATE_DISPLAYETDSE5 = "Thread - readAndDispatchETDSE5:";
//public void sendEvent (Event event, long aLastTime) {
public void sendEvent (Event event) {
	if (this.types == null) return;

//        long currentTime;
//        currentTime = System.currentTimeMillis();
//        //if(currentTime - aLastTime > 2000) {
//        if(aLastTime == Long.MAX_VALUE) {
//            System.out.println(UPDATE_DISPLAYETDSE + currentTime);
//        }
//        aLastTime = currentTime;
        
//        if(AvianUtil.isAvian()) {
//            if (SWT.MouseDown == event.type) {
//                currentTime = System.currentTimeMillis();
//                System.out.println("wmLButtonDown" + event.button + currentTime);
//            }
//        } else {
            //After adding dragged mouse event this does not seem to be a problem.
//            if (SWT.MouseDown == event.type) {
//                if (Widget.isMouseButtonDown[event.button - 1] != 1) {
//                    //System.out.println("wmLButtonDown:sendEvent:(duplicate): " + event.button); 
//                    duplicateArray[event.button - 1] = true;
//                    return;
//                } else {
//                    //System.out.println("wmLButtonDown:sendEvent: button: " + event.button); 
//                }
//            } else if (SWT.MouseUp == event.type) {
//                if (duplicateArray[event.button - 1]) {
//                    System.out.println("wmLButtonUp:sendEvent:(duplicate): " + event.button);
//                    duplicateArray[event.button - 1] = false;
//                    return;
//                } else {
//                    //System.out.println("wmLButtonUp:sendEvent: button: " + event.button); 
//                }
//            }
//        }

//        currentTime = System.currentTimeMillis();
////        if(currentTime - aLastTime > 2000) {
//            System.out.println(UPDATE_DISPLAYETDSE2 + currentTime);
////        }
//        aLastTime = currentTime;
        
	this.level += this.level >= 0 ? 1 : -1;
	try {
		for (int i=0; i<this.types.length; i++) {
			if (event.type == SWT.None) return;
			if (this.types [i] == event.type) {
				Listener listener = this.listeners [i];
				if (listener != null) {
                                    
//        currentTime = System.currentTimeMillis();
////        if(currentTime - aLastTime > 2000) {
//            System.out.println(UPDATE_DISPLAYETDSE3 + currentTime + listener);
////        }
//        aLastTime = currentTime;
                                    
                                    listener.handleEvent (event);
                                }
			}
		}
	} finally {
            
//        currentTime = System.currentTimeMillis();
//        if(currentTime - aLastTime > 2000) {
//            System.out.println(UPDATE_DISPLAYETDSE4 + currentTime);
//        }
//        aLastTime = currentTime;
            
		boolean compact = this.level < 0;
		this.level -= this.level >= 0 ? 1 : -1;
		if (compact && this.level == 0) {
			int index = 0;
			for (int i=0; i<this.types.length; i++) {
				if (this.types [i] != 0) {
					this.types [index] = this.types [i];
					this.listeners [index] = this.listeners [i];
					index++;
				}
			}
			for (int i=index; i<this.types.length; i++) {
				this.types [i] = 0;
				this.listeners [i] = null;
			}
		}
	}
        
//        currentTime = System.currentTimeMillis();
//        if(currentTime - aLastTime > 2000) {
//            System.out.println(UPDATE_DISPLAYETDSE5 + currentTime);
//        }
//        aLastTime = currentTime;
}

public int size () {
	if (this.types == null) return 0;
	int count = 0;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] != 0) count++;
	}
	return count;
}

void remove (int index) {
	if (this.level == 0) {
		int end = this.types.length - 1;
		System.arraycopy (types, index + 1, types, index, end - index);
		System.arraycopy (listeners, index + 1, listeners, index, end - index);
		index = end;
	} else {
		if (this.level > 0) this.level = -this.level;
	}
	types [index] = 0;
	listeners [index] = null;
}

public void unhook (int eventType, Listener listener) {
	if (this.types == null) return;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == eventType && this.listeners [i] == listener) {
			remove (i);
			return;
		}
	}
}

public void unhook (int eventType, SWTEventListener listener) {
	if (this.types == null) return;
	for (int i=0; i<this.types.length; i++) {
		if (this.types [i] == eventType) {
			if (this.listeners [i] instanceof TypedListener) {
				TypedListener typedListener = (TypedListener) this.listeners [i];
				if (typedListener.getEventListener () == listener) {
					remove (i);
					return;
				}
			}
		}
	}
}

}
