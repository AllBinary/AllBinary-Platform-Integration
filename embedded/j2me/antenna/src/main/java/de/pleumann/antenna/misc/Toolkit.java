package de.pleumann.antenna.misc;

import java.io.IOException;
import java.util.Properties;

/**
 * User: Erik Wetterberg, erik@upper88.com
 * Date: 2009-jun-15
 */
public class Toolkit {
    /**
     * Holds a printable name for the toolkit.
     */
    public final String name;
    public final Properties props;
    public static final String NAME = "name";
    public static final String PREVERIFYVERSION = "preverifyversion";
    public static final String EMULATOR = "emulator";
    public static final String DEVICE = "device";
    public static final String INCLUDE = "include";

    public Toolkit(String filename) {
        props = new Properties();
        loadProps(filename);
        this.name = props.getProperty(NAME, "invalid");
        if (props.containsKey(INCLUDE)) {
            loadProps(props.getProperty(INCLUDE));
        }
    }

    private void loadProps(String filename) {
        try {
            props.load(getClass().getResourceAsStream("/toolkit/" + filename + ".properties"));
        } catch (IOException e) {

        }
    }

    public Toolkit(String name, String api) {
        this.name = name;
        this.props = new Properties();
        props.setProperty("api", api);
    }
}
