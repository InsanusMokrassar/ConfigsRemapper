package com.github.insanusmokrassar.ConfigsRemapper

import com.github.insanusmokrassar.IObjectK.extensions.remap
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject

private fun IObject<Any>.toCommandReceiver(): Receiver {
    return toObject(CommandHandlerConfig::class.java).run {
        (paramsObjectInstance<Any>() ?: params) ?.let {
            extract<Receiver>(classPath, it)
        } ?:let {
            extract<Receiver>(classPath)
        }
    }
}

class TelegramBotHelperConfigModel {
    val receiver: IObject<Any> = SimpleIObject()
    val command: String = ""
    val defaultParams: IObject<Any>? = null
    val mapRules: IObject<Any>? = null

    private var receiverCachedObject: Receiver? = null
    val receiverObject: Receiver
        get() = receiverCachedObject ?: receiver.toCommandReceiver().also {
            receiverCachedObject = it
        }

    fun makeParamsObject(config: IObject<Any>): IObject<Any> {
        return mapRules ?.let {
            mapRules ->
            (defaultParams ?. toString() ?. byteInputStream() ?. readIObject() /* safe copy */ ?: SimpleIObject()).also {
                mapRules.remap(
                        config,
                        it
                )
            }
        } ?: config
    }
}
