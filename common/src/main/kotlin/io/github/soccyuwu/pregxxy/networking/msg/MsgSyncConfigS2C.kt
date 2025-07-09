package io.github.soccyuwu.pregxxy.networking.msg

import io.github.soccyuwu.pregxxy.config.PregxxyConfig
import net.minecraft.network.FriendlyByteBuf

data class MsgSyncConfigS2C(val serverConfig: PregxxyConfig.ServerConfig) : PregxxyMessageS2C {
    companion object : PregxxyMessageCompanion<MsgSyncConfigS2C> {
        override val type = MsgSyncConfigS2C::class.java

        override fun decode(buf: FriendlyByteBuf) = MsgSyncConfigS2C(
            serverConfig = PregxxyConfig.ServerConfig.decode(buf),
        )

        override fun MsgSyncConfigS2C.encode(buf: FriendlyByteBuf) {
            serverConfig.encode(buf)
        }
    }
}
