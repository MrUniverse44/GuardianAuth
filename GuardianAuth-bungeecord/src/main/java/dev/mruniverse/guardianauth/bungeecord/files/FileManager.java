package dev.mruniverse.guardianauth.bungeecord.files;

import dev.mruniverse.guardianauth.bungeecord.GuardianAuthBungeeCord;
import dev.mruniverse.guardianauth.bungeecord.enums.GuardianFile;
import dev.mruniverse.guardianauth.bungeecord.enums.GuardianSave;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;

public class FileManager {
    private final GuardianAuthBungeeCord plugin;
    private Configuration guardianSettings, guardianMessages, guardianMySQL;

    public FileManager(GuardianAuthBungeeCord main) {
        plugin = main;
    }

    private File newFile(String file) {
        return new File(plugin.getDataFolder(), file + ".yml");
    }

    private File getFile(GuardianFile fileToGet) {
        switch (fileToGet) {
            case MYSQL:
                return newFile("mysql");
            case MESSAGES:
                return newFile("messages");
            default:
                return newFile("settings");
        }
    }

    private void loadFile(File fileToLoad,String fileName) {
        boolean result = false;
        if(!fileToLoad.exists()) {
            try {
                result = fileToLoad.createNewFile();
            } catch (Throwable throwable) {
                plugin.getLogs().error("The plugin can't load or save configuration files!");
                plugin.getLogs().error(throwable);
            }
            if(result) {
                plugin.getLogs().debug("File: &b" + fileName + "&f created!");
            }
        }
    }
    public void loadFolder(File folderToLoad, String folderName) {
        boolean result = false;
        if(!folderToLoad.exists()) result = folderToLoad.mkdir();
        if(result) {
            plugin.getLogs().debug("Folder: &b" + folderName + "&f created!");
        }
    }

    public void loadFiles() {
        File dataFolder = plugin.getDataFolder();
        loadFolder(dataFolder,"Main Folder");
        loadFile(getFile(GuardianFile.SETTINGS),"settings.yml");
        loadFile(getFile(GuardianFile.MESSAGES),"messages.yml");
        loadFile(getFile(GuardianFile.MYSQL),"mysql.yml");
    }

    public Configuration getControl(GuardianFile guardianFile) {
        switch (guardianFile) {
            case MYSQL:
                if(guardianMySQL == null) reload(GuardianSave.MYSQL);
                return guardianMySQL;
            case MESSAGES:
                if(guardianMessages == null) reload(GuardianSave.MESSAGES);
                return guardianMessages;
            default:
                if(guardianSettings == null) reload(GuardianSave.SETTINGS);
                return guardianSettings;
        }
    }

    public void save(GuardianSave guardianSave) {
        try {
            if (guardianSave.equals(GuardianSave.MYSQL) || guardianSave.equals(GuardianSave.ALL)) {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(getControl(GuardianFile.MYSQL), getFile(GuardianFile.MYSQL));
            }
            if (guardianSave.equals(GuardianSave.MESSAGES) || guardianSave.equals(GuardianSave.ALL)) {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(getControl(GuardianFile.MESSAGES), getFile(GuardianFile.MESSAGES));
            }
            if (guardianSave.equals(GuardianSave.SETTINGS) || guardianSave.equals(GuardianSave.ALL)) {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(getControl(GuardianFile.SETTINGS), getFile(GuardianFile.SETTINGS));
            }
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't save a file, Guardian Save: " + guardianSave.toString().toLowerCase());
            plugin.getLogs().error(throwable);
        }
    }

    public void reload(GuardianSave guardianSave) {
        try {
            if(guardianSave.equals(GuardianSave.MYSQL) || guardianSave.equals(GuardianSave.ALL)) {
                guardianMySQL = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile(GuardianFile.MYSQL));
            }
            if(guardianSave.equals(GuardianSave.SETTINGS) || guardianSave.equals(GuardianSave.ALL)) {
                guardianSettings = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile(GuardianFile.SETTINGS));
            }
            if(guardianSave.equals(GuardianSave.MESSAGES) || guardianSave.equals(GuardianSave.ALL)) {
                guardianMessages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile(GuardianFile.MESSAGES));
            }
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't reload a file, Guardian Save: " + guardianSave.toString().toLowerCase());
            plugin.getLogs().error(throwable);
        }
    }
}
