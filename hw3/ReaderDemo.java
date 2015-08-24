

import java.io.*;

public class ReaderDemo {
    
    public static void main(String[] args) {
        String s = "Hello World";
        Reader reader = new StringReader(s);
        try {
            for (int i = 0; i < 5; i++) {
                char c = reader.read();
                System.out.print("" + c);
            }
            
            System.out.println();
            
            reader.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

