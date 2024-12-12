package org.example.kourswork;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.application.Application;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderService extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Логика для ReminderService (по сути, сцена не используется)
        primaryStage.setTitle("Reminder Service");
        primaryStage.show();
    }

    // Метод для добавления напоминаний
    public void addReminder(LocalDateTime reminderTime, String message) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    showReminderAlert(message);
                });
            }
        };

        // Напоминание за 5 минут до события
        long delay = reminderTime.minusMinutes(5).toEpochSecond(ZoneOffset.UTC) * 1000;
        timer.schedule(task, delay);
    }

    private void showReminderAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Напоминание");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // Запуск приложения JavaFX (если нужно)
        launch(args);
    }
}
