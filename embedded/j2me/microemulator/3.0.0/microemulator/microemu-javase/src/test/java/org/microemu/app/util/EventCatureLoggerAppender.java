package org.microemu.app.util;

import org.microemu.log.LoggerAppender;
import org.microemu.log.LoggingEvent;

public class EventCatureLoggerAppender implements LoggerAppender {

	private LoggingEvent lastEvent; 
	
	public void append(LoggingEvent event) {
		this.lastEvent = event;
	}

	public void clearLastEvent() {
		this.lastEvent = null;
	}
	
	public LoggingEvent getLastEvent() {
		LoggingEvent ev = this.lastEvent;
		this.lastEvent = null;
		return ev;
	}

}
