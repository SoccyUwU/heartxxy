package io.github.soccyuwu.pregxxy.networking

import dev.architectury.networking.NetworkChannel
import io.github.soccyuwu.pregxxy.Pregxxy
import io.github.soccyuwu.pregxxy.networking.msg.PregxxyMessageCompanion

object PregxxyNetworking {
    val CHANNEL: NetworkChannel = NetworkChannel.create(Pregxxy.id("networking_channel"))

    fun init() {
        for (subclass in PregxxyMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
