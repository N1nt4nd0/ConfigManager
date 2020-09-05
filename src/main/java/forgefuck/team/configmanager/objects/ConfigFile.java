package forgefuck.team.configmanager.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import forgefuck.team.configmanager.ConfigData;
import forgefuck.team.configmanager.Constants;
import forgefuck.team.configmanager.misc.FileProvider;

public class ConfigFile extends FileProvider {
    
    private final List<Module> modules;
    private boolean isConfigFile;
    private final Gson gson;
    private ConfigData data;
    
    public ConfigFile(File file) {
        this(file.getPath());
    }
    
    public ConfigFile(String path) {
        super(path);
        this.gson = new GsonBuilder().create();
        this.modules = new ArrayList<Module>();
        if (exists() && isFile() && getName().endsWith(Constants.CONFIG_TYPE) && length() <= Constants.MAX_CONFIG_SIZE && readContent().contains("moduleData")) {
            ConfigData checkData = readData();
            if (checkData != null) {
                isConfigFile = true;
                data = checkData;
                data.moduleData.forEach((mod, flds) -> {
                    modules.add(new Module(this, mod, flds));
                });
            }
        }
    }
    
    public boolean saveBackup() {
        return save() && new FileProvider(getPath().replaceAll(Constants.CONFIG_TYPE, "_bkp" + Constants.CONFIG_TYPE)).writeContent(readContent());
    }
    
    private ConfigData readData() {
        try {
            return gson.fromJson(readContent(), ConfigData.class);
        } catch(JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Module[] getModulesArray() {
        return modules.toArray(new Module[modules.size()]);
    }
    
    public boolean save() {
        return writeContent(gson.toJson(data));
    }
    
    public boolean isConfigFile() {
        return isConfigFile;
    }
    
    @Override public String toString() {
        return getName();
    }

}
