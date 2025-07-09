@file:JvmName("PregxxyAbstractions")

package io.github.soccyuwu.pregxxy

import dev.architectury.injectables.annotations.ExpectPlatform
import io.github.soccyuwu.pregxxy.registry.PregxxyRegistrar

fun initRegistries(vararg registries: PregxxyRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: PregxxyRegistrar<T>) {
    throw AssertionError()
}
