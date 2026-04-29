/*
 *  MicroEmulator
 *  Copyright (C) 2006 John Blackmon
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class SampledAudioPlayer implements Player, LineListener 
{
	private AudioInputStream audioInputStream = null;
    private AudioInputStream decodedStream = null;
	private Clip clip = null;
	private Vector vListeners = null;           // All PlayerListeners for this audio
	private String strType = null;
	
	public boolean open( InputStream stream, String type ) 
	{
	    this.strType = type;
		try 
	    {
			this.audioInputStream = AudioSystem.getAudioInputStream( new BufferedInputStream( stream ) );
			AudioFormat format = this.audioInputStream.getFormat();
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) 
			{
			    AudioFormat baseFormat = this.audioInputStream.getFormat();
			    AudioFormat decodedFormat = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED,
										                     baseFormat.getSampleRate(),
										                     16,
										                     baseFormat.getChannels(),
										                     baseFormat.getChannels() * 2,
										                     baseFormat.getSampleRate(),
										                     false );
			    this.decodedStream = AudioSystem.getAudioInputStream( decodedFormat, audioInputStream );
			    int frameLength = (int) this.decodedStream.getFrameLength();
			    int frameSize = decodedFormat.getFrameSize();
			    DataLine.Info info = new DataLine.Info( Clip.class, decodedFormat,
			    										frameLength * frameSize);
			    this.clip = (Clip) AudioSystem.getLine( info );
		    	this.clip.open( this.decodedStream );
			}
	        else
	        {
				DataLine.Info info2 = new DataLine.Info( Clip.class, format, AudioSystem.NOT_SPECIFIED );
				this.clip = (Clip) AudioSystem.getLine( info2 );
		    	this.clip.open( audioInputStream );
	        }
	    } 
	    catch( UnsupportedAudioFileException e ){ e.printStackTrace(); return false; }
	    catch( IOException e ){ e.printStackTrace(); return false; }
	    catch( LineUnavailableException e ){ e.printStackTrace(); return false; }
		return true;
	}

	public void addPlayerListener(PlayerListener playerListener) 
	{
		if( this.vListeners == null )
			this.vListeners = new Vector();
		this.vListeners.add( playerListener );
	}

	public void close() 
	{
		Manager.mediaDone( this );
		if( this.clip != null )
		{
			this.clip.flush();
	    	this.clip.close();
		}
		
		try 
	    {
			if( this.decodedStream != null )
				this.decodedStream.close();
			if( this.audioInputStream != null )
				this.audioInputStream.close();
	    } 
	    catch( IOException e ) { e.printStackTrace(); }
	}

	public void deallocate() {
		if( this.clip != null )
			this.clip.flush();
	}

	public String getContentType() {
		return this.strType;
	}

	public long getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getMediaTime() {
		if( this.clip != null )
			return this.clip.getMicrosecondPosition();
		return 0;
	}

	public int getState() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void prefetch() throws MediaException {
		// TODO Auto-generated method stub
	}

	public void realize() throws MediaException {
		// TODO Auto-generated method stub
	}

	public void removePlayerListener(PlayerListener playerListener) 
	{
	   if( this.vListeners == null )
		   return;
	   for( Iterator it = this.vListeners.iterator (); it.hasNext (); ) 
	   {
		    PlayerListener listener = (PlayerListener) it.next ();
		    if( listener == playerListener )
		    {
		    	this.vListeners.remove( listener );
		    	break;
	   		}
	   }
	}

	public void setLoopCount(int count) {
		if( this.clip != null )
			this.clip.loop( count );
	}

	public long setMediaTime(long now) throws MediaException {
		if( this.clip != null )
			this.clip.setMicrosecondPosition( now );
		return 0;
	}

	public void start() throws MediaException {
		if( this.clip != null )
		{
			this.clip.addLineListener( this );
			this.clip.start();
		}
	}

	public void stop() throws MediaException {
		if( this.clip != null )
			this.clip.stop();
	}

	public Control getControl(String controlType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Control[] getControls() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update( LineEvent event ) 
	{
		if (event.getType().equals(LineEvent.Type.STOP))
		{
			this.close();
			if( this.vListeners != null )
			{
                            final int size = this.vListeners.size();
                            for(int index = 0; index < size; index++) {
                                PlayerListener listener = (PlayerListener) this.vListeners.get(index);
                                listener.playerUpdate( this, PlayerListener.END_OF_MEDIA, null );
                            }
			}
		}
	}
}
