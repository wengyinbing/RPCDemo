package com.weng.netty.Server;

import com.weng.netty.Client.RpcRequest;

import com.weng.netty.Server.stage.ResponseCode;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author wengyinbing
 * @data 2021/1/19 15:44
 **/
public class RequestHandler {


        private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

        private Socket socket;
        private Object service;
    {
        logger.info("RequestHandler");
    }
        /*public RequestHandler(Socket socket, Object service) {
            this.socket = socket;
            this.service = service;

        }*/

        /*
        public void run() {
            try  {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
                Object returnObject = invokeMethod(rpcRequest);
                objectOutputStream.writeObject(RpcResponse.success(returnObject));
                objectOutputStream.flush();
            } catch (Exception e) {

                        logger.error("调用或发送时有错误发生：", e);
                    }

            }*/


        public Object handle(RpcRequest rpcRequest, Object service) {

            Object result = null;
            try {
                result = invokeTargetMethod(rpcRequest, service);
                logger.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("调用或发送时有错误发生：", e);
            }
            System.out.println("输出结果：" + result);
            return result;
        }
        private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
            Method method;
            try {
                method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            } catch (NoSuchMethodException e) {
                return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
            }
            return method.invoke(service, rpcRequest.getParameters());
        }
        }
