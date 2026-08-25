/*
 *  MicroEmulator
 *  Copyright (C) 2005 Andres Navarro
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
 *  Other Contributor(s):
 *    Travis Berthelot
 */
package org.microemu.graphics.form.item;

import jsinterop.annotations.JsType;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.NullCanvas;
import javax.microedition.lcdui.Screen;

import org.allbinary.graphics.form.item.ABStringComponent;

import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.ForcedLogUtil;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.graphics.displayable.command.MyCommandsFactory;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
public class CustomItem
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    @JsProperty
    public static final int LAYOUT_DEFAULT = 0x0000;

    @JsProperty
    public static final int LAYOUT_LEFT = 0x0001;
    @JsProperty
    public static final int LAYOUT_RIGHT = 0x0002;
    @JsProperty
    public static final int LAYOUT_CENTER = 0x0003;

    @JsProperty
    public static final int LAYOUT_TOP = 0x0010;
    @JsProperty
    public static final int LAYOUT_BOTTOM = 0x0020;
    @JsProperty
    public static final int LAYOUT_VCENTER = 0x0030;

    @JsProperty
    public static final int LAYOUT_NEWLINE_BEFORE = 0x0100;
    @JsProperty
    public static final int LAYOUT_NEWLINE_AFTER = 0x0200;

    @JsProperty
    public static final int LAYOUT_SHRINK = 0x0400;
    @JsProperty
    public static final int LAYOUT_EXPAND = 0x0800;
    @JsProperty
    public static final int LAYOUT_VSHRINK = 0x1000;
    @JsProperty
    public static final int LAYOUT_VEXPAND = 0x2000;

    @JsProperty
    public static final int LAYOUT_2 = 0x4000;

    private final ABStringComponent labelStringComponent;
    private Displayable owner = NullCanvas.NULL_CANVAS;
    private boolean focus = false;

    // MIDP2
    private int layout;
    private Vector<Object> commands;
    private Command defaultCommand = MyCommandsFactory.getInstance().NO_COMMAND;
    
    private CustomItemCommandListener commandListener = CustomItemCommand.NULL_CUSTOM_ITEM_COMMAND;

    private int prefWidth, prefHeight;

    @JsConstructor
    protected CustomItem(final String label, final BasicColor backgroundBasicColor, final BasicColor foregroundBasicColor)
    {
        this.labelStringComponent = new ABStringComponent(backgroundBasicColor, foregroundBasicColor);
        this.labelStringComponent.setText(label);
        this.commands = new Vector<Object>();
    }

    @JsMethod
    public void addCommand(final Command cmd)
    {
        if (cmd == null) {
            throw new NullPointerException();
        }

        if (!this.commands.contains(cmd))
        {
            // Now insert it in order
            boolean inserted = false;

            Command command;
            for (int i = 0; i < this.commands.size(); i++)
            {
                command = (Command) this.commands.elementAt(i);
                if (cmd.getPriority() < command.getPriority())
                {
                    this.commands.insertElementAt(cmd, i);
                    inserted = true;
                    break;
                }
            }
            
            if (!inserted)
            {
                // Not inserted just place it at the end
                this.commands.addElement(cmd);
            }
        }

    }

    @JsMethod
    public String getLabel()
    {
        return this.getLabelStringComponent().getText();
    }

    @JsMethod
    public int getLayout()
    {
        return this.layout;
    }

    @JsMethod
    public int getMinimumHeight()
    {
        if (this.getLabelStringComponent() != null) {
            return this.getLabelStringComponent().getHeight();
        } else {
            return 0;
        }
    }

    @JsMethod
    public int getMinimumWidth()
    {
        return this.getMaximumWidth();
    }

    @JsMethod
    public int getPreferredHeight()
    {
        int ret = this.prefHeight;
        final int min = this.getMinimumHeight();
        final int max = this.getMaximumHeight();

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

    @JsMethod
    public int getPreferredWidth()
    {
        int ret = this.prefWidth;
        final int min = this.getMinimumWidth();
        final int max = this.getMaximumWidth();

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

    @JsMethod
    public void removeCommand(Command cmd)
    {
        this.commands.removeElement(cmd);
        if (this.defaultCommand == cmd)
            this.defaultCommand = MyCommandsFactory.getInstance().NO_COMMAND;
    }

    @JsMethod
    public void setDefaultCommand(Command cmd)
    {
        this.defaultCommand = cmd;
        if (cmd != null)
        {
            // we should repaint even if the command was added
            // because the command layout could become different
            if (this.commands.contains(cmd)) {
                this.addCommand(cmd);
            }
        }
    }

    @JsMethod
    public void setItemCommandListener(CustomItemCommandListener l)
    {
        this.commandListener = l;
    }

    @JsMethod
    public void setLabel(String label)
    {
        this.getLabelStringComponent().setText(label);
    }

    @JsMethod
    public void setLayout(int layout)
    {
        // TODO validate container is not Alert
        // on add to Alert validate this is default

        // notice that the vertical and the horizontal
        // layout policies can't generate conflict
        // because the center is the or of the two
        // others (ie VCENTER == (LEFT | RIGHT))
        if ((((layout & CustomItem.LAYOUT_SHRINK) != 0) && ((layout & CustomItem.LAYOUT_EXPAND) != 0))
                || (((layout & CustomItem.LAYOUT_VSHRINK) != 0) && (layout & CustomItem.LAYOUT_VEXPAND) != 0)) {
            throw new IllegalArgumentException("Bad combination of layout policies");
        }
        
        this.layout = layout;
    }

    @JsMethod
    public void setPreferredSize(int width, int height)
    {
        if (width < -1 || height < -1)
        {
            throw new IllegalArgumentException();
        }
        this.prefWidth = width;
        this.prefHeight = height;
    }

    @JsMethod
    public int getHeight()
    {
        return this.getLabelStringComponent().getHeight() + 4;
    }

    @JsMethod
    public boolean isFocusable()
    {
        return false;
    }

    @JsMethod
    public void keyPressed(int keyCode)
    {
    }

    @JsMethod
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

    @JsMethod
    public boolean hasFocus()
    {
        return this.focus;
    }

    @JsMethod
    public void setFocus(boolean state)
    {
        this.focus = state;
    }

    @JsMethod
    Displayable getOwner()
    {
        return this.owner;
    }

    @JsMethod
    public void setOwner(Screen owner)
    //public void setOwner(Displayable owner)
    {
        this.owner = owner;

        if (owner == null)
        {
            this.setFocus(false);
        }
    }

    @JsMethod
    public boolean select()
    {
        // call the default command (if there is one)
        // however subclasses may override this behaviour
        // (ie popup choices uses select to bring the popup)
        if (this.defaultCommand != null && this.commandListener != null)
        {
            this.commandListener.commandAction(this.defaultCommand, this);
            return true;
        }
        else
        {
            return false;
        }
    }

    @JsMethod
    public int traverse(int gameKeyCode, int top, int bottom, boolean action)
    {
        return 0;
    }

    @JsMethod
    int getMaximumHeight()
    {
        if (this.owner != null)
        {
            return this.owner.getHeight() * 10;
        }
        else
        {
            return DisplayInfoSingleton.getInstance().getLastHeight() * 10;
        }
    }

    @JsMethod
    int getMaximumWidth()
    {
        if (this.owner != null)
        {
            return this.owner.getWidth() - 3;
        }
        else
        {
            return DisplayInfoSingleton.getInstance().getLastWidth() - 3;
        }
    }

    @JsMethod
    protected final void repaint()
    {
        
    }
    
    @JsMethod
    CustomItemCommandListener getItemCommandListener()
    {
        return this.commandListener;
    }

    @JsMethod
    public ABStringComponent getLabelStringComponent()
    {
        return this.labelStringComponent;
    }
}
