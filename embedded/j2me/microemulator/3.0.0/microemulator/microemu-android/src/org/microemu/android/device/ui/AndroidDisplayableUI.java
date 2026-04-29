/**
 *  MicroEmulator
 *  Copyright (C) 2008 Bartek Teodorczyk <barteo@barteo.net>
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
 *  @version $Id: AndroidDisplayableUI.java 2365 2010-04-12 20:18:01Z barteo@gmail.com $
 */

package org.microemu.android.device.ui;
import android.app.Activity;
import org.allbinary.thread.ARunnable;


import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;

import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.DisplayableUI;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.microemu.android.MicroEmulatorActivity;

public abstract class AndroidDisplayableUI implements DisplayableUI {
	
	protected Activity activity;
	
	protected Displayable displayable;
	
	protected View view;
	
	protected TextView titleView;
	
	private static Comparator<CommandUI> commandsPriorityComparator = new Comparator<CommandUI>() {

		public int compare(CommandUI first, CommandUI second) {
			if (first.getCommand().getPriority() == second.getCommand().getPriority()) {
				return 0;
			} else if (first.getCommand().getPriority() < second.getCommand().getPriority()) {
				return -1;
			} else {
				return 1;
			}
		}
		
	};
	
	private Vector<AndroidCommandUI> commands = new Vector<AndroidCommandUI>();
	
	private CommandListener commandListener = null;
	
	protected AndroidDisplayableUI(Activity activity, Displayable displayable, boolean initView) {
		this.activity = activity;
		this.displayable = displayable;
		
		if (initView) {
			this.view = new LinearLayout(activity);
			((LinearLayout) this.view).setOrientation(LinearLayout.VERTICAL);
			this.view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
			
			this.titleView = new TextView(activity);
			this.titleView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			this.titleView.setTextAppearance(this.titleView.getContext(), android.R.style.TextAppearance_DialogWindowTitle);
			((LinearLayout) this.view).addView(this.titleView);
		}
	}
	
	public Vector<AndroidCommandUI> getCommandsUI() {
		return this.commands;
	}
	
	public CommandListener getCommandListener() {
		return this.commandListener;
	}
	
	//
	// DisplayableUI
	//

	public void addCommandUI(CommandUI cmd) {
		synchronized (this) {
			if (!this.commands.contains(cmd)) {
				this.commands.add((AndroidCommandUI) cmd);
			}
			// TODO decide whether this is the best way for keeping sorted commands
			Collections.sort(this.commands, commandsPriorityComparator);
		}
	}

	public void removeCommandUI(CommandUI cmd) {
		synchronized (this) {
			this.commands.remove(cmd);
		}
	}

	public void setCommandListener(CommandListener l) {
		this.commandListener = l;
	}

	public void invalidate() {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				if (titleView != null) {
					titleView.setText(displayable.getTitle());
				}
			}
		});
	}

	public void showNotify() {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				activity.setContentView(view);
				view.requestFocus();
			}
		});
	}

	public void hideNotify() {
	}

}
