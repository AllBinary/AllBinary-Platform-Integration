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
package org.eclipse.swt.widgets;


import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;

/**	 
 * Instances of this class are <em>internal SWT implementation</em>
 * objects which provide a mapping between the typed and untyped 
 * listener mechanisms that SWT supports.
 * <p>
 * <b>IMPORTANT:</b> This class is <em>not</em> part of the SWT
 * public API. It is marked public only so that it can be shared
 * within the packages provided by SWT. It should never be
 * referenced from application code.
 * </p>
 *
 * @see Listener
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 */
public class TypedListener implements Listener {
	
	/**
	 * The receiver's event listener
	 */
	protected SWTEventListener eventListener;

/**
 * Constructs a new instance of this class for the given event listener.
 * <p>
 * <b>IMPORTANT:</b> This method is <em>not</em> part of the SWT
 * public API. It is marked public only so that it can be shared
 * within the packages provided by SWT. It should never be
 * referenced from application code.
 * </p>
 *
 * @param listener the event listener to store in the receiver
 * 
 * @noreference This method is not intended to be referenced by clients.
 */
public TypedListener (SWTEventListener listener) {
	this.eventListener = listener;
}

/**
 * Returns the receiver's event listener.
 * <p>
 * <b>IMPORTANT:</b> This method is <em>not</em> part of the SWT
 * public API. It is marked public only so that it can be shared
 * within the packages provided by SWT. It should never be
 * referenced from application code.
 * </p>
 *
 * @return the receiver's event listener
 * 
 * @noreference This method is not intended to be referenced by clients.
 */
public SWTEventListener getEventListener () {
	return eventListener;
}

/**
 * Handles the given event.
 * <p>
 * <b>IMPORTANT:</b> This method is <em>not</em> part of the SWT
 * public API. It is marked public only so that it can be shared
 * within the packages provided by SWT. It should never be
 * referenced from application code.
 * </p>
 * @param e the event to handle
 * 
 * @noreference This method is not intended to be referenced by clients.
 */
public void handleEvent (Event e) {
    
    //System.out.println(new StringBuffer().append("TypedListener - handleEvent: ").append(e.type).append(" t=").append(System.currentTimeMillis()).toString());
	switch (e.type) {
		case SWT.Activate: {
			((ShellListener) this.eventListener).shellActivated(new ShellEvent(e));
			break;
		}
		case SWT.Arm: {
			((ArmListener) this.eventListener).widgetArmed (new ArmEvent (e));
			break;
		}
		case SWT.Close: {
			/* Fields set by Decorations */
			ShellEvent event = new ShellEvent (e);
			((ShellListener) this.eventListener).shellClosed(event);
			e.doit = event.doit;
			break;
		}
		case SWT.Collapse: {
			if (this.eventListener instanceof TreeListener) {
				((TreeListener) this.eventListener).treeCollapsed(new TreeEvent(e));
			} else {
				((ExpandListener) this.eventListener).itemCollapsed(new ExpandEvent(e));	
			}
			break;
		}
		case SWT.Deactivate: {
			((ShellListener) this.eventListener).shellDeactivated(new ShellEvent(e));
			break;
		}
		case SWT.Deiconify: {
			((ShellListener) this.eventListener).shellDeiconified(new ShellEvent(e));
			break;
		}
		case SWT.DefaultSelection: {
			((SelectionListener)this.eventListener).widgetDefaultSelected(new SelectionEvent(e));
			break;
		}
		case SWT.Dispose: {
			((DisposeListener) this.eventListener).widgetDisposed(new DisposeEvent(e));
			break;
		}
		case SWT.DragDetect: {
			((DragDetectListener) this.eventListener).dragDetected(new DragDetectEvent(e));
			break;
		}
		case SWT.Expand: {
			if (this.eventListener instanceof TreeListener) {
				((TreeListener) this.eventListener).treeExpanded(new TreeEvent(e));
			} else {
				((ExpandListener) this.eventListener).itemExpanded(new ExpandEvent(e));	
			}
			break;
		}
		case SWT.FocusIn: {
			((FocusListener) this.eventListener).focusGained(new FocusEvent(e));
			break;
		}
		case SWT.FocusOut: {
			((FocusListener) this.eventListener).focusLost(new FocusEvent(e));
			break;
		}
		case SWT.Gesture: {
			GestureEvent event = new GestureEvent(e);
			((GestureListener)this.eventListener).gesture(event);
			e.doit = event.doit;
			break;
		}
		case SWT.Help: {
			((HelpListener) this.eventListener).helpRequested (new HelpEvent (e));
			break;
		}
		case SWT.Hide: {
			((MenuListener) this.eventListener).menuHidden(new MenuEvent(e));
			break;
		}
		case SWT.Iconify: {
			((ShellListener) this.eventListener).shellIconified(new ShellEvent(e));
			break;
		}
		case SWT.KeyDown: {
			/* Fields set by Control */
			KeyEvent event = new KeyEvent(e);
			((KeyListener) this.eventListener).keyPressed(event);
			e.doit = event.doit;
			break;
		}
		case SWT.KeyUp: {
			/* Fields set by Control */
			KeyEvent event = new KeyEvent(e);
			((KeyListener) this.eventListener).keyReleased(event);
			e.doit = event.doit;
			break;
		}
		case SWT.Modify: {
			((ModifyListener) this.eventListener).modifyText(new ModifyEvent(e));
			break;
		}
		case SWT.MenuDetect: {
			MenuDetectEvent event = new MenuDetectEvent(e);
			((MenuDetectListener) this.eventListener).menuDetected(event);
			e.x = event.x;
			e.y = event.y;
			e.doit = event.doit;
			e.detail = event.detail;
			break;
		}
		case SWT.MouseDown: {
			((MouseListener) this.eventListener).mouseDown(new MouseEvent(e));
			break;
		}
		case SWT.MouseDoubleClick: {
			((MouseListener) this.eventListener).mouseDoubleClick(new MouseEvent(e));
			break;
		}
		case SWT.MouseEnter: {
			((MouseTrackListener) this.eventListener).mouseEnter (new MouseEvent (e));
			break;
		}
		case SWT.MouseExit: {
			((MouseTrackListener) this.eventListener).mouseExit (new MouseEvent (e));
			break;
		}
		case SWT.MouseHover: {
			((MouseTrackListener) this.eventListener).mouseHover (new MouseEvent (e));
			break;
		}
		case SWT.MouseMove: {
			((MouseMoveListener) this.eventListener).mouseMove(new MouseEvent(e));
			return;
		}
		case SWT.MouseWheel: {
			((MouseWheelListener) this.eventListener).mouseScrolled(new MouseEvent(e));
			return;
		}
		case SWT.MouseUp: {
			((MouseListener) this.eventListener).mouseUp(new MouseEvent(e));
			break;
		}
		case SWT.Move: {
			((ControlListener) this.eventListener).controlMoved(new ControlEvent(e));
			break;
		}
		case SWT.Paint: {
			/* Fields set by Control */
			PaintEvent event = new PaintEvent (e);
			((PaintListener) this.eventListener).paintControl (event);
			e.gc = event.gc;
			break;
		}
		case SWT.Resize: {
			((ControlListener) this.eventListener).controlResized(new ControlEvent(e));
			break;
		}
		case SWT.Segments: {
			SegmentEvent event = new SegmentEvent(e);
			((SegmentListener) this.eventListener).getSegments(event);
			e.segments = event.segments;
			e.segmentsChars = event.segmentsChars;
			break;
		}
		case SWT.Selection: {
			/* Fields set by Sash */
			SelectionEvent event = new SelectionEvent (e);
			((SelectionListener) this.eventListener).widgetSelected (event);			
			e.x = event.x;
			e.y = event.y;
			e.doit = event.doit;
			break;
		}
		case SWT.Show: {
			((MenuListener) this.eventListener).menuShown(new MenuEvent(e));
			break;
		}
		case SWT.Touch: {
			((TouchListener)this.eventListener).touch(new TouchEvent(e));
			break;
		}
		case SWT.Traverse: {
			/* Fields set by Control */
			TraverseEvent event = new TraverseEvent (e);
			((TraverseListener) this.eventListener).keyTraversed (event);
			e.detail = event.detail;
			e.doit = event.doit;
			break;
		}
		case SWT.Verify: {
			/* Fields set by Text, RichText */
			VerifyEvent event = new VerifyEvent (e);
			((VerifyListener) this.eventListener).verifyText (event);
			e.text = event.text;
			e.doit = event.doit;
			break;
		}
	}
        //System.out.println(new StringBuffer().append("EndTypedListener - handleEvent: ").append(e.type).append(" t=").append(System.currentTimeMillis()).toString());
}

}
