package com.sosnitzka.taiga.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;

public class BlockPosMessage implements IMessage {

    private List<BlockPos> listPos;
    private boolean messageIsValid;

    public boolean isMessageIsValid() {
        return messageIsValid;
    }

    public BlockPosMessage(List<BlockPos> i_blockpos) {
        listPos = i_blockpos;
        messageIsValid = true;
    }

    public BlockPosMessage() {
        messageIsValid = false;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            int size = buf.readInt();
            listPos = new ArrayList<BlockPos>();
            for (int i = 0; i < size; i++) {
                listPos.add(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
            }
        } catch (IndexOutOfBoundsException ioe) {
            System.err.println("Exception while reading BlockPosMessage: " + ioe);
            return;
        }
        messageIsValid = true;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (!messageIsValid) return;
        buf.writeInt(listPos.size());
        for (BlockPos b : listPos) {
            buf.writeInt(b.getX());
            buf.writeInt(b.getY());
            buf.writeInt(b.getZ());
        }
    }

    public List<BlockPos> getBlockPosList() {
        return listPos;
    }
}
