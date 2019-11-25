package lab1.src.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void getA() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        assertEquals(2, triangle.getA());
    }

    @Test
    void setB() {
        Triangle triangle = new Triangle();
        triangle.setB(2);
        assertEquals(2, triangle.getB());
    }

    @Test
    void getC() {
        Triangle triangle = new Triangle();
        triangle.setC(2);
        assertEquals(2, triangle.getC());
    }

    @Test
    void getPerimeter() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(2);
        assertEquals(6, triangle.getPerimeter());
    }

    @Test
    void getArea() {
        Triangle triangle = new Triangle();
        triangle.setA(3);
        triangle.setB(4);
        triangle.setC(5);
        assertEquals(6, triangle.getArea());
    }

    @Test
    void setEquilateralTypeTriangle() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.EQUILATERAL);
        assertEquals(ETypeTriangle.EQUILATERAL, triangle.getTypeTriangle());
    }

    @Test
    void getIsoscelesTypeTriangle() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.ISOSCELES);
        assertEquals(ETypeTriangle.ISOSCELES, triangle.getTypeTriangle());
    }

    @Test
    void getNormalTypeTriangle() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.NORMAL);
        assertEquals(ETypeTriangle.NORMAL, triangle.getTypeTriangle());
    }

    @Test
    void getNoTriangleType() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.NOTRIANGLE);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void setEquilateralTypeTriangleOnSide() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(2);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.EQUILATERAL, triangle.getTypeTriangle());
    }

    @Test
    void setIsoscelesTypeTriangleOnSide() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(4);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.ISOSCELES, triangle.getTypeTriangle());
    }

    @Test
    void setNormalTypeTriangleOnSide() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(3);
        triangle.setC(4);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.NORMAL, triangle.getTypeTriangle());
    }

    @Test
    void setNoTriangleTypeOnSide() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(0);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }
}