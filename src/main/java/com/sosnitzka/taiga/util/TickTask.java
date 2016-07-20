package com.sosnitzka.taiga.util;


import net.minecraftforge.fml.relauncher.Side;

public class TickTask {
    private Runnable task;
    private int targetTicks = 0;
    private int ticks;
    private Side side;

    public TickTask(int targetTicks, Runnable runnable, Side side) {
        if (targetTicks <= 0)
            throw new IllegalArgumentException("Amount of ticks must be >0");

        this.task = runnable;
        this.targetTicks = targetTicks;
        this.side = side;
    }

    public TickTask(Runnable runnable, Side side) {
        this(1, runnable, side);
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
