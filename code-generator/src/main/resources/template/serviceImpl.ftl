package ${prop.serviceImplPackageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import ${prop.entityPackageName}.${table.tableName};
import ${prop.mapperPackageName}.${table.tableName}Mapper;
import ${prop.servicePackageName}.${table.tableName}Service;

@Service("${table.tableNameLower}Service")
public class ${table.tableName}ServiceImpl implements ${table.tableName}Service {

	@Autowired
	private ${table.tableName}Mapper ${table.tableNameLower}Mapper;

	public ${table.tableName} getEntityByPKId(String pkId) {
		return ${table.tableNameLower}Mapper.getEntityByPKId(pkId);
	}

	public int addEntity(${table.tableName} entity) {
		return ${table.tableNameLower}Mapper.addEntity(entity);
	}
	
	
	public int updateEntity(${table.tableName} entity){
		return ${table.tableNameLower}Mapper.updateEntity(entity);
	}
	
	
	public int deleteEntityByPKId(String pkId){
		return ${table.tableNameLower}Mapper.deleteEntityByPKId(pkId);
	}

	public List<${table.tableName}> getByDynamicWhere(${table.tableName} entity) {
		return ${table.tableNameLower}Mapper.getByDynamicWhere(entity);
	}

	public List<${table.tableName}> getByCustomWhere(String strWhere) {
		return ${table.tableNameLower}Mapper.getByCustomWhere(strWhere);
	}


}