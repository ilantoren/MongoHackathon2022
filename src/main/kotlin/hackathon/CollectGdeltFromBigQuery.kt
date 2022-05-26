package hackathon

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.cloud.bigquery.*
import org.bson.Document
import java.util.*


// [END bigquery_simple_app_deps]
object CollectGDeltEvents {
    @Throws(Exception::class)
    @JvmStatic
    fun call(lastId: Long): MutableList<GdeltEvent>{
        // [START bigquery_simple_app_client]

        val bigquery = BigQueryOptions.getDefaultInstance().service
        val queryConfig = QueryJobConfiguration.newBuilder(
            "select $fieldList from `gdelt-bq.gdeltv2.events`  where GLOBALEVENTID > $lastId limit 2000"
        ) // Use standard SQL syntax for queries.
            // See: https://cloud.google.com/bigquery/sql-reference/
            .setUseLegacySql(false)
            .build()

        // Create a job ID so that we can safely retry.
        val jobId = JobId.of(UUID.randomUUID().toString())
        var queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build())

        // Wait for the query to complete.
        queryJob = queryJob!!.waitFor()

        // Check for errors
        if (queryJob == null) {
            throw RuntimeException("Job no longer exists")
        } else if (queryJob.status.error != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw RuntimeException(queryJob.status.error.toString())
        }

        // Get the results.
        val result = queryJob.getQueryResults()


        //result.schema.fields.forEach { println(it) }
         val events = mutableListOf<GdeltEvent> ()
        for (row: FieldValueList in result.iterateAll()) {
            val gdelt = GdeltFromFieldValueList(row)
            gdelt?.let {
                events.add( it  )
            }

        }
        return events
    }

    private const val fieldList = """
    GlobalEventId,
    SQLDATE as Day,
    MonthYear,
    Year,
    FractionDate,
    Actor1Code,
    Actor1Name,
    Actor1CountryCode,
    Actor1KnownGroupCode,
    Actor1EthnicCode,
    Actor1Religion1Code,
    Actor1Religion2Code,
    Actor1Type1Code,
    Actor1Type2Code,
    Actor1Type3Code,
    Actor2Code,
    Actor2Name,
    Actor2CountryCode,
    Actor2KnownGroupCode,
    Actor2EthnicCode,
    Actor2Religion1Code,
    Actor2Religion2Code,
    Actor2Type1Code,
    Actor2Type2Code,
    Actor2Type3Code,
    IsRootEvent,
    EventCode,
    EventBaseCode,
    EventRootCode,
    QuadClass,
    GoldsteinScale,
    NumMentions,
    NumSources,
    NumArticles,
    AvgTone,
    Actor1Geo_Type,
    Actor1Geo_Fullname,
    Actor1Geo_CountryCode,
    Actor1Geo_ADM1Code,
    Actor1Geo_ADM2Code,
    Actor1Geo_Lat,
    Actor1Geo_Long,
    Actor1Geo_FeatureID,
    Actor2Geo_Type,
    Actor2Geo_Fullname,
    Actor2Geo_CountryCode,
    Actor2Geo_ADM1Code,
    Actor2Geo_ADM2Code,
    Actor2Geo_Lat,
    Actor2Geo_Long,
    Actor2Geo_FeatureID,
    ActionGeo_Type,
    ActionGeo_Fullname,
    ActionGeo_CountryCode,
    ActionGeo_ADM1Code,
    ActionGeo_ADM2Code,
    ActionGeo_Lat,
    ActionGeo_Long,
    ActionGeo_FeatureID,
    DATEADDED,
    SOURCEURL
   """

  
    fun GdeltFromFieldValueList(  fieldValueList: FieldValueList ): GdeltEvent?  {
        return GdeltEvent().apply{
            this::GlobalEventId.set( fieldValueList.get("GlobalEventId").longValue)
            this::Day.set( fieldValueList.get("Day").longValue)
            this::MonthYear.set( fieldValueList.get("MonthYear").longValue)
            this::Year.set( fieldValueList.get("Year").longValue)
            this::FractionDate.set(fieldValueList.get("FractionDate").doubleValue)
            
            fieldValueList.get("Actor1Code").value?.let {
                this::Actor1Code.set( it.toString() )
            }

            fieldValueList.get("Actor1Code").value?.let {
                this::Actor1Code.set( it.toString() )
            }

            fieldValueList.get("Actor1Name").value?.let {  this::Actor1Name.set(it.toString()) }
            fieldValueList.get("Actor1CountryCode").value?.let {  this::Actor1CountryCode.set(it.toString()) }
            fieldValueList.get("Actor1KnownGroupCode").value?.let {  this::Actor1KnownGroupCode.set(it.toString()) }
            fieldValueList.get("Actor1EthnicCode").value?.let {  this::Actor1EthnicCode.set(it.toString()) }
            fieldValueList.get("Actor1Religion1Code").value?.let {  this::Actor1Religion1Code.set(it.toString()) }
            fieldValueList.get("Actor1Religion2Code").value?.let {  this::Actor1Religion2Code.set(it.toString()) }
            fieldValueList.get("Actor1Type1Code").value?.let {  this::Actor1Type1Code.set(it.toString()) }
            fieldValueList.get("Actor1Type2Code").value?.let {  this::Actor1Type2Code.set(it.toString()) }
            fieldValueList.get("Actor1Type3Code").value?.let {  this::Actor1Type3Code.set(it.toString()) }
            fieldValueList.get("Actor2Code").value?.let {  this::Actor2Code.set(it.toString()) }
            fieldValueList.get("Actor2Name").value?.let {  this::Actor2Name.set(it.toString()) }
            fieldValueList.get("Actor2CountryCode").value?.let {  this::Actor2CountryCode.set(it.toString()) }
            fieldValueList.get("Actor2KnownGroupCode").value?.let {  this::Actor2KnownGroupCode.set(it.toString()) }
            fieldValueList.get("Actor2EthnicCode").value?.let {  this::Actor2EthnicCode.set(it.toString()) }
            fieldValueList.get("Actor2Religion1Code").value?.let {  this::Actor2Religion1Code.set(it.toString()) }
            fieldValueList.get("Actor2Religion2Code").value?.let {  this::Actor2Religion2Code.set(it.toString()) }
            fieldValueList.get("Actor2Type1Code").value?.let {  this::Actor2Type1Code.set(it.toString()) }
            fieldValueList.get("Actor2Type2Code").value?.let {  this::Actor2Type2Code.set(it.toString()) }
            fieldValueList.get("Actor2Type3Code").value?.let {  this::Actor2Type3Code.set(it.toString()) }
            fieldValueList.get("IsRootEvent").longValue.let{  this::IsRootEvent.set( it) }
            fieldValueList.get("EventCode").value?.let {  this::EventCode.set(it.toString()) }
            fieldValueList.get("EventBaseCode").value?.let {  this::EventBaseCode.set(it.toString()) }
            fieldValueList.get("EventRootCode").value?.let {  this::EventRootCode.set(it.toString()) }
            fieldValueList.get("QuadClass").longValue.let {  this::QuadClass.set(it  )}
            fieldValueList.get("GoldsteinScale").value?.let {  this::GoldsteinScale.set(fieldValueList.get("GoldsteinScale").doubleValue) }
            fieldValueList.get("NumMentions").value?.let {  this::NumMentions.set(   fieldValueList.get("NumMentions").longValue  ) }
            fieldValueList.get("NumSources").value?.let {  this::NumSources.set( fieldValueList.get("NumSources").longValue ) }
            fieldValueList.get("NumArticles").value?.let {  this::NumArticles.set( fieldValueList.get("NumArticles").longValue ) }
            fieldValueList.get("AvgTone").doubleValue.let {  this::AvgTone.set(it ) }
            fieldValueList.get("Actor1Geo_Type").longValue.let {  this::Actor1Geo_Type.set(it ) }
            fieldValueList.get("Actor1Geo_Fullname").value?.let {  this::Actor1Geo_Fullname.set(it.toString()) }
            fieldValueList.get("Actor1Geo_CountryCode").value?.let {  this::Actor1Geo_CountryCode.set(it.toString()) }
            fieldValueList.get("Actor1Geo_ADM1Code").value?.let {  this::Actor1Geo_ADM1Code.set(it.toString()) }
            fieldValueList.get("Actor1Geo_ADM2Code").value?.let {  this::Actor1Geo_ADM2Code.set(it.toString()) }
            fieldValueList.get("Actor1Geo_Lat").value?.let {  this::Actor1Geo_Lat.set( fieldValueList.get("Actor1Geo_Lat").doubleValue ) }
            fieldValueList.get("Actor1Geo_Long").value?.let {  this::Actor1Geo_Long.set(   fieldValueList.get("Actor1Geo_Long").doubleValue) }
            fieldValueList.get("Actor1Geo_FeatureID").value?.let {  this::Actor1Geo_FeatureID.set(it.toString()) }
           fieldValueList.get("Actor2Geo_Type").value?.let {  this::Actor2Geo_Type.set( fieldValueList.get("Actor2Geo_Type").longValue ) }
            fieldValueList.get("Actor2Geo_Fullname").value?.let {  this::Actor2Geo_Fullname.set(it.toString()) }
            fieldValueList.get("Actor2Geo_CountryCode").value?.let {  this::Actor2Geo_CountryCode.set(it.toString()) }
            fieldValueList.get("Actor2Geo_ADM1Code").value?.let {  this::Actor2Geo_ADM1Code.set(it.toString()) }
            fieldValueList.get("Actor2Geo_ADM2Code").value?.let {  this::Actor2Geo_ADM2Code.set(it.toString()) }
            fieldValueList.get("Actor2Geo_Lat").value?.let {  this::Actor2Geo_Lat.set(  fieldValueList.get("Actor2Geo_Lat").doubleValue ) }
            fieldValueList.get("Actor2Geo_Long").value?.let {  this::Actor2Geo_Long.set(  fieldValueList.get("Actor2Geo_Long").doubleValue) }
            fieldValueList.get("Actor2Geo_FeatureID").value?.let {  this::Actor2Geo_FeatureID.set(it.toString()) }
            fieldValueList.get("ActionGeo_Type").value?.let {  this::ActionGeo_Type.set(   fieldValueList.get("ActionGeo_Type").longValue     )  }
            fieldValueList.get("ActionGeo_Fullname").value?.let {  this::ActionGeo_Fullname.set(it.toString()) }
            fieldValueList.get("ActionGeo_CountryCode").value?.let {  this::ActionGeo_CountryCode.set(it.toString()) }
            fieldValueList.get("ActionGeo_ADM1Code").value?.let {  this::ActionGeo_ADM1Code.set(it.toString()) }
            fieldValueList.get("ActionGeo_ADM2Code").value?.let {  this::ActionGeo_ADM2Code.set(it.toString()) }
            fieldValueList.get("ActionGeo_Lat").value?.let {  this::ActionGeo_Lat.set( fieldValueList.get("ActionGeo_Lat").doubleValue ) }
            fieldValueList.get("ActionGeo_Long").value?.let {  this::ActionGeo_Long.set(fieldValueList.get("ActionGeo_Long").doubleValue ) }
            fieldValueList.get("ActionGeo_FeatureID").value?.let {  this::ActionGeo_FeatureID.set(it.toString()) }
            fieldValueList.get("DATEADDED").value.let {  this::DATEADDED.set(  fieldValueList.get("DATEADDED").longValue) }
            fieldValueList.get( "SOURCEURL").value?.let { this::SOURCEURL.set(it.toString())}
        
        }
    }
}

/**
 * {
"dataSource": "mongoworld",
"database": "mgd22",
"collection": "eventscsv",
"projection": {"id": 1, "Day":1, "GlobalEventId":1},
"sort": {"GlobalEventId": -1},
"limit": 3
}
 */
data class MongoRequest (
    val dataSource: String,
    val database: String,
    val collection: String,
    val projection: Document,
    val sort : Document,
    val limit:Int  = 10
)
data class MongoInsertMany (
    val dataSource: String,
    val database: String,
    val collection: String,
    val documents: List<GdeltEvent>
)


data class MongoResponse (
    @JsonProperty("_id")
    var id: String ="",
    var Day: Long = 0,
    var GlobalEventId: Int = Int.MAX_VALUE
)

/**
 *  Travel request for now just copies the properties
 *  of the MongoResponse
 */
data class BookMark (
    @JsonProperty("_id")
    val id: Long,
    val GlobalEventId: Long,
)



/**
 *  Data is returned as an array so a wrapper object
 *  aids in the deserialization from JSON to an array of
 *  Kotlin objects
 */
data class MongoResponseArray (
    val documents: List<MongoResponse>
)


