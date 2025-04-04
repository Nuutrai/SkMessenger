package com.nuutrai.skmessenger.util.listeners

import com.nuutrai.skmessenger.util.events.PluginMessageReceiveEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

class PluginMessageListener : PluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
        println("Plugin Message Received") // Debug
        if (channel != "BungeeCord") return
        println("Plugin Message Received through channel \"BungeeCord\"") // Debug
        val pluginMessageReceiveEvent = PluginMessageReceiveEvent(player, message ?: return)
        Bukkit.getPluginManager().callEvent(pluginMessageReceiveEvent)
    }

}