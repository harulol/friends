package dev.hawu.plugins.friends

import dev.hawu.plugins.api.Constants
import dev.hawu.plugins.api.collections.tuples.Pair
import groovy.transform.CompileStatic
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin

@CompileStatic
final class UserMap {

    private static JavaPlugin plugin
    private static final Map<UUID, User> map = new HashMap<>()

    static void init() {
        ConfigurationSerialization.registerClass(User)
        plugin = Constants.getPlugin()
        final File players = new File(plugin.getDataFolder(), "players")
        if(!players.exists()) players.mkdirs()

        if(players.listFiles() == null) {
            for(final OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                map.put(op.getUniqueId(), new User(op))
            }
            return
        }

        for(final File file : players.listFiles()) {
            try {
                final FileConfiguration config = YamlConfiguration.loadConfiguration(file)
                final User user = config.get("data") as User
                map.put(user.uniqueId, user)
            } catch(final Exception ignored) {}
        }
    }

    static void cleanup() {
        plugin = null
        map.clear()
    }

    static User getAt(final UUID uuid) {
        return map.get(uuid)
    }

}
