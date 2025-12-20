package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class GTDeleteStrategy implements PseudoCodeStrategy {
	@Override
	public String getTitle() { return "GeneralTree-Delete(value)"; }

	@Override
	public List<String> getLines() {
		return Arrays.asList(
				"1.  if (root == ∅) return",                  // 0
				"2.  target ← search(value)",                 // 1
				"3.  if (target == root)",                    // 2
				"4. 	 clearTree()",                        // 3
				"5.		 return",                             // 4
				"6.  parent ← target.parent",                 // 5
				"7.  if (parent ≠ ∅) parent.removeChild",     // 6
				"8.  else return"                             // 7
		);
	}

	@Override
	public int getLineIndex(AlgorithmEvent event) {
		if (event instanceof StandardEvent se) {
			return switch(se) {
				case START -> 0;
				case DELETE_START -> 1; // Bắt đầu tìm kiếm xóa
				case DELETE_SUCCESS -> 6; // Đã tìm thấy và xóa
				default -> -1;
			};
		}
		return -1;
	}
}