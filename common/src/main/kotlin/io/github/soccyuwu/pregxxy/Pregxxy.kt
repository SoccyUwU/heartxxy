package io.github.soccyuwu.pregxxy

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import io.github.soccyuwu.pregxxy.config.PregxxyConfig
import io.github.soccyuwu.pregxxy.networking.PregxxyNetworking
import io.github.soccyuwu.pregxxy.registry.PregxxyActions

object Pregxxy {
    const val MODID = "pregxxy"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        PregxxyConfig.init()
        initRegistries(
            PregxxyActions,
        )
        PregxxyNetworking.init()
    }
}
