package com.hmily.common.core;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 委托，表示将处理两个事件数据的事件的方法。
 *
 * @param <T1> 第一个数据类型。
 * @param <T2> 第二个数据类型。
 * @author hehui
 */
public class EventHandler<T1, T2> {
    private CopyOnWriteArraySet<Action.Two<T1, T2>> mEvents = new CopyOnWriteArraySet<Action.Two<T1, T2>>();

    /**
     * 添加事件。
     *
     * @param event ：委托执行的事件。
     */
    public void addEvent(Action.Two<T1, T2> event) {
        if (event != null) {
            mEvents.add(event);
        }
    }

    /**
     * 移除事件。
     *
     * @param event ：委托执行的事件。
     */
    public void removeEvent(Action.Two<T1, T2> event) {
        if (event != null) {
            mEvents.remove(event);
        }
    }

    /**
     * 触发事件。
     *
     * @param arg1 ：第一个数据。
     * @param arg2 ：第二个数据。
     */
    public void invoke(T1 arg1, T2 arg2) {
        synchronized (mEvents) {
            for (Action.Two<T1, T2> event : mEvents) {
                event.invoke(arg1, arg2);
            }
        }
    }
}
