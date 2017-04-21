IntelliDoors
============

> Plugin for Bukkit/Spigot (a mod for Minecraft).
>
> Compatible with **1.11+** (1.11.1/1.11.2), **1.10** (1.10.1/1.10.2), and **1.9** (1.9.1/1.9.2/1.9.3/1.9.4)

#### Please report all issues on [GitHub](https://github.com/moltendorf/IntelliDoors/issues), thank you!

## Overview

IntelliDoors provides additional functionality for doors, including:

  - When a player interacts with a door, it can reset after a delay.
    - If the door is not powered, and a player opens it via right click, it will automatically shut after a delay.
    - If the door is powered and a player closes it via right click, it will automatically open after a delay.
    - Default is 5 seconds.
  - When a door has a sibling/s, that/those sibling/s can mirror the other/s.
    - If you right click the a door, its sibling/s will open too (the clicked door will open instantly because that action is demonstrated client-side, but the sibling/s will open after 1 tick).
    - If you power a door, its sibling/s open too (perfectly in sync).
    - Simple pressure plates feel less glitchy.
    - Enabled by default.
  - When redstone power is lost doors can shut after a delay.
    - Prevents you from pressing a button, walking forward, and having the door slam on your face.
    - Default is 2 seconds.
  - Iron doors can be configured to open without redstone.
    - Disabled by default.


All features are fully configurable. Each type of door can be configured individually.

Source code is available on [GitHub](https://github.com/moltendorf/IntelliDoors).

Like what I've made? Support me by giving me a star, following me, or feel free to send me money [here]
(https://www.paypal.me/moltendorf). Thanks!

## Downloads

IntelliDoors is available for download at the following locations:

  - [SpigotMC](https://www.spigotmc.org/resources/intellidoors.24301/)
  - [BukkitDev](https://dev.bukkit.org/projects/intellidoors)
  - [Archive](https://share.moltendorf.net/Projects/Bukkit/IntelliDoors/)
