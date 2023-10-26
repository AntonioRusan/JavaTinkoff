package edu.hw2;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task3 {
    private final static Logger LOGGER = LogManager.getLogger();

    public interface Connection extends AutoCloseable {
        void execute(String command);

    }

    public record StableConnection() implements Connection {
        @Override public void execute(String command) {
            LOGGER.info("Stable connection executing command: " + command);
        }

        @Override public void close() throws ConnectionException {
            LOGGER.info("Closing stable connection...");
        }

    }

    public record FaultyConnection(double probabilityOfFault) implements Connection {
        @Override public void execute(String command) throws ConnectionException {
            if (new Random().nextDouble() < probabilityOfFault) {
                throw new ConnectionException("Faulty connection error!");
            } else {
                LOGGER.info("Faulty connection executing command: " + command);
            }
        }

        @Override public void close() throws ConnectionException {
            LOGGER.info("Closing faulty connection...");
        }

    }

    public interface ConnectionManager {
        Connection getConnection();
    }

    public record DefaultConnectionManager(double probabilityOfFault) implements ConnectionManager {
        @Override public Connection getConnection() {
            if (new Random().nextDouble() < probabilityOfFault) {
                return new FaultyConnection(probabilityOfFault);
            } else {
                return new StableConnection();
            }
        }
    }

    public record FaultyConnectionManager(double probabilityOfFault) implements ConnectionManager {
        @Override public Connection getConnection() {
            return new FaultyConnection(probabilityOfFault);
        }
    }

    public static class ConnectionException extends RuntimeException {
        public ConnectionException(String errorMessage) {
            super(errorMessage);
        }

        public ConnectionException(String errorMessage, Exception ex) {
            super(errorMessage, ex);
        }

    }

    public static final class PopularCommandExecutor {
        private final ConnectionManager manager;
        private final int maxAttempts;

        public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
            this.manager = manager;
            this.maxAttempts = maxAttempts;
        }

        public void updatePackages() throws ConnectionException {
            tryExecute("apt update && apt upgrade -y");
        }

        public void listAllFilesInDirectory() throws ConnectionException {
            tryExecute("ls -a");
        }

        void tryExecute(String command) throws ConnectionException {
            boolean hasConnection = false;
            int currentAttempt = 1;
            while (!hasConnection) {
                try (Connection currentConnection = manager.getConnection()) {
                    currentConnection.execute(command);
                    hasConnection = true;
                } catch (Exception ex) {
                    LOGGER.info("Cannot connect: attempt " + currentAttempt + " of " + maxAttempts);
                    if (currentAttempt == maxAttempts) {
                        throw new ConnectionException("Number of connection attempts exceeded!", ex);
                    } else {
                        currentAttempt++;
                    }
                }
            }
        }

    }
}
