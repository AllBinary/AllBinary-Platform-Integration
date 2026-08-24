/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form;

import jsinterop.annotations.JsType;

import javax.microedition.lcdui.Graphics;

import org.allbinary.game.input.GameInputStrings;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.ForcedLogUtil;
import org.allbinary.graphics.color.BasicColor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;


@JsType
public class CustomScreen extends CustomDisplayable
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    @JsProperty
    protected final CommonStrings commonStrings = CommonStrings.getInstance();
    @JsProperty
    protected final GameInputStrings gameInputStrings = GameInputStrings.getInstance();
    
    @JsConstructor
    CustomScreen(String title, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(title, backgroundBasicColor, foregroundBasicColor);
    }

    /*
    void scroll(int gameKeyCode) {
    	viewPortY += traverse(gameKeyCode, viewPortY, viewPortY + viewPortHeight);
    }
    */
    
    //abstract
	@JsMethod
	int traverse(int gameKeyCode, int top, int bottom)
	{
            ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
	    return 0;
	}

	/*
	public void keyPressed(int keyCode) 
	{
		try
		{
		    this.logUtil.putF(this.commonStrings.START, this, gameInputStrings.KEY_PRESSED);

		//int key = AndroidNumberKeyToJ2MECanvasNumberKey.getKey(keyCode);
		
		//int gameKeyCode = Display.getGameAction(keyCode);

		//if (gameKeyCode == Canvas.UP || gameKeyCode == Canvas.DOWN)
		if (keyCode == Canvas.UP || keyCode == Canvas.DOWN)
		{
			//viewPortY += traverse(gameKeyCode, viewPortY, viewPortY + viewPortHeight);
		}

	    }
		catch(Exception e)
		{
		    this.logUtil.put(this.commonStrings.EXCEPTION, this, gameInputStrings.KEY_PRESSED, e);
		}
	}
	*/

	@Override
	void hideNotify() 
	{
		super.hideNotify();
	}

        @Override
	       @JsMethod
	       public void keyRepeated(int keyCode) 
	{
		this.keyPressed(keyCode);
	}

        @Override
	       @JsMethod
	       public void paint(Graphics g) 
	{
		//logUtil.putF(commonStrings.START, this, "paint");
		
	    int contentHeight = 0;
        int translatedY = 0;

		/*
		if (viewPortY == 0) {
			this.setScrollUp(false);
		} else {
			this.setScrollUp(true);
		}
		*/
		
		g.setColor(this.title.getBackgroundBasicColor().intValue());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(this.title.getForegroundBasicColor().intValue());

		g.translate(0, contentHeight);
		translatedY = contentHeight;

        // TODO move to Displayable
		contentHeight += this.title.paint(g);
		g.drawLine(0, this.title.getHeight() + 4, this.getWidth(), this.title.getHeight() + 4);
		contentHeight += 5;

		g.translate(0, contentHeight - translatedY);
		translatedY = contentHeight;

		//g.setClip(0, 0, getWidth(), getHeight() - contentHeight);
		
		//g.translate(0, -viewPortY);
		
		contentHeight += this.paintContent(g);
		
		/*
		g.translate(0, viewPortY);

		if (contentHeight - viewPortY > getHeight()) {
			currentDisplay.setScrollDown(true);
		} else {
			currentDisplay.setScrollDown(false);
		}
		*/
		
		g.translate(0, -translatedY);
	}
	

	@JsMethod
	int paintContent(Graphics g)
	{
        //abstract
	    ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);

	    return 0;
	}

}