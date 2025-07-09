package io.github.soccyuwu.pregxxy.networking.handler

import dev.architectury.networking.NetworkManager.PacketContext
import io.github.soccyuwu.pregxxy.config.PregxxyConfig
import io.github.soccyuwu.pregxxy.networking.msg.*

fun PregxxyMessageS2C.applyOnClient(ctx: PacketContext) = ctx.queue {
    when (this) {
        is MsgSyncConfigS2C -> {
            PregxxyConfig.onSyncConfig(serverConfig)
        }

        // add more client-side message handlers here
    }
}
