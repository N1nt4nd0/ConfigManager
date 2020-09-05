package forgefuck.team.configmanager.gui;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ScrollingList<T> extends JList<T> {
    
    private final JScrollPane rootScroll;
    private final Comparator<T> sorter;
    
    public ScrollingList(int itemsCount, ListSorting sorting) {
        this(null, itemsCount, sorting);
    }
    
    public ScrollingList(String title, int itemsCount, ListSorting sorting) {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setVisibleRowCount(itemsCount);
        rootScroll = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        if (title != null) {
            setTitle(title);
        }
        switch (sorting) {
        case ALPHABET:
            sorter = Comparator.comparing(Objects::toString);
            break;
        case WIDTH:
            sorter = Comparator.comparing(i -> i.toString().length());
            break;
        case NONE:
        default:
            sorter = Comparator.comparing(Objects::nonNull);
        }
    }
    
    public void setTitle(String title) {
        rootScroll.setBorder(ExtendFrame.customTitledBorder(title));
    }
    
    public void setListData(T[] list) {
        Collections.sort(Arrays.asList(list), sorter);
        super.setListData(list);
    }
    
    public JScrollPane getRoot() {
        return rootScroll;
    }

}
