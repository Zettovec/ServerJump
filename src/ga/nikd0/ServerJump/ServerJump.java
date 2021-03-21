package ga.nikd0.ServerJump;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ServerJump extends JavaPlugin implements PluginMessageListener {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        tellConsole("§9-----------------------------------------------------------------");
        tellConsole("§9[§bServerJump§9] §bEnabling ServerJump v0.1.2 for 1.16.x by Nikd0");
        tellConsole("§9-----------------------------------------------------------------");
        config.addDefault("onlyConsole", "[!] This command can only be executed from console.");
        config.addDefault("sendPlayer", "[!] Sending player {player} between servers.");
        config.addDefault("sendFailed", "[!] An error occurred while sending the player.");
        config.addDefault("playerNotOnline", "[!] Player not found.");
        config.addDefault("wrongUsage", "[!] Use /jump <player> <server>");
        config.options().copyDefaults(true);
        this.getCommand("jump").setExecutor(new CommandJump(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        saveConfig();
    }

    @Override
    public void onDisable(){
        tellConsole("§9[§bServerJump§9] §bPlugin disabled.");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }
    }

    public void tellConsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
