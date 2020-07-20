package com.ie

import com.ie.service.JwtAuthService
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.jwt.jwt
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton

@Singleton
class JwtAuthFeature(private val jwtAuthService: JwtAuthService) : KtorApplicationBuilder({

    install(Authentication) {
        jwt {
            verifier(jwtAuthService.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("name").asString())
            }
        }
    }
})
