package com.weng.netty.Server.netty_item;

import com.esotericsoftware.kryo.Kryo;
import com.weng.netty.Client.RpcRequest;
import com.weng.netty.Server.RpcResponse;
import com.weng.netty.Server.stage.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author wengyinbing
 * @data 2021/1/28 21:41
 **/
public class KryoSerializer implements CommonSerializer {

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);
    /*
    kryo 可以记录类型信息，直接将对象信息写到序列化数据中，反序列化的时候也可以直接找到原始类的信息，这就意味着不用传入class或Type类信息。
    记录类型信息的writeClassAndObject/readClassAndObject方法，以及传统的writeObject/readObject方法。

    本身不是线程安全的。可以使用KryoPool或ThreadLocal来保障线程安全。

    类注册 给类分配一个int id 但是要求反序列化的时候的id必须与序列化是的顺序一致。但是不同的机器上注册的顺序很有可能不一致。
    所以很多都禁止类注册这功能。

    可变长存储 对int和long类型都采用了可变长存储机制。
    int是4个字节，一般用1-5个字节存储。防止小数有空间浪费。一个字节有八位存储的时候有效数字只有7位，最后一位表示是否需要读取下一字节。
    String也是变长存储的应用，String序列化位length + 内容，length也会使用变长int写入字符长度。
    链接 https://www.jianshu.com/p/f56c9360936d
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);//类注册
        kryo.register(RpcRequest.class);
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);//禁止类注册
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)){
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return o;
        } catch (Exception e) {
            logger.error("反序列化时有错误发生:", e);
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}
