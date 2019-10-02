package main;

public class Main {
    public static void main(String[] args) {
        try {
            double a;
            double b;
            double c;
            if (args.length != 3) {
                System.out.println("not a triangle");
                return;
            }
            String regex = "^([+-]?\\d*\\.?\\d*)$";
            if (args[0].matches(regex) && args[1].matches(regex) && args[2].matches(regex)) {
                a = Double.parseDouble(args[0]);
                b = Double.parseDouble(args[1]);
                c = Double.parseDouble(args[2]);
            } else {
                System.out.println("not a triangle");
                return;
            }
            if (a < 1 || b < 1 || c < 1) {
                System.out.println("not a triangle");
            } else if (a == b && a == c) {
                System.out.println("equilateral");
            } else if (a == b || b == c || c == a) {
                System.out.println("isosceles");
            } else {
                System.out.println("normal");
            }
        } catch (Exception e) {
            System.out.println("unknown error");
        }
    }
}
