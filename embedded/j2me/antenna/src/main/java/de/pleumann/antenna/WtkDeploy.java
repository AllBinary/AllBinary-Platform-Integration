/* -----------------------------------------------------------------------------
 * Antenna - An Ant-to-end solution for wireless Java 
 *
 * Copyright (c) 2002-2004 Joerg Pleumann <joerg@pleumann.de>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * -----------------------------------------------------------------------------
 */
package de.pleumann.antenna;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tools.ant.*;

import de.pleumann.antenna.misc.*;

/**
 * Deployment task.
 */
public class WtkDeploy extends Task {

	private File jarFile;

	private File jadFile;
	
	private String encoding;

	private String target;

	private boolean delete;

    private String login;

    private String password;

    private Conditional condition;

    @Override
    public void init() throws BuildException {
        super.init();
        this.condition = new Conditional(this.getProject());
    }

	public void setJarfile(File file) {
		this.jarFile = file;
	}

	public void setJadfile(File file) {
		this.jadFile = file;
	}
	
	public void setEncoding(String encoding) {
	    this.encoding = encoding;
	}

	public void setTarget(String s) {
		this.target = s;
	}

	public void setDelete(boolean b) {
		this.delete = b;
	}

    public void setLogin(String s) {
        this.login = s;
    }

    public void setPassword(String s ) {
        this.password = s;
    }

    public void setIf(String s) {
        this.condition.setIf(s);
    }
    
    public void setUnless(String s) {
        this.condition.setUnless(s);
    }

    public boolean isActive() {
        return this.condition.isActive();
    }

	@Override
	public void execute() throws BuildException {
        if (!this.isActive()) return;

		try {
			if (this.jarFile == null || !this.jarFile.exists()) {
				throw new IllegalArgumentException("Need a JAR file.");
			}

			if (this.jadFile == null || !this.jadFile.exists()) {
				throw new IllegalArgumentException("Need a JAD file.");
			}

			if (this.target == null) {
                JadFile jad = new JadFile();
                jad.load(this.jadFile.getAbsolutePath(), this.encoding);
                
                String s = jad.getValue("MIDlet-Jar-URL");
                if (s != null && s.startsWith("http://")) {
                    int p = s.lastIndexOf('/');
                    this.target = s.substring(0, p);
                }
            }
            
            if (this.target == null) {
				throw new IllegalArgumentException("Need a deployment target.");
			}

            this.log("Deploying to " + this.target + "...");

			this.upload(this.jarFile);
			this.upload(this.jadFile);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	private void upload(File file) throws IOException {
		this.log((this.delete ? "Deleting" : "Uploading") + " file " + file.getName());

		String s = this.target + "/" + file.getName() + "?delete=" + this.delete;
        if (this.login != null) {
            s = s + "&login=" + this.login;
        }
        if (this.password != null) {
            s = s + "&password=" + this.password;
        }
        
		URL url = new URL(s);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.connect();

		if (!this.delete) {
			InputStream input = new FileInputStream(file);
			OutputStream output = connection.getOutputStream();
			Utility.copyStreams(input, output);
			output.flush();
			output.close();
		}

		int i = connection.getResponseCode();
		String message = connection.getResponseMessage() + " (" + i + ")";

		this.log(message, Project.MSG_VERBOSE);
		if (i >= 300) {
			throw new IOException(message);
		}
	}
}
