/*
 * Created on 2003-07-08
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.microemu.device;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.PostLoadImageProcessor;


public class MutableImage extends Image 
{
    protected MutableImage() {
        
    }

    public MutableImage(final String name, final PostLoadImageProcessor postLoadImageProcessor) {
        super(name, postLoadImageProcessor);
    }

	public int[] getData() {
            return null;
        }

}
