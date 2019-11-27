package lab1.src.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void getA_returnsNumber() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        assertEquals(2, triangle.getA(),
                "Функция должна вернуть длину стороны треугольника");
    }

    @Test
    void setB_number() {
        Triangle triangle = new Triangle();
        triangle.setB(2);
        assertEquals(2, triangle.getB());
    }

    @Test
    void getC_returnsNumber() {
        Triangle triangle = new Triangle();
        triangle.setC(2);
        assertEquals(2, triangle.getC());
    }

    @Test
    void getPerimeter_returnsNumber() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(2);
        assertEquals(6, triangle.getPerimeter());
    }

    @Test
    void getArea_returnsNumber() {
        Triangle triangle = new Triangle();
        triangle.setA(3);
        triangle.setB(4);
        triangle.setC(5);
        assertEquals(6, triangle.getArea());
    }

    @Test
    void setTypeTriangle_returnsEquilateralType() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.EQUILATERAL);
        assertEquals(ETypeTriangle.EQUILATERAL, triangle.getTypeTriangle());
    }

    @Test
    void getTypeTriangle_returnsIsoscelesType() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.ISOSCELES);
        assertEquals(ETypeTriangle.ISOSCELES, triangle.getTypeTriangle());
    }

    @Test
    void getTypeTriangle_returnsNormalType() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.NORMAL);
        assertEquals(ETypeTriangle.NORMAL, triangle.getTypeTriangle());
    }

    @Test
    void getTypeTriangle_returnsNoTriangleType() {
        Triangle triangle = new Triangle();
        triangle.setTypeTriangle(ETypeTriangle.NOTRIANGLE);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void setTypeTriangle_equilateralTriangle() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(2);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.EQUILATERAL, triangle.getTypeTriangle());
    }

    @Test
    void setTypeTriangle_isoscelesTriangle() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(4);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.ISOSCELES, triangle.getTypeTriangle());
    }

    @Test
    void setTypeTriangle_normalTriangle() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(3);
        triangle.setC(4);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.NORMAL, triangle.getTypeTriangle());
    }

    @Test
    void setTypeTriangle_noTriangle() {
        Triangle triangle = new Triangle();
        triangle.setA(2);
        triangle.setB(2);
        triangle.setC(0);
        triangle.setTypeTriangle();
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void setTriangle_empty_returnsNoTriangle() {
        Triangle triangle = new Triangle();
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void setA_doubleNumber() {
        Triangle triangle = new Triangle();
        triangle.setA(34.78);
        assertEquals(34.78, triangle.getA());
    }

    @Test
    void setNullNumber_null_returnsNoTriangle() {
        Triangle triangle = new Triangle();
        triangle.setA(0);
        triangle.setA(0);
        triangle.setA(0);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }

    @Test
    void setNegativeNumber_negativeNumber_returnsNoTriangle() {
        Triangle triangle = new Triangle();
        triangle.setA(-4);
        triangle.setA(-1);
        triangle.setA(-487);
        assertEquals(ETypeTriangle.NOTRIANGLE, triangle.getTypeTriangle());
    }
}