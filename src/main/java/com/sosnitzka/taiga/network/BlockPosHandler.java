package com.sosnitzka.taiga.network;


import com.sosnitzka.taiga.TAIGA;
import com.sosnitzka.taiga.util.TickTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class BlockPosHandler implements IMessageHandler<BlockPosMsg, IMessage> {
    private Collection<BlockPos> currentBreakBlockList;

    public IMessage onMessage(final BlockPosMsg message, MessageContext ctx) {
        if (!message.isMessageValid()) {
            return null;
        }

        if (ctx.side == Side.SERVER) {
            if (currentBreakBlockList == null)
                currentBreakBlockList = new HashSet<BlockPos>();

            Collection<BlockPos> finalBlocks = new HashSet<BlockPos>(message.getBlockList());
            finalBlocks.removeAll(currentBreakBlockList);

            currentBreakBlockList.addAll(finalBlocks);

            final WorldServer playerWorldServer = ctx.getServerHandler().playerEntity.getServerWorld();
            final List<BlockPos> posList = new ArrayList<BlockPos>(finalBlocks);
            playerWorldServer.addScheduledTask(new Runnable() {
                public void run() {
                    for (int i = 0; i < message.getBlockList().size(); i++) {
                        final int finalI = i;
                        TAIGA.proxy.getTickHandler().addTask(new TickTask(2 + (i * 15), new Runnable() {
                            @Override
                            public void run() {
                                playerWorldServer.destroyBlock(posList.get(finalI), true);
                            }
                        }));
                    }
                }
            });

            //return new BlockPosMsg(finalBlocks);
        }

        /*
        if (ctx.side == Side.CLIENT) {
            Minecraft minecraft = Minecraft.getMinecraft();
            final WorldClient worldClient = minecraft.theWorld;
            final List<BlockPos> posList = new ArrayList<BlockPos>(message.getBlockList());

            minecraft.addScheduledTask(new Runnable() {
                public void run() {
                    for (int i = 0; i < message.getBlockList().size(); i++) {
                        final int finalI = i;
                        TAIGA.proxy.getTickHandler().addTask(new TickTask(2 + (i * 15), new Runnable() {
                            @Override
                            public void run() {
                                worldClient.destroyBlock(posList.get(finalI), true);
                            }
                        }));
                    }
                }
            });
        }
        */

        return null;
    }
}
