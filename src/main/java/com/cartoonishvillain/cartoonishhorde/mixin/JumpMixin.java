package com.cartoonishvillain.cartoonishhorde.mixin;

import com.cartoonishvillain.cartoonishhorde.CartoonishHorde;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class JumpMixin {
    @Inject(at = @At("TAIL"), method = "jumpFromGround")
    private void jumpFromGround(CallbackInfo ci) {
        Player player = ((Player) (Object) this);
        if(player instanceof ServerPlayer && !CartoonishHorde.horde.getHordeActive()) {
            //Step 4 - Start.
            CartoonishHorde.horde.SetUpHorde((ServerPlayer) player);
        }
    }
}
