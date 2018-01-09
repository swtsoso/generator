package ${prop.serviceImplPackageName};

import ${prop.baseMapperPackageName}.BaseCrudMapper;
import ${prop.baseServiceImplPackageName}.BaseCrudServiceImpl;
import ${prop.mapperPackageName}.${table.tableName}Mapper;
import ${prop.servicePackageName}.${table.tableName}Service;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("${table.tableNameLower}Service")
public class ${table.tableName}ServiceImpl extends BaseCrudServiceImpl implements ${table.tableName}Service {
    @Resource
    private ${table.tableName}Mapper ${table.tableNameLower}Mapper;

    @Override
    public BaseCrudMapper init() {
        return ${table.tableNameLower}Mapper;
    }
}
