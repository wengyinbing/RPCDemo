package com.weng.netty.Server.file;

import com.weng.netty.Server.annotation.Service;

/**
 * @author wengyinbing
 * @data 2021/1/29 21:05
 **/
//@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}