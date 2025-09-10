plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

group = "com.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    
    // 依赖common模块（提供protobuf消息定义）
    implementation(project(":common"))
    
    // Spring Boot依赖
    implementation("org.springframework.boot:spring-boot-starter-web")
    
    // gRPC依赖
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-netty-shaded")
    
    // Kotlin协程支持（用于emit等协程函数）
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("io.grpc:grpc-kotlin-stub")
    
    // 测试依赖
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Spring Boot配置
springBoot {
    mainClass.value("com.example.GrpcClientApplicationKt")
}