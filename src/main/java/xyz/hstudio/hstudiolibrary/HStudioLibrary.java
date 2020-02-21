package xyz.hstudio.hstudiolibrary;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.hstudio.hstudiolibrary.utils.Version;

public class HStudioLibrary extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Version.VERSION == Version.UNKNOWN) {
            getLogger().info("HStudioLibrary不支持该版本！");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        new Metrics(this, 6502);
    }
}