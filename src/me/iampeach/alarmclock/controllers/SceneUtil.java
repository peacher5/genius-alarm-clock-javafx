package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.iampeach.alarmclock.models.AlarmItem;

import java.io.IOException;

public final class SceneUtil {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 350;

    private SceneUtil() {
    }

    public static void launchAlarmWindow(String title) {
        Stage window = new Stage();
        SceneUtil.loadAlarmScene(window, title);
        SceneUtil.setMinSize(window);
        window.show();
    }

    public static void setMinSize(Stage window) {
        window.setMinWidth(SceneUtil.WIDTH);
        window.setMinHeight(SceneUtil.HEIGHT + 22);
    }

    public static void loadMainScene(Stage window) {
        loadScene(window, "main_scene.fxml");
    }

    public static void loadAlarmFormScene(Stage window, AlarmItem alarmItem) {
        loadScene(window, "alarm_form_scene.fxml", controller -> {
            if (alarmItem != null)
                ((AlarmFormSceneController) controller).setAlarmItem(alarmItem);
        });
    }

    public static void loadAlarmFormScene(Stage window) {
        loadAlarmFormScene(window, null);
    }

    public static void loadAlarmScene(Stage window, String title) {
        loadScene(window, "alarm_scene.fxml", controller -> ((AlarmSceneController) controller).setTitle(title));
    }

    public static void loadAboutScene(Stage window) {
        loadScene(window, "about_scene.fxml");
    }

    private static void loadScene(Stage window, String fileName, OnInitSceneCallback callback) {
        FXMLLoader viewLoader = new FXMLLoader(SceneUtil.class.getResource("/me/iampeach/alarmclock/fxml/" + fileName));
        try {
            window.setScene(new Scene(viewLoader.load(), WIDTH, HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SceneController controller = viewLoader.getController();

        if (callback != null)
            callback.onInit(controller);

        controller.initUI();
    }

    private static void loadScene(Stage window, String fileName) {
        loadScene(window, fileName, null);
    }
}
