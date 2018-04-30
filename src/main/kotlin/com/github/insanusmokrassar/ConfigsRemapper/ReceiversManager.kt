package com.github.insanusmokrassar.ConfigsRemapper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.github.insanusmokrassar.IObjectK.utils.plus
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject
import kotlinx.coroutines.experimental.async
import java.util.logging.Logger

class ReceiversManager(
        private val handlingMixinObject: IObject<Any> = SimpleIObject(),
        vararg configs: ConfigModel
) {
    private val logger = Logger.getLogger(ReceiversManager::class.java.simpleName)
    private val commandsMap = configs.let {
        val map = HashMap<String, MutableSet<ConfigModel>>()
        it.forEach {
            currentConfigModel ->
            currentConfigModel.commands.forEach {
                command ->
                (map[command] ?:let {
                    HashSet<ConfigModel>().apply {
                        map[command] = this
                    }
                }).add(currentConfigModel)
            }
        }
        map
    }
    constructor(
            handlingMixinObject: IObject<Any> = SimpleIObject(),
            vararg configs: IObject<Any>
    ) : this(
            handlingMixinObject,
            *configs.map { it.toObject(ConfigModel::class.java) }.toTypedArray()
    )
    constructor(
            handlingMixinObject: IObject<Any> = SimpleIObject(),
            vararg configs: String
    ) : this(
            handlingMixinObject,
            *configs.map { it.byteInputStream().readIObject() }.toTypedArray()
    )

    fun handle(
            command: String,
            config: IObject<Any>,
            async: Boolean = true,
            exceptionHandler: (Exception) -> Unit = {
                Logger.getGlobal().throwing(
                        this::class.java.simpleName,
                        "handle",
                        it
                )
            }
    ) {
        try {
            commandsMap[command] ?.apply {
                forEach {
                    configModel ->
                    if (async) {
                        async {
                            configModel.receiverObject(
                                    configModel.makeParamsObject(handlingMixinObject + config)
                            )
                        }
                    } else {
                        try {
                            configModel.receiverObject(
                                    configModel.makeParamsObject(handlingMixinObject + config)
                            )
                        } catch (e: Exception) {
                            logger.throwing(
                                    ReceiversManager::class.java.simpleName,
                                    "Handling command by receiver",
                                    e
                            )
                        }
                    }
                }
            } ?: throw IllegalArgumentException("Command receiver not found: $command")
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }
}