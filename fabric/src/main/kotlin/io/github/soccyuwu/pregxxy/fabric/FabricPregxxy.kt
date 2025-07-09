package io.github.soccyuwu.pregxxy.fabric

import io.github.soccyuwu.pregxxy.Pregxxy
import net.fabricmc.api.ModInitializer

object FabricPregxxy : ModInitializer {
    override fun onInitialize() {
        Pregxxy.init()
    }
}
