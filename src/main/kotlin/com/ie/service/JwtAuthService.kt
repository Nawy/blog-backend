package com.ie.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import javax.inject.Singleton

@Singleton
class JwtAuthService {
    private val secrect : String = "my-super-secret-for-jwt"
    private val algorithm = Algorithm.HMAC256(secrect)
    val verifier = JWT.require(algorithm).build()

    fun sign(name: String): String = JWT.create().withClaim("name", name).sign(algorithm)
}