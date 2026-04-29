/**
 *  MicroEmulator
 *  Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
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
 *  @version $Id: Logger.java 1877 2008-12-17 22:01:58Z vlads $
 */
package org.microemu.log;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.allbinary.string.CommonSeps;

import org.microemu.app.util.IOUtils;

/**
 * 
 * This class is used as abstraction layer for log messages Minimum Log4j implemenation with multiple overloaded
 * functions
 * 
 * @author vlads
 * 
 */
public class Logger {

	private static final String FQCN = Logger.class.getName();

	private static final Set fqcnSet = new HashSet();

	private static final Set logFunctionsSet = new HashSet();

	private static boolean java13 = false;

	private static boolean locationEnabled = true;

	private static List loggerAppenders = new Vector();

	static {
		Logger.fqcnSet.add(FQCN);
		Logger.addAppender(new StdOutAppender());

		// This is done for MIDletInternlaLogger a wrapper for
		// System.out.println functions.
		logFunctionsSet.add("debug");
		Logger.logFunctionsSet.add("log");
		Logger.logFunctionsSet.add("error");
		Logger.logFunctionsSet.add("fatal");
		Logger.logFunctionsSet.add("info");
		Logger.logFunctionsSet.add("warn");
	}

	public static boolean isDebugEnabled() {
		return true;
	}

	public static boolean isErrorEnabled() {
		return true;
	}

	public static boolean isLocationEnabled() {
		return Logger.locationEnabled;
	}

	public static void setLocationEnabled(boolean state) {
		locationEnabled = state;
	}

	private static StackTraceElement getLocation() {
		if (Logger.java13 || !Logger.locationEnabled) {
			return null;
		}
		try {
			StackTraceElement[] ste = new Throwable().getStackTrace();
			boolean wrapperFound = false;
			for (int i = 0; i < ste.length - 1; i++) {
				if (Logger.fqcnSet.contains(ste[i].getClassName())) {
					wrapperFound = false;
					String nextClassName = ste[i + 1].getClassName();
					if (nextClassName.startsWith("java.") || nextClassName.startsWith("sun.")) {
						continue;
					}
					if (!Logger.fqcnSet.contains(nextClassName)) {
						if (Logger.logFunctionsSet.contains(ste[i + 1].getMethodName())) {
							wrapperFound = true;
						} else {
							// if dynamic proxy classes
							if (nextClassName.startsWith("$Proxy")) {
								return ste[i + 2];
							} else {
								return ste[i + 1];
							}
						}
					}
				} else if (wrapperFound) {
					if (!Logger.logFunctionsSet.contains(ste[i].getMethodName())) {
						return ste[i];
					}
				}
			}
			return ste[ste.length - 1];
		} catch (Throwable e) {
			Logger.java13 = true;
		}
		return null;
	}

	private static void write(int level, String message, Throwable throwable) {
		while ((message != null) && message.endsWith(CommonSeps.getInstance().NEW_LINE)) {
			message = message.substring(0, message.length() - 1);
		}
		Logger.callAppenders(new LoggingEvent(level, message, getLocation(), throwable));
	}

	private static void write(int level, String message, Throwable throwable, Object data) {
		Logger.callAppenders(new LoggingEvent(level, message, getLocation(), throwable, data));
	}

	public static void debug(String message) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null);
		}
	}

	public static void debug(String message, Throwable t) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, t);
		}
	}

	public static void debug(Throwable t) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, "error", t);
		}
	}

	public static void debug(String message, String v) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, v);
		}
	}

	public static void debug(String message, Object o) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, new LoggerDataWrapper(o));
		}
	}

	public static void debug(String message, String v1, String v2) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, new LoggerDataWrapper(v1, v2));
		}
	}

	public static void debug(String message, long v) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, new LoggerDataWrapper(v));
		}
	}

	public static void debug0x(String message, long v) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, new LoggerDataWrapper("0x" + Long.toHexString(v)));
		}
	}

	public static void debug(String message, long v1, long v2) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, new LoggerDataWrapper(v1, v2));
		}
	}

	public static void debug(String message, boolean v) {
		if (isDebugEnabled()) {
			Logger.write(LoggingEvent.DEBUG, message, null, new LoggerDataWrapper(v));
		}
	}

	public static void debugClassLoader(String message, Object obj) {
		if (obj == null) {
			Logger.write(LoggingEvent.DEBUG, message + " no class, no object", null, null);
			return;
		}
		Class klass;
		StringBuffer buf = new StringBuffer();
		buf.append(message).append(" ");
		if (obj instanceof Class) {
			klass = (Class) obj;
			buf.append("class ");
		} else {
			klass = obj.getClass();
			buf.append("instance ");
		}
		buf.append(klass.getName() + " loaded by ");
		if (klass.getClassLoader() != null) {
			buf.append(klass.getClassLoader().hashCode());
			buf.append(" ");
			buf.append(klass.getClassLoader().getClass().getName());
		} else {
			buf.append("system");
		}
		Logger.write(LoggingEvent.DEBUG, buf.toString(), null, null);
	}

	public static void info(String message) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.INFO, message, null);
		}
	}

	public static void info(Object message) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.INFO, "" + message, null);
		}
	}

	public static void info(String message, String data) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.INFO, message, null, data);
		}
	}

	public static void warn(String message) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.WARN, message, null);
		}
	}

	public static void error(String message) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + message, null);
		}
	}

	public static void error(Object message) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + message, null);
		}
	}

	public static void error(String message, long v) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + message, null, new LoggerDataWrapper(v));
		}
	}

	public static void error(String message, String v) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + message, null, v);
		}
	}

	public static void error(String message, String v, Throwable t) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + message, t, v);
		}
	}

	public static void error(Throwable t) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + t.toString(), t);
		}
	}

	public static void error(String message, Throwable t) {
		if (isErrorEnabled()) {
			Logger.write(LoggingEvent.ERROR, "error " + message + " " + t.toString(), t);
		}
	}

	private static void callAppenders(LoggingEvent event) {
		for (Iterator iter = Logger.loggerAppenders.iterator(); iter.hasNext();) {
			LoggerAppender a = (LoggerAppender) iter.next();
			a.append(event);
		}
		;
	}

	/**
	 * Add the Class which serves as entry point for log message location.
	 * 
	 * @param origin
	 *            Class
	 */
	public static void addLogOrigin(Class origin) {
		Logger.fqcnSet.add(origin.getName());
	}

	public static void addAppender(LoggerAppender newAppender) {
		Logger.loggerAppenders.add(newAppender);
	}

	public static void removeAppender(LoggerAppender appender) {
		Logger.loggerAppenders.remove(appender);
	}

	public static void removeAllAppenders() {
		Logger.loggerAppenders.clear();
	}

	public static void threadDumpToConsole() {
		try {
			StringBuffer out = new StringBuffer("Full ThreadDump\n");
			Map traces = Thread.getAllStackTraces();
			for (Iterator iterator = traces.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				Thread thread = (Thread) entry.getKey();
				out.append("Thread= " + thread.getName() + " " + (thread.isDaemon() ? "daemon" : "") + " prio="
						+ thread.getPriority() + "id=" + thread.getId() + " " + thread.getState());
				out.append(CommonSeps.getInstance().NEW_LINE);

				StackTraceElement[] ste = (StackTraceElement[]) entry.getValue();
				for (int i = 0; i < ste.length; i++) {
					out.append("\t");
					out.append(ste[i].toString());
					out.append(CommonSeps.getInstance().NEW_LINE);
				}
				out.append("---------------------------------\n");
			}
			Logger.info(out.toString());
		} catch (Throwable ignore) {
		}
	}

	public static void threadDumpToFile() {
		SimpleDateFormat fmt = new SimpleDateFormat("MM-dd_HH-mm-ss");
		OutputStreamWriter out = null;
		try {
			File file = new File("ThreadDump-" + fmt.format(new Date()) + ".log");
			out = new FileWriter(file);
			Map traces = Thread.getAllStackTraces();
			for (Iterator iterator = traces.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				Thread thread = (Thread) entry.getKey();
				out.write("Thread= " + thread.getName() + " " + (thread.isDaemon() ? "daemon" : "") + " prio="
						+ thread.getPriority() + "id=" + thread.getId() + " " + thread.getState());
				out.write(CommonSeps.getInstance().NEW_LINE);

				StackTraceElement[] ste = (StackTraceElement[]) entry.getValue();
				for (int i = 0; i < ste.length; i++) {
					out.write("\t");
					out.write(ste[i].toString());
					out.write(CommonSeps.getInstance().NEW_LINE);
				}
				out.write("---------------------------------\n");
			}
			out.close();
			out = null;
			Logger.info("Full ThreadDump created " + file.getAbsolutePath());
		} catch (Throwable ignore) {
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
