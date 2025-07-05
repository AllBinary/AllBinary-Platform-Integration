/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Created By: Travis Berthelot
 */
package org.microemu.app;

import javax.microedition.midlet.MIDlet;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;

/**
 *
 * @author User
 */
public class SWTMouseEventListener {
    
    public void mouseDoubleClick(final MIDlet midlet, final MouseEvent mouseEvent) {
        
    }

    public void mouseDown(final MIDlet midlet, final MouseEvent mouseEvent) {

        midlet.mousePressed(mouseEvent.x, mouseEvent.y, mouseEvent.button);

    }

    public void mouseUp(final MIDlet midlet, final MouseEvent mouseEvent) {
        
        midlet.mouseReleased(mouseEvent.x, mouseEvent.y, mouseEvent.button);
        
    }
    
    public void mouseMove(final MIDlet midlet, final MouseEvent mouseEvent) {

        midlet.mouseMoved(mouseEvent.x, mouseEvent.y, mouseEvent.button);
        
    }

    public void dragDetected(final MIDlet midlet, final DragDetectEvent mouseEvent) {

        midlet.mouseDragged(mouseEvent.x, mouseEvent.y, mouseEvent.button);
        
    }    
    
}
