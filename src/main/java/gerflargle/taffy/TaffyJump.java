package gerflargle.taffy;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("taffyjump")
public class TaffyJump {
    private static final Logger LOGGER = LogManager.getLogger();

    public TaffyJump() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientLoad);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientLoad(final FMLCommonSetupEvent event) {
        LOGGER.info("TaffyJump Starting up");
    }

    @Mod.EventBusSubscriber(value= Dist.CLIENT)
    public static class RegistryJumpEvent {
        @SubscribeEvent
        public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
            if(event.getEntity() instanceof ClientPlayerEntity) {
                ClientPlayerEntity PC = (ClientPlayerEntity) event.getEntity();
                for(int x = -1; x <= 1; x++) {
                    for(int z = -1; z <= 1; z++) {
                        Block thisBlock = PC.getEntityWorld().getBlockState(new BlockPos(PC.getPosition().add(x, 0, z))).getBlock();
                        if(thisBlock instanceof FenceBlock || thisBlock instanceof WallBlock) {
                            PC.setMotion(PC.getMotion().add(0.0D,0.05D,0.0D));
                            PC.velocityChanged = true;
                            return;
                        }
                    }
                }
            }
        }
    }
}
