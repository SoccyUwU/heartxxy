package io.github.soccyuwu.pregxxy.forge

import io.github.soccyuwu.pregxxy.PregxxyClient
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgePregxxyClient {
    fun init(event: FMLClientSetupEvent) {
        PregxxyClient.init()
        LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
            ConfigScreenFactory { _, parent -> PregxxyClient.getConfigScreen(parent) }
        }
    }
}
