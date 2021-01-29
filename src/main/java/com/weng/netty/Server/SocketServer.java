package com.weng.netty.Server;

import com.weng.netty.Server.netty_item.CommonSerializer;
import com.weng.netty.Server.stage.RpcError;
import com.weng.netty.Server.stage.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author wengyinbing
 * @data 2021/1/27 18:58
 **/
public class SocketServer implements RpcServer {


    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
    private final ExecutorService threadPool;;
    private final String host;
    private final int port;
    private CommonSerializer serializer;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
    }


    /*private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;*/


    /*public SocketServer(ServiceRegistry serviceRegistry) {
            this.serviceRegistry = serviceRegistry;
            BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
        }*/

        public <T> void publishService(Object service, Class<T> serviceClass) throws RpcException {
            if(serializer == null) {
                logger.error("未设置序列化器");
                throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
            }
            serviceProvider.addServiceProvider(service);
            serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
            start();
        }

        @Override
        public void start() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                logger.info("服务器启动……");
                Socket socket;
                while ((socket = serverSocket.accept()) != null) {
                    logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                    threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
                }
                threadPool.shutdown();
            } catch (IOException e) {
                logger.error("服务器启动时有错误发生:", e);
            }
        }
        //@Override
        public void setSerializer(CommonSerializer serializer) {
            this.serializer = serializer;
        }

        @Override
        public void start(int port) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                logger.info("服务器启动……");
                Socket socket;
                while((socket = serverSocket.accept()) != null) {
                    logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                    threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
                }
                threadPool.shutdown();
            } catch (IOException e) {
                logger.error("服务器启动时有错误发生:", e);
            }
        }
    }