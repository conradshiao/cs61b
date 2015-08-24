package enigma;

import java.util.Arrays;
import enigma.*;

public class TestMethods {
    
    /** Runs all tests. */
    public static void main(String[] args) {
        report("Rotor.toLetter", testtoLetter());
        report("Rotor.toIndex", testtoIndex());
        report("Rotor.convertForward", testconvertForward());
        report("Rotor.convertBackward", testconvertBackward());
        report("Rotor.atNotch", testatNotch());
        report("Main.isConfigurationLine", testisConfigurationLine());
        //report("Machine.replaceRotors", testreplaceRotors()); */
        //report("Machine.setRotorsInConvert", testsetRotorsInConvert());
        report("Machine.convertHelperForward", testconvertHelperForward());
        report("Machine.convertHelperBackward", testconvertHelperBackward());
        report("Machine.convert", testConvert());
        report("Main.standardize", testStandardize());
    }
    
    /** Print message that test NAME has (if ISOK) or else has not
     *  passed. */
    private static void report(String name, boolean isOK) {
        if (isOK) {
            System.out.printf("%s OK.%n", name);
        } else {
            System.out.printf("%s FAILS.%n", name);
        }
    }

    /** Return true iff Rotor.toLetter passes its tests. */
    private static boolean testtoLetter() {
        char a = Rotor.toLetter(3);
        char b = Rotor.toLetter(0);
        char c = Rotor.toLetter(15);
        return a == 'D' && b == 'A' && c == 'P';
    }
    
    /** Return true iff Rotor.toIndex passes its tests. */
    private static boolean testtoIndex() {
        return Rotor.toIndex('A') == 0 && Rotor.toIndex('Y') == 24;
    }
    
    
    /** Returns true iff Rotor.convertForward passes its tests. */
    private static boolean testconvertForward() {
        Rotor a = new Rotor("I");
        int b = a.convertForward(5);
        int c = a.convertForward(9);
        a.set(22);
        int x = a.convertForward(20);
        int e = a.convertForward(0);
        int f = a.convertForward(24);
        return 6 == b && c == 25 && x == 1 && e == 5 && f == 4;
    }
    
    /** Returns true iff Rotor.convertBackward passes its tests. */
    private static boolean testconvertBackward() {
        Rotor x = new Rotor ("II");
        int a = x.convertBackward(0);
        int b = x.convertBackward(25);
        int c = x.convertBackward(10);
        x.set(10);
        int d = x.convertBackward(7);
        int e = x.convertBackward(12);
        return c == 3 && b == 18 && a == 0 && d == 22 && e == 2;
    }
    
    /** Returns true iff Rotor.atNotch passes its tests. */
    private static boolean testatNotch() {
        Rotor a = new Rotor("I");
        Rotor beta = new Rotor("VIII");
        a.set(16);
        boolean b = beta.atNotch();
        beta.set(25);
        boolean c = beta.atNotch();
        beta.set(12);
        boolean d = beta.atNotch();
        beta.set(17);
        boolean e = beta.atNotch();
        Rotor x = new FixedRotor("BETA");
        return a.atNotch() && !b && c && d && !e && !x.atNotch();
    }
    
    private static boolean testisConfigurationLine() {
        String line = "* B BETA IV I II AXLE";
        String line2 = "* BETA B IV I II AXLE";
        String line3 = "* B BETA IV I I AXLE";
        String line4 = "* B BETA I II III AXLe";
        String line5 = "! B BETA I II III AAAA";
        String line6 = "* B BETA IV I    II AXLE";
        return Main.isConfigurationLine(line);
             /**   && !Main.isConfigurationLine(line2)
                && !Main.isConfigurationLine(line3)
                && !Main.isConfigurationLine(line4)
                && !Main.isConfigurationLine(line5)
                && Main.isConfigurationLine(line6); */
    }
    
    private static boolean testConvert() {
        Machine m = new Machine();
        Reflector reflector = new Reflector("B");
        FixedRotor beta = new FixedRotor("BETA");
        Rotor rotor1 = new Rotor("I");
        Rotor rotor2 = new Rotor("II");
        Rotor rotor3 = new Rotor("III");
        Rotor[] rotors = {reflector, beta, rotor1, rotor2, rotor3};
        m.replaceRotors(rotors);
        m.setRotors("AAAA");
        String result = m.convert("HELLOWORLD");
        return result.equals("ILBDAAMTAZ");
    }
    
    /** private static boolean testsetRotorsInConvert() {
        Machine M = new Machine();
        Reflector reflector = new Reflector("B");
        FixedRotor beta = new FixedRotor("BETA");
        Rotor rotor1 = new Rotor("III");
        Rotor rotor2 = new Rotor("VI");
        Rotor rotor3 = new Rotor("I");
        Rotor[] rotors = {reflector, beta, rotor1, rotor2, rotor3};
        M.replaceRotors(rotors);
        M.setRotors("BYMQ");
        M.setRotorsInConvert();
        char first = Rotor.toLetter(M._myRotors[1].getSetting());
        char second = Rotor.toLetter(M._myRotors[2].getSetting());
        char third = Rotor.toLetter(M._myRotors[3].getSetting());
        char fourth = Rotor.toLetter(M._myRotors[4].getSetting());
        return first == 'B' && second == 'Z' && third == 'N'
                && fourth == 'R';
    } */
    
    /** private static boolean testreplaceRotors() {
        Machine m = new Machine();
        Rotor[] rotors = new Rotor[5];
        rotors[0] = new Rotor("C");
        rotors[1] = new Rotor("BETA");
        rotors[2] = new Rotor("I");
        rotors[3] = new Rotor("II");
        rotors[4] = new Rotor("III");
        m.replaceRotors(rotors);
        return m._myRotors[0]._name == "C" && m._myRotors[1]._name == "BETA"
                && m._myRotors[2]._name == "I" && m._myRotors[3]._name == "II"
                && m._myRotors[4]._name == "III";
    } */
    
    private static boolean testconvertHelperForward() {
        Machine m = new Machine();
        Rotor[] rotors = new Rotor[5];
        rotors[0] = new Rotor("B");
        rotors[1] = new Rotor("BETA");
        rotors[2] = new Rotor("I");
        rotors[3] = new Rotor("II");
        rotors[4] = new Rotor("III");
        m.replaceRotors(rotors);
        m.setRotors("AAAA");
        m.setRotorsInConvert();
        String msg = "A";
        String test = "H";
        char x = m.convertHelperForward(msg.charAt(0));
        char y = m.convertHelperForward(test.charAt(0));
        return x == 'K' && y == 'W';
    }
    
    private static boolean testconvertHelperBackward() {
        Machine m = new Machine();
        Rotor[] rotors = new Rotor[5];
        rotors[0] = new Rotor("B");
        rotors[1] = new Rotor("BETA");
        rotors[2] = new Rotor("I");
        rotors[3] = new Rotor("II");
        rotors[4] = new Rotor("III");
        m.replaceRotors(rotors);
        m.setRotors("AAAA");
        m.setRotorsInConvert();
        String msg = "K";
        String test = "W";
        char x = m.convertHelperBackward(msg.charAt(0));
        char y = m.convertHelperBackward(test.charAt(0));
        return x == 'B' && y == 'I';
    }
    
    private static boolean testStandardize() {
        String msg = "dog     bunny     Conradhere";
        return "DOGBUNNYCONRADHERE".equals((Main.standardize(msg)));
    }
}
