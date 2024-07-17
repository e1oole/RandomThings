package net.europium.eu_randomthings;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(RandomThings.MODID)
public class RandomThings
{
    public static final String MODID = "eu_randomthings";
    private static final Logger LOGGER = LogUtils.getLogger();
    public RandomThings(IEventBus modEventBus, ModContainer modContainer) {
        Register.BLOCK_REGISTER.register(modEventBus);
    }

}
