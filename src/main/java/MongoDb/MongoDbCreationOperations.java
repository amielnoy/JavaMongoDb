package MongoDb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
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

    public static Document createMongoDocument(MongoClient mongoClient
            ,String collectionName
            ,String dbName
            ,String nameValue
            ,String ageValue
            ,String CityValue){
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> currentCollection=db.getCollection(collectionName);
        //Preparing a document
        Document document = new Document();
        document.append("name", nameValue);
        document.append("age", ageValue);
        document.append("city", CityValue);
        //Inserting the document into the collection
        db.getCollection(collectionName).insertOne(document);
        System.out.println("Document inserted successfully");
        return document;
    }
}
