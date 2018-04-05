package com.github.insanusmokrassar.TelegramBotHelper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectKRealisations.toIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject

private fun IObject<Any>.toCommandReceiver(): Receiver<*> {
    return toObject(CommandHandlerConfig::class.java).run {
        (paramsObjectInstance() ?: params) ?.let {
            extract<Receiver<*>>(classPath, it)
        } ?:let {
            extract<Receiver<*>>(classPath)
        }
    }
}

data class TelegramBotHelperConfigModel (
        val receiver: Any,
        val command: String,
        val defaultParams: IObject<Any>?,
        val mapRules: IObject<Any>?,
        val gsonClassModel: String?
) {
    val receiverObject: Receiver<*>
        get() {
            return when (receiver) {
                is Receiver<*> -> receiver
                is String -> {
                    try {
                        receiver.toIObject().toCommandReceiver()
                    } catch (e: Exception) {
                        extract<Receiver<*>>(receiver)
                    }
                }
                is IObject<*> -> {
                    (receiver as IObject<Any>).toCommandReceiver()
                }
                else -> throw IllegalStateException("Can't create receiver from $receiver")
            }
        }
}
