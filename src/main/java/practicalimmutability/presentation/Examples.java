package practicalimmutability.presentation;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Examples {
    public static void part(String name, Runnable runnable) {
        System.out.println("************************************************************");
        System.out.println("*** " + name.toUpperCase());
        System.out.println("************************************************************");
        System.out.println();

        runnable.run();
    }

    public static void example(String name, Runnable runnable) {
        System.out.println("============================================================");
        System.out.println(name);
        System.out.println("------------------------------------------------------------");

        try {
            runnable.run();
        } catch (Exception exc) {
            final StringWriter writer = new StringWriter();
            final PrintWriter out = new PrintWriter(writer);
            exc.printStackTrace(out);
            System.out.println(writer.toString());
        }

        System.out.println("============================================================");
        System.out.println();
    }
}
