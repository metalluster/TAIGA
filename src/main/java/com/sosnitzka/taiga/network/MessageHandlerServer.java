package com.sosnitzka.taiga.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandlerServer implements IMessageHandler<BlockPosMessage, IMessage> {


    @Override
    public IMessage onMessage(BlockPosMessage message, MessageContext ctx) {
        if (ctx.side != Side.SERVER) {
            return null;
        }
        if (!message.isMessageIsValid()) {
            return null;
        }
        final EntityPlayerMP sendingPlayer = ctx.getServerHandler().playerEntity;
        if (sendingPlayer == null) {
            return null;
        }
        /*
        final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
        playerWorldServer.addScheduledTask(new Runnable(){
            @Override
            public void run(){

            }
        })*/
        return null;
    }
}
