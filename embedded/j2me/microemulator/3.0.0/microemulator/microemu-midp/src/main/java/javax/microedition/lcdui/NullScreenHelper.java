package javax.microedition.lcdui;

/**
 *
 * @author User
 */
public class NullScreenHelper {
    
    private static final NullScreenHelper instance = new NullScreenHelper();

    /**
     * @return the instance
     */
    public static NullScreenHelper getInstance() {
        return NullScreenHelper.instance;
    }
    
    public final NullScreen NULL_SCREEN = new NullScreen();
    
    public Screen createNull() {
        return this.NULL_SCREEN;
    }
    
    public Displayable handleNull(final Displayable screen) {
        return screen;
    }
}
