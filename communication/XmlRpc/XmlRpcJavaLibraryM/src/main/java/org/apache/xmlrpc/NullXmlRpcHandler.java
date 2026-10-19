/*
 * AllBinary Open Source under General Public License (LGPL) version 2.1 or the Apache License (AL) Version 2.0
 * Copyright (c) 2011
 * Created By: Travis Berthelot
 */
package org.apache.xmlrpc;

import org.allbinary.util.BasicArrayList;
import org.allbinary.util.BasicArrayListD;
import org.allbinary.logic.NullUtil;

/**
 *
 * @author User
 */
public class NullXmlRpcHandler implements XmlRpcHandler {
    
    public static final NullXmlRpcHandler NULL_XML_RPC_HANDLER = new NullXmlRpcHandler();
    
    @Override
    public Object execute (String method, BasicArrayList params)
            throws Exception {
        return NullUtil.getInstance().NULL_OBJECT;
    }

}
