import java.io.IOException;
import java.io.Reader;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Conrad Shiao */
public class TrReader extends Reader {

    /** The reader TrReader is drawing from. */
    private Reader _reader;
    /** String that translation is drawing from. */
    private String _from;
    /** String that translation is going to. */
    private String _to;

    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(0) to TO.charAt(0), etc., leaving other characters
     *  unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        if (from.length() != to.length()) {
            throw new IllegalArgumentException("Strings must have "
                    + "the same length");
        }
        _reader = str;
        _from = from;
        _to = to;
    }


    @Override
    public int read() throws IOException {
        int x;
        x = _reader.read();
        int temp = _to.indexOf(x);
        if (temp == -1) {
            return x;
        }
        int p = _to.charAt(temp);
        return p;
    }


    @Override
    public void close() throws IOException {
        _reader.close();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return _reader.read(cbuf, off, len);
    }
}
