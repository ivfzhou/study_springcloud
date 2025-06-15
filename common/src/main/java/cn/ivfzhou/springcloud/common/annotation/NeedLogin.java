package cn.ivfzhou.springcloud.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Target 当前注解的作用范围：
 * ElementType.CONSTRUCTOR：能够标注在构造方法上。
 * ElementType.FIELD：能够标注在属性（全局变量）上。
 * ElementType.LOCAL_VARIABLE：能够标注在局部变量上。
 * ElementType.METHOD：方法上。
 * ElementType.PARAMETER：方法形参。
 * ElementType.TYPE：类、接口、内部类。
 * <p>
 * Retention 当前注解的有效范围：
 * RetentionPolicy.SOURCE：表示当前注解在源码中有效，一旦编译成 class 文件，注解会丢失。
 * RetentionPolicy.CLASS：表示当前注解在源码、class 文件中有效，一旦运行时，就会丢失。
 * RetentionPolicy.RUNTIME：表示当前注解运行时有效，如果需要配合反射使用，必须是 Runtime 范围。
 * <p>
 * 方法语法： 返回值类型 方法名() [default 默认值];
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {
}
