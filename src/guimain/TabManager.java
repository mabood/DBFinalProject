package guimain;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabManager {
    static TabPane tabs = new TabPane();
    static final int TAB_MAX = 4;
    static final int BEERS_INDEX = 0;
    static final int BREWERIES_INDEX = 1;
    static final int BARS_INDEX = 2;
    private static GenericTableView[] pages = new GenericTableView[TAB_MAX];


    public static int addTab(String tabTitle, GenericTableView view, boolean closeable) {
        Tab tab = new Tab();
        tab.setText(tabTitle);
        tab.setContent(view.CreateLayout());
        tab.setClosable(closeable);

        if (tabs.getTabs().size() >= TAB_MAX) {
            tabs.getTabs().remove(TAB_MAX - 1);
        }

        pages[tabs.getTabs().size()] = view;
        tabs.getTabs().add(tab);
        return tabs.getTabs().size() - 1;
    }

    public static void setActiveTab(int index) {
        tabs.getSelectionModel().select(index);
    }

    public static void refreshTab(int index) {
        pages[index].updateTable(null);
    }


}
