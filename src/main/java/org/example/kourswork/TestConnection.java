package org.example.kourswork;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {




        private static final String DB_URL = "jdbc:sqlite:C:/databases/personal_manager.db"; // Убедитесь, что путь корректный

        public static Connection connect() throws SQLException {
            // Подключаемся к базе данных
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection established to " + DB_URL);
            return conn;
        }

        public static void createTable() {
            // SQL запрос для создания таблицы пользователей
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT NOT NULL, "
                    + "email TEXT NOT NULL);";

            try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);  // Выполняем SQL запрос
                System.out.println("Table created or already exists.");
            } catch (SQLException e) {
                System.err.println("Error creating table: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            createTable();  // Создание таблицы
        }
    }
