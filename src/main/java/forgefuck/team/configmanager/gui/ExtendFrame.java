package forgefuck.team.configmanager.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import forgefuck.team.configmanager.misc.Images;

public abstract class ExtendFrame extends JFrame {
    
    public static final Border BORDER = BorderFactory.createLineBorder(Color.BLACK);
    public static final GridBagConstraints GBC = new GridBagConstraints();
    public static final Font FONT = new Font("Terminal", Font.BOLD, 12);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color FAIL = new Color(255, 200, 200);
    protected final JToolBar buttonsBar;
    
    public ExtendFrame(String title) {
        this(title, EXIT_ON_CLOSE);
    }

    public ExtendFrame(String title, int closeOperation) {
        super(title);
        buttonsBar = new JToolBar();
        buttonsBar.setFloatable(false);
        GBC.gridwidth = GridBagConstraints.REMAINDER;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(closeOperation);
        setIconImage(Images.ICON);
        setResizable(false);
    }
    
    public boolean confirmMessage(Object message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION) == 0 ? true : false;
    }
    
    public void okMessage(Object message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void errMessage(Object message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static Border customTitledBorder(String title, int align) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleJustification(align);
        return border;
    }
    
    public static Border customTitledBorder(String title) {
        return customTitledBorder(title, TitledBorder.CENTER);
    }
    
    @Override public void setVisible(boolean visible) {
        if (visible) {
            localizeSet();
            pack();
            setLocationRelativeTo(null);
        }
        super.setVisible(visible);
    }
    
    public abstract void localizeSet();

}
