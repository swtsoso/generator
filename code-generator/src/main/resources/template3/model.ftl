package ${prop.entityPackageName};

<#list  table.importList as importStr>
import ${importStr};
</#list>
import java.io.Serializable;
/**
 *${table.actualTableName} ${table.remarks}
 */
public class ${table.tableName} implements Serializable{

	private static final long serialVersionUID = 1L;
	
	<#-- 字段 -->
	<#list  table.allColumns as column>
	//${column.remarks}
	private ${column.javaTypeShortName} ${column.columnNameLower};
	</#list>
	
	public ${table.tableName}(){}
	
	<#-- getter setter -->
	<#list  table.allColumns as column>
	public ${column.javaTypeShortName} get${column.columnNameUpper}(){
		return this.${column.columnNameLower};
	}
	
	public void set${column.columnNameUpper}(${column.javaTypeShortName} ${column.columnNameLower}){
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	</#list>
}