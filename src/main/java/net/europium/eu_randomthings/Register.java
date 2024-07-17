package net.europium.eu_randomthings;

import com.mojang.serialization.Codec;
import net.europium.eu_randomthings.blocks.StickBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Register {
    public static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK, RandomThings.MODID);
//    public static final DeferredRegister<AttachmentType<?>> STICK_BLOCK_COUNTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, RandomThings.MODID);
    public static final DeferredHolder<Block, StickBlock> STICK_BLOCK = BLOCK_REGISTER.register("stick_block", () -> new StickBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD)));
//    public static final Supplier<AttachmentType<Integer>> COUNT = STICK_BLOCK_COUNTER.register("count", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());
}
