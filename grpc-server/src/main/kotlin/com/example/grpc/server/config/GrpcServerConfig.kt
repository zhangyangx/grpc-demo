package com.example.grpc.server.config

import com.example.grpc.server.server.GrpcServer
import io.grpc.BindableService
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * gRPC服务器配置类
 * 负责配置和管理gRPC服务器
 */
@Configuration
class GrpcServerConfig {

    @Value("\${grpc.server.port:50051}")
    private val port: Int = 0

    @Value("\${grpc.server.host:localhost}")
    private val host: String = ""

    @Value("\${grpc.server.thread-pool.size:10}")
    private val threadPoolSize: Int = 0

    /**
     * 配置gRPC服务器
     * 自动注入所有gRPC服务实现（BindableService类型的Bean）
     * 支持添加新服务时无需修改此配置
     */
    @Bean
    fun grpcServer(@Autowired grpcServices: List<BindableService>): GrpcServer {
        println("初始化gRPC服务器，主机: $host，端口: $port，服务数量: \${grpcServices.size}，线程池大小: $threadPoolSize")
        return GrpcServer(port, grpcServices, threadPoolSize)
    }
}