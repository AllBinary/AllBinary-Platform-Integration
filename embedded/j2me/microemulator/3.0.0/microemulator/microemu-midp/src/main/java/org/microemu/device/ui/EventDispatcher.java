/**
 *  MicroEmulator
 *  Copyright (C) 2002 Bartek Teodorczyk <barteo@barteo.net>
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
 */

package org.microemu.device.ui;

import org.allbinary.TsUtil;
import org.microemu.device.DeviceFactory;

public class EventDispatcher implements Runnable {
	
	public static final String EVENT_DISPATCHER_NAME = "event-thread";

	public static int maxFps = -1;

	private volatile boolean cancelled = false;
	
	private Event head = null;

	private Event tail = null;

	private PaintEvent scheduledPaintEvent = null;

	private PointerEvent scheduledPointerDraggedEvent = null;

	private Object serviceRepaintsLock = new Object();
	
	private long lastPaintEventTime = 0;

	public EventDispatcher() {
	}
	
	public void run() {

		while (!this.cancelled) {
			Event event = null;
			synchronized (this) {
				if (this.head != null) {
					event = this.head;

					if (EventDispatcher.maxFps > 0 && event instanceof PaintEvent) {
						long difference = System.currentTimeMillis() - this.lastPaintEventTime;
						if (difference < (1000 / EventDispatcher.maxFps)) {
							event = null;
							try {
								TsUtil.getInstance().waitFor(this,(1000 / maxFps) - difference);
							} catch (InterruptedException e) {
							}
						}
					}
					
					if (event != null) {
						this.head = event.next;
						if (this.head == null) {
							this.tail = null;
						}
						if (event instanceof PointerEvent && ((PointerEvent) event).type == PointerEvent.POINTER_DRAGGED) {
							this.scheduledPointerDraggedEvent = null;
						}
					}
				} else {
					try {
						wait();
					} catch (InterruptedException e) {
					}
				}
			}

			if (event != null) {
				if (event instanceof PaintEvent) {
					synchronized (this.serviceRepaintsLock) {
						synchronized (this) {
							this.scheduledPaintEvent = null;
						}
						this.lastPaintEventTime = System.currentTimeMillis();
						this.post(event);
						this.serviceRepaintsLock.notifyAll();
					}					
				} else {
					this.post(event);
				}
			}
		}
	}

	/**
	 * Do not service any more events
	 */
	public final void cancel() {
		this.cancelled = true;
		synchronized (this) {
			notify();
		}
	}

	public void put(Event event) {
		synchronized (this) {
			if (event instanceof PaintEvent && this.scheduledPaintEvent != null) {
				this.scheduledPaintEvent.merge((PaintEvent) event);
			} else if (event instanceof PointerEvent && this.scheduledPointerDraggedEvent != null
					&& ((PointerEvent) event).type == PointerEvent.POINTER_DRAGGED) {
				this.scheduledPointerDraggedEvent.x = ((PointerEvent) event).x;
				this.scheduledPointerDraggedEvent.y = ((PointerEvent) event).y;
			} else {
				if (event instanceof PaintEvent) {
					this.scheduledPaintEvent = (PaintEvent) event;
				}
				if (event instanceof PointerEvent && ((PointerEvent) event).type == PointerEvent.POINTER_DRAGGED) {
					this.scheduledPointerDraggedEvent = (PointerEvent) event;
				}
				if (this.tail != null) {
					this.tail.next = event;
				}
				this.tail = event;
				if (this.head == null) {
					this.head = event;
				}
				notify();
			}
		}
	}

	public void put(Runnable runnable) {
		this.put(new RunnableEvent(runnable));
	}

	public void serviceRepaints() {
		synchronized (this.serviceRepaintsLock) {
			synchronized (this) {
				if (this.scheduledPaintEvent == null) {
					return;
				}

				// TODO move scheduledPaintEvent to head
			}

			try {
				this.serviceRepaintsLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	protected void post(Event event) {
		event.run();
	}

	public class Event implements Runnable {

		Event next = null;

                public void run() {
                    
                }
	}

	public final class PaintEvent extends Event {

		private int x = -1, y = -1, width = -1, height = -1;

                private final String REPAINT = "TWB:PaintEvent";
                
		public PaintEvent(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public void run() {
                    System.out.println(REPAINT + Thread.currentThread());
			DeviceFactory.getDevice().getDeviceDisplay().repaint(this.x, this.y, this.width, this.height);
		}

		/**
		 * Do a 2-D merge of the paint areas
		 * 
		 * @param event
		 */
		public final void merge(PaintEvent event) {
			int xMax = x + width;
			int yMax = this.y + height;

			this.x = Math.min(this.x, event.x);
			xMax = Math.max(xMax, event.x + event.width);

			this.y = Math.min(this.y, event.y);
			yMax = Math.max(yMax, event.y + event.height);

			this.width = xMax - this.x;
			this.height = yMax - this.y;
		}

	}

	public final class PointerEvent extends EventDispatcher.Event {

		public static final short POINTER_PRESSED = 0;

		public static final short POINTER_RELEASED = 1;

		public static final short POINTER_DRAGGED = 2;

		private Runnable runnable;

		private short type;

		private int x;

		private int y;

		public PointerEvent(Runnable runnable, short type, int x, int y) {
			this.runnable = runnable;
			this.type = type;
			this.x = x;
			this.y = y;
		}

		public void run() {
			this.runnable.run();
		}
	}
	
	public class RunnableEvent extends Event {

		private Runnable runnable;

		public RunnableEvent(Runnable runnable) {
			this.runnable = runnable;
		}

		public void run() {
			this.runnable.run();
		}

	}
	
}
