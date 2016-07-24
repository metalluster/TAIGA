package com.sosnitzka.taiga.util;


public class TickTask {
    private Runnable task;
    private int targetTicks = 0;
    private int ticks;

    public TickTask(int targetTicks, Runnable runnable) {
        if (targetTicks <= 0)
            throw new IllegalArgumentException("Amount of ticks must be >0");

        this.task = runnable;
        this.targetTicks = targetTicks;
    }

    public TickTask(Runnable runnable) {
        this(1, runnable);
    }

    public boolean tickRun() {
        ticks++;

        if (ticks >= targetTicks) {
            this.task.run();
            return true;
        }
        return false;
    }
}
