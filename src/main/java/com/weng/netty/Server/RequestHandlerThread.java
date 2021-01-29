package com.weng.netty.Server;


import com.weng.netty.Client.RpcRequest;
import com.weng.netty.Server.netty_item.CommonSerializer;
import com.weng.netty.Server.netty_item.ObjectReader;
import com.weng.netty.Server.netty_item.ObjectWriter;
import com.weng.netty.Server.stage.RpcException;
import com.weng.netty.Server.stage.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author wengyinbing
 * @data 2021/1/19 16:08
 **/
public class RequestHandlerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    private CommonSerializer serializer;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
        this.serializer = serializer;

    }

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }


    public void run() {

        try {
            /*RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            //Object service = serviceRegistry.getService(interfaceName);
            //Object result = requestHandler.handle(rpcRequest, service);
            Object result = requestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result,rpcRequest.getRequestId()));
            objectOutputStream.flush();*/
            //ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            Object obj = ObjectReader.readObject(inputStream);
            RpcRequest rpcRequest = (RpcRequest) obj;
            String interfaceName = rpcRequest.getInterfaceName();
            Object result = requestHandler.handle(rpcRequest);
            ObjectWriter.writeObject(outputStream, RpcResponse.success(result,rpcRequest.getRequestId()), serializer);
            logger.info("ResquestHandlerThread!!!!!");
        } catch (IOException e) {//| ClassNotFoundException
            logger.error("调用或发送时有错误发生：", e);
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }
}
