<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">

	
	<xsl:output media-type="xml/text" indent="yes" method="xml"/>
	<xsl:variable name="lookupDoc" select="document('mergeData.xml')"/>
	<xsl:key name="map-name" match="*" use=".."/>
	
	<xsl:template match="/document">
		<xsl:element name="document">
			<xsl:for-each select="recipe">
				<xsl:element name="recipe">
					<xsl:variable name="recipeId" select="@id" />
					
					<xsl:apply-templates select="steps">
						<xsl:with-param name="recipeId" select="$recipeId"/>
					</xsl:apply-templates>
					
					<xsl:apply-templates select="id|title|contributor|website|servings|ratings|photos"/>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	
	
	<xsl:template match="steps">
		<xsl:param name="recipeId"/>
		<xsl:element name="steps">
			<xsl:apply-templates>
				<xsl:with-param name="recipeId" select="$recipeId"/>
			</xsl:apply-templates>
			
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="lines">
		<xsl:param name="recipeId"></xsl:param>
		<xsl:variable name="lookupKey" select="concat($recipeId, '-',  ndb)"/>
		
		<xsl:element name="lines">
		<xsl:attribute name="id">
				<xsl:value-of select="$lookupKey"/>
			</xsl:attribute>
			<xsl:for-each select="*">
				<xsl:copy-of select="."/>
			</xsl:for-each>
				
			
			
				<xsl:message>
					<xsl:value-of select="$lookupKey"/>
				</xsl:message>
				<xsl:apply-templates mode="merge" select="//row[key/text() = $lookupKey]/*"/>
				
				
				
		</xsl:element>

		
	  
	
	</xsl:template>
	
	
	<xsl:template match="*">
		<xsl:copy-of select="."/>
	</xsl:template>
<xsl:template match="ndb"  mode="merge">
</xsl:template>
<xsl:template match="*" mode="merge">
		<xsl:copy-of select="."/>
	</xsl:template>
</xsl:stylesheet>
    