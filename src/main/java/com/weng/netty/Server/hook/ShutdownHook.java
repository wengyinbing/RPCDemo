package com.weng.netty.Server.hook;

import com.weng.netty.Server.ThreadPoolFactory;
import com.weng.netty.Server.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengyinbing
 * @data 2021/1/29 18:50
 **/
public class ShutdownHook {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    public void addClearAllHook() {
        logger.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            ThreadPoolFactory.shutDownAll();
        }));
    }
}
