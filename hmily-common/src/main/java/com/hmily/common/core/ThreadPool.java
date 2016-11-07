package com.hmily.common.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池处理类。<br>
 * 当正在运行的线程数达到最大值时，继续提交线程任务将直接抛出异常，开发人员需要对系统使用线程的方式重新评估。
 *
 * @author hehui
 */
public class ThreadPool {
    /**
     * 线程池维护线程的最少数量。
     */
    private static int mCorePoolSize = 3;

    /**
     * 线程池所使用的缓冲队列的容量。
     */
    private static int mWorkQueueCapacity = 2;

    /**
     * 线程池维护线程的最大数量。
     */
    private static int mMaxPoolSize = 25;

    /**
     * 线程池维护线程所允许的空闲时间（单位：毫秒）。
     */
    private static long mKeepAliveTime = 1000;

    /**
     * 执行实例。
     */
    private static ExecutorService mThreadPoolExecutor = null;

    static {
        mThreadPoolExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaxPoolSize, mKeepAliveTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(mWorkQueueCapacity),
                new ThreadPoolExecutor.AbortPolicy());
    }

    private ThreadPool() {
    }

    /**
     * 将方法排入队列以便执行。 此方法在有线程池线程变得可用时执行。
     *
     * @param r ：要执行的方法。
     */
    public static void queueWork(final Runnable r) {
        mThreadPoolExecutor.execute(r);
    }
}
