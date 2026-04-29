/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.app;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.FileNotFoundException;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.microedition.midlet.PlatformMIDletHelper;

/**
 *
 * @author User
 */
public class SwingMIDletHelper extends PlatformMIDletHelper
    implements ComponentListener, MouseListener, MouseMotionListener {

    private ActionListener menuExitListener;

    public void addExitListener(final Object menuExitListener) {
        this.menuExitListener = (ActionListener) menuExitListener;
    }

    public void handleExit() {
        this.menuExitListener.actionPerformed(null);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        this.midlet.mouseClicked(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mousePressed(MouseEvent mouseEvent) {
        this.midlet.mousePressed(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        this.midlet.mouseReleased(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        this.midlet.mouseEntered(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mouseExited(MouseEvent mouseEvent) {
        this.midlet.mouseExited(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        this.midlet.mouseDragged(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        this.midlet.mouseMoved(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getButton());
    }

    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        this.midlet.mouseWheelMoved(mouseWheelEvent.getX(), mouseWheelEvent.getY(), mouseWheelEvent.getButton());
    }

    public void componentResized(ComponentEvent e) {
        this.midlet.componentResized();
    }

    public void componentMoved(ComponentEvent e) {
        this.midlet.componentMoved();
    }

    public void componentShown(ComponentEvent e) {
        this.midlet.componentShown();
    }

    public void componentHidden(ComponentEvent e) {
        this.midlet.componentHidden();
    }

    private AccessControlContext accessControlContext;

    public void setAccessControlContext(AccessControlContext acc) {
        this.accessControlContext = acc;
    }

    /**
     * @return the accessControlContext
     */
    public AccessControlContext getAccessControlContext() {
        return this.accessControlContext;
    }

    public void exit() throws Exception {
        try
        {
            AccessController.doPrivileged(new PrivilegedExceptionAction()
            {
                public Object run() throws FileNotFoundException
                {
                    handleExit();
                    return null;
                }
            }, getAccessControlContext());
        }
        catch (PrivilegedActionException e)
        {
            throw new Exception("Unable To Exit");
        }        
    }
}
