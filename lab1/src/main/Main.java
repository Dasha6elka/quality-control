package main;

public class Main {
    public static void main(String[] args) {
        try {
            int a;
            int b;
            int c;
            if (args.length != 3) {
                System.out.println("not a triangle");
                return;
            }
            String regex = "\\d+";
            if (args[0].matches(regex) && args[1].matches(regex) && args[2].matches(regex)) {
                a = Integer.parseInt(args[0]);
                b = Integer.parseInt(args[1]);
                c = Integer.parseInt(args[2]);
            } else {
                System.out.println("not a triangle");
                return;
            }
            if (a == b && a == c) {
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
