package xyz.iffyspeak.reputations.Tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.bukkit.entity.EntityType.*;

public class Toolkit {

    public static class StringToolkit {
        public static String ParseViewRep(int rp)
        {
            return Globals.Language.ReputationMessages.ViewOwnReputation.replace("{REPUTATION}", Integer.toString(rp));
        }
        public static String ParseViewRep(String name, int rp)
        {
            return Globals.Language.ReputationMessages.ViewOtherReputation.replace("{REPUTATION}", Integer.toString(rp)).replace("{PLAYER}", name);
        }
    }

    public static class Reputation {
        public static List<EntityType> NotFriendlyEntityTypes = Arrays.asList(BLAZE,CAVE_SPIDER,CREEPER,ELDER_GUARDIAN,ENDERMITE,
                EVOKER,GHAST,GIANT,GUARDIAN,HOGLIN,HUSK,ILLUSIONER,PHANTOM,PIGLIN_BRUTE,PILLAGER,RAVAGER,SHULKER, SILVERFISH,
                SKELETON,SLIME,SPIDER,STRAY,VEX,VINDICATOR,WARDEN,WITCH,ZOGLIN,ZOMBIE,ZOMBIE_VILLAGER,DROWNED,ENDER_DRAGON,
                MAGMA_CUBE,WITHER,WITHER_SKELETON,BEE,ENDERMAN,IRON_GOLEM,LLAMA,PIGLIN,PUFFERFISH,SNOWMAN,TRADER_LLAMA,
                ARMOR_STAND,AREA_EFFECT_CLOUD,ARROW,BLOCK_DISPLAY,CHEST_BOAT,COD,DRAGON_FIREBALL,DROPPED_ITEM,EGG,
                ENDER_CRYSTAL,ENDER_PEARL,ENDER_SIGNAL,EXPERIENCE_ORB,FALLING_BLOCK,FIREBALL,FIREWORK,GLOW_ITEM_FRAME,
                ITEM_DISPLAY,ITEM_FRAME,LEASH_HITCH,LIGHTNING,MINECART,MINECART_CHEST,MINECART_COMMAND,MINECART_FURNACE,
                MINECART_HOPPER,MINECART_TNT,MINECART_MOB_SPAWNER,MARKER,PAINTING,SALMON,TADPOLE,TRIDENT,
                TROPICAL_FISH,UNKNOWN);

        public static boolean isEntityHostile(EntityType type)
        {
            switch (type)
            {
                case BLAZE,CAVE_SPIDER,CREEPER,ELDER_GUARDIAN,ENDERMITE,EVOKER,GHAST,GIANT,GUARDIAN,HOGLIN,HUSK,ILLUSIONER,
                        PHANTOM,PIGLIN_BRUTE,PILLAGER,RAVAGER,SHULKER, SILVERFISH,SKELETON,SLIME,SPIDER,STRAY,VEX,VINDICATOR,
                        WARDEN,WITCH,ZOGLIN,ZOMBIE,ZOMBIE_VILLAGER,DROWNED,ENDER_DRAGON,MAGMA_CUBE,WITHER,WITHER_SKELETON:
                {
                    return true;
                }
                default:
                {
                    return false;
                }
            }
        }
        public static boolean isEntityNeutral(EntityType type)
        {
            switch (type)
            {
                case BEE,ENDERMAN,IRON_GOLEM,LLAMA,PIGLIN,PUFFERFISH,SNOWMAN,TRADER_LLAMA:
                {
                    return true;
                }
                default:
                {
                    return false;
                }
            }
        }
        public static boolean isEntityUseless(EntityType type)
        {
            switch (type)
            {
                case ARMOR_STAND,AREA_EFFECT_CLOUD,ARROW,BLOCK_DISPLAY,CHEST_BOAT,COD,DRAGON_FIREBALL,DROPPED_ITEM,EGG,
                        ENDER_CRYSTAL,ENDER_PEARL,ENDER_SIGNAL,EXPERIENCE_ORB,FALLING_BLOCK,FIREBALL,FIREWORK,GLOW_ITEM_FRAME,
                        ITEM_DISPLAY,ITEM_FRAME,LEASH_HITCH,LIGHTNING,MINECART,MINECART_CHEST,MINECART_COMMAND,MINECART_FURNACE,
                        MINECART_HOPPER,MINECART_TNT,MINECART_MOB_SPAWNER,MARKER,PAINTING,SALMON,TADPOLE,TRIDENT,
                        TROPICAL_FISH,UNKNOWN:
                {
                    return true;
                }
                default:
                {
                    return false;
                }
            }
        }
        public static boolean isEntityFriendly(EntityType type)
        {
            for (EntityType t : NotFriendlyEntityTypes)
            {
                if (t.equals(type))
                {
                    return true;
                }
            }
            return false;
        }
        public static Player getAttackingPlayer(LivingEntity victim)
        {
            try {
                if (victim != null && victim.getKiller().getType().equals(PLAYER))
                {
                    return victim.getKiller();
                }
            } catch (Exception ignore)
            {
                return null;
            }
            return null;
        }
        public static void addReputationPointToPlayer(Player player)
        {
            int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString());
            SQLToolkit.setPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString(), atk_r + 1);
        }
        public static void addReputationPointToPlayer(Player player, int amount)
        {
            int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString());
            SQLToolkit.setPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString(), atk_r + amount);
        }
        public static void removeReputationPointFromPlayer(Player player)
        {
            int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString());
            SQLToolkit.setPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString(), atk_r - 1);
        }
        public static void removeReputationPointFromPlayer(Player player, int amount)
        {
            int atk_r = SQLToolkit.getPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString());
            SQLToolkit.setPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString(), atk_r - amount);
        }
        public static boolean isEntityZombie(Entity ent)
        {
            return ent.getType().equals(EntityType.ZOMBIE) || ent.getType().equals(ZOMBIE_VILLAGER) || ent.getType().equals(ZOMBIE_HORSE);
        }
        public static boolean passesTransformCheck(EntityTransformEvent.TransformReason reason)
        {
            return reason == EntityTransformEvent.TransformReason.CURED || reason == EntityTransformEvent.TransformReason.INFECTION;
        }
        public static boolean isEntityRaider(Entity ent)
        {
            return ent.getType().equals(PILLAGER) || ent.getType().equals(VINDICATOR) || ent.getType().equals(RAVAGER) || ent.getType().equals(WITCH) || ent.getType().equals(EVOKER);
        }
        public static boolean entityHatesVillager(Entity ent)
        {
            return isEntityZombie(ent) || isEntityRaider(ent);
        }
        public static SimpleNamedReputation getSimpleReputation(Player player)
        {
            int p_rep = SQLToolkit.getPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString());

            if (p_rep == 0)
            {
                return SimpleNamedReputation.Neutral;
            }
            if (p_rep > 0)
            {
                return SimpleNamedReputation.Positive;
            }
            if (p_rep < 0)
            {
                return SimpleNamedReputation.Negative;
            }
            return SimpleNamedReputation.Neutral;
        }
        public static ComplexNamedReputation getComplexReputation(Player player)
        {
            int p_rep = SQLToolkit.getPlayerRep(Globals.Database.mySQL, player.getUniqueId().toString());

            if (p_rep > -3 && p_rep < 3) // Neutral
            {
                return ComplexNamedReputation.Neutral;
            }
            if (p_rep >= 3 && p_rep < 8) // Friendly
            {
                return ComplexNamedReputation.Friendly;
            }
            if (p_rep >= 8) // Peacekeeper
            {
                return ComplexNamedReputation.Peacekeeper;
            }
            if (p_rep > -7 && p_rep <= -3) // Player Killer
            {
                return ComplexNamedReputation.Player_Killer;
            }
            if (p_rep <= -7)
            {
                return ComplexNamedReputation.Murderer;
            }
            return ComplexNamedReputation.Neutral;
        }
    }

    public static class ArmorMeta {
        public static Material getArmorMaterial(ItemStack armor) {
            Material armorPiece = armor.getType();

            switch (armorPiece)
            {
                // Leather
                case LEATHER_BOOTS:
                case LEATHER_LEGGINGS:
                case LEATHER_CHESTPLATE:
                case LEATHER_HELMET:
                case LEATHER: {
                    return Material.LEATHER;
                }

                // Gold
                case GOLDEN_BOOTS:
                case GOLDEN_LEGGINGS:
                case GOLDEN_CHESTPLATE:
                case GOLDEN_HELMET:
                case GOLD_INGOT: {
                    return Material.GOLD_INGOT;
                }

                // Chainmail
                case CHAINMAIL_BOOTS:
                case CHAINMAIL_LEGGINGS:
                case CHAINMAIL_CHESTPLATE:
                case CHAINMAIL_HELMET:
                case CHAIN: {
                    return Material.CHAIN;
                }

                // Iron
                case IRON_BOOTS:
                case IRON_LEGGINGS:
                case IRON_CHESTPLATE:
                case IRON_HELMET:
                case IRON_INGOT: {
                    return Material.IRON_INGOT;
                }

                // Diamond
                case DIAMOND_BOOTS:
                case DIAMOND_LEGGINGS:
                case DIAMOND_CHESTPLATE:
                case DIAMOND_HELMET:
                case DIAMOND: {
                    return Material.DIAMOND;
                }

                // Netherite
                case NETHERITE_BOOTS:
                case NETHERITE_LEGGINGS:
                case NETHERITE_CHESTPLATE:
                case NETHERITE_HELMET:
                case NETHERITE_INGOT: {
                    return Material.NETHERITE_INGOT;
                }

                default:
                {
                    return Material.AIR;
                }
            }
        }

        public static Material getArmorMaterial(Material armorPiece) {


            switch (armorPiece)
            {
                // Leather
                case LEATHER_BOOTS:
                case LEATHER_LEGGINGS:
                case LEATHER_CHESTPLATE:
                case LEATHER_HELMET:
                case LEATHER: {
                    return Material.LEATHER;
                }

                // Gold
                case GOLDEN_BOOTS:
                case GOLDEN_LEGGINGS:
                case GOLDEN_CHESTPLATE:
                case GOLDEN_HELMET:
                case GOLD_INGOT: {
                    return Material.GOLD_INGOT;
                }

                // Chainmail
                case CHAINMAIL_BOOTS:
                case CHAINMAIL_LEGGINGS:
                case CHAINMAIL_CHESTPLATE:
                case CHAINMAIL_HELMET:
                case CHAIN: {
                    return Material.CHAIN;
                }

                // Iron
                case IRON_BOOTS:
                case IRON_LEGGINGS:
                case IRON_CHESTPLATE:
                case IRON_HELMET:
                case IRON_INGOT: {
                    return Material.IRON_INGOT;
                }

                // Diamond
                case DIAMOND_BOOTS:
                case DIAMOND_LEGGINGS:
                case DIAMOND_CHESTPLATE:
                case DIAMOND_HELMET:
                case DIAMOND: {
                    return Material.DIAMOND;
                }

                // Netherite
                case NETHERITE_BOOTS:
                case NETHERITE_LEGGINGS:
                case NETHERITE_CHESTPLATE:
                case NETHERITE_HELMET:
                case NETHERITE_INGOT: {
                    return Material.NETHERITE_INGOT;
                }

                default:
                {
                    return Material.AIR;
                }
            }
        }
        public static class Costs {
            public static float HELMET_COST = 0.209f;
            public static float CHESTPLATE_COST = 0.333f;
            public static float LEGGINGS_COST = 0.292f;
            public static float BOOTS_COST = 0.166f;

            public static float LEATHER_COST = 0.95f;
            public static float GOLD_COST = 0.5f;
            public static float CHAINMAIL_COST = 0.87f;
            public static float IRON_COST = 0.80f;
            public static float DIAMOND_COST = 0.78f;
            public static float NETHERITE_COST = 0.40f;

            public static float FRIENDLY_MULTIPLIER = 0.5f;
            public static float PLAYER_KILLER_MULTIPLIER = 1.2f;
        }
        public static boolean shouldMovementBeAffected(Player p)
        {
            return !Reputation.getComplexReputation(p).equals(ComplexNamedReputation.Peacekeeper) && !Reputation.getComplexReputation(p).equals(ComplexNamedReputation.Murderer);
        }

        public static boolean shouldMovementBeAffected(ComplexNamedReputation reputation)
        {
            return switch (reputation) {
                case Peacekeeper, Murderer -> false;
                default -> true;
            };
        }

        /* REWRITE NEEDED
        public static float calculateEndSpeed(Player player)
            {
                float curSpeed = player.getWalkSpeed();
                ComplexNamedReputation reputation = Toolkit.Reputation.getComplexReputation(player);

                if (!shouldMovementBeAffected(reputation))
                {
                    return curSpeed;
                }

                ItemStack helm = null;
                ItemStack chest = null;
                ItemStack legging = null;
                ItemStack boots = null;

                if (player.getEquipment().getHelmet() != null)
                {
                    helm = player.getEquipment().getHelmet();
                }

                if (player.getEquipment().getChestplate() != null)
                {
                    chest = player.getEquipment().getChestplate();
                }

                if (player.getEquipment().getLeggings() != null)
                {
                    legging = player.getEquipment().getLeggings();
                }

                if (player.getEquipment().getBoots() != null)
                {
                    boots = player.getEquipment().getBoots();
                }

                {
                    if (helm != null && helm.getType().equals(Material.GOLDEN_HELMET) ||
                            chest != null && chest.getType().equals(Material.GOLDEN_CHESTPLATE) ||
                            legging != null && legging.getType().equals(Material.GOLDEN_LEGGINGS) ||
                            boots != null && boots.getType().equals(Material.GOLDEN_BOOTS)) {
                        return curSpeed * 0.5f;
                    }
                }

                {
                    if (helm != null && helm.getType().equals(Material.NETHERITE_HELMET) ||
                            chest != null && chest.getType().equals(Material.NETHERITE_CHESTPLATE) ||
                            legging != null && legging.getType().equals(Material.NETHERITE_LEGGINGS) ||
                            boots != null && boots.getType().equals(Material.NETHERITE_BOOTS)) {
                        return curSpeed * 0.4f;
                    }
                }

                float totalSetCost = 0.0f;
                if (helm != null)
                {
                    totalSetCost += calculatePieceCost(EquipmentSlot.HEAD, helm.getType());
                }
                if (chest != null)
                {
                    totalSetCost += calculatePieceCost(EquipmentSlot.CHEST, chest.getType());
                }
                if (legging != null)
                {
                    totalSetCost += calculatePieceCost(EquipmentSlot.LEGS, legging.getType());
                }
                if (boots != null)
                {
                    totalSetCost += calculatePieceCost(EquipmentSlot.FEET, boots.getType());
                }


                // current speed * ((helmet cost + chest cost + legs cost + boots cost) * reputation debuff cost)
                // curSpeed * (totalSetCost * debuff)
                // curSpeed * reputationSpeedMultiplier

                float reputationSpeedMultiplier = 1f;

                if (Toolkit.Reputation.getComplexReputation(player).equals(ComplexNamedReputation.Friendly))
                {
                    reputationSpeedMultiplier = totalSetCost * Costs.FRIENDLY_MULTIPLIER;
                }
                if (Toolkit.Reputation.getComplexReputation(player).equals(ComplexNamedReputation.Player_Killer))
                {
                    reputationSpeedMultiplier = totalSetCost * Costs.PLAYER_KILLER_MULTIPLIER;
                }

                float newSpeed = reputationSpeedMultiplier * totalSetCost;

                Bukkit.getLogger().info("cS: " + curSpeed + ", tSC: " + totalSetCost + ", rSM: " + reputationSpeedMultiplier + ", rSM tSC calc: " + newSpeed + ", calculated: " + (curSpeed * reputationSpeedMultiplier * totalSetCost));

                return curSpeed * newSpeed;
            } */

        public static float calculateEndSpeed(Player player, float default_speed)
        {
            ComplexNamedReputation reputation = Toolkit.Reputation.getComplexReputation(player);

            if (!Toolkit.ArmorMeta.shouldMovementBeAffected(reputation))
            {
                return default_speed;
            }

            float helmetCost = 1f;
            float chestCost = 1f;
            float pantsCost = 1f;
            float bootsCost = 1f;
            ItemStack helm;
            ItemStack chest;
            ItemStack pants;
            ItemStack boots;
            if (player.getEquipment().getHelmet() != null)
            {
                helm = player.getEquipment().getHelmet();
                Material a_mat = Toolkit.ArmorMeta.getArmorMaterial(helm);

                switch (a_mat)
                {
                    // LEATHER GOLD_INGOT CHAIN IRON_INGOT DIAMOND NETHERITE_INGOT AIR
                    default:
                    case AIR:
                    {
                        break;
                    }
                    case LEATHER:
                    {
                        helmetCost = helmetCost * Costs.LEATHER_COST * Costs.HELMET_COST;
                        break;
                    }
                    case GOLD_INGOT:
                    {
                        helmetCost = helmetCost * Costs.GOLD_COST * Costs.HELMET_COST;
                        break;
                    }
                    case CHAIN:
                    {
                        helmetCost = helmetCost * Costs.CHAINMAIL_COST * Costs.HELMET_COST;
                        break;
                    }
                    case IRON_INGOT:
                    {
                        helmetCost = helmetCost * Costs.IRON_COST * Costs.HELMET_COST;
                        break;
                    }
                    case DIAMOND:
                    {
                        helmetCost = helmetCost * Costs.DIAMOND_COST * Costs.HELMET_COST;
                        break;
                    }
                    case NETHERITE_INGOT:
                    {
                        helmetCost = helmetCost * Costs.NETHERITE_COST * Costs.HELMET_COST;
                        break;
                    }
                }
            } else
            {
                helmetCost = 0f;
            }

            if (player.getEquipment().getChestplate() != null)
            {
                chest = player.getEquipment().getChestplate();
                Material a_mat = Toolkit.ArmorMeta.getArmorMaterial(chest);

                switch (a_mat)
                {
                    // LEATHER GOLD_INGOT CHAIN IRON_INGOT DIAMOND NETHERITE_INGOT AIR
                    default:
                    case AIR:
                    {
                        break;
                    }
                    case LEATHER:
                    {
                        chestCost = chestCost * Costs.LEATHER_COST * Costs.CHESTPLATE_COST;
                        break;
                    }
                    case GOLD_INGOT:
                    {
                        chestCost = chestCost * Costs.GOLD_COST * Costs.CHESTPLATE_COST;
                        break;
                    }
                    case CHAIN:
                    {
                        chestCost = chestCost * Costs.CHAINMAIL_COST * Costs.CHESTPLATE_COST;
                        break;
                    }
                    case IRON_INGOT:
                    {
                        chestCost = chestCost * Costs.IRON_COST * Costs.CHESTPLATE_COST;
                        break;
                    }
                    case DIAMOND:
                    {
                        chestCost = chestCost * Costs.DIAMOND_COST * Costs.CHESTPLATE_COST;
                        break;
                    }
                    case NETHERITE_INGOT:
                    {
                        chestCost = chestCost * Costs.NETHERITE_COST * Costs.CHESTPLATE_COST;
                        break;
                    }
                }
            } else
            {
                chestCost = 0f;
            }

            if (player.getEquipment().getLeggings() != null)
            {
                pants = player.getEquipment().getLeggings();
                Material a_mat = Toolkit.ArmorMeta.getArmorMaterial(pants);

                switch (a_mat)
                {
                    // LEATHER GOLD_INGOT CHAIN IRON_INGOT DIAMOND NETHERITE_INGOT AIR
                    default:
                    case AIR:
                    {
                        break;
                    }
                    case LEATHER:
                    {
                        pantsCost = pantsCost * Costs.LEATHER_COST * Costs.LEGGINGS_COST;
                        break;
                    }
                    case GOLD_INGOT:
                    {
                        pantsCost = pantsCost * Costs.GOLD_COST * Costs.LEGGINGS_COST;
                        break;
                    }
                    case CHAIN:
                    {
                        pantsCost = pantsCost * Costs.CHAINMAIL_COST * Costs.LEGGINGS_COST;
                        break;
                    }
                    case IRON_INGOT:
                    {
                        pantsCost = pantsCost * Costs.IRON_COST * Costs.LEGGINGS_COST;
                        break;
                    }
                    case DIAMOND:
                    {
                        pantsCost = pantsCost * Costs.DIAMOND_COST * Costs.LEGGINGS_COST;
                        break;
                    }
                    case NETHERITE_INGOT:
                    {
                        pantsCost = pantsCost * Costs.NETHERITE_COST * Costs.LEGGINGS_COST;
                        break;
                    }
                }
            } else
            {
                pantsCost = 0f;
            }

            if (player.getEquipment().getBoots() != null)
            {
                boots = player.getEquipment().getBoots();
                Material a_mat = Toolkit.ArmorMeta.getArmorMaterial(boots);

                switch (a_mat)
                {
                    // LEATHER GOLD_INGOT CHAIN IRON_INGOT DIAMOND NETHERITE_INGOT AIR
                    default:
                    case AIR:
                    {
                        break;
                    }
                    case LEATHER:
                    {
                        bootsCost = bootsCost * Costs.LEATHER_COST * Costs.BOOTS_COST;
                        break;
                    }
                    case GOLD_INGOT:
                    {
                        bootsCost = bootsCost * Costs.GOLD_COST * Costs.BOOTS_COST;
                        break;
                    }
                    case CHAIN:
                    {
                        bootsCost = bootsCost * Costs.CHAINMAIL_COST * Costs.BOOTS_COST;
                        break;
                    }
                    case IRON_INGOT:
                    {
                        bootsCost = bootsCost * Costs.IRON_COST * Costs.BOOTS_COST;
                        break;
                    }
                    case DIAMOND:
                    {
                        bootsCost = bootsCost * Costs.DIAMOND_COST * Costs.BOOTS_COST;
                        break;
                    }
                    case NETHERITE_INGOT:
                    {
                        bootsCost = bootsCost * Costs.NETHERITE_COST * Costs.BOOTS_COST;
                        break;
                    }
                }
            } else
            {
                bootsCost = 0f;
            }

            Bukkit.getLogger().info("hC: " + helmetCost + ", cC: " + chestCost + ", pC: " + pantsCost + ", bC: " + bootsCost);
            float additionVar = helmetCost + chestCost + pantsCost + bootsCost;
            //float multiVar = helmetCost * chestCost * pantsCost * bootsCost;
            Bukkit.getLogger().info("add: " + additionVar);

            return default_speed * additionVar;
        }
    }




    public static class SQLChecks {
        public static boolean functioningSQL()
        {
            return Globals.Database.useDatabase && (Globals.Database.mySQL != null);
        }
    }
    public static class RNG {
        public static int randomMinMax(int min, int max)
        {
            Random rn = new Random();
            return rn.nextInt((max - min) + 1) + min;
        }
    }

}
