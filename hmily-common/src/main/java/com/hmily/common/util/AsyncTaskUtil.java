package com.hmily.common.util;

import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.Executor;

/**
 * {@linkplain AsyncTask} 工具类。
 *
 * @author hehui
 */
public class AsyncTaskUtil {
    /**
     * 执行异步任务。
     *
     * @param task   ：异步任务。
     * @param params ：异步任务的参数。
     * @return
     */
    @SafeVarargs
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> execute(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            return task.execute(params);
        } else {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        }
    }

    /**
     * 执行异步任务。
     *
     * @param task   ：异步任务。
     * @param exec   ：执行异步任务的执行器，在小于 {@link Build.VERSION_CODES#HONEYCOMB} 的版本时无效。
     * @param params ：异步任务的参数。
     * @return
     */
    @SafeVarargs
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> execute(
            AsyncTask<Params, Progress, Result> task, Executor exec, Params... params) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            return task.execute(params);
        } else {
            return task.executeOnExecutor(exec, params);
        }
    }
}
