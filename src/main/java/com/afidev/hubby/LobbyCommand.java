package com.afidev.hubby;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class LobbyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("lobby")
            .requires(source -> source.getEntity() instanceof ServerPlayerEntity)
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player != null) {
                    if (HubbyConfig.isHubSet()) {
                        HubbyConfig.Location lobby = HubbyConfig.getLobby();
                        RegistryKey<World> dimensionKey = RegistryKey.of(RegistryKeys.WORLD, new Identifier(lobby.dimension));
                        ServerWorld targetWorld = player.getServer().getWorld(dimensionKey);
                        if (targetWorld == null) {
                            context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().cannot_find_dimension));
                            return 0;
                        }
                        player.teleport(targetWorld, lobby.x, lobby.y, lobby.z, lobby.yaw, lobby.pitch);
                        context.getSource().sendFeedback(() -> TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().teleported_to_lobby), false);
                    } else {
                        context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().lobby_not_set));
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
