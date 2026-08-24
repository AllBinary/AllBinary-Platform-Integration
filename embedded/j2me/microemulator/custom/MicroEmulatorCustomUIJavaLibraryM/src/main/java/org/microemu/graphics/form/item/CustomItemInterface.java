/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import jsinterop.annotations.JsType;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Screen;
import jsinterop.annotations.JsMethod;

/**
 *
 * @author user
 */

@JsType
public interface CustomItemInterface {

    @JsMethod
    void setOwner(Screen owner);
    @JsMethod
    int getMinimumWidth();
    @JsMethod
    int getMinimumHeight();
    @JsMethod
    String getLabel();
    @JsMethod
    void paintXY(Graphics graphics, int x, int y);
    @JsMethod
    void paintUnselected(Graphics graphics, int x, int y);
}
