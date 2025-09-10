package com.example.grpc.server.service

import com.example.grpc.HelloServiceGrpcKt.HelloServiceCoroutineImplBase
import com.example.grpc.HelloServiceOuterClass.HelloRequest
import com.example.grpc.HelloServiceOuterClass.HelloResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Service

/**
 * gRPC HelloService 服务实现类
 * 提供所有 gRPC 方法的具体实现
 */
@Service
class HelloServiceImpl : HelloServiceCoroutineImplBase() {
    /**
     * 简单的一元RPC方法实现
     */
    override suspend fun sayHello(request: HelloRequest): HelloResponse {
        val name = request.name
        val responseMessage = "你好，$name！这是来自gRPC服务器的响应。"
        println("接收到请求：$name，返回响应：$responseMessage")
        return HelloResponse.newBuilder().setMessage(responseMessage).build()
    }

    /**
     * 服务端流式RPC实现
     */
    override fun sayHelloServerStreaming(request: HelloRequest): Flow<HelloResponse> {
        val name = request.name
        println("接收到服务端流式请求：$name")
        
        // 返回一个Flow，发送多条响应
        return flow {
            for (i in 1..5) {
                val response = HelloResponse.newBuilder()
                    .setMessage("你好，$name！这是服务端流式响应 #$i")
                    .build()
                emit(response)
                println("发送服务端流式响应 #$i")
                Thread.sleep(500) // 模拟处理延迟
            }
        }
    }

    /**
     * 客户端流式RPC实现
     */
    override suspend fun sayHelloClientStreaming(requests: Flow<HelloRequest>): HelloResponse {
        println("接收到客户端流式请求")
        val names = mutableListOf<String>()
        
        // 收集所有客户端发送的请求
        requests.collect {
            val name = it.name
            names.add(name)
            println("接收到客户端流式请求：$name")
        }
        
        // 返回最终响应
        val responseMessage = "你好，${names.joinToString { it }}"
        println("返回客户端流式响应：$responseMessage")
        return HelloResponse.newBuilder().setMessage(responseMessage).build()
    }

    /**
     * 双向流式RPC实现
     */
    override fun sayHelloBidirectionalStreaming(requests: Flow<HelloRequest>): Flow<HelloResponse> {
        println("接收到双向流式请求")
        
        // 创建一个Flow，对每个请求立即返回响应
        return flow {
            requests.collect {
                val name = it.name
                println("接收到双向流式请求：$name")
                val response = HelloResponse.newBuilder()
                    .setMessage("你好，$name！这是双向流式响应")
                    .build()
                emit(response)
                println("发送双向流式响应给：$name")
            }
        }
    }
}