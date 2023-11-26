package CarRent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode implements Serializable {
    Map<Character, TrieNode> children;
    List<String> documentIds;

    public TrieNode() {
        this.children = new HashMap<>();
        this.documentIds = new ArrayList<>();
    }
}
