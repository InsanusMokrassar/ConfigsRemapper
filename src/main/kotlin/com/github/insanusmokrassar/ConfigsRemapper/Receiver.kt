package com.github.insanusmokrassar.ConfigsRemapper

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

interface Receiver: (IObject<Any>) -> Unit {
    fun receive(data: IObject<Any>)
    override fun invoke(p1: IObject<Any>) = receive(p1)
}