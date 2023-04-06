package com.poisonedyouth

class AccessCounter {
    private var counter: Long = 0

    fun getCurrentCount() = counter

    fun increaseCounter() {
        counter++
    }
}