package com.example.examplemod.event;

import com.example.examplemod.capability.FarmXp.PlayerFarmXpProvider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.examplemod.Testmod.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = MODID)
public class ModEventListener {
    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(myblock);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(voucher);
        }
    }
    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(PlayerFarmXpProvider.class);
    }
}
