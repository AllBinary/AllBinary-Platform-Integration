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
import javax.microedition.lcdui.Image;
import org.allbinary.logic.communication.log.PreLogUtil;
import org.allbinary.string.CommonStrings;

/**
 *
 * @author Andres Navarro
 */


// Synchronization is important because of two things:
// first setStaticTileSet can change the whole Object
// so any function could be running at the same time as a setStaticTileSet
// and have inconsistent behaviour
// second is the animated tiles, their indexes should be consecutive and 
// two simultaneus createAnimatedTile methods could blow that up.
// One would expectt only one thread accessing this class at the same time
// but it becomes a little tricky when you consider that repaints access this
// class and you have no control or knowledge of the repaint thread on most
// systems

public class TiledLayer extends Layer {
    //protected final LogUtil logUtil = LogUtil.getInstance();

    private final int rowTotal, columnTotal;
    // package access for collision detection
    Image img;

    private int tileHeight, tileWidth, numStaticTiles;
    
    
    // the matrix for storing the tiles
    private int [][]tiles;
    
    // the list of anmated tiles
    // NOTE the first animatedTile (index -1) goes
    // into the first position in the array (index 0)
    // so to access the correct tile use animatedTiles[-n-1]
    int []animatedTiles;
    // the ammount of animated tiles
    int numAnimatedTiles;
    
    public TiledLayer(int cols, int rows, Image img, int tileWidth, int tileHeight) {
        // the specification doesn't states if the TiledLayer is visible on creation
        // we assume it is
        super(0, 0, cols * tileWidth, rows * tileHeight, true);
        
        if (img == null)
            throw new NullPointerException();
        if (cols <= 0 || rows <= 0 || tileHeight <= 0 || tileWidth <= 0)
            throw new IllegalArgumentException();
        if (img.getWidth() % tileWidth != 0 || img.getHeight() % tileHeight != 0)
            throw new IllegalArgumentException();
        
        this.img = img;
        this.columnTotal = cols;
        this.rowTotal = rows;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.numStaticTiles = (img.getWidth() / tileWidth) * (img.getHeight() / tileHeight);
        this.tiles = new int[rows][cols];
        this.animatedTiles = new int[5];
        this.numAnimatedTiles = 0;

    }
    
    // it is synchronized to avoid problems with the animatedTiles array and count
    public int createAnimatedTile(int staticTileIndex) {
    	//synchronized (this) {
	        if (staticTileIndex < 0 || staticTileIndex > numStaticTiles)
	            throw new IndexOutOfBoundsException();
	        
	        if (this.numAnimatedTiles == animatedTiles.length) {
	            int [] temp = new int [this.numAnimatedTiles + 6];
	            System.arraycopy(animatedTiles, 0, temp, 0, numAnimatedTiles);
	            animatedTiles = temp;
	        }
	        
	        animatedTiles[numAnimatedTiles] = staticTileIndex; 
	        numAnimatedTiles++;
	        return -numAnimatedTiles;
    	//}
    }
    
    public int getAnimatedTile(int index) {
    	//synchronized (this) {
	        index = -index-1;
                //System.out.println("getAnimatedTile: " + index);
	        if (index < 0 || index >= this.numAnimatedTiles)
	            throw new IndexOutOfBoundsException();
	        return animatedTiles[index];
    	//}
    }
    
    public void setAnimatedTile(int index, int staticTileIndex) {
    	//synchronized (this) {
	        index = -index-1;
	        if (index < 0 || index >= this.numAnimatedTiles)
	            throw new IndexOutOfBoundsException();
	        if (staticTileIndex < 0 || staticTileIndex > numStaticTiles)
	            throw new IndexOutOfBoundsException();
	        
	        animatedTiles[index] = staticTileIndex;
    	//}
    }
    
    public int getCell(int col, int row) {
        return this.tiles[row][col];
    }

//    boolean found;
    public void setCell(int col, int row, int index) {
    	//synchronized (this) {
	        if (-index-1 >= this.numAnimatedTiles || index > numStaticTiles)
	            throw new IndexOutOfBoundsException();
//                if(!found && index < 0) {
//                    found = true;
//                    System.out.println("AnimationTile: found: " + index);
//                }
	        tiles[row][col] = index;
    	//}
    }
    
    public void setStaticTileSet(Image image, int tileWidth, int tileHeight) {
    	//synchronized (this) {
	        if (this.img == null)
	            throw new NullPointerException();
	        if (tileHeight <= 0 || tileWidth <= 0)
	            throw new IllegalArgumentException();
	        if (this.img.getWidth() % tileWidth != 0 || this.img.getHeight() % tileHeight != 0)
	            throw new IllegalArgumentException();
	
	        int newNumStaticTiles = (this.img.getWidth() / getCellWidth()) * 
	                                    (this.img.getHeight() / getCellHeight());
	        
	        
	        // recalculate size
	        int w = columnTotal * tileWidth;
	        int h = rowTotal * tileHeight;
	        
	        setSize(w, h);
	        
	        this.img = img;
	        this.tileWidth = tileWidth;
	        this.tileHeight = tileHeight;
	        
	        if (newNumStaticTiles >= numStaticTiles) {
	            this.numStaticTiles = newNumStaticTiles;
	            return;
	        }
	        // if there are less static tiles
	        // all animated tiles are discarded and
	        // the tiledLayer is filled with tiles with index 0
	
	        this.numStaticTiles = newNumStaticTiles;
	        this.animatedTiles = new int[5];
	        this.numAnimatedTiles = 0;
	        this.fillCells(0, 0, getColumns(), getRows(), 0);
    	//}
    }

    public void fillCells(int col, int row, int numCols, int numRows, int index) {
    	//synchronized (this) {
	        if (numCols < 0 || numRows < 0)
	            throw new IllegalArgumentException();
	        if (row < 0 || col < 0 || col + numCols > this.columnTotal || row + numRows > this.rowTotal)    
	            throw new IndexOutOfBoundsException();
	        if (-index-1 >= this.numAnimatedTiles || index > numStaticTiles)
	            throw new IndexOutOfBoundsException();
	        
	        int maxRow = row + numRows;
	        int maxColumn = col + numCols;
	        for (int r = row; r < maxRow; r++) {
	            for (int c = col; c < maxColumn; c++) {
	                tiles[r][c] = index; 
	            }
	        }
    	//}
    }
    
    // dont need for synch here as columns are a constant
    // after creation
    public final int getColumns() {
        return columnTotal;
    }
    
    // dont need for synch here as rows are a constant
    // after creation
    public final int getRows() {
        return rowTotal;
    }
    
    public final int getCellWidth() {
        return tileWidth;
    }
    
    public final int getCellHeight() {
        return tileHeight;
    }
    
//    private int lastRow0 = Integer.MIN_VALUE;
//    private int lastMaxRow = Integer.MIN_VALUE;
//    private int lastColumn0 = Integer.MIN_VALUE;
//    private int lastMaxColumn = Integer.MIN_VALUE;

//    public void setPosition(int x, int y) {
//        if(x > 1800) {
//            throw new RuntimeException();
//        }
//        super.setPosition(x, x);
//    }

    public final void paint(final Graphics graphics) {
        
        try {
            
    	//synchronized (this) {
	        //if (!this.isVisible())
	            //return;
	        
	        int x = getX();
	        int y = getY();
	
	        int column0 = 0;
	        int row0 = 0;
	        int maxColumn = this.columnTotal;
	        int maxRow = this.rowTotal;
	        	        
	        //int cX = graphics.getClipX();
	        //int cY = graphics.getClipY();
	        int clipWidth = graphics.getClipWidth();
	        int clipHeight = graphics.getClipHeight();
	                        
	        // take out the columns and rows that are outside of
	        // the clip area, this should speed things up a bit
//	        int diff = cX - x;
//	        if (diff > 0)
//	            column0 += diff / tileWidth;
//	        
//	        diff = cX + clipWidth - (x + maxColumn*tileWidth);
//	        if (diff > 0)
//	            maxColumn -= diff / tileWidth;
//	
//	        diff = cY - y;
//	        if (diff > 0)
//	            row0 += diff / tileHeight;
//	        
//	        diff = cY + clipHeight - (x + maxRow*tileHeight);
//	        if (diff > 0)
//	            maxRow -= diff / tileHeight;

                int width = this.getWidth();
                int height = this.getHeight();

        if (width > clipWidth)
        {
            int clipX = graphics.getClipX();

            int diff = clipX - x;
            if (diff > tileWidth)
            {
                column0 = (diff / tileWidth) - 1;
                
//                if(this.lastColumn0 != column0) {
//                    this.lastColumn0 = column0;
//                    //logUtil.putF("column0: " + column0, this, "paint");
//                    System.out.println("column0: " + column0);
//                }

            }

            diff = width - ((clipX - x) + clipWidth);
            if (diff > tileWidth)
            {
                maxColumn -= (diff / tileWidth);
                
//                if(this.lastMaxColumn != maxColumn) {
//                    this.lastMaxColumn = maxColumn;
//                    
//                    //logUtil.putF("maxColumn: " + maxColumn, this, "paint");
//                    System.out.println("maxColumn: " + maxColumn);
//                    
//                    if(maxColumn == 2) {
//                        System.out.println(new StringBuilder().append("diff2: ").append(diff).append(" x: ").append(x).toString());
//                    } else {
//                        System.out.println(new StringBuilder().append("diff: ").append(diff).append(" x: ").append(x).toString());
//                    }
//                }

            }
        }

        if (height > clipHeight)
        {
            int clipY = graphics.getClipY();

            int diff = clipY - y;
            if (diff > tileHeight)
            {
                row0 = diff / tileHeight - 1;

//                if(lastRow0 != row0) {
//                    lastRow0 = row0;
//                    //logUtil.putF("row0: " + row0, this, "paint");
//                    System.out.println("row0: " + row0);
//                }

            }

            diff = height - ((clipY - y) + clipHeight);
            if (diff > tileHeight)
            {
                maxRow -= diff / tileHeight;
                
//                if(lastMaxRow != maxRow) {
//                    lastMaxRow = maxRow;
//                    //logUtil.putF("maxRow: " + maxRow, this, "paint");
//                    System.out.println("maxRow: " + maxRow);
//                }

            }
        }

	        int x0 = x;
	        int anchor = Graphics.LEFT | Graphics.TOP;
	        
	        int imgCols = this.img.getWidth() / tileWidth;
	        //int imgRows = img.getHeight() / tileHeight;
	                
                int tile = 0;
                int xSrc = 0;
                int ySrc = 0;

                y += (row0 * tileHeight);
	        for (int r = row0; r < maxRow; r++, y += tileHeight) {
                    x = x0 + (column0 * tileWidth);
	            for (int c = column0; c < maxColumn; c++, x += tileWidth) {
	                tile = this.tiles[r][c];
	                if (tile < 0)
	                    tile = getAnimatedTile(tile);
	                if (tile == 0)
	                    continue;
	                
	                tile--;
	                
	                xSrc = tileWidth * (tile % imgCols);
	                ySrc = (tile / imgCols) * tileHeight;
	                
	                graphics.drawRegion(this.img, xSrc, ySrc, tileWidth, tileHeight, Sprite.TRANS_NONE, x, y, anchor);
	            }
	        }
    	//}
        
        } catch(Exception e) {
            final CommonStrings commonStrings = CommonStrings.getInstance();
            PreLogUtil.putOE(commonStrings.EXCEPTION, this, commonStrings.EXCEPTION, e);
        }

    }
}
