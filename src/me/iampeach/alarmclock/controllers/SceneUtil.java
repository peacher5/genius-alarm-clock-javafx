package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.iampeach.alarmclock.models.AlarmItem;

import java.io.IOException;

public class SceneUtil {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 360;

    private SceneUtil() {
    }

    public static void loadMainScene(Stage window) {
        setScene(getLoader("../fxml/main_scene.fxml"), window);
        window.setMinWidth(WIDTH);
        window.setMinHeight(HEIGHT);
    }

    public static void loadAlarmFormScene(Stage window, AlarmItem alarmItem) {
        FXMLLoader loader = getLoader("../fxml/alarm_form_scene.fxml");
        setScene(loader, window);
        AlarmFormController controller = loader.getController();
        if (alarmItem != null)
            controller.setAlarmItem(alarmItem);
        controller.initUI();
    }

    public static void loadAlarmFormScene(Stage window) {
        loadAlarmFormScene(window, null);
    }

    private static void setScene(FXMLLoader viewLoader, Stage window) {
        try {
            window.setScene(new Scene(viewLoader.load(), WIDTH, HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FXMLLoader getLoader(String path) {
        return new FXMLLoader(SceneUtil.class.getResource(path));
    }
}
