package lab1.src.main;

public class Triangle {
    private double a;
    private double b;
    private double c;
    private double perimeter;
    private ETypeTriangle typeTriangle;

    public Triangle() {}

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getPerimeter() {
        perimeter = a + b + c;
        return perimeter;
    }

    public double getArea() {
        getPerimeter();
        double halfPerimeter = perimeter / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }

    public void setTypeTriangle(ETypeTriangle typeTriangle) {
        this.typeTriangle = typeTriangle;
    }

    public void setTypeTriangle() {
        if (a < 1 || b < 1 || c < 1) {
            typeTriangle = ETypeTriangle.NOTRIANGLE;
        } else if (a == b && a == c) {
            typeTriangle = ETypeTriangle.EQUILATERAL;
        } else if (a == b || b == c || c == a) {
            typeTriangle = ETypeTriangle.ISOSCELES;
        } else {
            typeTriangle = ETypeTriangle.NORMAL;
        }
    }

    public ETypeTriangle getTypeTriangle() {
        return typeTriangle;
    }
}
