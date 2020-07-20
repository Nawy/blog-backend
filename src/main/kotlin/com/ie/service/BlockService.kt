package com.ie.service

import com.ie.model.Block
import com.ie.model.User
import javax.inject.Singleton

@Singleton
class BlockService(val userService : UserService) {

    fun get(id : Long) : Block {
        return Block(id, "Title1", "Value", User("11", "11", "12"))
}

    fun save(block : Block) : Block {
        return block
    }

}