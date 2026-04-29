package org.microemu.app.capture;

import java.io.*;
import java.awt.*;
import java.awt.image.*;


/**
 * Class AnimatedGifEncoder - Encodes a GIF file consisting of one or
 * more frames.
 * <pre>
 * Example:
 *    AnimatedGifEncoder e = new AnimatedGifEncoder();
 *    e.start(outputFileName);
 *    e.setDelay(1000);   // 1 frame per sec
 *    e.addFrame(image1);
 *    e.addFrame(image2);
 *    e.finish();
 * </pre>
 * No copyright asserted on the source code of this class.  May be used
 * for any purpose, however, refer to the Unisys LZW patent for restrictions
 * on use of the associated LZWEncoder class.  Please forward any corrections
 * to kweiner@fmsware.com.
 *
 * @author Kevin Weiner, FM Software
 * @version 1.03 November 2003
 *
 */

public class AnimatedGifEncoder {

	protected int width; // image size
	protected int height;
	protected Color transparent = null; // transparent color if given
	protected int transIndex; // transparent index in color table
	protected int repeat = -1; // no repeat
	protected int delay = 0; // frame delay (hundredths)
	protected boolean started = false; // ready to output frames
	protected OutputStream out;
	protected BufferedImage image; // current frame
	protected byte[] pixels; // BGR byte array from frame
	protected byte[] indexedPixels; // converted frame indexed to palette
	protected int colorDepth; // number of bit planes
	protected byte[] colorTab; // RGB palette
	protected boolean[] usedEntry = new boolean[256]; // active palette entries
	protected int palSize = 7; // color table size (bits-1)
	protected int dispose = -1; // disposal code (-1 = use default)
	protected boolean closeStream = false; // close stream when finished
	protected boolean firstFrame = true;
	protected boolean sizeSet = false; // if false, get size from first frame
	protected int sample = 10; // default sample interval for quantizer

	/**
	 * Sets the delay time between each frame, or changes it
	 * for subsequent frames (applies to last frame added).
	 *
	 * @param ms int delay time in milliseconds
	 */
	public void setDelay(int ms) {
		delay = Math.round(ms / 10.0f);
	}
	
	/**
	 * Sets the GIF frame disposal code for the last added frame
	 * and any subsequent frames.  Default is 0 if no transparent
	 * color has been set, otherwise 2.
	 * @param code int disposal code.
	 */
	public void setDispose(int code) {
		if (code >= 0) {
			this.dispose = code;
		}
	}
	
	/**
	 * Sets the number of times the set of GIF frames
	 * should be played.  Default is 1; 0 means play
	 * indefinitely.  Must be invoked before the first
	 * image is added.
	 *
	 * @param iter int number of iterations.
	 * @return
	 */
	public void setRepeat(int iter) {
		if (iter >= 0) {
			this.repeat = iter;
		}
	}
	
	/**
	 * Sets the transparent color for the last added frame
	 * and any subsequent frames.
	 * Since all colors are subject to modification
	 * in the quantization process, the color in the final
	 * palette for each frame closest to the given color
	 * becomes the transparent color for that frame.
	 * May be set to null to indicate no transparent color.
	 *
	 * @param c Color to be treated as transparent on display.
	 */
	public void setTransparent(Color c) {
		transparent = c;
	}
	
	/**
	 * Adds next GIF frame.  The frame is not written immediately, but is
	 * actually deferred until the next frame is received so that timing
	 * data can be inserted.  Invoking <code>finish()</code> flushes all
	 * frames.  If <code>setSize</code> was not invoked, the size of the
	 * first image is used for all subsequent frames.
	 *
	 * @param im BufferedImage containing frame to write.
	 * @return true if successful.
	 */
	public boolean addFrame(BufferedImage im) {
		if ((im == null) || !this.started) {
			return false;
		}
		boolean ok = true;
		try {
			if (!this.sizeSet) {
				// use first frame's size
				this.setSize(im.getWidth(), im.getHeight());
			}
			this.image = im;
			this.getImagePixels(); // convert to correct format if necessary
			this.analyzePixels(); // build color table & map pixels
			if (this.firstFrame) {
				this.writeLSD(); // logical screen descriptior
				this.writePalette(); // global color table
				if (this.repeat >= 0) {
					// use NS app extension to indicate reps
					this.writeNetscapeExt();
				}
			}
			this.writeGraphicCtrlExt(); // write graphic control extension
			this.writeImageDesc(); // image descriptor
			if (!this.firstFrame) {
				this.writePalette(); // local color table
			}
			this.writePixels(); // encode and write pixel data
			this.firstFrame = false;
		} catch (IOException e) {
			ok = false;
		}

		return ok;
	}
	
	/**
	 * Flushes any pending data and closes output file.
	 * If writing to an OutputStream, the stream is not
	 * closed.
	 */
	public boolean finish() {
		if (!this.started) return false;
		boolean ok = true;
		this.started = false;
		try {
			this.out.write(0x3b); // gif trailer
			this.out.flush();
			if (this.closeStream) {
				this.out.close();
			}
		} catch (IOException e) {
			ok = false;
		}

		// reset for subsequent use
		this.transIndex = 0;
		this.out = null;
		this.image = null;
		this.pixels = null;
		this.indexedPixels = null;
		this.colorTab = null;
		this.closeStream = false;
		this.firstFrame = true;

		return ok;
	}
	
	/**
	 * Sets frame rate in frames per second.  Equivalent to
	 * <code>setDelay(1000/fps)</code>.
	 *
	 * @param fps float frame rate (frames per second)
	 */
	public void setFrameRate(float fps) {
		if (fps != 0f) {
			this.delay = Math.round(100f / fps);
		}
	}
	
	/**
	 * Sets quality of color quantization (conversion of images
	 * to the maximum 256 colors allowed by the GIF specification).
	 * Lower values (minimum = 1) produce better colors, but slow
	 * processing significantly.  10 is the default, and produces
	 * good color mapping at reasonable speeds.  Values greater
	 * than 20 do not yield significant improvements in speed.
	 *
	 * @param quality int greater than 0.
	 * @return
	 */
	public void setQuality(int quality) {
		if (quality < 1) quality = 1;
		this.sample = quality;
	}
	
	/**
	 * Sets the GIF frame size.  The default size is the
	 * size of the first frame added if this method is
	 * not invoked.
	 *
	 * @param w int frame width.
	 * @param h int frame width.
	 */
	public void setSize(int w, int h) {
		if (this.started && !this.firstFrame) return;
		this.width = w;
		this.height = h;
		if (this.width < 1) this.width = 320;
		if (this.height < 1) this.height = 240;
		this.sizeSet = true;
	}
	
	/**
	 * Initiates GIF file creation on the given stream.  The stream
	 * is not closed automatically.
	 *
	 * @param os OutputStream on which GIF images are written.
	 * @return false if initial write failed.
	 */
	public boolean start(OutputStream os) {
		if (os == null) return false;
		boolean ok = true;
		this.closeStream = false;
		this.out = os;
		try {
			this.writeString("GIF89a"); // header
		} catch (IOException e) {
			ok = false;
		}
		return this.started = ok;
	}
	
	/**
	 * Initiates writing of a GIF file with the specified name.
	 *
	 * @param file String containing output file name.
	 * @return false if open or initial write failed.
	 */
	public boolean start(String file) {
		boolean ok = true;
		try {
			this.out = new BufferedOutputStream(new FileOutputStream(file));
			ok = this.start(this.out);
			this.closeStream = true;
		} catch (IOException e) {
			ok = false;
		}
		return this.started = ok;
	}
	
	/**
	 * Analyzes image colors and creates color map.
	 */
	protected void analyzePixels() {
		int len = pixels.length;
		int nPix = len / 3;
		this.indexedPixels = new byte[nPix];
		NeuQuant nq = new NeuQuant(this.pixels, len, sample);
		// initialize quantizer
		this.colorTab = nq.process(); // create reduced palette
		// convert map from BGR to RGB
		for (int i = 0; i < this.colorTab.length; i += 3) {
			byte temp = this.colorTab[i];
			this.colorTab[i] = this.colorTab[i + 2];
			this.colorTab[i + 2] = temp;
			this.usedEntry[i / 3] = false;
		}
		// map image pixels to new palette
		int k = 0;
		for (int i = 0; i < nPix; i++) {
			int index =
				nq.map(pixels[k++] & 0xff,
					   pixels[k++] & 0xff,
					   pixels[k++] & 0xff);
			this.usedEntry[index] = true;
			this.indexedPixels[i] = (byte) index;
		}
		this.pixels = null;
		this.colorDepth = 8;
		this.palSize = 7;
		// get closest match to transparent color if specified
		if (this.transparent != null) {
			this.transIndex = this.findClosest(this.transparent);
		}
	}
	
	/**
	 * Returns index of palette color closest to c
	 *
	 */
	protected int findClosest(Color c) {
		if (this.colorTab == null) return -1;
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int minpos = 0;
		int dmin = 256 * 256 * 256;
		int len = this.colorTab.length;
		for (int i = 0; i < len;) {
			int dr = r - (this.colorTab[i++] & 0xff);
			int dg = g - (this.colorTab[i++] & 0xff);
			int db = b - (this.colorTab[i] & 0xff);
			int d = dr * dr + dg * dg + db * db;
			int index = i / 3;
			if (this.usedEntry[index] && (d < dmin)) {
				dmin = d;
				minpos = index;
			}
			i++;
		}
		return minpos;
	}
	
	/**
	 * Extracts image pixels into byte array "pixels"
	 */
	protected void getImagePixels() {
		int w = image.getWidth();
		int h = this.image.getHeight();
		int type = this.image.getType();
		if ((w != this.width)
			|| (h != this.height)
			|| (type != BufferedImage.TYPE_3BYTE_BGR)) {
			// create new image with right size/format
			BufferedImage temp =
				new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = temp.createGraphics();
			g.drawImage(this.image, 0, 0, null);
			this.image = temp;
		}
		this.pixels = ((DataBufferByte) this.image.getRaster().getDataBuffer()).getData();
	}
	
	/**
	 * Writes Graphic Control Extension
	 */
	protected void writeGraphicCtrlExt() throws IOException {
		this.out.write(0x21); // extension introducer
		this.out.write(0xf9); // GCE label
		this.out.write(4); // data block size
		int transp, disp;
		if (this.transparent == null) {
			transp = 0;
			disp = 0; // dispose = no action
		} else {
			transp = 1;
			disp = 2; // force clear if using transparent color
		}
		if (this.dispose >= 0) {
			disp = this.dispose & 7; // user override
		}
		disp <<= 2;

		// packed fields
		this.out.write(0 | // 1:3 reserved
			   disp | // 4:6 disposal
			      0 | // 7   user input - 0 = none
		     transp); // 8   transparency flag

		this.writeShort(delay); // delay x 1/100 sec
		this.out.write(transIndex); // transparent color index
		this.out.write(0); // block terminator
	}
	
	/**
	 * Writes Image Descriptor
	 */
	protected void writeImageDesc() throws IOException {
		this.out.write(0x2c); // image separator
		this.writeShort(0); // image position x,y = 0,0
		this.writeShort(0);
		this.writeShort(width); // image size
		this.writeShort(height);
		// packed fields
		if (this.firstFrame) {
			// no LCT  - GCT is used for first (or only) frame
			this.out.write(0);
		} else {
			// specify normal LCT
			this.out.write(0x80 | // 1 local color table  1=yes
						 0 | // 2 interlace - 0=no
						 0 | // 3 sorted - 0=no
						 0 | // 4-5 reserved
				   this.palSize); // 6-8 size of color table
		}
	}
	
	/**
	 * Writes Logical Screen Descriptor
	 */
	protected void writeLSD() throws IOException {
		// logical screen size
		this.writeShort(this.width);
		this.writeShort(this.height);
		// packed fields
		this.out.write((0x80 | // 1   : global color table flag = 1 (gct used)
				   0x70 | // 2-4 : color resolution = 7
				   0x00 | // 5   : gct sort flag = 0
			   this.palSize)); // 6-8 : gct size

		this.out.write(0); // background color index
		this.out.write(0); // pixel aspect ratio - assume 1:1
	}
	
	/**
	 * Writes Netscape application extension to define
	 * repeat count.
	 */
	protected void writeNetscapeExt() throws IOException {
		this.out.write(0x21); // extension introducer
		this.out.write(0xff); // app extension label
		this.out.write(11); // block size
		this.writeString("NETSCAPE" + "2.0"); // app id + auth code
		this.out.write(3); // sub-block size
		this.out.write(1); // loop sub-block id
		this.writeShort(repeat); // loop count (extra iterations, 0=repeat forever)
		this.out.write(0); // block terminator
	}
	
	/**
	 * Writes color table
	 */
	protected void writePalette() throws IOException {
		this.out.write(colorTab, 0, colorTab.length);
		int n = (3 * 256) - colorTab.length;
		for (int i = 0; i < n; i++) {
			this.out.write(0);
		}
	}
	
	/**
	 * Encodes and writes pixel data
	 */
	protected void writePixels() throws IOException {
		LZWEncoder encoder =
			new LZWEncoder(this.width, this.height, this.indexedPixels, this.colorDepth);
		encoder.encode(this.out);
	}
	
	/**
	 *    Write 16-bit value to output stream, LSB first
	 */
	protected void writeShort(int value) throws IOException {
		this.out.write(value & 0xff);
		this.out.write((value >> 8) & 0xff);
	}
	
	/**
	 * Writes string to output stream
	 */
	protected void writeString(String s) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			this.out.write((byte) s.charAt(i));
		}
	}
}