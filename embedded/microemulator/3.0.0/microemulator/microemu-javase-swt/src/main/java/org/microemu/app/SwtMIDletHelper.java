/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Created By: Travis Berthelot
 */
package org.microemu.app;

import javax.microedition.midlet.PlatformMIDletHelper;

import org.allbinary.logic.system.os.GenericOperatingSystem;
import org.allbinary.logic.system.os.OperatingSystemFactory;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;

import org.eclipse.swt.widgets.Listener;

/**
 *
 * @author User
 */
public class SwtMIDletHelper extends PlatformMIDletHelper 
    implements MouseListener, MouseMoveListener, DragDetectListener
    //GestureListener
    {

    private SWTMouseEventListener swtMouseEventListener;
    
   private Listener menuExitListener;
   
   public SwtMIDletHelper() {
       final GenericOperatingSystem operatingSystem = OperatingSystemFactory.getInstance().getOperatingSystemInstance();
       if(operatingSystem.isScalable()) {
           this.swtMouseEventListener = new ScalableSWTMouseEventListener();
       } else {
           this.swtMouseEventListener = new SWTMouseEventListener();
       }
   }
   
    public void addExitListener(final Object menuExitListener)
    {
        
        this.menuExitListener = (Listener) menuExitListener;
        
    }
 
    public void handleExit() {
        
        this.menuExitListener.handleEvent(null);
        
    }
    
    public void mouseDoubleClick(final MouseEvent mouseEvent) {
        
        this.swtMouseEventListener.mouseDoubleClick(midlet, mouseEvent);

    }

    public void mouseDown(final MouseEvent mouseEvent) {

        this.swtMouseEventListener.mouseDown(midlet, mouseEvent);

    }

    public void mouseUp(final MouseEvent mouseEvent) {
        
        this.swtMouseEventListener.mouseUp(midlet, mouseEvent);
        
    }
    
    public void mouseMove(MouseEvent mouseEvent) {

        this.swtMouseEventListener.mouseMove(midlet, mouseEvent);
        
    }

    public void dragDetected(DragDetectEvent mouseEvent) {

        this.swtMouseEventListener.dragDetected(midlet, mouseEvent);

    }    
    
}
