package net.loothelper.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class JdkCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("jdk")
            // Ветка /jdk bind <функция> <клавиша>
            .then(ClientCommandManager.literal("bind")
                .then(ClientCommandManager.argument("module", StringArgumentType.string())
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(context -> {
                            String module = StringArgumentType.getString(context, "module");
                            String key = StringArgumentType.getString(context, "key");
                            
                            // Логика привязки клавиши к модулю
                            context.getSource().sendFeedback(Text.literal(
                                "§a[LootHelper] §fКлавиша §e" + key.toUpperCase() + 
                                " §fуспешно назначена на модуль §e" + module
                            ));
                            return 1;
                        })
                    )
                )
            )
            // Ветка /jdk config ...
            .then(ClientCommandManager.literal("config")
                // /jdk config save <имя>
                .then(ClientCommandManager.literal("save")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            String configName = StringArgumentType.getString(context, "name");
                            
                            // Логика сохранения конфига в файл
                            context.getSource().sendFeedback(Text.literal(
                                "§a[LootHelper] §fКонфигурация §e" + configName + " §fсохранена."
                            ));
                            return 1;
                        })
                    )
                )
                // /jdk config remove <имя>
                .then(ClientCommandManager.literal("remove")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            String configName = StringArgumentType.getString(context, "name");
                            
                            // Логика удаления файла конфига
                            context.getSource().sendFeedback(Text.literal(
                                "§c[LootHelper] §fКонфигурация §e" + configName + " §fудалена."
                            ));
                            return 1;
                        })
                    )
                )
                // /jdk config reset <имя>
                .then(ClientCommandManager.literal("reset")
                    .then(ClientCommandManager.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            String configName = StringArgumentType.getString(context, "name");
                            
                            // Логика сброса настроек конфига
                            context.getSource().sendFeedback(Text.literal(
                                "§e[LootHelper] §fНастройки конфигурации §e" + configName + " §fсброшены."
                            ));
                            return 1;
                        })
                    )
                )
            )
        );
    }
                          }
