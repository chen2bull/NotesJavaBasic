<?xml version="1.0" encoding="UTF-8"?>
<!-- 将XML文件转换成html -->
<xsl:stylesheet 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
   version="1.0">

   <xsl:output method="html"/>

   <xsl:template match="/staff">	<!-- match属性是一个XPath表达式 -->
      <table border="1"><xsl:apply-templates/></table>	<!-- 中间xsl:...代表整个/staff元素要替换成的"东西",
      														具体它要转换的形式是什么要根据下面的内容来决定 -->
   </xsl:template>

   <xsl:template match="/staff/employee">
      <tr><xsl:apply-templates/></tr>
   </xsl:template>

   <xsl:template match="/staff/employee/name">
      <td><xsl:apply-templates/></td>
   </xsl:template>

   <xsl:template match="/staff/employee/salary">
      <td>$<xsl:apply-templates/></td>
   </xsl:template>

   <xsl:template match="/staff/employee/hiredate">	<!-- 对于以下表达式,该模板会产生<td>year属性值-month属性值-day属性值-</td> -->
      <td><xsl:value-of select="@year"/>-<xsl:value-of 
      select="@month"/>-<xsl:value-of select="@day"/></td>
   </xsl:template>

</xsl:stylesheet>

