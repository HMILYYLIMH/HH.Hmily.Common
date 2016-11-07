package com.hmily.common.core;

/**
 * 封装一个没有返回值的方法。
 *
 * @author hehui
 */
public class Action {
    /**
     * 封装一个没有参数且没有返回值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyActionVoid extends Void {
     * 
     * public MyActionVoid() {
     * new Action().super();
     * }
     * 
     * public MyActionVoid(Action action) {
     * action.super();
     * }
     * 
     * &#064;Override
     * public void invoke() {
     * ...
     * }
     * }
     * </pre>
     *
     * @author hehui
     */
    public abstract class Void {
        /**
         * 执行方法。
         */
        public abstract void invoke();
    }

    /**
     * 封装一个具有一个参数且没有返回值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyActionOne&lt;T&gt; extends Action.One&lt;T&gt; {
     * public MyActionOne() {
     * new Action().super();
     * }
     * 
     * public MyActionOne(Action action) {
     * action.super();
     * }
     * 
     * &#064;Override
     * public void invoke(T arg) {
     * ...
     * }
     * }
     * </pre>
     *
     * @param <T>：此委托封装的方法的参数类型。
     * @author hehui
     */
    public abstract class One<T> {
        /**
         * 执行方法。
         *
         * @param arg ：此委托封装的方法的参数。
         */
        public abstract void invoke(T arg);
    }

    /**
     * 封装一个具有两个参数且没有返回值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyActionTwo&lt;T1, T2&gt; extends Action.Two&lt;T1, T2&gt; {
     * public MyActionTwo() {
     * new Action().super();
     * }
     * 
     * public MyActionTwo(Action action) {
     * action.super();
     * }
     * 
     * &#064;Override
     * public void invoke(T1 arg1, T2 arg2) {
     * ...
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @author hehui
     */
    public abstract class Two<T1, T2> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         */
        public abstract void invoke(T1 arg1, T2 arg2);
    }

    /**
     * 封装一个具有三个参数且没有返回值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyActionThree&lt;T1, T2, T3&gt; extends Action.Three&lt;T1, T2, T3&gt; {
     * public MyActionThree() {
     * new Action().super();
     * }
     * 
     * public MyActionThree(Action action) {
     * action.super();
     * }
     * 
     * &#064;Override
     * public void invoke(T1 arg1, T2 arg2, T3 arg3) {
     * ...
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <T3>：此委托封装的方法的第三个参数类型。
     * @author hehui
     */
    public abstract class Three<T1, T2, T3> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @param arg3 ：此委托封装的方法的第三个参数。
         */
        public abstract void invoke(T1 arg1, T2 arg2, T3 arg3);
    }

    /**
     * 封装一个具有四个参数且没有返回值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyActionFour&lt;T1, T2, T3, T4&gt; extends Action.Four&lt;T1, T2, T3, T4&gt; {
     * public MyActionFour() {
     * new Action().super();
     * }
     * 
     * public MyActionFour(Action action) {
     * action.super();
     * }
     * 
     * &#064;Override
     * public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
     * ...
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <T3>：此委托封装的方法的第三个参数类型。
     * @param <T4>：此委托封装的方法的第四个参数类型。
     * @author hehui
     */
    public abstract class Four<T1, T2, T3, T4> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @param arg3 ：此委托封装的方法的第三个参数。
         * @param arg4 ：此委托封装的方法的第四个参数。
         */
        public abstract void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }

    /**
     * 封装一个具有五个参数且没有返回值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyActionFive&lt;T1, T2, T3, T4, T5&gt; extends Action.Five&lt;T1, T2, T3, T4, T5&gt; {
     * public MyActionFive() {
     * new Action().super();
     * }
     * 
     * public MyActionFive(Action action) {
     * action.super();
     * }
     * 
     * &#064;Override
     * public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
     * ...
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <T3>：此委托封装的方法的第三个参数类型。
     * @param <T4>：此委托封装的方法的第四个参数类型。
     * @param <T5>：此委托封装的方法的第五个参数类型。
     * @author hehui
     */
    public abstract class Five<T1, T2, T3, T4, T5> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @param arg3 ：此委托封装的方法的第三个参数。
         * @param arg4 ：此委托封装的方法的第四个参数。
         * @param arg5 ：此委托封装的方法的第五个参数。
         */
        public abstract void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
    }
}