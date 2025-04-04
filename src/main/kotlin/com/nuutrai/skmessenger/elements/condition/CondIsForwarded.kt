package com.nuutrai.skmessenger.elements.condition

import ch.njol.skript.conditions.base.PropertyCondition
import com.nuutrai.skmessenger.util.types.PluginMessageInfo

class CondIsForwarded : PropertyCondition<PluginMessageInfo>() {

    companion object {
        init {
            register(CondIsForwarded::class.java, PropertyType.BE, "forward[ed]","pluginmessageinfo")
        }
    }

    override fun getPropertyName(): String {
        return "is forwarded"
    }

    override fun check(value: PluginMessageInfo?): Boolean {
        return value?.forward ?: false
    }

}