package com.weng.netty.Server.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengyinbing
 * @data 2021/1/21 15:31
 **/
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String hello(HelloObject hello) {
        logger.info("接收到：{}",hello.getMessage());
        return "这是掉用的返回值，id=" + hello.getId();
    }
}
