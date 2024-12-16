package com.afidev.hubby;

import com.mojang.brigadier.CommandDispatcher;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.literal;

public class SetLobbyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("setlobby")
            .requires(source -> source.hasPermissionLevel(2) && source.getEntity() instanceof ServerPlayerEntity)
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();
                if (player == null) {
                    context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().player_only));
                    return 0;
                }
                if (hasPermission(player, "hubby.setlobby")) {
                    Vec3d pos = player.getPos();
                    float yaw = player.getYaw();
                    float pitch = player.getPitch();
                    String dimension = player.getWorld().getRegistryKey().getValue().toString();
                    HubbyConfig.setLobby(pos.x, pos.y, pos.z, yaw, pitch, dimension);
                    context.getSource().sendFeedback(() -> (Text) TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().lobby_set_confirmation), true);
                    return 1;
                } else {
                    context.getSource().sendError(TextUtils.parseColor(HubbyConfig.getConfigData().getMessages().no_permission));
                    return 0;
                }
            }));
    }

    private static boolean hasPermission(ServerPlayerEntity player, String permission) {
        try {
            User user = LuckPermsProvider.get().getUserManager().getUser(player.getUuid());
            if (user != null) {
                return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
            }
        } catch (Exception e) {
            player.sendMessage(Text.literal("Error when checking permissions."), false);
        }
        return false;
    }
}
