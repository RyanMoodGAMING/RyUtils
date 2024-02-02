package me.ryanmood.ryutils.bungee.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.ryanmood.ryutils.bungee.RyMessageUtils;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class RyMongo {

    private boolean disableLogging;
    @Getter
    private boolean connected;

    private MongoClient client;
    @Getter
    private MongoDatabase database;

    /**
     * Create a MongoDB Connection.
     *
     * @param address  The IP/URL of the database.
     * @param database The name of the database.
     * @param username The username of the database.
     * @param password The password of the database.
     */
    public RyMongo(String address, String database, String username, String password) {
        this(address, 27017, database, username, password, true, true, true);
    }

    /**
     * Create a MongoDB Connection.
     *
     * @param address  The IP/URL of the database.
     * @param port     The port of the database.
     * @param database The name of the database.
     * @param username The username of the database.
     * @param password The password of the database.
     * @param auth     Is auth database required?
     */
    public RyMongo(String address, int port, String database, String username, String password, boolean auth) {
        this(address, port, database, username, password, auth, true, true);
    }

    /**
     * Create a MongoDB Connection.
     *
     * @param address  The IP/URL of the database.
     * @param port     The port of the database.
     * @param database The name of the database.
     * @param username The username of the database.
     * @param password The password of the database.
     * @param auth     Is auth database required?
     * @param ssl      Does SSL need to be used?
     * @param disableLogging Should MongoDB logger be disabled?
     */
    public RyMongo(String address, int port, String database, String username, String password, boolean auth, boolean ssl, boolean disableLogging) {
        StringBuilder uriBuilder = new StringBuilder("mongodb://");
        uriBuilder.append(address).append(":").append(port);

        if (auth) {
            uriBuilder.append("/?authSource=").append(database).append("&retryWrites=true");
        }

        if (ssl) {
            uriBuilder.append("&ssl=true");
        }

        ConnectionString connectionString = new ConnectionString(uriBuilder.toString());

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        builder.credential(credential);

        this.connect(builder, connectionString.getDatabase(), disableLogging);
    }

    /**
     * Create a MongoDB Connection
     *
     * @param uri            The connection URI.
     * @param disableLogging Should MongoDB logger be disabled?
     */
    public RyMongo(String uri, boolean disableLogging) {
        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        this.connect(builder, connectionString.getDatabase(), disableLogging);
    }

    /**
     * Create a MongoDB Connection.
     *
     * @param uri            The connection URI.
     * @param database       The name of the database.
     * @param username       The username of the user.
     * @param password       The password of the user.
     * @param disableLogging Should MongoDB logger be disabled?
     */
    public RyMongo(String uri, String database, String username, String password, boolean disableLogging) {
        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        builder.credential(credential);

        this.connect(builder, connectionString.getDatabase(), disableLogging);
    }

    /**
     * Initialize the database's collections.
     */
    protected abstract void initCollections();


    /**
     * Connect to a MongoDB Database.
     *
     * @param builder        The settings builder.
     * @param database       Name of the database.
     * @param disableLogging Should logs be sent?
     */
    private void connect(MongoClientSettings.Builder builder, @Nullable String database, boolean disableLogging) {
        MongoClientSettings settings = builder.build();

        this.client = MongoClients.create(settings);

        String databaseName = database;
        if (databaseName == null) {
            databaseName = "RyUtils";
        }

        this.database = this.client.getDatabase(databaseName);

        this.initCollections();

        try {
            this.client.listDatabaseNames().first();
            this.connected = true;
        } catch (Exception ignored) {
            RyMessageUtils.sendPluginError("MongoDB failed to connect to database " + databaseName);
            this.connected = false;
        }

        this.disableLogging = disableLogging;

        if (this.disableLogging) {
            final Logger mongoLogger = Logger.getLogger("org.mongodb.driver.cluster");
            mongoLogger.setLevel(Level.SEVERE);
        }
    }
}
