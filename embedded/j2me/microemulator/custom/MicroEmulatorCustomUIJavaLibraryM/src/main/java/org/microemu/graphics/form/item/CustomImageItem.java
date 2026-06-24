/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.NullImage;
import javax.microedition.lcdui.Screen;

import org.allbinary.graphics.Anchor;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.MyFontProcessor;
import org.allbinary.graphics.font.UpdateMyFontInterface;
import org.allbinary.graphics.font.UpdateMyFontProcessor;
import org.allbinary.graphics.form.item.ABCustomItemInterface;
import org.allbinary.logic.string.StringUtil;
import org.microemu.graphics.form.CustomForm;

/**
 *
 * @author user
 */
public class CustomImageItem
    extends ImageItem
    implements ABCustomItemInterface, UpdateMyFontInterface {

    private final MyFontProcessor updateMyFontProcessor = new UpdateMyFontProcessor(this);
    private MyFontProcessor myFontProcessor = this.updateMyFontProcessor;
    
    private Screen owner = CustomForm.getNullForm();

    private BasicColor basicColor;

    protected int yOffset = 0;

    private int max;
    private int maxLabelLength;

    private String labelViewable = StringUtil.getInstance().EMPTY_STRING;

//    public CustomImageItem(String label, Image image, int layout, String altText, BasicColor basicColor) throws Exception {
//        this(label, image, layout, altText, basicColor, 0);
//    }

    public CustomImageItem(String label, Image image, int layout, String altText, BasicColor basicColor, int yOffset) throws Exception {
        super(label, image, layout, altText, ImageItem.PLAIN);

        this.basicColor = basicColor;
        this.yOffset = yOffset;

        if (image == NullImage.NULL_IMAGE) {
            throw new Exception("Image: " + image);
        }
    }

    @Override
    public void updateMeasurement(final Graphics graphics) {
        final Font font = graphics.getFont();

        this.max = this.getImage().getWidth() / MyFontProcessor.defaultCharWidth(font);

        final String labelSet = this.getLabel();
        if (this.max > labelSet.length()) {
            this.maxLabelLength = labelSet.length();
        } else {
            this.maxLabelLength = this.max;
        }

        this.labelViewable = labelSet.substring(0, this.maxLabelLength);

        this.myFontProcessor = MyFontProcessor.getInstance();
    }
        
    @Override
    public void setOwner(Screen owner) {
        this.owner = owner;
    }

    @Override
    public int getMinimumWidth() {
        return this.getImage().getWidth();
    }

    @Override
    public int getMinimumHeight() {
        return this.getImage().getHeight();
    }

    private int topCenterAnchor = Graphics.HCENTER | Graphics.TOP;
    private int anchor = Anchor.TOP_LEFT;

    @Override
    public void paintXY(Graphics graphics, int x, int y) {
        
        this.myFontProcessor.process(graphics);
        
        final Image image = this.getImage();
        final int layout = this.getLayout();
        // int height = image.getHeight();

        final int height = y;

        graphics.setColor(this.basicColor.intValue());

        if (layout == ImageItem.LAYOUT_DEFAULT || layout == ImageItem.LAYOUT_LEFT) {
            graphics.drawImage(image, x, height, this.anchor);
        } else if (layout == ImageItem.LAYOUT_RIGHT) {
            graphics.drawImage(image, this.owner.getWidth(), x, this.anchor);
        } else if (layout == ImageItem.LAYOUT_CENTER) {
            graphics.drawImage(image, (this.owner.getWidth() >> 1), x, this.topCenterAnchor);
        } else {
            graphics.drawImage(image, x, 0, this.anchor);
        }

        graphics.drawString(this.labelViewable, x + 2, y - this.yOffset, 0);
    }

    @Override
    public void paintUnselected(Graphics graphics, int x, int y) {
    }
}
