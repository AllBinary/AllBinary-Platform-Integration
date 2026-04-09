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

import java.util.ArrayList;
import org.allbinary.logic.string.StringMaker;

public class EmuThreadPool
{
    //protected final LogUtil logUtil = LogUtil.getInstance();

    //protected final CommonStrings commonStrings = CommonStrings.getInstance();

    protected final String START_TASK = "Started Task: ";
    protected final String COMPLETE_TASK = "Completed Task: ";
    
    private boolean isAlive;
    private ArrayList taskQueue;
    private int threadID;
    private int numThreads;
    //private static int threadPoolID;

    public EmuThreadPool(int numThreads)
    {
        this.numThreads = numThreads;
    }

    public void init()
    {
        if (!this.isAlive)
        {
            isAlive = true;

            taskQueue = new ArrayList();
            for (int i = 0; i < numThreads; i++)
            {
                new DisplayPooledThread().start();
            }
        }
    }

    public synchronized void runTask(Runnable task)
    {
        if (!isAlive)
        {
            this.init();
            //throw new IllegalStateException();
        }
        if (task != null)
        {

            //logUtil.put("Add: ").append(task, this, "runTask");
            //PreLogUtil.put("Add: ").append(task, this, "runTask");

            taskQueue.add(task);
            notify();
        }
    }

    protected synchronized Runnable getTask()
            throws InterruptedException
    {
        while (taskQueue.size() == 0)
        {
            if (!isAlive)
            {
                return null;
            }
            this.wait();
        }
        return (Runnable) taskQueue.remove(0);
    }

    public synchronized void close()
    {
        if (isAlive)
        {
            isAlive = false;
            taskQueue.clear();
            //interrupt();
        }
    }

    public void join()
    {

        synchronized (this)
        {
            isAlive = false;
            notifyAll();
        }

        /*
        Thread[] threads = new Thread[MAX];
        int count = threads.length;
        for (int i = 0; i < count; i++) {
        try {
        threads[i].join();
        } catch (InterruptedException ex) {
        }
        }
         */
    }

    public boolean isBusy()
    {
        if (this.taskQueue.size() > 0)
        {
            return true;
        }

        if (this.runningTask)
        {
            return true;
        }
        return false;
    }

    protected void threadStarted()
    {
    }

    protected void threadStopped()
    {
        if (this.numThreads == 1)
        {
            this.isAlive = false;
        }
    }

    protected void startTask(Runnable task)
    {
    }

    protected void completedTask(Runnable task)
    {
    }

    private boolean runningTask;

    private class DisplayPooledThread extends Thread
    {

        private static final String ROOT_NAME = "-PooledThread-";
        
        public DisplayPooledThread()
        {
            //super(ThreadPool.this, 
            super(new StringMaker().append(EmuThreadPool.this.toString()).append(ROOT_NAME).appendint(threadID++).toString());
            //this.setDaemon(true);
        }

        private final String INTERRUPT_EXCEPTION = "Exit InterruptedException";

        public void run()
        {

            threadStarted();

            while (true)
            //while (!isInterrupted())
            {

                Runnable task = null;
                try
                {
                    task = getTask();
                    //System.out.println(task + " with Thread: " + this.toString());
                    //logUtil.put(task + " with Thread: " + this.toString(), this, commonStrings.RUN);
                    runningTask = true;

                    startTask(task);

                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                    System.out.println(INTERRUPT_EXCEPTION);
                    //logUtil.put(INTERRUPT_EXCEPTION, this, commonStrings.RUN);
                    break;
                }

                if (task == null)
                {
                    break;
                }
                /*
                else
                {
                PreLogUtil.put("Running: ").append(task, this, commonStrings.RUN);
                }
                 */

                try
                {
                    task.run();
                    completedTask(task);
                    runningTask = false;
                } catch (Exception e)
                {
                    e.printStackTrace();
                    final String EXCEPTION_LABEL = "Exception: ";
                    System.out.println(new StringMaker().append(EXCEPTION_LABEL).append(task.toString()).toString());
                    //logUtil.put(new StringMaker().append(commonStrings.EXCEPTION_LABEL).append(task).toString(), this, commonStrings.RUN, e);
                }
            }

            threadStopped();
        }
    }
}
