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
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;

import de.pleumann.antenna.misc.JadFile;
import de.pleumann.antenna.post.DependencyChecker;
import de.pleumann.antenna.post.PostProcessor;

public class WtkSmartLink extends PostProcessor {

	@Override
	public void execute() throws BuildException {
        if (!this.isActive()) return;
        
		if (this.getJarFile() == null) {
			throw new BuildException("Need a JAR file");
		}

		try {
			try {
				File tmpFile = this.getToJarFile();
				if (tmpFile == null) {
					tmpFile = new File(this.getUtility().getTempDir(), "output.jar");
				}

				JadFile jad = this.getJad();

				File tmpDir = new File(this.getUtility().getTempDir(), "files");

				Expand expand = new Expand();
				expand.setProject(this.getProject());
				expand.setTaskName(this.getTaskName());
				expand.setSrc(this.getJarFile());
				expand.setDest(tmpDir);
				expand.setOverwrite(true);
				expand.execute();

				Jar zip = new Jar();
				zip.setProject(this.getProject());
				zip.setTaskName(this.getTaskName());
				zip.setDestFile(tmpFile);
				zip.setDefaultexcludes(false);

				zip.setManifest(new File(tmpDir + "/META-INF/MANIFEST.MF"));

				FileSet nonClasses = new FileSet();
				nonClasses.setDir(tmpDir);
				nonClasses.setIncludes("**/*");
				nonClasses.setExcludes("**/*.class");

				FileSet classes = new FileSet();
				classes.setDir(tmpDir);

				zip.addFileset(classes);
				zip.addFileset(nonClasses);

                Vector preserve = new Vector(this.getPreserve());
                this.getUtility().getPreserveList(jad, preserve);
                
				DependencyChecker checker =
					new DependencyChecker("" + tmpDir, this.getFullClasspath());
				for (int i = 0; i < preserve.size(); i++) {
					try {
						checker.addRootClass(preserve.elementAt(i).toString());
					}
					catch (Exception e) {
						throw new BuildException(e);
					}
				}

				Vector link = checker.getClassNames();
				for (int i = 0; i < link.size(); i++) {
					String name = (String) link.elementAt(i);
					name = name.replace('.', '/') + ".class";
					classes.setIncludes(name);
				}

				zip.execute();

				if (this.getToJarFile() == null) {
					this.getJarFile().delete();
					tmpFile.renameTo(this.getJarFile());
				}
                
                this.updateJad();
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new BuildException(e);
			}
		}

		finally {
			this.getUtility().delete(this.getUtility().getTempDir());
		}
	}
}
