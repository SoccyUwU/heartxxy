package io.github.soccyuwu.pregxxy.casting.actions.spells

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import io.github.soccyuwu.pregxxy.casting.mishaps.MishapCantNurture
import net.minecraft.world.entity.AgeableMob
import kotlin.math.pow

object OpNurture : SpellAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val target = args.getEntity(0, argc)
        val ageMod = args.getDouble(1, argc)
        env.assertEntityInRange(target)
        if(target !is AgeableMob){
            throw MishapCantNurture(target)
        }

        return SpellAction.Result(
            Spell(target, ageMod),
            (((ageMod / 200).pow(2) + ageMod/100 + 2) * MediaConstants.DUST_UNIT).toLong(),
            listOf(ParticleSpray.cloud(target.position().add(0.0, target.eyeHeight / 2.0, 0.0), 1.0))
        )
    }

    private data class Spell(val target: AgeableMob, val ageMod: Double) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            target.age += ageMod.toInt()
        }
    }
}
