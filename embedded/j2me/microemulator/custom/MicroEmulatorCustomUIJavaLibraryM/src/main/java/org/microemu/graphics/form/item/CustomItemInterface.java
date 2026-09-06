/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;


import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Screen;

/**
 *
 * @author user
 */

public interface CustomItemInterface {

    void setOwner(Screen owner);
    int getMinimumWidth();
    int getMinimumHeight();
    String getLabel();
    void paintXY(Graphics graphics, int x, int y);
    void paintUnselected(Graphics graphics, int x, int y);
}
