package xyz.hstudio.hstudiolibrary.command;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import xyz.hstudio.hstudiolibrary.command.annotation.Cmd;
import xyz.hstudio.hstudiolibrary.command.annotation.Tab;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录所有注册的命令
 * 其中/aaa bbb ccc ddd eee
 * 'aaa'为主命令
 * 'bbb' 为子命令
 * 'ccc ddd ···' 为参数
 */
public class CmdHandler implements CommandExecutor, TabCompleter {

    // 记录所有的命令
    // <主命令, 命令信息>
    // 用ConcurrentHashMap来解决线程安全问题
    protected static final Map<String, CmdWrapper> COMMANDS = new ConcurrentHashMap<>();

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        String cmd = command.getName().toLowerCase();
        // 根据主命令名获取命令信息
        CmdWrapper wrapper = CmdHandler.COMMANDS.get(cmd);
        if (wrapper == null) {
            return true;
        }
        // 如果插件已经被卸载就扔报错
        if (!wrapper.plugin.isEnabled()) {
            throw new IllegalStateException("插件 " + wrapper.plugin.getName() + " 已被卸载");
        }

        // 当前输入的子命令
        String subCmd = args.length == 0 ? "" : args[0];
        // 当前输入的参数
        String[] newArgs = args.length == 0 ? new String[0] : (String[]) ArrayUtils.remove(args, 0);
        // 遍历所有已注册的子命令
        for (Map.Entry<Cmd, Method> entry : wrapper.subCmdList) {
            Cmd annotation = entry.getKey();
            // 如果子命令名等于当前输入的子命令
            if (!annotation.name().equalsIgnoreCase(subCmd)) {
                continue;
            }
            try {
                // 调用该方法
                // 参数：CommandSender, String[] args
                entry.getValue().invoke(wrapper.instance, sender, newArgs);
            } catch (Throwable throwable) {
                // 出现任何错误直接甩锅
                throw new IllegalStateException("执行插件 " + wrapper.plugin.getName() + " 命令 " + cmd + " 子命令 " + annotation.name() + " 时出现错误！", throwable);
            }
            return true;
        }

        // 没有找到该子命令，发送未知命令的信息
        // 因为如果找到了就会return true，所以没找到就会执行该行
        sender.sendMessage(wrapper.cmdNotFoundMsg);
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        String cmd = command.getName().toLowerCase();
        // 根据主命令名获取命令信息
        CmdWrapper wrapper = CmdHandler.COMMANDS.get(cmd);
        if (wrapper == null) {
            return Collections.emptyList();
        }
        // 如果插件已经被卸载就扔报错
        if (!wrapper.plugin.isEnabled()) {
            throw new IllegalStateException("插件 " + wrapper.plugin.getName() + " 已被卸载");
        }
        // 当前输入的子命令
        String subCmd = args.length == 0 ? "" : args[0];
        // 当前输入的参数
        String[] newArgs = args.length == 0 ? new String[0] : (String[]) ArrayUtils.remove(args, 0);
        // 遍历所有已注册的子命令
        for (Map.Entry<Tab, Method> entry : wrapper.subTabList) {
            Tab annotation = entry.getKey();
            // 如果子命令名等于当前输入的子命令
            if (!annotation.name().equalsIgnoreCase(subCmd)) {
                continue;
            }
            try {
                // 调用该方法
                // 参数：CommandSender, String[] args
                return (List<String>) entry.getValue().invoke(wrapper.instance, sender, newArgs);
            } catch (Throwable throwable) {
                // 出现任何错误直接甩锅
                throw new IllegalStateException("执行插件 " + wrapper.plugin.getName() + " Tab命令 " + cmd + " 子命令 " + annotation.name() + " 时出现错误！", throwable);
            }
        }
        // 没有找到该子命令，返回空List
        return Collections.emptyList();
    }

    /**
     * 此类用于记录命令的信息
     */
    @RequiredArgsConstructor
    protected static class CmdWrapper {
        // 注册的插件
        private final Plugin plugin;
        // 注册类的实例
        private final Object instance;
        // 子命令列表
        private final List<Map.Entry<Cmd, Method>> subCmdList;
        // 子命令Tab处理器列表
        private final List<Map.Entry<Tab, Method>> subTabList;
        // 如果没有该命令，就会发送这个消息
        private final String cmdNotFoundMsg;
    }
}