package net.loothelper.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class InventoryMove {
    private static boolean enabled = true;
    private static boolean isHandlingItem = false;

    public static void onTick(MinecraftClient client) {
        if (!enabled || client.player == null || client.world == null) return;

        if (client.currentScreen instanceof HandledScreen<?>) {
            // Если игрок держит предмет курсором — останавливаем движение для обхода проверки
            if (isHandlingItem || !client.player.currentScreenHandler.getCursorStack().isEmpty()) {
                resetMovementKeys(client);
                return;
            }

            // Трансляция нажатий клавиш движения
            updateKey(client.options.forwardKey);
            updateKey(client.options.backKey);
            updateKey(client.options.leftKey);
            updateKey(client.options.rightKey);
            updateKey(client.options.jumpKey);
            updateKey(client.options.sprintKey);
        }
    }

    private static void updateKey(KeyBinding key) {
        long window = MinecraftClient.getInstance().getWindow().getHandle();
        int keyCode = InputUtil.fromTranslationKey(key.getBoundKeyTranslationKey()).getCode();
        
        if (keyCode != -1) {
            key.setPressed(InputUtil.isKeyPressed(window, keyCode));
        }
    }

    private static void resetMovementKeys(MinecraftClient client) {
        client.options.forwardKey.setPressed(false);
        client.options.backKey.setPressed(false);
        client.options.leftKey.setPressed(false);
        client.options.rightKey.setPressed(false);
        client.options.jumpKey.setPressed(false);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean state) {
        enabled = state;
    }
}
