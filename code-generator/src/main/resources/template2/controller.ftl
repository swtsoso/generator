package ${prop.controllerPackageName};

import ${prop.baseContollerPackageName}.BaseCrud4RestfulController;
import ${prop.entityPackageName}.${table.tableName};
import ${prop.servicePackageName}.${table.tableName}Service;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/${table.tableNameLower}")
public class ${table.tableName}Controller extends BaseCrud4RestfulController<${table.tableName}> {
    @Resource
    private ${table.tableName}Service ${table.tableNameLower}Service;

    @Override
    public CrudInfo init() {
        return new CrudInfo("${table.tableNameLower}/",${table.tableNameLower}Service);
    }
 }