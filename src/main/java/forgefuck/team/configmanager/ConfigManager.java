package forgefuck.team.configmanager;

import forgefuck.team.configmanager.gui.ConfigFrame;
import forgefuck.team.configmanager.misc.Logging;

public class ConfigManager {
    
    public static final ConfigFrame frame = new ConfigFrame();
    public static final Logging log = new Logging();
    
    static {
        frame.loadFolder(Constants.DEFAULT_CONFIG_FOLDER);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {}
    
}