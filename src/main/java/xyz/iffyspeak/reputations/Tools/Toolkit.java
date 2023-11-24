package xyz.iffyspeak.reputations.Tools;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTransformEvent;
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
        public static Material getArmorMaterial(Material armorPiece) {
            switch (armorPiece)
            {
                // Leather
                case LEATHER_BOOTS:
                case LEATHER_LEGGINGS:
                case LEATHER_CHESTPLATE:
                case LEATHER_HELMET: {
                    return Material.LEATHER;
                }

                // Gold
                case GOLDEN_BOOTS:
                case GOLDEN_LEGGINGS:
                case GOLDEN_CHESTPLATE:
                case GOLDEN_HELMET: {
                    return Material.GOLD_INGOT;
                }

                // Chainmail
                case CHAINMAIL_BOOTS:
                case CHAINMAIL_LEGGINGS:
                case CHAINMAIL_CHESTPLATE:
                case CHAINMAIL_HELMET: {
                    return Material.CHAIN;
                }

                // Iron
                case IRON_BOOTS:
                case IRON_LEGGINGS:
                case IRON_CHESTPLATE:
                case IRON_HELMET: {
                    return Material.IRON_INGOT;
                }

                // Diamond
                case DIAMOND_BOOTS:
                case DIAMOND_LEGGINGS:
                case DIAMOND_CHESTPLATE:
                case DIAMOND_HELMET: {
                    return Material.DIAMOND;
                }

                // Netherite
                case NETHERITE_BOOTS:
                case NETHERITE_LEGGINGS:
                case NETHERITE_CHESTPLATE:
                case NETHERITE_HELMET: {
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
        }
        public static boolean shouldMovementBeAffected(Player p)
        {
            return !Reputation.getComplexReputation(p).equals(ComplexNamedReputation.Peacekeeper) && !Reputation.getComplexReputation(p).equals(ComplexNamedReputation.Murderer);
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
