package com.github.insanusmokrassar.TelegramBotHelper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject

open class ConfigWrapper {
    val params: IObject<Any>? = null
    val paramsObjectClass: String? = null

    fun <T> paramsObjectInstance(): T? {
        paramsObjectClass ?: return null
        val clazz = Class.forName(paramsObjectClass)
        return try {
            params ?. toObject(clazz) as T
        } catch (e: ClassCastException) {
            null
        }
    }
}
