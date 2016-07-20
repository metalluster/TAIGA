package com.sosnitzka.taiga.util;


import net.minecraftforge.fml.relauncher.Side;

public class TickTask {
    private Runnable task;
    private int targetTicks = 0;
    private int ticks;
    private Side side;

    public TickTask(int targetTicks, Side side, Runnable runnable) {
        if (targetTicks <= 0)
            throw new IllegalArgumentException("Amount of ticks must be >0");

        this.task = runnable;
        this.targetTicks = targetTicks;
        this.side = side;
    }

    public TickTask(Side side, Runnable runnable) {
        this(1, side, runnable);
    }

    public boolean tickRun() {
        ticks++;

        if (ticks >= targetTicks) {
            this.task.run();
            return true;
        }
        return false;
    }

    public Side getSide() {
        return side;
    }
}
