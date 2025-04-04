package com.nuutrai.skmessenger.elements.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.Literal
import ch.njol.skript.lang.SkriptEvent
import ch.njol.skript.lang.SkriptParser
import com.nuutrai.skmessenger.util.events.PluginMessageReceiveEvent
import com.nuutrai.skmessenger.util.types.PluginMessageType
import org.bukkit.event.Event

@Suppress("UNCHECKED_CAST")
class EvtPluginMessageReceived : SkriptEvent() {

    companion object {
        init {
            Skript.registerEvent("Plugin Message Receive Event", EvtPluginMessageReceived::class.java, PluginMessageReceiveEvent::class.java,
                "[plugin] message receive[d] [(with|of) %-incomingpluginmessagetype%]",
                "forward[ed] [plugin] message receive[d] [through [channel] %-string%]"
            )
        }
    }

    private var type: Literal<PluginMessageType.Incoming>? = null
    private var channel: Literal<String>? = null

    override fun init(args: Array<out Literal<*>?>, matchedPattern: Int, parseResult: SkriptParser.ParseResult?): Boolean {
        when (matchedPattern) {
            0 -> type = args[0] as Literal<PluginMessageType.Incoming>?
            1 -> channel = args[0] as Literal<String>?
            else -> return false
        }
        return true
    }

    override fun check(event: Event?): Boolean {
        if (event !is PluginMessageReceiveEvent) return false
        if (channel != null)
            return event.forward
        if (type != null)
            return event.type == type!!.getSingle(event)
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return buildString {
            if (channel != null) append("forwarded ")
            append("plugin message received")
            if (channel != null)
                append(" through channel ${channel!!.toString(event, debug)}")
            else if (type != null) append(" with ${type!!.toString(event, debug)}")
        }
    }

}