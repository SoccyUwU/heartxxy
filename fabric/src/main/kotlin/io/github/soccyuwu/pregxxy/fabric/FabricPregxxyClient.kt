package io.github.soccyuwu.pregxxy.fabric

import io.github.soccyuwu.pregxxy.PregxxyClient
import net.fabricmc.api.ClientModInitializer

object FabricPregxxyClient : ClientModInitializer {
    override fun onInitializeClient() {
        PregxxyClient.init()
    }
}
