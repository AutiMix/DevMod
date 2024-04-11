package com.example.examplemod.event;

import com.example.examplemod.capability.FarmXp.PlayerFarmXpProvider;
import com.example.examplemod.command.GetFarmXpCommand;
import com.mojang.realmsclient.client.Request;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.example.examplemod.Testmod.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventListener {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event){
        GetFarmXpCommand.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void breakCrop(BlockEvent.BreakEvent event){
        if(event.getState().getBlock() instanceof CropBlock){
            event.getPlayer().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent((xp) ->{
                xp.increase();
            } );
        }
    }
    @SubscribeEvent
    public static void preventStem(BonemealEvent event){
        var blockstate = event.getBlock();
        if(blockstate.getBlock() instanceof StemBlock){
            event.getEntity().getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent((xp)-> {
                if(!xp.decrease()) event.setCanceled(true);
            });
        }
    }

}
