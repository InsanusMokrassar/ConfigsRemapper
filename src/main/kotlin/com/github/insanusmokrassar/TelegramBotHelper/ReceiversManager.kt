package com.github.insanusmokrassar.TelegramBotHelper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.github.insanusmokrassar.IObjectK.utils.plus
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject
import java.io.InputStream
import java.util.logging.Logger

class ReceiversManager(
        vararg configs: TelegramBotHelperConfigModel
) {
    private val commandsMap = mapOf(
            *configs.map {
                it.command to it
            }.toTypedArray()
    )
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
            commandsMap[command]?.apply {
                receiverObject.receive(
                        makeParamsObject(config)
                )
            } ?: throw IllegalArgumentException("Command receiver not found: $command")
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }
}