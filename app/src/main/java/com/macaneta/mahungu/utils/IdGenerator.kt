package com.macaneta.mahungu.utils

object IdGenerator {
    fun generateAlphaNumericId(length: Int = 11): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

}