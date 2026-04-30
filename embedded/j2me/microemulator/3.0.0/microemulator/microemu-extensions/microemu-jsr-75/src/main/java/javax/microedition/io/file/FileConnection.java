/**
 *  MicroEmulator
 *  Copyright (C) 2006-2007 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2006-2007 Vlad Skarzhevskyy
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 *
 *  @version $Id: FileConnection.java 1605 2008-02-25 21:07:14Z barteo $
 */
package javax.microedition.io.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.microedition.io.StreamConnection;

public interface FileConnection extends StreamConnection {

	abstract boolean isOpen();

	abstract InputStream openInputStream() throws IOException;

	abstract DataInputStream openDataInputStream() throws IOException;

	abstract OutputStream openOutputStream() throws IOException;

	abstract DataOutputStream openDataOutputStream() throws IOException;

	abstract OutputStream openOutputStream(long byteOffset) throws IOException;

	abstract long totalSize();

	abstract long availableSize();

	abstract long usedSize();

	abstract long directorySize(boolean includeSubDirs) throws IOException;

	abstract long fileSize() throws IOException;

	abstract boolean canRead();

	abstract boolean canWrite();

	abstract boolean isHidden();

	abstract void setReadable(boolean readable) throws IOException;

	abstract void setWritable(boolean writable) throws IOException;

	abstract void setHidden(boolean hidden) throws IOException;

	abstract Enumeration list() throws IOException;

	abstract Enumeration list(String filter, boolean includeHidden) throws IOException;

	abstract void create() throws IOException;

	abstract void mkdir() throws IOException;

	abstract boolean exists();

	abstract boolean isDirectory();

	abstract void delete() throws IOException;

	abstract void rename(String newName) throws IOException;

	abstract void truncate(long byteOffset) throws IOException;

	abstract void setFileConnection(String s) throws IOException;

	abstract String getName();

	abstract String getPath();

	abstract String getURL();

	abstract long lastModified();
}
