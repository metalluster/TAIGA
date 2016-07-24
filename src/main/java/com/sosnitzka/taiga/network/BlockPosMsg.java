package com.sosnitzka.taiga.network;


import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Collection;
import java.util.HashSet;

public class BlockPosMsg implements IMessage {
    private Collection<BlockPos> blockPosList;
    private boolean messageIsValid;

    public BlockPosMsg(Collection<BlockPos> posList) {
        blockPosList = posList;
        messageIsValid = true;
    }

    public BlockPosMsg() {
        messageIsValid = false;
    }

    public Collection<BlockPos> getBlockList() {
        return blockPosList;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            int len = buf.readInt();

            blockPosList = new HashSet<BlockPos>();
            for (int i = 0; i < len; i++) {
                blockPosList.add(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
            }
        } catch (IndexOutOfBoundsException ioe) {
            System.err.println("Exception while reading BlockPosMsg: " + ioe);
            return;
        }
        messageIsValid = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (!messageIsValid) return;

        buf.writeInt(blockPosList.size());

        for (BlockPos pos : blockPosList) {
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
        }
    }
}