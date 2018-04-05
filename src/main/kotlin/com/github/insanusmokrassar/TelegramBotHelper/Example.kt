package com.github.insanusmokrassar.TelegramBotHelper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import com.github.insanusmokrassar.IObjectKRealisations.JSONIObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toObject
import java.util.*

private class SimpleReceiverConfigClass {
    val first: String = ""
    val second: String = ""
}

private class SimpleReceiver(config: SimpleReceiverConfigClass): Receiver {
    init {
        println("${config.first} ${config.second}")
    }

    override fun receive(data: IObject<Any>) {
        println(data)
    }
}

private class ExampleConfig {
    val commands: List<IObject<Any>> = emptyList()
}

private val endInputRegex = Regex("\n\n$")

fun main(args: Array<String>) {
    ClassLoader.getSystemResourceAsStream("example_config.json").readIObject().toObject(ExampleConfig::class.java).let {
        ReceiversManager(*it.commands.toTypedArray())
    }.apply {
        val reader = Scanner(System.`in`)
        while (true) {
            var config = ""
            var command = ""
            println("You enter a command")
            while (endInputRegex.find(command) ?. groupValues ?. isEmpty() != false) {
                command += "${reader.nextLine()}\n"
            }
            command = endInputRegex.replaceFirst(command, "")
            println("You enter a config")
            while (endInputRegex.find(config) ?. groupValues ?. isEmpty() != false) {
                config += "${reader.nextLine()}\n"
            }
            config = endInputRegex.replaceFirst(config, "")
            handle(
                    command,
                    if (config.isEmpty()) {
                        SimpleIObject()
                    } else {
                        JSONIObject(config)
                    }
            )
        }
    }
}
