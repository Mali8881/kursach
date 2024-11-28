package org.example.kourswork;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class PersonalManager extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Размеры экрана
        double screenWidth = 1920;
        double screenHeight = 1080;

        // Основной макет
        BorderPane root = new BorderPane();

        // Панель навигации (слева)
        VBox navPanel = createNavPanel();

        // Основная рабочая панель (центральная)
        StackPane mainPanel = new StackPane();
        mainPanel.setStyle("-fx-background-color: #f0f0f0;");

        // Изменение контента в зависимости от выбора
        navPanel.setOnMouseClicked(event -> mainPanel.getChildren().setAll(createTaskWidget())); // Начнем с задач

        // Устанавливаем панели
        root.setLeft(navPanel);
        root.setCenter(mainPanel);

        // Настройка сцены
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setTitle("Personal Manager Application");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private VBox createNavPanel() {
        VBox navPanel = new VBox(15);
        navPanel.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20; -fx-alignment: center;");

        // Кнопки навигации
        Button taskButton = new Button("Задачи");
        Button calendarButton = new Button("Календарь");
        Button contactsButton = new Button("Контакты");
        Button financeButton = new Button("Финансы");

        styleButton(taskButton);
        styleButton(calendarButton);
        styleButton(contactsButton);
        styleButton(financeButton);

        navPanel.getChildren().addAll(taskButton, calendarButton, contactsButton, financeButton);

        // Обработчики для кнопок навигации
        taskButton.setOnAction(e -> setMainPanel(createTaskWidget()));
        calendarButton.setOnAction(e -> setMainPanel(createCalendarWidget()));
        contactsButton.setOnAction(e -> setMainPanel(createContactsWidget()));
        financeButton.setOnAction(e -> setMainPanel(createFinanceWidget()));

        return navPanel;
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white;");
        button.setMaxSize(200, 40);
        button.setMinSize(200, 40);
    }

    private void setMainPanel(Pane panel) {
        StackPane mainPanel = new StackPane();
        mainPanel.getChildren().setAll(panel);
    }

    // Виджет задач
    private VBox createTaskWidget() {
        VBox taskWidget = new VBox(10);
        taskWidget.setStyle("-fx-background-color: lightgray; -fx-padding: 10;");

        // Задачи: добавление, редактирование, удаление
        TextField newTaskField = new TextField();
        newTaskField.setPromptText("Введите описание задачи");

        Button addTaskButton = new Button("Добавить задачу");
        addTaskButton.setStyle("-fx-background-color: lightgreen;");

        ListView<String> taskListView = new ListView<>();
        taskListView.setPrefHeight(300);

        // Добавление задачи
        addTaskButton.setOnAction(e -> {
            String task = newTaskField.getText();
            if (!task.isEmpty()) {
                taskListView.getItems().add(task);
                newTaskField.clear(); // Очистка поля
            }
        });

        taskWidget.getChildren().addAll(newTaskField, addTaskButton, taskListView);
        return taskWidget;
    }

    // Виджет календаря
    private VBox createCalendarWidget() {
        VBox calendarWidget = new VBox(10);
        calendarWidget.setStyle("-fx-background-color: lightblue; -fx-padding: 10;");

        Label label = new Label("Календарь");
        label.setStyle("-fx-font-size: 24px;");

        calendarWidget.getChildren().addAll(label);
        return calendarWidget;
    }

    // Виджет контактов
    private VBox createContactsWidget() {
        VBox contactsWidget = new VBox(10);
        contactsWidget.setStyle("-fx-background-color: lightyellow; -fx-padding: 10;");

        Label label = new Label("Контакты");
        label.setStyle("-fx-font-size: 24px;");

        contactsWidget.getChildren().addAll(label);
        return contactsWidget;
    }

    // Виджет финансов
    private VBox createFinanceWidget() {
        VBox financeWidget = new VBox(10);
        financeWidget.setStyle("-fx-background-color: lightcoral; -fx-padding: 10;");

        Label label = new Label("Финансовый планировщик");
        label.setStyle("-fx-font-size: 24px;");

        financeWidget.getChildren().addAll(label);
        return financeWidget;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
