package forgefuck.team.configmanager.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigFolder {
    
    private final List<ConfigFile> configs;
    
    public ConfigFolder(File root) {
        this.configs = new ArrayList<ConfigFile>();
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                ConfigFile checkFile = new ConfigFile(file);
                if (checkFile.isConfigFile()) {
                    configs.add(checkFile);
                }
            }
        }
    }
    
    public boolean hasConfigs() {
        return !configs.isEmpty();
    }
    
    public ConfigFile[] getConfigsArray() {
        return configs.toArray(new ConfigFile[configs.size()]);
    }

}
