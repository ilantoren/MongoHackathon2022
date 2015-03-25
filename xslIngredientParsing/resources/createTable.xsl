<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="1.0">
    <xsl:output media-type="xml/text"  indent="yes" method="xml"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Ingredients</title>
            </head>
            <div>
                <table>
                    <thead>
                        <xsl:apply-templates select="/data/ingredient[1]" mode="header" />
                    </thead>
                    <tbody>
                    <xsl:apply-templates select = "/data/ingredient" mode="body" />
               </tbody>
               </table>
            </div>
        </html>
    </xsl:template>
    <xsl:template mode="header" match="ingredient">
        <xsl:for-each select="@*" >
            <th><xsl:value-of select="name()" /></th>       
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="ingredient" mode="body">
        <row><xsl:for-each select = "@*">
            <td> 
                <xsl:value-of select="."/>
            </td>
        </xsl:for-each>
        </row>
    </xsl:template>
</xsl:stylesheet>