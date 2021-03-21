package ga.nikd0.ServerJump;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandJump implements CommandExecutor {

    String msg_onlyConsole;
    String msg_sendPlayer;
    String msg_sendFailed;
    String msg_playerNotOnline;
    String msg_wrongUsage;
    Plugin main;

    public CommandJump(ServerJump serverjump){
        main = serverjump;
        msg_onlyConsole = main.getConfig().getString("onlyConsole");
        msg_sendPlayer = main.getConfig().getString("sendPlayer");
        msg_sendFailed = main.getConfig().getString("sendFailed");
        msg_playerNotOnline = main.getConfig().getString("playerNotOnline");
        msg_wrongUsage = main.getConfig().getString("wrongUsage");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args.length != 2) {
                Bukkit.getConsoleSender().sendMessage(msg_wrongUsage);
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                Bukkit.getConsoleSender().sendMessage(msg_sendPlayer.replace("{player}", args[0]));
                ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
                dataOutput.writeUTF("Connect");
                dataOutput.writeUTF(args[1]);
                target.sendPluginMessage(main, "BungeeCord", dataOutput.toByteArray());
                return true;
            } else {
                Bukkit.getConsoleSender().sendMessage(msg_playerNotOnline);
                return true;
            }

        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(msg_onlyConsole);
            return true;
        }
        return false;
    }
}
