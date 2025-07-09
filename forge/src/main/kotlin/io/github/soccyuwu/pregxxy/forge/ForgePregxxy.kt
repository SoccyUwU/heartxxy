package io.github.soccyuwu.pregxxy.forge

import dev.architectury.platform.forge.EventBuses
import io.github.soccyuwu.pregxxy.Pregxxy
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Pregxxy.MODID)
class PregxxyForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Pregxxy.MODID, this)
            addListener(ForgePregxxyClient::init)
            addListener(::gatherData)
        }
        Pregxxy.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
