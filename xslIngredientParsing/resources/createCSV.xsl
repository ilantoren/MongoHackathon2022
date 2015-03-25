<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="1.0">
    <xsl:output media-type="text/text"  method="text"/>
    <xsl:template match="/">
        
                        <xsl:apply-templates select="/data/ingredient[1]" mode="header" />
                    
                    <xsl:apply-templates select = "/data/ingredient" mode="body" />
               
    </xsl:template>
    <xsl:template mode="header" match="ingredient">
        <xsl:for-each select="@*" >
            <xsl:value-of select="concat('&quot;', name(),'&quot;', ',') " />     
        </xsl:for-each>
                <xsl:value-of disable-output-escaping="yes" select="concat('', '&#10;')" />
    </xsl:template>
    <xsl:template match="ingredient" mode="body">
        <xsl:for-each select = "@*">
                <xsl:value-of select="concat('&quot;', ., '&quot;' ,',')"/>
        </xsl:for-each>
                 <xsl:value-of disable-output-escaping="yes" select="concat('', '&#10;')" />
    </xsl:template>
</xsl:stylesheet>