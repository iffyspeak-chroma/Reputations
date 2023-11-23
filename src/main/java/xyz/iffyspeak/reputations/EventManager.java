package xyz.iffyspeak.reputations;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.iffyspeak.reputations.Tools.Globals;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;
import xyz.iffyspeak.reputations.Tools.Toolkit;

public class EventManager implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent _e)
    {
        // Check if it's their first time joining
        if (!_e.getPlayer().hasPlayedBefore())
        {
            if (Globals.Database.useDatabase && (Globals.Database.mySQL != null))
            {
                SQLToolkit.addPlayer(Globals.Database.mySQL, _e.getPlayer().getUniqueId().toString(), _e.getPlayer().getName(), 0);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent _e)
    {
        LivingEntity victim = _e.getEntity();
        Player attacker = Toolkit.ArmorMeta.getAttackingPlayer(victim);

        { /* THIS IS EVERYTHING TO DO WITH MODIFYING REPUTATION, NOTHING TO DO WITH XP AND INVENTORY MODIFICATION */
            if (_e.getEntityType().equals(EntityType.PLAYER) && (attacker != null))
            {
                // Player kill player, do reputation checks
            }

            if (_e.getEntityType().equals(EntityType.VILLAGER) && (attacker != null))
            {
                // Player kill villager, remove reputation
            }

            if (Toolkit.ArmorMeta.isEntityHostile(_e.getEntityType()) && (attacker != null))
            {
                // Player kill hostile, do reputation check and roll for reputation changes (10% neutral, 10% positive, 5% negative)
            }
            if (Toolkit.ArmorMeta.isEntityNeutral(_e.getEntityType()) && (attacker != null))
            {
                // Player kill neutral, do reputation check and roll for reputation changes (15% neutral, 20% positive, 10% negative)
            }
            if (Toolkit.ArmorMeta.isEntityFriendly(_e.getEntityType()) && (attacker != null))
            {
                // Player kill friendly, do reputation check and roll for reputation changes (10% neutral, 5% positive, 15% negative)
            }
        }

        { /* THIS IS EVERYTHING TO DO WITH XP AND INVENTORY MODIFICATION */
            if (_e.getEntityType().equals(EntityType.PLAYER) && (attacker != null))
            {
                // Player kill player, 60% echo shard gain chance
            }
        }
    }
}
