package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BSTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { 
        return "BST - Delete(value)"; 
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (root == ∅) return",                                     // Index 0
                "2.  if (search(value) == ∅) return",                            // Index 1
                "3.  root ← deleteRecursive(root, value)",                       // Index 2
                "4.  function deleteRecursive(current, value):",                 // Index 3
                "5.      if (current == ∅) return null",                         // Index 4
                "6.      if (value < current.val):",                             // Index 5
                "7.          current.left ← deleteRecursive(left, value)",       // Index 6
                "8.          return current",                                    // Index 7
                "9.      else if (value > current.val):",                        // Index 8
                "10.         current.right ← deleteRecursive(right, value)",     // Index 9
                "11.         return current",                                    // Index 10
                "12.     else: // DELETE_SUCCESS",                               // Index 11
                "13.         if (left == ∅ && right == ∅) return null",          // Index 12
                "14.         if (left == ∅) return current.right",               // Index 13
                "15.         if (right == ∅) return current.left",               // Index 14
                "16.         smallestValue ← findSmallestValue(right)",          // Index 15
                "17.         current.val ← smallestValue",                       // Index 16
                "18.         current.right ← deleteRecursive(right, smallestValue)" // Index 17
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 0;    // Highlight dòng kiểm tra root
                case COMPARE_LESS -> 5;    // Highlight dòng so sánh nhỏ hơn
                case GO_LEFT -> 6;         // Highlight dòng gọi đệ quy trái
                case COMPARE_GREATER -> 8; // Highlight dòng so sánh lớn hơn
                case GO_RIGHT -> 9;        // Highlight dòng gọi đệ quy phải
                case DELETE_SUCCESS -> 11; // Highlight nhánh tìm thấy node
                // case FINISHED trả về -1 để tránh lỗi nhảy dòng sớm, thanh highlight sẽ đứng yên
                default -> -1;
            };
        }
        return -1;
    }
}