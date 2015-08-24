package make;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import graph.Graph;

import graph.Traversal;
import graph.RejectException;

/** Extends Traversal for the make package to traverse through Rules.
 *  @author Conrad Shiao
 */
public class MakeTraversal<VLabel, ELabel> extends Traversal<VLabel, ELabel> {
    /** My constructor that keeps track of CURRENTTIME and FILEINFO. */
    public MakeTraversal(HashMap<String, Integer> fileInfo, int currentTime) {
        _currentTime = currentTime;
        _ages = fileInfo;
    }

    @Override
    protected void preVisit(Graph<VLabel, ELabel>.Edge e,
            Graph<VLabel, ELabel>.Vertex v0) {
        Rule temp = (Rule) e.getV(v0).getLabel();
        List<String> prereqs = temp.getPrerequisites();
        if (prereqs.isEmpty() && _ages.containsKey(temp.toString())) {
            throw new RejectException();
        }
        for (String target : prereqs) {
            boolean exists =  _ages.containsKey(target)
                    && _ages.containsKey(temp.toString());
            if ((!exists || _ages.get(target) <= _ages.get(temp.toString()))
                    && _ages.containsKey(temp)) {
                throw new RejectException();
            }
        }
    }

    @Override
    protected void postVisit(Graph<VLabel, ELabel>.Vertex v) {
        Rule rule = (Rule) v.getLabel();
        boolean emptyPrereqs = rule.getPrerequisites().isEmpty();
        if (!_preVisited.contains(rule.toString())) {
            if (!emptyPrereqs || !_ages.containsKey(rule.toString())) {
                for (String command : rule.getCommandSet()) {
                    System.out.println(command);
                }
                _preVisited.add(rule.toString());
                if (!rule.getPrerequisites().isEmpty()) {
                    rule.clearCommandSet();
                }
            }
        }
        rule.clearPrerequisites();
        _ages.put(rule.toString(), _currentTime);
    }

    /** The current time in the make file. */
    private int _currentTime;
    /** The mappings of currently existing objects and their ages. */
    private HashMap<String, Integer> _ages;
    /** A list of Target names that have already been postvisited in me. */
    private ArrayList<String> _preVisited = new ArrayList<String>();
}
