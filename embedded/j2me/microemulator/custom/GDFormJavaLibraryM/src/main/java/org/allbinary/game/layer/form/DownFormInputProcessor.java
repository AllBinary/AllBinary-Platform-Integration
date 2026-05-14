/*
 * AllBinary Open License Version 1
 * Copyright (c) 2022 AllBinary
 * 
 * By agreeing to this license you and any business entity you represent are
 * legally bound to the AllBinary Open License Version 1 legal agreement.
 * 
 * You may obtain the AllBinary Open License Version 1 legal agreement from
 * AllBinary or the root directory of AllBinary's AllBinary Platform repository.
 * 
 * Created By: Travis Berthelot
 * 
 */
package org.allbinary.game.layer.form;

import org.allbinary.game.input.event.DownKeyEventHandler;
import org.allbinary.logic.util.event.EventListenerInterface;
import org.allbinary.util.BasicArrayList;

/**
 *
 * @author User
 */
public class DownFormInputProcessor extends PlatformFormInputProcessor {
    
    private final DownKeyEventHandler downKeyEventHandler = DownKeyEventHandler.getInstance();
    //private final DownGameKeyEventHandler downGameKeyEventHandler = DownGameKeyEventHandler.getInstance();
    
    public void addListener(final EventListenerInterface eventListenerInterface) {
        //this.downGameKeyEventHandler.addListener(eventListenerInterface);
        this.downKeyEventHandler.addListenerInterface(eventListenerInterface);
    }
    
    public void addListeners(final BasicArrayList list) {
        //this.downGameKeyEventHandler.addListeners(list);
        this.downKeyEventHandler.addListeners(list);
    }
    
    public void removeListener(final EventListenerInterface eventListenerInterface) {
        //this.downGameKeyEventHandler.removeListener(eventListenerInterface);
        this.downKeyEventHandler.removeListener(eventListenerInterface);
    }
    
}
