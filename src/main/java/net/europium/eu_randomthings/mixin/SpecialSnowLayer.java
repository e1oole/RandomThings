package net.europium.eu_randomthings.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowLayerBlock.class)
public abstract class SpecialSnowLayer extends Block {

    public SpecialSnowLayer(Properties p_49795_) {
        super(p_49795_);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (pLevel.getBrightness(LightLayer.BLOCK, pPos) > 11) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), Items.SNOWBALL.getDefaultInstance());
        }
    }
}
