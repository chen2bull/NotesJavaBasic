<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
   version="1.0">

   <xsl:output method="text"/>

   <xsl:template match="/staff/employee">
employee.<xsl:value-of select="position()"/>.name=<xsl:value-of select="name/text()"/>
			<!-- 该示例使用position函数来产生以其父节点的角度来看当前节点的位置.如果需要从文本转回xml,这些信息不会丢失-->
employee.<xsl:value-of select="position()"/>.salary=<xsl:value-of select="salary/text()"/>
employee.<xsl:value-of select="position()"/>.hiredate=<xsl:value-of select="hiredate/@year"/>-<xsl:value-of select="hiredate/@month"/>-<xsl:value-of select="hiredate/@day"/>
   </xsl:template>

</xsl:stylesheet>
