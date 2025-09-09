package com.example.grpc.server.server

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import java.util.concurrent.Executors

/**
 * gRPC服务器实现类
 * 负责启动、停止和管理gRPC服务器
 * 支持自动注册多个gRPC服务，无需每次添加新服务都修改启动类
 */
class GrpcServer(
    private val port: Int,
    grpcServices: List<BindableService>,
    threadPoolSize: Int
) {
    private val server: Server

    init {
        val executor = Executors.newFixedThreadPool(threadPoolSize)
        val serverBuilder = ServerBuilder.forPort(port)
            .executor(executor)

        // 自动注册所有gRPC服务
        grpcServices.forEach {
            serverBuilder.addService(it)
            println("已注册gRPC服务: \${it.javaClass.simpleName}")
        }

        server = serverBuilder.build()
    }

    /**
     * 启动服务器
     */
    fun start() {
        server.start()
        println("gRPC服务器启动成功，监听端口: $port")

        // 添加JVM关闭钩子，确保服务器能够优雅关闭
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("正在关闭gRPC服务器...")
                this@GrpcServer.stop()
                println("gRPC服务器已关闭")
            }
        )
    }

    /**
     * 停止服务器
     */
    private fun stop() {
        server.shutdown()
    }

    /**
     * 阻塞当前线程直到服务器被终止
     */
    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}