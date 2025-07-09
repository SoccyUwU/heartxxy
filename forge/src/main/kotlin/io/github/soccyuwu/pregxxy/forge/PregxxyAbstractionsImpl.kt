@file:JvmName("PregxxyAbstractionsImpl")

package io.github.soccyuwu.pregxxy.forge

import io.github.soccyuwu.pregxxy.registry.PregxxyRegistrar
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: PregxxyRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}
