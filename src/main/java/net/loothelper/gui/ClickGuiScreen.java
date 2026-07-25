package net.loothelper.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.loothelper.module.InventoryMove;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {

    private String selectedCategory = "Movement";
    private final List<String> categories = List.of("Automation", "Movement", "Combat", "Visuals", "Telegram");

    private TextFieldWidget tokenField;
    private TextFieldWidget chatIdField;
    private final List<ButtonWidget> categoryButtons = new ArrayList<>();
    private final List<ButtonWidget> moduleButtons = new ArrayList<>();

    // Состояния модулей (примеры переключателей)
    private static boolean autoWarden = false;
    private static boolean cartLoot = false;
    private static boolean autoDrop = false;
    private static boolean esp = true;

    public ClickGuiScreen() {
        super(Text.literal("LootHelper ClickGUI"));
    }

    @Override
    protected void init() {
        int windowWidth = 420;
        int windowHeight = 240;
        int startX = (this.width - windowWidth) / 2;
        int startY = (this.height - windowHeight) / 2;

        categoryButtons.clear();
        moduleButtons.clear();

        // 1. Отрисовка кнопок категорий (слева)
        int catY = startY + 40;
        for (String category : categories) {
            ButtonWidget catBtn = ButtonWidget.builder(
                Text.literal((selectedCategory.equals(category) ? "§e> " : "") + category),
                button -> {
                    selectedCategory = category;
                    this.clearAndInit(); // Перерисовка при смене вкладки
                }
            ).dimensions(startX + 10, catY, 90, 20).build();

            this.addDrawableChild(catBtn);
            categoryButtons.add(catBtn);
            catY += 25;
        }

        // 2. Отрисовка содержимого выбранной категории (справа)
        int contentX = startX + 115;
        int contentY = startY + 40;

        switch (selectedCategory) {
            case "Movement" -> {
                ButtonWidget invMoveBtn = ButtonWidget.builder(
                    Text.literal("InventoryMove: " + (InventoryMove.isEnabled() ? "§aВКЛ" : "§cВЫКЛ")),
                    button -> {
                        InventoryMove.setEnabled(!InventoryMove.isEnabled());
                        button.setMessage(Text.literal("InventoryMove: " + (InventoryMove.isEnabled() ? "§aВКЛ" : "§cВЫКЛ")));
                    }
                ).dimensions(contentX, contentY, 280, 20).build();

                this.addDrawableChild(invMoveBtn);
                moduleButtons.add(invMoveBtn);
            }
            case "Automation" -> {
                ButtonWidget wardenBtn = ButtonWidget.builder(
                    Text.literal("AutoWardenLoot: " + (autoWarden ? "§aВКЛ" : "§cВЫКЛ")),
                    button -> {
                        autoWarden = !autoWarden;
                        button.setMessage(Text.literal("AutoWardenLoot: " + (autoWarden ? "§aВКЛ" : "§cВЫКЛ")));
                    }
                ).dimensions(contentX, contentY, 280, 20).build();

                ButtonWidget cartBtn = ButtonWidget.builder(
                    Text.literal("CartLoot: " + (cartLoot ? "§aВКЛ" : "§cВЫКЛ")),
                    button -> {
                        cartLoot = !cartLoot;
                        button.setMessage(Text.literal("CartLoot: " + (cartLoot ? "§aВКЛ" : "§cВЫКЛ")));
                    }
                ).dimensions(contentX, contentY + 25, 280, 20).build();

                ButtonWidget dropBtn = ButtonWidget.builder(
                    Text.literal("Smart AutoDrop: " + (autoDrop ? "§aВКЛ" : "§cВЫКЛ")),
                    button -> {
                        autoDrop = !autoDrop;
                        button.setMessage(Text.literal("Smart AutoDrop: " + (autoDrop ? "§aВКЛ" : "§cВЫКЛ")));
                    }
                ).dimensions(contentX, contentY + 50, 280, 20).build();

                this.addDrawableChild(wardenBtn);
                this.addDrawableChild(cartBtn);
                this.addDrawableChild(dropBtn);
            }
            case "Visuals" -> {
                ButtonWidget espBtn = ButtonWidget.builder(
                    Text.literal("ChestMinecart ESP: " + (esp ? "§aВКЛ" : "§cВЫКЛ")),
                    button -> {
                        esp = !esp;
                        button.setMessage(Text.literal("ChestMinecart ESP: " + (esp ? "§aВКЛ" : "§cВЫКЛ")));
                    }
                ).dimensions(contentX, contentY, 280, 20).build();

                this.addDrawableChild(espBtn);
            }
            case "Telegram" -> {
                this.tokenField = new TextFieldWidget(
                    this.textRenderer, contentX, contentY + 15, 280, 20, Text.literal("Bot Token")
                );
                this.tokenField.setMaxLength(128);
                this.tokenField.setPlaceholder(Text.literal("Введите Bot Token..."));
                this.addSelectableChild(this.tokenField);

                this.chatIdField = new TextFieldWidget(
                    this.textRenderer, contentX, contentY + 55, 280, 20, Text.literal("Chat ID")
                );
                this.chatIdField.setMaxLength(32);
                this.chatIdField.setPlaceholder(Text.literal("Введите Chat ID..."));
                this.addSelectableChild(this.chatIdField);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int windowWidth = 420;
        int windowHeight = 240;
        int startX = (this.width - windowWidth) / 2;
        int startY = (this.height - windowHeight) / 2;

        // Затемнение заднего плана
        this.renderBackground(context, mouseX, mouseY, delta);

        // Основной контейнер окна GUI (темный прямоугольник с рамкой)
        context.fill(startX, startY, startX + windowWidth, startY + windowHeight, 0xE0101010);
        context.drawBorder(startX, startY, windowWidth, windowHeight, 0xFF444444);

        // Разделитель между категориями и модулями
        context.fill(startX + 105, startY + 30, startX + 106, startY + windowHeight - 10, 0xFF333333);

        // Заголовок
        context.drawTextWithShadow(
            this.textRenderer, "§l§6LootHelper §fClient", startX + 12, startY + 12, 0xFFFFFF
        );

        // Текстовые подписи в разделе Telegram
        if (selectedCategory.equals("Telegram")) {
            int contentX = startX + 115;
            int contentY = startY + 40;

            context.drawTextWithShadow(this.textRenderer, "§7Bot Token:", contentX, contentY, 0xA0A0A0);
            context.drawTextWithShadow(this.textRenderer, "§7Chat ID:", contentX, contentY + 42, 0xA0A0A0);

            if (this.tokenField != null) this.tokenField.render(context, mouseX, mouseY, delta);
            if (this.chatIdField != null) this.chatIdField.render(context, mouseX, mouseY, delta);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
            }
