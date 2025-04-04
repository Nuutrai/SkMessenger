package com.nuutrai.skmessenger

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import com.nuutrai.skmessenger.util.listeners.PluginMessageListener
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException

class SkMessenger : JavaPlugin(), Listener {

    companion object {
        lateinit var addon: SkriptAddon
        lateinit var Instance: SkMessenger
    }

    override fun onEnable() {
        // Plugin startup logic

        if (!Skript.classExists("kotlin.jvm.internal.Intrinsics")) {
            logger.severe("Kotlin is not already supported on the server!")
            logger.severe("Please download SkMessenger with Kotlin prepackaged!")
            server.pluginManager.disablePlugin(this)
            return
        }
        Instance = this
        addon = Skript.registerAddon(this)
        if (!loadClasses()) {
            logger.severe("Couldn't load Skript classes.")
            server.pluginManager.disablePlugin(this)
            return
        }
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        println("Outgoing channel registered") // Debug
        server.messenger.registerIncomingPluginChannel(this, "BungeeCord", PluginMessageListener())
        println("Incoming channel registered") // Debug
        logger.info("SkMessenger received a message:")
        logger.info("SkMessenger has been enabled!")
    }

    private fun loadClasses(): Boolean {
        try {
            addon.loadClasses("com.nuutrai.skmessenger", "elements")
        } catch (e: IOException) {
            logger.severe(e.toString())
            return false
        }
        return true
    }


    override fun onDisable() {
        // Plugin shutdown logic
    }
}
