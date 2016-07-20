package com.sosnitzka.taiga.util;


import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TickTaskHandler {
    private List<TickTask> clientTickTasks = new ArrayList<TickTask>();
    private List<TickTask> serverTickTasks = new ArrayList<TickTask>();

    private TickTaskHandler() {
    }

    public static TickTaskHandler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @SubscribeEvent
    public synchronized void onTick(TickEvent e) {
        for (Iterator<TickTask> clientIterator = clientTickTasks.iterator(); clientIterator.hasNext(); ) {
            TickTask task = clientIterator.next();

            if (task.tickRun()) {
                clientIterator.remove();
            }
        }

        for (Iterator<TickTask> serverIterator = serverTickTasks.iterator(); serverIterator.hasNext(); ) {
            TickTask task = serverIterator.next();

            if (task.tickRun()) {
                serverIterator.remove();
            }
        }
    }

    public void addTask(TickTask tickTask) {
        if (tickTask.getSide() == Side.CLIENT || tickTask.getSide() == null)
            clientTickTasks.add(tickTask);

        if (tickTask.getSide() == Side.SERVER || tickTask.getSide() == null)
            serverTickTasks.add(tickTask);

    }

    private static final class InstanceHolder {
        static final TickTaskHandler INSTANCE = new TickTaskHandler();
    }
}
