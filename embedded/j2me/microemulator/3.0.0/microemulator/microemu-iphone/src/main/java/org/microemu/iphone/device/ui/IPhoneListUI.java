/**
 *  MicroEmulator
 *  Copyright (C) 2008 Markus Heberling <markus@heberling.net>
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
 *  @version $Id: IPhoneListUI.java 1942 2009-02-20 15:39:17Z tisoft $
 */
package org.microemu.iphone.device.ui;
import org.allbinary.thread.ARunnable;


import java.lang.reflect.Field;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

import joc.Message;
import obc.CGRect;
import obc.NSIndexPath;
import obc.UINavigationBar;
import obc.UINavigationItem;
import obc.UITableView;
import obc.UITableViewCell;
import obc.UIToolbar;
import obc.UIView;

import org.microemu.device.ui.ListUI;
import org.microemu.iphone.MicroEmulator;
import org.microemu.iphone.ThreadDispatcher;

public class IPhoneListUI extends AbstractUI<List> implements ListUI {

	private final class ChoiceGroupDelegate extends ChoiceGroup {
		private int type;
		public ChoiceGroupDelegate(ChoiceGroup cg, int type) {
			super(cg.getLabel(), List.EXCLUSIVE);
			try {
				Field choiceType=ChoiceGroup.class.getDeclaredField("choiceType");
				choiceType.setAccessible(true);
				choiceType.set(this, type);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			this.type=type;
			for(int i=0;i<cg.size();i++){
				this.append(cg.getString(i), cg.getImage(i));
			}
		}
		
		@Override
		public void delete(int itemNum) {
			// TODO Auto-generated method stub
			super.delete(itemNum);
			this.doReload();
		}
		
		@Override
		public void deleteAll() {
			// TODO Auto-generated method stub
			super.deleteAll();
			this.doReload();
		}
		
		@Override
		public void insert(int elementNum, String stringPart, Image imagePart) {
			// TODO Auto-generated method stub
			super.insert(elementNum, stringPart, imagePart);
			this.doReload();
		}
		@Override
		public void set(int elementNum, String stringPart, Image imagePart) {
			// TODO Auto-generated method stub
			super.set(elementNum, stringPart, imagePart);
			this.doReload();
		}
		
		@Override
		public void setSelectedIndex(int elementNum, boolean selected) {
			// TODO Auto-generated method stub
			super.setSelectedIndex(elementNum, selected);
			this.doReload();
		}
		public int getType() {
			return this.type;
		}
		
		protected void doReload() {
			if(this.tableView!=null)
				ThreadDispatcher.dispatchOnMainThread(new ARunnable() {
					public void run() {
						tableView.reloadData();
					}
				}, false);
		}
	}

	static final int UIViewAutoresizingFlexibleHeight =  1 << 4;

	static final int UIViewAutoresizingFlexibleWidth =  1 << 1;

	private UITableView tableView;

	private Command selectCommand=List.SELECT_COMMAND;
	
	private UIView view;

	private ChoiceGroupDelegate choiceGroup;
	
	private UINavigationBar navigtionBar;
	
	public IPhoneListUI(MicroEmulator microEmulator, List list) {
		super(microEmulator, list);
	}

	@Message
	public final int tableView$numberOfRowsInSection$(UITableView table, int sectionIndex) {
		return displayable.size();
	}

	@Message
	public final UITableViewCell tableView$cellForRowAtIndexPath$(UITableView table, NSIndexPath indexPath) {
		UITableViewCell cell = (UITableViewCell) table.dequeueReusableCellWithIdentifier$("reuse");
		if (cell == null) {
			cell = new UITableViewCell().init();
			cell.setReuseIdentifier$("reuse");
		}
		cell.setText$(displayable.getString(indexPath.row()));
		if(this.choiceGroup.getType()==List.MULTIPLE&&displayable.isSelected(indexPath.row()))
			cell.setAccessoryType$(3);
		else
			cell.setAccessoryType$(0);
		return cell;
	}

	@Message
	public final void tableView$didSelectRowAtIndexPath$(UITableView table, NSIndexPath indexPath) {
		System.out.println(displayable.getString(indexPath.row()) + " selected");
		if(this.choiceGroup.getType()==List.MULTIPLE){
			displayable.setSelectedIndex(indexPath.row(), !displayable.isSelected(indexPath.row()));
		}else{
			displayable.setSelectedIndex(indexPath.row(),true);
		}
		if(commandListener!=null&&this.selectCommand!=null&&this.choiceGroup.getType()==List.IMPLICIT)
			commandListener.commandAction(this.selectCommand, displayable);
	}

	public int append(String stringPart, Image imagePart) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSelectedIndex() {
		return displayable.getSelectedIndex();
	}

	public String getString(int elementNum) {
		return displayable.getString(elementNum);
	}

	public void setSelectCommand(Command command) {
		System.out.println("setSelectCommand"+command);
		this.selectCommand=command;
	}


	public void hideNotify() {
	}

	public void invalidate() {
		// TODO Auto-generated method stub

	}

	public void showNotify() {
		System.out.println("showNotify");

		if (this.view==null) {
			this.view = new UIView().initWithFrame$(microEmulator.getWindow().bounds());

			this.navigtionBar = new UINavigationBar().initWithFrame$(new CGRect(0, 0,
					microEmulator.getWindow().bounds().size.width, NAVIGATION_HEIGHT));
			UINavigationItem title = new UINavigationItem().initWithTitle$(displayable.getTitle());
			title.setBackButtonTitle$("Back");
			this.navigtionBar.pushNavigationItem$(title);
			this.view.addSubview$(this.navigtionBar);
			
			this.tableView = new UITableView().initWithFrame$style$(
					new CGRect(0, NAVIGATION_HEIGHT, microEmulator.getWindow().bounds().size.width,
							microEmulator.getWindow().bounds().size.height - NAVIGATION_HEIGHT - TOOLBAR_HEIGHT), 0);
			
			try {
				Field cg = List.class.getDeclaredField("choiceGroup");
				cg.setAccessible(true);
				ChoiceGroup cgO = (ChoiceGroup)cg.get(displayable);
				Field type=ChoiceGroup.class.getDeclaredField("choiceType");
				type.setAccessible(true);
				this.choiceGroup = new ChoiceGroupDelegate(cgO,(Integer)type.get(cgO));
				cg.set(displayable, choiceGroup);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
			this.view.addSubview$(this.tableView);
			toolbar = (UIToolbar) new UIToolbar().initWithFrame$(new CGRect(0,
					microEmulator.getWindow().bounds().size.height - TOOLBAR_HEIGHT, microEmulator.getWindow().bounds().size.width,
					TOOLBAR_HEIGHT));
			this.view.addSubview$(toolbar);
			updateToolbar();
		}
		this.tableView.setDataSource$(this);
		this.tableView.setDelegate$(this);
		this.tableView.reloadData();

		this.view.retain();
		microEmulator.getWindow().addSubview$(this.view);
	}
}

