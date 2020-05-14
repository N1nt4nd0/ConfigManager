package forgefuck.team.configmanager.gui;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import forgefuck.team.configmanager.Constants;
import forgefuck.team.configmanager.objects.ConfigFile;
import forgefuck.team.configmanager.objects.ConfigFolder;
import forgefuck.team.configmanager.objects.Module;

public class ConfigFrame extends ExtendFrame {

    private final JButton chooseFolderBut, saveBut, deleteBut, updateFolderBut, openFolderBut, createBackupBut;
    private final ScrollingList<ConfigFile> configsList;
    private final ScrollingList<Module> modulesList;
    private final JToolBar directoryBar, editorBar;
    private final JTextField directoryField;
    
    public ConfigFrame() {
        super("Xenobyte Config Manager v." + Constants.VERSION);
        configsList = new ScrollingList<ConfigFile>("Конфигурации", 5, ListSorting.NONE);
        modulesList = new ScrollingList<Module>("Модули", 15, ListSorting.ALPHABET);
        createBackupBut = new JButton("Сохранить в бэкап");
        chooseFolderBut = new JButton("Выбрать папку");
        deleteBut = new JButton("Удалить конфиг");
        updateFolderBut = new JButton("Обновить");
        openFolderBut = new JButton("Открыть");
        directoryField = new JTextField(30);
        saveBut = new JButton("Сохранить");
        directoryBar = new JToolBar();
        editorBar = new JToolBar();
        directoryBar.setBorder(customTitledBorder("Рабочая папка", TitledBorder.LEFT));
        directoryField.setEditable(false);
        directoryBar.setFloatable(false);
        editorBar.setFloatable(false);
        directoryField.setFont(FONT);
        updateFolderBut.addActionListener(e -> loadFolder());
        configsList.addListSelectionListener((e) -> {
            if (e.getValueIsAdjusting()) {
                ConfigFile conf = configsList.getSelectedValue();
                if (conf != null) {
                    loadModules(conf.getModulesArray());
                }
            }
        });
        modulesList.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {
                if (10 == e.getExtendedKeyCode()) {
                    openModuleEdit();
                }
            }
        });
        modulesList.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (2 == e.getClickCount()) {
                    openModuleEdit();
                }
            }
        });
        createBackupBut.addActionListener((e) -> {
            ConfigFile conf = configsList.getSelectedValue();
            if (conf != null && conf.saveBackup()) {
                loadFolder();
            }
        });
        openFolderBut.addActionListener((e) -> {
            File dir = new File(directoryField.getText());
            if (dir.exists() && dir.isDirectory() && Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(dir);
                } catch (IOException ex) {}
            }
        });
        chooseFolderBut.addActionListener((e) -> {
            File folder = new FolderChooser().openChooser();
            if (folder != null) {
                loadFolder(folder);
            }
        });
        deleteBut.addActionListener((e) -> {
            ConfigFile conf = configsList.getSelectedValue();
            if (conf != null && confirmMessage("Удалить " + conf + "?") && conf.delete()) {
                loadFolder();
            }
        });
        saveBut.addActionListener((e) -> {
            ConfigFile conf = configsList.getSelectedValue();
            if (conf != null) {
                conf.save();
            }
        });
        directoryBar.add(directoryField);
        directoryBar.addSeparator();
        directoryBar.add(updateFolderBut);
        directoryBar.add(chooseFolderBut);
        directoryBar.add(openFolderBut);
        editorBar.add(configsList.getRoot());
        editorBar.add(modulesList.getRoot());
        buttonsBar.add(saveBut);
        buttonsBar.add(createBackupBut);
        buttonsBar.add(deleteBut);
        add(directoryBar);
        add(editorBar);
        add(buttonsBar);
        packFrame();
    }
    
    private void loadConfigs(ConfigFile[] configs) {
        configsList.setListData(configs);
        configsList.getRoot().getVerticalScrollBar().setValue(0);
        configsList.clearSelection();
    }
    
    private void loadModules(Module[] modules) {
        modulesList.setListData(modules);
        modulesList.getRoot().getVerticalScrollBar().setValue(0);
        modulesList.clearSelection();
    }
    
    private void openModuleEdit(Module mod) {
        if (mod != null) {
            ModuleEditFrame frame = new ModuleEditFrame(mod);
            frame.packFrame(ConfigFrame.this);
            frame.setVisible(true);
        }
    }
    
    private void openModuleEdit() {
        openModuleEdit(modulesList.getSelectedValue());
    }
    
    public void loadFolder(File file) {
        if (file.exists() && file.isDirectory()) {
            directoryField.setText(file.getPath());
            directoryField.setCaretPosition(0);
            loadConfigs(new ConfigFolder(file).getConfigsArray());
            loadModules(new Module[0]);
        }
    }
    
    public void loadFolder(String path) {
        loadFolder(new File(path));
    }
    
    public void loadFolder() {
        loadFolder(directoryField.getText());
    }

}
