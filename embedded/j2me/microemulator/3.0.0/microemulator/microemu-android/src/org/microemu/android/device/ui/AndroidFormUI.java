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
 *  @version $Id: AndroidFormUI.java 2514 2011-10-17 16:02:22Z barteo@gmail.com $
 */

package org.microemu.android.device.ui;
import android.app.Activity;
import org.allbinary.thread.ARunnable;


import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ItemStateListener;

import org.microemu.device.ui.FormUI;
import org.microemu.device.ui.ItemUI;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import org.microemu.android.MicroEmulatorActivity;

public class AndroidFormUI extends AndroidDisplayableUI implements FormUI {

	private ScrollView scrollView;
	
	private AndroidListView listView;
	
	private ItemStateListener itemStateListener;
	
	public AndroidFormUI(final Activity activity, Form form) {
		super(activity, form, true);
		
                final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				scrollView = new ScrollView(activity);
				((LinearLayout) AndroidFormUI.this.view).addView(scrollView);
				listView = new AndroidListView(activity, AndroidFormUI.this);
				listView.setOrientation(LinearLayout.VERTICAL);
				listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
				scrollView.addView(listView);

				invalidate();
			}
		});		
	}
	
	//
	// FormUI
	//
	
	private int appendTransfer;

	public int append(final ItemUI item) {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		if (activity2.isActivityThread()) {
			this.appendTransfer = this.doAppend(item);
		} else {
			this.appendTransfer = Integer.MIN_VALUE;
			activity2.post(new ARunnable() {
				public void run() {
					synchronized (AndroidFormUI.this) {
						appendTransfer = doAppend(item);
						AndroidFormUI.this.notify();
					}
				}
			});

			synchronized (AndroidFormUI.this) {
				if (this.appendTransfer == Integer.MIN_VALUE) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return appendTransfer;
	}
	
	private int doAppend(ItemUI item) {
		if (item instanceof AndroidCustomItemUI) {
			// SurfaceView cannot be put inside the ScrollView
			if (((LinearLayout) AndroidFormUI.this.view).indexOfChild(this.scrollView) != -1) {
				this.scrollView.removeAllViews();
				((LinearLayout) AndroidFormUI.this.view).removeView(this.scrollView);
				((LinearLayout) AndroidFormUI.this.view).addView(this.listView);
			}		
		}
		this.listView.addView((View) item);
		
		return this.listView.getChildCount() - 1;
	}
	 
	public void delete(final int itemNum) {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				listView.removeViewAt(itemNum);				
			}
		});
	}
	 
	public void deleteAll() {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				listView.removeAllViews();				
			}
		});
	}
	 
	public void insert(final int itemNum, final ItemUI item) {
            final MicroEmulatorActivity activity2 = (MicroEmulatorActivity) activity;
		activity2.post(new ARunnable() {
			public void run() {
				listView.addView((View) item, itemNum);
			}
		});
	}

	public void set(int itemNum, ItemUI item) {
		System.out.println("set(int itemNum, Item item)");
	}
	
	public void setItemStateListener(ItemStateListener itemStateListener) {
		this.itemStateListener = itemStateListener;
	}
	
	public ItemStateListener getItemStateListener() {
		return this.itemStateListener;
	}
	
	class AndroidListView extends LinearLayout {
		
		private AndroidFormUI ui;

		public AndroidListView(Context context, AndroidFormUI ui) {
			super(context);
			
			this.ui = ui;
		}
	
		public AndroidFormUI getUI() {
			return this.ui;
		}
		
	}

}
