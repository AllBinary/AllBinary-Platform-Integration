package javax.microedition.lcdui;

/**
 *
 * @author User
 */
public class NullItemCommandListener implements ItemCommandListener {
    
    public static final NullItemCommandListener NULL_ITEM_COMMAND_LISTENER = new NullItemCommandListener();
    
    @Override
    public void commandAction(Command c, Item item) {
        
    }

}
