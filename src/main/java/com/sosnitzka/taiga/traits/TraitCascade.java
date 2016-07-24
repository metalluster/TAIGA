package com.sosnitzka.taiga.traits;

import com.sosnitzka.taiga.TAIGA;
import com.sosnitzka.taiga.network.BlockPosMsg;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.Collection;
import java.util.HashSet;

public class TraitCascade extends AbstractTrait {

    public TraitCascade() {
        super("cascade", TextFormatting.DARK_GRAY);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, final World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        float f = random.nextFloat();
        if (!world.isRemote && tool.canHarvestBlock(state) && f <= 0.9) {
            double x, y, z, sx, sy, sz;
            sx = x = pos.getX();
            sy = y = pos.getY();
            sz = z = pos.getZ();
            final Collection<BlockPos> posList = new HashSet<BlockPos>();
            for (int i = random.nextInt((int) (ToolHelper.getCurrentDurability(tool) * 1.5f)); i > 0; i--) { // TODO: limit to 100
                int r = random.nextInt(3);
                int d = random.nextBoolean() ? 1 : -1;
                if (r == 0) x += d;
                if (r == 1) y += d;
                if (r == 2) z += d;
                BlockPos nextBlock = new BlockPos(x, y, z);
                if (world.getBlockState(nextBlock).equals(world.getBlockState(pos))) {
                    //world.destroyBlock(nextBlock, true);
                    posList.add(nextBlock);
                    sx = x = nextBlock.getX();
                    sy = y = nextBlock.getY();
                    sz = z = nextBlock.getZ();
                    ToolHelper.damageTool(tool, random.nextInt(2), player);
                } else {
                    x = sx;
                    y = sy;
                    z = sz;
                }
            }

            TAIGA.simpleNetworkWrapper.sendToServer(new BlockPosMsg(posList));
        }
    }
}
