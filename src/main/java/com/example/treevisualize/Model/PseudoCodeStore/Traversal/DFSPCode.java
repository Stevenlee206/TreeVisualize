package com.example.treevisualize.Model.PseudoCodeStore.Traversal;

import java.util.List;
import java.util.Arrays;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;

public class DFSPCode implements PseudoCodeStrategy {
	@Override
	public String getTitle() { return "DFS(root)"; }

	@Override
	public List<String> getLines() {
		return Arrays.asList(
				"1.  dfsRecursive(root, result)",
				"2.  function dfsRecursive(node):",
				"3.  \tif (node == ∅) return",
				"4.  \tresult.add(node)",             // Index 3: VISIT
				"5.  \tdfsRecursive(children)"
		);
	}

	@Override
	public int getLineIndex(AlgorithmEvent event) {
		if (event instanceof TraversalEvent te) {
			return switch (te) {
				case START -> 0;
				case VISIT -> 3; // Chỉ highlight dòng này
				default -> -1;
			};
		}
		return -1;
	}
}
