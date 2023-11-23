package xyz.iffyspeak.reputations.Commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iffyspeak.reputations.Tools.Globals;
import xyz.iffyspeak.reputations.Tools.SQL.SQLToolkit;
import xyz.iffyspeak.reputations.Tools.Toolkit;

import java.util.Arrays;

public class ReputationCommand extends Command {
    public ReputationCommand() {
        super("reputation", "Get player reputation", "/reputation [player]", Arrays.asList("rep", "myrep", "getrep"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (!Toolkit.SQLChecks.functioningSQL())
        {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(Globals.Language.ReputationMessages.NotUsingDatabase));
            return true;
        }
        // Check if we're dealing with a player or the console
        if (sender instanceof Player)
        {
            if (args.length >= 1 && (sender.hasPermission("rep.others") || sender.isOp()))
            {
                // Player can view other people's reputations
                OfflinePlayer player;
                try {
                    player = Bukkit.getServer().getOfflinePlayer(args[0]);
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(Toolkit.StringToolkit.ParseViewRep(player.getName(), SQLToolkit.getPlayerRep(Globals.Database.mySQL, ((Player) sender).getUniqueId().toString()))));
                    return true;
                } catch (Exception ignore) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(Toolkit.StringToolkit.ParseViewRep(SQLToolkit.getPlayerRep(Globals.Database.mySQL, ((Player) sender).getUniqueId().toString()))));
                    return true;
                }
            }

            // Player cannot view other people's reputations or wants to view own reputation
            sender.sendMessage(MiniMessage.miniMessage().deserialize(Toolkit.StringToolkit.ParseViewRep(SQLToolkit.getPlayerRep(Globals.Database.mySQL, ((Player) sender).getUniqueId().toString()))));
            return true;
        } else
        {
            if (args.length <= 0)
            {
                // Console wants to view others reputation
                OfflinePlayer player;
                try {
                    player = Bukkit.getServer().getOfflinePlayer(args[0]);
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(Toolkit.StringToolkit.ParseViewRep(player.getName(), SQLToolkit.getPlayerRep(Globals.Database.mySQL, ((Player) sender).getUniqueId().toString()))));
                    return true;
                } catch (Exception ignore) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize(Globals.Language.ReputationMessages.ConsoleViewReputation));
                    return true;
                }
            }
            // Console attempts to check its reputation but will never have one
            sender.sendMessage(MiniMessage.miniMessage().deserialize(Globals.Language.ReputationMessages.ConsoleViewReputation));
            return true;
        }
    }
}
