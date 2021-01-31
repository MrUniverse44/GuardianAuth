package dev.mruniverse.guardianauth.bungeecord.utils;

import dev.mruniverse.guardianauth.bungeecord.GuardianAuthBungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("unused")
public class Logger {
    private final GuardianAuthBungeeCord plugin;
    public Logger(GuardianAuthBungeeCord main) {
        plugin = main;
    }
    /**
     * Colorize a string provided to method
     *
     * @param message Message to transform.
     * @return transformed message with colors.
     */
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a error message to console.
     * @param message message to send.
     */
    public void error(String message) {
        sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH] " + message);
    }
    /**
     * Send a error message to console.
     * @param throwable throwable to send.
     */
    public void error(Throwable throwable) {
        String location = throwable.getClass().getName();
        String error = throwable.getClass().getSimpleName();
        sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH] -------------------------");
        sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH] Location: " + location.replace("." + error,""));
        sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH] Error: " + error);
        if(throwable.getStackTrace() != null) {
            sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH] StackTrace: ");
            for(StackTraceElement line : throwable.getStackTrace()) {
                if(line.toString().contains("mruniverse")) {
                    sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH] (" + line.getLineNumber() + ") " + line.toString());
                }
            }
        }
        sendMessage("&f[&cERROR &7| &fGUARDIAN AUTH]  -------------------------");
    }

    /**
     * Send a warn message to console.
     * @param message message to send.
     */
    public void warn(String message) {
        sendMessage("&f[&eWARN &7| &fGUARDIAN AUTH] " + message);
    }

    /**
     * Send a debug message to console.
     * @param message message to send.
     */
    public void debug(String message) {
        sendMessage("&f[&9DEBUG &7| &fGUARDIAN AUTH] " + message);
    }

    /**
     * Send a info message to console.
     * @param message message to send.
     */
    public void info(String message) {
        sendMessage("&f[&bINFO &7| &fGUARDIAN AUTH] " + message);
    }

    /**
     * Sends a message to a BungeeCord command sender.
     *
     * @param sender Bukkit CommandSender
     * @param message Message to send.
     */
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(new TextComponent(color(message)));
    }


    /**
     * Used to other methods and prevent this copy pasta
     * to those methods.
     *
     * @param message Provided message
     */
    private void sendMessage(String message) {
        plugin.getProxy().getConsole().sendMessage(new TextComponent(color(message)));
    }
}

