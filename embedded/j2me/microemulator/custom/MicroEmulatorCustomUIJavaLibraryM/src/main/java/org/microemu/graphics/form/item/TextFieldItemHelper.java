/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Canvas;
import org.allbinary.game.input.GameInputStrings;


import org.allbinary.logic.string.StringUtil;
import org.allbinary.logic.communication.log.PreLogUtil;
import org.allbinary.logic.java.bool.BooleanFactory;
import org.allbinary.game.input.GameKey;
import org.allbinary.game.input.GameKeyFactory;
import org.allbinary.game.input.Input;
import org.allbinary.game.input.InputFactory;
import org.allbinary.game.input.PlatformFormInputMappingFactory;
import org.allbinary.game.input.PlatformKeyFactory;

import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.string.StringMaker;
import org.allbinary.logic.util.visitor.Visitor;

public class TextFieldItemHelper
{
    protected final LogUtil logUtil = LogUtil.getInstance();

    protected final GameInputStrings gameInputStrings = GameInputStrings.getInstance();
    
    private final Canvas canvas;
    private final TextFieldItem textFieldItem;
    private final Visitor textItemVisitor;
    
    public TextFieldItemHelper(final Canvas canvas, final TextFieldItem textFieldItem, final Visitor textItemVisitor)
    {
        this.canvas = canvas;
        this.textFieldItem = textFieldItem;
        this.textItemVisitor = textItemVisitor;
    }
    
    public void setString(final String text)
    {
        this.textFieldItem.setString(text);
        this.canvas.repaint();
    }
    
    private final GameKeyFactory gameKeyFactory = GameKeyFactory.getInstance();
    
    private final InputFactory inputFactory = InputFactory.getInstance();
    
    private final String EMPTY_STRING = StringUtil.getInstance().EMPTY_STRING;
    
    public boolean keyPressed(int keyCode)
    {
        try {
            
            //logUtil.put(commonStrings.START_LABEL + keyCode, this, gameInputStrings.KEY_PRESSED);
            //PreLogUtil.put(commonStrings.START_LABEL + keyCode, this, gameInputStrings.KEY_PRESSED);

            final PlatformKeyFactory platformKeyFactory
                = PlatformKeyFactory.getInstance();
            final StringUtil stringUtil = StringUtil.getInstance();

            final Input input = this.inputFactory.getInstance(keyCode);
            final String name = platformKeyFactory.getString(keyCode);

            PreLogUtil.put(new StringMaker().append("Input: ").append(stringUtil.toString(input)).append(" Name: ").append(name).toString(), this, gameInputStrings.KEY_PRESSED);

            if (name != this.EMPTY_STRING) {
                final GameKey gameKey
                    = PlatformFormInputMappingFactory.getInstance().getOrCreate().getInstance(keyCode);
                //( (InputToGameKeyMapping) 
                //      PlatformInputMappingFactory.getInstance()).getInstance(keyCode);

                //logUtil.putF("GameKey: " + gameKey, this, gameInputStrings.KEY_PRESSED);
                if (gameKey == this.gameKeyFactory.LEFT || platformKeyFactory.isLeft(input)) {
                    PreLogUtil.put("Position Change", this, gameInputStrings.KEY_PRESSED);
                    this.caretPositionChanged(this.textFieldItem.getCaretPosition() - 1);
                } else if (gameKey == gameKeyFactory.RIGHT || platformKeyFactory.isRight(input)) {
                    PreLogUtil.put("Position Change", this, gameInputStrings.KEY_PRESSED);
                    this.caretPositionChanged(this.textFieldItem.getCaretPosition() + 1);
                } else if (this.textItemVisitor.visit(name) == BooleanFactory.getInstance().TRUE) {
                    PreLogUtil.put("Appending Text", this, gameInputStrings.KEY_PRESSED);

                    this.appendText(name);
                } else if (platformKeyFactory.isDelete(input)) {
                    PreLogUtil.put("Deleting Text", this, gameInputStrings.KEY_PRESSED);

                    //PreLogUtil.put("Should Delete", this, gameInputStrings.KEY_PRESSED);
                    this.deleteAtText();
                } else if (platformKeyFactory.isBackSpace(input)) {
                    PreLogUtil.put("Deleting Before Text", this, gameInputStrings.KEY_PRESSED);
                    this.deleteBeforeText();
                } else {
                    return false;
                }
                /*
            else if (platformKeyFactory.isSubmission(input))
            {
            //ameKeyFactory.FIRE.getId().intValue()
            }
                 */
            }
            
        } catch(Exception e) {
            final CommonStrings commonStrings = CommonStrings.getInstance();
            this.logUtil.put(commonStrings.EXCEPTION, this, gameInputStrings.KEY_PRESSED, e);
        }
        return true;
    }
    
    private void setCaretPosition(int position)
    {
        //logUtil.putF("Position: " + position, this, "setCaretPosition");

        this.textFieldItem.setCaretPosition(position);

        /*
        if (tf.getCharPositionY(position) < viewPortY)
        {
        viewPortY = tf.getCharPositionY(position);
        }
        else if (tf.getCharPositionY(position) + tf.getCharHeight() > viewPortY + viewPortHeight - 6)
        {
        viewPortY = tmp.getCharPositionY(position) + tf.getCharHeight() - (viewPortHeight - 6);
        }
         */
    }

    private void caretPositionChanged(int position)
    {
        this.setCaretPosition(position);
        this.textFieldItem.setCaretVisible(true);
        this.canvas.repaint();
    }

    private void appendText(String string)
    {
        //tf.setCaretVisible(false);

        final String textFieldValue = this.textFieldItem.getString();
        final String start = textFieldValue.substring(0, this.textFieldItem.getCaretPosition());
        final String end = textFieldValue.substring(this.textFieldItem.getCaretPosition(), textFieldValue.length());

        this.textFieldItem.setString(start + string + end);
        //textFieldItem.setCaretPosition(string2.length());
        this.textFieldItem.setCaretPosition(start.length() + string.length());
        this.canvas.repaint();
    }

    private void deleteBeforeText()
    {
        final String textFieldValue = this.textFieldItem.getString();
        final int size = textFieldValue.length();
        if (size > 0 && this.textFieldItem.getCaretPosition() > 0)
        {
            final String start = textFieldValue.substring(0, this.textFieldItem.getCaretPosition() - 1);
            final String end = textFieldValue.substring(this.textFieldItem.getCaretPosition(), size);

            this.textFieldItem.setString(start + end);
            
            this.setCaretPosition(this.textFieldItem.getCaretPosition() - 1);
            
            this.canvas.repaint();
        }
    }

    private void deleteAtText()
    {
        final String textFieldValue = this.textFieldItem.getString();
        final int size = textFieldValue.length();
        if (size > 0 && this.textFieldItem.getCaretPosition() < size)
        {
            final String start = textFieldValue.substring(0, this.textFieldItem.getCaretPosition());
            final String end = textFieldValue.substring(this.textFieldItem.getCaretPosition() + 1, size);

            this.textFieldItem.setString(start + end);

            this.setCaretPosition(this.textFieldItem.getCaretPosition() - 1);

            this.canvas.repaint();
        }
    }    
}
