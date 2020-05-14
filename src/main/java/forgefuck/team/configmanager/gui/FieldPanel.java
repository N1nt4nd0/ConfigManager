package forgefuck.team.configmanager.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import forgefuck.team.configmanager.misc.Parser;
import forgefuck.team.configmanager.objects.ModuleField;

public class FieldPanel extends JPanel {
    
    private final JTextField editField;
    private final JTextArea listArea;
    private final ModuleField field;
    private final boolean isList;
    
    public FieldPanel(ModuleField field) {
        this.field = field;
        listArea = new JTextArea(5, 18);
        JScrollPane areaScroll = new JScrollPane(listArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel label = new JLabel(" " + field.getName() + " ");
        editField = new JTextField(field.getValue(), 20);
        isList = field.getType().contains("List") || field.getType().contains("Map");
        KeyListener checkListener = new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {
                checkInput();
            }
        };
        listArea.setFont(ExtendFrame.FONT);
        listArea.setLineWrap(true);
        listArea.addKeyListener(checkListener);
        listArea.setText(field.getValue());
        editField.setFont(ExtendFrame.FONT);
        editField.addKeyListener(checkListener);
        areaScroll.setBorder(BorderFactory.createEtchedBorder());
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setToolTipText(field.getType());
        add(label);
        if (isList) {
            add(areaScroll);
        } else {
            add(editField);
        }
        checkInput();
    }
    
    public void setNewValue() {
        setNewValue(getTextValue());
    }
    
    public void setNewValue(String newVal) {
        field.setValue(newVal);
    }
    
    public String getTextValue() {
        return isList ? listArea.getText() : editField.getText();
    }
    
    public boolean checkInput() {
        JTextComponent toCheck = isList ? listArea : editField;
        boolean checked = Parser.parseFieldValue(field, toCheck.getText()) != null;
        toCheck.setBackground(checked ? ExtendFrame.WHITE : ExtendFrame.FAIL);
        return checked;
    }
    
}
