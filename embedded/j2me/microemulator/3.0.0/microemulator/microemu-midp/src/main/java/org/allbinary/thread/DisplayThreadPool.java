/*
* AllBinary Open License Version 1
* Copyright (c) 2011
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
package org.allbinary.thread;

/**
 *
 * @author user
 */
public class DisplayThreadPool extends EmuThreadPool
{

    //Watch out for the Android/J2ME thread limit
    private static final EmuThreadPool THREAD_POOL = new DisplayThreadPool(1);

    public static EmuThreadPool getInstance()
    {
        return THREAD_POOL;
    }

    public DisplayThreadPool(int total)
    {
        super(total);
    }

    public synchronized void runTask(Runnable task)
    {
        //System.out.println(new StringBuffer().append(this).append(START_LABEL).append(task).append(System.currentTimeMillis()).toString());
        //LogUtil.put(LogFactory.getInstance(new StringMaker().append(CommonLabels.getInstance().START_LABEL).append(task).toString(), this, "runTask"));

        super.runTask(task);
    }

    //private final String START_LABEL = "runTask";
    //private final String START_TASK = "Started Task: ";
    //private final String COMPLETE_TASK = "Completed Task: ";

    protected void startTask(Runnable task)
    {
        //System.out.println(new StringBuffer().append(this).append(START_TASK).append(task).append(System.currentTimeMillis()).toString());
        //LogUtil.put(LogFactory.getInstance(new StringMaker().append(START_TASK).append(task).toString(), this, CommonStrings.getInstance().RUN));
    }

    protected void completedTask(Runnable task)
    {
        //System.out.println(new StringBuffer().append(this).append(COMPLETE_TASK).append(task).append(System.currentTimeMillis()).toString());
        //LogUtil.put(LogFactory.getInstance(new StringMaker().append(COMPLETE_TASK).append(task).toString(), this, CommonStrings.getInstance().RUN));
    }

}
