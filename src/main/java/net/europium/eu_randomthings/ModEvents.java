package net.europium.eu_randomthings;

import net.europium.eu_randomthings.blocks.StickBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber(modid = RandomThings.MODID)
public class ModEvents {
    public static final List<Block> woods = List.of(
            Blocks.ACACIA_LOG, Blocks.BIRCH_LOG,
            Blocks.CHERRY_LOG, Blocks.JUNGLE_LOG,
            Blocks.MANGROVE_LOG, Blocks.DARK_OAK_LOG,
            Blocks.SPRUCE_LOG, Blocks.OAK_LOG);
    @SubscribeEvent
    public static void placeStickBlock(PlayerInteractEvent.RightClickBlock event) {
        Item i = event.getItemStack().getItem();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockPos pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        Player player = event.getEntity();
        if (i == Items.STICK) {
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() == Blocks.FIRE) return;
            if (blockState.getBlock() != Register.STICK_BLOCK.get()){
                if (!level.isClientSide()) {
                    level.setBlock(pos1, Register.STICK_BLOCK.get().defaultBlockState(), 1);
                } else{
                    player.playSound(SoundEvents.WOOD_PLACE);
                    player.swing(event.getHand());
                }
            } else {
                int num = blockState.getValue(StickBlock.COUNT);
                if (num < 3) {
                    level.setBlock(pos, blockState.setValue(StickBlock.COUNT, num + 1), 1);
                    player.playSound(SoundEvents.WOOD_PLACE);
                    player.playSound(SoundEvents.FLINTANDSTEEL_USE);
                } else {
                    level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 1);
                    player.playSound(SoundEvents.FIRECHARGE_USE);
                }
                if(!level.isClientSide) {
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    player.getItemInHand(event.getHand()).consume(1, player);
                }else player.swing(event.getHand());
            }
        }
    }

    @SubscribeEvent
    public static void onBreakWood(BlockEvent.BreakEvent event) {

        Block block = event.getState().getBlock();

        if (!event.getLevel().isClientSide()) {
            for (Block wood : woods) {
                if (block == wood) {
                    event.setCanceled(true);
                    event.getPlayer().playSound(SoundEvents.ITEM_BREAK);
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSnowballDestroy(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        HitResult result = event.getRayTraceResult();
        Level level = event.getProjectile().level();
        if (projectile instanceof Snowball snowball && !level.isClientSide) {
            Vec3 location = result.getLocation();
            int x = (int) location.x;
            int y = (int) location.y;
            int z = (int) location.z;
            for (int i = -3; i < 3; i++) {
                for (int j = -3; j < 3; j++) {
                    for (int k = -3; k < 3; k++) {
                        int x1 = x + i;
                        int y1 = y + j;
                        int z1 = z + k;
                        BlockPos pos = new BlockPos(x1, y1, z1);
                        Block block = level.getBlockState(pos).getBlock();
                        if(woods.contains(block)) level.destroyBlock(pos, true);
                    }
                }
            }
        }
    }

    public static void onPlankPlace(BlockEvent.EntityPlaceEvent event){
        Block block = event.getPlacedBlock().getBlock();
        if(block == Blocks.ACACIA_PLANKS){

        }
    }
}
