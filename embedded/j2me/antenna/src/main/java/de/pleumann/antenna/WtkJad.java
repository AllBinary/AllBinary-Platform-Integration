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

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.tools.ant.*;

import de.pleumann.antenna.misc.*;

public class WtkJad extends Task {

	public class Attribute extends Conditional {
		String name;

		String value;

        public Attribute(Project project) {
            super(project);
        }
        
		public void setName(String name) {
			this.name = name;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public class MIDlet extends Conditional {
		String name = "";

		String cls = "";

		String icon = "";

        public MIDlet(Project project) {
            super(project);
        }
        
		public void setName(String name) {
			this.name = name;
		}

		public void setClass(String cls) {
			this.cls = cls;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}
	}

    private Conditional condition;
    
    private Utility utility;

	private Vector attributes = new Vector();

	private Vector midlets = new Vector();

	private File jadFile;

	private File jarFile;
    
    private File manifest;

	private String name;

	private String vendor;

	private String version;

    private String config;

    private String profile;

    private boolean autoversion;
    
	private boolean update;

    private String target;
    
    private String encoding;
    
    /**
     * The id of the JadAttrib for this jad, links a JadAttrib object to a Jad file produced by WtkJad
     */
    private String attribName;
    
    public void init() throws BuildException {
        super.init();
        this.utility = Utility.getInstance(getProject(), this);
        this.condition = new Conditional(getProject());
        
        this.config = "CLDC-" + this.utility.getCldcVersion();
        this.profile = "MIDP-" + this.utility.getMidpVersion();
    }
	public void setJadfile(File file) {
		this.jadFile = file;
	}

	public void setJarfile(File file) {
		this.jarFile = file;
	}

    public void setTarget(String url) {
        this.target = url;
    }

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public void setVersion(String version) {
		this.version = version;
	}

    public void setAutoversion(boolean versioning) {
        this.autoversion = versioning;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setManifest(File manifest) {
        this.manifest = manifest;
    }

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Attribute createAttribute() {
		Attribute a = new Attribute(getProject());
		this.attributes.addElement(a);
		return a;
	}

	public MIDlet createMidlet() {
		MIDlet m = new MIDlet(getProject());
		this.midlets.addElement(m);
		return m;
	}

    public void setIf(String s) {
        condition.setIf(s);
    }
    
    public void setUnless(String s) {
        condition.setUnless(s);
    }

    public boolean isActive() {
        return condition.isActive();
    }

	public void execute() throws BuildException {
        if (!isActive()) return;
        
        if (this.jadFile == null) {
            throw new BuildException("JAD file name needed");
        }
        
        log((this.update ? "Updating" : "Creating") + " JAD file " + this.jadFile);
        
		JadFile jad = new JadFile();

		if (this.update) {
			try {
				jad.load(this.jadFile.getPath(), encoding);
			}
			catch (IOException ignored) {
			}
		}

		if (this.jarFile != null) {
            String url = this.jarFile.getName();
            if (this.target != null && this.target.length() != 0) {
                if(!this.target.startsWith("http://") && !target.startsWith("http://"))
                    url =  "http://" + target + "/" + url;
                else
                    url = this.target + "/" +url;
            }
            
			jad.setValue("MIDlet-Jar-URL", url);
			jad.setValue("MIDlet-Jar-Size", "" + this.jarFile.length());
		}

		if (name != null)
			jad.setValue("MIDlet-Name", name);
		if (this.vendor != null)
			jad.setValue("MIDlet-Vendor", vendor);
		if (this.version != null)
			jad.setValue("MIDlet-Version", version);

		if (this.midlets.size() != 0) {
			if (this.update) {
				// Clear old MIDlet list
				for (int i = jad.getMIDletCount(); i > 0; i--) {
					jad.setValue("MIDlet-" + i, null);
				}
			}

			// Set new MIDlet list
			int number = 1;
			for (int i = 0; i < this.midlets.size(); i++) {
				MIDlet m = (MIDlet) this.midlets.elementAt(i);
                if (m.isActive()) {
    				jad.setValue("MIDlet-" + (number++), m.name + ", " + m.icon + ", " + m.cls);
                }
			}
		}
		
		// populate attributes from JadAttributes external task, if it has attributes for this jad.
		if (this.attribName != null && JadAttributes.hasAttributesFor(this.attribName))
		{
			String pairs[][] = JadAttributes.getAttributesFor(this, attribName);
			for (int i = 0; i < pairs.length; i++)
			{
				Attribute at = new Attribute(getProject());
				at.name = pairs[i][0];
				at.value = pairs[i][1];
				this.attributes.addElement(at);
			}
		}
	
		// Set attributes. If value is null, existing attribute is
        // deleted (makes sense in update mode)
		for (int i = 0; i < this.attributes.size(); i++) {
			Attribute a = (Attribute) this.attributes.elementAt(i);
			if (a.isActive() && a.name != null) {
				jad.setValue(a.name, a.value);
			}
		}
        
        // If versioning is requested, increase version number or set to "1.0.0"
        if (this.autoversion) {
            jad.setValue("MIDlet-Version", utility.getNewVersion(jad.getValue("MIDlet-Version")));
        }

        try {
            jad.save(this.jadFile.getPath(), encoding);
        }
        catch (IOException ex) {
            throw new BuildException(ex);
        }
        
        if (this.manifest != null) {
            log("Creating MANIFEST file " + this.manifest);
            
            jad.setValue("MIDlet-Jar-URL", null);
            jad.setValue("MIDlet-Jar-Size", null);

            jad.setValue("MicroEdition-Configuration", config);
            jad.setValue("MicroEdition-Profile", profile);

            try {
                jad.save(this.manifest.getPath(), encoding);
            }
            catch (IOException ex) {
                throw new BuildException(ex);
            }
        }
	}

	/**
	 * @param attribName The attribName to set.
	 */
	public void setAttribName(String attribName)
	{
		this.attribName = attribName;
	}
}