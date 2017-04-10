package com.kaveenrodrigo.minemidi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by pc on 4/10/2017.
 */
public class MineMidi extends JavaPlugin {

    private FileConfiguration fc = getConfig();
    private EventListner el = new EventListner(fc);

    @Override
    public void onEnable() {
        super.onEnable();
        initConf();
        getServer().getPluginManager().registerEvents(el,this);
    }

    private void initConf() {
        fc.addDefault("device_name","MINEMIDI");
        fc.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
