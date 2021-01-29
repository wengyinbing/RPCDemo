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
     KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;

}