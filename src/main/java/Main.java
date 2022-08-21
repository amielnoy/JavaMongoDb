import MongoDb.MongoBuilder;
import MongoDb.MongoDbCreationOperations;
import MongoDb.MongoQueryOperations;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static void main(String[] args) {
        System.out.println("Mongo db operations!");
        MongoBuilder mongoBuilder = new MongoBuilder();
        MongoQueryOperations currMongOperations =
                mongoBuilder.addMongoHost("127.0.0.1").connect();
        MongoClient mongoClient=MongoBuilder.getMongoClient();
        System.out.println("\nBefore Creating Example DB\n");
        MongoIterable<String> list = mongoClient.listDatabaseNames();
        for (String name : list) {
            System.out.println(name);
        }

        System.out.println("\nAfter Creating Example DB\n"+ LocalDateTime.now()+"\n");
        try {
            String dbName="ExampleMongoDb";
            String collectionName="StudentCollection";

            mongoClient.dropDatabase(dbName);
            ///////////////////////////////////////////////////////////
            //////////Create new DataBase:  StudentCollection//////////
            ///////////////////////////////////////////////////////////
            MongoDatabase exampleDb = mongoClient.getDatabase(dbName);
            ///////////////////////////////////////////////////////////
            //////////Create new collection: StudentCollection//////////
            ///////////////////////////////////////////////////////////
            exampleDb.createCollection(collectionName);

            ////////////////////////////////////////////////////////////
            //Create new Document under collection=StudentCollection////
            ////////////////////////////////////////////////////////////
            Document newDocument=
                    MongoDbCreationOperations.createMongoDocument(
                            mongoClient,
                            collectionName,
                            dbName,
                            "John",
                            "50",
                            "TelAviv"
                    );
            ///////////////////////////////////////////////
            //Print all the document key,values pairs//////
            ///////////////////////////////////////////////
            System.out.println("\n"+newDocument.entrySet());


            ///////////////////////////////////////////////
            ///1.Add new document//////////////////////////
            // 2.Find the Document/////////////////////////
            ///////////////////////////////////////////////
            Document newDocument2=
                    MongoDbCreationOperations.createMongoDocument(
                            mongoClient,
                            collectionName,
                            dbName,
                            "Haim"
                            ,"30"
                            ,"Haifa"
                    );
            System.out.println("\n"+newDocument2.entrySet());

            Document foundDocument=
                    exampleDb.getCollection(collectionName).find(eq("name", "Haim")).first();
            System.out.println("\nThe documend found by name=Haim:\n"+foundDocument.entrySet());

            ///////////////////////////////////////////////
            ///1.Add new document//////////////////////////
            // 2.Delete the Document/////////////////////////
            ///////////////////////////////////////////////
            Document newDocument3=
                    MongoDbCreationOperations.createMongoDocument(
                            mongoClient,
                            collectionName,
                            dbName,
                            "Miryam"
                            ,"70"
                            ,"Netanya"
                    );
            System.out.println("\n"+newDocument3.entrySet());

            foundDocument=
                    MongoQueryOperations.getMongoDocument(mongoClient,collectionName,dbName,"Netanya");

            System.out.println("\nThe documend found by city=Netanya:\n"+foundDocument.entrySet());
            DeleteResult deleteResult=exampleDb.getCollection(collectionName).deleteOne(eq("city", "Netanya"));
            System.out.println("\nThe number of documents deleted by city=Netanya:\n"+deleteResult.getDeletedCount());

            ///////////////////////////////////////////////
            ///1.Add new document//////////////////////////
            // 2.Update Document/////////////////////////
            ///////////////////////////////////////////////
            Document newDocument4=
                    MongoDbCreationOperations.createMongoDocument(
                            mongoClient,
                            collectionName,
                            dbName,
                            "Abraham"
                            ,"180"
                            ,"Betel"
                    );
            System.out.println("\n"+newDocument4.entrySet());

            foundDocument=
                    MongoQueryOperations.getMongoDocument(mongoClient,collectionName,dbName,"Betel");

            System.out.println("\nThe documend found by city=Betel:\n"+foundDocument.entrySet());

            Bson updates = Updates.combine(
                    Updates.set("city", "Updated City"),
                    Updates.set("age", "100"));
            UpdateResult updateResult =exampleDb.getCollection(collectionName).updateOne(foundDocument,updates);
            System.out.println("\nThe document UpdateResult after update operation:\n"+updateResult);
            ////////////////////////////////////////////////////////////////////////////////
            /////////////////Print all the documents in collection//////////////////////////
            ////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nAll the document after one document update:\n" );
            MongoCollection<Document> collection = exampleDb.getCollection(collectionName);
            //Retrieving the documents
            FindIterable<Document> iterDoc = collection.find();
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                System.out.println("\nThe document :\n" + it.next());
            }
        }catch (Exception e){
            System.out.println("Exception cause="+e.getCause());
        }

    }
}