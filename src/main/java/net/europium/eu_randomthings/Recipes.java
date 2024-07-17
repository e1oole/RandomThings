package net.europium.eu_randomthings;

import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

@EventBusSubscriber(modid = RandomThings.MODID)
public class Recipes {
    @SubscribeEvent
    public static void recipeManager(RecipesUpdatedEvent event) {
        RecipeManager recipeManager = event.getRecipeManager();

    }
}
