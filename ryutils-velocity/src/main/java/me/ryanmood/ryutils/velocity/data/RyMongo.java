package me.ryanmood.ryutils.velocity.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.AccessLevel;
import lombok.Getter;
import me.ryanmood.ryutils.base.data.RyMongoBase;
import me.ryanmood.ryutils.velocity.RyMessageUtil;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public abstract class RyMongo implements RyMongoBase {

    private boolean disableLogging;
    private boolean connected;

    @Getter(AccessLevel.PRIVATE)
    private MongoClient client;
    private MongoDatabase database;

    public RyMongo(String address, String database, String username, String password) {
        this(address, 27017, database, username, password, true, true, true);
    }

    public RyMongo(String address, int port, String database, String username, String password, boolean auth) {
        this(address, port, database, username, password, auth, true, true);
    }

    public RyMongo(String address, int port, String database, String username, String password, boolean auth, boolean ssl, boolean disableLogging) {
        StringBuilder uriBuilder = new StringBuilder("mongodb://");
        uriBuilder.append(address).append(":").append(port);

        if (auth) uriBuilder.append(database).append("&retryWrites=true");
        if (ssl) uriBuilder.append("&ssl=true");

        ConnectionString connectionString = new ConnectionString(uriBuilder.toString());

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        builder.credential(credential);

        String databaseName = connectionString.getDatabase();
        if (databaseName == null) databaseName = database;

        this.connect(builder, databaseName, disableLogging);
    }

    public RyMongo(String uri, String database, boolean disableLogging) {
        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        this.connect(builder, database, disableLogging);
    }

    public RyMongo(String uri, boolean disableLogging) {
        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        String databaseName = connectionString.getDatabase();
        if (databaseName == null || databaseName.isEmpty()) databaseName = "RyMongo";

        this.connect(builder, databaseName, disableLogging);
    }

    public RyMongo(String uri, String database, String username, String password, boolean disableLogging) {
        ConnectionString connectionString = new ConnectionString(uri);

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToConnectionPoolSettings(connection -> connection.maxConnectionIdleTime(30, TimeUnit.SECONDS));

        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        builder.credential(credential);

        String databaseName = connectionString.getDatabase();
        if (databaseName == null || databaseName.isEmpty()) databaseName = database;

        this.connect(builder, databaseName, disableLogging);
    }

    /**
     * Initialize the database's collections.
     */
    @Override
    public abstract void initCollections();

    private void connect(MongoClientSettings.Builder builder, String database, boolean disableLogging) {
        MongoClientSettings settings = builder.build();
        this.client = MongoClients.create(settings);

        if (database == null || database.isEmpty()) database = "RyMongo";
        this.database = this.client.getDatabase(database);
        this.initCollections();

        try {
            this.client.listDatabases().first();
            this.connected = true;
        } catch (Exception exception) {
            RyMessageUtil.getUtil().sendError("MongoDB failed to connect to database " + database);
            this.connected = false;
        }

        this.disableLogging = disableLogging;

        if (this.isDisableLogging()) {
            final Logger mongoLogger = Logger.getLogger("org.mongodb.driver.cluster");
            mongoLogger.setLevel(Level.SEVERE);
        }
    }

}
