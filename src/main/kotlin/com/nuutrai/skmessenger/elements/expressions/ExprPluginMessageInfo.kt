package com.nuutrai.skmessenger.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.EventRestrictedSyntax
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.nuutrai.skmessenger.util.events.PluginMessageReceiveEvent
import com.nuutrai.skmessenger.util.types.PluginMessageInfo
import org.bukkit.event.Event

class ExprPluginMessageInfo : SimpleExpression<PluginMessageInfo>(), EventRestrictedSyntax {

    companion object {
        init {
            Skript.registerExpression(
                ExprPluginMessageInfo::class.java,
                PluginMessageInfo::class.java,
                ExpressionType.EVENT,
                "[plugin]( |-)[message]( |-)info[rmation]"
            )
        }
    }

    override fun init(expressions: Array<out Expression<*>?>, matchedPattern: Int, isDelayed: Kleenean?, parseResult: SkriptParser.ParseResult?): Boolean {
        return true
    }

    override fun supportedEvents(): Array<Class<out Event>> {
        return arrayOf(PluginMessageReceiveEvent::class.java)
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out PluginMessageInfo> {
        return PluginMessageInfo::class.java
    }

    override fun get(event: Event?): Array<PluginMessageInfo>? {
        if (event !is PluginMessageReceiveEvent) return null
        return arrayOf(event.pluginMessageInfo)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "plugin message info"
    }


}