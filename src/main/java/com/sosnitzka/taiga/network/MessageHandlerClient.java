package com.sosnitzka.taiga.network;

import com.sosnitzka.taiga.util.TickTask;
import com.sosnitzka.taiga.util.TickTaskHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class MessageHandlerClient implements IMessageHandler<BlockPosMessage, IMessage> {


    @Override
    public IMessage onMessage(final BlockPosMessage message, MessageContext ctx) {
        if (ctx.side != Side.CLIENT) {
            return null;
        }

        if (!message.isMessageIsValid()) {
            return null;
        }
        Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.theWorld;
        minecraft.addScheduledTask(new Runnable() {
                                       @Override
                                       public void run() {
                                           processMessage(worldClient, message);
                                       }
                                   }
        );
        return null;
    }

    void processMessage(final WorldClient worldClient, BlockPosMessage message) {
        final List<BlockPos> posliste = message.getBlockPosList();
        for (int i = 0; i < posliste.size(); i++) {
            final int finalI = i;
            TickTaskHandler.getInstance().addTask(new TickTask(2 + (i * 15), Side.CLIENT, new Runnable() {
                @Override
                public void run() {
                    worldClient.destroyBlock(posliste.get(finalI), true);
                }
            }));
        }
    }

}
