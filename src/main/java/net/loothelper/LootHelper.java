package net.loothelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.loothelper.command.JdkCommand;
import net.loothelper.config.ConfigManager;
import net.loothelper.module.InventoryMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootHelper implements ClientModInitializer {
    public static final String MOD_ID = "loothelper";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Запуск LootHelper v1.0.0...");

        // Инициализация директории конфигов
        ConfigManager.init();

        // Регистрация команд /jdk
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            JdkCommand.register(dispatcher);
        });

        // Главный цикл обновлений
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            // Работа модуля InventoryMove
            InventoryMove.onTick(client);
        });
    }
}
