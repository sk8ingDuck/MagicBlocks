package me.sk8ingduck.magicblocks.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

public class Config {

    private final File file;
    private final FileConfiguration fileConfiguration;

    public Config(String name, File path) {
        file = new File(path, name);

        if (!file.exists()) {
            path.mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    protected File getFile() {
        return file;
    }

    protected FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    protected void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getPathOrSet(String path, Object defaultValue) {
        if (fileConfiguration.get(path) == null) {
            defaultValue = translateColors(defaultValue);
            fileConfiguration.set(path, defaultValue);
            save();

            return defaultValue;
        }

        return translateColors(fileConfiguration.get(path));
    }

    private Object translateColors(Object value) {
        if (value instanceof String) {
            return ((String) value).replaceAll("&", "§");
        }
        if (value instanceof ItemStack) {
            ItemStack itemStack = (ItemStack) value;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.getDisplayName() != null) {
                itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll("&", "§"));
            }
            itemStack.setItemMeta(itemMeta);
        }

        return value;
    }
}
