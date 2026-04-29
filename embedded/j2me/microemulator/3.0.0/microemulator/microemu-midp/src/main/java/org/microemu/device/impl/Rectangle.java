/*
 *  MicroEmulator
 *  Copyright (C) 2006 Bartek Teodorczyk <barteo@barteo.net>
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
 *  
 *  @version $Id: Rectangle.java 1605 2008-02-25 21:07:14Z barteo $
 */

package org.microemu.device.impl;

import org.allbinary.logic.string.StringMaker;

public class Rectangle extends Shape {
	
	private boolean initialized;
	
	public int x;

	public int y;

	public int width;

	public int height;

	public Rectangle() {
		this.initialized = false;
	}
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.initialized = true;
	}

	public Rectangle(Rectangle rect) {
		this.x = rect.x;
		this.y = rect.y;
		this.width = rect.width;
		this.height = rect.height;
		
		this.initialized = false;
	}
	
	public void add(int newx, int newy) {
		if (this.initialized) {
			if (newx < this.x) {
				this.width += this.x - newx;
				this.x = newx;			
			} else if (newx > this.x + this.width) {
				this.width = newx - x;
			}
			if (newy < this.y) {
				this.height += this.y - newy;
				this.y = newy;
			} else if (newy > this.y + this.height) {
				this.height = newy - y;
			}
		} else {
			this.x = newx;
			this.y = newy;
			this.initialized = true;
		}
	}

	public boolean contains(int x, int y) {
		if (x >= this.x && x < this.x + this.width && y >= this.y
				&& y < this.y + this.height) {
			return true;
		} else {
			return false;
		}
	}

        @Override
	public Rectangle getBounds() {
		return this;
	}
	
	public String toString() {
	    StringMaker buf = new StringMaker();
	    buf.appendint(this.x).append(",").appendint(this.y).append(" ").appendint(this.width).append("x").appendint(this.height);
	    return buf.toString();
	}

}
