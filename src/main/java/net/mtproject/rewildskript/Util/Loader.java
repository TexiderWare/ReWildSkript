package net.mtproject.rewildskript.Util;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.config.Config;
import ch.njol.util.OpenCloseable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Loader {

    private static void loadScript(File file) {
        try {
            Class<?> scriptLoaderClass = ScriptLoader.class;
            Method loadStructureMethod = scriptLoaderClass.getDeclaredMethod("loadStructure", File.class);
            loadStructureMethod.setAccessible(true);

            List<Config> configs = new ArrayList<>();
            Config config = (Config) loadStructureMethod.invoke(null, file);
            configs.add(config);

            Method loadScriptsMethod = scriptLoaderClass.getDeclaredMethod("loadScripts", List.class, OpenCloseable.class);
            loadScriptsMethod.setAccessible(true);
            loadScriptsMethod.invoke(null, configs, OpenCloseable.EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFile(File file) {
        loadScript(file);
    }

    public static void loadString(String content) {
        File tempFile = new File("plugins" + File.separator + "Skript" + File.separator + "scripts" + File.separator + "temp.lck");
        try (FileWriter fileWriter = new FileWriter(tempFile)) {
            fileWriter.write(content);
            loadFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            while (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    public static void loadURL(URL url) {
        File tempFile = new File("plugins" + File.separator + "Skript" + File.separator + "scripts" + File.separator + "temp.lck");
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            loadFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            while (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    public static void loadPlugin(String path) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        path = path.replaceAll("/", Matcher.quoteReplacement(File.separator));
        File file = new File(path);

        try {
            Plugin plugin = pluginManager.loadPlugin(file);
            pluginManager.enablePlugin(plugin);
        } catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
            e.printStackTrace();
        }
    }
}
