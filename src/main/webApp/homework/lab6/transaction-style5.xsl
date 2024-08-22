<?xml version="1.0"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/dailyTransaction">
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Mobile</th>
                    <th>Staff Id</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <xsl:for-each select="person">
                <tr>
                    <td>
                        <xsl:value-of select="firstName"/>
                        <xsl:text> </xsl:text>
                        <xsl:value-of select="lastName"/>
                    </td>
                    <xsl:choose>
                        <xsl:when test="mobile = ''">
                            <td bgcolor="#ffe6e6"></td>
                        </xsl:when>
                        <xsl:otherwise>
                            <td>
                                <xsl:value-of select="mobile"/>
                            </td>
                        </xsl:otherwise>
                    </xsl:choose>

                    <td>
                        <xsl:value-of select="@staffDbId"/>
                    </td>
                    <td>
                        <xsl:value-of select="@operation"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>