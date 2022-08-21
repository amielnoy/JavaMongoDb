package MongoDb;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.bson.Document;
//import utils.AllureUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoQueryOperations {
    //private static final Logger LOGGER = LogManager.getLogger();
    private String username;
    private String password;
    private List<String> mongoHosts;
    private List<String> mongoPorts;
    private MongoClient mongoClient;
    protected final String SORTING_FILTER_NAME = "LastModifiedDateDisplay";


    public MongoQueryOperations(MongoClient mongoClient, String username,
                                String password, List<String> mongoHosts, List<String> mongoPorts) {
        this.mongoClient = mongoClient;
        this.username = username;
        this.password = password;
        this.mongoHosts = mongoHosts;
        this.mongoPorts = mongoPorts;
    }

    public FindIterable<Document> getMongoCollection(String database, String mongoCollection) {
        MongoDatabase databaseObj = mongoClient.getDatabase(database);
        for (String name : databaseObj.listCollectionNames()) {
            //AllureUtils.reportToLog4jAndAllure("name="+name, LOGGER);
        }


        FindIterable<Document> iterable = databaseObj.getCollection(mongoCollection).find();

        return iterable;

    }

    public Document getTopMongoDocBySortFilterNameAndvalue(String database, String collection
            , String sortFilterName, int sortFilterValue) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(collection)
                .find().sort(eq(sortFilterName, sortFilterValue)).limit(1);
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        Document currDoc = findIterable.first();
        return currDoc;
    }

    public Document getMongoTopDocumentByParamAndValue(String database, String colection
            , String FilterParamName, String FilterParamValue
            , String sortFilterName, int sortFilterValue) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(colection)
                .find(eq(FilterParamName, FilterParamValue)).sort(eq(sortFilterName, sortFilterValue)).limit(1);
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        Document currDoc = findIterable.first();
        return currDoc;
    }

    public Document getMongoTopDocumentByParamAndValue(String database, String colection
            , String FilterParamName, String FilterParamValue
            , String sortFilterName, String sortFilterValue) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(colection)
                .find(eq(FilterParamName, FilterParamValue)).sort(eq(sortFilterName, sortFilterValue)).limit(1);
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        Document currDoc = findIterable.first();
        return currDoc;
    }

    public FindIterable<Document> getMongoTopTenDocsCollectionUsingFindFilter(
            String database
            , String colection
            , String filterName
            , String filterValue) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(colection)
                .find(Filters.eq(filterName, filterValue)).sort(eq(SORTING_FILTER_NAME, +1)).limit(10);
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        FindIterable<Document> docCollection = findIterable;
        return docCollection;
    }

    public FindIterable<Document> getMongoTopTenDocsCollectionUsing2FindFilterNoSort(
            String database
            , String colection
            , String filterName1
            , String filterValue1
            , String filterName2
            , String filterValue2) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(colection)
                .find(Filters.and(Filters.eq(filterName1, filterValue1)
                        ,Filters.eq(filterName2, filterValue2)));
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        FindIterable<Document> docCollection = findIterable;
        return docCollection;
    }

    public Document getMongoTopTenDocsCollectionUsing2FindFilterSort(
            String database
            , String colection
            , String filterName1
            , String filterValue1
            , String filterName2
            , String filterValue2) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(colection)
                .find(Filters.and(Filters.eq(filterName1, filterValue1)
                        ,Filters.eq(filterName2, filterValue2))).sort(eq(SORTING_FILTER_NAME, -1)).limit(1);
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        FindIterable<Document> docCollection = findIterable;
        return docCollection.first();
    }

    public Document getMongoTopDocUsing2FindFilterNoSort(
            String database
            , String colection
            , String filterName1
            , String filterValue1
            , String filterName2
            , String filterValue2) {

        MongoDatabase databaseObj = mongoClient.getDatabase(database);

        FindIterable<Document> findIterable = databaseObj.getCollection(colection)
                .find(Filters.and(Filters.eq(filterName1, filterValue1)
                        ,Filters.eq(filterName2, filterValue2)));
        findIterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        Document currDoc = findIterable.first();
        return currDoc;
    }
    public static Document getMongoDocument(MongoClient mongoClient
            ,String collectionName
            ,String dbName
            ,String cityToSearch){
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> currentCollection=db.getCollection(collectionName);
        //Preparing a document
        Document foundDocument=
                (Document) currentCollection.find(eq("city",cityToSearch)).first();
        return foundDocument;
    }

    public static Document getMongoDocument(MongoClient mongoClient
            ,String collectionName
            ,String dbName
            ,String propertyToSearch
            ,String valueToSearch){
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> currentCollection=db.getCollection(collectionName);
        //Preparing a document
        Document foundDocument=
                (Document) currentCollection.find(eq(propertyToSearch,valueToSearch)).first();
        return foundDocument;
    }
//    public void getMongoCollectionAndDeleteLast(String database
//            , String colection
//            , String filterName
//            , String filterValue
//            ) throws ParseException {
//
//        MongoDatabase databaseObj = mongoClient.getDatabase(database);
//
//        MongoCollection myCollection = databaseObj.getCollection(colection);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
//        String date = LocalDate.now().toString();
//        Date startDate = simpleDateFormat.parse(date);
//        BasicDBObject query = new BasicDBObject();
//        query.put("DocumentDate", new BasicDBObject("$gte", startDate ));
//        query.put(filterName, filterValue);
//        myCollection.deleteOne(query);
//        mongoClient.close();
//    }

//    public void getMongoCollectionAndDeleteAllOfCurrentDate(String database
//            , String colection
//            , String filterName
//            , String filterValue
//    ) throws ParseException {
//
//        MongoDatabase databaseObj = mongoClient.getDatabase(database);
//
//        MongoCollection myCollection = databaseObj.getCollection(colection);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
//        String date = LocalDate.now().toString();
//        Date startDate = simpleDateFormat.parse(date);
//        BasicDBObject query = new BasicDBObject();
//        query.put("LastModifiedDateDisplay", new BasicDBObject("$gte", startDate ));
//        query.put(filterName, filterValue);
//        myCollection.deleteMany(query);
//        mongoClient.close();
//    }

//    public void getMongoCollectionAndDeleteAll(String database
//            , String colection
//            , String filterName
//            , String filterValue
//    ) throws ParseException {
//
//        MongoDatabase databaseObj = mongoClient.getDatabase(database);
//
//        MongoCollection myCollection = databaseObj.getCollection(colection);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
//        String date = LocalDate.now().toString();
//        Date startDate = simpleDateFormat.parse(date);
//        BasicDBObject query = new BasicDBObject();
//        //query.put("LastModifiedDateDisplay", new BasicDBObject("$gte", startDate ));
//        query.put(filterName, filterValue);
//        myCollection.deleteMany(query);
//        mongoClient.close();
//    }



    public FindIterable<Document> getEqualTo(FindIterable<Document> iterable, String keyName, String keyValue) {
        BasicDBObject getQuery = new BasicDBObject();
        getQuery.put(keyName, new BasicDBObject("$eq", keyValue));
        FindIterable<Document> iterable1 = iterable.filter(eq(keyName, keyValue)).limit(10);
        //AllureUtils.reportToLog4jAndAllure("\n\n\nAbout to print filtering according to " + keyName + "==" + keyValue,LOGGER);

        return iterable1;
    }
}
