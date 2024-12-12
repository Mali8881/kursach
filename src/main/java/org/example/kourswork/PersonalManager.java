package org.example.kourswork;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PersonalManager extends Application {

    private VBox mainPanel;
    private List<User> users = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Основной макет
        BorderPane root = new BorderPane();

        // Панель навигации
        VBox navPanel = createNavPanel();
        mainPanel = new VBox();
        mainPanel.setStyle("-fx-padding: 20;");

        // Панель регистрации
        createRegistrationPanel();

        // Установка панелей
        root.setLeft(navPanel);
        root.setCenter(mainPanel);

        // Настройка сцены
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Personal Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createNavPanel() {
        VBox navPanel = new VBox(15);
        navPanel.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");

        Button registrationButton = createNavButton("Регистрация");
        registrationButton.setOnAction(e -> createRegistrationPanel());

        navPanel.getChildren().addAll(registrationButton);
        return navPanel;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white;");
        button.setMaxSize(200, 40);
        button.setMinSize(200, 40);
        return button;
    }

    private void createHomePage() {
        mainPanel.getChildren().clear();

        Label lblHome = new Label("Добро пожаловать в Personal Manager!");
        lblHome.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button profileButton = new Button("Профиль");
        profileButton.setOnAction(e -> createProfilePage());

        Button tasksButton = new Button("Задачи");
        tasksButton.setOnAction(e -> createTasksPage());

        Button settingsButton = new Button("Настройки");
        settingsButton.setOnAction(e -> createSettingsPage());

        Button logoutButton = new Button("Выйти");
        logoutButton.setOnAction(e -> createRegistrationPanel());

        VBox homePanel = new VBox(10, lblHome, profileButton, tasksButton, settingsButton, logoutButton);
        homePanel.setPadding(new Insets(20));
        homePanel.setStyle("-fx-background-color: #ecf0f1;");

        mainPanel.getChildren().add(homePanel);
    }

    private void createProfilePage() {
        mainPanel.getChildren().clear();

        Label lblProfile = new Label("Страница профиля");
        lblProfile.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> createHomePage());

        VBox profilePanel = new VBox(10, lblProfile, backButton);
        profilePanel.setPadding(new Insets(20));
        profilePanel.setStyle("-fx-background-color: #ecf0f1;");

        mainPanel.getChildren().add(profilePanel);
    }

    private void createTasksPage() {
        mainPanel.getChildren().clear();

        Label lblTasks = new Label("Страница задач");
        lblTasks.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> createHomePage());

        VBox tasksPanel = new VBox(10, lblTasks, backButton);
        tasksPanel.setPadding(new Insets(20));
        tasksPanel.setStyle("-fx-background-color: #ecf0f1;");

        mainPanel.getChildren().add(tasksPanel);
    }

    private void createSettingsPage() {
        mainPanel.getChildren().clear();

        Label lblSettings = new Label("Страница настроек");
        lblSettings.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button backButton = new Button("Назад");
        backButton.setOnAction(e -> createHomePage());

        VBox settingsPanel = new VBox(10, lblSettings, backButton);
        settingsPanel.setPadding(new Insets(20));
        settingsPanel.setStyle("-fx-background-color: #ecf0f1;");

        mainPanel.getChildren().add(settingsPanel);
    }

    private void createRegistrationPanel() {
        mainPanel.getChildren().clear();

        Label lblRegister = new Label("Регистрация пользователя:");
        TextField txtName = new TextField();
        txtName.setPromptText("Имя");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Пароль");

        PasswordField txtConfirmPassword = new PasswordField();
        txtConfirmPassword.setPromptText("Подтвердите пароль");

        Button btnRegister = new Button("Зарегистрироваться");
        btnRegister.setOnAction(e -> {
            String name = txtName.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Все поля должны быть заполнены.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Пароли не совпадают.");
                return;
            }

            users.add(new User(name, email, password));
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Регистрация прошла успешно.");
            createHomePage(); // Переход на главную страницу
        });

        VBox registrationPanel = new VBox(10, lblRegister, txtName, txtEmail, txtPassword, txtConfirmPassword, btnRegister);
        registrationPanel.setPadding(new Insets(20));
        registrationPanel.setStyle("-fx-background-color: #ecf0f1;");

        mainPanel.getChildren().add(registrationPanel);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
