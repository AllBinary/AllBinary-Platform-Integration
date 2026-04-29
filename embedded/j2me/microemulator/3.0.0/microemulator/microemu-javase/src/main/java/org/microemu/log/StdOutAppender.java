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
 *  @version $Id: StdOutAppender.java 1605 2008-02-25 21:07:14Z barteo $
 */
package org.microemu.log;

import java.io.PrintStream;

/**
 * @author vlads
 * 
 */
public class StdOutAppender implements LoggerAppender {

	public static boolean enabled = true;
	
	public static String formatLocation(StackTraceElement ste) {
		if (ste == null) {
			return "";
		}
		// Make Line# clickable in eclipse
		return ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber()
				+ ")";
	}

        private final String EMPTY = "";
        private final String OPEN = " [";
        private final String CLOSE = "]";
        private final String LOCATION = "\n\t  ";
        //private final String LOGGING_EVENT_LABEL = "Logging Event: ";
        
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.microemu.log.LoggerAppender#append(org.microemu.log.LoggingEvent)
	 */
	public void append(LoggingEvent event) {
		if (!StdOutAppender.enabled) {
			return;
		}
		PrintStream out = System.out; 
    	if (event.getLevel() == LoggingEvent.ERROR) {
    		out = System.err;
    	}
        StringBuilder stringBuilder = new StringBuilder();
    	String data = this.EMPTY;
    	if (event.hasData()) {
    		data = stringBuilder.append(this.OPEN).append(event.getFormatedData()).append(this.CLOSE).toString();
    	}
    	String location = StdOutAppender.formatLocation(event.getLocation());
    	if (location.length() > 0) {
            stringBuilder.delete(0, stringBuilder.length());
    		location = stringBuilder.append(this.LOCATION).append(location).toString();
    	}
        stringBuilder.delete(0, stringBuilder.length());
        String output = stringBuilder
                //.append(LOGGING_EVENT_LABEL)
                .append(event.getMessage()).append(data).append(location).toString();
    	out.println(output);
        
    	if (event.getThrowable() != null) {
    		event.getThrowable().printStackTrace(out);
    	}

	}

}
