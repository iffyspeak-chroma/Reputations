package xyz.iffyspeak.reputations;

import com.codingforcookies.armorequip.ArmorEquipEvent;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import xyz.iffyspeak.reputations.Tools.ComplexNamedReputation;
import xyz.iffyspeak.reputations.Tools.Globals;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;
import xyz.iffyspeak.reputations.Tools.SimpleNamedReputation;
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
        {
            /*
            THIS IS EVERYTHING TO DO WITH MODIFYING REPUTATION, NOTHING TO DO WITH XP AND INVENTORY MODIFICATION
            WHICH IS ACTUALLY WHY IT REQUIRES SQL. IF THERE'S NO SQL, THERE'S NO REPUTATION
            */
            if (_e.getEntityType().equals(EntityType.PLAYER) && (attacker != null))
            {
                // Player kill player, do reputation checks

                if ((Toolkit.Reputation.getSimpleReputation((Player) victim).equals(SimpleNamedReputation.Negative))) {
                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.Reputation.getSimpleReputation((Player) victim).equals(SimpleNamedReputation.Neutral)
                        || Toolkit.Reputation.getSimpleReputation((Player) victim).equals(SimpleNamedReputation.Positive)))
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
                //int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 90) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Neutral)
                        || Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Positive)))
                {
                    // Neutral and positive accounted for

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 95) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Negative)))
                {
                    // Negative accounted for

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }


            }
            if (Toolkit.Reputation.isEntityNeutral(_e.getEntityType()) && (attacker != null))
            {
                // Player kill neutral, do reputation check and roll for reputation changes (15% neutral, 20% positive, 10% negative)

                //int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 85) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Neutral))) {
                    // Case of neutral

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 80) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Positive))) {
                    // Case of positive

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 80) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Negative))) {
                    // Case of negative

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.addReputationPointToPlayer(attacker);
                }
            }
            if (Toolkit.Reputation.isEntityFriendly(_e.getEntityType()) && (attacker != null))
            {
                // Player kill friendly, do reputation check and roll for reputation changes (10% neutral, 5% positive, 15% negative)

                //int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString());

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 90) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Neutral))) {
                    // Case of neutral

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 95) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Positive))) {
                    // Case of positive

                    //SQLToolkit.setPlayerRep(Globals.Database.mySQL, attacker.getUniqueId().toString(), atk_r + 1);
                    Toolkit.Reputation.removeReputationPointFromPlayer(attacker);
                }

                if ((Toolkit.RNG.randomMinMax(0, 100) >= 85) && (Toolkit.Reputation.getSimpleReputation(attacker).equals(SimpleNamedReputation.Negative))) {
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


            if (victim.getType().equals(EntityType.PLAYER) && attacker != null)
            {
                // First lets check if a murderer killed someone with negative reputation
                if (Toolkit.Reputation.getComplexReputation(attacker).equals(ComplexNamedReputation.Murderer)
                        && Toolkit.Reputation.getSimpleReputation((Player) victim).equals(SimpleNamedReputation.Negative))
                {
                    _e.setDroppedExp((int) Math.floor(_e.getDroppedExp() * 0.2f));
                }

                // Let's now check if it's against someone with positive reputation
                if (Toolkit.Reputation.getComplexReputation(attacker).equals(ComplexNamedReputation.Murderer)
                        && Toolkit.Reputation.getSimpleReputation((Player) victim).equals(SimpleNamedReputation.Positive))
                {
                    _e.setDroppedExp((int) Math.ceil(_e.getDroppedExp() * 1.3f));
                }

                // Check if peacekeeper killed someone with negative reputation
                if (Toolkit.Reputation.getComplexReputation(attacker).equals(ComplexNamedReputation.Peacekeeper)
                        && Toolkit.Reputation.getSimpleReputation((Player) victim).equals(SimpleNamedReputation.Negative))
                {
                    _e.setDroppedExp((int) Math.ceil(_e.getDroppedExp() * 1.3f));
                }
            } /* Murderer and Peacekeeper checks */

            if (Toolkit.Reputation.isEntityHostile(_e.getEntityType()))
            {
                if (attacker != null)
                {
                    ComplexNamedReputation attacker_r = Toolkit.Reputation.getComplexReputation(attacker);

                    switch (attacker_r)
                    {
                        case Neutral:
                        {
                            break;
                        }
                        case Friendly:
                        {
                            _e.setDroppedExp((int) Math.ceil(_e.getDroppedExp() * 1.2f));
                        }
                        case Peacekeeper:
                        {
                            _e.setDroppedExp((int) Math.ceil(_e.getDroppedExp() * 1.2f));
                        }
                        case Player_Killer:
                        {
                            break;
                        }
                        case Murderer:
                        {
                            _e.setDroppedExp((int) Math.ceil(_e.getDroppedExp() * 1.1f));
                        }
                    }
                }
            }
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
        List<Entity> nearby;

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
                nearby.clear();
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
                nearby.clear();
                return;
            }
        }
    }

    /*
    @EventHandler
    public void onInventoryClick(InventoryClickEvent _e)
    {
        Player player = (Player) _e.getWhoClicked();

        // First, let's reset the player's speed back to the default 0.2f
        player.setWalkSpeed(0.2f);

        // NOW, we can calculate their new walk speed
        player.setWalkSpeed(Toolkit.ArmorMeta.calculateEndSpeed(player));
    }
     */

    @EventHandler
    public void onEquipArmor(ArmorEquipEvent _e)
    {
        Player player = _e.getPlayer();

        // First, let's reset the player's speed back to the default 0.2f
        player.setWalkSpeed(0.2f);

        // NOW, we can calculate their new walk speed
        player.setWalkSpeed(Toolkit.ArmorMeta.calculateEndSpeed(player));
    }
}
