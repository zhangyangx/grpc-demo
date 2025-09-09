package com.example.grpc.client.controller

import com.example.grpc.client.service.HelloClient
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.runBlocking

/**
 * gRPC客户端控制器
 * 提供HTTP接口来测试gRPC服务调用
 */
@RestController
@RequestMapping("/api/hello")
class HelloController(private val helloClient: HelloClient) {

    /**
     * 测试一元RPC调用
     */
    @GetMapping("/unary")
    fun sayHello(@RequestParam name: String): String {
        return runBlocking {
            helloClient.sayHello(name)
        }
    }

    /**
     * 测试服务端流式RPC调用
     */
    @GetMapping("/server-streaming")
    fun sayHelloServerStreaming(@RequestParam name: String): List<String> {
        return runBlocking {
            helloClient.sayHelloServerStreaming(name).toList()
        }
    }

    /**
     * 测试客户端流式RPC调用
     */
    @GetMapping("/client-streaming")
    fun sayHelloClientStreaming(@RequestParam names: List<String>): String {
        return runBlocking {
            helloClient.sayHelloClientStreaming(names)
        }
    }

    /**
     * 测试双向流式RPC调用
     */
    @GetMapping("/bidirectional-streaming")
    fun sayHelloBidirectionalStreaming(@RequestParam names: List<String>): List<String> {
        return runBlocking {
            helloClient.sayHelloBidirectionalStreaming(names).toList()
        }
    }
}