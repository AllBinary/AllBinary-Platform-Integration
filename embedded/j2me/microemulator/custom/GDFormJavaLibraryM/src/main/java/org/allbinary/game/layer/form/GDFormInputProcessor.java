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

import org.allbinary.J2MEUtil;
import org.allbinary.input.event.VirtualKeyboardEventHandler;
import org.allbinary.input.motion.gesture.observer.BasicMotionGesturesHandler;
import org.allbinary.input.motion.gesture.observer.MovedMotionGesturesHandler;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;
import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 *
 * @author User
 */
public class GDFormInputProcessor
{
    protected final LogUtil logUtil = LogUtil.getInstance();


    private static final GDFormInputProcessor instance = new GDFormInputProcessor();
    
    /**
     * @return the instance
     */
    public static GDFormInputProcessor getInstance() {
        return instance;
    }
    
    //Hack as we may need more than 1 form per scene.
    public static final GDForm gdForm = new GDForm();
    
    private final VirtualKeyboardEventHandler virtualKeyboardEventHandler = 
                VirtualKeyboardEventHandler.getInstance();
    
    private final BasicMotionGesturesHandler basicMotionGesturesHandler = BasicMotionGesturesHandler.getInstance();
    private final MovedMotionGesturesHandler movedMotionGesturesHandler = MovedMotionGesturesHandler.getInstance();
    
    private final BasicArrayList sceneFormInputListenerList = new BasicArrayListD();

    private final PlatformFormInputProcessor platformFormInputProcessor;
    
    public GDFormInputProcessor() {
        
        if(J2MEUtil.isHTML()) {
            this.platformFormInputProcessor = new RawFormInputProcessor();
        } else {
            this.platformFormInputProcessor = new DownFormInputProcessor();
        }
    }

    public void append(final GDFormInput gdFormInput) {

        this.platformFormInputProcessor.addListener(gdFormInput);
        this.basicMotionGesturesHandler.addListener(gdFormInput);
        this.movedMotionGesturesHandler.addListener(gdFormInput);
        
        if(!this.sceneFormInputListenerList.contains(gdFormInput)) {
            this.sceneFormInputListenerList.add(gdFormInput);
        }
        
    }
    
    public void open() {

        this.platformFormInputProcessor.addListeners(this.sceneFormInputListenerList);
        this.movedMotionGesturesHandler.addListeners(this.sceneFormInputListenerList);
        this.basicMotionGesturesHandler.addListeners(this.sceneFormInputListenerList);

    }

    public void close() {
        
        final int size = this.sceneFormInputListenerList.size();
        GDFormInput gdFormInput;
        for(int index = 0; index < size; index++) {
            gdFormInput = (GDFormInput) this.sceneFormInputListenerList.get(index);
            this.platformFormInputProcessor.removeListener(gdFormInput);
            this.movedMotionGesturesHandler.removeListener(gdFormInput);
            this.basicMotionGesturesHandler.removeListener(gdFormInput);
        }

    }

    public void reset() {
        
        try {
            virtualKeyboardEventHandler.close();
        } catch(Exception e) {
            final CommonStrings commonStrings = CommonStrings.getInstance();
            this.logUtil.put(commonStrings.EXCEPTION, this, commonStrings.PROCESS, e);
        }
        
        final int size = this.sceneFormInputListenerList.size();
        GDFormInput gdFormInput;
        for(int index = 0; index < size; index++) {
            gdFormInput = (GDFormInput) this.sceneFormInputListenerList.get(index);

            this.movedMotionGesturesHandler.removeListener(gdFormInput);
            this.basicMotionGesturesHandler.removeListener(gdFormInput);
            this.platformFormInputProcessor.removeListener(gdFormInput);

            gdFormInput.reset();
        }
        
        this.sceneFormInputListenerList.clear();
    }
    
}
