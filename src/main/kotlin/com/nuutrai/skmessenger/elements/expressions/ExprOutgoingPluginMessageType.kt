package com.nuutrai.skmessenger.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.nuutrai.skmessenger.util.types.PluginMessageType
import org.bukkit.event.Event

@Suppress("UNCHECKED_CAST")
class ExprOutgoingPluginMessageType : SimpleExpression<PluginMessageType.Outgoing>() {

    companion object {
        init {
            Skript.registerExpression(
                ExprOutgoingPluginMessageType::class.java,
                PluginMessageType.Outgoing::class.java,
                ExpressionType.COMBINED,
                "[the] out[going( |-)][plugin( |-)]message( |-)type %-string%"
            )
        }
    }

    private var type: Expression<String>? = null

    override fun init(expressions: Array<out Expression<*>?>, matchedPattern: Int, isDelayed: Kleenean?, parseResult: SkriptParser.ParseResult?): Boolean {
        type = expressions[0] as Expression<String>
        return true
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out PluginMessageType.Outgoing> {
        return PluginMessageType.Outgoing::class.java
    }

    override fun get(event: Event?): Array<PluginMessageType.Outgoing?> {
        println("Getting did gotten")
        return arrayOf(try {
            PluginMessageType.Outgoing.valueOf(type?.getSingle(event) ?: "null").also { println("Printy print") }
        } catch (ignored: IllegalArgumentException) {
            println("That's some bullshit")
            null
        })
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "the outgoing plugin message type ${type?.getSingle(event) ?: "null"}"
    }

}