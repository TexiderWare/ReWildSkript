package net.mtproject.rewildskript;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class ReWildSkript extends JavaPlugin {

    private static ReWildSkript instance;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("net.mtproject.rewildskript", "elements");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[WildSkript] has been enabled!");
    }

    public static ReWildSkript getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }
}
