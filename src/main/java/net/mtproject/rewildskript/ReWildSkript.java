package net.mtproject.rewildskript;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReWildSkript extends JavaPlugin {

    private static ReWildSkript instance;
    private SkriptAddon addon;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        try {
            addon = Skript.registerAddon(this);
            addon.loadClasses("net.mtproject.rewildskript", "Elements");
            Bukkit.getLogger().info("[WildSkript] has been enabled!");
        } catch (Exception e) {
            Bukkit.getLogger().severe("[WildSkript] Failed to register Skript addon or load classes!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public static ReWildSkript getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }
}
