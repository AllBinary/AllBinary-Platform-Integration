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
package org.microemu.graphics.form.item;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.NullCanvas;
import javax.microedition.lcdui.Screen;

import org.allbinary.graphics.form.item.StringComponent;

import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.ForcedLogUtil;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.graphics.displayable.command.MyCommandsFactory;

public class CustomItem
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    public static final int LAYOUT_DEFAULT = 0x0000;

    public static final int LAYOUT_LEFT = 0x0001;
    public static final int LAYOUT_RIGHT = 0x0002;
    public static final int LAYOUT_CENTER = 0x0003;

    public static final int LAYOUT_TOP = 0x0010;
    public static final int LAYOUT_BOTTOM = 0x0020;
    public static final int LAYOUT_VCENTER = 0x0030;

    public static final int LAYOUT_NEWLINE_BEFORE = 0x0100;
    public static final int LAYOUT_NEWLINE_AFTER = 0x0200;

    public static final int LAYOUT_SHRINK = 0x0400;
    public static final int LAYOUT_EXPAND = 0x0800;
    public static final int LAYOUT_VSHRINK = 0x1000;
    public static final int LAYOUT_VEXPAND = 0x2000;

    public static final int LAYOUT_2 = 0x4000;

    private final StringComponent labelStringComponent;
    private Displayable owner = NullCanvas.NULL_CANVAS;
    private boolean focus = false;

    // MIDP2
    private int layout;
    private Vector<Object> commands;
    private Command defaultCommand = MyCommandsFactory.getInstance().NO_COMMAND;
    
    private CustomItemCommandListener commandListener = CustomItemCommand.NULL_CUSTOM_ITEM_COMMAND;

    private int prefWidth, prefHeight;

    protected CustomItem(final String label, final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor)
    {
        labelStringComponent = new StringComponent(label, backgroundBasicColor, foregroundBasicColor);
        commands = new Vector<Object>();
    }

    public void addCommand(final Command cmd)
    {
        if (cmd == null) {
            throw new NullPointerException();
        }

        if (!commands.contains(cmd))
        {
            // Now insert it in order
            boolean inserted = false;

            Command command;
            for (int i = 0; i < commands.size(); i++)
            {
                command = (Command) commands.elementAt(i);
                if (cmd.getPriority() < command.getPriority())
                {
                    commands.insertElementAt(cmd, i);
                    inserted = true;
                    break;
                }
            }
            
            if (!inserted)
            {
                // Not inserted just place it at the end
                commands.addElement(cmd);
            }
        }

    }

    public String getLabel()
    {
        return getLabelStringComponent().getText();
    }

    public int getLayout()
    {
        return layout;
    }

    public int getMinimumHeight()
    {
        if (getLabelStringComponent() != null) {
            return getLabelStringComponent().getHeight();
        } else {
            return 0;
        }
    }

    public int getMinimumWidth()
    {
        return getMaximumWidth();
    }

    public int getPreferredHeight()
    {
        int ret = prefHeight;
        final int min = getMinimumHeight();
        final int max = getMaximumHeight();

        if (ret == -1) {
            return min;
        }

        if (ret < min) {
            ret = min;
        } else if (ret > max) {
            ret = max;
        }
        return ret;
    }

    public int getPreferredWidth()
    {
        int ret = prefWidth;
        final int min = getMinimumWidth();
        final int max = getMaximumWidth();

        if (ret == -1) {
            return max;
        }

        if (ret < min) {
            ret = min;
        } else if (ret > max) {
            ret = max;
        }

        return ret;
    }

    public void removeCommand(Command cmd)
    {
        commands.removeElement(cmd);
        if (defaultCommand == cmd)
            defaultCommand = MyCommandsFactory.getInstance().NO_COMMAND;
    }

    public void setDefaultCommand(Command cmd)
    {
        this.defaultCommand = cmd;
        if (cmd != null)
        {
            // we should repaint even if the command was added
            // because the command layout could become different
            if (commands.contains(cmd)) {
                this.addCommand(cmd);
            }
        }
    }

    public void setItemCommandListener(CustomItemCommandListener l)
    {
        this.commandListener = l;
    }

    public void setLabel(String label)
    {
        getLabelStringComponent().setText(label);
    }

    public void setLayout(int layout)
    {
        // TODO validate container is not Alert
        // on add to Alert validate this is default

        // notice that the vertical and the horizontal
        // layout policies can't generate conflict
        // because the center is the or of the two
        // others (ie VCENTER == (LEFT | RIGHT))
        if ((((layout & LAYOUT_SHRINK) != 0) && ((layout & LAYOUT_EXPAND) != 0))
                || (((layout & LAYOUT_VSHRINK) != 0) && (layout & LAYOUT_VEXPAND) != 0)) {
            throw new IllegalArgumentException("Bad combination of layout policies");
        }
        
        this.layout = layout;
    }

    public void setPreferredSize(int width, int height)
    {
        if (width < -1 || height < -1)
        {
            throw new IllegalArgumentException();
        }
        this.prefWidth = width;
        this.prefHeight = height;
    }

    public int getHeight()
    {
        return getLabelStringComponent().getHeight() + 4;
    }

    public boolean isFocusable()
    {
        return false;
    }

    public void keyPressed(int keyCode)
    {
    }

    public int paint(Graphics graphics)
    {
        ForcedLogUtil.log(CommonStrings.getInstance().NOT_IMPLEMENTED, this);
        return -1;
    }

    /*
    void paintContent(Graphics g)
    {
        // logUtil.putF(commonStrings.START, this, "paintContent");
        labelComponent.paint(g);
    }
    */

    public boolean hasFocus()
    {
        return focus;
    }

    public void setFocus(boolean state)
    {
        this.focus = state;
    }

    Displayable getOwner()
    {
        return owner;
    }

    public void setOwner(Screen owner)
    //public void setOwner(Displayable owner)
    {
        this.owner = owner;

        if (owner == null)
        {
            setFocus(false);
        }
    }

    public boolean select()
    {
        // call the default command (if there is one)
        // however subclasses may override this behaviour
        // (ie popup choices uses select to bring the popup)
        if (defaultCommand != null && commandListener != null)
        {
            commandListener.commandAction(defaultCommand, this);
            return true;
        }
        else
        {
            return false;
        }
    }

    public int traverse(int gameKeyCode, int top, int bottom, boolean action)
    {
        return 0;
    }

    int getMaximumHeight()
    {
        if (owner != null)
        {
            return owner.getHeight() * 10;
        }
        else
        {
            return DisplayInfoSingleton.getInstance().getLastHeight() * 10;
        }
    }

    int getMaximumWidth()
    {
        if (owner != null)
        {
            return owner.getWidth() - 3;
        }
        else
        {
            return DisplayInfoSingleton.getInstance().getLastWidth() - 3;
        }
    }

    protected final void repaint()
    {
        
    }
    
    CustomItemCommandListener getItemCommandListener()
    {
        return this.commandListener;
    }

    public StringComponent getLabelStringComponent()
    {
        return labelStringComponent;
    }
}
