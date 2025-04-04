package com.nuutrai.skmessenger.util.events

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import com.nuutrai.skmessenger.util.types.PluginMessageInfo
import com.nuutrai.skmessenger.util.types.PluginMessageType
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.event.player.PlayerEvent
import java.io.ByteArrayInputStream
import java.io.DataInputStream

@Suppress("RecursivePropertyAccessor")
class PluginMessageReceiveEvent(player: Player, data: ByteArray) : PlayerEvent(player) {

    val subchannel: String
    val type: PluginMessageType.Incoming
    var forward = false
        private set
    var dataList = listOf<Any>()
        private set
    val pluginMessageInfo: PluginMessageInfo

    init {
        println("Plugin Message Event Called") // Debug
        val input = ByteStreams.newDataInput(data)
        subchannel = input.readUTF()
        println("Subchannel: $subchannel") // Debug
        type = PluginMessageType.Incoming.valueOf(subchannel)
        println("Subchannel: ${type.name}") // Debug
        pluginMessageInfo =
            if (type == PluginMessageType.Incoming.Forward || type == PluginMessageType.Incoming.ForwardToPlayer)
                handleForward(input)
            else
                handleNonForward(input)
        println("Created plugin message info") // Debug
    }

    private fun handleForward(input: ByteArrayDataInput): PluginMessageInfo {
        forward = true
        val len = input.readShort()
        val msgBytes = ByteArray(len.toInt())
        input.readFully(msgBytes)
        val msgIn = DataInputStream(ByteArrayInputStream(msgBytes))
        val msgLength = msgIn.readShort()
        val list = arrayListOf<Any>()
        repeat(msgLength.toInt()) {
            val type = msgIn.readByte()
            when (type) {
                0.toByte() -> list.add(msgIn.readUTF())
                1.toByte() -> list.add(msgIn.readDouble())
                2.toByte() -> list.add(msgIn.readBoolean())
            }
        }
        dataList = list
        println("Data from forwarded:") // Debug
        for (thing in list) { // Debug
            println(thing.toString()) // Debug
        } // Debug

        return PluginMessageInfo(type, list, forward)

    }

    private fun handleNonForward(input: ByteArrayDataInput): PluginMessageInfo {
        val list = arrayListOf<Any>()
        for (dataType in type.types) {
            if (Array<String>::class.java.isAssignableFrom(dataType)) {
                input.readUTF().removePrefix("[").removeSuffix("]").split(", ").forEach { e ->
                    list.add(e)
                }
            } else if (Int::class.java.isAssignableFrom(dataType)) {
                list.add(input.readInt())
            } else {
                list.add(input.readUTF())
            }
        }
        dataList = list

        println("Data from Non forwarded:") // Debug
        for (thing in list) { // Debug
            println(thing.toString()) // Debug
        } // Debug

        return PluginMessageInfo(type, list, forward)

    }

    override fun getHandlers(): HandlerList = getHandlerList()

    companion object {
        private val handlers: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }

    }

}