package com.weng.netty.Server.netty_item;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author wengyinbing
 * @data 2021/1/26 23:11
 **/
@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private final int code;

}