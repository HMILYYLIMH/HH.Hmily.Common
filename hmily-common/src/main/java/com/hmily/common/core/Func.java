package com.hmily.common.core;

/**
 * 封装一个返回 TResult 参数指定的类型值的方法。
 *
 * @author hehui
 */
public class Func {
    /**
     * 封装一个返回 TResult 参数指定的类型值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyFuncVoid&lt;TResult&gt; extends Func.Void&lt;TResult&gt; {
     * public MyFuncVoid() {
     * new Func().super();
     * }
     * 
     * public MyFuncVoid(Func func) {
     * func.super();
     * }
     * 
     * &#064;Override
     * public TResult invoke() {
     * return null;
     * }
     * }
     * </pre>
     *
     * @param <TResult>：此委托封装的方法的返回值类型。
     * @author hehui
     */
    public abstract class Void<TResult> {
        /**
         * 执行方法。
         *
         * @return ：此委托封装的方法的返回值
         */
        public abstract TResult invoke();
    }

    /**
     * 封装一个具有一个参数并返回 TResult 参数指定的类型值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyFuncOne&lt;T, TResult&gt; extends Func.One&lt;T, TResult&gt; {
     * public MyFuncOne() {
     * new Func().super();
     * }
     * 
     * public MyFuncOne(Func func) {
     * func.super();
     * }
     * 
     * &#064;Override
     * public TResult invoke(T arg) {
     * return null;
     * }
     * }
     * </pre>
     *
     * @param <T>：此委托封装的方法的参数类型。
     * @param <TResult>：此委托封装的方法的返回值类型。
     * @author hehui
     */
    public abstract class One<T, TResult> {
        /**
         * 执行方法。
         *
         * @param arg ：此委托封装的方法的参数。
         * @return ：此委托封装的方法的返回值
         */
        public abstract TResult invoke(T arg);
    }

    /**
     * 封装一个具有两个参数并返回 TResult 参数指定的类型值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyFuncTwo&lt;T1, T2, TResult&gt; extends Func.Two&lt;T1, T2, TResult&gt; {
     * public MyFuncTwo() {
     * new Func().super();
     * }
     * 
     * public MyFuncTwo(Func func) {
     * func.super();
     * }
     * 
     * &#064;Override
     * public TResult invoke(T1 arg1, T2 arg2) {
     * return null;
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <TResult>：此委托封装的方法的返回值类型。
     * @author hehui
     */
    public abstract class Two<T1, T2, TResult> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @return ：此委托封装的方法的返回值
         */
        public abstract TResult invoke(T1 arg1, T2 arg2);
    }

    /**
     * 封装一个具有三个参数并返回 TResult 参数指定的类型值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyFuncThree&lt;T1, T2, T3, TResult&gt; extends Func.Three&lt;T1, T2, T3, TResult&gt; {
     * public MyFuncThree() {
     * new Func().super();
     * }
     * 
     * public MyFuncThree(Func func) {
     * func.super();
     * }
     * 
     * &#064;Override
     * public TResult invoke(T1 arg1, T2 arg2, T3 arg3) {
     * return null;
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <T3>：此委托封装的方法的第三个参数类型。
     * @param <TResult>：此委托封装的方法的返回值类型。
     * @author hehui
     */
    public abstract class Three<T1, T2, T3, TResult> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @param arg3 ：此委托封装的方法的第三个参数。
         * @return ：此委托封装的方法的返回值
         */
        public abstract TResult invoke(T1 arg1, T2 arg2, T3 arg3);
    }

    /**
     * 封装一个具有四个参数并返回 TResult 参数指定的类型值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyFuncFour&lt;T1, T2, T3, T4, TResult&gt; extends Func.Four&lt;T1, T2, T3, T4, TResult&gt; {
     * public MyFuncFour() {
     * new Func().super();
     * }
     * 
     * public MyFuncFour(Func func) {
     * func.super();
     * }
     * 
     * &#064;Override
     * public TResult invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
     * return null;
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <T3>：此委托封装的方法的第三个参数类型。
     * @param <T4>：此委托封装的方法的第四个参数类型。
     * @param <TResult>：此委托封装的方法的返回值类型。
     * @author hehui
     */
    public abstract class Four<T1, T2, T3, T4, TResult> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @param arg3 ：此委托封装的方法的第三个参数。
         * @param arg4 ：此委托封装的方法的第四个参数。
         * @return ：此委托封装的方法的返回值
         */
        public abstract TResult invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }

    /**
     * 封装一个具有五个参数并返回 TResult 参数指定的类型值的方法。
     * 
     * <pre class="prettyprint">
     * public class MyFuncFive&lt;T1, T2, T3, T4, T5, TResult&gt; extends Func.Five&lt;T1, T2, T3, T4, T5, TResult&gt; {
     * public MyFuncFive() {
     * new Func().super();
     * }
     * 
     * public MyFuncFive(Func func) {
     * func.super();
     * }
     * 
     * &#064;Override
     * public TResult invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
     * return null;
     * }
     * }
     * </pre>
     *
     * @param <T1>：此委托封装的方法的第一个参数类型。
     * @param <T2>：此委托封装的方法的第二个参数类型。
     * @param <T3>：此委托封装的方法的第三个参数类型。
     * @param <T4>：此委托封装的方法的第四个参数类型。
     * @param <T5>：此委托封装的方法的第五个参数类型。
     * @param <TResult>：此委托封装的方法的返回值类型。
     * @author hehui
     */
    public abstract class Five<T1, T2, T3, T4, T5, TResult> {
        /**
         * 执行方法。
         *
         * @param arg1 ：此委托封装的方法的第一个参数。
         * @param arg2 ：此委托封装的方法的第二个参数。
         * @param arg3 ：此委托封装的方法的第三个参数。
         * @param arg4 ：此委托封装的方法的第四个参数。
         * @param arg5 ：此委托封装的方法的第五个参数。
         * @return ：此委托封装的方法的返回值
         */
        public abstract TResult invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
    }
}
