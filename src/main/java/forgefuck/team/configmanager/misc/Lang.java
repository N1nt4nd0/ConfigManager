package forgefuck.team.configmanager.misc;

public enum Lang {
    
    RUS, ENG;
    
	public static final Lang defaultLang = ENG;
    private static Lang current = defaultLang;
    
    public static void set(Lang lang) {
        current = lang;
    }
    
    public static String get(String eng, String rus) {
        switch (current) {
        case RUS:
            return rus;
        default:
            return eng;
        }
    }

}
