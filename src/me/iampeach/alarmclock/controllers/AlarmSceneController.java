package me.iampeach.alarmclock.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import me.iampeach.alarmclock.components.ActionButton;
import me.iampeach.alarmclock.components.ComponentUtil;
import me.iampeach.alarmclock.components.RoundButton;
import me.iampeach.alarmclock.models.MathProblem;

public class AlarmSceneController implements SceneController {
    @FXML
    private Label title, mathProblemLabel;
    @FXML
    private HBox answerContainer;
    @FXML
    private TextField answerTextField;
    @FXML
    private AnchorPane root;

    private MathProblem problem = new MathProblem();
    private ActionButton answerButton = new ActionButton("ตอบ", ComponentUtil.getImage("send.png"), "answer-button");
    private ActionButton incorrectButton = new ActionButton("ผิด!", ComponentUtil.getImage("incorrect.png"), "incorrect-button");
    private Stage window;

    // Make it global to prevent GC clean this during in the scene
    private MediaPlayer mediaPlayer;

    public void setTitle(String text) {
        title.setText(text);
    }

    @Override
    public void initUI() {
        window = (Stage) root.getScene().getWindow();

        mathProblemLabel.setText(problem.toString() + " =");

        answerTextField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (answerContainer.getChildren().contains(incorrectButton))
                setIncorrectButtonVisible(false);
            if (!newValue.matches("\\d{0,2}|100"))
                answerTextField.setText(oldValue);
        });

        answerTextField.setOnAction(event -> onAnswer());

        answerButton.setOnMouseClicked(event -> onAnswer());
        answerContainer.getChildren().add(answerButton);

        // Play alarm sound
        if (!title.getText().equals("Math Challenges")) {
            Media media = new Media(getClass().getResource("/me/iampeach/alarmclock/sounds/alarm.mp3").toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            // Stop sound when close the window
            window.setOnHiding(event -> mediaPlayer.stop());
        }
    }

    private void onAnswer() {
        int answer;
        try {
            answer = Integer.parseInt(answerTextField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        if (problem.getAnswer() == answer) {
            answerContainer.getChildren().clear();

            VBox container = new VBox();

            Label correctLabel = new Label("ถูกต้อง! คำตอบคือ " + problem.getAnswer());
            correctLabel.getStyleClass().add("correct-text");

            RoundButton closeButton = new RoundButton("ปิดหน้าต่าง", ComponentUtil.getImage("cancel.png"));
            closeButton.setOnMouseClicked(event -> window.close());

            container.getChildren().addAll(correctLabel, closeButton);
            answerContainer.getChildren().add(container);

        } else {
            setIncorrectButtonVisible(true);
        }
    }

    private void setIncorrectButtonVisible(boolean toggle) {
        if (toggle) {
            answerContainer.getChildren().remove(answerButton);
            answerContainer.getChildren().add(incorrectButton);
        } else {
            answerContainer.getChildren().remove(incorrectButton);
            answerContainer.getChildren().add(answerButton);
        }
    }
}
