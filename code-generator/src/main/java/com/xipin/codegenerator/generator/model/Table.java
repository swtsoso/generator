package com.xipin.codegenerator.generator.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl.JdbcTypeInformation;

public class Table {
	
	public Table(){
		
	}
	
	/**
	 * 数据库中保存的表名
	 */
	private String actualTableName;
	
	/**
	 * 转换后的表名
	 */
	private String tableName;
	
	/**
	 * 表名首字母小写
	 */
	public String getTableNameLower(){
		return tableName.substring(0,1).toLowerCase() + tableName.substring(1);
	}

	
	/**
	 * 注释
	 */
	private String remarks;
	
	private String catalog;
	
	private String schema;
	
	
	/**
	 * 非主键列，非blob列
	 */
	private List<Column> baseColumns = new ArrayList<Column>();
	
	/**
	 * 主键列
	 */
	private List<Column> primaryKeyColumns = new ArrayList<Column>();
	
	/**
	 * blob列
	 */
	private List<Column> blobColumns = new ArrayList<Column>();
	
	/**
	 * blob列
	 */
	public List<Column> getBlobColumns() {
		return blobColumns;
	}
	/**
	 * blob列
	 */
	public void setBlobColumns(List<Column> blobColumns) {
		this.blobColumns = blobColumns;
	}

	/**
	 * 数据库中保存的表名
	 */
	public String getActualTableName() {
		return actualTableName;
	}
	/**
	 * 数据库中保存的表名
	 */
	public void setActualTableName(String actualTableName) {
		this.actualTableName = actualTableName;
	}
	/**
	 * 转换后的表名
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * 转换后的表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 注释
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 注释
	 */
	public void setRemarks(String remark) {
		this.remarks = remark;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String caltaLog) {
		this.catalog = caltaLog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	/**
	 * 非主键列，非blob列
	 */
	public List<Column> getBaseColumns() {
		return baseColumns;
	}
	/**
	 * 非主键列，非blob列
	 */
	public void setBaseColumns(List<Column> baseColumns) {
		this.baseColumns = baseColumns;
	}

	/**
	 * 主键列
	 * @return
	 */
	public List<Column> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}
	/**
	 * 主键列
	 */
	public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}
	/**
	 * blob列
	 */
	public List<Column> getBlobCoumns() {
		return blobColumns;
	}

	/**
	 * blob列
	 */
	public void setBlobCoumns(List<Column> blobColumns) {
		this.blobColumns = blobColumns;
	}
	
	/**
	 * 所有列 keyColumns+baseColumns+bolbCoumns
	 */
	public List<Column> getAllColumns(){
		List<Column> columns = new ArrayList<Column>();
		if(primaryKeyColumns != null && primaryKeyColumns.size()>0){
			columns.addAll(primaryKeyColumns);
		}
		if(baseColumns != null && baseColumns.size()>0){
			columns.addAll(baseColumns);
		}
		if(blobColumns != null && blobColumns.size()>0){
			columns.addAll(blobColumns);
		}
		return columns;
	}
	
	/**
	 * 返回所有非主键列
	 * @return
	 */
	public List<Column> getNonPriamryKeyColumns(){
		List<Column> columns = new ArrayList<Column>();
		if(baseColumns != null && baseColumns.size()>0){
			columns.addAll(baseColumns);
		}
		if(blobColumns != null && blobColumns.size()>0){
			columns.addAll(blobColumns);
		}
		return columns;
	}
	
	/**
	 * 返回非blob列
	 * @return
	 */
	public List<Column> getNonBlobColumns(){
		List<Column> columns = new ArrayList<Column>();
		if(primaryKeyColumns != null && primaryKeyColumns.size()>0){
			columns.addAll(primaryKeyColumns);
		}
		if(baseColumns != null && baseColumns.size()>0){
			columns.addAll(baseColumns);
		}
		return columns;
	}
	
	/**
	 * 表中所有列使用的import,Set转为List是由于freemarker遍历set会报错
	 * @return
	 */
	public List<String> getImportList(){
		Set<String> set = new HashSet<String>();
		List<Column> allColumns = getAllColumns();
		Iterator<Column> it = allColumns.iterator();
		while(it.hasNext()){
			Column column = it.next();
			if(column != null){
				JdbcTypeInformation jdbcTypeInfo =  column.getJdbcTypeInformation();
				if(jdbcTypeInfo != null){
					FullyQualifiedJavaType javaType = jdbcTypeInfo.getFullyQualifiedJavaType();
					if(javaType != null){
						List<String> columnImportList = javaType.getImportList();
						if(columnImportList != null){
							set.addAll(columnImportList);
						}
					}
				}
			}
		}
		List<String> answer = new ArrayList<String>();
		if(set.size()>0){
			answer.addAll(set);
		}
		return answer;
	}
	
}
