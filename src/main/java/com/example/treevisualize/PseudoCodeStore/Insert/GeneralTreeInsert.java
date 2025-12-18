package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class GeneralTreeInsert implements PseudoCodeStrategy{
	@Override
	public String getTitle() {
        return "GeneralTree-Insert(parentVal, childVal)";
    }

	@Override
	public List<String> getLines() {
        return Arrays.asList(
        		"1.  newNode ← new Node(childVal)",
        		"2.  parent ← search(parentVal) ",
        		"3.  if (parent == ∅) return",
        		"4.  else parent.addChild(newNode)"
        );
	}
}
