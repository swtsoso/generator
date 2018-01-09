package com.xipin.codegenerator.generator.model;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl.JdbcTypeInformation;

public class Column {
	/**
	 * 对应的表
	 */
	private Table table;
	
	/**
	 * 数据库实际列名
	 */
	private String actualColumnName;
	
	/**
	 * 列名,首字母大写
	 */
	private String columnNameUpper;
	
	/**
	 * 列名,首字母小写
	 */
	private String columnNameLower;
	
	
	/**
	 * 来自 java.sql.Types 的 SQL 类型 
	 */
	private int dataType;
	
	/**
	 * 根据sql类型获得的JdbcTypeInformation
	 */
	private JdbcTypeInformation jdbcTypeInformation;
	/**
	 * 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的 
	 */
	private String typeName;
	/**
	 * 列的大小
	 */
	private int columnSize;
	/**
	 *  小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
	 */
	private int decimalDigits;
	/**
	 * 基数（通常为 10 或 2）
	 */
	private int numPrecRadix;
	
	/**
	 * 是否允许使用 NULL
	 */
	private int nullable;
	/**
	 * 描述列的注释（可为 null） 
	 */
	private String remarks;
	/**
	 * 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
	 */
	private String columnDef;
	/**
	 *  对于 char 类型，该长度是列中的最大字节数 
	 */
	private int charOctetLength;
	/**
	 * 表中的列的索引（从 1 开始）
	 */
	private int ordinalPosition;
	/**
	 *ISO 规则用于确定列是否包括 null。 
		YES --- 如果参数可以包括 NULL 
		NO --- 如果参数不可以包括 NULL 
		空字符串 --- 如果不知道参数是否可以包括 null 
	 */
	private String isNullable;
	/**
	 * 指示此列是否自动增加 
			YES --- 如果该列自动增加 
			NO --- 如果该列不自动增加 
			空字符串 --- 如果不能确定该列是否是自动增加参数 
	 */
	private String isAutoincrement;
	/**
	 * 指示此列是否自动增加 
			YES --- 如果该列自动增加 
			NO --- 如果该列不自动增加 
			空字符串 --- 如果不能确定该列是否是自动增加参数 
	 */
	public String getIsAutoincrement() {
		return isAutoincrement;
	}
	/**
	 * 指示此列是否自动增加 
			YES --- 如果该列自动增加 
			NO --- 如果该列不自动增加 
			空字符串 --- 如果不能确定该列是否是自动增加参数 
	 */
	public void setIsAutoincrement(String isAutoincrement) {
		this.isAutoincrement = isAutoincrement;
	}
	/**
	 *ISO 规则用于确定列是否包括 null。 
		YES --- 如果参数可以包括 NULL 
		NO --- 如果参数不可以包括 NULL 
		空字符串 --- 如果不知道参数是否可以包括 null 
	 */
	public String getIsNullable() {
		return isNullable;
	}
	/**
	 *ISO 规则用于确定列是否包括 null。 
		YES --- 如果参数可以包括 NULL 
		NO --- 如果参数不可以包括 NULL 
		空字符串 --- 如果不知道参数是否可以包括 null 
	 */
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	/**
	 * 表中的列的索引（从 1 开始）
	 */
	public int getOrdinalPosition() {
		return ordinalPosition;
	}
	/**
	 * 表中的列的索引（从 1 开始）
	 */
	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	/**
	 * 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
	 */
	public String getColumnDef() {
		return columnDef;
	}
	/**
	 * 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
	 */
	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}
	/**
	 *  对于 char 类型，该长度是列中的最大字节数 
	 */
	public int getCharOctetLength() {
		return charOctetLength;
	}
	/**
	 *  对于 char 类型，该长度是列中的最大字节数 
	 */
	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
	}
	/**
	 * 描述列的注释（可为 null） 
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 描述列的注释（可为 null） 
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 是否允许使用 NULL
	 */
	public int getNullable() {
		return nullable;
	}

	/**
	 * 是否允许使用 NULL
	 */
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	/**
	 * 基数（通常为 10 或 2）
	 */
	public int getNumPrecRadix() {
		return numPrecRadix;
	}
	
	/**
	 * 基数（通常为 10 或 2）
	 */
	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}
	/**
	 *  小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
	 */
	public int getDecimalDigits() {
		return decimalDigits;
	}
	/**
	 *  小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
	 */
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	/**
	 * 列的大小
	 */
	public int getColumnSize() {
		return columnSize;
	}
	/**
	 * 列的大小
	 */
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	/**
	 * 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的 
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的 
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 根据sql类型获得的JdbcTypeInformation
	 */
	public JdbcTypeInformation getJdbcTypeInformation() {
		return jdbcTypeInformation;
	}
	/**
	 * 根据sql类型获得的JdbcTypeInformation
	 */
	public void setJdbcTypeInformation(JdbcTypeInformation jdbcTypeInformation) {
		this.jdbcTypeInformation = jdbcTypeInformation;
	}
	/**
	 * 来自 java.sql.Types 的 SQL 类型 
	 */
	public int getDataType() {
		return dataType;
	}
	/**
	 * 来自 java.sql.Types 的 SQL 类型 
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	/**
	 * 列名，首字母大写
	 */
	public String getColumnNameUpper() {
		return columnNameUpper;
	}
	/**
	 * 列名，首字母大写
	 */
	public void setColumnNameUpper(String columnNameUpper) {
		this.columnNameUpper = columnNameUpper;
	}
	/**
	 * 列名，首字母小写
	 */
	public String getColumnNameLower() {
		return columnNameLower;
	}
	/**
	 * 列名，首字母小写
	 */
	public void setColumnNameLower(String columnNameLower) {
		this.columnNameLower = columnNameLower;
	}
	/**
	 * 数据库实际列名
	 */
	public String getActualColumnName() {
		return actualColumnName;
	}
	/**
	 * 数据库实际列名
	 */
	public void setActualColumnName(String actualColumnName) {
		this.actualColumnName = actualColumnName;
	}
	/**
	 * 对应的表
	 */
	public Table getTable() {
		return table;
	}
	/**
	 * 对应的表
	 */
	public void setTable(Table table) {
		this.table = table;
	}
	
	/**
	 * 列对应的java类型
	 * @return
	 */
	public String getJavaTypeShortName(){
		String answer = null;
		if(this.jdbcTypeInformation != null){
			FullyQualifiedJavaType javaType = jdbcTypeInformation.getFullyQualifiedJavaType();
			if(javaType != null){
				answer = javaType.getShortName();
			}
		}
		return answer;
	}
	
	/**
	 * 列对应的java类型
	 * @return
	 */
	public String getJavaTypeName(){
		String answer = null;
		if(this.jdbcTypeInformation != null){
			FullyQualifiedJavaType javaType = jdbcTypeInformation.getFullyQualifiedJavaType();
			if(javaType != null){
				answer = javaType.getFullyQualifiedName();
			}
		}
		return answer;
	}
	
}
