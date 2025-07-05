/*
* AllBinary Open License Version 1
* Copyright (c) 2011 AllBinary
* 
* By agreeing to this license you and any business entity you represent are
* legally bound to the AllBinary Open License Version 1 legal agreement.
* 
* You may obtain the AllBinary Open License Version 1 legal agreement from
* AllBinary or the root directory of AllBinary's AllBinary Platform repository.
* 
* Created By: Travis Berthelot
* 
*/
package org.allbinary.logic.io;

import java.io.InputStream;
import org.allbinary.string.CommonStrings;
import org.allbinary.logic.communication.log.LogFactory;
import org.allbinary.logic.communication.log.LogUtil;

public class InputStreamUtil
{

    private static final InputStreamUtil instance = new InputStreamUtil();

    /**
     * @return the instance
     */
    public static InputStreamUtil getInstance()
    {
        return instance;
    }

    private InputStreamUtil()
    {
    }

    public int get(final InputStream inputStream, final byte[] buffer, final int size) throws Exception
    {
        //final CommonStrings commonStrings = CommonStrings.getInstance();

        int total = 0;
        int len = 0;
        int max = buffer.length;

        //LogUtil.put(LogFactory.getInstance("size: " + size, this, commonStrings.PROCESS));
        
        if(size > 0) {
            max = size;
        }

        while ((len = inputStream.read(buffer, total, max - total)) > 0 || (size != 0 && inputStream.available() > 0))
        {
            //LogUtil.put(LogFactory.getInstance("len: " + len, this, commonStrings.PROCESS));
            total = total + len;
            //LogUtil.put(LogFactory.getInstance("total: " + total, this, commonStrings.PROCESS));
        }
       
        return total;
    }

}
