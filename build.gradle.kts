plugins {
    // 共享插件定义
    id("org.springframework.boot") version "3.2.9" apply false
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "2.0.21" apply false
    kotlin("plugin.spring") version "2.0.21" apply false
    id("com.google.protobuf") version "0.9.4" apply false
}

group = "com.example"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

// 版本定义
val grpcVersion by extra("1.64.0")
val protobufVersion by extra("3.25.3")
val kotlinCoroutinesVersion by extra("1.8.1")
val grpcKotlinVersion by extra("1.4.1")
val javaxAnnotationVersion by extra("1.3.2")

subprojects {
    // 应用依赖管理插件
    apply(plugin = "io.spring.dependency-management")
    
    // 统一JVM版本
    tasks.withType<JavaCompile> {
        options.release.set(17)
    }
    
    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
        // 集中管理依赖版本
        dependencies {
            dependency("io.grpc:grpc-protobuf:$grpcVersion")
            dependency("io.grpc:grpc-stub:$grpcVersion")
            dependency("io.grpc:grpc-netty-shaded:$grpcVersion")
            dependency("com.google.protobuf:protobuf-java:$protobufVersion")
            dependency("com.google.protobuf:protoc:$protobufVersion")
            dependency("io.grpc:protoc-gen-grpc-java:$grpcVersion")
            dependency("io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
            dependency("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
            dependency("javax.annotation:javax.annotation-api:$javaxAnnotationVersion")
        }
    }
}