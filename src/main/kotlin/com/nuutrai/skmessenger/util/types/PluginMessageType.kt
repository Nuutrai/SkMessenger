package com.nuutrai.skmessenger.util.types

class PluginMessageType {



    enum class Outgoing(vararg val types: Class<*>) {
        Connect(String::class.java),
        ConnectOther(String::class.java, String::class.java),
        IP,
        IPOther(String::class.java),
        PlayerCount(String::class.java),
        PlayerList(String::class.java),
        GetServers,
        Message(String::class.java, String::class.java),
        GetServer,
        GetPlayerServer(String::class.java),
        UUID,
        UUIDOther(String::class.java),
        ServerIp(String::class.java),
        KickPlayer(String::class.java, String::class.java),
        Forward,
        ForwardToPlayer
    }

    enum class Incoming(vararg val types: Class<*>) {
        Connect,
        ConnectOther,
        IP(String::class.java, Int::class.java),
        IPOther(String::class.java, String::class.java, Int::class.java),
        PlayerCount(String::class.java, Int::class.java),
        PlayerList(String::class.java, Array<String>::class.java),
        GetServers(Array<String>::class.java),
        Message,
        GetServer(String::class.java),
        GetPlayerServer(String::class.java, String::class.java),
        UUID(String::class.java),
        UUIDOther(String::class.java, String::class.java),
        ServerIp(String::class.java, Int::class.java),
        KickPlayer,
        Forward,
        ForwardToPlayer
    }

}