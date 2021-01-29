package com.weng.netty.Server.loadbalancer;

import java.util.Random;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
/**
 * @author wengyinbing
 * @data 2021/1/29 18:37
 **/
public class RandomLoadBalancer implements LoadBalancer {
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}
