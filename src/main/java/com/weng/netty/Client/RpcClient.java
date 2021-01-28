package com.weng.netty.Client;


import com.weng.netty.Client.RpcRequest;

/**
 * @author wengyinbing
 * @data 2021/1/21 15:19
 **/
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
