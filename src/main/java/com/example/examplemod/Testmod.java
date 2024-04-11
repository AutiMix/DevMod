package com.example.examplemod;

import com.example.examplemod.capability.FarmXp.PlayerFarmXpProvider;
import com.example.examplemod.item.voucher;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import javax.swing.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Testmod.MODID)
public class Testmod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "testmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    public static final RegistryObject<Block> myblock = BLOCKS.register("myblock",() -> new Block(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.GLASS)));
    public static final RegistryObject<BlockItem> myblockitem = ITEMS.register("myblock",() -> new BlockItem(myblock.get(),new Item.Properties()));
    public static final RegistryObject<Item> voucher = ITEMS.register("voucher",() -> new voucher(new Item.Properties()));
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MODID);
    public static final RegistryObject<CreativeModeTab> mytab = CREATIVE_MODE_TABS.register("mytab",() -> CreativeModeTab.builder()
            .title(Component.translatable("mytabname"))
            .icon(() -> new ItemStack(voucher.get()))
            .displayItems((parm,output)->{
                output.accept(myblockitem.get());
                output.accept(voucher.get());
            })
            .build());

    public Testmod() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapability);
    }
    public void attachCapability(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player player){
            if(!player.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).isPresent()){
                event.addCapability(new ResourceLocation(MODID,"farmxp"),new PlayerFarmXpProvider());
            }
        }
    }
}
