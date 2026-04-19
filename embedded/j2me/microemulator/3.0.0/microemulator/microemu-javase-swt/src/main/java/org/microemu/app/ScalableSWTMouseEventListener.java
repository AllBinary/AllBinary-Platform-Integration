/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.app;

import javax.microedition.midlet.MIDlet;

import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.graphics.displayable.ScalableListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseEvent;

/**
 *
 * @author User
 */
public class ScalableSWTMouseEventListener extends SWTMouseEventListener {
    //protected final LogUtil logUtil = LogUtil.getInstance();

    
    private final DisplayInfoSingleton displayInfo = DisplayInfoSingleton.getInstance();
    
    public void mouseDoubleClick(final MIDlet midlet, final MouseEvent mouseEvent) {
        
    }

    public void mouseDown(final MIDlet midlet, final MouseEvent mouseEvent) {

        final ScalableListener scalableListener = this.displayInfo.getScalableListener();
        final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
        final int y = (int) ((mouseEvent.y - this.displayInfo.getTop())  / scalableListener.getRatio(this.displayInfo.getRatio()));
        
        if(this.displayInfo.isPortrait()) {

            //logUtil.putF(new StringBuilder().append("Checking: portrait - ratio: ").append(displayInfo.getRatio()).append(" x: ").append(mouseEvent.x).append(" y: ").append(mouseEvent.y).append(" xs: ").append(x).append(" ys: ").append(y).toString(), this, "mouseDown");
            
            if (this.displayInfo.getLastHeight() >= this.displayInfo.getScaleLargestTo()) {
                midlet.mousePressed(x, y, mouseEvent.button);
            } else {
                midlet.mousePressed(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }
            
        } else {

            //logUtil.putF(new StringBuilder().append("Checking: landscape - ratio: ").append(displayInfo.getRatio()).append(" x: ").append(mouseEvent.x).append(" y: ").append(mouseEvent.y).append(" xs: ").append(x).append(" ys: ").append(y).toString(), this, "mouseDown");
            
            if (this.displayInfo.getLastWidth() >= this.displayInfo.getScaleLargestTo()) {
                midlet.mousePressed(x, y, mouseEvent.button);
            } else {
                midlet.mousePressed(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        }

    }

    public void mouseUp(final MIDlet midlet, final MouseEvent mouseEvent) {
        
        if (this.displayInfo.isPortrait()) {

            if (this.displayInfo.getLastHeight() >= this.displayInfo.getScaleLargestTo()) {
                final ScalableListener scalableListener = this.displayInfo.getScalableListener();
                final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
                final int y = (int) ((mouseEvent.y - this.displayInfo.getTop()) / scalableListener.getRatio(this.displayInfo.getRatio()));

                midlet.mouseReleased(x, y, mouseEvent.button);
            } else {
                midlet.mouseReleased(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        } else {

            if (this.displayInfo.getLastWidth() >= this.displayInfo.getScaleLargestTo()) {
                final ScalableListener scalableListener = this.displayInfo.getScalableListener();
                final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
                final int y = (int) ((mouseEvent.y - this.displayInfo.getTop()) / scalableListener.getRatio(this.displayInfo.getRatio()));

                midlet.mouseReleased(x, y, mouseEvent.button);
            } else {
                midlet.mouseReleased(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        }
        
    }
    
    public void mouseMove(final MIDlet midlet, final MouseEvent mouseEvent) {

        if (this.displayInfo.isPortrait()) {

            if (this.displayInfo.getLastHeight() >= this.displayInfo.getScaleLargestTo()) {
                final ScalableListener scalableListener = this.displayInfo.getScalableListener();
                final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
                final int y = (int) ((mouseEvent.y - this.displayInfo.getTop()) / scalableListener.getRatio(this.displayInfo.getRatio()));

                midlet.mouseMoved(x, y, mouseEvent.button);
            } else {
                midlet.mouseMoved(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        } else {

            if (this.displayInfo.getLastWidth() >= this.displayInfo.getScaleLargestTo()) {
                final ScalableListener scalableListener = this.displayInfo.getScalableListener();
                final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
                final int y = (int) ((mouseEvent.y - this.displayInfo.getTop()) / scalableListener.getRatio(this.displayInfo.getRatio()));

                midlet.mouseMoved(x, y, mouseEvent.button);
            } else {
                midlet.mouseMoved(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        }
        
    }

    public void dragDetected(final MIDlet midlet, final DragDetectEvent mouseEvent) {

        if (this.displayInfo.isPortrait()) {

            if (this.displayInfo.getLastHeight() >= this.displayInfo.getScaleLargestTo()) {
                final ScalableListener scalableListener = this.displayInfo.getScalableListener();
                final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
                final int y = (int) ((mouseEvent.y - this.displayInfo.getTop()) / scalableListener.getRatio(this.displayInfo.getRatio()));

                midlet.mouseDragged(x, y, mouseEvent.button);
            } else {
                midlet.mouseDragged(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        } else {

            if (this.displayInfo.getLastWidth() >= this.displayInfo.getScaleLargestTo()) {
                final ScalableListener scalableListener = this.displayInfo.getScalableListener();
                final int x = (int) ((mouseEvent.x - this.displayInfo.getLeft()) / scalableListener.getRatio(this.displayInfo.getRatio()));
                final int y = (int) ((mouseEvent.y - this.displayInfo.getTop()) / scalableListener.getRatio(this.displayInfo.getRatio()));

                midlet.mouseDragged(x, y, mouseEvent.button);
            } else {
                midlet.mouseDragged(mouseEvent.x, mouseEvent.y, mouseEvent.button);
            }

        }
        
    }    
    
}
