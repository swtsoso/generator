package ${prop.controllerPackageName};

import ${prop.baseContollerPackageName}.BaseCrud4RestfulController;
import ${prop.entityPackageName}.${table.tableName};
import ${prop.managerPackageName}.${table.tableName}Manager;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/${table.tableNameLower}")
public class ${table.tableName}Controller extends BaseCrud4RestfulController<${table.tableName}> {
    @Resource
    private ${table.tableName}Manager ${table.tableNameLower}Manager;

    @Override
    public CrudInfo init() {
        return new CrudInfo("${table.tableNameLower}/",${table.tableNameLower}Manager);
    }
 }