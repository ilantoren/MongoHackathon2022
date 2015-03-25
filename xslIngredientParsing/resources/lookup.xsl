<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="/root">
        <list>
            <xsl:apply-templates select="row"/>
        </list>
    </xsl:template>
    <xsl:template match="row">
        <xsl:element name="item">
            <xsl:attribute name="id" select="@Heading1"/>
            <xsl:attribute name="value" select="@Heading2"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>