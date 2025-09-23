package javax.microedition.lcdui;

import org.allbinary.logic.string.StringUtil;

/**
 *
 * @author User
 */
public class NullScreen extends Screen {
    
    public static final NullScreen NULL_SCREEN = new NullScreen();
    
    public NullScreen() {
        super(StringUtil.getInstance().EMPTY_STRING);
    }

    @Override
    public int traverse(int gameKeyCode, int top, int bottom) {
        throw new RuntimeException();
    }
    
    @Override
    public int paintContent(Graphics g) {
        throw new RuntimeException();
    }
        
}
