package com.ie
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.KtorApplication
import io.micronaut.ktor.runApplication
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class KtorApp : KtorApplication<NettyApplicationEngine.Configuration>({

    applicationEngineEnvironment {
        log = LoggerFactory.getLogger(Application.javaClass)
    }

    applicationEngine {
        workerGroupSize = 10
    }

})

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        runApplication(args)
    }
}

