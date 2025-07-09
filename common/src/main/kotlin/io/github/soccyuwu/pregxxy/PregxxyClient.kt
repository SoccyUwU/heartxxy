package io.github.soccyuwu.pregxxy

import io.github.soccyuwu.pregxxy.config.PregxxyConfig
import io.github.soccyuwu.pregxxy.config.PregxxyConfig.GlobalConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screens.Screen

object PregxxyClient {
    fun init() {
        PregxxyConfig.initClient()
    }

    fun getConfigScreen(parent: Screen): Screen {
        return AutoConfig.getConfigScreen(GlobalConfig::class.java, parent).get()
    }
}
