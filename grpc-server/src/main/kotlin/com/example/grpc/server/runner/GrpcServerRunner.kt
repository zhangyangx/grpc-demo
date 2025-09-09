package com.example.grpc.server.runner

import com.example.grpc.server.server.GrpcServer
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import javax.annotation.Resource

/**
 * gRPC服务器启动器
 * 负责在应用启动时启动gRPC服务器
 */
@Component
class GrpcServerRunner(@Resource private val grpcServer: GrpcServer) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // 在单独的线程中启动gRPC服务器，避免阻塞主线程
        Thread {
            grpcServer.start()
            grpcServer.blockUntilShutdown()
        }.start()
    }
}