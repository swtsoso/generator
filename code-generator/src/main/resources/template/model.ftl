package ${prop.entityPackageName};

<#list  table.importList as importStr>
import ${importStr};
</#list>

/**
 *${table.actualTableName}
 *${table.remarks}
 */
public class ${table.tableName}{

	public ${table.tableName}(){
	
	}
	
	<#-- 字段 -->
	<#list  table.allColumns as column>
	/**
	 *${column.remarks}
	 */
	private ${column.javaTypeShortName} ${column.columnNameLower};
	
	</#list>
	
	<#-- getter setter -->
	<#list  table.allColumns as column>
	/**
	 *${column.remarks}
	 */
	public ${column.javaTypeShortName} get${column.columnNameUpper}(){
		return this.${column.columnNameLower};
	}
	
	/**
	 *${column.remarks}
	 */
	public void set${column.columnNameUpper}(${column.javaTypeShortName} ${column.columnNameLower}){
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	</#list>
	
}