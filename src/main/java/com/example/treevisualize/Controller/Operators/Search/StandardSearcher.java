package com.example.treevisualize.Controller.Operators.Search;

import com.example.treevisualize.Trees.Tree;

public class StandardSearcher implements Searcher {
    @Override public void search(Tree tree, int value) { tree.search(value); }
}
