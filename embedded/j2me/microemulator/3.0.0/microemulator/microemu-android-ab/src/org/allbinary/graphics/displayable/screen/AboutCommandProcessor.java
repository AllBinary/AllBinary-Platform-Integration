/*
* AllBinary Open License Version 1
* Copyright (c) 2022 AllBinary
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

package org.allbinary.graphics.displayable.screen;


import android.content.Context;
import android.content.Intent;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import org.allbinary.TsUtil;

import org.allbinary.business.init.LicenseInitInfoUtil;

import org.allbinary.logic.NullUtil;
import org.allbinary.logic.communication.log.LogUtil;
import org.allbinary.string.CommonStrings;

/**
 *
 * @author User
 */

public class AboutCommandProcessor {
    protected final LogUtil logUtil = LogUtil.getInstance();


    private static final AboutCommandProcessor instance = new AboutCommandProcessor();

    /**
     * @return the instance
     */
    public static AboutCommandProcessor getInstance() {
        return AboutCommandProcessor.instance;
    }

    public final String URL = "url";
    public final String NAME = "name";
    private final String WEB_VIEW_ACTIVITY = "org.allbinary.android.activity.WebViewActivity";
    
    private Object context = NullUtil.getInstance().NULL_OBJECT;
    
    public void process(final CommandListener midletCommandListener, final Command command, final Canvas canvas) {
        try {
            //midletCommandListener.commandAction(command, canvas);

            final Context context = (Context) this.context;
            final Intent intent = new Intent(context, TsUtil.getInstance().getClassClassLoader(this).loadClass(this.WEB_VIEW_ACTIVITY));
            intent.putExtra(this.NAME, LicenseInitInfoUtil.getInstance().ABOUT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch(ClassNotFoundException e) {
            final CommonStrings commonStrings = CommonStrings.getInstance();
            this.logUtil.put(commonStrings.EXCEPTION, this, "process", e);
        }
    }
    
    public void setContext(final Context context) {
        this.context = context;
    }
    
    public Context getContext() {
        return (Context) this.context;
    }

}