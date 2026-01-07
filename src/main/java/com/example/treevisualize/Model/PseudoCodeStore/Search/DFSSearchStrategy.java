package com.example.treevisualize.Model.PseudoCodeStore.Search;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class DFSSearchStrategy implements PseudoCodeStrategy {

    @Override
    public String getTitle() { return "DFSSearch(node, value)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1.  if (node == ∅) return ∅",             // 0
            "2.  if (node.value == value)",            // 1
            "3.      return node",                     // 2
            "4.  for each child in node.children",     // 3
            "5.      res ← DFS(child, value)",  		       // 4
            "6.      if (res ≠ ∅) return res",         // 5
            "7.  return ∅"                             // 6
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch(se) {
                case CHECK_ROOT_EMPTY -> 0;
                case SEARCH_CHECK -> 1;
                case SEARCH_FOUND -> 2;
                case SEARCH_RECURSE -> 4;
                default -> -1;
            };
        }
        return -1;
    }
}
