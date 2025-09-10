package com.example.grpc.client.service

import com.example.grpc.HelloServiceGrpcKt.HelloServiceCoroutineStub
import io.grpc.ManagedChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

/**
 * gRPC客户端服务实现类
 */
class HelloClient(
    private val stub: HelloServiceCoroutineStub,
    private val channel: ManagedChannel
) {
    
    /**
     * 简单的一元RPC调用
     */
    suspend fun sayHello(name: String): String {
        val request = com.example.grpc.HelloServiceOuterClass.HelloRequest.newBuilder()
            .setName(name)
            .build()

        val response = stub.sayHello(request)
        return response.message
    }

    /**
     * 服务端流式RPC调用
     */
    fun sayHelloServerStreaming(name: String): Flow<String> {
        val request = com.example.grpc.HelloServiceOuterClass.HelloRequest.newBuilder()
            .setName(name)
            .build()

        val responseFlow = stub.sayHelloServerStreaming(request)
        return flow {
            responseFlow.collect {
                emit(it.message)
            }
        }
    }

    /**
     * 客户端流式RPC调用
     */
    suspend fun sayHelloClientStreaming(names: List<String>): String {
        val requestFlow = flow {
            for (name in names) {
                val request = com.example.grpc.HelloServiceOuterClass.HelloRequest.newBuilder()
                    .setName(name)
                    .build()
                emit(request)
            }
        }

        val response = stub.sayHelloClientStreaming(requestFlow)
        return response.message
    }

    /**
     * 双向流式RPC调用
     */
    fun sayHelloBidirectionalStreaming(names: List<String>): Flow<String> {
        val requestFlow = flow {
            for (name in names) {
                val request = com.example.grpc.HelloServiceOuterClass.HelloRequest.newBuilder()
                    .setName(name)
                    .build()
                emit(request)
            }
        }

        val responseFlow = stub.sayHelloBidirectionalStreaming(requestFlow)
        return flow {
            responseFlow.collect {
                emit(it.message)
            }
        }
    }

    /**
     * 关闭通道
     */
    fun shutdown() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}