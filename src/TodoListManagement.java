import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TodoListManagement {


    public static void showTask() throws SQLException {
        Connection connection = DatabaseConfig.getConnection();
        String selectTask = "SELECT * FROM task";

        PreparedStatement preparedStatement = connection.prepareStatement(selectTask);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            int state = resultSet.getInt("state");
            String importance = resultSet.getString("importance");


            String stateString = (state == 0) ? "Incompleto" : "Completo";


            System.out.println("ID: " + id);
            System.out.println("Titolo: " + title);
            System.out.println("Descrizione: " + description);
            System.out.println("Stato: " + stateString);
            System.out.println("Importanza: " + importance);

        }

    }

    public static void createTable() throws SQLException {
        String createTable = "CREATE TABLE task ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(100), "
                + "description  VARCHAR(100), "
                + "state BOOLEAN DEFAULT 0 , "
                + "importance ENUM('high','medium','low')"
                + ")";

        Connection connection = DatabaseConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.executeUpdate();
    }

    public static void touchTask() throws SQLException {
        String insertTask = "INSERT INTO task(title,description,importance) VALUES(?,?,?)";
        Connection connection = DatabaseConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertTask);
        Scanner scanner = new Scanner(System.in);


        System.out.println("Inserisci il titolo della task");
        String title = scanner.nextLine();
        System.out.println("Inserisci la descrizione ");
        String description = scanner.nextLine();
        System.out.println("Inserisci l'importanza \n1. high \n2. medium \n3. low");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String importance = "";
        switch (choice) {
            case 1:
                importance = "high";
                break;

            case 2:
                importance = "medium";
                break;

            case 3:
                importance = "low";
                break;
            default:
                System.out.println("Scelta sbagliata, imposto automaticamente in default (low)");
                importance = "low";

        }
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, importance);
        preparedStatement.executeUpdate();
        System.out.println("Task aggiunta con successo!");

    }

    public static void editTask() throws SQLException {
        Connection connection = DatabaseConfig.getConnection();
        Scanner scanner = new Scanner(System.in);
        String allTask = "SELECT * FROM task";

        System.out.println("Inserisci il titolo della task da modificare");
        String titleTask = scanner.nextLine();


        String findTask = "SELECT * FROM task WHERE title=?";
        PreparedStatement preparedStatementFind = connection.prepareStatement(findTask);


        preparedStatementFind.setString(1, titleTask);
        ResultSet resultSet = preparedStatementFind.executeQuery();


        if (resultSet.next()) {
            System.out.println("task trovato inserisci le modifiche");

            System.out.println("Nuoo titolo");
            String newTitle = scanner.nextLine();

            System.out.println("Nuova descrizione");
            String newDescription = scanner.nextLine();

            System.out.println("setta stato");
            String newState = scanner.nextLine();

            System.out.println("nuova importanza");
            String newImportance = scanner.nextLine();

            String editQuery = "UPDATE task SET title = ?, description = ?, state = ?, importance = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(editQuery);

            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newDescription);
            preparedStatement.setString(3, newState);
            preparedStatement.setString(4, newImportance);

            int id = resultSet.getInt("id");
            preparedStatement.setInt(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("modifica avvenuta ");
            } else {
                System.out.println("nessuna modifica avvenuta ");
            }

        } else {
            System.out.println("Task non trovata ");
        }


    }

    public static void removeTask() throws SQLException {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Inserisci l'ID della task da eliminare: ");
        int taskId = scanner.nextInt();


        String deleteQuery = "DELETE FROM task WHERE id = ?";
        Connection connection = DatabaseConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, taskId);


        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Task eliminata con successo.");
        } else {
            System.out.println("Nessuna task trovata con l'ID fornito.");
        }


    }

    public static void setTaskStatus() throws SQLException {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Inserisci l'ID della task da modificare lo stato: ");
        int taskId = scanner.nextInt();


        System.out.print("Inserisci lo stato della task (0 = incompleto, 1 = completo): ");
        int newState = scanner.nextInt();

        if (newState != 0 && newState != 1) {
            System.out.println("Stato non valido. Devi inserire 0 o 1.");
            return;
        }


        String updateStateQuery = "UPDATE task SET state = ? WHERE id = ?";
        Connection connection = DatabaseConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateStateQuery);
        preparedStatement.setInt(1, newState);
        preparedStatement.setInt(2, taskId);


        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Stato della task aggiornato con successo.");
        } else {
            System.out.println("Nessuna task trovata con l'ID fornito.");
        }


    }


}
