<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output media-type="xml/text"  indent="yes" method="xml"/>
    <xsl:key name="map-name" match="item" use="@id"/>
    <xsl:variable name="lookupDoc" select="document('map.xml')"/>
    <xsl:attribute-set name="nutrientGroup">
        <xsl:attribute name="protein">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="totalFat">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="calories">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="starch">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="sucrose">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="glucose">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="lactose">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="alcohol">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="adj_protein">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="caffeine">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="theobromine">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="sugars">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="fiber">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="calcium">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="iron">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="magnesium">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="sodium">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="vitA">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="vitD">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="vitC">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="monoFat">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="polyFat">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="transFat">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="satFat">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="cholesterol">
            <xsl:value-of select="0"/>
        </xsl:attribute>
        <xsl:attribute name="carbohydrate">
            <xsl:value-of select="0"/>
        </xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="/">
        <xsl:element name="data">
        <xsl:for-each-group select="/DATA/ROW" group-by="NDB_NO">
             <xsl:element name="ingredient" use-attribute-sets="nutrientGroup">
                 <xsl:attribute name="id"><xsl:value-of select="NDB_NO"/></xsl:attribute>
                 <xsl:attribute name="desc"><xsl:value-of select="Long_Desc"/></xsl:attribute>
                 <xsl:attribute name="foodGroup"><xsl:value-of select="FdGrp_Desc"/></xsl:attribute>
                 <xsl:attribute name="fgId"><xsl:value-of select="FdGrp_CD"/></xsl:attribute>
                 <xsl:attribute name="choFact"><xsl:value-of select=" CHO_Factor"/></xsl:attribute>
                 <xsl:attribute name="nfactor"><xsl:value-of select="N_Factor"/></xsl:attribute>
                 <xsl:attribute name="proFact"><xsl:value-of select="Pro_Factor"/></xsl:attribute>
                 <xsl:attribute name="fatFact"><xsl:value-of select="Fat_Factor"/></xsl:attribute>
                 <xsl:apply-templates select="current-group()" mode="group" /> 
             </xsl:element>
        </xsl:for-each-group>
       </xsl:element>
    </xsl:template>
    <xsl:template match="ROW" mode="group" >
        <xsl:variable name="myd" select="Nutr_No"/>
        <xsl:variable name = "altName"><xsl:value-of select="string($lookupDoc//item[@id = $myd]/@value)"/></xsl:variable>
            <xsl:if test="$myd = 205">
                <xsl:message select="concat( $myd , ' ', $altName, ' is  ' , Nutr_Val)"></xsl:message>
            </xsl:if>
        <xsl:attribute name="{$altName}"><xsl:value-of select="Nutr_Val"/></xsl:attribute>
    </xsl:template>
</xsl:stylesheet>