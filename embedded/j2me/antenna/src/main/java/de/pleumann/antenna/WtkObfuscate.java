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
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import de.pleumann.antenna.misc.Conditional;
import de.pleumann.antenna.post.PostProcessor;

public class WtkObfuscate extends PostProcessor {

	public class Argument extends Conditional {
		String value;

		public Argument(Project project) {
			super(project);
		}

		public void setValue(String value) {
            value = value.replaceAll("&lt;", "<");
            value = value.replaceAll("&gt;", ">");
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}

	private Vector arguments = new Vector();

	private String obfuscator;

	public Argument createArgument() {
		Argument a = new Argument(this.getProject());
		this.arguments.addElement(a);
		return a;
	}

	public Vector getArguments() {
		Vector result = new Vector();
		for (int i = 0; i < this.arguments.size(); i++) {
			Argument a = (Argument) this.arguments.elementAt(i);
			if (a.isActive()) {
				result.add(a);
			}
		}

		return result;
	}

	public void setObfuscator(String obfuscator) {
		this.obfuscator = obfuscator;
	}

	// Add ability to work on JAR/JAD pair. JAD size is
	// updated automatically.
	//
	// Same for obfuscate, smartlink, preverify
	//
	// Generic class for ME tasks that take JAR/JAR?

	@Override
	public void execute() throws BuildException {
		if (!this.isActive())
			return;

		if (this.getJarFile() == null) {
			throw new BuildException("Need a JAR file");
		}

		File tmpDir = this.getUtility().getTempDir();

		try {
			try {
				File tmpFile = this.getToJarFile();
				if (tmpFile == null) {
					tmpFile = new File(tmpDir + "/output.jar");
				}

				Vector preserve = this.getPreserve();
				this.getUtility().getPreserveList(this.getJad(), preserve);
				this.getUtility().obfuscate(this.getJarFile(), tmpFile, this.getFullClasspath(), this.getVerbose(), preserve, this.obfuscator, this.getArguments(), this.getJad());

				if (this.getToJarFile() == null) {
					this.setTojarfile(this.getJarFile());
				}

				if (!this.getToJarFile().delete()) {
					this.log("Unable to delete " + this.getToJarFile(), Project.MSG_WARN);
				}

				if (!tmpFile.renameTo(this.getToJarFile())) {
					this.log("Unable to rename " + tmpFile, Project.MSG_WARN);
				}

				this.updateJad();
			}
			finally {
				this.getUtility().delete(tmpDir);
			}
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}
}