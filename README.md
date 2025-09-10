# gRPC演示项目

这是一个基于Kotlin和Spring Boot的gRPC演示项目，展示了四种gRPC通信模式的实现：简单一元RPC、服务端流式RPC、客户端流式RPC和双向流式RPC。

## 项目结构

```
grpc-demo/
├── common/                # 公共模块，包含protobuf消息定义
├── grpc-server/           # gRPC服务器实现
├── grpc-client/           # gRPC客户端实现
└── build.gradle.kts       # 项目构建配置
```

### 模块说明

1. **common**
   - 包含Protobuf消息定义文件
   - 定义了所有服务接口和消息结构

2. **grpc-server**
   - 实现了gRPC服务接口
   - 提供了主服务和扩展服务两种实现
   - 支持多服务自动注册

3. **grpc-client**
   - 实现了gRPC客户端调用
   - 提供REST API来测试gRPC服务

## 技术栈

- **编程语言**: Kotlin 2.0.21
- **框架**: Spring Boot 3.2.9
- **RPC框架**: gRPC 1.64.0
- **消息格式**: Protocol Buffers 3.25.3
- **构建工具**: Gradle
- **协程支持**: Kotlin Coroutines

## 功能特性

### gRPC服务接口

项目实现了四种gRPC通信模式：

1. **一元RPC (Unary RPC)**
   - 客户端发送一个请求，服务端返回一个响应

2. **服务端流式RPC (Server Streaming RPC)**
   - 客户端发送一个请求，服务端返回一个流式响应

3. **客户端流式RPC (Client Streaming RPC)**
   - 客户端发送一个流式请求，服务端返回一个响应

4. **双向流式RPC (Bidirectional Streaming RPC)**
   - 客户端和服务端都可以通过流发送一系列消息

### 扩展特性

- **多服务自动注册**：支持同时注册多个gRPC服务实现
- **线程池配置**：可配置的线程池大小
- **优雅关闭**：支持JVM关闭钩子，确保服务器能够优雅关闭
- **REST API接口**：提供HTTP接口来测试gRPC服务调用

## 快速开始

### 环境要求

- JDK 17或更高版本
- Gradle 8.0或更高版本

### 构建项目

```bash
./gradlew build
```

### 启动服务器

```bash
# 启动gRPC服务器
./gradlew :grpc-server:bootRun
```

服务器将在端口50051上启动gRPC服务，并在端口8082上启动Web管理界面。

### 启动客户端

```bash
# 启动gRPC客户端
./gradlew :grpc-client:bootRun
```

客户端将在端口8080上启动Web服务，提供REST API接口。

## API测试

客户端提供了以下REST API来测试gRPC服务：

1. **一元RPC调用**
   ```
   GET /api/hello/unary?name={name}
   ```

2. **服务端流式RPC调用**
   ```
   GET /api/hello/server-streaming?name={name}
   ```

3. **客户端流式RPC调用**
   ```
   GET /api/hello/client-streaming?names={name1}&names={name2}
   ```

4. **双向流式RPC调用**
   ```
   GET /api/hello/bidirectional-streaming?names={name1}&names={name2}
   ```

## 配置说明

### 服务器配置 (grpc-server/src/main/resources/application.yml)

```yaml
server:
  port: 8082

grpc:
  server:
    port: 50051
    # 线程池配置
    thread-pool:
      size: 10
```

### 客户端配置 (grpc-client/src/main/resources/application.yml)

```yaml
server:
  port: 8080

grpc:
  server:
    host: localhost
    port: 50051
```

## 实现细节

### Protobuf定义

服务接口和消息定义位于 `common/src/main/proto/hello_service.proto` 文件中。

### 服务实现

1. **主服务实现**: `HelloServiceImpl`
2. **扩展服务实现**: `HelloServiceExtendedImpl`

两种实现提供了不同的业务逻辑，可以同时注册到服务器。

### 自动服务注册

服务器配置类 `GrpcServerConfig` 负责自动注入和注册所有实现了 `BindableService` 接口的Bean，无需手动添加服务。

## 注意事项

- 本项目使用明文传输（`usePlaintext()`），在生产环境中应该使用TLS加密通信
- 服务器和客户端都配置了连接保活机制，保持长连接稳定
- 流式RPC使用Kotlin协程的Flow API实现异步数据流处理

## License

This project is licensed under the Apache License 2.0.