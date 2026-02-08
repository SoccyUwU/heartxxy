package io.github.soccyuwu.pregxxy.casting.actions.spells

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.GarbageIota
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.world.entity.AgeableMob

object OpGetAge : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ent = args.getEntity(0, argc)
        env.assertEntityInRange(ent)
        return if (ent is AgeableMob){
            ent.age.asActionResult
        }else{
            listOf(GarbageIota())
        }
    }
}