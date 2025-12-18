package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class ScapegoatInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Scapegoat-Insert(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. BST-Insert(val)",
                "2. Update subtree sizes up to root",
                "3. If depth(val) > log_1/α(TreeSize):",
                "4.    Find 'Scapegoat' node (ancestor not α-balanced)",
                "5.    Rebuild(Scapegoat) "
        );
    }
}