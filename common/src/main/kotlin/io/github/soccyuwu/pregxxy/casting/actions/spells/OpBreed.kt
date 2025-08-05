package io.github.soccyuwu.pregxxy.casting.actions.spells

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import io.github.soccyuwu.pregxxy.casting.mishaps.MishapCantBreed
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player

object OpBreed : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val target = args.getEntity(0, argc)
        env.assertEntityInRange(target)
        if (target !is Animal || target.age < 0) {
            throw MishapCantBreed(target, false)
        }

        return SpellAction.Result(
            Spell(target),
            (10 * MediaConstants.DUST_UNIT).toLong(),
            listOf(ParticleSpray.cloud(target.position().add(0.0, target.eyeHeight / 2.0, 0.0), 1.0))
        )
    }

    private data class Spell(val target: Entity) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if(target is Villager){
                target.canBreed()
            }
            if(target is Animal){
                target.age = 0
                target.setInLove(env.castingEntity as? Player)
            }
        }
    }
}
