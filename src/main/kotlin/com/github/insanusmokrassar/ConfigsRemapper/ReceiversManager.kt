package com.github.insanusmokrassar.ConfigsRemapper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject
import kotlinx.coroutines.experimental.async
import java.util.logging.Logger

class ReceiversManager(
        vararg configs: TelegramBotHelperConfigModel
) {
    private val commandsMap = configs.let {
        val map = HashMap<String, MutableSet<TelegramBotHelperConfigModel>>()
        it.forEach {
            currentConfigModel ->
            currentConfigModel.commands.forEach {
                command ->
                (map[command] ?:let {
                    HashSet<TelegramBotHelperConfigModel>().apply {
                        map[command] = this
                    }
                }).add(currentConfigModel)
            }
        }
        map
    }
    constructor(vararg configs: IObject<Any>) : this(
            *configs.map { it.toObject(TelegramBotHelperConfigModel::class.java) }.toTypedArray()
    )
    constructor(vararg configs: String) : this(
            *configs.map { it.byteInputStream().readIObject() }.toTypedArray()
    )

    fun handle(
            command: String,
            config: IObject<Any>,
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
                    async {
                        configModel.receiverObject(
                                configModel.makeParamsObject(config)
                        )
                    }
                }
            } ?: throw IllegalArgumentException("Command receiver not found: $command")
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }
}