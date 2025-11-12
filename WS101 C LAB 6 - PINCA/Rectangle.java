public class Rectangle {
    double length;
    double width;

 
    double calculateArea() {
        return length * width;
    }

 
    public static void main(String[] args) {
   
        Rectangle rect1 = new Rectangle();
        rect1.length = 5.0;
        rect1.width = 3.0;

        Rectangle rect2 = new Rectangle();
        rect2.length = 10.0;
        rect2.width = 4.5;

        System.out.println("Rectangle 1 Area: " + rect1.calculateArea());
        System.out.println("Rectangle 2 Area: " + rect2.calculateArea());
    }
}
