package com.xipin.codegenerator.generator;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.ConnectionFactory;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xipin.codegenerator.config.xml.model.Configuration;
import com.xipin.codegenerator.config.xml.model.Tables;
import com.xipin.codegenerator.config.xml.model.Template;
import com.xipin.codegenerator.config.xml.model.Templates;
import com.xipin.codegenerator.generator.model.Column;
import com.xipin.codegenerator.generator.model.Table;
import com.xipin.codegenerator.utils.FreeMarkerUtil;
import com.xipin.codegenerator.utils.WildcardUtils;

public class CodeGenerator {

	private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
	
	public CodeGenerator(){
		
	}
	
	public CodeGenerator(Configuration config){
		this.config = config;
	}
	
	private Configuration config;
	
	
	public Configuration getConfig() {
		return config;
	}


	public void setConfig(Configuration config) {
		this.config = config;
	}


	/**
	 * 生成代码
	 */
	public void generate(){
		validate();
		//把jar包或者类文件加载到classPath
		addClassEntry();
		List<Table> tables = getAllTable();
		if(tables != null){
			int size = tables.size();
			for(int index = 0; index<size; index++){
				Table table = tables.get(index);
				if(table != null){
					getColumns(table);
				}
			}
			Templates templates = config.getTemplates();
			if(templates != null){
				List<Template> list = templates.getTemplates();
				Iterator<Template> it = list.iterator();
				while(it.hasNext()){
					Template template = it.next();
					for(Table table : tables){
						Map<String,Object> root = new HashMap<String,Object>();
						root.put("table", table);
						/**
						 * com.xipin.codegenerator.config.xml.model.Configuration中的Properties属性
						 */
						root.put("prop", config.getProperties());
						
						//保存的文件名为：文件名前缀+表名（首字母大写）+文件名后缀+"."+生成文件的类型
						String outFileName = template.getTargetDirectory();
						outFileName = outFileName.replaceAll("\\\\", "/");
						if(!outFileName.endsWith("/")){
							outFileName += "/";
						}
						if(StringUtility.stringHasValue(template.getFileNamePrefix())){
							outFileName += template.getFileNamePrefix();
						}
						outFileName +=  table.getTableName();
						if(StringUtility.stringHasValue(template.getFileNameSuffix())){
							outFileName += template.getFileNameSuffix();
						}
						outFileName +=  "." + template.getFileType();
						
						//TODO: 表生成时引入需要的类  column取得javaType
						//生成文件
						FreeMarkerUtil.generateFile(root,templates.getTplDirectory(),template.getTplFile(),outFileName,
								templates.isMarkDirIfNotExists(),templates.isOverwrite());
					}
				}
			}
		}
	}

	/**
	 * 获得数据库中所有的表
	 */
	protected List<Table> getAllTable() {
		List<Table> tables = new ArrayList<Table>();
		Connection connection = null;
		ResultSet tableSet = null;
		try {
			connection = getConnection();
			Tables configTables = config.getTables();
			String[] types = new String[]{"TABLE"};
			DatabaseMetaData metaData = connection.getMetaData();
			if(configTables.isContainsView()){
				types = new String[]{"TABLE","VIEW"};
			}
			tableSet = metaData.getTables(configTables.getCatalog(),configTables.getSchema(), null, types);
			while(tableSet.next()){
				String tableName = tableSet.getString("TABLE_NAME");
				String tableSchema = tableSet.getString("TABLE_SCHEM");
				String catalog = tableSet.getString("TABLE_CAT");
				if(!tableName.contains("$")){
					String theTableName = tableName.toUpperCase();
					boolean isGenerate = false;
					/*
					 生成规则：
						如果包含在 exclude中，不生成
						不在exclude中：
							如果include为空，生成
							如果include不为空
								包含在include中，生成
								不包含，不生成
					 */
					if(configTables.getExcludeLikeTable().contains(theTableName)){
						isGenerate = false;
					}else if(WildcardUtils.stringInWildcarString(theTableName, configTables.getExcludeLikeTable())){
						isGenerate = false;
					}else if(StringUtility.stringHasValue(configTables.getInclude())){
						if(configTables.getIncludeTable().contains(theTableName)){
							isGenerate = true;
						}else if(WildcardUtils.stringInWildcarString(theTableName, configTables.getIncludeLikeTable())){
							isGenerate = true;
						}else{
							isGenerate = false;
						}
					}else{
						isGenerate = true;
					}
					if(isGenerate){
						Table table = new Table();
						table.setActualTableName(tableName);
						table.setRemarks(tableSet.getString("REMARKS"));
						table.setTableName(getTableName(tableName));
						table.setSchema(tableSchema);
						table.setCatalog(catalog);
						tables.add(table);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("数据库连接出错", e);
		}finally {
			closeResultSet(tableSet);
			closeConnection(connection);
		}
		return tables;
	}
	
	/**
	 * 根据表名获取列
	 * @param table
	 * @return
	 */
	protected void getColumns(Table table){
		if(table != null){
			String tableName = table.getActualTableName();
			Connection connection = null;
			//所有列
			ResultSet rs = null;
			//主键列
			ResultSet primaryKeyRs = null;
			
			List<Column> primaryKeyColumns = new ArrayList<Column>();
			List<Column> blobColumns = new ArrayList<Column>();
			List<Column> baseColumns = new ArrayList<Column>();
			try{
				connection = getConnection();
				rs = connection.getMetaData().getColumns(table.getCatalog(), table.getSchema(), tableName, null);
				while(rs.next()){
					Column column = new Column();
					column.setTable(table);
					/*
					 每个列描述都有以下列： 
						TABLE_CAT String => 表类别（可为 null） 
						TABLE_SCHEM String => 表模式（可为 null） 
						TABLE_NAME String => 表名称 
					*/
					
//						COLUMN_NAME String => 列名称 
					String actualColumnName = rs.getString("COLUMN_NAME");
					column.setActualColumnName(actualColumnName);
					column.setColumnNameLower(getColumnName(actualColumnName, false));
					column.setColumnNameUpper(getColumnName(actualColumnName, true));
//						DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型 
					int dataType = rs.getInt("DATA_TYPE");
					column.setDataType(dataType);
//						TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的 
//						COLUMN_SIZE int => 列的大小。 
					int columnSize = rs.getInt("COLUMN_SIZE");
					column.setColumnSize(columnSize);
//						BUFFER_LENGTH 未被使用。 
//						DECIMAL_DIGITS int => 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。 
					int decimalDigits = rs.getInt("DECIMAL_DIGITS");
					column.setDecimalDigits(decimalDigits);
//						NUM_PREC_RADIX int => 基数（通常为 10 或 2） 
					int numPrecRadix = rs.getInt("NUM_PREC_RADIX");
					column.setNumPrecRadix(numPrecRadix);
/**						NULLABLE int => 是否允许使用 NULL。 
	   						columnNoNulls - 可能不允许使用 NULL 值 
							columnNullable - 明确允许使用 NULL 值 
							columnNullableUnknown - 不知道是否可使用 null 
 *
 */
					int nullable = rs.getInt("NULLABLE");
					column.setNullable(nullable);
//						REMARKS String => 描述列的注释（可为 null） 
					String remarks = rs.getString("REMARKS");
					column.setRemarks(remarks);
//						COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null） 
					String columnDef = rs.getString("COLUMN_DEF");
					column.setColumnDef(columnDef);
						/*
						SQL_DATA_TYPE int => 未使用 
						SQL_DATETIME_SUB int => 未使用 
						*/
//						CHAR_OCTET_LENGTH int => 对于 char 类型，该长度是列中的最大字节数 
					int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
					column.setCharOctetLength(charOctetLength);
//						ORDINAL_POSITION int => 表中的列的索引（从 1 开始） 
					int ordinalPosition = rs.getInt("ORDINAL_POSITION");
					column.setOrdinalPosition(ordinalPosition);
					/*
						IS_NULLABLE String => ISO 规则用于确定列是否包括 null。 
							YES --- 如果参数可以包括 NULL 
							NO --- 如果参数不可以包括 NULL 
							空字符串 --- 如果不知道参数是否可以包括 null 
					*/
					String isNullable = rs.getString("IS_NULLABLE");
					column.setIsNullable(isNullable);
					/*
						SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null） 
						SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null） 
						SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null） 
						SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null） 
					*/
					/*
						IS_AUTOINCREMENT String => 指示此列是否自动增加 
							YES --- 如果该列自动增加 
							NO --- 如果该列不自动增加 
							空字符串 --- 如果不能确定该列是否是自动增加参数 

					 * 
					 */
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					// 查看有那些列
//					for (int i = 1; i < columnCount + 1; i++ ) {
//						String name = rsmd.getColumnName(i);
//						logger.info(name);
//					}
					if(columnCount >= 23){
						String isAutoincrement = rs.getString("IS_AUTOINCREMENT");
						column.setIsAutoincrement(isAutoincrement);
					}
					
					JavaTypeResolverImpl resolver = new JavaTypeResolverImpl();
					resolver.setForceBigDecimals(this.config.getTables().isForceBigDecimals());
					resolver.calculateJdbcTypeInformation(column);
					
					//blob字段
					if(dataType == Types.BLOB){
						blobColumns.add(column);
					}else{
						baseColumns.add(column);
					}
					
					
				}
				
				//主键 从baseColumns和blobColumn中查找，找到后加到primaryKeyColumns中，并从baseColumns或者blobColumns中删除
				primaryKeyRs = connection.getMetaData().getPrimaryKeys(table.getCatalog(), table.getSchema(), tableName);
				if(primaryKeyRs.next()){
					String columnName = primaryKeyRs.getString("COLUMN_NAME");
					boolean found = false;
					Iterator<Column> baseIt = baseColumns.iterator();
					while(baseIt.hasNext()){
						Column column = baseIt.next();
						if(columnName.equals(column.getActualColumnName())){
							primaryKeyColumns.add(column);
							baseIt.remove();
							found = true;
							break;
						}
					}
					//在baseColumns中未找到，则到blobColumns中找
					if(!found){
						Iterator<Column> blobIt = blobColumns.iterator();
						while(blobIt.hasNext()){
							Column column = blobIt.next();
							if(columnName.equals(column.getActualColumnName())){
								primaryKeyColumns.add(column);
								blobIt.remove();
								found = true;
								break;
							}
						}
					}
				}
				
			}catch(Exception e){
				logger.error("获取"+tableName+"的列失败",e);
			}finally{
				table.setBaseColumns(baseColumns);
				table.setPrimaryKeyColumns(primaryKeyColumns);
				table.setBlobCoumns(blobColumns);
				closeResultSet(rs);
				closeResultSet(primaryKeyRs);
				closeConnection(connection);
			}
		}
	}
	
	/**
	 * 把jar包或者类文件加载到classPath
	 */
	protected void addClassEntry(){
		validate();
		if (config.getClassPathEntries().size() > 0) {
            ClassLoader classLoader = getCustomClassloader(config.getClassPathEntries());
            ObjectFactory.addExternalClassLoader(classLoader);
        }
	}
	
	/**
	 * 判断configuration是否已赋值
	 */
	protected void validate(){
		if(this.config == null){
			String message = "config属性未赋值，不能操作！";
			logger.error(message);
			throw new RuntimeException(message);
		}
	}
	

	/**
	 * 获得与数据的连接
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection(config.getJdbcConnectionConfiguration());

        return connection;
    }

    /**
     * 关闭与数据库的连接
     * @param connection
     */
	protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }
    
    /**
     * 关闭与数据库的连接
     * @param connection
     */
	protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
            	rs.close();
            } catch (SQLException e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }
    
    /**
     * 表名，首字母大写
     * @param tableName
     * @return
     */
	protected String getTableName(String tableName){
    	String result = null;
    	if(StringUtility.stringHasValue(tableName)){
    		result = JavaBeansUtil.getCamelCaseString(tableName, true);
    	}else{
    		String message = "表名不能为空！";
    		logger.error(message);
    		throw new RuntimeException(message);
    	}
    	return result;
    }
	
	/**
	 * 列名
	 * @param columnName
	 * @param firstCharacterUppercase 首字母是否大写
	 * @return
	 */
	protected String getColumnName(String columnName,boolean firstCharacterUppercase){
    	String result = null;
    	if(StringUtility.stringHasValue(columnName)){
    		result = JavaBeansUtil.getCamelCaseString(columnName, firstCharacterUppercase);
    	}else{
    		String message = "列名不能为空！";
    		logger.error(message);
    		throw new RuntimeException(message);
    	}
    	return result;
    }
	
	
}
