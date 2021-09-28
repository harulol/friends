package dev.hawu.plugins.friends

import dev.hawu.plugins.api.Constants
import dev.hawu.plugins.api.I18n
import dev.hawu.plugins.api.collections.tuples.Pair
import groovy.transform.CompileStatic
import org.bukkit.plugin.java.JavaPlugin

@CompileStatic
class FriendsPlugin extends JavaPlugin {

    private static I18n i18n

    @Override
    void onEnable() {
        Constants.setPlugin this
        i18n = new I18n()
    }

    static String tl(final String key, final Pair<?, ?>... pairs) {
        return i18n.tl(key, pairs)
    }

}
