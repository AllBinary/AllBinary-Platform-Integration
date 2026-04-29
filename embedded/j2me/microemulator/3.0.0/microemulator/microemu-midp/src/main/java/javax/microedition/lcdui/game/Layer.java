/*
 *  MicroEmulator
 *  Copyright (C) 2005 Andres Navarro
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Andres Navarro
 */

// i suppose this Class needs no comments 
public class Layer {
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean visible;
    
    Layer(int x, int y, int width, int height, boolean visible) {
        this.setSize(width, height);
        this.setPosition(x, y);
        this.setVisible(visible);
    }
    
    // package access to modify from Sprite
    void setSize(int width, int height) {
//        if (width < 1 || height < 1)
//            throw new IllegalArgumentException();

        this.width = width;
        this.height = height;
    }
    
    public final int getWidth() {
        return this.width;
    }
    
    public final int getHeight() {
        return this.height;
    }
    
    public final int getX() {
        return this.x;
    }
    
    public final int getY() {
        return this.y;
    }
    
    public final boolean isVisible() {
        return this.visible;
    }
    
    public void move(int dx, int dy) {
    	synchronized (this) {
	        this.x += dx;
	        this.y += dy;
    	}
    }
    
    public void paint(Graphics g) {
        
    }
    
    public void setPosition(int x, int y) {
    	synchronized (this) {
	        this.x = x;
	        this.y = y;
    	}
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
