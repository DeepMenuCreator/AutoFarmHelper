package net.loothelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.loothelper.command.JdkCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootHelper implements ClientModInitializer {
    public static final String MOD_ID = "loothelper";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("LootHelper v1.0.0 успешно запущен!");

        // Регистрация команд /jdk
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            JdkCommand.register(dispatcher);
        });

        // Главный цикл тиков
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            // Инициализация модулей AutoWardenLoot, CartLoot, AutoDrop, Telegram Notifier
        });
    }
}
