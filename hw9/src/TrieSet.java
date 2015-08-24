/* HW9, problem 1. */

import java.util.ArrayList;

/** A set of strings.
 *  @author Conrad Shiao */
public class TrieSet {

    /** An empty set. */
    public TrieSet() {
        root = new EmptyTrieNode();
    }

    /** Returns true iff THIS contains KEY. */
    public boolean contains(String key) {
        return root.contains(key, 0);
    }

    /** Add KEY to THIS.  Has no effect if KEY is already in THIS. */
    void add(String key) {
        root = root.insert(key, 0);
    }

    /** Root of the trie, representing the empty string. */
    private TrieNode root;

    /** A node of the trie. */
    private abstract static class TrieNode {

        /** Returns true iff THIS contains KEY, assuming that THIS is a
         *  TrieNode at level K in the entire tree (where the root is at
         *  level 0). Assumes that the length of KEY is at least K, and
         *  that THIS is the subtree that should contain KEY, if present. */
        abstract boolean contains(String key, int k);

        /** Insert KEY into THIS, if necessary, assuming that THIS is a
         *  TrieNode at level K in the entire tree (where the root is at
         *  level 0) Assumes that the length of KEY
         *  is at least K, and that THIS is
         *  the subtree that should contain KEY, if present. Returns
         *  the modified trie node. */
        abstract TrieNode insert(String key, int k);
    }

    /** Represents an empty trie. */
    private static class EmptyTrieNode extends TrieNode {
        @Override
        boolean contains(String key, int k) {
            if (_newNode == null) {
                return false;
            } else {
                return _newNode.contains(key, k);
            }
        }

        @Override
        TrieNode insert(String key, int k) {
            if (_newNode == null) {
                _newNode = new SingletonTrieNode(key);
                return _newNode;
            } else {
                _newNode.insert(key, k);
                return _newNode;
            }
        }

        /** The TrieNode that this class becomes if something is inserted into
         *  it. By default, the value is null until a key is inserted. */
        private TrieNode _newNode = null;
    }


    /** Represents a trie subtree that contains a single String. */
    private static class SingletonTrieNode extends TrieNode {
        /** The entire string represented by this leaf node. */
        private final String _key;

        /** A leaf trie node representing KEY. */
        SingletonTrieNode(String key) {
            _key = key;
        }

        @Override
        boolean contains(String key, int k) {
            if (_newNode == null) {
                return key.equals(_key);
            } else {
                return _newNode.contains(key, k);
            }
        }

        @Override
        TrieNode insert(String key, int k) {
            if (_newNode == null) {
                _newNode = new InnerTrieNode(this, k);
                return _newNode;
            } else {
                _newNode.insert(key, k);
                return _newNode;
            }
        }

        /** The inner node that is created from this, when something is
         *  inserted into it. */
        private TrieNode _newNode = null;
    }


    /** A nonleaf node in a trie. */
    private static class InnerTrieNode extends TrieNode {
        /** An inner node at level K (root is 0) that initially contains just
         *  the string contained in S. [It is convenient to do it this way
         *  because one always creates an inner node out of a singleton node,
         *  and the same singleton node moves down the tree.] */
        InnerTrieNode(SingletonTrieNode s, int k) {
            _values.add(k, s._key);
        }

        @Override
        boolean contains(String key, int k) {
            return _values.contains(key);
        }

        @Override
        TrieNode insert(String key, int k) {
            if (!this.contains(key, k)) {
                _values.add(k, key);
            }
            return this;
        }

        /** The strings that this node contains. */
        private ArrayList<String> _values = new ArrayList<String>();
    }

}
