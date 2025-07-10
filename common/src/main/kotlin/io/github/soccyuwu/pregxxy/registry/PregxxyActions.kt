package io.github.soccyuwu.pregxxy.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import io.github.soccyuwu.pregxxy.casting.actions.spells.OpBreed
import io.github.soccyuwu.pregxxy.casting.actions.spells.great.OpGreaterBreed

object PregxxyActions : PregxxyRegistrar<ActionRegistryEntry>(
    HexRegistries.ACTION,
    { HexActions.REGISTRY },
) {
    val BREED = make("pregxxy", HexDir.SOUTH_EAST, "wdwewawqwqwwaqwq", OpBreed)
    val GBREED = make("greater_pregxxy", HexDir.SOUTH_EAST, "wwdwwewwawwqwwqwwwawqwqqadadaaead", OpGreaterBreed)

    private fun make(name: String, startDir: HexDir, signature: String, action: Action) =
        make(name, startDir, signature) { action }

    private fun make(name: String, startDir: HexDir, signature: String, getAction: () -> Action) = register(name) {
        ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), getAction())
    }
}
