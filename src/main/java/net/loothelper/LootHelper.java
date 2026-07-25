package net.loothelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootHelper implements ClientModInitializer {
    public static final String MOD_ID = "loothelper";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Инициализация мода LootHelper v1.0.0...");

        // Главный цикл обновлений
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            
            // Здесь вызывается логика активных модулей
        });
    }
}
