package com.ie.routes

import com.ie.model.User
import com.ie.service.UserService
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.queryString
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.micronaut.ktor.KtorRoutingBuilder
import org.abstractj.kalium.crypto.Hash
import org.abstractj.kalium.encoders.Encoder.HEX
import javax.inject.Singleton

@Singleton
class UserRoute(private val userService: UserService) : KtorRoutingBuilder({
    val hash : Hash = Hash()

    route("user") {
        authenticate {
            post {
                val userDto = call.receive<CreateUserDto>()

                // brew install libsoudium
                val passwordHashed = HEX.encode(hash.blake2(userDto.password.toByteArray()))

                call.respond(
                        userService.save(
                                User(userDto.login, passwordHashed, userDto.role)
                        ).map { it.toDTO() }.blockingGet()
                )
            }

            delete("/{login}") {
                val login : String = call.parameters["login"]!!
                val user : UserDto? = userService.delete(login).map { user : User? -> user?.toDTO() }.blockingGet()
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    call.respond(HttpStatusCode.Accepted, user)
                }
            }
        }

        get("/{login}") {
            val login : String = call.parameters["login"]!!
            val user : User? = userService.get(login).blockingGet()

            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(user.toDTO())
            }

        }

        get {
            call.respond(
                    userService.getAll()
                            .map { it.map { it?.toDTO() }}
                            .blockingGet()
            )
        }
    }
})

data class CreateUserDto(val login : String, var password : String, val role : String)
data class UserDto(val login : String, val role : String?)

fun User.toDTO() : UserDto {
    return UserDto(this.login, this.role)
}
