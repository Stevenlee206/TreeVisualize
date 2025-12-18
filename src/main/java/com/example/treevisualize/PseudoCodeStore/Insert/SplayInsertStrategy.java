package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class SplayInsertStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Splay Tree Insertion";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. Normal BST Insert(val)",
                "2. x = newly inserted node",
                "3. Splay(x) // Move x to Root",
                "   while x != root:",
                "     if parent is root: Rotate(parent)",
                "     else if Zig-Zig:   Rotate(grand), Rotate(parent)",
                "     else (Zig-Zag):    Rotate(parent), Rotate(grand)"
        );
    }
}