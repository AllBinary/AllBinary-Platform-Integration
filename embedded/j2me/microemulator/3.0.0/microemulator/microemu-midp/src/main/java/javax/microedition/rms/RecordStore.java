/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
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
 
package javax.microedition.rms;

import jsinterop.annotations.JsType;

import org.microemu.MIDletBridge;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;



@JsType
public class RecordStore 
{  
	
	@JsProperty
	public static final int AUTHMODE_PRIVATE = 0;
	
	@JsProperty
	public static final int AUTHMODE_ANY = 1;
	
//	TODO there should be a public constructors
//	private RecordStore() {
//	    
//	}
	
	@JsMethod
	public static void deleteRecordStore(String recordStoreName)
			throws RecordStoreException, RecordStoreNotFoundException
	{
		MIDletBridge.getRecordStoreManager().deleteRecordStore(recordStoreName);
	}
	
	
    @JsMethod
    public static String[] listRecordStores()
    {
    	return MIDletBridge.getRecordStoreManager().listRecordStores();
    }
	
	
    @JsMethod
    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) 
    		throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException
    {
        return MIDletBridge.getRecordStoreManager().openRecordStore(recordStoreName, createIfNecessary);
    }
    
    
    @JsMethod
    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary, int authmode, boolean writable)
    		throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException
    {
		// TODO Not yet implemented
    	return RecordStore.openRecordStore(recordStoreName, createIfNecessary);
    }
	
    
    @JsMethod
    public static RecordStore openRecordStore(String recordStoreName, String vendorName, String suiteName)
    		throws RecordStoreException, RecordStoreNotFoundException
	{
		// TODO Not yet implemented
    	return RecordStore.openRecordStore(recordStoreName, false);
	}
	
    @JsMethod
    public void closeRecordStore() 
    		throws RecordStoreNotOpenException, RecordStoreException
    {
    	// Must be overriden
    }
	
	
	@JsMethod
	public String getName()
			throws RecordStoreNotOpenException
	{
    	// Must be overriden
		
		return null;
	}
	
	
    @JsMethod
    public int getVersion() 
    		throws RecordStoreNotOpenException
    {
    	// Must be overriden
		
		return -1;
    }


    @JsMethod
    public int getNumRecords() 
    		throws RecordStoreNotOpenException
    {
    	// Must be overriden
		
		return -1;
    }


    @JsMethod
    public int getSize()
    		throws RecordStoreNotOpenException
    {
    	// Must be overriden
		
		return -1;
    }

  
    @JsMethod
    public int getSizeAvailable()
    		throws RecordStoreNotOpenException
    {
    	// Must be overriden
		
		return -1;
    }

  
    @JsMethod
    public long getLastModified() 
    		throws RecordStoreNotOpenException
    {
    	// Must be overriden
		
		return -1;
    }


    @JsMethod
    public void addRecordListener(RecordListener listener)
    {
    	// Must be overriden
    }
	
	
    @JsMethod
    public void removeRecordListener(RecordListener listener)
    {
    	// Must be overriden
    }
	
	
    @JsMethod
    public int getNextRecordID() 
    		throws RecordStoreNotOpenException, RecordStoreException
    {
    	// Must be overriden
		
		return -1;
    }
	
	
    @JsMethod
    public int addRecord(byte[] data, int offset, int numBytes)
            throws RecordStoreNotOpenException, RecordStoreException, RecordStoreFullException
    {
    	// Must be overriden
		
		return -1;
    }
	
	
    @JsMethod
    public void deleteRecord(int recordId) 
    		throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException
    {
    	// Must be overriden
    }
	

    @JsMethod
    public int getRecordSize(int recordId) 
    		throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException
    {
    	// Must be overriden
		
		return -1;
    }
	

    @JsMethod
    public int getRecord(int recordId, byte[] buffer, int offset)
            throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException
    {
    	// Must be overriden
		
		return -1;
    }
	
	
    @JsMethod
    public byte[] getRecord(int recordId) 
    		throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException
    {
    	// Must be overriden
		
		return null;
    }
	
    
    @JsMethod
    public void setMode(int authmode, boolean writable)
    		throws RecordStoreException
	{
		// TODO Not yet implemented
	}
	
    @JsMethod
    public void setRecord(int recordId, byte[] newData, int offset, int numBytes)
            throws RecordStoreNotOpenException, InvalidRecordIDException, RecordStoreException, RecordStoreFullException
    {
    	// Must be overriden
    }

  	
    @JsMethod
    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated)
            throws RecordStoreNotOpenException
    {
  		// Must be overriden
    	
    	return null;
    }
    
}

