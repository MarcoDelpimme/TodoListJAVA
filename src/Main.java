import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            int scelta = 0;

            while (true) {
                System.out.println("\n--- TODOLIST MENU ---");
                System.out.println("1. Task list");
                System.out.println("2. Add Task");
                System.out.println("3. Edit task");
                System.out.println("4. Remove task");
                System.out.println("5. Set task status");
                System.out.println("6. Exit todolist APP");
                System.out.print("Inserisci la tua scelta: ");
                scelta = scanner.nextInt();

                switch (scelta) {
                    case 1:
                        TodoListManagement.showTask();
                        break;
                    case 2:
                        TodoListManagement.touchTask();
                        break;
                    case 3:
                        TodoListManagement.showTask();
                        TodoListManagement.editTask();
                        break;
                    case 4:
                        TodoListManagement.removeTask();
                        break;
                    case 5:
                        TodoListManagement.setTaskStatus();
                        break;
                    case 6:
                        System.out.println("Uscita dal programma...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            }
        } catch (Exception e) {
            System.out.println("Aia, è andato qualcosa storto");
            e.printStackTrace();
        }
    }
}

