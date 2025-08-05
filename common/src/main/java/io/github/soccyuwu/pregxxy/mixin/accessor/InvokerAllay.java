package io.github.soccyuwu.pregxxy.mixin.accessor;

import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Allay.class)
public interface InvokerAllay {
    @Invoker("duplicateAllay")
    public void invokeDuplicateAllay();
}
