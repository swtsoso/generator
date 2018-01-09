package com.xipin.codegenerator.config.xml.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.config.JDBCConnectionConfiguration;

public class Configuration extends org.mybatis.generator.config.Configuration {

	/**
	 * 连接数据的配置
	 */
	private JDBCConnectionConfiguration jdbcConnectionConfiguration;
	/**
	 * 系统及xml中配置的属性
	 */
	private Properties properties;
	/**
	 * 要加载的类或者jar文件
	 */
	private List<String> classPathEntry = new ArrayList<String>();
	
	/**
	 * 模版文件
	 */
	private Templates templates;
	/**
	 * 要生成文件的数据库表
	 */
	private Tables tables;
	/**
	 * 模版文件
	 */
	public Templates getTemplates() {
		return templates;
	}
	/**
	 * 模版文件
	 */
	public void setTemplates(Templates templates) {
		this.templates = templates;
	}
	/**
	 * 要生成文件的数据库表
	 */
	public Tables getTables() {
		return tables;
	}
	/**
	 * 要生成文件的数据库表
	 */
	public void setTables(Tables tables) {
		this.tables = tables;
	}
	/**
	 * 系统及xml中配置的属性
	 */
	public Properties getProperties() {
		return properties;
	}
	/**
	 * 系统及xml中配置的属性
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	/**
	 * 连接数据的配置
	 */
	public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
		return jdbcConnectionConfiguration;
	}
	/**
	 * 连接数据的配置
	 */
	public void setJdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration) {
		this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
	}

	/**
	 * 要加载的类或者jar文件
	 */
	public List<String> getClassPathEntry() {
		return classPathEntry;
	}

	/**
	 * 要加载的类或者jar文件
	 */
	public void setClassPathEntry(List<String> classPathEntry) {
		this.classPathEntry = classPathEntry;
	}
	
}
