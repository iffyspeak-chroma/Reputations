package xyz.iffyspeak.reputations.Tools;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.iffyspeak.reputations.Tools.SQL.MySQL;

public class Globals {

    public static class Configuration {
        public static YamlDocument configuration;

        /*
        public static void saveLocalConfig()
        {
            if (configuration != null)
            {
                try {
                    configuration.save();
                } catch (Exception e)
                {
                    Bukkit.getLogger().severe(e.toString());
                }
            }
        }
         */

        public static void loadLocalConfig()
        {
            Bukkit.getLogger().info("Loading configuration... Please wait.");
            Database.dbHost = Globals.Configuration.configuration.getString("database.host");
            Database.dbPort = Globals.Configuration.configuration.getString("database.port");
            Database.dbDatabase = Globals.Configuration.configuration.getString("database.database");
            Database.dbUsername = Globals.Configuration.configuration.getString("database.username");
            Database.dbPassword = Globals.Configuration.configuration.getString("database.password");
            Database.dbUseSSL = Globals.Configuration.configuration.getBoolean("database.useSSL");

        }
    }
    public static class Database {
        public static boolean useDatabase = false;
        public static String dbHost = "localhost";
        public static String dbPort = "3306";
        public static String dbDatabase = "iffyspeak";
        public static String dbUsername = "plugin";
        public static String dbPassword = "examplepassword";
        public static boolean dbUseSSL = false;
        public static String useSsl()
        {
            return (dbUseSSL ? "true" : "false");
        }
        public static MySQL mySQL = null;
    }

}
