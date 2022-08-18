package MongoDb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import utils.AllureUtils;

import java.util.ArrayList;
import java.util.List;

public class MongoBuilder {
    private static final String MONGO_PORT = "27017";
    //private static final Logger LOGGER = LogManager.getLogger();
    private List<String> mongoHosts = new ArrayList<String>();
    private List<String> mongoPorts = new ArrayList<String>();
    private String username;
    private String password;
    private String suffix;
    private static MongoClient mongoClient;


    public MongoBuilder addMongoHost(String mongoHost) {
        mongoHosts.add(mongoHost);
        mongoPorts.add(MONGO_PORT);
        return this;
    }

    public MongoBuilder addMongoHost(String mongoHost, String mongoPort) {
        mongoHosts.add(mongoHost);
        mongoPorts.add(mongoPort);
        return this;
    }

    public MongoBuilder setUserName(String username) {
        this.username = username;
        return this;
    }

    public MongoBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public MongoBuilder setConnectionStringSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public MongoQueryOperations connect() {
        String connectionString;

        if(username!=null)
            connectionString = "mongodb://" + username + ":" + password + "@";
        else
            connectionString="mongodb://";

        for (int i = 0; i < mongoHosts.size(); i++) {
            connectionString += mongoHosts.get(i) + ":" + mongoPorts.get(i);
            if (i < mongoHosts.size() - 1) {
                connectionString += ",";
            }
        }
        if(suffix==null)
            suffix="";
        MongoClientURI uri = new MongoClientURI(connectionString + suffix);

        mongoClient = new MongoClient(uri);
        MongoQueryOperations mongoOperations = new MongoQueryOperations(mongoClient, username, password, mongoHosts, mongoPorts);
        //AllureUtils.reportToLog4jAndAllure("Connected to the database successfully", LOGGER);
        return mongoOperations;
    }

    public static MongoClient getMongoClient(){
        return mongoClient;
    }
}
