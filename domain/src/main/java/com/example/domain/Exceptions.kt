package com.example.domain

class Exceptions {

    class Incorrect: Exception()

    class Unauthorized: Exception()

    class HttpException(
        override val message: String,
        val code: Int
    ): Exception()

}