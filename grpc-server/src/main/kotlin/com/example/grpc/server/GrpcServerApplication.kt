package com.example.grpc.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * gRPC服务器Spring Boot应用主类
 * 作为应用程序的入口点，负责启动Spring Boot应用
 */
@SpringBootApplication(scanBasePackages = ["com.example.grpc.server"]) // 指定扫描包路径以包含配置、服务和启动器类
class GrpcServerApplication

/**
 * 应用程序入口点
 */
fun main(args: Array<String>) {
    runApplication<GrpcServerApplication>(*args)
}