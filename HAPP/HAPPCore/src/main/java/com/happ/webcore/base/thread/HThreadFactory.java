package com.happ.webcore.base.thread;

import java.util.concurrent.ThreadFactory;

/**
 * Created by hyc on 17/2/15.
 */

public class HThreadFactory implements ThreadFactory {

    private ThreadGroup group;

    public HThreadFactory(ThreadGroup group) {
        this.group = group;
    }

    @Override
    public Thread newThread(Runnable r) {
    	 Thread thread= new Thread(this.group, r);
         thread.setName("good-"+System.currentTimeMillis());
         thread.setPriority(Thread.NORM_PRIORITY);
         return thread;
    }

}