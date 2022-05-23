package hackathon


//@DefaultSchema(JavaFieldSchema::class)
 data class GdeltEvent(
    var GlobalEventId: Long? = null,
    var Day: Long? = null,
    var MonthYear: Long? = null,
    var Year: Long? = null,
    var FractionDate: Double? = null,
    var Actor1Code: String? = "",
    var Actor1Name: String? = "",
    var Actor1CountryCode: String? = "",
    var Actor1KnownGroupCode: String? = "",
    var Actor1EthnicCode: String? = "",
    var Actor1Religion1Code: String? = "",
    var Actor1Religion2Code: String? = "",
    var Actor1Type1Code: String? = "",
    var Actor1Type2Code: String? = "",
    var Actor1Type3Code: String? = "",
    var Actor2Code: String? = "",
    var Actor2Name: String? = "",
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
    var GoldsteinScale: Double? = null,
    var NumMentions: Long? = null,
    var NumSources: Long? = null,
    var NumArticles: Long? = null,
    var AvgTone: Double? = null,
    var Actor1Geo_Type: Long? = null,
    var Actor1Geo_Fullname: String? = "",
    var Actor1Geo_CountryCode: String? = "",
    var Actor1Geo_ADM1Code: String? = "",
    var Actor1Geo_ADM2Code: String? = "",
    var Actor1Geo_Lat: Double? = null,
    var Actor1Geo_Long: Double? = null,
    var Actor1Geo_FeatureID: String? = "",
    var Actor2Geo_Type: Long? = null,
    var Actor2Geo_Fullname: String? = "",
    var Actor2Geo_CountryCode: String? = "",
    var Actor2Geo_ADM1Code: String? = "",
    var Actor2Geo_ADM2Code: String? = "",
    var Actor2Geo_Lat: Double? = null,
    var Actor2Geo_Long: Double? = null,
    var Actor2Geo_FeatureID: String? = "",
    var ActionGeo_Type: Long? = null,
    var ActionGeo_Fullname: String? = "",
    var ActionGeo_CountryCode: String? = "",
    var ActionGeo_ADM1Code: String? = "",
    var ActionGeo_ADM2Code: String? = "",
    var ActionGeo_Lat: Double? = null,
    var ActionGeo_Long: Double? = null,
    var ActionGeo_FeatureID: String? = "",
    var DATEADDED: Long? = 0,
    var SOURCEURL: String? =""
)