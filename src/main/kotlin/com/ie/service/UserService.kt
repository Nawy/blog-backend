package com.ie.service

import com.ie.model.User
import io.reactivex.Single
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.Tuple
import javax.inject.Singleton

@Singleton
class UserService(private val client : PgPool) {

    fun save(user : User) : Single<User> {
        println(user.toString())
        return client.preparedQuery("INSERT INTO users (login, password, role) VALUES($1, $2, $3)")
                .rxExecute(Tuple.of(user.login, user.password, user.role))
                .map {
                    user
                }
    }

    fun get(login : String) : Single<User?> {
        return client.preparedQuery("SELECT * FROM users WHERE login = $1")
                .rxExecute(Tuple.of(login))
                .map {
                    it.map { mapUser(it) }.firstOrNull()
                }
    }

    fun delete(login: String) : Single<User?> {
        return client.preparedQuery("DELETE FROM users WHERE login = $1 RETURNING *")
                .rxExecute(Tuple.of(login))
                .map {
                    it.map { mapUser(it) }.firstOrNull()
                }
    }

    fun getAll() : Single<List<User?>> {
        return client.query("SELECT * FROM users")
                .rxExecute()
                .map {
                    it.map { mapUser(it) }
                }
    }
}

fun mapUser(row : Row) : User? {
    return User(
            row.getString("login"),
            row.getString("password"),
            row.getString("role")
    )
}