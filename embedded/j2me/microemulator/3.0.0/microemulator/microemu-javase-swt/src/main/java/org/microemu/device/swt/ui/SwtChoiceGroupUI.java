/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.microemu.device.swt.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;

import org.microemu.device.ui.ChoiceGroupUI;

/**
 *
 * @author User
 */
public class SwtChoiceGroupUI implements ChoiceGroupUI {
    
	public void setDefaultCommand(Command cmd) {
            
        }
	
	public void setLabel(String label) {
            
        }

        
	public void delete(int elementNum) {
            
        }
	
	public void deleteAll() {
            
        }

	public void setSelectedIndex(int elementNum, boolean selected) {
            
        }

	public int getSelectedIndex() {
            return -1;
        }

	public void insert(int elementNum, String stringPart, Image imagePart) {
            
        }
	
	public boolean isSelected(int elementNum) {
            return false;
        }

	public void setSelectedFlags(boolean[] selectedArray) {
            
        }

	public int getSelectedFlags(boolean[] selectedArray) {
            return -1;
        }
	
	public String getString(int elementNum) {
            return null;
        }

	public void set(int elementNum, String stringPart, Image imagePart) {
            
        }
	
	public int size() {
            return 0;
        }
    
}
