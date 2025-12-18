package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class ScapegoatDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Scapegoat-Delete(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. BST-Delete(val)",
                "2. n--",
                "3. If n < α * maxSize:",
                "4.    Rebuild(Root) "
        );
    }
}