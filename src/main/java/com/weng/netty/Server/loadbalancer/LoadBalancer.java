package com.weng.netty.Server.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author wengyinbing
 * @data 2021/1/29 18:35
 **/
public interface LoadBalancer {
    Instance select(List<Instance> instances);
}
