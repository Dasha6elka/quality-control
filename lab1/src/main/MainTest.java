package lab1.src.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void parseNotFullArgs() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "2"};
        Main.parseArgs(args, triangle);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void parseNotNumberArgs() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "2", "a"};
        Main.parseArgs(args, triangle);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void firstSideInParseAllArgs() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "3", "4"};
        Main.parseArgs(args, triangle);
        assertEquals(2, triangle.getA());
    }

    @Test
    void secondSideInParseAllArgs() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "3", "4"};
        Main.parseArgs(args, triangle);
        assertEquals(3, triangle.getB());
    }

    @Test
    void thirdSideInParseAllArgs() {
        Triangle triangle = new Triangle();
        String[] args = {"2", "3", "4"};
        Main.parseArgs(args, triangle);
        assertEquals(4, triangle.getC());
    }
}