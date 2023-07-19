package me.ryanmood.ryutils.spigot.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.ryanmood.ryutils.spigot.RyMessageUtils;
import me.ryanmood.ryutils.spigot.RySetup;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @param host     The IP/URL of the database.
     * @param database The name of the database.
     * @param username The username of the database.
     * @param password The password of the database.
     */
    public RyMongo(String host, String database, String username, String password) {
        this(host, 27017, database, username, password, true);
    }

    /**
     * Create a MongoDB Connection.
     *
     * @param host     The IP/URL of the database.
     * @param port     The port of the database.
     * @param database The name of the database.
     * @param username The username of the database.
     * @param password The password of the database.
     */
    public RyMongo(String host, int port, String database, String username, String password) {
        this(host, port, database, username, password, true);
    }

    /**
     * Create a MongoDB Connection.
     *
     * @param host     The IP/URL of the database.
     * @param port     The port of the database.
     * @param database The name of the database.
     * @param username The username of the database.
     * @param password The password of the database.
     * @param disableLogging Should MongoDB logger be disabled?
     */
    public RyMongo(String host, int port, String database, String username, String password, boolean disableLogging) {
        try {
            MongoClientSettings.Builder settings = MongoClientSettings.builder();
            settings.applyConnectionString(new ConnectionString("mongodb://" + host + ":" + port));
            settings.applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(30, TimeUnit.SECONDS));
            settings.retryWrites(true);
            settings.credential(MongoCredential.createCredential(username, database, password.toCharArray()));

            this.client = MongoClients.create(settings.build());
            this.database = this.client.getDatabase(Objects.requireNonNull(database));
            this.initCollections();
            this.connected = true;
        } catch (Exception exception) {
            RyMessageUtils.sendPluginError("Error occured while conecting to MongoDB.", exception, RySetup.isDebug(), true);
        }

        this.disableLogging = disableLogging;

        if (this.disableLogging) {
            final Logger mongoLogger = Logger.getLogger("org.mongodb.driver.cluster");
            mongoLogger.setLevel(Level.SEVERE);
        }
    }

    /**
     * Initialize the database's collections.
     */
    abstract void initCollections();

}
