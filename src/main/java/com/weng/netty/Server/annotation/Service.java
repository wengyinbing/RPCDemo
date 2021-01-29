package com.weng.netty.Server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *表示一个服务提供类，用于远程接口的实现类
 * @author wengyinbing
 * @data 2021/1/29 20:28
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    public String name() default "";
    /*
    @Service 放在一个类上，标识这个类提供一个服务，
    @ServiceScan 放在启动的入口类上（main 方法所在的类），标识服务的扫描的包的范围。
    Service 注解的值定义为该服务的名称，默认值是该类的完整类名，
    而 ServiceScan 的值定义为扫描范围的根包，默认值为入口类所在的包，扫描时会扫描该包及其子包下所有的类，
    找到标记有 Service 的类，并注册。
     */

}