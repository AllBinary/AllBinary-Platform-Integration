/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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
 *
 *  Contributor(s):
 *    3GLab
 */

package javax.microedition.lcdui;

import java.util.Vector;
import static javax.microedition.lcdui.Item.LAYOUT_EXPAND;
import static javax.microedition.lcdui.Item.LAYOUT_SHRINK;
import static javax.microedition.lcdui.Item.LAYOUT_VEXPAND;
import static javax.microedition.lcdui.Item.LAYOUT_VSHRINK;

import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.ItemUI;

//TWB - name concrete
public class Item
{

	public static final int OUTOFITEM = Integer.MAX_VALUE;

	public static final int LAYOUT_DEFAULT          = 0x0000;

    public static final int LAYOUT_LEFT             = 0x0001;
    public static final int LAYOUT_RIGHT            = 0x0002;
    public static final int LAYOUT_CENTER           = 0x0003;

    public static final int LAYOUT_TOP              = 0x0010;
    public static final int LAYOUT_BOTTOM           = 0x0020;
    public static final int LAYOUT_VCENTER          = 0x0030;

    public static final int LAYOUT_NEWLINE_BEFORE   = 0x0100;
    public static final int LAYOUT_NEWLINE_AFTER    = 0x0200;

    public static final int LAYOUT_SHRINK           = 0x0400;
    public static final int LAYOUT_EXPAND           = 0x0800;
    public static final int LAYOUT_VSHRINK          = 0x1000;
    public static final int LAYOUT_VEXPAND          = 0x2000;

    public static final int LAYOUT_2                = 0x4000;


    public static final int PLAIN = 0;
    public static final int HYPERLINK = 1;
    public static final int BUTTON = 2;
    
    ItemUI ui;

	StringComponent labelComponent;
        //TWB changed to displayable
	Displayable owner = null;
	private boolean focus = false;
	
	// MIDP2
	int layout;
	Vector commands;
    Command defaultCommand;
    ItemCommandListener commandListener;
    
    // -1 means unlocked, otherwise it is the application requested preffered size
    // for the one use the getPrefXXXX() method package access
    int prefWidth;
    
    int prefHeight;
  
    Item(String label) {
		labelComponent = new StringComponent(label);
		this.commands = new Vector();
		this.setPreferredSize(-1, -1);
	}
	
    void setUI(ItemUI ui) {
    	this.ui = ui;
    }
    
	public void addCommand(Command cmd) {
	    if (cmd == null)
	        throw new NullPointerException();
	
	    if (!this.commands.contains(cmd)) {
	        // Now insert it in order
	        boolean inserted = false;
	          
	        for (int i = 0; i < this.commands.size(); i++) {
	            if (cmd.getPriority() < ((Command)this.commands.elementAt(i)).getPriority()) {
	                this.commands.insertElementAt(cmd, i);
	                inserted = true;
	                break;
	            }
	        }
	        if (!inserted) {
	          // Not inserted just place it at the end
	              this.commands.addElement(cmd);
	        }
	    	this.repaintOwner();
        }

	}
  
	public String getLabel()
	{
		return this.labelComponent.getText();
	}

	public int getLayout() {
		return this.layout;
	}
	
	public int getMinimumHeight() {
		if (this.labelComponent != null)
			return this.labelComponent.getHeight();
		else 
			return 0;
    }

    public int getMinimumWidth() {
    	return this.getMaximumWidth();
    }
    
    public int getPreferredHeight() {
        int ret = this.prefHeight;
        int min = this.getMinimumHeight();
        int max = this.getMaximumHeight();

        if (ret == -1)
        	return min;
        
        if (ret < min)
        	ret = min;
        else if (ret > max)
        	ret = max;
    	return ret;
    }

    public int getPreferredWidth() {
        int ret = this.prefWidth;
        int min = this.getMinimumWidth();
        int max = this.getMaximumWidth();
        
        if (ret == -1)
        	return max;
        	
        if (ret < min)
        	ret = min;
        else if (ret > max)
        	ret = max;
    	return ret;
    }

    //TWB changed to displayable
	public void notifyStateChanged() {
		Displayable owner = this.getOwner();
		if (owner != null && owner instanceof Form) {
			Form form = (Form) owner;
			form.fireItemStateListener(this);
		}
		
    }

	public void removeCommand(Command cmd) {
        this.commands.removeElement(cmd);
        if (this.defaultCommand == cmd)
        	this.defaultCommand = null;
        this.repaintOwner();
    }
	
    public void setDefaultCommand(Command cmd) {
        
        this.ui.setDefaultCommand(cmd);
        
        this.defaultCommand = cmd;
        if (cmd != null) {
            // we should repaint even if the command was added
            // because the command layout could become different
            if (this.commands.contains(cmd))
            	this.addCommand(cmd);
            else 
            	this.repaintOwner();
        } else {
        	this.repaintOwner();
        }
    }

    public void setItemCommandListener(ItemCommandListener l) {
        this.commandListener = l;
    }
    
    public void setLabel(String label)	
	{
    	this.ui.setLabel(label);
    	
		this.labelComponent.setText(label);
		this.repaint();
	}

    public void setLayout(int layout) {
    	// TODO validate container is not Alert
    	// on add to Alert validate this is default
    	
    	// notice that the vertical and the horizontal
    	// layout policies can't generate conflict
    	// because the center is the or of the two
    	// others (ie VCENTER == (LEFT | RIGHT))
    	if ((( (layout & Item.LAYOUT_SHRINK) != 0) &&
    		  ((layout & Item.LAYOUT_EXPAND) != 0)) ||
    			( ((layout & Item.LAYOUT_VSHRINK) != 0) &&
    	    	(layout & Item.LAYOUT_VEXPAND) != 0) )
    		throw new IllegalArgumentException(
    				"Bad combination of layout policies");
		this.layout = layout;
    	this.repaint();
    }

    public void setPreferredSize(int width, int height) {
        if (width < -1 || height < -1) {
            throw new IllegalArgumentException();
        }
        this.prefWidth = width;
        this.prefHeight = height;
        this.repaint();
    }

    //
    // package access methods
    //
    
    // repaint the owner of this item (if any)
    //TWB changed to displayable
    void repaintOwner() {
        Displayable owner = this.getOwner();
        if (owner != null)
        	owner.repaint();
    }

    //TWB - made public
  public int getHeight()
	{
		return this.labelComponent.getHeight();
	}
	
	//TWB - made public
	public boolean isFocusable()
	{
		return false;
	}

  //TWB - made public
  public void keyPressed(int keyCode)
  {
  }
  

  //TWB - made public
  public int paint(Graphics g)
  {
      return 0;
  }
	
	
	void paintContent(Graphics g)
	{
		this.labelComponent.paint(g);
	}
	
	
	void repaint()
	{
		if (this.owner != null) {
			this.owner.repaint();
		}
	}
	

        //TWB - made public
	public boolean hasFocus()
	{
		return this.focus;
	}
  
        //TWB - made public
	public void setFocus(boolean state)
	{
		this.focus = state;
	}

  //TWB - made public
  public Displayable getOwner()
  {
    return this.owner;
  }

  //TWB - made public
  	public void setOwner(Displayable owner)
  	{
		this.owner = owner;

		if (owner == null) {
			this.setFocus(false);
		}
	}


	//TWB - made public
	public boolean select()
	{
		// call the default command (if there is one)
		// however subclasses may override this behaviour
		// (ie popup choices uses select to bring the popup)
		if (this.defaultCommand != null && this.commandListener != null) {
			this.commandListener.commandAction(this.defaultCommand, this);
			return true;
		} else {
			return false;
		}
	}

//TWB - made public
	public int traverse(int gameKeyCode, int top, int bottom, boolean action)
	{
		return 0;
	}

//TWB - made public
	public int getMaximumHeight() {
		if (this.owner != null) {
			return this.owner.getHeight() * 10;
		} else {
			return DeviceFactory.getDevice().getDeviceDisplay().getHeight() * 10;
		}
	}

	//TWB - made public
	public int getMaximumWidth() {
		if (this.owner != null) {
			return this.owner.getWidth() - 3;
		} else {
			return DeviceFactory.getDevice().getDeviceDisplay().getWidth() - 3;
		}
	}

        //TWB - made public
	public ItemCommandListener getItemCommandListener() {
		return this.commandListener;
	}
}
