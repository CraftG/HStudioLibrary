package xyz.hstudio.hstudiolibrary.yaml;

import org.bukkit.plugin.Plugin;
import xyz.hstudio.hstudiolibrary.yaml.annotation.LoadConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class YamlUtils {

    /**
     * 自动加载配置文件
     *
     * @param plugin   插件实例
     * @param instance 配置文件类实例
     * @param file     配置文件
     * @param update   如果配置文件中没有所需的值，是否更新配置文件
     */
    public static <T> T load(final Plugin plugin, final T instance, final File file, final boolean update) {
        // 如果File不存在就扔报错
        if (!file.exists()) {
            throw new IllegalStateException("文件 " + file.getName() + " 不存在！");
        }
        // 使用UTF8编码加载
        Yaml yaml = Yaml.loadConfiguration(file);
        // 遍历所有成员
        for (Field field : instance.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                // 判断是否有注解
                LoadConfig annotation = field.getAnnotation(LoadConfig.class);
                if (annotation == null) {
                    continue;
                }
                // 从配置中取值
                Object value = yaml.get(annotation.path());

                if (value == null) {
                    if (update) {
                        // 如果update==true，更新配置
                        yaml.set(annotation.path(), value = field.get(instance));
                    } else {
                        continue;
                    }
                }

                // 给成员赋值
                field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("加载文件 " + file.getName() + " 值 " + field.getName() + " 时出现错误！");
            } finally {
                field.setAccessible(false);
            }
        }
        if (update) {
            try {
                // 如果update==true，保存配置到文件
                yaml.save(file);
            } catch (IOException e) {
                throw new IllegalStateException("保存文件 " + file.getName() + " 时出现错误！");
            }
        }
        return instance;
    }
}