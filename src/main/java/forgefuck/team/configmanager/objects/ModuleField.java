package forgefuck.team.configmanager.objects;

import java.util.List;

public class ModuleField {
    
    private final List<String> data;
    private final boolean isLocal;
    private final String name;
    
    public ModuleField(String name, List<String> data) {
        this.isLocal = name.startsWith("this:");
        this.name = name.split(":")[1];
        this.data = data;
    }
    
    public void setValue(String newVal) {
        data.set(1, newVal);
    }
    
    public boolean isLocal() {
        return isLocal;
    }
    
    public String getType() {
        return data.get(0);
    }
    
    public String getValue() {
        return data.get(1);
    }
    
    public String getName() {
        return name;
    }
    
    @Override public String toString() {
        return getName();
    }

}
