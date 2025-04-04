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
class ExprIncomingPluginMessageType : SimpleExpression<PluginMessageType.Incoming>() {

    companion object {
        init {
            Skript.registerExpression(
                ExprIncomingPluginMessageType::class.java,
                PluginMessageType.Incoming::class.java,
                ExpressionType.COMBINED,
                "[the] in[coming( |-)][plugin( |-)]message( |-)type %-string%"
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

    override fun getReturnType(): Class<out PluginMessageType.Incoming> {
        return PluginMessageType.Incoming::class.java
    }

    override fun get(event: Event?): Array<PluginMessageType.Incoming?> {
        return arrayOf(try {
            PluginMessageType.Incoming.valueOf(type?.getSingle(event)?:"null")
        } catch (ignored: IllegalArgumentException) {
            null
        })
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "the incoming plugin message type ${type?.getSingle(event) ?: "null"}"
    }

}