/*
 *  MicroEmulator
 *  Copyright (C) 2005 Andres Navarro
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
 *
 *  Other Contributor(s):
 *    Travis Berthelot
 */
package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;

import org.allbinary.graphics.color.BasicColorFactory;
import org.allbinary.layer.Layer;

/**
 * 
 */
public class SimpleTiledLayer extends Layer
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    private final int totalRows, totalColumns;
    private final int tileHeight, tileWidth;

    public SimpleTiledLayer(int cols, int rows, int tileWidth, int tileHeight)
    {
        super(cols * tileWidth, rows * tileHeight);

        //if (cols <= 0 || rows <= 0 || tileHeight <= 0 || tileWidth <= 0)
          //  throw new IllegalArgumentException();

        this.totalColumns = cols;
        this.totalRows = rows;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;        
    }
    
    public final int getColumns()
    {
        return this.totalColumns;
    }

    public final int getRows()
    {
        return this.totalRows;
    }

    public final int getCellWidth()
    {
        return this.tileWidth;
    }

    public final int getCellHeight()
    {
        return this.tileHeight;
    }

    private final int color = BasicColorFactory.getInstance().TRANSPARENT_BLACK.intValue();
    
    public final void paint(final Graphics graphics, final short[][] tiles)
    {
        int column0 = 0;
        int row0 = 0;
        int maxColumn = this.totalColumns;
        int maxRow = this.totalRows;

        int x0 = this.getXP() - this.tileWidth;
        int x = x0;
        int y = this.getYP() - this.tileHeight;

        final int clipWidth = graphics.getClipWidth();
        final int clipHeight = graphics.getClipHeight();

        final int width = this.getWidth();
        final int height = this.getHeight();
        
        if (width > clipWidth)
        {
            int clipX = graphics.getClipX();

            int diff = clipX - x;
            if (diff > this.tileWidth)
            {
                column0 = diff / this.tileWidth - 1;
                // logUtil.putF("column0: " + column0, this, "paint");
            }

            diff = width - ((clipX - x) + clipWidth);
            if (diff > this.tileWidth)
            {
                maxColumn -= diff / this.tileWidth + 1;
                //logUtil.putF("maxColumn: " + maxColumn, this, "paint");
                //System.out.println("maxColumn: " + maxColumn);
            }
        }

        if (height > clipHeight)
        {
            int clipY = graphics.getClipY();

            int diff = clipY - y;
            if (diff > this.tileHeight)
            {
                row0 = diff / this.tileHeight - 1;
                
                // logUtil.putF("row0: " + row0, this, "paint");
            }

            diff = height - ((clipY - y) + clipHeight);
            if (diff > this.tileHeight)
            {
                maxRow -= diff / this.tileHeight + 1;
                //System.out.println("maxRow: " + maxRow);
                //logUtil.putF("maxRow: " + maxRow, this, "paint");
            }
        }

        y += (row0 * this.tileHeight);

        int originalColor = graphics.getColor();
        
        graphics.setColor(this.color);
        
        int cell;
        for (int rowIndex = row0; rowIndex < maxRow; rowIndex++)
        {
            y += this.tileHeight;
            x = x0 + (column0 * this.tileWidth);
            for (int columnIndex = column0; columnIndex < maxColumn; columnIndex++)
            {
                x += this.tileWidth;

                cell = (int) tiles[rowIndex][columnIndex];
                if (cell == 0)
                {
                    graphics.fillRect(x, y, this.tileWidth, this.tileHeight);
                }
            }
        }
        
        graphics.setColor(originalColor);
    }
}
