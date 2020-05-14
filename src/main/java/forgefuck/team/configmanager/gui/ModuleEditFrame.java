package forgefuck.team.configmanager.gui;

import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import forgefuck.team.configmanager.objects.Module;

public class ModuleEditFrame extends ExtendFrame {
    
    private final JPanel globalPanel, localPanel;
    private final JButton acceptBut, cancelBut;
    private static ModuleEditFrame lastFrame;

    public ModuleEditFrame(Module module) {
        super(module.getName() + " - параметры", DISPOSE_ON_CLOSE);
        if (lastFrame != null) {
            lastFrame.dispose();
        }
        lastFrame = this;
        acceptBut = new JButton("Применить");
        cancelBut = new JButton("Отмена");
        globalPanel = new JPanel();
        localPanel = new JPanel();
        globalPanel.setBorder(BorderFactory.createEtchedBorder());
        localPanel.setBorder(BorderFactory.createEtchedBorder());
        globalPanel.setLayout(new GridBagLayout());
        localPanel.setLayout(new GridBagLayout());
        cancelBut.addActionListener((e) -> {
            dispose();
        });
        acceptBut.addActionListener((e) -> {
            if (getFields().allMatch(FieldPanel::checkInput)) {
                getFields().forEach(FieldPanel::setNewValue);
                dispose();
            }
        });
        module.getFields().forEach(f -> {
            FieldPanel fp = new FieldPanel(f);
            if (f.isLocal()) {
                localPanel.add(fp, GBC);
            } else {
                globalPanel.add(fp, GBC);
            }
        });
        buttonsBar.add(acceptBut);
        buttonsBar.add(cancelBut);
        add(globalPanel);
        if (module.hasLocal()) {
            add(localPanel);
        }
        add(buttonsBar);
    }
    
    public Stream<FieldPanel> getFields() {
        return Stream.concat(Arrays.stream(globalPanel.getComponents()), Arrays.stream(localPanel.getComponents())).filter(c -> c instanceof FieldPanel).map(c -> (FieldPanel) c);
    }

}
