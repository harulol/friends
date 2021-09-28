package dev.hawu.plugins.friends

import groovy.transform.CompileStatic
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player

import java.util.stream.Collectors

@CompileStatic
final class User implements ConfigurationSerializable {

    private final UUID uuid
    private final Set<UUID> friends

    private Privacy messagesPrivacy = Privacy.FRIENDS
    private Privacy requestsPrivacy = Privacy.ANY

    static User deserialize(final Map<String, Object> map) {
        final UUID uuid = UUID.fromString(map.get("uuid").toString())
        final Set<UUID> friends = (map.get("friends") as List<String>).stream().map { UUID.fromString(it) }.collect(Collectors.toSet())
        final User user = new User(uuid)
        user.getFriends().addAll(friends)
        user.setMessagesPrivacy(Privacy.fromID(Integer.parseInt(map.get("messagesPrivacy").toString())))
        user.setRequestsPrivacy(Privacy.fromID(Integer.parseInt(map.get("requestsPrivacy").toString())))
        user
    }

    User(UUID base) {
        uuid = base
        friends = new HashSet<>()
    }

    User(OfflinePlayer base) {
        this(base.getUniqueId())
    }

    Set<UUID> getFriends() {
        friends
    }

    String getName() {
        getOfflinePlayer().getName()
    }

    UUID getUniqueId() {
        uuid
    }

    Privacy getMessagesPrivacy() {
        messagesPrivacy
    }

    void setMessagesPrivacy(final Privacy messages) {
        this.messagesPrivacy = messages
    }

    Privacy getRequestsPrivacy() {
        requestsPrivacy
    }

    void setRequestsPrivacy(final Privacy requests) {
        this.requestsPrivacy = requests
    }

    OfflinePlayer getOfflinePlayer() {
        Bukkit.getOfflinePlayer(uuid)
    }

    Player getPlayer() {
        Bukkit.getPlayer(uuid)
    }

    Map<String, Object> serialize() {
        final Map<String, Object> map = new LinkedHashMap<>()
        map.put("uuid", uuid.toString())
        map.put("friends", friends.stream().map { it.toString() }.collect(Collectors.toList()))
        map.put("messagesPrivacy", messagesPrivacy.ordinal())
        map.put("requestsPrivacy", requestsPrivacy.ordinal())
        return map
    }

    int hashCode() {
        uuid.hashCode()
    }

    String toString() {
        "User{uuid=${uuid.toString()}}".toString()
    }

    boolean equals(final Object other) {
        other instanceof User && other.uuid == uuid
    }

}
