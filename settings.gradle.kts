plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "grpc-demo"

// 定义子模块
include("common")
include("grpc-server")
include("grpc-client")