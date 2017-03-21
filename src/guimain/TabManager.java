package guimain;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabManager {
    static TabPane tabs = new TabPane();

    public static void addTab(String tabTitle, GenericTableView view, boolean closeable) {
        Tab tab = new Tab();
        tab.setText(tabTitle);
        tab.setContent(view.CreateLayout());
        tab.setClosable(closeable);
        tabs.getTabs().add(tab);
    }


}
