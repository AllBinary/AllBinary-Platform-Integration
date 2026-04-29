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

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DependencyChecker {

    private Vector classpath = new Vector();

    private Vector bootclasspath = new Vector();

    private Hashtable classes = new Hashtable();

    private Hashtable bootclasses = new Hashtable();

    public DependencyChecker(String classpath, String bootclasspath) {
        this.splitPath(classpath, this.classpath);
        this.splitPath(bootclasspath, this.bootclasspath);
    }

    public void destroy() {
        try {
            for (int i = 0; i < this.classpath.size(); i++) {
                Object o = this.classpath.elementAt(i);
                if (o instanceof ZipFile) {
                    ((ZipFile)o).close();
                }
            }
    
            for (int i = 0; i < this.bootclasspath.size(); i++) {
                Object o = this.bootclasspath.elementAt(i);
                if (o instanceof ZipFile) {
                    ((ZipFile)o).close();
                }
            }
        }
        catch (Exception ignored) {
        }

    }
        
    private void splitPath(String source, Vector target) {
        source = source + File.pathSeparatorChar;

        int p = 0;
        while (p < source.length()) {
            int q = source.indexOf(File.pathSeparator, p);
            String t = source.substring(p, q).trim();
            if (t.length() != 0) {
                File file = new File(t);
                if (file.isDirectory()) {
                    target.addElement(file);
                }
                else {
                    try {
                       target.addElement(new ZipFile(file));
                    }
                    catch (Exception ignored) {
                    }
                }
            }
            p = q + 1;
        }
    }

    /**
     * Loads a class.
     */
    public ClassFile loadClass(Vector classpath, String name) {
        for (int i = 0; i < classpath.size(); i++) {
            Object o = classpath.elementAt(i);
            
            if (o instanceof File) {
                ClassFile cf = this.loadClassFromDir((File)o, name);
                if (cf != null) return cf;
            }
            else {
                ClassFile cf = this.loadClassFromZip((ZipFile)o, name);
                if (cf != null) return cf;
            }
        }

        return null;
    }
    
    /**
     * Loads a class or returns an existing entry from the hashtable.
     */
    public ClassFile loadClass(String name) throws ClassNotFoundException {
        ClassFile c = null;
        
        c = (ClassFile) this.classes.get(name);
        if (c != null) {
            return c;
        }

        c = (ClassFile) this.bootclasses.get(name);
        if (c != null) {
            return c;
        }
        
        // System.out.println("Loading class: " + name);

        c = this.loadClass(this.classpath, name);
        if (c != null) {
            this.classes.put(name, c);
            this.resolveClass(c);
            return c;
        }
        
        c = this.loadClass(this.bootclasspath, name);
        if (c != null) {
            this.bootclasses.put(name, c);
            this.resolveClass(c);
            return c;
        }

        throw new ClassNotFoundException(name);
    }

    private ClassFile loadClassFromZip(ZipFile zip, String name) {
        String realName = name.replace('.', '/') + ".class";

        ZipEntry entry = zip.getEntry(realName);
        if (entry != null) {
            try {
                return new ClassFile(zip.getInputStream(entry));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    private ClassFile loadClassFromDir(File dir, String name) {
        String realName = name.replace('.', '/') + ".class";

        File file = new File(dir, realName);
        if (file.exists()) {
            try {
                return new ClassFile(new FileInputStream(file));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public void addRootClass(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.loadClass(name);
    }
    
    public Vector getClassNames() {
        Vector result = new Vector();
        
        Enumeration e = this.classes.keys();
        while (e.hasMoreElements()) {
            result.addElement(e.nextElement());
        }
        
        return result;
    }
    
    public void resolveClass(ClassFile cf) throws ClassNotFoundException {
        for (int i = 0; i < cf.getRequiredClassCount(); i++) {
            this.loadClass(cf.getRequiredClass(i));
        }
    }
    
}
