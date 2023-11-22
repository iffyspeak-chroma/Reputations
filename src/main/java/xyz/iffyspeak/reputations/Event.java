package xyz.iffyspeak.reputations;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.iffyspeak.reputations.Tools.Globals;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;

public class Event implements Listener {

    public void onPlayerJoin(PlayerJoinEvent _e)
    {
        // Check if it's their first time joining
        if (!_e.getPlayer().hasPlayedBefore())
        {
            if (Globals.Database.useDatabase)
            {
                SQLToolkit.addPlayer(Globals.Database.mySQL, _e.getPlayer().getUniqueId().toString(), _e.getPlayer().getName(), 0);
            }
        }
    }

}
