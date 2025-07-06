/*
 *  MicroEmulator
 *  Copyright (C) 2005 Andres Navarro
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
        return totalColumns;
    }

    public final int getRows()
    {
        return totalRows;
    }

    public final int getCellWidth()
    {
        return tileWidth;
    }

    public final int getCellHeight()
    {
        return tileHeight;
    }

    private final int color = BasicColorFactory.getInstance().TRANSPARENT_BLACK.intValue();
    
    public final void paint(Graphics graphics, short[][] tiles)
    {
        int column0 = 0;
        int row0 = 0;
        int maxColumn = totalColumns;
        int maxRow = totalRows;

        int x0 = this.getX() - tileWidth;
        int x = x0;
        int y = this.getY() - tileHeight;

        int clipWidth = graphics.getClipWidth();
        int clipHeight = graphics.getClipHeight();

        int width = this.getWidth();
        int height = this.getHeight();
        
        if (width > clipWidth)
        {
            int clipX = graphics.getClipX();

            int diff = clipX - x;
            if (diff > tileWidth)
            {
                column0 = diff / tileWidth - 1;
                // LogUtil.put(LogFactory.getInstance("column0: " + column0, this, "paint"));
            }

            diff = width - ((clipX - x) + clipWidth);
            if (diff > tileWidth)
            {
                maxColumn -= diff / tileWidth + 1;
                //LogUtil.put(LogFactory.getInstance("maxColumn: " + maxColumn, this, "paint"));
                //System.out.println("maxColumn: " + maxColumn);
            }
        }

        if (height > clipHeight)
        {
            int clipY = graphics.getClipY();

            int diff = clipY - y;
            if (diff > tileHeight)
            {
                row0 = diff / tileHeight - 1;
                
                // LogUtil.put(LogFactory.getInstance("row0: " + row0, this, "paint"));
            }

            diff = height - ((clipY - y) + clipHeight);
            if (diff > tileHeight)
            {
                maxRow -= diff / tileHeight + 1;
                //System.out.println("maxRow: " + maxRow);
                //LogUtil.put(LogFactory.getInstance("maxRow: " + maxRow, this, "paint"));
            }
        }

        y += (row0 * tileHeight);

        int originalColor = graphics.getColor();
        
        graphics.setColor(color);
        
        for (int rowIndex = row0; rowIndex < maxRow; rowIndex++)
        {
            y += tileHeight;
            x = x0 + (column0 * tileWidth);
            for (int columnIndex = column0; columnIndex < maxColumn; columnIndex++)
            {
                x += tileWidth;

                if (tiles[rowIndex][columnIndex] == 0)
                {
                    graphics.fillRect(x, y, tileWidth, tileHeight);
                }
            }
        }
        
        graphics.setColor(originalColor);
    }
}
