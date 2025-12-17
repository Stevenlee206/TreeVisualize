package com.example.treevisualize.PseudoCodeStore.Traversal;

import java.util.List;
import java.util.Arrays;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;

public class DFSPCode implements PseudoCodeStrategy {
	@Override
	public String getTitle() {
		return "DFS(root)";
	}
	
	@Override
	public List<String> getLines() {
		return Arrays.asList(
				"1.  result ← ArrayList()",
				"2.  dfsRecursive(root, result)",
				"3.  return result",
				"4.  function dfsRecursive(node, result)",
				"5.  	if (node == ∅) return",
				"6.  	result.add(node)",
				"7.  	dfsRecursive(node.left, result)",
				"8.  	defRecursive(node.right, result)"
		);
	}
}
