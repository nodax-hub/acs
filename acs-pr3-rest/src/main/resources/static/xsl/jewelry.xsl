<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Jewelry</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .nav a { margin-right: 12px; }
                    .box { border: 1px solid #ddd; padding: 12px; border-radius: 8px; }
                </style>
            </head>
            <body>

                <div class="nav">
                    <a href="/api/categories?format=xml">Categories (XML)</a>
                    <a href="/api/jewelry?format=xml">Jewelry (XML)</a>
                </div>

                <h2>Jewelry</h2>

                <div class="box">
                    <div><b>ID:</b> <xsl:value-of select="jewelry/id"/></div>
                    <div><b>Title:</b> <xsl:value-of select="jewelry/title"/></div>
                    <div><b>Material:</b> <xsl:value-of select="jewelry/material"/></div>
                    <div><b>Price:</b> <xsl:value-of select="jewelry/price"/></div>

                    <div><b>Category:</b>
                        <xsl:choose>
                            <xsl:when test="jewelry/category/id">
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>/api/categories/</xsl:text>
                                        <xsl:value-of select="jewelry/category/id"/>
                                        <xsl:text>?format=xml</xsl:text>
                                    </xsl:attribute>
                                    <xsl:value-of select="jewelry/category/name"/>
                                </a>
                            </xsl:when>
                            <xsl:otherwise>
                                <i>category deleted</i>
                            </xsl:otherwise>
                        </xsl:choose>
                    </div>
                </div>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>