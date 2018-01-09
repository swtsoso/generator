<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${prop.mapperPackageName}.${table.tableName}Mapper" >
  <resultMap id="BaseResultMap" type="${prop.entityPackageName}.${table.tableName}" >
    <#-- 主键字段-->
	<#list  table.primaryKeyColumns as column>
	<id column="${column.actualColumnName}" property="${column.columnNameLower}" jdbcType="${column.jdbcTypeInformation.jdbcTypeName}" /> 
	</#list>
	<#--非主键列，非blob列-->
	<#list  table.baseColumns as column>
	<result column="${column.actualColumnName}" property="${column.columnNameLower}" jdbcType="${column.jdbcTypeInformation.jdbcTypeName}" /> 
	</#list>
  </resultMap>
  <resultMap id="ResultMapWithBLOBs" type="${prop.entityPackageName}.${table.tableName}" extends="BaseResultMap" >
    <#--blob列-->
	<#list  table.blobColumns as column>
	<result column="${column.actualColumnName}" property="${column.columnNameLower}" jdbcType="${column.jdbcTypeInformation.jdbcTypeName}" /> 
	</#list>
  </resultMap>
  <sql id="Base_Column_List" >
    <#-- 主键字段和普通字段 -->
	<#list  table.primaryKeyColumns as column>${column.actualColumnName}<#if column_has_next>,</#if></#list><#if (table.primaryKeyColumns?size>0)>,</#if><#list  table.baseColumns as column>${column.actualColumnName}<#if column_has_next>,</#if></#list>
  </sql>
  <#if (table.blobColumns?size > 0)>
  <sql id="Blob_Column_List" >
	<#-- blob字段 -->
    <#list  table.blobColumns as column>${column.actualColumnName}<#if column_has_next>,</#if></#list>
  </sql>
  </#if>
  <select id="get" resultMap="ResultMapWithBLOBs" parameterType="java.lang.Long" flushCache="false" useCache="true" >
    SELECT 
    <include refid="Base_Column_List" />
    <#if (table.blobColumns?size > 0)>
    ,
    <include refid="Blob_Column_List" />
    </#if>
    FROM ${table.actualTableName}
    WHERE 
	<#list  table.primaryKeyColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>id,jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next> AND </#if>
	</#list>
  </select>
  <select id="queryListByBiz" resultMap="BaseResultMap" parameterType="map" flushCache="false" useCache="true" >
    SELECT 
    <include refid="Base_Column_List" />
    <#if (table.blobColumns?size > 0)>
    ,
    <include refid="Blob_Column_List" />
    </#if>
     FROM ${table.actualTableName} WHERE 1=1 
    <include refid="condition" />
  </select>
  <delete id="delete" parameterType="java.lang.Long" flushCache="true" >
    DELETE FROM ${table.actualTableName}
    WHERE 
	<#list  table.primaryKeyColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>id,jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next> AND </#if>
	</#list>
  </delete>
  <delete id="deleteByPKSel" parameterType="${prop.entityPackageName}.${table.tableName}" flushCache="true" >
    DELETE FROM FROM ${table.actualTableName}
    WHERE 
	<#list  table.primaryKeyColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>id,jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next> AND </#if>
	</#list>
	<include refid="condition" />
  </delete>
  <insert id="insert" parameterType="${prop.entityPackageName}.${table.tableName}" flushCache="true" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO ${table.actualTableName} (
	<#-- 字段 -->
	<#list  table.allColumns as column>${column.actualColumnName}<#if column_has_next>,</#if></#list>
	)values(
	<#-- 字段 -->
	<#list  table.allColumns as column><#noparse>#{</#noparse>${column.columnNameLower}<#noparse>}</#noparse><#if column_has_next>,</#if></#list>
	)
  </insert>
  <insert id="insertSel" parameterType="${prop.entityPackageName}.${table.tableName}" flushCache="true" useGeneratedKeys="true" keyProperty="id" >
    INSERT INTO ${table.actualTableName}
    <trim prefix="(" suffix=")" suffixOverrides="," >
	  <#list table.allColumns as column>
		<if test="${column.columnNameLower}!=null">  ${column.actualColumnName} <#if column_has_next>,</#if> </if>
	  </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
	  <#list table.allColumns as column>
		<if test="${column.columnNameLower}!=null"> <#noparse>#{</#noparse>${column.columnNameLower},jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next>,</#if> </if>
	  </#list>
    </trim>
  </insert>
  <update id="updateByPKSel" parameterType="${prop.entityPackageName}.${table.tableName}" flushCache="true" >
    UPDATE ${table.actualTableName}
    <set >
      <#list table.allColumns as column>
		<if test="${column.columnNameLower}!=null">  ${column.actualColumnName} = <#noparse>#{</#noparse>${column.columnNameLower},jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next>,</#if> </if>
	  </#list>
    </set>
    WHERE 
	<#list  table.primaryKeyColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>id,jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next> AND </#if>
	</#list>
  </update>
  <update id="updateByPrimaryKeyWithBLOBs" parameterType="${prop.entityPackageName}.${table.tableName}" flushCache="true" >
    update ${table.actualTableName} set 
	<#--含blob列-->
	<#list  table.allColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>${column.columnNameLower}<#noparse>}</#noparse><#if column_has_next>,</#if>
	</#list>
	 where 
	<#-- 主键字段-->
	<#list  table.primaryKeyColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>id,jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next> AND </#if>
	</#list>
  </update>
  <update id="update" parameterType="${prop.entityPackageName}.${table.tableName}" flushCache="true" >
    update ${table.actualTableName} set 
	<#--非主键列，非blob列-->
	<#list  table.baseColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>${column.columnNameLower}<#noparse>}</#noparse><#if column_has_next>,</#if>
	</#list>
	 where 
	<#-- 主键字段-->
	<#list  table.primaryKeyColumns as column>
	${column.actualColumnName}=<#noparse>#{</#noparse>id,jdbcType=${column.jdbcTypeInformation.jdbcTypeName}} <#if column_has_next> AND </#if>
	</#list>
  </update>
  <sql id="condition" >
	 <#list table.baseColumns as column>
		<if test="${column.columnNameLower}!=null">AND  ${column.actualColumnName} = <#noparse>#{</#noparse>${column.columnNameLower},jdbcType=${column.jdbcTypeInformation.jdbcTypeName}}</if>
	  </#list>
  </sql>
  <!--已上为自动生成-->
  <!--新添加的写到下面-->
</mapper>