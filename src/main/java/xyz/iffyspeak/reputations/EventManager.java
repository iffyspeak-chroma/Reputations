package xyz.iffyspeak.reputations;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import xyz.iffyspeak.reputations.Tools.Globals;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;
import xyz.iffyspeak.reputations.Tools.Toolkit;

import java.util.ArrayList;
import java.util.List;

public class EventManager implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent _e)
    {
        // Check if it's their first time joining
        if (!_e.getPlayer().hasPlayedBefore())
        {
            if (Toolkit.SQLChecks.functioningSQL())
            {
                SQLToolkit.addPlayer(Globals.Database.mySQL, _e.getPlayer().getUniqueId().toString(), _e.getPlayer().getName(), 0);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent _e)
    {
        LivingEntity victim = _e.getEntity();
        Player attacker = Toolkit.Reputation.getAttackingPlayer(victim);

        if (Toolkit.SQLChecks.functioningSQL())
        { /*
            THIS IS EVERYTHING TO DO WITH MODIFYING REPUTATION, NOTHING TO DO WITH XP AND INVENTORY MODIFICATION
            WHICH IS ACTUALLY WHY IT REQUIRES SQL. IF THERE'S NO SQL, THERE'S NO REPUTATION
            */
            if (_e.getEntityType().equals(EntityType.PLAYER) && (attacker != null))
            {
                // Player kill player, do reputation checks

                // Get victim and attacker's reputations
                int vic_r,atk_r;
                //int atk_r_u = 0;
                vic_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, victim.getUniqueId().toString());
                atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if (vic_r <= -1) {
                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if (vic_r >= 0)
                {
                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r - 1);
                    Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
                }
            }

            if (_e.getEntityType().equals(EntityType.VILLAGER) && (attacker != null))
            {
                // Player kill villager, remove reputation

                //int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());
                //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r - 1);
                Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
            }

            if (Toolkit.Reputation.isEntityHostile(_e.getEntityType()) && (attacker != null))
            {
                // Player kill hostile, do reputation check and roll for reputation changes (10% neutral, 10% positive, 5% negative)
                int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 90) && atk_r >= 0)
                {
                    // Neutral and positive accounted for

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 95) && atk_r <= -1)
                {
                    // Negative accounted for

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }


            }
            if (Toolkit.Reputation.isEntityNeutral(_e.getEntityType()) && (attacker != null))
            {
                // Player kill neutral, do reputation check and roll for reputation changes (15% neutral, 20% positive, 10% negative)

                int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 85) && atk_r == 0) {
                    // Case of neutral

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 80) && atk_r > 0) {
                    // Case of positive

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 80) && atk_r < 0) {
                    // Case of negative

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }
            }
            if (Toolkit.Reputation.isEntityFriendly(_e.getEntityType()) && (attacker != null))
            {
                // Player kill friendly, do reputation check and roll for reputation changes (10% neutral, 5% positive, 15% negative)

                int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 90) && atk_r == 0) {
                    // Case of neutral

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 95) && atk_r > 0) {
                    // Case of positive

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 85) && atk_r < 0) {
                    // Case of negative

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
                }
            }
        }

        { /* THIS IS EVERYTHING TO DO WITH XP AND INVENTORY MODIFICATION */
            if (_e.getEntityType().equals(EntityType.PLAYER) && (attacker != null))
            {
                // Player kill player, 60% echo shard gain chance

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 40)) {
                    victim.getWorld().dropItemNaturally(victim.getLocation(), new ItemStack(Material.ECHO_SHARD, 1));
                }
            } /* Echo shard stuff */


        }
    }

    @EventHandler
    public void onEntityDie(EntityDamageByEntityEvent _e) {
        Entity e_victim = _e.getEntity();
        Entity e_attacker = _e.getDamager();

        if (!e_victim.getType().equals(EntityType.VILLAGER) || Toolkit.Reputation.entityHatesVillager(e_attacker))
        {
            // If the victim is not a villager or if the attacker is not a zombie variant we can go ahead and
            // ignore this bs
            return;
        }

        LivingEntity victim = (LivingEntity) e_victim;
        LivingEntity attacker = (LivingEntity) e_attacker;

        double healthPostEvent = victim.getHealth() - _e.getDamage();
        if (healthPostEvent <= 0)
        {
            // Do a radius check and punish any player who's around

            for (Entity e : victim.getNearbyEntities(7d, 7d, 7d))
            {
                if (e.getType().equals(EntityType.PLAYER))
                {
                    Player abuser = (Player) e;
                    abuser.sendMessage(MiniMessage.miniMessage().deserialize(Globals.Language.ReputationMessages.VillagerDeathNearby));
                    Toolkit.Reputation.removeReputationPointFromPlayer(abuser);
                }
            }
        }
    }

    @EventHandler
    public void onEntityTransform(EntityTransformEvent _e)
    {
        EntityTransformEvent.TransformReason reason = _e.getTransformReason();
        Entity transformer = _e.getTransformedEntity();
        List<Entity> nearby = new ArrayList<>();

        if (Toolkit.Reputation.passesTransformCheck(reason))
        {
            nearby = transformer.getNearbyEntities(7d,7d,7d);

            if (reason == EntityTransformEvent.TransformReason.CURED)
            {
                for (Entity e : nearby)
                {
                    if (e.getType().equals(EntityType.PLAYER))
                    {
                        Player p = (Player) e;
                        Toolkit.Reputation.addReputationPointToPlayer(p);
                        p.sendMessage(MiniMessage.miniMessage().deserialize(Globals.Language.ReputationMessages.VillagerHealNearby));
                    }
                }
                return;
            }

            if (reason == EntityTransformEvent.TransformReason.INFECTION)
            {
                for (Entity e : nearby)
                {
                    if (e.getType().equals(EntityType.PLAYER))
                    {
                        Player p = (Player) e;
                        Toolkit.Reputation.removeReputationPointFromPlayer(p);
                        p.sendMessage(MiniMessage.miniMessage().deserialize(Globals.Language.ReputationMessages.VillagerInfectNearby));
                    }
                }
                return;
            }
        }
    }
}
