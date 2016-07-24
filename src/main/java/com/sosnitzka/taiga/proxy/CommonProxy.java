package com.sosnitzka.taiga.proxy;

import com.sosnitzka.taiga.util.TickTaskHandler;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.tconstruct.library.materials.Material;

public class CommonProxy {
    private TickTaskHandler taskHandler;

    public void registerModels() {

    }

    public void setRenderInfo(Material material) {

    }

    public void registerFluidModels(Fluid fluid) {
    }

    public TickTaskHandler getTickHandler() {
        if (taskHandler == null)
            taskHandler = new TickTaskHandler(Side.SERVER);

        return taskHandler;
    }
}
