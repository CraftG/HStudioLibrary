package xyz.hstudio.hstudiolibrary.command;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import xyz.hstudio.hstudiolibrary.command.annotation.Cmd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CmdUtils {

    // 用静态变量，只实例化一次
    private final static CmdHandler HANDLER = new CmdHandler();

    /**
     * 注册一个命令
     *
     * @param plugin         插件实例
     * @param instance       命令类实例
     * @param name           主命令名
     * @param cmdNotFoundMsg 没有找到该子命令的提示
     */
    public static void register(final Plugin plugin, final Object instance, final String name, final String cmdNotFoundMsg) {
        // 子命令列表
        // 参数：Entry<注解, 方法>
        List<Map.Entry<Cmd, Method>> subCmdList = new ArrayList<>();
        // 遍历该类的所有方法
        for (Method method : instance.getClass().getMethods()) {
            // 判断是否有注解
            Cmd annotation = method.getAnnotation(Cmd.class);
            if (annotation == null) {
                continue;
            }
            subCmdList.add(new AbstractMap.SimpleImmutableEntry<>(annotation, method));
        }
        // 保存该命令
        CmdHandler.CmdWrapper wrapper = new CmdHandler.CmdWrapper(plugin, instance, subCmdList, cmdNotFoundMsg);
        CmdHandler.COMMANDS.put(name.toLowerCase(), wrapper);
        // 将命令执行器注册给CmdHandler
        try {
            PluginCommand command = Bukkit.getPluginCommand(name.toLowerCase());
            if (command == null) {
                Constructor<PluginCommand> constructor = PluginCommand.class
                        .getDeclaredConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);
                command = constructor.newInstance(name.toLowerCase(), plugin);
                constructor.setAccessible(false);

                Method method = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap");
                SimpleCommandMap commandMap = (SimpleCommandMap) method.invoke(Bukkit.getServer());
                commandMap.register(plugin.getName().toLowerCase().trim(), command);

                command = Bukkit.getPluginCommand(name.toLowerCase());
            }
            command.setExecutor(HANDLER);
        } catch (Throwable throwable) {
            throw new IllegalStateException("注册命令 " + name + " 时出现错误！", throwable);
        }
    }
}