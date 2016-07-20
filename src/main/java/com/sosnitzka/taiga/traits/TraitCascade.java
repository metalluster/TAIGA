package com.sosnitzka.taiga.traits;

import com.sosnitzka.taiga.util.TickTask;
import com.sosnitzka.taiga.util.TickTaskHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.ArrayList;
import java.util.List;

public class TraitCascade extends AbstractTrait {

    public TraitCascade() {
        super("cascade", TextFormatting.DARK_GRAY);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, final World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        float f = random.nextFloat();
        float b = 0.99F * calcBonus(tool);
        if (!world.isRemote && tool.canHarvestBlock(state) && f <= b) {
            double x, y, z, sx, sy, sz;
            sx = x = pos.getX();
            sy = y = pos.getY();
            sz = z = pos.getZ();
            final List<BlockPos> posliste = new ArrayList<BlockPos>();
            for (int i = random.nextInt((int) (ToolHelper.getCurrentDurability(tool) * 1.5f)); i > 0; i--) {

                int r = random.nextInt(3);
                int d = random.nextBoolean() ? 1 : -1;
                if (r == 0) x += d;
                if (r == 1) y += d;
                if (r == 2) z += d;
                BlockPos nextBlock = new BlockPos(x, y, z);
                if (world.getBlockState(nextBlock).equals(world.getBlockState(pos))) {
                    //world.destroyBlock(nextBlock, true);
                    posliste.add(nextBlock);
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
            for (int i = 0; i < posliste.size(); i++) {
                final int finalI = i;
                TickTaskHandler.getInstance().addTask(new TickTask(5, Side.CLIENT, new Runnable() {
                    @Override
                    public void run() {
                        world.destroyBlock(posliste.get(finalI), true);
                    }
                }));
            }

        }

    }

    private float calcBonus(ItemStack tool) {
        int durability = ToolHelper.getCurrentDurability(tool);
        int maxDurability = ToolHelper.getMaxDurability(tool);
        return (0.4f) / (maxDurability - 50) * (durability) + 0.55f;
    }
}
