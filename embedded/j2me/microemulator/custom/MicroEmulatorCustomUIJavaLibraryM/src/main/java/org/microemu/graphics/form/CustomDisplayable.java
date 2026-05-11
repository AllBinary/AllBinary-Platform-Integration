/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form;

import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.displayable.DisplayInfoSingleton;
import org.allbinary.graphics.paint.Paintable;

public class CustomDisplayable extends Paintable
{
    protected StringComponent title;

    private final Vector<Object> commands = new Vector<Object>();

    CustomDisplayable(String title, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        this.title = new StringComponent(title, Font.getDefaultFont(), backgroundBasicColor, foregroundBasicColor);
    }

    public void addCommand(Command cmd)
    {
        Command command;
        final int size = this.commands.size();
        for (int i = 0; i < size; i++)
        {
            command = (Command) this.commands.elementAt(i);
            if (cmd == command)
            {
                return;
            }
        }

        boolean inserted = false;
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
        if (inserted == false)
        {
            this.commands.addElement(cmd);
        }
    }

    public void removeCommand(Command cmd)
    {
        this.commands.removeElement(cmd);
    }

    public int getWidth()
    {
        return DisplayInfoSingleton.getInstance().getLastWidth();
    }

    public int getHeight()
    {
        return DisplayInfoSingleton.getInstance().getLastHeight();
    }

    public boolean isShown()
    {
        return true;
    }

    public String getTitle()
    {
        return this.title.getText();
    }

    public void setTitle(String s)
    {
        this.title.setText(s);
    }

    protected void sizeChanged(int w, int h)
    {
    }

    public Vector<Object> getCommands()
    {
        return this.commands;
    }

    void hideNotify()
    {
    }

//    final void hideNotify(Display d)
//    {
//        this.hideNotify();
//    }

    public void keyPressed(int keyCode)
    {
    }

    public void keyRepeated(int keyCode)
    {
    }

    public void keyReleased(int keyCode)
    {
    }

    void pointerPressed(int x, int y)
    {
    }

    void pointerReleased(int x, int y)
    {
    }

    void pointerDragged(int x, int y)
    {
    }

    @Override    
    public void paint(Graphics graphics)
    {
        
    }
}
