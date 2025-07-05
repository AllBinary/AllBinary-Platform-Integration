/*-
 * #%L
 * This file is part of libtiled-java.
 * %%
 * Copyright (C) 2004 - 2020 Thorbjørn Lindeijer <thorbjorn@lindeijer.nl>
 * Copyright (C) 2004 - 2020 Adam Turk <aturk@biggeruniverse.com>
 * Copyright (C) 2016 - 2020 Mike Thomas <mikepthomas@outlook.com>
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.mapeditor.core;

import javax.microedition.lcdui.Image;
import org.allbinary.graphics.PointFactory;
import org.allbinary.graphics.Rectangle;

import org.allbinary.logic.string.StringUtil;

/**
 * An object occupying an {@link org.mapeditor.core.ObjectGroup}.
 *
 * @version 1.4.2
 */
public class MapObject extends MapObjectData implements Cloneable {

    private ObjectGroup objectGroup;
    //private Shape shape = new Rectangle2D.Double(0,0,0,0);
    //private Rectangle shape = new Rectangle(PointFactory.getInstance().ZERO_ZERO,0,0);
    private String imageSource;
    private Image image;
    private Image scaledImage;
    private Tile tile;
    private boolean flipHorizontal;
    private boolean flipVertical;
    private boolean flipDiagonal;

    /**
     * Constructor for MapObject.
     */
    public MapObject() {
        super();
        this.properties = new Properties();
        this.name = StringUtil.getInstance().EMPTY_STRING;
        this.type = StringUtil.getInstance().EMPTY_STRING;
        this.imageSource = StringUtil.getInstance().EMPTY_STRING;
        this.flipHorizontal = false;
        this.flipVertical = false;

        //System.out.println("TWB = MapObject");
    }

    /**
     * Constructor for MapObject.
     *
     * @param x a double.
     * @param y a double.
     * @param width a double.
     * @param height a double.
     * @param rotation a double.
     */
    public MapObject(double x, double y, double width, double height, double rotation) {
        this();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    /** {@inheritDoc} */
//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        MapObject clone = (MapObject) super.clone();
//        clone.properties = (Properties) properties.clone();
//        return clone;
//    }

    /**
     * Getter for the field <code>objectGroup</code>.
     *
     * @return the object group this object is part of
     */
    public ObjectGroup getObjectGroup() {
        return objectGroup;
    }

    /**
     * Sets the object group this object is part of. Should only be called by
     * the object group.
     *
     * @param objectGroup the object group this object is part of
     */
    public void setObjectGroup(ObjectGroup objectGroup) {
        this.objectGroup = objectGroup;
    }

    /**
     * Getter for the field <code>bounds</code>.
     *
     */
//    public Rectangle getBounds() {
//        System.out.println("TWB = MapObject:getBounds");
//        return new Rectangle(PointFactory.getInstance().getInstance((int) x, (int) y), (int) width.intValue(), (int) height.intValue());
//    }

    /**
     * Setter for the field <code>bounds</code>.
     *
     */
//      public void setBounds(Rectangle bounds) {
//          System.out.println("TWB = MapObject:setBounds");
//        this.x = bounds.getPoint().getX();
//        this.y = bounds.getPoint().getY();
//        this.width = new Double(bounds.getWidth());
//        this.height =  new Double(bounds.getHeight());
//    }

    /**
     * Getter for the field <code>shape</code>.
     *
     */
//    public Rectangle getShape() {
//        System.out.println("TWB = MapObject:getShape");
//        return shape;
//    }

    /**
     * Setter for the field <code>shape</code>.
     *
     */
//    public void setShape(Rectangle shape) {
//        System.out.println("TWB = MapObject:setShape");
//        this.shape = shape;
//    }

    /**
     * Getter for the field <code>imageSource</code>.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getImageSource() {
        return imageSource;
    }

    /**
     * Setter for the field <code>imageSource</code>.
     *
     * @param source a {@link java.lang.String} object.
     */
    public void setImageSource(String source) {
        if (imageSource.equals(source)) {
            return;
        }

        imageSource = source;

        // Attempt to read the image
        if (imageSource.length() > 0) {
            try {
                throw new RuntimeException();
                //image = ImageIO.read(new File(imageSource));
            } catch (Exception e) {
                image = null;
            }
        } else {
            image = null;
        }

        scaledImage = null;
    }

    /**
     * Getter for the field <code>tile</code>.
     *
     * @return a {@link org.mapeditor.core.Tile} object.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Setter for the field <code>tile</code>.
     *
     * @param tile a {@link org.mapeditor.core.Tile} object.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean getFlipHorizontal() { return flipHorizontal; }
    public void setFlipHorizontal(boolean flip) { this.flipHorizontal = flip; }

    public boolean getFlipVertical() { return flipVertical; }
    public void setFlipVertical(boolean flip) { this.flipVertical = flip; }

    public boolean getFlipDiagonal() { return flipDiagonal; }
    public void setFlipDiagonal(boolean flip) { this.flipDiagonal = flip; }

    /**
     * Returns the image to be used when drawing this object. This image is
     * scaled to the size of the object.
     *
     * @param zoom the requested zoom level of the image
     * @return the image to be used when drawing this object
     */
    public Image getImage(double zoom) {
        if (image == null) {
            return null;
        }

        final int zoomedWidth = (int) (getWidth() * zoom);
        final int zoomedHeight = (int) (getHeight() * zoom);

        if (scaledImage == null || scaledImage.getWidth() != zoomedWidth
                || scaledImage.getHeight() != zoomedHeight) {
            //scaledImage = image.getScaledInstance(zoomedWidth, zoomedHeight, Image.SCALE_SMOOTH);
            throw new RuntimeException();
        }

        return scaledImage;
    }

    /**
     * translate.
     *
     * @param dx a double.
     * @param dy a double.
     */
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return type + " (" + getX() + "," + getY() + ")";
    }
}
