package com.weng.netty.Server;

import com.weng.netty.Server.annotation.Service;
import com.weng.netty.Server.annotation.ServiceScan;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.RpcException;
import com.weng.netty.Server.util.ReflectUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author wengyinbing
 * @data 2021/1/29 20:39
 **/
public abstract class AbstractRpcServer implements RpcServer {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass = null;
        try {
            startClass = Class.forName(mainClassName);
            if(!startClass.isAnnotationPresent(ServiceScan.class)) {
                logger.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            logger.error("出现未知错误");
            try {
                throw new RpcException(RpcError.UNKNOWN_ERROR);
            } catch (RpcException e1) {
                e1.printStackTrace();
            }
        } catch (RpcException e) {
            e.printStackTrace();
        }
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if("".equals(basePackage)) {
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for(Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        try {
            serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }

}
