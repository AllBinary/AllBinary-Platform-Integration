/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form;

import javax.microedition.lcdui.Graphics;

import org.allbinary.game.input.GameInputStrings;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.ForcedLogUtil;
import org.allbinary.graphics.color.BasicColor;

public class CustomScreen extends CustomDisplayable
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    protected final CommonStrings commonStrings = CommonStrings.getInstance();
    protected final GameInputStrings gameInputStrings = GameInputStrings.getInstance();
    
    CustomScreen(String title, BasicColor backgroundBasicColor, BasicColor foregroundBasicColor)
    {
        super(title, backgroundBasicColor, foregroundBasicColor);
    }

    /*
    void scroll(int gameKeyCode) {
    	viewPortY += traverse(gameKeyCode, viewPortY, viewPortY + viewPortHeight);
    }
    */
    
	int traverse(int gameKeyCode, int top, int bottom)
	{
        //abstract
        ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);
	    return 0;
	}

	/*
	public void keyPressed(int keyCode) 
	{
		try
		{
		    logUtil.putF(this.commonStrings.START, this, gameInputStrings.KEY_PRESSED);

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
		    logUtil.put(this.commonStrings.EXCEPTION, this, gameInputStrings.KEY_PRESSED, e);
		}
	}
	*/

	@Override
	void hideNotify() 
	{
		super.hideNotify();
	}

        @Override
	public void keyRepeated(int keyCode) 
	{
		keyPressed(keyCode);
	}

        @Override
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
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(this.title.getForegroundBasicColor().intValue());

		g.translate(0, contentHeight);
		translatedY = contentHeight;

        // TODO move to Displayable
		contentHeight += title.paint(g);
		g.drawLine(0, title.getHeight() + 4, getWidth(), title.getHeight() + 4);
		contentHeight += 5;

		g.translate(0, contentHeight - translatedY);
		translatedY = contentHeight;

		//g.setClip(0, 0, getWidth(), getHeight() - contentHeight);
		
		//g.translate(0, -viewPortY);
		
		contentHeight += paintContent(g);
		
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
	

	int paintContent(Graphics g)
	{
        //abstract
	    ForcedLogUtil.log(this.commonStrings.NOT_IMPLEMENTED, this);

	    return 0;
	}

}