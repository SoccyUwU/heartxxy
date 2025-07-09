@file:JvmName("PregxxyAbstractionsImpl")

package io.github.soccyuwu.pregxxy.fabric

import io.github.soccyuwu.pregxxy.registry.PregxxyRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: PregxxyRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
