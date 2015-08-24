import java.io.IOException;
import java.io.StringReader;

/** String translation.
 *  @author Conrad Shiao */
public class Translate {
    /** Return the String S, but with all characters that occur in FROM
     *  changed to the corresponding characters in TO. FROM and TO must
     *  have the same length. */
    static String translate(String S, String from, String to) {
        try {
            StringReader reader = new StringReader(S);
            String answer = new String();
            TrReader translator = new TrReader(reader, from, to);
            while (true) {
                int x = translator.read();
                if (x == -1) {
                    break;
                }
                answer += (char) x;
            }
            return answer;
        } catch (IOException e) {
            return null;
        }
    }
}
