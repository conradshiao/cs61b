package trip;

import graph.Graph;
import graph.Graphs;
import graph.Iteration;
import graph.UndirectedGraph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;


/** Initial class for the 'trip' program.
 *  @author Conrad Shiao
 */
public final class Main {

    /** Location of the usage message resource. */
    static final String USAGE = "trip/Usage.txt";

    /** Entry point for the CS61B trip program.  ARGS may contain options
     *  and targets:
     *      [ -m MAP ] [ -o OUT ] [ REQUEST ]
     *  where MAP (default Map) contains the map data, OUT (default standard
     *  output) takes the result, and REQUEST (default standard input) contains
     *  the locations along the requested trip.
     */
    public static void main(String... args) {
        String mapFileName;
        String outFileName;
        String requestFileName;

        mapFileName = "Map";
        outFileName = requestFileName = null;

        int a;
        for (a = 0; a < args.length; a += 1) {
            if (args[a].equals("-m")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    mapFileName = args[a];
                }
            } else if (args[a].equals("-o")) {
                a += 1;
                if (a == args.length) {
                    usage();
                } else {
                    outFileName = args[a];
                }
            } else if (args[a].startsWith("-")) {
                usage();
            } else {
                break;
            }
        }

        if (a == args.length - 1) {
            requestFileName = args[a];
        } else if (a > args.length) {
            usage();
        }

        if (requestFileName != null) {
            try {
                System.setIn(new FileInputStream(requestFileName));
            } catch (FileNotFoundException e) {
                System.err.printf("Could not open %s.%n", requestFileName);
                System.exit(1);
            }
        }

        if (outFileName != null) {
            try {
                System.setOut(new PrintStream(new FileOutputStream(outFileName),
                                              true));
            } catch  (FileNotFoundException e) {
                System.err.printf("Could not open %s for writing.%n",
                                  outFileName);
                System.exit(1);
            }
        }

        trip(mapFileName);
    }

    /** Print a trip for the request on the standard input to the standard
     *  output, using the map data in MAPFILENAME.
     */
    private static void trip(String mapFileName) {
        createMap(mapFileName);
        Scanner requests = new Scanner(System.in);
        requests.useDelimiter("\\n+");
        while (requests.hasNext()) {
            String request = requests.next().trim();
            String[] locations = request.split("\\s*,\\s+");
            if (locations.length > 1) {
                System.out.printf("From %s:%n%n", locations[0]);
                Graph<Location, Distance>.Vertex startV, endV, currV;
                String start, end, currentLocation;
                for (int j = 1; j < locations.length; j++) {
                    currentLocation = start = locations[j - 1];
                    end = locations[j];
                    _destinations.add(end);
                    currV = startV = findVertex(start);
                    endV = findVertex(end);
                    for (Graph<Location, Distance>.Edge road
                        : Graphs.shortestPath(
                                _map, startV, endV, new Length())) {
                        Distance currentRoad = road.getLabel();
                        String roadName = currentRoad.toString();
                        String direction =
                                currentRoad.getDirection(currentLocation);
                        String len = String.valueOf(currentRoad.weight());
                        currV = road.getV(currV);
                        currentLocation = currV.getLabel().toString();
                        _data.add(new String[]
                        {roadName, len, direction, currentLocation});
                    }
                    addInstruction();
                }
                finallyPrintInstructions();
            } else {
                System.err.printf("User must provide more than one location in"
                        + " one line.");
            }
        }
        requests.close();
    }

    /** Modify the temporary information found in this._data and add
     *  this information to the instructions. */
    private static void addInstruction() {
        while (!_data.isEmpty()) {
            if (_data.size() == 1) {
                _instructions.add(_data.remove(0));
            } else {
                String[] current = _data.get(0);
                String[] future = _data.get(1);
                String currentRoad = current[0], futureRoad = future[0];
                String currentDirection = current[2],
                        futureDirection = future[2];
                if (currentRoad.equals(futureRoad)
                        && currentDirection.equals(futureDirection)) {
                    double newLength = Double.parseDouble(current[1])
                            + Double.parseDouble(future[1]);
                    _data.remove(0);
                    _data.get(0)[1] = String.valueOf(newLength);
                } else {
                    _instructions.add(_data.remove(0));
                }
            }
        }
    }

    /** Remove and print the instructions in me. */
    private static void finallyPrintInstructions() {
        String nextPlace = _destinations.remove(0);
        int count = 1;
        while (!_instructions.isEmpty()) {
            String[] data = _instructions.remove(0);
            if (nextPlace.equals(data[3])) {
                System.out.printf("%d. Take %s %s for %.1f miles to %s.%n",
                        count, data[0], data[2], Double.parseDouble(data[1]),
                        nextPlace);
                if (!_destinations.isEmpty()) {
                    nextPlace = _destinations.remove(0);
                }
            } else {
                System.out.printf("%d. Take %s %s for %.1f miles.%n", count,
                        data[0], data[2], Double.parseDouble(data[1]));
            }
            count++;
        }
    }

    /** Create the map in me from the file MAPFILENAME, adding locations
     *  as vertices and distances as edges. */
    private static void createMap(String mapFileName) {
        try {
            Scanner scanner =
                    new Scanner(new FileReader(new File(mapFileName)));
            _map = new UndirectedGraph<Location, Distance>();
            while (scanner.hasNext()) {
                String token = scanner.next();
                if (token.equals("L")) {
                    addLocationEntry(scanner);
                } else if (token.equals("R")) {
                    addDistanceEntry(scanner);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.printf("%s file not found\n", mapFileName);
            System.exit(1);
        }
    }

    /** Adds a location entry into my map as a vertex from SCANNER. If input
     *  is invalid, by default will do nothing. */
    private static void addLocationEntry(Scanner scanner) {
        if (scanner.hasNext()) {
            String nameOfLocation = scanner.next();
            if (scanner.hasNextDouble()) {
                double x = scanner.nextDouble();
                if (scanner.hasNextDouble()) {
                    double y = scanner.nextDouble();
                    _map.add(new Location(nameOfLocation, x, y));
                }
            }
        }
    }

    /** Adds a distance entry into my map as an edge from SCANNER.
     *  If input is invalid, by default will do nothing. */
    private static void addDistanceEntry(Scanner scanner) {
        if (scanner.hasNext()) {
            String start = scanner.next();
            Graph<Location, Distance>.Vertex from = findVertex(start);
            if (scanner.hasNext()) {
                String roadName = scanner.next();
                if (scanner.hasNextDouble()) {
                    double distance = scanner.nextDouble();
                    if (scanner.hasNext()) {
                        String direction = scanner.next();
                        if (scanner.hasNext()) {
                            String end = scanner.next();
                            Graph<Location, Distance>.Vertex to =
                                    findVertex(end);
                            _map.add(from, to, new Distance(start, end,
                                    roadName, distance, direction));
                        }
                    }
                }
            }
        }
    }

    /** Returns the vertex in my graph corresponding to NAME using the
     *  vertex label of Distance. */
    private static Graph<Location, Distance>.Vertex findVertex(String name) {
        if (_found.containsKey(name)) {
            return _found.get(name);
        } else {
            Iteration<Graph<Location, Distance>.Vertex> iter = _map.vertices();
            while (iter.hasNext()) {
                Graph<Location, Distance>.Vertex temp = iter.next();
                if (temp.getLabel().toString().equals(name)) {
                    _found.put(name, temp);
                    return temp;
                }
            }
            System.err.println("Did not find the given specified location.");
            System.exit(1);
            return null;
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

    /** The places I need to visit. */
    private static List<String> _destinations = new ArrayList<String>();

    /** My map of that contains all the information of locations and roads. */
    private static Graph<Location, Distance> _map;

    /** The temporary data that carries data to get from one location
     *  to another. The first element of data will be the name of the road,
     *  the second element will be the length of the road, the third element
     *  will be the direction to travel, and the fourth element the name
     *  of the location of the next vertex. */
    private static List<String[]> _data = new ArrayList<String[]>();
    /** The information that I will print as instructions. The array content
     *  for each element is as that of this._data. */
    private static List<String[]> _instructions = new ArrayList<String[]>();

    /** Convenience lookup to find the vertex that corresponds to the string
     *  found in a value's key. */
    private static Map<String, Graph<Location, Distance>.Vertex> _found =
            new HashMap<String, Graph<Location, Distance>.Vertex>();
}
