package xyz.hstudio.hstudiolibrary.yaml;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Yaml extends YamlConfiguration {

    /**
     * 使用UTF8编码加载一个配置文件
     *
     * @param file 文件
     */
    public static Yaml loadConfiguration(final File file) {
        Yaml yaml = new Yaml();
        try {
            yaml.load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException ignored) {
        } catch (IOException | InvalidConfigurationException var4) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var4);
        }
        return yaml;
    }

    /**
     * 使用UTF8编码加载一个流
     *
     * @param stream 流
     */
    public static Yaml loadConfiguration(final InputStream stream) {
        Yaml yaml = new Yaml();
        try {
            yaml.load(new InputStreamReader(stream));
        } catch (IOException | InvalidConfigurationException var3) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", var3);
        }
        return yaml;
    }
}