import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class scratch {
    public static void main(String[] args) {
        /** String x = "[\\-*[0-9]*\\.*[0-9]*]";
        Scanner inp = new Scanner (System.in);
        Pattern p = Pattern.compile(x);
        Matcher m = p.matcher("0.3");
        for (int i = 0; i < 5; i++) {
            if (m.find()) {
                System.out.println("found match at " + m.start());
            }
        } */
        String Mercury = "Mercury";
        System.out.printf("%-8s|%7.2f", Mercury, 234.5424);
        //System.out.printf("%-8.2f|%n", 3.234);
        final String FORMAT_P4 = "%-8|%7.2f";
        // System.out.printf(FORMAT_P4, "Mercury", 35983610 * 1.0e-6);
    }
}
