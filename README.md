# Hubby

![banner](https://github.com/user-attachments/assets/eb1136a1-c771-4650-a601-8f45993e3c61)


**Hubby** is a **mod** for **Fabric servers** that provides a set of commands for managing spawn points, hubs, and lobbies. This mod allows administrators to effortlessly define and manage key areas with ease.

## Features

Hubby introduces a set of commands that simplify `hub`/`lobby`/`spawn` management on your server:

- `/hub` : Teleports the player to the defined hub point.
- `/lobby` : Teleports the player to the defined lobby point.
- `/spawn` : Teleports the player to the defined spawn point.
- `/sethub` : Sets the hub point for the server.
- `/setlobby` : Sets the lobby point for the server.
- `/setspawn` : Sets the spawn point for the server.

## Permission System

Hubby integrates a permission system to restrict access to the `hub`/`lobby`/`spawn` configuration commands:

- `hubby.sethub` : Permission to set the hub point (`/sethub`).
- `hubby.setlobby` : Permission to set the lobby point (`/setlobby`).
- `hubby.setspawn` : Permission to set the spawn point (`/setspawn`).

Permissions can be assigned to players or groups via a permissions manager like LuckPerms.

## Dependencies

### Fabric API
The mod requires Fabric API to function correctly.

### LuckPerms
For managing permissions, the mod requires LuckPerms.

## Why This Mod?

As a web developer with little experience in Java, I created this mod out of frustration. I needed a simple solution to add `/hub`, `/lobby`, and `/spawn` commands to a Fabric server, and since I couldn’t find one, I decided to build it myself as a way to practice and provide a straightforward solution for others.

## FAQ

### Why is there no support for other versions?

As explained in the "Why This Mod?" section, I created this mod on a whim. However, I do not rule out the possibility of working on a port from version **1.8** to **1.21** in the future. The goal would be to provide a teleportation command system for everyone and ensure that no one else like me has to feel frustrated when they can't find a suitable hub/lobby/spawn mod for Fabric.

### Does this mod work with other teleportation mods?

This mod is designed to work independently, but it should be compatible with other teleportation-related mods as long as there are no conflicting commands. Always test compatibility before using them together.

### Is this mod compatible with other permission mods besides LuckPerms?

No, this mod was specifically developed to work with LuckPerms. I designed it to integrate directly with LuckPerms, and there will be no support for other permission mods in the future.

### What Fabric versions is this mod compatible with?

This mod is currently compatible with **1.20.1** Minecraft versions.
