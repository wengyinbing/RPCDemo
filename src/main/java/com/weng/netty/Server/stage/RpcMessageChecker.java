package com.weng.netty.Server.stage;

import com.weng.netty.Client.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wengyinbing
 * @data 2021/1/29 10:42
 * 检查响应请求
 **/
public class RpcMessageChecker {
    public static final String INTERFACE_NAME = "interfaceName";
    private static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);

    private RpcMessageChecker() {
    }

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse) throws RpcException {
        if (rpcResponse == null) {
            logger.error("调用服务失败,serviceName:{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
            logger.error("调用服务失败,serviceName:{},RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}
