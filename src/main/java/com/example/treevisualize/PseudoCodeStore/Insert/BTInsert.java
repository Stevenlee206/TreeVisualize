package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class BTInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "LevelOrder-Insert(root, val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  newNode ← new Node(val)",               // Index 0
                "2.  if (root == ∅)",                        // Index 1
                "3.  	 return root ← newNode",             // Index 2
                "4.  Q ← Queue()",                           // Index 3
                "5.  Q.enqueue(root)",                       // Index 4
                "6.  while Q ≠ ∅ do",                        // Index 5
                "7.      temp ← Q.dequeue()",                // Index 6
                "8.      if (temp.left == ∅)",               // Index 7
                "9.          temp.left ← newNode",           // Index 8
                "10.         return",                        // Index 9
                "11.     else Q.enqueue(temp.left)",         // Index 10
                "12.     if (temp.right == ∅)",              // Index 11
                "13.         temp.right ← newNode",          // Index 12
                "14.         return",                        // Index 13
                "15.     else Q.enqueue(temp.right)"         // Index 14
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                // Start map vào dòng 1
                case START -> 0;

                // Check root empty
                case CHECK_ROOT_EMPTY -> 1;

                // Nếu chèn vào root thành công
                case INSERT_SUCCESS -> {
                    // Logic tương đối: Nếu vừa check root empty xong -> dòng 2
                    // Nhưng ở đây ta return chung dòng 9 hoặc 13 cho case bình thường
                    // Tạm thời để -1 hoặc xử lý chi tiết hơn nếu cần
                    yield 8; // Mặc định highlight dòng gán left (phổ biến)
                }

                // Tìm thấy vị trí (temp.left == null hoặc temp.right == null)
                case FOUND_INSERT_POS -> 7; // Hoặc 11

                // Đi trái (Enqueue Left)
                case GO_LEFT -> 10;

                // Đi phải (Enqueue Right)
                case GO_RIGHT -> 14;

                default -> -1;
            };
        }
        return -1;
    }
}