package dev.mruniverse.guardianauth.bungeecord;

import dev.mruniverse.guardianauth.bungeecord.files.FileManager;
import dev.mruniverse.guardianauth.bungeecord.utils.Logger;
import net.md_5.bungee.api.plugin.Plugin;

public final class GuardianAuthBungeeCord extends Plugin {
    private FileManager fileManager;
    private Logger logger;
    @Override
    public void onEnable() {
        logger = new Logger(this);
        fileManager = new FileManager(this);
        fileManager.loadFiles();
    }

    public Logger getLogs() { return logger; }
    public FileManager getFiles() { return fileManager; }
}
