package test;

import java.io.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        FileReader in = new FileReader(args[0]);
        FileWriter out = new FileWriter("output.txt");

        try (Scanner scanner = new Scanner(in)) {
            while (scanner.hasNext()) {
                String numbers = scanner.useDelimiter(",").next().trim();
                scanner.skip(",");
                String expected = scanner.useDelimiter("\n").next().trim();

                Process io = Runtime.getRuntime().exec("java -jar " + args[1] + " " + numbers);
                BufferedReader reader = new BufferedReader(new InputStreamReader(io.getInputStream()));

                String actual = reader.readLine();

                if (expected.equals(actual)) {
                    out.write("success\n");
                } else {
                    out.write("error\n");
                }
            }
        }
        out.close();
    }
}
