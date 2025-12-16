package com.example.treevisualize.Controller;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Trees.GeneralTree;
import com.example.treevisualize.Trees.InOrderTraversal;
import com.example.treevisualize.Trees.TraversalStrategy;
import com.example.treevisualize.Trees.Tree;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeObserver;
import com.example.treevisualize.Visualizer.TreeVisualizer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import com.example.treevisualize.Visualizer.TreeSnapShot;
public class AnimationController {

    // --- ATTRIBUTES (Theo UML) ---
    private Tree tree;
    private TreeVisualizer visualizer;
    private PseudoCodeBlock pseudoCode;

    // Timeline của JavaFX để quản lý việc chạy tự động
    private Timeline timeLine;

    private boolean isPlaying;
    private double speed; // Thời gian delay (ms)

    // --- CÁC THUỘC TÍNH BỔ SUNG (Để quản lý logic Snapshot) ---
    // Danh sách các khung hình (Snapshot) của hoạt cảnh hiện tại
    private List<TreeSnapShot> animationFrames;

    // Chỉ số khung hình đang hiển thị
    private int currentFrameIndex;

    // --- CONSTRUCTOR ---
    public AnimationController(Tree tree, TreeVisualizer vis, PseudoCodeBlock pseudoCode) {
        this.tree = tree;
        this.visualizer = vis;
        this.pseudoCode = pseudoCode;

        this.animationFrames = new ArrayList<>();
        this.currentFrameIndex = 0;
        this.isPlaying = false;
        this.speed = 1000.0; // Mặc định 1 giây/bước

        // Khởi tạo Timeline (vòng lặp vô hạn, nhưng ta sẽ control nó)
        this.timeLine = new Timeline();
        this.timeLine.setCycleCount(Timeline.INDEFINITE);
    }

    // --- PUBLIC OPERATIONS (Theo UML) ---

    public void play() {
        if (animationFrames.isEmpty() || currentFrameIndex >= animationFrames.size() - 1) {
            return; // Không có gì để chạy hoặc đã chạy xong
        }

        this.isPlaying = true;

        // Tạo lại animation sequence với tốc độ hiện tại
        createAnimationSequence();

        this.timeLine.play();
    }

    public void pause() {
        this.isPlaying = false;
        this.timeLine.pause();
    }

    /**
     * Tiến tới 1 bước (Dùng cho nút "Next Step" hoặc khi Timeline tick)
     */
    public void stepForward() {
        if (currentFrameIndex < animationFrames.size() - 1) {
            currentFrameIndex++;
            restoreSnapshot(animationFrames.get(currentFrameIndex));
        } else {
            // Đã đến cuối, tự động dừng
            pause();
        }
    }

    /**
     * Quay về trạng thái ban đầu của thuật toán hiện tại
     */
    public void reset() {
        pause();
        if (!animationFrames.isEmpty()) {
            currentFrameIndex = 0;
            restoreSnapshot(animationFrames.get(0));
        }
    }

    /**
     * Cài đặt tốc độ chạy (Delay tính bằng ms)
     */
    public void setSpeed(double delayMs) {
        this.speed = delayMs;
        // Nếu đang chạy thì cập nhật lại timeline ngay lập tức
        if (isPlaying) {
            pause();
            play();
        }
    }

    // --- CÁC HÀM START (Bắt đầu các thuật toán) ---

    /**
     * Logic cốt lõi:
     * 1. Gắn "Camera" (Recorder) vào cây.
     * 2. Chạy thuật toán thật -> Camera ghi lại các bước vào List Snapshot.
     * 3. Reset cây về trạng thái đầu.
     * 4. Bắt đầu phát lại (Play).
     */

    public void startInsert(int val) {
        // 1. Chuẩn bị ghi hình
        prepareRecording();

        // 2. Chạy thuật toán (Snapshot sẽ được tạo tự động nhờ Observer)
        tree.insert(val);

        // 3. Kết thúc ghi hình & Chuẩn bị phát
        finishRecording();
    }

    public void startDelete(int val) {
        prepareRecording();
        tree.delete(val);
        finishRecording();
    }

    public void startSearch(int val) {
        prepareRecording();
        tree.search(val);
        finishRecording();
    }

    // --- PRIVATE HELPER METHODS ---

    private void createAnimationSequence() {
        // Stop timeline cũ
        timeLine.stop();
        timeLine.getKeyFrames().clear();

        // Tạo 1 KeyFrame duy nhất chạy lặp lại sau mỗi khoảng thời gian 'speed'
        // Mỗi lần chạy sẽ gọi hàm stepForward()
        KeyFrame frame = new KeyFrame(Duration.millis(speed), event -> {
            stepForward();
        });

        timeLine.getKeyFrames().add(frame);
    }

    /**
     * Khôi phục giao diện từ một Snapshot
     */
    private void restoreSnapshot(TreeSnapShot snapshot) {
        // 1. Cập nhật cấu trúc cây (com.example.treevisualize.Visualizer sẽ tự vẽ lại nhờ Observer trong Tree)
        // Lưu ý: Cần setRoot bằng bản COPY để không làm hỏng dữ liệu gốc
        tree.setRoot(snapshot.getRootCopy());

        // 2. Cập nhật highlight code
        pseudoCode.highlightLine(snapshot.getPseudoLineIndex());

        // 3. (Tuỳ chọn) Hiển thị thông báo trạng thái nếu có
        // System.out.println(snapshot.getStatusMessage());
    }

    // --- LOGIC GHI HÌNH (RECORDING) ---

    // Một Observer tạm thời dùng để "quay phim" quá trình thuật toán chạy
    private TreeObserver recorder;

    private void prepareRecording() {
        // Reset danh sách phim
        this.animationFrames.clear();
        this.currentFrameIndex = 0;

        // Lưu lại trạng thái ban đầu (trước khi insert/delete)
        // Snapshot đầu tiên (Frame 0)
        captureFrame(-1, "Start");

        // Tạo Observer đặc biệt để nghe ngóng thay đổi từ Tree
        recorder = new TreeObserver() {
            @Override
            public void onNodeChanged(Node node) {
                // Mỗi khi node đổi màu -> Chụp 1 tấm ảnh
                captureFrame(getCurrentCodeLine(), "com.example.treevisualize.Node Changed");
            }

            @Override
            public void onStructureChanged() {
                // Mỗi khi cấu trúc đổi (thêm/xóa/xoay) -> Chụp 1 tấm ảnh
                captureFrame(getCurrentCodeLine(), "Structure Changed");
            }

            @Override
            public void onError(String message) {
                // Lỗi cũng chụp lại để hiển thị thông báo lỗi
                captureFrame(-1, "Error: " + message);
            }
        };

        // Gắn recorder vào Tree
        tree.addObserver(recorder);
    }

    private void finishRecording() {
        // Gỡ recorder ra để không ảnh hưởng các thao tác sau
        tree.removeObserver(recorder);

        // Reset cây về khung hình đầu tiên để chuẩn bị chiếu phim
        // (Vì lúc chạy lệnh tree.insert, cây đã biến đổi thành kết quả cuối cùng rồi)
        if (!animationFrames.isEmpty()) {
            restoreSnapshot(animationFrames.get(0));
        }

        // Tự động chạy
        play();
    }

    private void captureFrame(int lineIndex, String msg) {
        // Tạo snapshot mới từ trạng thái hiện tại của Tree
        // TreeSnapshot constructor sẽ lo việc Deep Copy dữ liệu
        TreeSnapShot snapshot = new TreeSnapShot(tree.getRoot(), lineIndex, msg);
        animationFrames.add(snapshot);
    }

    // Giả lập hàm lấy dòng code hiện tại (Trong thực tế bạn cần cơ chế
    // để Tree báo về nó đang chạy dòng code nào, hoặc ước lượng)
    private int getCurrentCodeLine() {
        // Để đơn giản, ta có thể trả về 0 hoặc một biến global
        return 0;
    }

    public void startTraversal(String type) {
        // 1. Dọn dẹp màu sắc cũ trên cây
        tree.resetTreeStatus();

        // 2. Bắt đầu chế độ Ghi hình
        prepareRecording();

        TraversalStrategy strategy = null;

        // 3. Chọn chiến lược dựa trên tên (Factory Pattern)
        switch (type) {
            case "In-Order (LNR)":
                strategy = new InOrderTraversal();
                break;
            // Nếu bạn chưa tạo các class Strategy kia thì tạm thời comment lại để tránh lỗi compile
            /*
            case "Pre-Order (NLR)":
                strategy = new PreOrderTraversal();
                break;
            case "Post-Order (LRN)":
                strategy = new PostOrderTraversal();
                break;
            case "BFS (Level Order)":
                strategy = new BFSTraversal();
                break;
            */
            default:
                System.out.println("The algorithm hasn't been installed yet: " + type);
                finishRecording(); // Kết thúc sớm để tránh lỗi
                return;
        }

        // 4. Chạy thuật toán (Quan trọng: Tree sẽ thực hiện loop và visit tại đây)
        if (strategy != null) {
            tree.traverse(strategy);
        }

        // 5. Kết thúc ghi hình và Phát lại
        finishRecording();
    }

    /**
     * Hàm Insert dành riêng cho General Tree (Gọi hàm có sẵn trong GeneralTree.java)
     */
    public void startInsert(int parentValue, int childValue) {
        // 1. Kiểm tra xem cây hiện tại có phải GeneralTree không
        if (tree instanceof GeneralTree) {
            GeneralTree generalTree = (GeneralTree) tree;

            // 2. Gọi hàm insert(parent, child) ĐÃ CÓ SẴN trong file GeneralTree của bạn
            // Hàm này trả về void nên ta không cần check true/false
            // Nó tự động gọi notifyError bên trong nếu không tìm thấy cha
            generalTree.insert(parentValue, childValue);

            // 3. Vẽ lại cây để thấy sự thay đổi
            visualizer.render();

        } else {
            System.err.println("ERROR: Current tree is not General Tree!");
        }
    }
}
