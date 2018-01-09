package com.xipin.codegenerator.config.xml.model;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.internal.util.StringUtility;

/**
 * 生成时包含的数据库表
 * @author songchangyou
 *
 */
public class Tables {
	/**
	 * 包含的表, 多个值用","(英文)隔开，"*"匹配任意字符
	 */
	private String include;
	/**
	 * 排除的表, 多个值用","(英文)隔开，"*"匹配任意字符
	 */
	private String exclude;
	
	/**
	 * 完全匹配的表名(已转换为大写)
	 */
	private Set<String> includeTable;
	
	/**
	 * 部分匹配的表名(已转换为大写)
	 */
	private Set<String> includeLikeTable;
	
	/**
	 * 完全匹配的表名(已转换为大写)
	 */
	private Set<String> excludeTable;
	
	/**
	 * 部分匹配的表名(已转换为大写)
	 */
	private Set<String> excludeLikeTable;
	/**
	 * 为java中DatabaseMetaData的getTables方法的参数
	 */
	private String catalog;
	/**
	 * 为java中DatabaseMetaData的getTables方法的参数
	 *	oracle数据时为表用户
	 */
	private String schema;
	/**
	 * 是否包含数据库的视图
	 */
	private boolean containsView;
	/**
	 * 数值类型是否强制转化为BigDecimal类型
	 */
	private boolean forceBigDecimals;
	/**
	 * 数值类型是否强制转化为BigDecimal类型
	 */
	public boolean isForceBigDecimals() {
		return forceBigDecimals;
	}
	/**
	 * 数值类型是否强制转化为BigDecimal类型
	 */
	public void setForceBigDecimals(boolean forceBigDecimals) {
		this.forceBigDecimals = forceBigDecimals;
	}
	/**
	 * 为java中DatabaseMetaData的getTables方法的参数
	 */
	public String getCatalog() {
		return catalog;
	}
	/**
	 * 为java中DatabaseMetaData的getTables方法的参数
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	/**
	 * 为java中DatabaseMetaData的getTables方法的参数
	 *	oracle数据时为表用户
	 */
	public String getSchema() {
		return schema;
	}
	/**
	 * 为java中DatabaseMetaData的getTables方法的参数
	 *	oracle数据时为表用户
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}
	/**
	 * 是否包含数据库的视图
	 */
	public boolean isContainsView() {
		return containsView;
	}
	/**
	 * 是否包含数据库的视图
	 */
	public void setContainsView(boolean containsView) {
		this.containsView = containsView;
	}
	/**
	 * 完全匹配的表名(已转换为大写)
	 */
	public Set<String> getIncludeTable() {
		return includeTable;
	}
	/**
	 * 部分匹配的表名(已转换为大写)
	 */
	public Set<String> getIncludeLikeTable() {
		return includeLikeTable;
	}
	/**
	 * 完全匹配的表名(已转换为大写)
	 */
	public Set<String> getExcludeTable() {
		return excludeTable;
	}
	/**
	 * 部分匹配的表名(已转换为大写)
	 */
	public Set<String> getExcludeLikeTable() {
		return excludeLikeTable;
	}
	/**
	 * 包含的表, 多个值用","(英文)隔开，"*"匹配任意字符
	 */
	public String getInclude() {
		return include;
	}
	/**
	 * 包含的表, 多个值用","(英文)隔开，"*"匹配任意字符
	 */
	public void setInclude(String include) {
		this.include = include;
	}
	/**
	 * 排除的表, 多个值用","(英文)隔开，"*"匹配任意字符
	 */
	public String getExclude() {
		return exclude;
	}
	/**
	 * 排除的表, 多个值用","(英文)隔开，"*"匹配任意字符
	 */
	public void setExclude(String exclude) {
		this.exclude = exclude;
	}
	
	/**
	 * 设置 includeTable includeLikeTable excludeTable excludeLikeTable的值
	 */
	public void parseTable(){
		if(StringUtility.stringHasValue(include)){
			String[] includes = include.split(",");
			includeTable = new HashSet<String>();
			includeLikeTable = new HashSet<String>();
			for(String in : includes){
				if(in.indexOf("*") > -1){
					includeLikeTable.add(in.toUpperCase());
				}else{
					includeTable.add(in.toUpperCase());
				}
			}
		}
		if(StringUtility.stringHasValue(exclude)){
			String[] excludes = exclude.split(",");
			excludeTable = new HashSet<String>();
			excludeLikeTable = new HashSet<String>();
			for(String ex : excludes){
				if(ex.indexOf("*") > -1){
					excludeLikeTable.add(ex.toUpperCase());
				}else{
					excludeTable.add(ex.toUpperCase());
				}
			}
		}
	}
}
