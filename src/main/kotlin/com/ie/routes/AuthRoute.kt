package com.ie.routes

import com.ie.service.JwtAuthService
import com.ie.service.UserService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.route
import io.micronaut.ktor.KtorRoutingBuilder
import org.abstractj.kalium.crypto.Hash
import org.abstractj.kalium.encoders.Encoder
import javax.inject.Singleton

@Singleton
class AuthRoute(
        private val jwtAuthService: JwtAuthService,
        private val userService: UserService
) : KtorRoutingBuilder({

    val hash = Hash()

    route("auth") {
        post("login") {
            val credentials = call.receive<LoginCredentialsDto>()

            val hashedPassword = Encoder.HEX.encode(hash.blake2(credentials.password.toByteArray()))
            val user = userService.get(credentials.login).blockingGet()

            if (user?.password == hashedPassword) {
                call.respond(mapOf("token" to jwtAuthService.sign(credentials.login)))
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
})

data class LoginCredentialsDto(
        val login : String,
        val password : String
)