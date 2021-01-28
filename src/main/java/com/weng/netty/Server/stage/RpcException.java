package com.weng.netty.Server.stage;



import java.util.concurrent.Executors;

/**
 * @author wengyinbing
 * @data 2021/1/21 16:10
 **/
public class RpcException extends Exception {
    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
