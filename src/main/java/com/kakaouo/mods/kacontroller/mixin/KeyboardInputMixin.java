package com.kakaouo.mods.kacontroller.mixin;

import com.kakaouo.mods.kacontroller.utils.ButtonState;
import com.kakaouo.mods.kacontroller.utils.GamePad;
import com.kakaouo.mods.kacontroller.utils.PlayerIndex;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(boolean slowDown, CallbackInfo ci) {
        var state = GamePad.getState(PlayerIndex.ONE);
        if(!state.isConnected()) return;

        var leftThumb = state.getThumbSticks().left();
        movementForward += leftThumb.y();
        movementSideways -= leftThumb.x();

        if(movementForward > 1) movementForward = 1;
        if(movementForward < -1) movementForward = -1;
        if(movementSideways > 1) movementSideways = 1;
        if(movementSideways < -1) movementSideways = -1;

        jumping = state.getButtons().a() == ButtonState.PRESSED;
        sneaking = state.getButtons().b() == ButtonState.PRESSED;
    }
}