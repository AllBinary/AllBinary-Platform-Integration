/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.color.BasicColorFactory;
import org.allbinary.logic.string.StringUtil;

/**
 *
 * @author User
 */
public class StringComponentProperties {
    
    public static final boolean[] HAS_NOT_CHANGED_ARRAY = new boolean[1];
    public static final int[] LAST_WIDTH_ARRAY = new int[1];
    public boolean[] hasNotChanged = HAS_NOT_CHANGED_ARRAY;
    public int[] lastWidth = LAST_WIDTH_ARRAY;
    
    public String text = StringUtil.getInstance().EMPTY_STRING;

    public int breaks[] = new int[4];

    public boolean invertPaint = false;

    public int numOfBreaks;

    public int width;

    public int widthDecreaser;

    public BasicColor backgroundBasicColor = BasicColorFactory.getInstance().BLACK;
    public BasicColor foregroundBasicColor = BasicColorFactory.getInstance().WHITE;
    
    public void copy(final StringComponentProperties stringComponentProperties) {
        this.text = stringComponentProperties.text;
        
        this.breaks[0] = stringComponentProperties.breaks[0];
        this.breaks[1] = stringComponentProperties.breaks[1];
        this.breaks[2] = stringComponentProperties.breaks[2];
        this.breaks[3] = stringComponentProperties.breaks[3];
        
        this.invertPaint = stringComponentProperties.invertPaint;
        this.numOfBreaks = stringComponentProperties.numOfBreaks;
        this.widthDecreaser = stringComponentProperties.widthDecreaser;
        this.backgroundBasicColor = stringComponentProperties.backgroundBasicColor;
        this.foregroundBasicColor = stringComponentProperties.foregroundBasicColor;
        
    }
}
