/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import org.microemu.graphics.form.StringComponent;

import org.allbinary.graphics.Anchor;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.FontDebugFactory;
import org.allbinary.graphics.font.MyFont;
import org.allbinary.input.event.VirtualKeyboardEventHandler;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.logic.string.StringUtil;
import org.allbinary.logic.util.visitor.Visitor;
import org.allbinary.time.TimeDelayHelper;

/**
 *
 * @author user
 */
public class TextFieldItem extends TextItem
{
    protected final LogUtil logUtil = LogUtil.getInstance();

    private final FontDebugFactory fontDebugFactory = FontDebugFactory.getInstance();
    private final int defaultSize = Font.getDefaultFont().getSize();

    private final int anchor = Anchor.TOP_LEFT;

    private int position;
    //private int positionX;
    private int positionY = 1;
    private boolean caretVisible = false;
    private int maxSize;

    private final StringComponent stringComponent;
    
    private final TextFieldItemHelper textFieldItemHelper;
    
    private final TimeDelayHelper timeDelayHelper = new TimeDelayHelper(900);
    private final TimeDelayHelper timeDelayHelper2 = new TimeDelayHelper(200);
    private boolean hide;

    public TextFieldItem(Canvas canvas, Visitor visitor, String label, String value, int maxSize, int layout, String altText, 
            BasicColor backgroundBasicColor, BasicColor foregroundBasicColor) {
        this(canvas, visitor, label, value, maxSize, layout, altText, Font.getDefaultFont(),
            backgroundBasicColor, foregroundBasicColor);
    }

    public TextFieldItem(Canvas canvas, Visitor visitor, String label, String value, int maxSize, int layout, String altText, 
            final Font font, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(label, layout, altText, backgroundBasicColor, foregroundBasicColor);

        this.logUtil.putF(commonStrings.START + font.getSize(), this, commonStrings.CONSTRUCTOR);
        
        this.stringComponent = new StringComponent(StringUtil.getInstance().EMPTY_STRING, font, backgroundBasicColor, foregroundBasicColor);
        
        this.maxSize = maxSize;
        
        this.stringComponent.setText(value);
        
        this.textFieldItemHelper = new TextFieldItemHelper(canvas, this, visitor);
        
    }

    @Override
    public boolean isFocusable()
    {
        return true;
    }
    
    /**
     * @return the string
     */
    public String getString()
    {
        return this.stringComponent.getText();
    }

    public void setString(String string)
    {
        if(string.length() < this.maxSize)
        {
            this.stringComponent.setText(string);
        }
    }

    public void setCaretVisible(boolean caretVisible)
    {
        this.logUtil.putF(commonStrings.START, this, "setCaretVisible:setFocus: " + caretVisible);
        this.caretVisible = caretVisible;
    }

    private int getCharPositionX(Graphics graphics)
    {
        //return graphics.getFont().stringWidth(this.stringComponent.getText().substring(0, this.getCaretPosition()));
        return this.stringComponent.getCharPositionX(this.getCaretPosition());
        //return positionX;
    }

    private int getCharPositionY()
    {
        return this.positionY;
    }

    @Override
    public int getHeight()
    {
        return super.getHeight() + this.stringComponent.getHeight() + 4;
    }
    
    @Override
    public void setFocus(boolean state)
    {
        this.logUtil.putF(commonStrings.START, this, "setFocus: " + state);
        
        this.caretVisible = state;
        
        if(state) {
            VirtualKeyboardEventHandler.getInstance().open();
        }

        super.setFocus(state);
    }
    
    @Override
    public void paint(Graphics graphics, int x, int y)
    {

        final MyFont myFont = MyFont.getInstance();
        final Font existingFont = graphics.getFont();
        this.fontDebugFactory.setFont(this.stringComponent.getFont(), graphics);
        
        int height = 0;
        
        final String label = this.getLabel();
        if(label.length() > 0)
        {
            height = this.stringComponent.getHeight() + 4;
        }
        
        super.paint(graphics, x, y);
        
        graphics.setColor(this.stringComponent.getBackgroundBasicColor().intValue());
        
        graphics.fillRect(x, y + height,
                myFont.defaultStringWidth(this.maxSize) * this.stringComponent.getFont().getSize() / this.defaultSize,
                //owner.getWidth() - 3, 
                stringComponent.getHeight());

        graphics.setColor(this.stringComponent.getForegroundBasicColor().intValue());
        graphics.drawString(this.stringComponent.getText(), x + 2, y + height, this.anchor);

        if (this.caretVisible)
        {
            final int x_pos = this.getCharPositionX(graphics);
            final int y_pos = this.getCharPositionY();
                //Font.getDefaultFont().getHeight()
            final int dx = x + x_pos + 2;
            final int dy = y + y_pos + height;
            final int caretWidth = (this.stringComponent.getFont().getSize() > 10) ? this.stringComponent.getFont().getSize() / 10 : 1;
            //final int caretHeight = y_pos + height + (AndroidUtil.isAndroid() ? stringComponent.getHeight() * 2 : stringComponent.getHeight());
            final int caretHeight = this.stringComponent.getHeight();
            
            if(this.timeDelayHelper.isTimeTNT()) {
                this.hide = true;
                this.timeDelayHelper2.setStartTimeTNT();
            }

            if(this.hide) { 
                if(this.timeDelayHelper2.isTimeTNT()) {
                    this.hide = false;
                }
            } else {
                graphics.fillRect(dx, dy, caretWidth, caretHeight);
            }

        }
        
        this.fontDebugFactory.setFont(existingFont, graphics);
    }

    @Override
    public void keyPressed(int keyCode)
    {
        //ForcedLogUtil.log(commonStrings.NOT_IMPLEMENTED, this);
        this.textFieldItemHelper.keyPressed(keyCode);
    }
    
    /**
     * @return the position
     */
    public int getCaretPosition()
    {
        return this.position;
    }

    /**
     * @param position the position to set
     */
    public void setCaretPosition(int position)
    {
        final String text = this.stringComponent.getText();
        if(position > text.length())
        {
            this.position = 0;
        }
        else
        if(position < 0)
        {
            this.position = text.length();
        }
        else
        {
            this.position = position;
        }
        
        //this.positionX = this.getCaretPosition() * (MyFont.MYFONT.DEFAULT_CHAR_WIDTH >> 1);
    }
}
