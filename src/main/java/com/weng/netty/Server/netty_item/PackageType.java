package com.weng.netty.Server.netty_item;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wengyinbing
 * @data 2021/1/26 23:09
 **/
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}