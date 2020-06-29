package wlfrotaneto.sda;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sun.awt.X11.XException;

import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private Logger logger;
    private FileConfiguration config;
    private Server server;
    private Location prison;

    @Override
    public void onEnable() {

        this.logger = getLogger();
        this.server = getServer();

        initConfig();

        prison.setX(20);
        prison.setY(64);
        prison.setZ(50);

        logger.info("Só Doente Aqui Plugin Carregado.");
    }

    @Override
    public void onDisable() {
        logger.info("Só Doente Aqui Plugin Desligado.");
    }

    private void initConfig() {

        this.config = getConfig();

        config.options().copyDefaults(true);

        config.addDefault("message", "Bem vindo ao servidor, @p!");

        saveConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("hello")) {
            if (sender instanceof Player) {
                // Player
                Player player = (Player) sender;
                if (player.hasPermission("hello.use")) {
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Bem vindo ao servidor Só Doente Aqui!");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1S&2e &3D&4i&5v&6i&7r&8t&9a&1!"));
                    return true;
                }
                player.sendMessage(ChatColor.RED + "Você não tem permissão!");
                return true;
            }
            else {
                // Console
                sender.sendMessage("Oi console!");
                return true;
            }
        }

        if (label.equalsIgnoreCase("prender")) {
            if (sender instanceof Player) {
                // Player
                Player player = (Player) sender;
                if (args == null) {
                    player.sendMessage(ChatColor.RED + "Informe o nick do bandido!");
                    return true;
                }
                if (player.hasPermission("prender.use")) {
                    try {
                        Player receiver = server.getPlayer(args[0]);
                    } catch (XException exception) {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Jogador inválido!");
                        return true;
                    }
                    if (Objects.requireNonNull(server.getPlayer(args[0])).isOnline())
                    {
                        Player receiver = server.getPlayer(args[0]);
                        assert receiver != null;
                        receiver.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Você foi preso em nome da lei!");
                        receiver.teleport(prison);
                        receiver.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Jogador foi preso!");
                    } else {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Jogador está foragido!");
                    }
                    return true;
                }
                player.sendMessage(ChatColor.RED + "Você não tem autoridade!");
                return true;
            }
            else {
                // Console
                //sender.sendMessage("Oi console!");
                return true;
            }
        }

        return false;
    }
}
