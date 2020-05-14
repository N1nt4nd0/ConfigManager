package forgefuck.team.configmanager.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Module {
    
    private final List<ModuleField> fields;
    private final String name;
    
    public Module(String name, Map<String, List<String>> moduleContent) {
        this.fields = new ArrayList<ModuleField>();
        this.name = name;
        moduleContent.forEach((fName, data) -> {
            fields.add(new ModuleField(fName, data));
        });
    }
    
    public String getName() {
        return name;
    }
    
    public List<ModuleField> getFields() {
        return fields;
    }
    
    public boolean hasLocal() {
        return fields.stream().anyMatch(ModuleField::isLocal);
    }
    
    @Override public String toString() {
        return getName();
    }

}
