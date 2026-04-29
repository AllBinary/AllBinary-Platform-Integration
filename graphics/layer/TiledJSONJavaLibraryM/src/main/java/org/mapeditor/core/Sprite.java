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
import org.allbinary.string.CommonSeps;
import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;

/**
 * Sprite class.
 *
 * @version 1.4.2
 */
public class Sprite {

    private BasicArrayList keys;
    private int borderWidth = 0;
    private int fpl = 0;
    private int totalKeys = -1;

    private float currentFrame = 0;
    private Rectangle frameSize;
    private boolean bPlaying = true;

    public class KeyFrame {

        public static final int MASK_ANIMATION = 0x0000000F;

        public static final int KEY_LOOP = 0x01;
        public static final int KEY_STOP = 0x02;
        public static final int KEY_AUTO = 0x04;
        public static final int KEY_REVERSE = 0x08;

        public static final int KEY_NAME_LENGTH_MAX = 32;

        private String name = null;
        private int id = -1;
        private int flags = KeyFrame.KEY_LOOP;
        private float frameRate = 1.0f;   //one fps
        private Tile[] frames;

        public KeyFrame() {
            flags = KEY_LOOP;
        }

        public KeyFrame(String name) {
            this();
            this.name = name;
        }

        public KeyFrame(String name, Tile[] tile) {
            this(name);
            frames = tile;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFrameRate(float r) {
            frameRate = r;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public int getLastFrame() {
            return this.frames.length - 1;
        }

        public boolean isFrameLast(int frame) {
            return this.frames.length - 1 == frame;
        }

        public void setFlags(int f) {
            flags = f;
        }

        public int getFlags() {
            return this.flags;
        }

        public String getName() {
            return this.name;
        }

        public Tile getFrame(int f) {
            if (f > 0 && f < this.frames.length) {
                return this.frames[f];
            }
            return null;
        }

        public float getFrameRate() {
            return this.frameRate;
        }

        public int getTotalFrames() {
            return this.frames.length;
        }

        public boolean equalsIgnoreCase(String n) {
            return this.name != null && this.name.equalsIgnoreCase(n);
        }

        @Override
        public String toString() {
            return "(" + this.name + ")" + this.id + ": @ " + this.frameRate;
        }
    }

    private KeyFrame currentKey = null;

    /**
     * Constructor for Sprite.
     */
    public Sprite() {
        frameSize = new Rectangle(PointFactory.getInstance().ZERO_ZERO, 0, 0);
        this.keys = new BasicArrayListD();
    }

    /**
     * Constructor for Sprite.
     *
     * @param frames an array of {@link org.mapeditor.core.Tile} objects.
     */
    public Sprite(Tile[] frames) {
        this.setFrames(frames);
    }

    /**
     * Constructor for Sprite.
     *
     * @param fpl a int.
     * @param border a int.
     * @param totalFrames a int.
     */
    public Sprite(Image image, int fpl, int border, int totalFrames) {
        Tile[] frames = null;
        this.fpl = fpl;
        this.borderWidth = border;

        //TODO: break up the image into tiles
        //given this information, extrapolate the rest...
        this.frameSize.setWidth(image.getWidth() / (fpl + this.borderWidth * fpl));
        this.frameSize.setHeight((int) (image.getHeight() / (Math.ceil(totalFrames / fpl) + Math.ceil(totalFrames / fpl) * this.borderWidth)));
        this.createKey(StringUtil.getInstance().EMPTY_STRING, frames, KeyFrame.KEY_LOOP);
    }

    /**
     * setFrames.
     *
     * @param frames an array of {@link org.mapeditor.core.Tile} objects.
     */
    public final void setFrames(Tile[] frames) {
        frameSize = new Rectangle(PointFactory.getInstance().ZERO_ZERO, frames[0].getWidth(), frames[0].getHeight());

        this.createKey(StringUtil.getInstance().EMPTY_STRING, frames, KeyFrame.KEY_LOOP);
    }

    /**
     * Setter for the field <code>frameSize</code>.
     *
     * @param w a int.
     * @param h a int.
     */
    public void setFrameSize(int w, int h) {
        this.frameSize.setWidth(w);
        this.frameSize.setHeight(h);
    }

    /**
     * Setter for the field <code>borderWidth</code>.
     *
     * @param b a int.
     */
    public void setBorderWidth(int b) {
        this.borderWidth = b;
    }

    /**
     * Setter for the field <code>fpl</code>.
     *
     * @param f a int.
     */
    public void setFpl(int f) {
        fpl = f;
    }

    /**
     * Setter for the field <code>currentFrame</code>.
     *
     * @param c a float.
     */
    public void setCurrentFrame(float c) {
        if (c < 0) {
            switch (this.currentKey.flags & KeyFrame.MASK_ANIMATION) {
                case KeyFrame.KEY_LOOP:
                    this.currentFrame = this.currentKey.getLastFrame();
                    break;
                case KeyFrame.KEY_AUTO:
                    this.currentKey = this.getPreviousKey();
                    this.currentFrame = this.currentKey.getLastFrame();
                    break;
                case KeyFrame.KEY_REVERSE:
                    this.currentKey.setFrameRate(-this.currentKey.getFrameRate());
                    this.currentFrame = 0;
                    break;
                case KeyFrame.KEY_STOP:
                    this.bPlaying = false;
                    this.currentFrame = 0;
                    break;
            }
        } else if (c > this.currentKey.getLastFrame()) {
            switch (this.currentKey.flags & KeyFrame.MASK_ANIMATION) {
                case KeyFrame.KEY_LOOP:
                    this.currentFrame = 0;
                    break;
                case KeyFrame.KEY_AUTO:
                    this.currentFrame = 0;
                    this.currentKey = this.getNextKey();
                    break;
                case KeyFrame.KEY_REVERSE:
                    this.currentKey.setFrameRate(-this.currentKey.getFrameRate());
                    this.currentFrame = this.currentKey.getLastFrame();
                    break;
                case KeyFrame.KEY_STOP:
                    this.bPlaying = false;
                    this.currentFrame = this.currentKey.getLastFrame();
                    break;
            }
        } else {
            this.currentFrame = c;
        }
    }

    /**
     * Setter for the field <code>totalKeys</code>.
     *
     * @param t a int.
     */
    public void setTotalKeys(int t) {
        this.totalKeys = t;
    }

    /**
     * Getter for the field <code>frameSize</code>.
     *
     */
    public Rectangle getFrameSize() {
        return this.frameSize;
    }

    /**
     * getTotalFrames.
     *
     * @return a int.
     */
    public int getTotalFrames() {
//        return keys.stream().
//                map(key -> key.getTotalFrames()).
//                reduce(0, Integer::sum);
        throw new RuntimeException();
    }

    /**
     * Getter for the field <code>borderWidth</code>.
     *
     * @return a int.
     */
    public int getBorderWidth() {
        return this.borderWidth;
    }

    /**
     * Getter for the field <code>currentFrame</code>.
     *
     * @return a {@link org.mapeditor.core.Tile} object.
     */
    public Tile getCurrentFrame() {
        return this.currentKey.getFrame((int) this.currentFrame);
    }

    /**
     * getNextKey.
     *
     * @return a {@link org.mapeditor.core.Sprite.KeyFrame} object.
     */
    public KeyFrame getNextKey() {
        final int size = keys.size();
        KeyFrame k;
        int index = this.keys.indexOf(this.currentKey);
        if(index < size - 1) {
            return (KeyFrame) this.keys.get(index + 1);
        }
//        for (int index = 0; index < size; index++) {
//            k = (KeyFrame) keys.get(index);
//            if (k == currentKey && index < size - 1) {
//                return (KeyFrame) keys.get(index + 1);
//            }
//        }

        return (KeyFrame) this.keys.get(0);
    }

    /**
     * getPreviousKey.
     *
     * @return a {@link org.mapeditor.core.Sprite.KeyFrame} object.
     */
    public KeyFrame getPreviousKey() {
        //TODO: this
        return null;
    }

    /**
     * Getter for the field <code>currentKey</code>.
     *
     * @return a {@link org.mapeditor.core.Sprite.KeyFrame} object.
     */
    public KeyFrame getCurrentKey() {
        return this.currentKey;
    }

    /**
     * getFPL.
     *
     * @return a int.
     */
    public int getFPL() {
        return this.fpl;
    }

    /**
     * Getter for the field <code>totalKeys</code>.
     *
     * @return a int.
     */
    public int getTotalKeys() {
        return this.keys.size();
    }

    /**
     * setKeyFrameTo.
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setKeyFrameTo(String name) {
        final int size = keys.size();
        KeyFrame k;
        for (int index = 0; index < size; index++) {
            k = (KeyFrame) this.keys.get(index);
            if (k.equalsIgnoreCase(name)) {
                this.currentKey = k;
                break;
            }
        }
    }

    /**
     * addKey.
     *
     * @param k a {@link org.mapeditor.core.Sprite.KeyFrame} object.
     */
    public void addKey(KeyFrame k) {
        this.keys.add(k);
    }

    /**
     * removeKey.
     *
     * @param name a {@link java.lang.String} object.
     */
    public void removeKey(String name) {
        this.keys.remove(getKey(name));
    }

    /**
     * createKey.
     *
     * @param name a {@link java.lang.String} object.
     * @param frames an array of {@link org.mapeditor.core.Tile} objects.
     * @param flags a int.
     */
    public final void createKey(String name, Tile[] frames, int flags) {
        KeyFrame kf = new KeyFrame(name, frames);
        kf.setName(name);
        kf.setFlags(flags);
        this.addKey(kf);
    }

    /**
     * iterateFrame.
     */
    public void iterateFrame() {
        if (this.currentKey != null && this.bPlaying) {
            this.setCurrentFrame(this.currentFrame + this.currentKey.getFrameRate());
        }
    }

    /**
     * Sets the current frame relative to the starting frame of the current key.
     *
     * @param c a int.
     */
    public void keySetFrame(int c) {
        this.setCurrentFrame(c);
    }

    /**
     * play.
     */
    public void play() {
        bPlaying = true;
    }

    /**
     * stop.
     */
    public void stop() {
        bPlaying = false;
    }

    /**
     * keyStepBack.
     *
     * @param amt a int.
     */
    public void keyStepBack(int amt) {
        this.setCurrentFrame(currentFrame - amt);
    }

    /**
     * keyStepForward.
     *
     * @param amt a int.
     */
    public void keyStepForward(int amt) {
        this.setCurrentFrame(currentFrame + amt);
    }

    /**
     * getKey.
     *
     * @param keyName a {@link java.lang.String} object.
     * @return a {@link org.mapeditor.core.Sprite.KeyFrame} object.
     */
    public KeyFrame getKey(String keyName) {
        final int size = keys.size();
        KeyFrame k;
        for (int index = 0; index < size; index++) {
            k = (KeyFrame) this.keys.get(index);
            if (k != null && k.equalsIgnoreCase(keyName)) {
                return k;
            }
        }
        return null;
    }

    /**
     * getKey.
     *
     * @param i a int.
     * @return a {@link org.mapeditor.core.Sprite.KeyFrame} object.
     */
    public KeyFrame getKey(int i) {
        return (KeyFrame) this.keys.get(i);
    }

    /**
     * Getter for the field <code>keys</code>.
     *
     * @return a {@link java.util.Iterator} object.
     * @throws java.lang.Exception if any.
     */
//    public Iterator<KeyFrame> getKeys() throws Exception {
//        return keys.iterator();
//    }

    /**
     * getCurrentFrameRect.
     *
     */
    public Rectangle getCurrentFrameRect() {
        int x = 0, y = 0;

        if (this.frameSize.getHeight() > 0 && this.frameSize.getWidth() > 0) {
            y = ((int) this.currentFrame / this.fpl) * (this.frameSize.getHeight() + this.borderWidth);
            x = ((int) this.currentFrame % this.fpl) * (this.frameSize.getWidth() + this.borderWidth);
        }

        return new Rectangle(PointFactory.getInstance().createXY(x, y), frameSize.getWidth(), frameSize.getHeight());
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Frame: (" + this.frameSize.getWidth() + "x" + this.frameSize.getHeight() + ")\n"
                + "Border: " + this.borderWidth + CommonSeps.getInstance().NEW_LINE
                + "FPL: " + this.fpl + CommonSeps.getInstance().NEW_LINE
                + "Total Frames: " + this.getTotalFrames() + CommonSeps.getInstance().NEW_LINE
                + "Total keys: " + this.totalKeys;
    }
}
