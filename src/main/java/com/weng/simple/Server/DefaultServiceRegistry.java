package com.weng.simple.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wengyinbing
 * @data 2021/1/19 15:13
 **/
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);
    private final Map<String,Object> serviceMap = new ConcurrentHashMap<String, Object>();
    private final Set<String> registereService = ConcurrentHashMap.newKeySet();


    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();//获得类名
        if(registereService.contains(serviceName)){
            return;
        }
        registereService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();//可能对应多个接口，但是这些接口都对应这一个service
        if(interfaces.length==0){
            //有错误
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);

        }
        for(Class<?>i : interfaces){
            serviceMap.put(i.getCanonicalName(),service);
        }
        logger.info("向接口: {} 注册服务: {}", interfaces, serviceName);

    }

    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service==null){
            //error
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
