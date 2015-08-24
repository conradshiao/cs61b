import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

/** Solving Problem #2.
 *  @author Conrad Shiao
 */
public class P2 {

    /** Prints out traversal of tree with file(s) ARGS. */
    public static void main(String[] args) {
        try {
            Reader reader;
            reader = new FileReader(new File(args[0]));
            Scanner input = new Scanner(reader);
            traverse(input);
        } catch (IOException e) {
            System.exit(1);
            return;
        }
    }

    /** Traverses INPUT and outputs the postorder traversal. */
    public static void traverse(Scanner input) {
        int i = 1;
        String traversed = "";
        while (input.hasNext()) {
            String preorder = input.next();
            String inorder = "";
            if (input.hasNext()) {
                inorder = input.next();
            }
            traversed = postorder(preorder, inorder);
            System.out.println("Case " + i + ": " + traversed);
            i += 1;
            traversed = "";
        }
    }

    /** Returns post-ordered String after traversing PREORDER and INORDER. */
    public static String postorder(String preorder, String inorder) {
        if (preorder.equals("")) {
            return preorder;
        }
        int i = inorder.indexOf((int) preorder.charAt(0));
        String root = "";
        if (i != -1) {
            root = inorder.substring(i, i + 1);
        }
        String left = "";
        String right = "";
        if (i - 1 >= 0) {
            left = inorder.substring(0, i);
        }
        if (i + 1 <= inorder.length()) {
            right = inorder.substring(i + 1, inorder.length());
        }
        String next = "";
        if (preorder.length() == 1) {
            next = "";
        } else if (preorder.length() >= 1) {
            next = preorder.substring(1, i + 1);
        }
        return postorder(next, left) + postorder(next, right) + root;
    }
}
