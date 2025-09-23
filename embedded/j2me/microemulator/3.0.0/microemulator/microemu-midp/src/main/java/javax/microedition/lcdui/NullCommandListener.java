package javax.microedition.lcdui;

/**
 *
 * @author User
 */
public class NullCommandListener implements CommandListener {
 
    public static final NullCommandListener NULL_COMMAND_LISTENER = new NullCommandListener();
    
    @Override
    public void commandAction(Command c, Displayable d) {
        
    }
        
}
