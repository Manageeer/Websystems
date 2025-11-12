import java.util.ArrayList;

class Product {
    String name;
    double price;


    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }


    void displayInfo() {
        System.out.println("Product Name: " + name + ", Price: ₱" + price);
    }
}

public class ProductList {
    public static void main(String[] args) {

        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("Laptop", 45000.00));
        products.add(new Product("Smartphone", 18000.00));
        products.add(new Product("Headphones", 1500.00));
        products.add(new Product("Mouse", 700.00));
        products.add(new Product("Keyboard", 1200.00));

        System.out.println("=== Product List ===");
        for (Product p : products) {
            p.displayInfo();
        }
    }
}
