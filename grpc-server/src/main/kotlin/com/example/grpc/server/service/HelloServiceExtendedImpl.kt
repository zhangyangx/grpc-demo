package com.example.grpc.server.service

import com.example.grpc.HelloServiceGrpcKt.HelloServiceCoroutineImplBase
import com.example.grpc.HelloServiceOuterClass.HelloRequest
import com.example.grpc.HelloServiceOuterClass.HelloResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service

/**
 * gRPC HelloService 扩展实现类
 * 提供与主服务不同的实现，用于演示多服务自动注册功能
 */
@Service("extendedHelloService")
class HelloServiceExtendedImpl : HelloServiceCoroutineImplBase() {
    /**
     * 扩展版一元RPC方法实现
     */
    override suspend fun sayHello(request: HelloRequest): HelloResponse {
        val name = request.name
        return HelloResponse.newBuilder()
            .setMessage("扩展服务: 你好, $name! 这是来自扩展服务的问候")
            .build()
    }

    /**
     * 扩展版服务端流式RPC方法实现
     */
    override fun sayHelloServerStreaming(request: HelloRequest): Flow<HelloResponse> {
        val name = request.name
        return flow {
            for (i in 1..5) {
                delay(500) // 模拟处理延迟
                emit(HelloResponse.newBuilder()
                    .setMessage("扩展服务流式响应 $i: 你好, $name!")
                    .build())
            }
        }
    }

    /**
     * 扩展版客户端流式RPC方法实现
     */
    override suspend fun sayHelloClientStreaming(requests: Flow<HelloRequest>): HelloResponse {
        val names = mutableListOf<String>()
        requests.collect {
            names.add(it.name)
        }
        return HelloResponse.newBuilder()
            .setMessage("扩展服务: 收到了 ${names.size} 个客户端流请求，问候 ${names.joinToString(", ")}")
            .build()
    }

    /**
     * 扩展版双向流式RPC方法实现
     */
    override fun sayHelloBidirectionalStreaming(requests: Flow<HelloRequest>): Flow<HelloResponse> {
        return flow {
            requests.collect {
                delay(300) // 模拟处理延迟
                emit(HelloResponse.newBuilder()
                    .setMessage("扩展服务双向流响应: 你好, ${it.name}!")
                    .build())
            }
        }
    }
}