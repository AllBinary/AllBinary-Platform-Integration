/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.android.device;

import javax.microedition.lcdui.Font;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import org.allbinary.android.NullAndroidCanvas;
import org.allbinary.device.GraphicsInterface;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.FontDebugBase;
import org.allbinary.graphics.font.FontDebugFactory;
import org.allbinary.graphics.font.FontDebugFactoryBase;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;

/**
 *
 * @author user
 */
public class AndroidDisplayGraphicsBase
        extends javax.microedition.lcdui.Graphics 
        implements GraphicsInterface
{
    protected final LogUtil logUtil = LogUtil.getInstance();

    protected final CommonStrings commonStrings = CommonStrings.getInstance();
    
    private final FontDebugFactoryBase fontDebugFactory;
//    private final AndroidCanvasBase androidCanvas = AndroidCanvasFactory.getInstance().androidCanvas;
//    private final AndroidFontToPaintMapping androidFontToPaintMapping = AndroidFontToPaintMapping.instance;
    
    //Andres Navarro
    protected android.graphics.Canvas g = NullAndroidCanvas.NULL_CANVAS;

    //Paint is used for font info and fill style
    // protected final Paint paint = PaintSingleton.getInstance();
    protected final Paint paint = new Paint();
    protected final Paint strokeAndFillPaint = new Paint();
    protected final Paint strokePaint = new Paint();

    // protected MutableImage image;
    protected int colorP = 0;

    // TODO use IntHashMap
    // protected HashMap colorCache = new HashMap();
    protected Rect rect = new Rect();
    protected Rect dstRect = new Rect();
    protected RectF rectF = new RectF();

    //protected Rect clip = new Rect();
    protected javax.microedition.lcdui.Font currentFont = javax.microedition.lcdui.Font.getDefaultFont();

    protected final char[] CHAR_ARRAY = new char[1023];
    protected final char[] characterArray = new char[1];

    public AndroidDisplayGraphicsBase()
    {
        this.fontDebugFactory = FontDebugFactory.getInstance();
        
        //this.fontPaint = new Paint();

        this.strokeAndFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.paint.setStyle(Paint.Style.FILL);

        this.strokePaint.setStyle(Paint.Style.STROKE);
        this.strokePaint.setStrokeWidth(2.0f);

        this.setFont(this.currentFont);

        /*
         * Device device = DeviceFactory.getDevice(); AndroidFontManager
         * fontManager = (AndroidFontManager) device .getFontManager();
         * AndroidDeviceDisplay display = (AndroidDeviceDisplay) device
         * .getDeviceDisplay();
         */
    }

    public AndroidDisplayGraphicsBase(final android.graphics.Canvas a_g)
    {
        this(a_g, FontDebugFactory.getInstance());
    }

    public AndroidDisplayGraphicsBase(final android.graphics.Canvas a_g, final FontDebugFactoryBase fontDebugFactory)
    {
        this.fontDebugFactory = fontDebugFactory;
        
        //this.fontPaint = new Paint();

        this.setCanvas(a_g);

        this.strokeAndFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.paint.setStyle(Paint.Style.FILL);

        this.strokePaint.setStyle(Paint.Style.STROKE);
        this.strokePaint.setStrokeWidth(2.0f);

        this.setFont(this.currentFont);
    }

    @Override    
    public int getClipX()
    {
        //return clip.left;
        return 0;
    }

    @Override
    public int getClipY()
    {
        //return clip.top;
        return 0;
    }

    @Override
    public int getClipHeight()
    {
        return this.g.getHeight();
        //return clip.height();
    }

    @Override
    public int getClipWidth()
    {
        return this.g.getWidth();
        //return clip.width();
    }

    public void setCanvas(final android.graphics.Canvas a_g)
    {
        if(this.g != a_g)
        {
            this.g = a_g;
            // this.clip = a_g.getClipBounds();
            //a_g.getClipBounds(this.clip);
            throw new RuntimeException();
//            this.androidCanvas.save(this.g);
        }
    }

    public android.graphics.Canvas getCanvas()
    {
        return this.g;
    }

    @Override
    public int getColor()
    {
        return colorP;
    }

    /*
     public void setColor(int red, int green, int blue)
     {
     super.setColor(red, green, blue);
     this.setColor(0xff000000 | this.getColor());
     }
     */

    public void setBasicColorP(final BasicColor basicColor)
    {
        this.setColor(basicColor.intValue());
    }

    @Override
    public void setColor(final int RGB)
    {
        this.colorP = RGB;

        /*
         * TWB - Performance Improvement Color awtColor = (Color)
         * colorCache.get(Integer.valueOf(RGB)); if (awtColor == null) {
         * awtColor = new Color(RGB); colorCache.put(Integer.valueOf(RGB),
         * awtColor); } this.paint.setColor(awtColor.getRGB());
         * this.strokePaint.setColor(awtColor.getRGB());
         */
        this.paint.setColor(this.colorP);
        //this.fontPaint.setColor(color);
        this.strokePaint.setColor(this.colorP);
        this.strokeAndFillPaint.setColor(this.colorP);
    }

    @Override
    public javax.microedition.lcdui.Font getFont()
    {
        return currentFont;
    }

    @Override
    public void setFont(final Font font)
    {
        //logUtil.put("setFont", this, commonStrings.EXCEPTION, new Exception());
        this.setFont(font, fontDebugFactory.DEBUG);
    }

    public void setFontNoDebug(final Font font)
    {
        this.setFont(font, fontDebugFactory.NO_DEBUG);
    }

    @Override    
    public void setFont(final Font font, final FontDebugBase fontDebug)
    {   
        try
        {   
            this.currentFont = font;
            //fontPaint
            //logUtil.put("font: " + font, this, "setFont");
            //androidFontToPaintMapping.add(font, paint);

            throw new RuntimeException();
//            androidFontToPaintMapping.setTextSize(paint, font, fontDebug);
        }
        catch(Exception e)
        {
            this.logUtil.put(this.commonStrings.EXCEPTION, this, this.fontDebugFactory.NO_DEBUG.SET_FONT, e);
        }
    }

    // These 2 should not be called
    /*
     * // TWB hack public void clipRect(int x, int y, int width, int height) {
     * g.clipRect(x, y, width, height); clip = g.getClipBounds(); }
     * 
     * // TWB hack public void setClip(int x, int y, int width, int height) {
     * g.restore(); g.save(Canvas.CLIP_SAVE_FLAG); g.clipRect(x, y, x + width, y
     * + height); clip = g.getClipBounds(); // g.clipRect(x, y, width, height);
     * // clip.set(x, y, x + width, y + height); }
     */
}
