package com.github.insanusmokrassar.TelegramBotHelper

interface Receiver<in T>: (T) -> Unit {
    fun receive(data: T)
}