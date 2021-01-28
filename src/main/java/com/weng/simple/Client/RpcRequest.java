package com.weng.simple.Client;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消费者向提供者发送的请求对象
 * @author
 */
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RpcRequest implements Serializable {
    //待调用接口名称
    private String interfaceName;
    //待调用方法名称
    private String methodName;
    //调用方法的参数
    private Object[] parameters;
    //调用方法的参数类型
    private Class<?>[] paramTypes;
}
