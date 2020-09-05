package forgefuck.team.configmanager.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Module {
    
    private final List<ModuleField> fields;
    private final ConfigFile parent;
    private final String name;
    
    public Module(ConfigFile parent, String name, Map<String, List<String>> moduleContent) {
        this.fields = new ArrayList<ModuleField>();
        this.parent = parent;
        this.name = name;
        moduleContent.forEach((fName, data) -> {
            fields.add(new ModuleField(fName, data));
        });
    }
    
    public String getName() {
        return name;
    }
    
    public ConfigFile getParent() {
        return parent;
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
