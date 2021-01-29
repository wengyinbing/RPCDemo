package com.weng.netty.Server;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import com.weng.netty.Server.loadbalancer.LoadBalancer;
import com.weng.netty.Server.loadbalancer.RandomLoadBalancer;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author wengyinbing
 * @data 2021/1/29 9:06
 **/
public class NacosServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static  NamingService namingService ;

    static {
        /*
        通过 NamingFactory 创建 NamingService 连接 Nacos
        在类加载时自动连接。
         */
        try {
            namingService = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            logger.error("连接到Nacos时有错误发生: ", e);
            try {
                throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
            } catch (RpcException e1) {
                e1.printStackTrace();
            }

        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) throws RpcException {
        try {
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
            //直接向 Nacos 注册服务
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);//获得提供某个服务的所有提供者的列表
            Instance instance = instances.get(0);//所有提供者列表后，需要选择一个，这里就涉及了负载均衡策略，这里我们先选择第 0 个，
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
