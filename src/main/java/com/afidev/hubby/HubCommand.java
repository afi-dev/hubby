package com.afidev.hubby;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class HubCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("hub")
            .requires(source -> source.getEntity() instanceof ServerPlayerEntity)
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    if (HubbyConfig.isHubSet()) {
                        HubbyConfig.Location hub = HubbyConfig.getHub();
                        RegistryKey<World> dimensionKey = RegistryKey.of(RegistryKeys.WORLD, new Identifier(hub.dimension));
                        ServerWorld targetWorld = player.getServer().getWorld(dimensionKey);
                        if (targetWorld == null) {
                            context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().cannot_find_dimension));
                            return 0;
                        }
                        player.teleport(targetWorld, hub.x, hub.y, hub.z, hub.yaw, hub.pitch);
                        context.getSource().sendFeedback(() -> TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().teleported_to_hub), false);
                    } else {
                        context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().hub_not_set));
                        return 0;
                    }
                } else {
                    context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().player_only));
                    return 0;
                }
                return 1;
            }));
    }
}
