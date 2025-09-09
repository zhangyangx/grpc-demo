plugins {
    kotlin("jvm")
    id("com.google.protobuf")
}

group = "com.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    
    // gRPC核心依赖
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    
    // Protobuf依赖
    implementation("com.google.protobuf:protobuf-java")
    
    // 辅助依赖
    implementation("javax.annotation:javax.annotation-api")
    
    // Kotlin支持
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("io.grpc:grpc-kotlin-stub")
}

// Protobuf插件配置
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.ext.get("protobufVersion")}"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext.get("grpcVersion")}"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.ext.get("grpcKotlinVersion")}:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.plugins {
                create("grpc") {}
                create("grpckt") {}
            }
        }
    }
}

// 源码目录配置
sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
        kotlin {
            srcDirs(
                "src/main/kotlin",
                layout.buildDirectory.file("generated/source/proto/main/grpc").get().asFile,
                layout.buildDirectory.file("generated/source/proto/main/grpckt").get().asFile
            )
        }
    }
}

// 处理重复文件
tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}