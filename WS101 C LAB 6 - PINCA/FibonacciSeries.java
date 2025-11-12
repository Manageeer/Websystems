import java.util.Scanner;

public class FibonacciSeries {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of terms (N): ");
        int N = sc.nextInt();
        
        int first = 0, second = 1;
        
        System.out.println("Fibonacci Series up to " + N + " terms:");
        
        for (int i = 1; i <= N; i++) {
            System.out.print(first + " ");
            int next = first + second;
            first = second;
            second = next;
        }
        
        sc.close();
    }
}
