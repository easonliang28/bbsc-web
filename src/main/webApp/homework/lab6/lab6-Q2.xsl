<?xml version="1.0"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/bookList">
        <table border="1">
            <thead>
                <tr>
                    <th>ISBN</th>
                    <th>title</th>
                    <th>author</th>
                    <th>publisher</th>
                    <th>price</th>
                </tr>
            </thead>
            <xsl:for-each select="book">
                <tr>
                    <td>
                        <span style="background-color:pink"><xsl:value-of select="@isbn"/></span>
                    </td>
                    <td>
                        <xsl:value-of select="title"/>
                    </td>
                    <td>
                        <xsl:value-of select="author"/>
                    </td>
                    <td>
                        <xsl:value-of select="publisher"/>
                    </td>
                    <td>
                        <xsl:if test="price &gt; 100">
                            <span style="color:red"><xsl:value-of select="price"/></span>
                        </xsl:if>
                        <xsl:if test="price &lt;= 100">
                            <span style="color:green"><xsl:value-of select="price"/></span>
                        </xsl:if>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>