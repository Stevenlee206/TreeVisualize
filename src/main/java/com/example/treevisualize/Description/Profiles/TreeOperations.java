package com.example.treevisualize.Description.Profiles;

import com.example.treevisualize.Controller.Operators.Delete.Deleter;
import com.example.treevisualize.Controller.Operators.Insert.Inserter;
import com.example.treevisualize.Controller.Operators.Search.Searcher;
import com.example.treevisualize.Trees.Tree;
import java.util.function.Supplier;

public class TreeOperations {
    private final Supplier<Tree> factory;
    private final Inserter inserter;
    private final Deleter deleter;
    private final Searcher searcher;

    public TreeOperations(Supplier<Tree> factory, Inserter inserter, Deleter deleter, Searcher searcher) {
        this.factory = factory;
        this.inserter = inserter;
        this.deleter = deleter;
        this.searcher = searcher;
    }

    public Tree createTree() { return factory.get(); }
    public Inserter getInserter() { return inserter; }
    public Deleter getDeleter() { return deleter; }
    public Searcher getSearcher() { return searcher; }
}