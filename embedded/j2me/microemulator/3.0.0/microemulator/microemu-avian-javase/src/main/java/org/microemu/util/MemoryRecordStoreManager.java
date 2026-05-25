/*
 *  MicroEmulator
 *  Copyright (C) 2001-2005 Bartek Teodorczyk <barteo@barteo.net>
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

package org.microemu.util;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

import org.microemu.MicroEmulator;
import org.microemu.RecordStoreManager;

public class MemoryRecordStoreManager implements RecordStoreManager {
	private Hashtable recordStores = new Hashtable();

	private ExtendedRecordListener recordListener = null;

	public void initForMicroemulator(MicroEmulator emulator) {
	}

	public String getName() {
		return "Memory record store";
	}

	public void deleteRecordStore(String recordStoreName) throws RecordStoreNotFoundException, RecordStoreException {
		RecordStoreImpl recordStoreImpl = (RecordStoreImpl) recordStores.get(recordStoreName);
		if (recordStoreImpl == null) {
			throw new RecordStoreNotFoundException(recordStoreName);
		}
		if (recordStoreImpl.isOpen()) {
			throw new RecordStoreException();
		}
		this.recordStores.remove(recordStoreName);

		this.fireRecordStoreListener(ExtendedRecordListener.RECORDSTORE_DELETE, recordStoreName);
	}

	public RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary)
			throws RecordStoreNotFoundException {
		RecordStoreImpl recordStoreImpl = (RecordStoreImpl) this.recordStores.get(recordStoreName);
		if (recordStoreImpl == null) {
			if (!createIfNecessary) {
				throw new RecordStoreNotFoundException(recordStoreName);
			}
			recordStoreImpl = new RecordStoreImpl(this, recordStoreName);
			this.recordStores.put(recordStoreName, recordStoreImpl);
		}
		recordStoreImpl.setOpen(true);
		if (this.recordListener != null) {
			recordStoreImpl.addRecordListener(this.recordListener);
		}

		this.fireRecordStoreListener(ExtendedRecordListener.RECORDSTORE_OPEN, recordStoreName);

		return recordStoreImpl;
	}

	public String[] listRecordStores() {
		String[] result = null;

		int i = 0;
		for (Enumeration e = this.recordStores.keys(); e.hasMoreElements();) {
			if (result == null) {
				result = new String[this.recordStores.size()];
			}
			result[i] = (String) e.nextElement();
			i++;
		}

		return result;
	}

	public void deleteRecord(RecordStoreImpl recordStoreImpl, int recordId) {
	}
	
	public void loadRecord(RecordStoreImpl recordStoreImpl, int recordId) {
	}

	public void saveRecord(RecordStoreImpl recordStoreImpl, int recordId) {
	}

	public void init() {
		this.deleteStores();
	}

	public void deleteStores() {
		if (this.recordStores != null)
			this.recordStores.clear();
	}

	public int getSizeAvailable(RecordStoreImpl recordStoreImpl) {
		// FIXME returns too much
		return (int) Runtime.getRuntime().freeMemory();
	}

	public void setRecordListener(ExtendedRecordListener recordListener) {
		this.recordListener = recordListener;
	}

	public void fireRecordStoreListener(int type, String recordStoreName) {
		if (this.recordListener != null) {
			this.recordListener.recordStoreEvent(type, System.currentTimeMillis(), recordStoreName);
		}
	}
}
