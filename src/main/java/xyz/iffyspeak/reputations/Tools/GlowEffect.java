package xyz.iffyspeak.reputations.Tools;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class GlowEffect {

    // Written by Qruet
    // https://github.com/qruet
    @SuppressWarnings("FieldMayBeFinal")
    private static Map<String, String> Entries = new HashMap<>();

    //called on enable
    public static void setupColorTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team negative = scoreboard.registerNewTeam("GLOW_COLOR_DARK");
        Team positive = scoreboard.registerNewTeam("GLOW_COLOR_BRIGHT");
        //team.prefix(MiniMessage.miniMessage().deserialize("<color:#ff0000><b>ADMINISTRATOR</b></color> "));
        negative.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.ALWAYS);
        negative.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        negative.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
        positive.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.ALWAYS);
        positive.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        positive.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS);
    }

    //called on disable
    public static void clearColorTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team team : scoreboard.getTeams()) {
            if (team.getName().contains("GLOW_COLOR_")) {
                team.unregister();
            }
        }
    }

    public static void setDarkGlow(Entity entity, NamedTextColor color) {
        Team negative = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("GLOW_COLOR_DARK");
        assert negative != null;
        negative.color(color);
        negative.addEntry(entity.getName());
        Entries.put(entity.getName(), negative.getName());
        entity.setGlowing(true);
    }

    public static void setBrightGlow(Entity entity, NamedTextColor color) {
        Team positive = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("GLOW_COLOR_BRIGHT");
        assert positive != null;
        positive.color(color);
        positive.addEntry(entity.getName());
        Entries.put(entity.getName(), positive.getName());
        entity.setGlowing(true);
    }

    @SuppressWarnings("unused")
    public static void removeGlow(Entity entity) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        entity.setGlowing(false);
        if(Entries.containsKey(entity.getName())){
            Team team = scoreboard.getTeam(Entries.get(entity.getName()));
            assert team != null;
            team.removeEntry(entity.getName());
            Entries.remove(entity.getName());
        }
    }

}