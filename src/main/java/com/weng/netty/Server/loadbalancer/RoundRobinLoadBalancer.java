package com.weng.netty.Server.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
/**
 * @author wengyinbing
 * @data 2021/1/29 18:39
 **/
public class RoundRobinLoadBalancer implements LoadBalancer{

    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if(index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index++);
    }
}
