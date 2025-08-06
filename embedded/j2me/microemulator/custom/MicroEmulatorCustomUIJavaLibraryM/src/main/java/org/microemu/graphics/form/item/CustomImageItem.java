/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.graphics.form.item;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.NullCanvas;
import javax.microedition.lcdui.Screen;

import org.allbinary.graphics.Anchor;
import org.allbinary.graphics.color.BasicColor;
import org.allbinary.graphics.font.MyFont;

/**
 *
 * @author user
 */
public class CustomImageItem
    extends ImageItem
    implements org.allbinary.graphics.form.item.CustomItemInterface {

    private Screen owner = NullCanvas.NULL_SCREEN;

    private BasicColor basicColor;

    protected int yOffset = 0;

    private int max;
    private int maxLabelLength;

    private final String labelViewable;

    public CustomImageItem(String label, Image image, int layout, String altText, BasicColor basicColor) throws Exception {
        this(label, image, layout, altText, basicColor, 0);
    }

    public CustomImageItem(String label, Image image, int layout, String altText, BasicColor basicColor, int yOffset) throws Exception {
        super(label, image, layout, altText, ImageItem.PLAIN);

        this.basicColor = basicColor;
        this.yOffset = yOffset;

        if (image == null) {
            throw new Exception("Image: " + image);
        }

        this.max = image.getWidth() / MyFont.getInstance().charWidth();

        if (max > this.getLabel().length()) {
            maxLabelLength = this.getLabel().length();
        } else {
            maxLabelLength = max;
        }

        this.labelViewable = this.getLabel().substring(0, maxLabelLength);
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
    public void paint(Graphics graphics, int x, int y) {
        Image image = this.getImage();
        int layout = this.getLayout();
        // int height = image.getHeight();

        int height = y;

        graphics.setColor(this.basicColor.intValue());

        if (layout == LAYOUT_DEFAULT || layout == LAYOUT_LEFT) {
            graphics.drawImage(image, x, height, anchor);
        } else if (layout == LAYOUT_RIGHT) {
            graphics.drawImage(image, owner.getWidth(), x, anchor);
        } else if (layout == LAYOUT_CENTER) {
            graphics.drawImage(image, (owner.getWidth() >> 1), x, topCenterAnchor);
        } else {
            graphics.drawImage(image, x, 0, anchor);
        }

        graphics.drawString(labelViewable, x + 2, y - yOffset, 0);
    }

    @Override
    public void paintUnselected(Graphics graphics, int x, int y) {
    }
}
