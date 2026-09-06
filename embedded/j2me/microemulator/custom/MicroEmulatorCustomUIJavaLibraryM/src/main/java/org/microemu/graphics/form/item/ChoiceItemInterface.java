/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2005 Andres Navarro
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
 *  Other Contributor(s):
 *    Travis Berthelot
 */
 
package org.microemu.graphics.form.item;


import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;


public interface ChoiceItemInterface {  

  int append(String stringPart, Image imagePart);
    
  void delete(int elementNum);
  
  void deleteAll();
  
  int getFitPolicy();
  
  Font getFont(int elementNum);
  
  //Image getImage(int elementNum);
    
  int getSelectedFlags(boolean[] selectedArray_return);
  
  int getSelectedIndex();
  
  String getString(int elementNum);
    
  void insert(int elementNum, String stringPart, Image imagePart);
  
  boolean isSelected(int elementNum);
  
  void set(int elementNum, String stringPart, Image imagePart);

  void setFitPolicy(int fitPolicy);
  
  void setFont(int elementNum, Font font);
  
  void setSelectedFlags(boolean[] selectedArray);
  
  void setSelectedIndex(int elementNum, boolean selected);
  
  int size();
  
}

