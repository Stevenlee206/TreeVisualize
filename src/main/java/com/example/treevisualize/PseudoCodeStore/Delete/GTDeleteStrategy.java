package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class GTDeleteStrategy implements PseudoCodeStrategy {
	public String getTitle() {
		return "GeneralTree-Delete(value)";
	}
	public List<String> getLines() {
		return Arrays.asList(
				"1.  if (root == ∅) return",
				"2.  target ← search(value)",
				"3.  if (target == root)",
				"4. 	 clearTree()",
				"5.		 return",
				"6.  parent ← target.parent",
				"7.  if (parent ≠ ∅) parent.removeChild(value)",
				"8.  else return"
				);
	}
}
