package net.loothelper.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.loothelper.module.InventoryMove;

public class ClickGuiScreen extends Screen {
    private TextFieldWidget tokenField;
    private TextFieldWidget chatIdField;

    public ClickGuiScreen() {
        super(Text.literal("LootHelper ClickGUI"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Поле ввода Bot Token
        this.tokenField = new TextFieldWidget(
            this.textRenderer, 
            centerX - 100, centerY - 50, 200, 20, 
            Text.literal("Bot Token")
        );
        this.tokenField.setMaxLength(128);
        this.tokenField.setPlaceholder(Text.literal("Введите Bot Token..."));
        this.addSelectableChild(this.tokenField);

        // Поле ввода Chat ID
        this.chatIdField = new TextFieldWidget(
            this.textRenderer, 
            centerX - 100, centerY - 20, 200, 20, 
            Text.literal("Chat ID")
        );
        this.chatIdField.setMaxLength(32);
        this.chatIdField.setPlaceholder(Text.literal("Введите Chat ID..."));
        this.addSelectableChild(this.chatIdField);

        // Кнопка переключения InventoryMove
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("InventoryMove: " + (InventoryMove.isEnabled() ? "§aВКЛ" : "§cВЫКЛ")),
            button -> {
                InventoryMove.setEnabled(!InventoryMove.isEnabled());
                button.setMessage(Text.literal("InventoryMove: " + (InventoryMove.isEnabled() ? "§aВКЛ" : "§cВЫКЛ")));
            }
        ).dimensions(centerX - 100, centerY + 20, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Затемнение заднего фона
        this.renderBackground(context, mouseX, mouseY, delta);

        // Заголовок GUI
        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            "§l§6LootHelper §fClient GUI", 
            this.width / 2, this.height / 2 - 80, 0xFFFFFF
        );

        // Отрисовка полей ввода
        this.tokenField.render(context, mouseX, mouseY, delta);
        this.chatIdField.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false; // Игра не ставится на паузу в одиночном/мультиплеерном режиме
    }
}
