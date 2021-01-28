package com.weng.simple.Server;

/**
 * @author wengyinbing
 * @data 2021/1/19 15:08
 *保存本地服务的信息，在获得一个服务名字的时候，能够返回这个服务的信息
 **/
public interface ServiceRegistry {
    <T> void register(T service);
    Object getService(String serviceName);
}
