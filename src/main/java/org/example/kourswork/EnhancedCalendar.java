package org.example.kourswork;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;

public class EnhancedCalendar extends Application {

    private final Map<LocalDate, List<Task>> tasks = new HashMap<>();
    private final List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Создаем основное окно приложения с вкладками
        TabPane tabPane = createTabPane();
        root.setCenter(tabPane);

        // Настройка сцены
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("Enhanced Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();

        // Вкладка Календарь
        Tab calendarTab = new Tab("Календарь", createCalendarTab());
        calendarTab.setClosable(false);

        // Вкладка Задачи
        Tab tasksTab = new Tab("Задачи", createTasksTab());
        tasksTab.setClosable(false);

        // Вкладка Контакты
        Tab contactsTab = new Tab("Контакты", createContactsTab());
        contactsTab.setClosable(false);

        // Добавляем вкладки в TabPane
        tabPane.getTabs().addAll(calendarTab, tasksTab, contactsTab);
        return tabPane;
    }

    private VBox createCalendarTab() {
        VBox calendarTab = new VBox(10);
        calendarTab.setPadding(new Insets(10));

        Label label = new Label("Календарь с задачами");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextArea taskDescription = new TextArea();
        taskDescription.setPromptText("Описание задачи");

        Button openTasksButton = new Button("Открыть задачи");
        openTasksButton.setOnAction(e -> openAddTaskWindow(datePicker.getValue()));  // Передаем выбранную дату

        calendarTab.getChildren().addAll(label, datePicker, taskDescription, openTasksButton);
        return calendarTab;
    }

    private VBox createTasksTab() {
        VBox tasksTab = new VBox(10);
        tasksTab.setPadding(new Insets(10));

        Label label = new Label("Управление задачами");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Список задач
        ListView<String> tasksList = new ListView<>();
        tasksList.getItems().addAll("Задача 1", "Задача 2"); // Пример

        Button addTaskButton = new Button("Добавить задачу");
        addTaskButton.setOnAction(e -> openAddTaskWindow(LocalDate.now()));  // Добавление задачи на сегодня

        tasksTab.getChildren().addAll(label, tasksList, addTaskButton);
        return tasksTab;
    }

    private VBox createContactsTab() {
        VBox contactsTab = new VBox(10);
        contactsTab.setPadding(new Insets(10));

        Label label = new Label("Контакты для задач");
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Список контактов
        ListView<String> contactsList = new ListView<>();
        contactsList.getItems().addAll("Контакт 1", "Контакт 2"); // Пример

        Button addContactButton = new Button("Добавить контакт");
        addContactButton.setOnAction(e -> openAddContactWindow());

        contactsTab.getChildren().addAll(label, contactsList, addContactButton);
        return contactsTab;
    }

    // Окно добавления задачи
    private void openAddTaskWindow(LocalDate selectedDate) {
        Stage taskStage = new Stage();
        taskStage.setTitle("Добавить задачу");

        // Панель для добавления задачи
        VBox taskVBox = new VBox(10);
        taskVBox.setPadding(new Insets(10));

        Label label = new Label("Описание задачи");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Описание задачи");

        Label categoryLabel = new Label("Категория");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Работа", "Личные дела", "Отдых");

        Label priorityLabel = new Label("Приоритет");
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Низкий", "Средний", "Высокий");

        Label contactLabel = new Label("Поделитесь с контактами");
        ComboBox<String> contactComboBox = new ComboBox<>();
        contactComboBox.getItems().addAll(getContactNames());

        Button saveButton = new Button("Сохранить задачу");

        saveButton.setOnAction(e -> {
            String description = descriptionField.getText();
            String category = categoryComboBox.getValue();
            String priority = priorityComboBox.getValue();
            String contact = contactComboBox.getValue();

            if (!description.isEmpty() && category != null && priority != null && contact != null) {
                Task newTask = new Task(description, category, priority, contact);

                // Добавляем задачу на выбранную дату
                tasks.computeIfAbsent(selectedDate, k -> new ArrayList<>()).add(newTask);
                taskStage.close();
            } else {
                // Сообщение об ошибке
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Не все поля заполнены");
                alert.setContentText("Пожалуйста, заполните все поля");
                alert.showAndWait();
            }
        });

        taskVBox.getChildren().addAll(label, descriptionField, categoryLabel, categoryComboBox, priorityLabel, priorityComboBox, contactLabel, contactComboBox, saveButton);

        Scene scene = new Scene(taskVBox, 400, 300);
        taskStage.setScene(scene);
        taskStage.show();
    }

    // Окно добавления контакта
    private void openAddContactWindow() {
        Stage contactStage = new Stage();
        contactStage.setTitle("Добавить контакт");

        // Панель для добавления контакта
        VBox contactVBox = new VBox(10);
        contactVBox.setPadding(new Insets(10));

        Label label = new Label("Имя контакта");
        TextField contactNameField = new TextField();
        contactNameField.setPromptText("Введите имя");

        Button saveButton = new Button("Сохранить контакт");

        saveButton.setOnAction(e -> {
            String contactName = contactNameField.getText();
            if (!contactName.isEmpty()) {
                contacts.add(new Contact(contactName));
                contactStage.close();
            } else {
                // Сообщение об ошибке
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Имя контакта не введено");
                alert.setContentText("Пожалуйста, введите имя контакта");
                alert.showAndWait();
            }
        });

        contactVBox.getChildren().addAll(label, contactNameField, saveButton);

        Scene scene = new Scene(contactVBox, 400, 200);
        contactStage.setScene(scene);
        contactStage.show();
    }

    // Получить список имен контактов для отображения в выпадающем списке
    private List<String> getContactNames() {
        List<String> contactNames = new ArrayList<>();
        for (Contact contact : contacts) {
            contactNames.add(contact.getName());
        }
        return contactNames;
    }

    // Класс задачи
    public static class Task {
        private String description;
        private String category;
        private String priority;
        private String contact;

        public Task(String description, String category, String priority, String contact) {
            this.description = description;
            this.category = category;
            this.priority = priority;
            this.contact = contact;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public String getPriority() {
            return priority;
        }

        public String getContact() {
            return contact;
        }
    }

    // Класс контакта
    public static class Contact {
        private String name;

        public Contact(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
