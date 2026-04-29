/*
 *  Siemens API for MicroEmulator
 *  Copyright (C) 2003 Markus Heberling <markus@heberling.net>
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 */
package com.siemens.mp.color_game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class Layer {
    Image image;
    protected Layer(int width, int height) {
        this.width=width;
        this.height=height;
    }
    
    protected Layer(Image img) {
        this(img.getWidth(),img.getHeight());
        this.image=img;
    }
    
    protected  void copyAllLayerVariables(com.siemens.mp.color_game.Layer l) {
        l.width=width;
        l.height=height;
        l.x=x;
        l.y=y;
        l.image=this.image;
    }
    
    protected void setLayerImage(Image img) {
        this.image=img;
    }
    
    protected  javax.microedition.lcdui.Image getLayerImage() {
        return this.image;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    
    public final int getX() {
        return this.x;
    }
    
    
    public final int getY() {
        return this.y;
    }
    
    public final int getWidth() {
        return this.width;
    }
    
    
    public final int getHeight() {
        return this.height;
    }
    
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
    public final boolean isVisible() {
        return this.visible;
    }
    
    public abstract void paint(Graphics g);
    
    void setWidthImpl(int w){
        this.width=w;
    }
    
    void setHeightImpl(int h){
        this.height=h;
    }
    
    int x; // = 0;
    int y; // = 0;
    int width; // = 0;
    int height; // = 0;
    boolean visible = true;
    
}