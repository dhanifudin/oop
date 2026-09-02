package id.ac.polinema;

public class Rectangle {
    int width;
    int height;

    int area() {
        return width * height;
    }

    int perimeter() {
        return 2 * (width + height);
    }
}
