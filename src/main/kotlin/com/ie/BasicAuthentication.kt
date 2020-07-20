package com.ie

import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton

//@Singleton
//class BasicAuthentication : KtorApplicationBuilder({
//
//    install(Authentication) {
//        basic {
//            realm = "myrealm"
//            validate { if (it.name == "user" && it.password == "password") UserIdPrincipal("user") else null }
//        }
//    }
//})
