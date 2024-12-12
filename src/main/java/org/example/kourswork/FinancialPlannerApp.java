package org.example.kourswork;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialPlannerApp extends Application {

    private List<Transaction> transactions = new ArrayList<>();
    private Map<String, Double> budgetLimits = new HashMap<>();
    private VBox mainPanel;

    @Override
    public void start(Stage primaryStage) {
        // Основной макет
        BorderPane root = new BorderPane();

        // Навигационная панель
        VBox navPanel = createNavPanel();
        mainPanel = new VBox();
        mainPanel.setStyle("-fx-padding: 20;");

        // Установка основных панелей
        root.setLeft(navPanel);
        root.setCenter(mainPanel);

        // Настройка сцены
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Финансовый планировщик");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createNavPanel() {
        VBox navPanel = new VBox(15);
        navPanel.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");

        Button financeButton = createNavButton("Финансовый план");
        Button historyButton = createNavButton("История транзакций");
        Button reportButton = createNavButton("Отчёты");
        Button chartButton = createNavButton("Графики");

        financeButton.setOnAction(e -> showFinancePlanner());
        historyButton.setOnAction(e -> showTransactionHistory());
        reportButton.setOnAction(e -> showMonthlyReport());
        chartButton.setOnAction(e -> showCharts());

        navPanel.getChildren().addAll(financeButton, historyButton, reportButton, chartButton);
        return navPanel;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white;");
        button.setMaxSize(200, 40);
        button.setMinSize(200, 40);
        return button;
    }

    private void showFinancePlanner() {
        mainPanel.getChildren().clear();

        Label title = new Label("Финансовый планировщик");
        title.setStyle("-fx-font-size: 24px;");

        TextField incomeField = new TextField();
        incomeField.setPromptText("Введите доход");

        TextField expenseField = new TextField();
        expenseField.setPromptText("Введите расход");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Категория");

        Button addIncomeButton = new Button("Добавить доход");
        Button addExpenseButton = new Button("Добавить расход");

        addIncomeButton.setOnAction(e -> addTransaction("Доход", incomeField, categoryField));
        addExpenseButton.setOnAction(e -> addTransaction("Расход", expenseField, categoryField));

        mainPanel.getChildren().addAll(title, incomeField, expenseField, categoryField, addIncomeButton, addExpenseButton);
    }

    private void addTransaction(String type, TextField amountField, TextField categoryField) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryField.getText();

            if (category.isEmpty()) {
                showError("Введите категорию");
                return;
            }

            transactions.add(new Transaction(type, amount, category));
            amountField.clear();
            categoryField.clear();
            showInfo("Транзакция добавлена: " + type + " - " + amount + " - " + category);
        } catch (NumberFormatException e) {
            showError("Неверный формат суммы");
        }
    }

    private void showTransactionHistory() {
        mainPanel.getChildren().clear();

        Label title = new Label("История транзакций");
        title.setStyle("-fx-font-size: 24px;");

        ListView<String> transactionList = new ListView<>();
        for (Transaction transaction : transactions) {
            transactionList.getItems().add(transaction.toString());
        }

        mainPanel.getChildren().addAll(title, transactionList);
    }

    private void showMonthlyReport() {
        mainPanel.getChildren().clear();

        Label title = new Label("Отчёты за месяц");
        title.setStyle("-fx-font-size: 24px;");

        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);

        double totalIncome = transactions.stream().filter(t -> t.type.equals("Доход")).mapToDouble(t -> t.amount).sum();
        double totalExpense = transactions.stream().filter(t -> t.type.equals("Расход")).mapToDouble(t -> t.amount).sum();

        reportArea.setText("Общий доход: " + totalIncome + "\n" +
                "Общие расходы: " + totalExpense + "\n" +
                "Баланс: " + (totalIncome - totalExpense));

        mainPanel.getChildren().addAll(title, reportArea);
    }

    private void showCharts() {
        mainPanel.getChildren().clear();

        Label title = new Label("Графики расходов по категориям");
        title.setStyle("-fx-font-size: 24px;");

        PieChart chart = new PieChart();

        Map<String, Double> expenseByCategory = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.type.equals("Расход")) {
                expenseByCategory.put(transaction.category, expenseByCategory.getOrDefault(transaction.category, 0.0) + transaction.amount);
            }
        }

        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            chart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        mainPanel.getChildren().addAll(title, chart);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class Transaction {
        String type;
        double amount;
        String category;

        public Transaction(String type, double amount, String category) {
            this.type = type;
            this.amount = amount;
            this.category = category;
        }

        @Override
        public String toString() {
            return type + ": " + amount + " (" + category + ")";
        }
    }
}
