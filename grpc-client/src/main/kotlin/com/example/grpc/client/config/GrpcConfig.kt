package com.example.grpc.client.config

import com.example.grpc.HelloServiceGrpcKt.HelloServiceCoroutineStub
import com.example.grpc.client.service.HelloClient
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * gRPC客户端配置类
 */
@Configuration
class GrpcConfig {

    @Value("\${grpc.server.host:localhost}")
    private val host: String = ""

    @Value("\${grpc.server.port:50051}")
    private val port: Int = 0

    /**
     * 配置gRPC通道
     */
    @Bean
    fun managedChannel(): ManagedChannel {
        println("初始化gRPC客户端连接，主机: $host，端口: $port")
        
        return ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext() // 在生产环境中应该使用TLS
            .keepAliveTime(1, TimeUnit.HOURS) // 保持连接活动
            .build()
    }

    /**
     * 配置gRPC客户端Stub
     */
    @Bean
    fun helloServiceStub(channel: ManagedChannel): HelloServiceCoroutineStub {
        return HelloServiceCoroutineStub(channel)
    }

    /**
     * 配置gRPC客户端实现
     */
    @Bean
    fun helloClient(stub: HelloServiceCoroutineStub, channel: ManagedChannel): HelloClient {
        return HelloClient(stub, channel)
    }
}