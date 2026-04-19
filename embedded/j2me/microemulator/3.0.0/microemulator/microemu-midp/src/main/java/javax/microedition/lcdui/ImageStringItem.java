/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 */
 
package javax.microedition.lcdui;


class ImageStringItem extends Item
{

	Image img = NullCanvas.NULL_IMAGE;
  StringComponent stringComponent;


  public ImageStringItem(String label, Image img, String text)
  {
    super(label);
		this.stringComponent = new StringComponent(text);
    setImage(img);
  }


	public Image getImage()
	{
    return img;
  }
    
    
	public void setImage(Image img)
	{
    this.img = img;
		if (this.img != NullCanvas.NULL_IMAGE) {
			this.stringComponent.setWidthDecreaser(img.getWidth() + 2);
		}
	}


	public String getText()
	{
		return stringComponent.getText();
	}


	public void setText(String text)
	{
		this.stringComponent.setText(text);
	}
	

        //TWB - made public
	public int getHeight()
	{
		if (this.img != NullCanvas.NULL_IMAGE && this.img.getHeight() > this.stringComponent.getHeight()) {
			return img.getHeight();
		} else {
			return stringComponent.getHeight();
		}
	}


  void invertPaint(boolean state)
  {
    this.stringComponent.invertPaint(state);
  }


  //TWB - made public
  public int paint(Graphics g)
  {
		if (this.stringComponent == null) {
			return 0;
		}

		if (this.img != NullCanvas.NULL_IMAGE) {
			g.drawImage(this.img, 0, 0, Graphics.LEFT | Graphics.TOP);
			g.translate(this.img.getWidth() + 2, 0);
		}

		int y = this.stringComponent.paint(g);

		if (this.img != NullCanvas.NULL_IMAGE) {
			g.translate(-this.img.getWidth() - 2, 0);
		}

		return y;
  }

}
