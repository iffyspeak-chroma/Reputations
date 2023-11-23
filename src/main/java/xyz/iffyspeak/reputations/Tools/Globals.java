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

    public static class Language {
        public static class ReputationMessages {
            public static String ViewOwnReputation = "<green>You currently have <b><yellow>{REPUTATION}</yellow></b> reputation points.</green>";
            public static String ViewOtherReputation = "<green><b><yellow>{PLAYER}</yellow></b> currently has <b><yellow>{REPUTATION}</yellow></b> reputation points.</green>";
            public static String ConsoleViewReputation = "<red>You silly billy! You don't get to have any reputation because it'd be both positive ∞ and negative ∞</red>";
            public static String NotUsingDatabase = "<red>Reputation saving is not active which means you have no reputation to read.</red>";
            public static String VillagerDeathNearby = "<red>A villager died close to you causing you to lose a reputation point.</red>";
            public static String VillagerHealNearby = "<green>A villager was cured nearby giving you a reputation point.</green>";
            public static String VillagerInfectNearby = "<red>A villager was infected by a zombie nearby causing you to lose a reputation point.</red>";
        }
    }
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
