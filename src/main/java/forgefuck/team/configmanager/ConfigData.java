package forgefuck.team.configmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class ConfigData {
    
    @SerializedName("moduleData") public final Map<String, Map<String, List<String>>> moduleData;
    
    public ConfigData() {
        moduleData = new HashMap<String, Map<String, List<String>>>();
    }

}
