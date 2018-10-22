package me.iampeach.alarmclock.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ComponentUtil {

    private ComponentUtil() {
    }

    static void loadFXML(Pane pane, String fileName) {
        FXMLLoader loader = new FXMLLoader(ComponentUtil.class.getResource("/me/iampeach/alarmclock/fxml/" + fileName));
        loader.setRoot(pane);
        loader.setController(pane);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImagePath(String fileName) {
        return "me/iampeach/alarmclock/images/" + fileName;
    }
}
