package ${prop.managerImplPackageName};

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ${prop.baseManagerImplPackageName}.BaseCrudServiceImpl;
import ${prop.servicePackageName}.${table.tableName}Service;
import ${prop.baseServicePackageName}.${table.tableName}Service;


@Service("${table.tableNameLower}Manager")
public class ${table.tableName}ManagerImpl extends BaseCrudManagerImpl implements ${table.tableName}Manager {
    @Resource
    private ${table.tableName}Service ${table.tableNameLower}Service;

    @Override
    public BaseCrudMapper init() {
        return ${table.tableNameLower}Service;
    }
}
