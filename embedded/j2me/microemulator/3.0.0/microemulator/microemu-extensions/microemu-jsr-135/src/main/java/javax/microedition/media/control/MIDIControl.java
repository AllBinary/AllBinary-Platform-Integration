/**
 *  MicroEmulator
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  @version $Id: MIDIControl.java 1127 2007-03-14 17:52:30Z vlads $
 */
package javax.microedition.media.control;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;

public interface MIDIControl extends Control {

	public static final int NOTE_ON = 144;

	public static final int CONTROL_CHANGE = 176;
	
	abstract boolean isBankQuerySupported();

	abstract int[] getProgram(int i) throws MediaException;

	abstract int getChannelVolume(int i);

	abstract void setProgram(int i, int j, int k);

	abstract void setChannelVolume(int i, int j);

	abstract int[] getBankList(boolean flag) throws MediaException;

	abstract int[] getProgramList(int i) throws MediaException;

	abstract String getProgramName(int i, int j) throws MediaException;

	abstract String getKeyName(int i, int j, int k) throws MediaException;

	abstract void shortMidiEvent(int i, int j, int k);

	abstract int longMidiEvent(byte abyte0[], int i, int j);

}
