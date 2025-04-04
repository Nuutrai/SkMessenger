package com.nuutrai.skmessenger.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import com.nuutrai.skmessenger.util.types.PluginMessageInfo
import org.bukkit.event.Event

class ExprPluginMessageInfoData : SimplePropertyExpression<PluginMessageInfo, Array<Any>>() {

    companion object {
        init {
            register(
                ExprPluginMessageInfoData::class.java,
                Array<Any>::class.java,
                "data",
                "pluginmessageinfo"
            )
        }
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out Array<Any>> {
        return Array<Any>::class.java
    }

    override fun getPropertyName(): String {
        return "plugin message info data"
    }

    override fun convert(from: PluginMessageInfo?): Array<Any>? {
        if (from == null) return null
        return from.data.toTypedArray()
    }

    override fun get(event: Event?, source: Array<out PluginMessageInfo>?): Array<Array<Any>> {
        if (source == null) return emptyArray()
        return arrayOf(source.first().data.toTypedArray())
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "plugin message info data"
    }

}