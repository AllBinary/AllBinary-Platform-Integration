package org.microemu.microedition.io;

import java.io.IOException;

import javax.microedition.io.Connection;

public class ConnectorImpl extends ConnectorAdapter {

    public ConnectorImpl() {
    }

    @Override
    public Connection open(final String name, final int mode, final boolean timeouts) throws IOException {
        throw new RuntimeException();
    }

    private Connection openSecure(final String name, final int mode, final boolean timeouts) throws IOException {
        throw new RuntimeException();
    }
}
