/**
 *  MicroEmulator
 *  Copyright (C) 2009 Bartek Teodorczyk <barteo@barteo.net>
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
 *  @version $Id: AndroidAlertUI.java 2499 2011-05-25 16:04:03Z barteo@gmail.com $
 */

package org.microemu.android.device.ui;
import android.app.Activity;
import org.allbinary.thread.ARunnable;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;

import org.microemu.MIDletBridge;
import org.microemu.device.ui.AlertUI;
import org.microemu.device.ui.CommandUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.Map;
import java.util.HashMap;
import org.microemu.android.MicroEmulatorActivity;

/**
 * Represents an Alert dialog or a Toast, depending on the properties.
 */
public class AndroidAlertUI extends AndroidDisplayableUI implements AlertUI {

	private AlertDialog alertDialog;

	private DialogInterface.OnClickListener onClickListener;

	private Map<Integer, CommandUI> buttons = new HashMap<Integer, CommandUI>();

	/**
	 * A quick reference to the Alert, without needing to unbox.
	 */
	protected Alert displayableUnboxed;

	/**
	 * Needed at runtime to know if we are showing a Toast instead of an Alert.
	 */
	protected boolean inShowingToast;

	/**
	 * Depending on the properties, the Alert can be rendered as a Toast or
	 * Alert.
	 * 
	 * @see #isToastable()
	 * @see #showNotifyAsToast()
	 */
	public AndroidAlertUI(final Activity activity,
			final Alert alert) {
		super(activity, alert, false);

		this.displayableUnboxed = alert;

                final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				alertDialog = new AlertDialog.Builder(activity).create();
				if (alert.getTitle() != null) {
					alertDialog.setTitle(alert.getTitle());
				}
				onClickListener = new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						activity2.setDialog(null);
						MIDletBridge.getMIDletAccess().getDisplayAccess().commandAction(
								buttons.get(which).getCommand(), displayable);
					}
				};
			}
		});
	}

	/**
	 * Decides if we should show a Toast instead of an Alert.
	 * 
	 * @return <code>true</code> if the current Alert is of type
	 *         {@link AlertType#INFO} and has one command only and the timeout
	 *         {@link Alert#getTimeout()} is different from
	 *         {@link Alert#FOREVER}.
	 */
	protected boolean isToastable() {
		boolean isToastable = 
				(displayableUnboxed.getType() == null || displayableUnboxed.getType().equals(AlertType.INFO))
				&& displayableUnboxed.getTimeout() != Alert.FOREVER
				&& getCommandsUI().size() == 1;

		return isToastable;
	}

	/**
	 * Gets the message to show in the Toast.<br>
	 * Called by {@link #showNotifyAsToast()}.
	 * 
	 * @return if both {@link Alert#getTitle()} and {@link Alert#getString()}
	 *         available then Alert.title + ": " + Alert.string. If not both
	 *         available, then Alert.title + Alert.string.
	 */
	protected String getToastMessage() {
		String title = displayableUnboxed.getTitle();
		String string = this.displayableUnboxed.getString();

		boolean hasBoth = title != null && !title.equals("") && string != null
				&& !string.equals("");

		String message = title + (hasBoth ? ": " : "") + string;

		return message;
	}

	/**
	 * Shows a Toast instead of the Alert dialog and calls the commandAction
	 * handler for the Ok/Default button of the Alert.
	 * 
	 * @see #isToastable()
	 */
	protected void showNotifyAsToast() {
		// show toast

		// according to the documentation the duration of the Toast
		// can be user defined, but this is not true according to the
		// implementation.
		
		//IMPLEMENTATION
		// http://android.git.kernel.org/?p=platform/frameworks/base.git;a=blob;f=services/java/com/android/server/NotificationManagerService.java#l74
		// private static final int LONG_DELAY = 3500; // 3.5 seconds
		// private static final int SHORT_DELAY = 2000; // 2 seconds
		  
		// displayableUnboxed.getTimeout() in J2ME is in millis

		int duration = displayableUnboxed.getTimeout() <= 2000 ? Toast.LENGTH_SHORT
				: Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(activity, getToastMessage(), duration);
		toast.show();

		// needed because we later need to call the commandListener
		fixLookAndFeel();

		// call commandAction
		MIDletBridge.getMIDletAccess().getDisplayAccess().commandAction(
				getCommandsUI().elementAt(0).getCommand(), displayable);
	}

	@Override
	public void showNotify() {
		boolean isToastable = isToastable();

		// set toast flag
		// needed for hideNotify
		this.inShowingToast = isToastable;

                final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		if (isToastable) {
			activity2.post(new ARunnable() {
				public void run() {
					showNotifyAsToast();
				}
			});
		} else {
			activity2.post(new ARunnable() {
				public void run() {
					fixLookAndFeel();
					activity2.setDialog(alertDialog);
				}
			});
		}
	}

	@Override
	public void hideNotify() {
		// we don't need to hide anything if Toasted
		if (this.inShowingToast) {
			this.inShowingToast = false;
		} else {
                    final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
			activity2.post(new ARunnable() {
				public void run() {
					activity2.setDialog(null);
				}
			});
		}
	}

	private void fixLookAndFeel() {
		for (CommandUI cmd : getCommandsUI()) {
			buttonize(cmd);
		}
	}

	private void buttonize(CommandUI cmd) {
		int which = 0;
		Command command = cmd.getCommand();
		if (command == Alert.DISMISS_COMMAND) {
			which = DialogInterface.BUTTON_NEUTRAL;
		} else {
			switch (command.getCommandType()) {
			case Command.OK:
				which = DialogInterface.BUTTON_POSITIVE;
				break;
			case Command.CANCEL:
				which = DialogInterface.BUTTON_NEGATIVE;
				break;
			}
		}
		if (which == 0) {
			this.alertDialog.setButton(command.getLabel(), onClickListener);
			which = DialogInterface.BUTTON_POSITIVE;
		} else {
			this.alertDialog.setButton(which, command.getLabel(), onClickListener);
		}
		buttons.put(which, cmd);
	}

	//
	// AlertUI
	//		

	public void setString(final String str) {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				alertDialog.setMessage(str);
			}
		});
	}

}
