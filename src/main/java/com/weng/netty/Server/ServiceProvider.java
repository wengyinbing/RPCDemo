package com.weng.netty.Server;

/**
 * @author wengyinbing
 * @data 2021/1/29 8:59
 * 本地保存服务
 * 保存本地服务的信息，在获得一个服务名字的时候，能够返回这个服务的信息
 **/
public interface ServiceProvider {
    <T> void addServiceProvider(T service);
    <T> void addServiceProvider(T service, String serviceName);
    Object getServiceProvider(String serviceName);
}
