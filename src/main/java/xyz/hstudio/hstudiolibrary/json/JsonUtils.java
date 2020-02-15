package xyz.hstudio.hstudiolibrary.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.Plugin;
import xyz.hstudio.hstudiolibrary.json.annotation.LoadJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;

public class JsonUtils {

    /**
     * 自动加载Json文件
     *
     * @param plugin   插件实例
     * @param instance Json类实例
     * @param file     Json文件
     */
    public static <T> T load(final Plugin plugin, final T instance, final File file) {
        if (!file.exists()) {
            throw new IllegalStateException("文件 " + file.getName() + " 不存在！");
        }
        try {
            Gson gson = new Gson();
            JsonObject element = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            // 遍历所有成员
            for (Field field : instance.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    // 判断是否有注解
                    LoadJson annotation = field.getAnnotation(LoadJson.class);
                    if (annotation == null) {
                        continue;
                    }
                    String[] paths = annotation.path().contains(".") ? annotation.path().split("\\.") : new String[]{annotation.path()};

                    JsonElement value = null;
                    for (String path : paths) {
                        value = value == null ? element.get(path) : value.getAsJsonObject().get(path);
                    }

                    if (value == null) {
                        throw new IllegalStateException("没有找到文件 " + file.getName() + " 值 " + field.getName() + " ！");
                    }

                    field.set(instance, gson.fromJson(value, field.getType()));
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("加载文件 " + file.getName() + " 值 " + field.getName() + " 时出现错误！");
                } finally {
                    field.setAccessible(false);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("读取文件 " + file.getName() + " 时出现错误！");
        }
        return instance;
    }
}