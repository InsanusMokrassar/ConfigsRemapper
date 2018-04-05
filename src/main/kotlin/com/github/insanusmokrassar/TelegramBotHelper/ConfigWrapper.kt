package com.github.insanusmokrassar.TelegramBotHelper

import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject

open class ConfigWrapper {
    val params: String? = null
    val paramsObjectClass: String? = null

    fun <T> paramsObjectInstance(): T?{
        paramsObjectClass ?: return null
        val clazz = Class.forName(paramsObjectClass)
        return try {
            params ?. byteInputStream() ?. readIObject() ?. toObject(clazz) as T
        } catch (e: ClassCastException) {
            null
        }
    }
}
