package lab1.src.main;

class Triangle {
    private double a;
    private double b;
    private double c;
    private double perimeter;
    private ETypeTriangle typeTriangle = ETypeTriangle.NOTRIANGLE;

    Triangle() {}

    double getA() {
        return a;
    }

    void setA(double a) {
        this.a = a;
    }

    double getB() {
        return b;
    }

    void setB(double b) {
        this.b = b;
    }

    double getC() {
        return c;
    }

    void setC(double c) {
        this.c = c;
    }

    double getPerimeter() {
        perimeter = a + b + c;
        return perimeter;
    }

    double getArea() {
        getPerimeter();
        double halfPerimeter = perimeter / 2;
        return Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
    }

    void setTypeTriangle(ETypeTriangle typeTriangle) {
        this.typeTriangle = typeTriangle;
    }

    void setTypeTriangle() {
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

    ETypeTriangle getTypeTriangle() {
        return typeTriangle;
    }
}
