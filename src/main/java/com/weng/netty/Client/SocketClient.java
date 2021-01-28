package com.weng.netty.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.weng.netty.Server.RpcResponse;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.ResponseCode;
import com.weng.netty.Server.stage.RpcException;
/**
 * @author wengyinbing
 * @data 2021/1/27 18:33
 **/
public class SocketClient implements RpcClient {


    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private final String host;
    private final int port;

    public SocketClient(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public Object sendRequest(RpcRequest rpcRequest) {
            try (Socket socket = new Socket(host, port)) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream.writeObject(rpcRequest);
                objectOutputStream.flush();
                RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
                System.out.println(rpcResponse.getStatusCode());
                if(rpcResponse == null) {
                    logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                    throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
                }
                if(rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
                    /*System.out.println(rpcResponse.getStatusCode() == null);
                    System.out.println(rpcResponse.getStatusCode());
                    System.out.println(ResponseCode.SUCCESS.getCode());*/
                    logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                    throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
                }
                return rpcResponse.getData();
            } catch (IOException | ClassNotFoundException | RpcException e) {
                logger.error("调用时有错误发生：", e);
                //throw new RpcException("服务调用失败: ", e);
            }
            return null;
        }

    }