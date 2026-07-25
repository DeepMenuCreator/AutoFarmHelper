package net.loothelper.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.loothelper.config.ConfigManager;
import net.minecraft.text.Text;

public class JdkCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("jdk")
            .then(ClientCommandManager.literal("bind")
                .then(ClientCommandManager.argument("module", StringArgumentType.string())
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(context -> {
                            String module = StringArgumentType.getString(context, "module");
                            String key = StringArgumentType.getString(context, "key");
                            context.getSource().sendFeedback(Text.literal(
                                "§a[LootHelper] §fКлавиша §e" + key.toUpperCase() + 
                                " §fназначена на модуль §e" + module
                            ));
                            return 1;
                        })
                    )
                )
            )
            .then(ClientCommandManager.literal("config")
                .then(ClientCommandManager.literal("load")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            String name = StringArgumentType.getString(context, "name");
                            if (ConfigManager.loadConfig(name)) {
                                context.getSource().sendFeedback(Text.literal(
                                    "§a[LootHelper] §fКонфигурация §e" + name + " §fуспешно загружена!"
                                ));
                            } else {
                                context.getSource().sendFeedback(Text.literal(
                                    "§c[LootHelper] §fФайл §e" + name + ".json §cне найден."
                                ));
                            }
                            return 1;
                        })
                    )
                )
                .then(ClientCommandManager.literal("save")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            String name = StringArgumentType.getString(context, "name");
                            if (ConfigManager.saveConfig(name)) {
                                context.getSource().sendFeedback(Text.literal(
                                    "§a[LootHelper] §fКонфигурация §e" + name + " §fсохранена в config/LootHelper/" + name + ".json"
                                ));
                            } else {
                                context.getSource().sendFeedback(Text.literal(
                                    "§c[LootHelper] §fОшибка при сохранении."
                                ));
                            }
                            return 1;
                        })
                    )
                )
                .then(ClientCommandManager.literal("remove")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            String name = StringArgumentType.getString(context, "name");
                            if (ConfigManager.removeConfig(name)) {
                                context.getSource().sendFeedback(Text.literal(
                                    "§c[LootHelper] §fКонфигурация §e" + name + " §fудалена."
                                ));
                            } else {
                                context.getSource().sendFeedback(Text.literal(
                                    "§c[LootHelper] §fКонфигурация §e" + name + " §cне найдена."
                                ));
                            }
                            return 1;
                        })
                    )
                )
                .then(ClientCommandManager.literal("reset")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            ConfigManager.saveConfig("default");
                            context.getSource().sendFeedback(Text.literal(
                                "§e[LootHelper] §fНастройки сброшены."
                            ));
                            return 1;
                        })
                    )
                )
            )
        );
    }
}
