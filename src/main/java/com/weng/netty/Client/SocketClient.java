package com.weng.netty.Client;


import com.weng.netty.Server.NacosServiceRegistry;
import com.weng.netty.Server.ServiceRegistry;
import com.weng.netty.Server.netty_item.CommonSerializer;
import com.weng.netty.Server.netty_item.ObjectReader;
import com.weng.netty.Server.netty_item.ObjectWriter;
import com.weng.netty.Server.stage.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.weng.netty.Server.stage.RpcResponse;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.ResponseCode;
import com.weng.netty.Server.stage.RpcException;
/**
 * @author wengyinbing
 * @data 2021/1/27 18:33
 **/
public class SocketClient implements RpcClient {


    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    //private final String host;
    //private final int port;
    private final ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;
    /*public SocketClient(String host, int port) {
            this.host = host;
            this.port = port;
        }*/
    public SocketClient() {
        this.serviceRegistry = new NacosServiceRegistry();
    }
    @Override
    public Object sendRequest(RpcRequest rpcRequest) throws RpcException {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //try (Socket socket = new Socket(host, port)) {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            try (Socket socket = new Socket()) {
                socket.connect(inetSocketAddress);
                logger.info("客户端连接服务器{}成功！",inetSocketAddress.toString());
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
                Object obj = ObjectReader.readObject(inputStream);
                RpcResponse rpcResponse = (RpcResponse) obj;
                //System.out.println(rpcResponse.toString());
                if (rpcResponse == null) {
                    logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                    throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
                }
                if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
                    logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                    throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
                }
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                return rpcResponse.getData();
            } catch (IOException e) {
                logger.error("调用时有错误发生：", e);
                throw new RpcException("服务调用失败: ", e);
            }
        }
        //@Override
        public void setSerializer(CommonSerializer serializer) {
            this.serializer = serializer;
        }

        /*@Override
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
                    *//*System.out.println(rpcResponse.getStatusCode() == null);
                    System.out.println(rpcResponse.getStatusCode());
                    System.out.println(ResponseCode.SUCCESS.getCode());*//*
                    logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                    throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
                }
                return rpcResponse.getData();
            } catch (IOException | ClassNotFoundException | RpcException e) {
                logger.error("调用时有错误发生：", e);
                //throw new RpcException("服务调用失败: ", e);
            }
            return null;
        }*/

    }