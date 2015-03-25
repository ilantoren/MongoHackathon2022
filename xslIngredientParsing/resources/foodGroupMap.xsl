<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output  method="xml" indent="yes"/>
    <xsl:template match="/root">
        <xsl:element name="map">
        <xsl:for-each select="row">
            <xsl:element name="item">
                <xsl:attribute name="id"><xsl:value-of select="FdGrp_CD"/></xsl:attribute>
                <xsl:attribute name="name"><xsl:value-of select="mapsTo"/></xsl:attribute>
                <xsl:attribute name="grpId"><xsl:value-of select="code"/></xsl:attribute>
            </xsl:element>
        </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>