package com.weng.netty.Server;

import java.net.InetSocketAddress;

/**
 * @author wengyinbing
 * @data 2021/1/29 18:53
 **/
public interface ServiceDiscovery {
    //根据服务名称查找服务实体
    InetSocketAddress lookupService(String serviceName);
}
