package guimain;

import javafx.scene.control.Alert;

public class CriticalAlertBox extends AlertBox {
    public static void displayAndQuit(String title, String message) {
        if (display(title, message)) {
            System.exit(1);
        }

    }
}
