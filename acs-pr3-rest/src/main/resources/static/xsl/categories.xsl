<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Categories</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #ddd; padding: 8px; }
                    th { background: #f2f2f2; }
                    .nav a { margin-right: 12px; }
                </style>
            </head>
            <body>

                <div class="nav">
                    <a href="/api/categories?format=xml">Categories (XML)</a>
                    <a href="/api/jewelry?format=xml">Jewelry (XML)</a>
                </div>

                <h2>Categories</h2>

                <table>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Links</th>
                    </tr>

                    <xsl:for-each select="categories/category">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>/api/categories/</xsl:text>
                                        <xsl:value-of select="id"/>
                                        <xsl:text>?format=xml</xsl:text>
                                    </xsl:attribute>
                                    <xsl:value-of select="name"/>
                                </a>
                            </td>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>/api/categories/</xsl:text>
                                        <xsl:value-of select="id"/>
                                        <xsl:text>/jewelry?format=xml</xsl:text>
                                    </xsl:attribute>
                                    jewelry
                                </a>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>