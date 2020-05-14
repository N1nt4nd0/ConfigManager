package forgefuck.team.configmanager.misc;

public class Logging {
    
    private final String prefix = "[ConfigManager] ";
    
    public void info(Object mess) {
        System.out.println(prefix + mess);
    }
    
    public void error(Object mess) {
        info("[ERROR] " + mess);
    }

}
