package org.example;

import MongoDb.MongoBuilder;
import MongoDb.MongoDbCreationOperations;
import MongoDb.MongoQueryOperations;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.time.LocalDateTime;

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
            String dbName="ExampleMongoDbCreated";
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

//            list = mongoClient.listDatabaseNames();
//            for (String name : list) {
//                System.out.println(name);
//            }
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
        }catch (Exception e){
            System.out.println("Exception cause="+e.getCause());
        }

    }
}