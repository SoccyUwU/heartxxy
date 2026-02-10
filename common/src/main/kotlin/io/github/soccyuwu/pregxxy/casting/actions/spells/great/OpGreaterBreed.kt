package io.github.soccyuwu.pregxxy.casting.actions.spells.great

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.common.lib.HexItems
import io.github.soccyuwu.pregxxy.casting.mishaps.MishapCantBreed
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.NeutralMob
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.animal.allay.Allay
import net.minecraft.world.entity.boss.enderdragon.EnderDragon
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import kotlin.math.max
import kotlin.math.round

object OpGreaterBreed : SpellAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val target = args.getEntity(0, argc)
        env.assertEntityInRange(target)
        if(target is Player || target !is Mob){
            throw MishapCantBreed(target, true)
        }
        if(target !is Animal && (target is Monster || target is NeutralMob)) {
            if (target.health >= max(target.maxHealth / 20, 1.0f)) {
                throw MishapCantBreed(target, true)
            }
        }

        var spellCost = max(target.health, 1.0f) * MediaConstants.SHARD_UNIT
        if(target is Allay){
            spellCost = 150f * MediaConstants.DUST_UNIT
        }
        return SpellAction.Result(
            Spell(target),
            spellCost.toLong(),
            listOf(ParticleSpray.cloud(target.position().add(0.0, target.eyeHeight / 2.0, 0.0), 3.0))
        )
    }

    private data class Spell(val target: Mob) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            // glass the dragon (with the player as origin because the dragon
            // is just quirky like that
            if(target is EnderDragon){
                target.kill()
                val egg = Blocks.DRAGON_EGG.defaultBlockState()
                FallingBlockEntity.fall(env.world.level, target.blockPosition(), egg)
                FallingBlockEntity.fall(env.world.level, target.blockPosition().above(1), egg)
                return
            }
            // otherwise glass it and split it
            target.kill()
            for (i in -1..1 step 2) {
                var child : Mob? = null
                if(target is AgeableMob && target.age >= 0){
                    child = target.getBreedOffspring(env.world.level, target)
                }
                if(target is Allay){
                    child = EntityType.ALLAY.create(env.world.level)
                }
                if (child !== null) {
                    if(child is AgeableMob) {
                        child.age = -24000
                    }
                    child.moveTo(target.position())
                    child.setDeltaMovement(0.2 * i, 0.2, 0.2 * i)
                    env.world.level.addFreshEntity(child)
                }
            }

            // if all else fails just crystallize the fucker
            val dustWorth = max(1.0f, round(target.health * 2)).toInt()
            val numCharged = dustWorth / 10
            val numShard = (dustWorth - 10 * numCharged) / 5
            val numDust = (dustWorth - 10 * numCharged - 5 * numShard)
            target.spawnAtLocation(ItemStack(HexItems.CHARGED_AMETHYST, numCharged))
            target.spawnAtLocation(ItemStack(Items.AMETHYST_SHARD, numShard))
            target.spawnAtLocation(ItemStack(HexItems.AMETHYST_DUST, numDust))
        }
    }
}
