package me.kyjn.onehitmod;

import me.kyjn.onehitmod.init.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("onehitmod")
public class OneHitMod {
    public OneHitMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
    }
}
