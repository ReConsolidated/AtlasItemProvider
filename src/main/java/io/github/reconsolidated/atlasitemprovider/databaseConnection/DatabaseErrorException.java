package io.github.reconsolidated.atlasitemprovider.databaseConnection;

public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException(String message) {
        super(message);
    }
}
