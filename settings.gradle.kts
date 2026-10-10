    rootProject.name = "VersatiaExample-DI-SharedBeans"

    pluginManagement {
        repositories {
            gradlePluginPortal()
            mavenCentral()
        }
    }

include("provider")
include("consumer")
