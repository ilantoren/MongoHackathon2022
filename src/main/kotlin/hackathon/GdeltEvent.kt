package hackathon

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Ktor uses this file to serialize the GDEVENT record to the database
 */
 data class GdeltEvent(
   @JsonProperty("GlobalEventId")
    var GlobalEventId: Long? = null,


   @JsonProperty("Day")
    var Day: Long? = null,

   @JsonProperty("MonthYear")
    var MonthYear: Long? = null,
    @JsonProperty("Year")
    var Year: Long? = null,
   @JsonProperty("FractionDate")
    var FractionDate: Double? = null,
      @JsonProperty("Actor1Code")
    var Actor1Code: String? = "",

         @JsonProperty("Actor1Name")
    var Actor1Name: String? = "",

      @JsonProperty("Actor1CountryCode")
    var Actor1CountryCode: String? = "",

      @JsonProperty("Actor1KnownGroupCode")
    var Actor1KnownGroupCode: String? = "",

   @JsonProperty("Actor1EthnicCode")
    var Actor1EthnicCode: String? = "",
    var Actor1Religion1Code: String? = "",
    var Actor1Religion2Code: String? = "",
    var Actor1Type1Code: String? = "",
    var Actor1Type2Code: String? = "",
    var Actor1Type3Code: String? = "",
      @JsonProperty("Actor2Code")
    var Actor2Code: String? = "",

   @JsonProperty("Actor2Name")
    var Actor2Name: String? = "",

      @JsonProperty("Actor2CountryCode")
    var Actor2CountryCode: String? = "",


    var Actor2KnownGroupCode: String? = "",
    var Actor2EthnicCode: String? = "",
    var Actor2Religion1Code: String? = "",
    var Actor2Religion2Code: String? = "",
    var Actor2Type1Code: String? = "",
    var Actor2Type2Code: String? = "",
    var Actor2Type3Code: String? = "",
    var IsRootEvent: Long? = null,
    var EventCode: String? = "",
    var EventBaseCode: String? = "",
    var EventRootCode: String? = "",
    var QuadClass: Long? = null,

      @JsonProperty("GoldsteinScale")
    var GoldsteinScale: Double? = null,

    var NumMentions: Long? = null,
    var NumSources: Long? = null,
    var NumArticles: Long? = null,

      @JsonProperty("AvgTone")
    var AvgTone: Double? = null,

    var Actor1Geo_Type: Long? = null,


    var Actor1Geo_Fullname: String? = "",
    var Actor1Geo_CountryCode: String? = "",
         @JsonProperty("Actor1Geo_ADM1Code")
    var Actor1Geo_ADM1Code: String? = "",
    var Actor1Geo_ADM2Code: String? = "",

      @JsonProperty("Actor1Geo_Lat")
    var Actor1Geo_Lat: Double? = null,

      @JsonProperty("Actor1Geo_Long")
    var Actor1Geo_Long: Double? = null,
    var Actor1Geo_FeatureID: String? = "",
    var Actor2Geo_Type: Long? = null,
    var Actor2Geo_Fullname: String? = "",

      @JsonProperty("Actor2Geo_CountryCode")
    var Actor2Geo_CountryCode: String? = "",


    var Actor2Geo_ADM1Code: String? = "",
    var Actor2Geo_ADM2Code: String? = "",

      @JsonProperty("Actor2Geo_Lat")
    var Actor2Geo_Lat: Double? = null,

      @JsonProperty("Actor2Geo_Long")
    var Actor2Geo_Long: Double? = null,
    var Actor2Geo_FeatureID: String? = "",
    var ActionGeo_Type: Long? = null,
    var ActionGeo_Fullname: String? = "",
    var ActionGeo_CountryCode: String? = "",
    var ActionGeo_ADM1Code: String? = "",
    var ActionGeo_ADM2Code: String? = "",

      @JsonProperty("ActionGeo_Lat")
    var ActionGeo_Lat: Double? = null,

         @JsonProperty("ActionGeo_Long")
    var ActionGeo_Long: Double? = null,


    var ActionGeo_FeatureID: String? = "",
      @JsonProperty("DATEADDED")
    var DATEADDED: Long? = 0,

      @JsonProperty("SOURCEURL")
    var SOURCEURL: String? =""
)