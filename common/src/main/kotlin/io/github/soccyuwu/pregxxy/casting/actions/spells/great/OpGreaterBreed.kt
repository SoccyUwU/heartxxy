package io.github.soccyuwu.pregxxy.casting.actions.spells.great

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.common.lib.HexDamageTypes
import at.petrak.hexcasting.common.lib.HexItems
import io.github.soccyuwu.pregxxy.casting.mishaps.MishapCantBreed
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.NeutralMob
import net.minecraft.world.entity.boss.enderdragon.EnderDragon
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.monster.Enemy
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import kotlin.math.max
import kotlin.math.round

object OpGreaterBreed : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val target = args.getEntity(0, argc)
        env.assertEntityInRange(target)
        if(target !is Mob){
            throw MishapCantBreed(target, true)
        }
        if(target is Monster || target is NeutralMob || target is Enemy) {
            if (target.health >= max(target.maxHealth / 50, 1.0f)) {
                throw MishapCantBreed(target, true)
            }
        }

        return SpellAction.Result(
            Spell(target),
            (max(target.health, 1.0f) * MediaConstants.SHARD_UNIT).toLong(),
            listOf(ParticleSpray.cloud(target.position().add(0.0, target.eyeHeight / 2.0, 0.0), 3.0))
        )
    }

    private data class Spell(val target: Mob) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if(target is EnderDragon){
                target.hurt(env.world.level.damageSources().source(HexDamageTypes.OVERCAST, env.castingEntity)
                    , target.health * 999)
                val egg = Blocks.DRAGON_EGG.defaultBlockState()
                FallingBlockEntity.fall(env.world.level, target.blockPosition(), egg)
                return
            }
            target.hurt(env.world.level.damageSources().source(HexDamageTypes.OVERCAST)
                , target.health * 999)
            if(target is AgeableMob && target.age >= 0){
                var child = target.getBreedOffspring(env.world.level, target)
                if (child !== null) {
                    child.age = -24000
                    child.moveTo(target.position())
                    child.setDeltaMovement(0.2, 0.2, 0.2)
                    env.world.level.addFreshEntity(child)
                }
                child = target.getBreedOffspring(env.world.level, target)
                if (child !== null) {
                    child.age = -24000
                    child.moveTo(target.position())
                    child.setDeltaMovement(-0.2, 0.2, -0.2)
                    env.world.level.addFreshEntity(child)
                }
                return
            }

            // if all else fails just crystallize the fucker
            val dustWorth = max(1.0f, round(target.health * 2)).toInt()
            val numCharged = dustWorth / 10
            val numShard = (dustWorth - 10 * numCharged) / 5
            val numDust = (dustWorth - 10 * numCharged - 5 * numShard)
            target.spawnAtLocation(HexItems.CHARGED_AMETHYST, numCharged)
            target.spawnAtLocation(Items.AMETHYST_SHARD, numShard)
            target.spawnAtLocation(HexItems.AMETHYST_DUST, numDust)
        }
    }
}
