package MongoDb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbCreationOperations {
    public static void CreateDocumentInDb(MongoClient mongoClient){
        MongoDatabase db = mongoClient.getDatabase("PlayerDB");
        Document doc = new Document("playerName", "Ronaldo")
                .append("age", 25)
                .append("nationality", "Filipino")
                .append("JerseyNumber", 23)
                .append("position", "Guard");

        db.getCollection("playerInfo").insertOne(doc);
    }

    public static MongoDatabase getMongoDb(MongoClient mongoClient){
        MongoDatabase db = mongoClient.getDatabase("ExampleDb");
        return db;
    }
}
