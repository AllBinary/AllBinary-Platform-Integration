/*
 *  MicroEmulator
 *  Copyright (C) 2006 Bartek Teodorczyk <barteo@barteo.net>
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

package javax.microedition.media;

public interface Player extends Controllable
{

	public static final int UNREALIZED = 100;
	
	public static final int REALIZED = 200;
	
	public static final int PREFETCHED = 300;
	
	public static final int STARTED = 400;
	
	public static final int CLOSED = 0;
	
	public static final long TIME_UNKNOWN = -1;
	
	void realize() throws MediaException;
	
	void prefetch() throws MediaException;
	
	void start() throws MediaException;
	
	void stop() throws MediaException;
	
	void deallocate();
	
	void close();
	
	long setMediaTime(long now) throws MediaException;
	
	long getMediaTime();
	
	int getState();
	
	long getDuration();
	
	String getContentType();
	
	void setLoopCount(int count);
	
	void addPlayerListener(PlayerListener playerListener);
	
	void removePlayerListener(PlayerListener playerListener);
	
}
