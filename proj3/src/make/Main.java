package make;

import graph.Graph;
import graph.DirectedGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;


/** Initial class for the 'make' program.
 *  @author Conrad Shiao
 */
public final class Main {

    /** Location of the usage message resource. */
    static final String USAGE = "make/Usage.txt";

    /** Entry point for the CS61B make program.  ARGS may contain options
     *  and targets:
     *      [ -f MAKEFILE ] [ -D FILEINFO ] TARGET1 TARGET2 ...
     */
    public static void main(String... args) {
        String makefileName;
        String fileInfoName;

        if (args.length == 0) {
            usage();
        }

        makefileName = "Makefile";
        fileInfoName = "fileinfo";

        int a;
        for (a = 0; a < args.length; a += 1) {
            if (args[a].equals("-f")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    makefileName = args[a];
                }
            } else if (args[a].equals("-D")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    fileInfoName = args[a];
                }
            } else if (args[a].startsWith("-")) {
                usage();
            } else {
                break;
            }
        }

        ArrayList<String> targets = new ArrayList<String>();

        for (; a < args.length; a += 1) {
            targets.add(args[a]);
        }

        make(makefileName, fileInfoName, targets);
    }

    /** Carry out the make procedure using MAKEFILENAME as the makefile,
     *  taking information on the current file-system state from FILEINFONAME,
     *  and building TARGETS, or the first target in the makefile if TARGETS
     *  is empty.
     */
    private static void make(String makefileName, String fileInfoName,
                             ArrayList<String> targets) {
        makeMakefile(makefileName);
        makeFileInfo(fileInfoName);
        makeGraph();
        MakeTraversal<Rule, Integer> traversal = new MakeTraversal<
                Rule, Integer>(_infoFileHashMap, _currentTime);
        for (String target : targets) {
            traversal.depthFirstTraverse(_graph, _ruleToVertex.get(target));
        }
    }

    /** This method read FILEINFONAME to find information on currently
     *  existing objects and their ages.
     */
    private static void makeFileInfo(String fileInfoName) {
        try {
            Scanner scanner = new Scanner(new FileReader
            (new File(fileInfoName)));
            String[] firstLine = scanner.nextLine().trim().split("\\s+");
            if (firstLine.length != 1) {
                System.err.printf("The first line of file %s is wrongly"
                        + "formatted", fileInfoName);
                System.exit(1);
            }
            _currentTime = Integer.parseInt(firstLine[0]);
            while (scanner.hasNextLine()) {
                String[] currLine = scanner.nextLine().trim().split("\\s+");
                if (currLine.length != 2) {
                    System.err.printf("File %s is wrongly formmated",
                            fileInfoName);
                    System.exit(1);
                }
                String currName = currLine[0];
                Integer changedate = Integer.parseInt(currLine[1]);
                _infoFileHashMap.put(currName, changedate);
            }
        } catch (FileNotFoundException e) {
            System.err.println("file for fileInfoName not found");
            System.exit(1);
        } catch (InputMismatchException e) {
            System.err.printf("The formatting of file %s is erroneous",
                    fileInfoName);
            System.exit(1);
        }
    }

    /** Reads the makefile FILENAME to process its contents and read
     *  its Rules. */
    private static void makeMakefile(String fileName) {
        try {
            Scanner scanner = new Scanner(new FileReader
            (new File(fileName)));
            boolean flag = false;
            List<String> prereqs = new ArrayList<String>();
            Rule currentRule = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("#") || line.matches("\\s*")) {
                    continue;
                } else if (Character.isWhitespace(line.charAt(0)) && flag) {
                    currentRule.addToCommandSet(line);
                } else {
                    if (line.matches("[^\\s:=#\\\\]+\\s*"
                            + ":(?:\\s+[^:=#\\\\]*)*")) {
                        flag = true;
                        String[] contents = line.split("\\s+");
                        if (contents[0].endsWith(":")) {
                            contents[0] = contents[0].replaceAll(":", "");
                        }
                        for (int i = 1; i < contents.length; i++) {
                            prereqs.add(contents[i]);
                        }
                        currentRule = new Rule(contents[0], prereqs);
                        _rules.add(currentRule);
                        prereqs.clear();
                    } else {
                        scanner.close();
                        System.err.printf("The formatting of %s is erroneous",
                                fileName);
                        System.exit(1);
                    }
                }
            }
            combineDuplicateRules();
        } catch (FileNotFoundException e) {
            System.err.println("makefile name not found");
            System.exit(1);
        }
    }

    /** Merges the prerequisites of a duplicate rule together. */
    private static void combineDuplicateRules() {
        HashMap<String, Rule> map = new HashMap<String, Rule>();
        Iterator<Rule> rules = _rules.iterator();
        while (rules.hasNext()) {
            Rule temp = rules.next();
            String nameOfRule = temp.toString();
            if (map.containsKey(nameOfRule)) {
                rules.remove();
                Rule duplicateRule = map.get(nameOfRule);
                if (!temp.commandsetIsEmpty()
                        && !duplicateRule.commandsetIsEmpty()) {
                    System.err.printf("Cannot have multiple command sets"
                        + " for one target.");
                    System.exit(1);
                } else {
                    duplicateRule.addPrerequisites(temp.getPrerequisites());
                    duplicateRule.addToCommandSet(temp.getCommandSet());
                }
            } else {
                map.put(nameOfRule, temp);
            }
        }
    }

    /** Adds targets with no prerequisites into my list of prerequisites. */
    private static void addEmptyPrereqs() {
        ArrayList<String> nameOfRules = getNamesOfRules();
        HashSet<String> storage = new HashSet<String>();
        for (Rule rule : _rules) {
            for (String prereq : rule.getPrerequisites()) {
                if (!nameOfRules.contains(prereq)) {
                    storage.add(prereq);                   
                }
            }
        }
        for (String x : storage) {
            _rules.add(new Rule(x, new ArrayList<String>()));
        }
        _ruleNames = getNamesOfRules();
    }

    /** Returns a list containing the target names of the Rules that I have. */
    private static ArrayList<String> getNamesOfRules() {
        ArrayList<String> storage = new ArrayList<String>();
        for (Rule rule : _rules) {
            storage.add(rule.toString());
        }
        return storage;
    }

    /** Makes the associated graph to my Rules. */
    private static void makeGraph() {
        addEmptyPrereqs();
        checkForCycle();
        checkForExistence();
        _ruleToVertex = new HashMap<String, Graph<Rule, Integer>.Vertex>();
        int edgeCounter = 1;
        for (String ruleName : _ruleNames) {
            Rule rule = getRule(ruleName);
            if (!_ruleToVertex.containsKey(ruleName)) {
                Graph<Rule, Integer>.Vertex v = _graph.add(rule);
                _ruleToVertex.put(ruleName, v);
            }
            for (String prereq : rule.getPrerequisites()) {
                if (!_ruleToVertex.containsKey(prereq)) {
                    Graph<Rule, Integer>.Vertex v = _graph.add(getRule(prereq));
                    _ruleToVertex.put(prereq, v);
                }
            }
            for (String prereq : rule.getPrerequisites()) {
                Graph<Rule, Integer>.Vertex targ = _ruleToVertex.get(ruleName),
                        prerequisite = _ruleToVertex.get(prereq);
                if (!_graph.contains(targ, prerequisite)) {
                    _graph.add(targ, prerequisite, edgeCounter);
                    edgeCounter++;
                }
            }
        }
        Comparator<Integer> order = new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return -x.compareTo(y);
            }
        };
        _graph.orderEdges(order);
    }

    /** Returns the rule whose target name corresponds to RULENAME. */
    private static Rule getRule(String ruleName) {
        for (Rule rule : _rules) {
            if (rule.toString().equals(ruleName)) {
                return rule;
            }
        }
        return null;
    }

    /** Checks to see if my associated graphs would contain any cycles.
     *  If so, my program will exit abnormally. Otherwise, by default
     *  this does nothing. */
    private static void checkForCycle() {
        for (String name : _ruleNames) {
            for (String prereq : getRule(name).getPrerequisites()) {
                if (getRule(prereq).getPrerequisites().contains(name)) {
                    System.err.println("The Makefile should not contain"
                            + " any logical cycles.");
                    System.exit(1);
                }
            }
        }
    }

    /** Checks to see if any target that I had was not actually
     *  built. If so, then the program should exit abnormally.
     *  Else, by default this will do nothing. */
    private static void checkForExistence() {
        for (String name : _ruleNames) {
            for (String prereq : getRule(name).getPrerequisites()) {
                Rule currRule = getRule(prereq);
                if (currRule.getPrerequisites().isEmpty()
                        && currRule.getCommandSet().isEmpty()
                        && !_infoFileHashMap.containsKey(prereq)) {
                    System.err.println("Invalid Makefile: Contains target"
                            + " that was not built");
                    System.exit(1);
                }
            }
        }
    }

    /** Print a brief usage message and exit program abnormally. */
    private static void usage() {
        printHelpResource(USAGE, new PrintWriter(System.err));
        System.exit(1);
    }

    /** Print the contents of the resource named NAME on OUT.
     *  NAME will typically be a file name based in one of the directories
     *  in the class path.  */
    static void printHelpResource(String name, PrintWriter out) {
        try {
            InputStream resource =
                Main.class.getClassLoader().getResourceAsStream(name);
            BufferedReader str =
                new BufferedReader(new InputStreamReader(resource));
            for (String s = str.readLine(); s != null; s = str.readLine())  {
                out.println(s);
            }
            str.close();
            out.flush();
        } catch (IOException excp) {
            out.printf("No help found.");
            out.flush();
        }
    }

    /** Mapping from NAMES to CHANGEDATES in fileInfoName. */
    private static HashMap<String, Integer> _infoFileHashMap =
            new HashMap<String, Integer>();
    /** Convenience list that stores the names of the Rules
     *  that I contain. Will only be a valid value after
     *  addEmptyPrereqs() is called. */
    private static ArrayList<String> _ruleNames;
    /** List of rules that follow logic in the targets. */
    private static ArrayList<Rule> _rules = new ArrayList<Rule>();
    /** An integer indicating the current time. */
    private static int _currentTime;
    /** My graph that keeps track of relationships between Rules. */
    private static Graph<Rule, Integer> _graph =
            new DirectedGraph<Rule, Integer>();
    /** A mapping from the string names of targets to the corresponding
     *  vertices they are associated with in my graph. */
    private static Map<String, Graph<Rule, Integer>.Vertex> _ruleToVertex;
}
