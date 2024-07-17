package net.europium.eu_randomthings.muiltblocks;

import net.europium.eu_randomthings.ModEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashSet;
import java.util.Set;

public class CraftingTableShape {
    public static final BlockPos SOUTH = new BlockPos(0, 0, 1);
    public static final BlockPos NORTH = new BlockPos(0, 0, -1);
    public static final BlockPos WEST = new BlockPos(-1, 0, 0);
    public static final BlockPos EAST = new BlockPos(1, 0, 0);
    private final Level level;
    private final BlockPos pos;
    private final Set<BlockPos> position = new HashSet<>();

    public CraftingTableShape(Level level, BlockPos pos) {
        this.level = level;
        this.pos = pos;
    }

    public static BlockPos posMove(BlockPos pos, BlockPos move) {
        return new BlockPos(pos.getX() + move.getX(), pos.getY() + move.getY(), pos.getZ() + move.getZ());
    }

    public boolean checkStructure() {
        Block block = level.getBlockState(pos).getBlock();

        if(!ModEvents.PLANKS.contains(block)) return false;

        BlockPos N = posMove(pos, NORTH);
        BlockPos S = posMove(pos, SOUTH);
        BlockPos W = posMove(pos, WEST);
        BlockPos E = posMove(pos, EAST);
        BlockPos NE = posMove(N, EAST);
        BlockPos NW = posMove(N, WEST);
        BlockPos SE = posMove(S, EAST);
        BlockPos SW = posMove(S, WEST);


        if (testSeparateDirection(block, N, W, E, NE, NW)) return true;
        return testSeparateDirection(block, S, W, E, SE, SW);
    }

    private boolean testSeparateDirection(Block block, BlockPos s, BlockPos w, BlockPos e, BlockPos SE, BlockPos SW) {
        if (level.getBlockState(s).getBlock() == block) {
            position.add(s);
            if (level.getBlockState(w).getBlock() == block && level.getBlockState(SW).getBlock() == block){
                position.add(w);
                position.add(SW);
                return true;
            }
            if (level.getBlockState(e).getBlock() == block && level.getBlockState(SE).getBlock() == block){
                position.add(e);
                position.add(SE);
                return true;
            }
        }
        return false;
    }

    public void cleanOldBlocks() {
        for (BlockPos pos1 : position) {
            level.setBlock(pos1, Blocks.AIR.defaultBlockState(), 2);
        }
    }
}
