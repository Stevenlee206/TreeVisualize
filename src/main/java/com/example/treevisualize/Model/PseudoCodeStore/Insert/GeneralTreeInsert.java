package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class GeneralTreeInsert implements PseudoCodeStrategy{
	@Override
	public String getTitle() { return "GeneralTree-Insert(parentVal, childVal)"; }

	@Override
	public List<String> getLines() {
		return Arrays.asList(
				"1.  newNode ← new Node(childVal)",   // 0
				"2.  parent ← search(parentVal) ",    // 1
				"3.  if (parent == ∅) return",        // 2
				"4.  else parent.addChild(newNode)"   // 3
		);
	}

	@Override
	public int getLineIndex(AlgorithmEvent event) {
		if (event instanceof StandardEvent se) {
			return switch(se) {
				case START -> 0;
				case COMPARE_LESS, COMPARE_GREATER -> 1; // Đang tìm kiếm
				case FOUND_INSERT_POS -> 3; // Tìm thấy cha
				case INSERT_SUCCESS -> 3;
				default -> -1;
			};
		}
		return -1;
	}
}