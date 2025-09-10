package com.example.grpc.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * gRPC客户端Spring Boot应用主类
 * 作为应用程序的入口点，负责启动Spring Boot应用
 */
@SpringBootApplication(scanBasePackages = ["com.example.grpc.client"]) // 指定扫描包路径以包含配置和服务类
class GrpcClientApplication

/**
 * 应用程序入口点
 */
fun main(args: Array<String>) {
    runApplication<GrpcClientApplication>(*args)
}