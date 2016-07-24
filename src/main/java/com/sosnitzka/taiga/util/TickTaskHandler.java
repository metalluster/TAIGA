package com.sosnitzka.taiga.util;


import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TickTaskHandler {
    private List<TickTask> tickTasks = new ArrayList<TickTask>();
    private Side side;

    public TickTaskHandler(Side side) {
        this.side = side;
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (e.side == side) {
            for (Iterator<TickTask> iterator = tickTasks.iterator(); iterator.hasNext(); ) {
                TickTask task = iterator.next();

                if (task.tickRun()) {
                    iterator.remove();
                }
            }
        }
    }

    public void addTask(TickTask tickTask) {
        tickTasks.add(tickTask);
    }
}
