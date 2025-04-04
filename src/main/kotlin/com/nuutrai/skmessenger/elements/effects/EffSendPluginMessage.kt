package com.nuutrai.skmessenger.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.expressions.base.EventValueExpression
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import com.google.common.io.ByteStreams
import com.nuutrai.skmessenger.SkMessenger
import com.nuutrai.skmessenger.util.types.PluginMessageType
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream


@Suppress("UNCHECKED_CAST")
class EffSendPluginMessage : Effect() {

    companion object {
        init {
            Skript.registerEffect(EffSendPluginMessage::class.java,
                "make [a] plugin message (with|of) %-outgoingpluginmessagetype% [with arg[ument][s] %-objects%] [to %-string%] [from %-player%]",
                "make [a] forward[ed] plugin message (through|in) [the] channel %-string% [with arg[ument][s] %-objects%] [to %-string%] [from %-player%]"
                )
        }
    }

    private var type: Expression<PluginMessageType.Outgoing>? = null
    private var channel: Expression<String>? = null
    private var arguments: Expression<*>? = null
    private var recipient: Expression<String>? = null
    private var sender: Expression<Player>? = null
    private var neededRandomUser = false
    private var forward = false


    override fun init(exprs: Array<out Expression<*>?>, matchedPattern: Int, isDelayed: Kleenean?, parser: SkriptParser.ParseResult?): Boolean {
        if (matchedPattern == 1) {
            forward = true
            channel = exprs[0] as Expression<String>
        } else
            type = exprs[0] as Expression<PluginMessageType.Outgoing>
        arguments = exprs[1]
        recipient = exprs[2] as Expression<String>?
        sender = (exprs[3] ?: EventValueExpression(Player::class.java)) as Expression<Player>
        return true
    }

    override fun execute(event: Event?) {
        println("Message init")
        val type = type?.getSingle(event) ?: if (forward) channel?.getSingle(event) ?: return else return
        val arguments = arguments?.getArray(event)
        val recipient = recipient?.getSingle(event) ?: "ALL"
        val sender = (sender?.getSingle(event) ?: getRandomPlayer() ?: return)

        println("Sender is ${sender.name}") // Debug

        if (!forward) // Ensure User put in a valid type (just in case)
            try {
                PluginMessageType.Outgoing.valueOf(type.toString())
            } catch (ignored: IllegalArgumentException) {
                return
            }

        val out = ByteStreams.newDataOutput()

        out.writeUTF((if (forward) "Forward" else type).toString())
        out.writeUTF(recipient)
        if (forward) {
            println("Forwarded Message") // Debug
            out.writeUTF(type.toString())
        }

        if (!arguments.isNullOrEmpty()) {
            println("Has arguments") // Debug
            if (forward) {
                println("Is forwarded") // Debug
                val msgbytes = ByteArrayOutputStream()
                val msgout = DataOutputStream(msgbytes)
                msgout.writeShort(arguments.size)
                for (arg in arguments) {
                    when (arg) {
                        is String -> {
                            msgout.writeByte(0)
                            msgout.writeUTF(arg)
                        }
                        is Number -> {
                            msgout.writeByte(1)
                            msgout.writeDouble(arg.toDouble())
                        }
                        is Boolean -> {
                            msgout.writeByte(2)
                            msgout.writeBoolean(arg)
                        }
                        else -> {
                            msgout.write(0)
                            msgout.writeUTF(arg.toString())
                        }
                    }
                }
                out.writeShort(msgbytes.toByteArray().size)
                out.write(msgbytes.toByteArray())

            } else {
                for (arg in arguments) {
                    when (arg) {
                        is String -> out.writeUTF(arg)
                        else -> out.writeUTF(arg.toString())
                    }
                }
            }
        }
        sender.sendPluginMessage(SkMessenger.Instance, "BungeeCord", out.toByteArray())
        println("Sent plugin message") // Debug

    }

    private fun getRandomPlayer(): Player? {
        try {
            neededRandomUser = true
            println("Needed a random user") // Debug
            return Bukkit.getOnlinePlayers().random()
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return buildString {
            append("send a ")
            if (forward) append("forwarded")
            append(" plugin message ")
            append(if (forward) "through the channel " else "of type ")
            append(if (forward) channel?.toString(event, debug) ?: "null" else type?.toString(event, debug) ?: null)
            append(" with ")
            append(if (arguments == null) "no arguments" else "arguments ${arguments!!.toString(event, debug)}")
            append(" to ")
            append(if (recipient == null) "ALL" else recipient!!.toString(event, debug))
            append(" from ")
            append(if (neededRandomUser) "any player" else sender?.toString(event, debug) ?: "null")
        }
    }

}