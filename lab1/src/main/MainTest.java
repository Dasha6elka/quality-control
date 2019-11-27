package lab1.src.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void parseArgs_twoArgs_returnsNoTriangle() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "2"};
        Main.parseArgs(args, triangle);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle(),
                "Функция должна вернуть тип треугольника NoTriangle," +
                        "т.к не все стороны треугольника введены");
    }

    @Test
    void parseArgs_noTriangleArgs_returnsNoTriangle() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "2", "a"};
        Main.parseArgs(args, triangle);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle(),
                "Функция должна вернуть тип треугольника NoTriangle, т.к одна из сторон это буква");
    }

    @Test
    void parseArgs_triangle_returnsFirstSide() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "3", "4"};
        Main.parseArgs(args, triangle);
        assertEquals(2, triangle.getA(),
                "Функция должна вернуть первую сторону треугольника");
    }

    @Test
    void parseArgs_triangle_returnsSecondSide() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "3", "4"};
        Main.parseArgs(args, triangle);
        assertEquals(3, triangle.getB(),
                "Функция должна вернуть вторую сторону треугольника");
    }

    @Test
    void parseArgs_triangle_returnsThirdSide() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "3", "4"};
        Main.parseArgs(args, triangle);
        assertEquals(4, triangle.getC(),
                "Функция должна вернуть третью сторону треугольника");
    }
}