import java.util.Scanner;
import java.nio.file.*;  
import java.io.IOException;

public class UserGreeting {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your age: ");
            int age = scanner.nextInt();

            String greeting = "Hello, " + name + "! You are " + age + " years old.";

            System.out.println("\n" + greeting);

            Path filePath = Paths.get("greeting.txt");
            Files.writeString(filePath, greeting);

            System.out.println("Your greeting has been saved to 'greeting.txt'.");
        } 
        catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
        }
        finally {
            scanner.close();
        }
    }
}

