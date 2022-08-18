package org.example;

import MongoDb.MongoBuilder;
import MongoDb.MongoDbCreationOperations;
import MongoDb.MongoQueryOperations;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

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

        System.out.println("\nAfter Creating Example DB\n");
        try {
            String dbName="ExampleMongoDbCreated";
            mongoClient.dropDatabase("ExampleDb");
            mongoClient.dropDatabase(dbName);

            MongoDatabase exampleDb = mongoClient.getDatabase(dbName);
            exampleDb.createCollection("ExampleCollection");

            list = mongoClient.listDatabaseNames();
            for (String name : list) {
                System.out.println(name);
            }

        }catch (Exception e){
            System.out.println("Exception cause="+e.getCause());
        }

    }
}