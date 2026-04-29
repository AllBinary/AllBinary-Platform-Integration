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
package de.pleumann.antenna.post;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

import de.pleumann.antenna.misc.*;

public abstract class PostProcessor extends Task {

	public class Preserve extends Conditional {
		private String value = "";

        public Preserve(Project project) {
            super(project);
        }
        
		public void setClass(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}

    private boolean verbose = true;

    private Conditional condition;
    
	private Vector preserve = new Vector();

	private File jarFile;

	private File toJarFile;

	private File jadFile;

	private JadFile jad;
	
	private String encoding;

	private Path bootclasspath;

	private Path classpath;

	private Utility utility;

	public void init() {
		super.init();
		this.classpath = new Path(getProject(), "");
		this.utility = Utility.getInstance(getProject(), this);
        this.condition = new Conditional(getProject());
	}

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

	public Object createPreserve() {
		Preserve pre = new Preserve(getProject());
		this.preserve.addElement(pre);
		return pre;
	}

	public void setJarfile(File srcFile) {
		this.jarFile = srcFile;
	}

	public void setTojarfile(File destFile) {
		this.toJarFile = destFile;
	}

	public void setJadfile(File file) {
		jadFile = file;
	}
	
	public void setEncoding(String encoding) {
	    this.encoding = encoding;
	}

	public String getFullClasspath() {
		String cp;

		if (this.bootclasspath == null) {
			cp = this.utility.getMidpApi();  // was: getEmptyApi()
		}
		else {
			cp = this.bootclasspath.toString();
		}

		if ((this.classpath != null) && (this.classpath.size() > 0)) {
			cp = cp + File.pathSeparatorChar + this.classpath;
		}
		return cp;
	}

	public void setClasspath(Path classpath) {
		if (this.classpath == null) {
			this.classpath = classpath;
		}
		else {
			this.classpath.append(classpath);
		}
	}

	/** Gets the classpath to be used for this compilation. */
	public Path getClasspath() {
		return this.classpath;
	}

	/**
	* Adds a path to the classpath.
	*/
	public Path createClasspath() {
		if (this.classpath == null) {
			this.classpath = new Path(getProject());
		}
		return this.classpath.createPath();
	}

	/**
	* Adds a reference to a classpath defined elsewhere.
	*/
	public void setClasspathref(Reference r) {
		this.createClasspath().setRefid(r);
	}

	public void setBootclasspath(Path classpath) {
		if (this.bootclasspath == null) {
			this.bootclasspath = classpath;
		}
		else {
			this.bootclasspath.append(classpath);
		}
	}

	/** Gets the classpath to be used for this compilation. */
	public Path getBootclasspath() {
		return this.bootclasspath;
	}

	/**
	* Adds a path to the bootclasspath.
	*/
	public Path createBootclasspath() {
		if (this.bootclasspath == null) {
			this.bootclasspath = new Path(getProject());
		}
		return this.bootclasspath.createPath();
	}

	/**
	* Adds a reference to a bootclasspath defined elsewhere.
	*/
	public void setBootclasspathref(Reference r) {
		this.createBootclasspath().setRefid(r);
	}

   public boolean getVerbose() {
      return this.verbose;
   }

	public Vector getPreserve() {
        Vector result = new Vector();
        for (int i = 0; i < this.preserve.size(); i++) {
            Preserve p = (Preserve)this.preserve.elementAt(i);
            if (p.isActive()) {
                result.add(p);
            }
        }
        
		return result;
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
        
	public File getJarFile() {
		return this.jarFile;
	}

	public File getToJarFile() {
		return this.toJarFile;
	}

	public JadFile getJad() throws IOException {
		if (this.jad != null) {
			return this.jad;
		}
		else {
			if (this.jadFile != null) {
				this.jad = new JadFile();
				this.jad.load("" + this.jadFile, encoding);
			}

			return this.jad;
		}
	}

	public Utility getUtility() {
		return this.utility;
	}

	public void updateJad() throws IOException {
		JadFile jad = this.getJad();

		if ((jad != null) && (this.jarFile != null)) {
			if ((this.toJarFile == null) || (this.jarFile.equals(this.toJarFile))) {
				jad.setValue("MIDlet-Jar-Size", "" + this.jarFile.length());

				log("Updating JAD file " + this.jadFile);
				try {
					jad.save("" + this.jadFile, encoding);
				}
				catch (IOException ex) {
					throw new BuildException("Error processing JAD file", ex);
				}
			}
		}
	}
}
