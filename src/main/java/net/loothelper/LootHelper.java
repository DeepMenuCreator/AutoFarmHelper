package net.loothelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.loothelper.command.JdkCommand;
import net.loothelper.config.ConfigManager;
import net.loothelper.gui.ClickGuiScreen;
import net.loothelper.module.InventoryMove;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootHelper implements ClientModInitializer {
    public static final String MOD_ID = "loothelper";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Запуск LootHelper v1.0.0...");

        // Инициализация конфигов
        ConfigManager.init();

        // Регистрация клавиши открывания ClickGUI (Правый Shift по умолчанию)
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.loothelper.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.loothelper"
        ));

        // Регистрация команд /jdk
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            JdkCommand.register(dispatcher);
        });

        // Главный цикл обновлений
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            // Нажатие клавиши открывания GUI
            while (openGuiKey.wasPressed()) {
                client.setScreen(new ClickGuiScreen());
            }

            // Обновление логики модулей
            InventoryMove.onTick(client);
        });
    }
}
