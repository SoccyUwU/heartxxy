package io.github.soccyuwu.pregxxy.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import io.github.soccyuwu.pregxxy.PregxxyClient

object FabricPregxxyModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(PregxxyClient::getConfigScreen)
}
