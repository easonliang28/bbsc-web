<?xml version="1.0"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/timetable">

        <html>

        <head><title>Producenter</title></head>
        <body>
        <h1>
            <xsl:value-of select="name"/>
            <xsl:text>, due: </xsl:text>
            <xsl:value-of select="leavingTime"/>
        </h1>
        <ul>
        <xsl:for-each select="schedule">
            <li>
                <xsl:value-of select="place"/>
                <xsl:text>, </xsl:text>
                <xsl:value-of select="time"/>
                <xsl:text>, </xsl:text>
                <xsl:value-of select="Platform"/>
            </li>
        </xsl:for-each>
        </ul>
        </body>
        </html>
    </xsl:template>
</xsl:stylesheet>