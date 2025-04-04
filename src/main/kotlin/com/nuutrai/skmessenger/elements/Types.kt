package com.nuutrai.skmessenger.elements

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.expressions.base.EventValueExpression
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.registrations.Classes
import com.nuutrai.skmessenger.util.types.PluginMessageInfo
import com.nuutrai.skmessenger.util.types.PluginMessageType

class Types {

    companion object {
        init {
            Classes.registerClass(ClassInfo(PluginMessageType.Outgoing::class.java, "outgoingpluginmessagetype")
                .user("[out[going]] [plugin] message type")
                .name("Outgoing Plugin Message Type")
                .description("Represents an outgoing Plugin Message type")
                .since("1.0.0")
                .parser(object : Parser<PluginMessageType.Outgoing>() {
                    override fun parse(id: String?, context: ParseContext?): PluginMessageType.Outgoing? {
                        return null
                    }

                    override fun toString(pluginMessageType: PluginMessageType.Outgoing?, flags: Int): String {
                        return pluginMessageType?.name ?: "null outgoing plugin message type"
                    }

                    override fun toVariableNameString(pluginMessageType: PluginMessageType.Outgoing?): String {
                        return "${pluginMessageType?.name}PLMTO"
                    }

                })
            )

            Classes.registerClass(ClassInfo(PluginMessageType.Incoming::class.java, "incomingpluginmessagetype")
                .defaultExpression(EventValueExpression(PluginMessageType.Incoming::class.java))
                .user("in[coming] [plugin] message type")
                .name("Incoming Plugin Message Type")
                .description("Represents an incoming Plugin Message type")
                .since("1.0.0")
                .parser(object : Parser<PluginMessageType.Incoming>() {
                    override fun parse(id: String?, context: ParseContext?): PluginMessageType.Incoming? {
                        return null
                    }

                    override fun toString(pluginMessageType: PluginMessageType.Incoming?, flags: Int): String {
                        return pluginMessageType?.name ?: "null incoming plugin message type"
                    }

                    override fun toVariableNameString(pluginMessageType: PluginMessageType.Incoming?): String {
                        return "${pluginMessageType?.name}PLMTI"
                    }

                })
            )

            Classes.registerClass(ClassInfo(PluginMessageInfo::class.java, "pluginmessageinfo")
                .defaultExpression(EventValueExpression(PluginMessageInfo::class.java))
                .user("[plugin]( |-)[message]( |-)info[rmation]")
                .name("Plugin Message Information")
                .description("Represents the information from a plugin message")
                .since("1.0.0")
                .parser(object : Parser<PluginMessageInfo>() {
                    override fun parse(id: String?, context: ParseContext?): PluginMessageInfo? {
                        return null
                    }

                    override fun toString(pluginMessageInfo: PluginMessageInfo?, flags: Int): String {
                        return "plugin message info"
                    }

                    override fun toVariableNameString(pluginMessageInfo: PluginMessageInfo?): String {
                        if (pluginMessageInfo == null) return "nullPluginMessageInfo"
                        return buildString {
                            append("PMI-")
                            if (pluginMessageInfo.forward) append("F-")
                            append(pluginMessageInfo.type.name)
                        }
                    }

                })
            )

        }
    }

}