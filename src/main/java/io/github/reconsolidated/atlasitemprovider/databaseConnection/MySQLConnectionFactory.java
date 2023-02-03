package io.github.reconsolidated.atlasitemprovider.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionFactory implements ConnectionFactory {
    @Override
    public String getImplementationName() {
        return "MySQL";
    }

    @Override
    public Connection getConnection(String hostName, int port, String dbname, String userName, String password) throws SQLException {
        String connectionUrl = "jdbc:mysql://%s:%d/%s".formatted(hostName, port, dbname);

        return DriverManager.getConnection(connectionUrl, userName, password);
    }

    @Override
    public void init() {

    }
}
