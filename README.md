# MongoHackathon2022
[What is all this?](https://hackathon-lmiij.mongodbstitch.com/)
  Using data analysis on GDELT data

## How to obtain the GDELT event data
* Use the gdettools script  to load as zip files from the GDELT
* Use this jar file to load directly from bigquery then use the Mongodb Data api to load to atlas

# Structure of Kotlin Code
- First create an .env file to control access to the Atlas Cluster via the Data Api 

```
mongo-api-key=YOUR API KEY HERE
mongo_database=mdg22
mongo_source_collection=eventscsvmongo_database=mdg22
mongo_source_collection=eventscsv
mongo_datasource=mongoworld

```
## What the code does:
- Connects to the collection with the events and find the last GlobalEventId added
- Calls BigQuery to retrieve a set of events with a greater GlobalEventId (they increase with time)
- Reads from the results and converts into an array of  data class 
- Using a Kotlin ***data class*** and Ktor  send a insert_many request via the Data Api
- A Trigger in Atlas responds to the insert by adding calculated fields that such as converting Long and Lat to valid GeoJson 

# Loading directly from BigQuery and loading to Atlas with the [Atlas Data Api](https://www.mongodb.com/docs/atlas/api/data-api)

# Using R and ggplot with Atlas and GDELT 
On the Atlas side the Data Api can be turned on very simply after the standard creation of an authentication method
![](images/Advanced Rest Client.png)
### Using the Advanced Rest Client to pull data with an HTTP POST 


## Calling BigQuery from within Kotlin code
```
        val bigquery = BigQueryOptions.getDefaultInstance().service
        val queryConfig = QueryJobConfiguration.newBuilder(
            "select $fieldList from `gdelt-bq.gdeltv2.events`  where GLOBALEVENTID > $lastId limit 2000"
        ) // Use standard SQL syntax for queries.
            // See: https://cloud.google.com/bigquery/sql-reference/
            .setUseLegacySql(false)
            .build()

```
   **Field list matches a Kotlin data class limit is obtained from  Atlas  with the Data Api using Ktor**

There is a basic setup to use Google Cloud,  you have to enable a service to your account, so for this you will need the
Cloud Scheduler, PubSub, and BigQuery
I have a more detailed description at [Mongo's Data API is Cloud Ready](https://medium.com/@mongo-loves-data/mongos-data-api-is-cloud-ready-9d0537841ead)
To upload the built code you need to run the "build" task in  Gradlew (./gradlew build) and then
from you need to remove the 'MongoHackathon2022-1.0-SNAPSHOT-all.jar' file then run the deploy.sh 

```#!/usr/bin/env zsh
#   Deploying to GCP
#      This script uploads jar for Cloud build then creates a  PubSub taoke that can be linked to
#      the Cloud scheduler

cd /Users/freda/Downloads/MongoHackathon2022
gcloud functions deploy fn-gdelt-update  --entry-point hackathon.PubSub --runtime java11 --memory 512MB --trigger-topic hackathon --source build/libs

```



## Reshaping data - Tying an update by the Data Api to a Realm Function that reshapes the GDELT event documents

```
exports = function(changeEvent) {
  

/*
    Access a mongodb service:  Then use the aggregation to reshape the data for this collection
    
*/

    const aggregation = [
      {$match: {
        points: {
  $exists: 0
 }
}}, {$addFields: {
 actorCodes: {
  $concatArrays: [
   [
    '$Actor1Code'
   ],
   [
    '$Actor2Code'
   ]
  ]
 },
 geoCodes: {
  $concatArrays: [
   [
    [
     '$ActionGeo_Long',
     '$ActionGeo_Lat'
    ]
   ],
   [
    [
     '$Actor1Geo_Long',
     '$Actor1Geo_Lat'
    ]
   ],
   [
    [
     '$Actor2Geo_Long',
     '$Actor2Geo_Lat'
    ]
   ]
  ]
 }
}}, {$addFields: {
 geoCodes: {
  $map: {
   input: '$geoCodes',
   as: 'a',
   'in': {
    $map: {
     input: '$$a',
     as: 'b',
     'in': {
      $convert: {
       input: '$$b',
       to: 'double',
       onError: ''
      }
     }
    }
   }
  }
 }
}}, {$addFields: {
 geoCodes: {
  $filter: {
   input: '$geoCodes',
   as: 'b',
   cond: {
    $and: [
     {
      $ne: [
       {
        $arrayElemAt: [
         '$$b',
         0
        ]
       },
       ''
      ]
     },
     {
      $ne: [
       {
        $arrayElemAt: [
         '$$b',
         1
        ]
       },
       ''
      ]
     }
    ]
   }
  }
 },
 news: {
  $regexFind: {
   input: '$sourceurl',
   regex: '//([^/]+)'
  }
 }
}}, {$addFields: {
 points: {
  $map: {
   input: '$geoCodes',
   'in': {
    geometry: {
     type: 'Point',
     coordinates: '$$this'
    }
   }
  }
 },
 news: {
  $arrayElemAt: [
   '$news.captures',
   0
  ]
 }
}}, {$project: {
 geoCodes: 0
}}, {$merge: {
 into: 'eventscsv',
 on: '_id',
 whenMatched: 'replace',
 whenNotMatched: 'discard'
}}]
    const collection = context.services.get("mongoworld").db("mgd22").collection("eventscsv");
    const response =  collection.aggregate( aggregation );

```


# Creating ggplot2 plots from RStudio 

## Loading the data from Atlas:
```
       library( dply)
         library( ggplot2)
         library( mongolite)
         hackDB = mongo( collection="eventscsv", db="mgd22", url=mongoworld)
        Where mongoworld = mongodb+srv://yyyy:xxxxxxxxx@mongoworld.rgeiy.mongodb.net/test?retryWrites=true&w=majority

```
---
## Relationship between ***AvgTone*** and the ***GoldsteinScale***
![](images/AvgTone v GoldsteinScale  scatter with line.jpeg)
```
mongodata %>%  ggplot( aes( x=GoldsteinScale, y=AvgTone, color=AvgTone))  + geom_point()  + scale_color_gradient(low="blue", high="red") +labs(title="AvgTone v GoldsteinScale with trend line") + stat_smooth( method="lm", col="red")
```
---


## Goldstein Scale is a measure that is correlated to the EventRootCode
![](images/GoldsteinScale by EventRootCode.jpeg)
```
mongodata %>% group_by( GoldsteinScale) %>% ggplot( aes( x=EventRootCode, y=GoldsteinScale, fill=EventRootCode)) + geom_boxplot() + labs(title="GoldsteinScale by EventRootCode")
```

---
# Countries are not all the same (die of non-surprise)

![](images/eventcode_count_country.jpeg)

---
## Another neat ggplot2 
![](images/avgtone_goldsteinscale_actorcode.jpeg)

```mongodata %>%  ggplot( aes( x=GoldsteinScale, y=AvgTone)) + geom_density2d_filled() +ylim(-10,10) + labs(title="AvgTone, GoldsteinScale as an index of conflict and happiness",subtitle = "Country codes aus,fin,fra,irn,isr,ita,jew,jpn,rus,swz,tur,ukr") + facet_wrap(~Actor1Code, ncol=4)```


