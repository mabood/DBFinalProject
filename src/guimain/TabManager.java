package guimain;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class TabManager {
    static TabPane tabs = new TabPane();
    static final int TAB_MAX = 4;


    public static int addTab(String tabTitle, GenericTableView view, boolean closeable) {
        Tab tab = new Tab();
        tab.setText(tabTitle);
        tab.setContent(view.CreateLayout());
        tab.setClosable(closeable);

        if (tabs.getTabs().size() >= TAB_MAX) {
            tabs.getTabs().remove(TAB_MAX - 1);
        }

        tabs.getTabs().add(tab);
        return tabs.getTabs().size() - 1;
    }

    public static void setActiveTab(int index) {
        tabs.getSelectionModel().select(index);
    }


}
