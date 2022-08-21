Installation(windows):

Install mongo localy use default port 27017
mongo url= mongodb://localhost:27017

Install Mongo shell
download both from:
https://www.mongodb.com/try/download/community

Shell Commands:

show all mongo server databases:
>>>show dbs
> 
switch to specific db context 
>>>use <dbname> 

find all documents in collection:
>>>db.StudentCollection.find({})

Mongo db hirarchy:
DB
   collection1,collection2,..collectionN(per DB)
                                        Document1,Document2,...DocumentN(Per Collection)       