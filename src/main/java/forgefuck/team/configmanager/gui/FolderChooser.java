package forgefuck.team.configmanager.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;

public class FolderChooser extends JFileChooser {
    
    private static File lastOpenedDirectory;
    private final int openerIndent = 350;
    
    public FolderChooser() {
        this("Выбрать папку");
    }
    
    public FolderChooser(String title) {
        super(lastOpenedDirectory);
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(scr.width - openerIndent, scr.height - openerIndent));
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(false);
        setDialogTitle(title);
    }
    
    public File openChooser() {
        if (showDialog(null, "Выбрать") == JFileChooser.APPROVE_OPTION) {
            File dir = getSelectedFile();
            lastOpenedDirectory = dir;
            return dir;
        }
        return null;
    }

}
