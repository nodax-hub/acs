<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Category</title>
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

                <h2>Category</h2>

                <div class="box">
                    <div><b>ID:</b> <xsl:value-of select="category/id"/></div>
                    <div><b>Name:</b> <xsl:value-of select="category/name"/></div>
                </div>

                <p>
                    <a>
                        <xsl:attribute name="href">
                            <xsl:text>/api/categories/</xsl:text>
                            <xsl:value-of select="category/id"/>
                            <xsl:text>/jewelry?format=xml</xsl:text>
                        </xsl:attribute>
                        View jewelry of this category
                    </a>
                </p>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>