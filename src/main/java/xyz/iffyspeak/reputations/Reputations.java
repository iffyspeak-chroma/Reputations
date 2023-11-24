package xyz.iffyspeak.reputations;

import com.codingforcookies.armorequip.ArmorListener;
import com.codingforcookies.armorequip.DispenserArmorListener;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import xyz.iffyspeak.reputations.Commands.ReputationCommand;
import xyz.iffyspeak.reputations.Tools.ComplexNamedReputation;
import xyz.iffyspeak.reputations.Tools.Globals;
import xyz.iffyspeak.reputations.Tools.GlowEffect;
import xyz.iffyspeak.reputations.Tools.SQL.MySQL;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;
import xyz.iffyspeak.reputations.Tools.Toolkit;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Reputations extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new EventManager(), this);
        //                                      Label            Prefix
        getServer().getCommandMap().register("reputation", "reputation", new ReputationCommand());

        getServer().getPluginManager().registerEvents(new ArmorListener(null), this);
        try{
            //Better way to check for this? Only in 1.13.1+?
            Class.forName("org.bukkit.event.block.BlockDispenseArmorEvent");
            getServer().getPluginManager().registerEvents(new DispenserArmorListener(), this);
        }catch(Exception ignored){}

        try {
            Globals.Configuration.configuration = YamlDocument.create(new File(getDataFolder(), "configuration.yml"), Objects.requireNonNull(getResource("configuration.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.DEFAULT,
                    DumperSettings.DEFAULT,
                    UpdaterSettings.DEFAULT
            );

            Globals.Configuration.loadLocalConfig();

            if (Globals.Configuration.configuration.getBoolean("database.enabled"))
            {
                Globals.Database.useDatabase = true;
                try {
                    Globals.Database.mySQL = new MySQL();
                    Globals.Database.mySQL.connect();
                } catch (Exception e)
                {
                    Bukkit.getLogger().severe(e.toString());
                    Bukkit.getLogger().severe("Unable to connect to database.\nDo you have the right credentials?");
                }
            } else
            {
                Bukkit.getLogger().info("Not using databases.");
            }

            if (Globals.Database.mySQL != null)
            {
                if (Globals.Database.mySQL.isConnected())
                {
                    Bukkit.getLogger().info("Successfully connected to database.");
                    Bukkit.getLogger().info("Checking tables");
                    SQLToolkit.createTable(Globals.Database.mySQL);
                }
            }
        } catch (Exception e)
        {
            Bukkit.getLogger().severe(e.toString());
        }


        //GlowEffect.clearColorTeams();
        GlowEffect.setupColorTeams();


        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers())
            {
                /* ALL OF THIS IS FOR CONTROLLING PLAYER GLOW */
                if (Toolkit.Reputation.getComplexReputation(p).equals(ComplexNamedReputation.Murderer))
                {
                    // Set a red glow
                    GlowEffect.setDarkGlow(p, NamedTextColor.RED);
                }
                if (Toolkit.Reputation.getComplexReputation(p).equals(ComplexNamedReputation.Peacekeeper))
                {
                    // Set a blue glow
                    GlowEffect.setBrightGlow(p, NamedTextColor.BLUE);
                }
                if (!p.hasPotionEffect(PotionEffectType.GLOWING) && p.isGlowing())
                {
                    Team ateam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("GLOW_COLOR_RED");
                    if (!(ateam != null && ateam.hasPlayer(p)))
                    {
                        // Remove player glow
                        GlowEffect.removeGlow(p);
                    }
                }

                //p.setWalkSpeed(Toolkit.ArmorMeta.calculateEndSpeed(p, 0.2f));
            }
        }, 0, 20);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (Toolkit.SQLChecks.functioningSQL())
        {
            Globals.Database.mySQL.disconnect();
        }
        GlowEffect.clearColorTeams();
    }
}
