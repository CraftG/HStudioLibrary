package xyz.hstudio.hstudiolibrary;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class HStudioLibrary extends JavaPlugin {

    @Override
    public void onEnable() {
        new Metrics(this, 6502);
    }
}