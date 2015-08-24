package make;

import java.util.ArrayList;
import java.util.List;

/** Class that holds the contents of any rule.
 *  @author Conrad Shiao
 */
class Rule {

    /** My constructor. The name of my target will be set to TARGETNAME,
     *  my prerequisites will be stored using PREREQUISITES, and my
     *  command set is initialized.
     */
    Rule(String targetName, List<String> prerequisites) {
        _targetName = targetName;
        _prerequisites.addAll(prerequisites);
        _commandSet = new ArrayList<String>();
    }

    /** Adds COMMANDS to my command set. */
    void addToCommandSet(List<String> commands) {
        _commandSet.addAll(commands);
    }

    /** Adds COMMAND to my command set. */
    void addToCommandSet(String command) {
        _commandSet.add(command);
    }

    /** Returns my command set. */
    List<String> getCommandSet() {
        return _commandSet;
    }

    /** Clears my command set. */
    void clearCommandSet() {
        _commandSet.clear();
    }

    /** Clears my prerequisites. */
    void clearPrerequisites() {
        _prerequisites.clear();
    }

    /** Adds PREREQ to my list of prerequisites. */
    void addPrerequisites(List<String> prereq) {
        _prerequisites.addAll(prereq);
    }

    /** Returns my prerequisites. */
    List<String> getPrerequisites() {
        return _prerequisites;
    }

    /** Returns true iff I have no commands in my command set. */
    boolean commandsetIsEmpty() {
        return _commandSet.size() == 0;
    }

    @Override
    public String toString() {
        return _targetName;
    }

    /** The name of my Target. */
    private String _targetName;
    /** A List storing my prerequisites. */
    private List<String> _prerequisites = new ArrayList<String>();

    /** A list storing all of my command set. */
    private List<String> _commandSet;
}
