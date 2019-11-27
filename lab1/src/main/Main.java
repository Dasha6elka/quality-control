package lab1.src.main;

public class Main {
    public static void main(String[] args) {
        try {
            Triangle triangle = new Triangle();
            parseArgs(args, triangle);
            triangle.setTypeTriangle();
            printTriangleInformation(triangle);
        } catch (Exception e) {
            System.out.println("unknown error");
        }
    }

    static void parseArgs(String[] args, Triangle triangle) {
        if (args.length != 3) {
            triangle.setTypeTriangle(ETypeTriangle.NOTRIANGLE);
            return;
        }
        String regex = "^([+-]?\\d*\\.?\\d*)$";
        if (args[0].matches(regex) && args[1].matches(regex) && args[2].matches(regex)) {
            triangle.setA(Double.parseDouble(args[0]));
            triangle.setB(Double.parseDouble(args[1]));
            triangle.setC(Double.parseDouble(args[2]));
        } else {
            triangle.setTypeTriangle(ETypeTriangle.NOTRIANGLE);
        }
    }

    private static void printTriangleInformation(Triangle triangle) {
        if (triangle.getTypeTriangle().equals(ETypeTriangle.NOTRIANGLE)) {
            System.out.println("not a triangle");
        } else if (triangle.getTypeTriangle().equals(ETypeTriangle.EQUILATERAL)) {
            System.out.println("equilateral");
        } else if (triangle.getTypeTriangle().equals(ETypeTriangle.ISOSCELES)) {
            System.out.println("isosceles");
        } else if (triangle.getTypeTriangle().equals(ETypeTriangle.NORMAL)) {
            System.out.println("normal");
        }
        System.out.println("Периметр треугольника: " + triangle.getPerimeter());
        System.out.println("Площадь треугольника: " + triangle.getArea());
    }
}

