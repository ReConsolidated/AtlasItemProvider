package io.github.reconsolidated.atlasitemprovider.databaseConnection;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    String getImplementationName();

    Connection getConnection(String hostName, int port, String dbName, String userName, String password) throws SQLException;

    default Connection getConnection() {
        FileConfiguration config = AtlasItemProvider.plugin.getConfig();
        boolean dirty = false;
        if (!config.contains("db.hostname")) {
            config.set("db.hostname", "localhost");
            dirty = true;
        }
        if (!config.contains("db.port")) {
            config.set("db.port", 3306);
            dirty = true;
        }
        if (!config.contains("db.username")) {
            config.set("db.username", "root");
            dirty = true;
        }
        if (!config.contains("db.password")) {
            config.set("db.password", "password");
            dirty = true;
        }
        if (!config.contains("db.dbname")) {
            config.set("db.dbname", "kcdungeons");
            dirty = true;
        }
        if (dirty) {
            AtlasItemProvider.plugin.saveConfig();
        }

        try {
            return getConnection(
                    config.getString("db.hostname", "localhost"),
                    config.getInt("db.port", 3306),
                    config.getString("db.dbname", "kcdungeons"),
                    config.getString("db.username", "root"),
                    config.getString("db.password", "password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void init();
}
