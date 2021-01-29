package com.weng.netty.Server;

import com.weng.netty.Server.stage.RpcException;

import java.net.InetSocketAddress;

/**
 * @author wengyinbing
 * @data 2021/1/19 15:08
 * 远程注册表（Nacos）
 **/
public interface ServiceRegistry {
    void register(String serviceName, InetSocketAddress inetSocketAddress) throws RpcException;
    InetSocketAddress lookupService(String serviceName);
}
