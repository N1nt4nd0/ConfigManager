package forgefuck.team.configmanager.gui;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import forgefuck.team.configmanager.misc.Lang;
import forgefuck.team.configmanager.objects.ConfigFile;
import forgefuck.team.configmanager.objects.ConfigFolder;
import forgefuck.team.configmanager.objects.Module;

public class ConfigFrame extends ExtendFrame {

    private final JButton chooseFolderBut, saveBut, deleteBut, updateFolderBut, openFolderBut, createBackupBut;
    private final ScrollingList<ConfigFile> configsList;
    private final ScrollingList<Module> modulesList;
    private final JToolBar directoryBar, editorBar;
    private final JTextField directoryField;
    private ModuleEditFrame lastEditFrame;
    private final JRadioButton eng, rus;
    private final ButtonGroup langs;
    
    public ConfigFrame() {
        super("Xenobyte Config Manager");
        modulesList = new ScrollingList<Module>(15, ListSorting.ALPHABET);
        configsList = new ScrollingList<ConfigFile>(5, ListSorting.NONE);
        directoryField = new JTextField(30);
        createBackupBut = new JButton();
        chooseFolderBut = new JButton();
        updateFolderBut = new JButton();
        eng = new JRadioButton("ENG");
        rus = new JRadioButton("RUS");
        directoryBar = new JToolBar();
        openFolderBut = new JButton();
        editorBar = new JToolBar();
        deleteBut = new JButton();
        langs = new ButtonGroup();
        saveBut = new JButton();
        updateFolderBut.addActionListener(e -> loadFolder());
        directoryField.setEditable(false);
        directoryBar.setFloatable(false);
        editorBar.setFloatable(false);
        directoryField.setFont(FONT);
        eng.setSelected(true);
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
            if (conf != null && confirmMessage(Lang.get("Delete", "Удалить") + " " + conf + "?") && conf.delete()) {
                loadFolder();
            }
        });
        saveBut.addActionListener((e) -> {
            ConfigFile conf = configsList.getSelectedValue();
            if (conf != null) {
                conf.save();
            }
        });
        eng.addActionListener((e) -> {
            setLanguage(Lang.ENG);
        });
        rus.addActionListener((e) -> {
            setLanguage(Lang.RUS);
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
        buttonsBar.add(eng);
        buttonsBar.add(rus);
        langs.add(eng);
        langs.add(rus);
        add(directoryBar);
        add(editorBar);
        add(buttonsBar);
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
    
    private void setLanguage(Lang lang) {
        Lang.set(lang);
        localizeSet();
        loadFolder();
    }
    
    private void closeModuleEdit() {
        if (lastEditFrame != null) {
            lastEditFrame.dispose();
            lastEditFrame = null;
        }
    }
    
    private void openModuleEdit(Module mod) {
        if (mod != null) {
            closeModuleEdit();
            lastEditFrame = new ModuleEditFrame(mod);
            lastEditFrame.setVisible(true);
        }
    }
    
    private void openModuleEdit() {
        openModuleEdit(modulesList.getSelectedValue());
    }
    
    public void loadFolder(File file) {
        if (file.exists() && file.isDirectory()) {
            closeModuleEdit();
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

    @Override public void localizeSet() {
        directoryBar.setBorder(customTitledBorder(Lang.get("Working directory", "Рабочая папка"), TitledBorder.LEFT));
        createBackupBut.setText(Lang.get("Save to backup", "Сохранить в бэкап"));
        chooseFolderBut.setText(Lang.get("Choose folder", "Выбрать папку"));
        deleteBut.setText(Lang.get("Delete config", "Удалить конфиг"));
        configsList.setTitle(Lang.get("Configs", "Конфигурации"));
        updateFolderBut.setText(Lang.get("Refresh", "Обновить"));
        modulesList.setTitle(Lang.get("Modules", "Модули"));
        openFolderBut.setText(Lang.get("Open", "Открыть"));
        saveBut.setText(Lang.get("Save", "Сохранить"));
    }

}
