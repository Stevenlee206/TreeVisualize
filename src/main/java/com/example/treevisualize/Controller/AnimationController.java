package com.example.treevisualize.Controller;

// Import các bộ phận chuyên trách (Sub-systems)
import com.example.treevisualize.Controller.SubSystems.Executor.AlgorithmExecutor;
import com.example.treevisualize.Controller.SubSystems.History.HistoryStorage;
import com.example.treevisualize.Controller.SubSystems.History.InMemoryHistory;
import com.example.treevisualize.Controller.SubSystems.Playback.PlaybackController;
import com.example.treevisualize.Controller.SubSystems.Playback.PlaybackDirector;
import com.example.treevisualize.Controller.SubSystems.Recorder.RecorderStrategy;
import com.example.treevisualize.Controller.SubSystems.Recorder.StandardRecorder;

// Import các thành phần bổ trợ
import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.Model.Tree.GeneralTree;
import com.example.treevisualize.Shared.TraversalType;
import com.example.treevisualize.Model.Tree.Tree;
import com.example.treevisualize.View.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.View.Visualizer.TreeVisualizer;
import com.example.treevisualize.View.Visualizer.TreeSnapShot;

import java.util.List;

/**
 * AnimationController (Role: CEO / Facade)
 * Nhiệm vụ: Điều phối hoạt động giữa Giao diện (UI) và các bộ phận xử lý bên dưới.
 * Tuân thủ SOLID: Không chứa logic nghiệp vụ, chỉ chứa logic điều khiển.
 */
public class AnimationController {

    // --- CÁC TRƯỞNG PHÒNG BAN (DEPENDENCIES) ---
    private final AlgorithmExecutor executor;   // Phòng Sản Xuất (Chạy thuật toán)
    private final RecorderStrategy recorder;    // Phòng Ghi Hình (Tạo Snapshot)
    private final HistoryStorage history;       // Phòng Lưu Trữ (Quản lý Undo/Redo)
    private final PlaybackController player;    // Phòng Trình Chiếu (Animation & Render)

    /**
     * Constructor: Tuyển dụng và khởi tạo các bộ phận
     */
    public AnimationController(Tree tree, TreeVisualizer vis, PseudoCodeBlock pseudoCode, TreeType type) {
        // 1. Khởi tạo Executor (Logic xử lý cây đa hình)
        this.executor = new AlgorithmExecutor(tree, type, pseudoCode);

        // 2. Khởi tạo Recorder (Logic ghi hình)
        this.recorder = new StandardRecorder(tree);

        // 3. Khởi tạo History (Logic lưu trữ)
        this.history = new InMemoryHistory();

        // 4. Khởi tạo Player (Logic hiển thị & hiệu ứng)
        this.player = new PlaybackDirector(tree, vis, pseudoCode);

        // 5. Thiết lập đường dây nóng (Wiring)
        // Khi Player chạy hết một frame, nó sẽ "gõ cửa" xin frame tiếp theo -> Gọi hàm autoNextStep
        this.player.setOnTickRequest(this::autoNextStep);
    }

    // =================================================================================
    // 1. KHU VỰC TIẾP NHẬN YÊU CẦU TỪ UI (COMMAND INTERFACE)
    // =================================================================================

    public void startInsert(int val) {
        runTransaction(() -> executor.executeInsert(val));
    }

    public void startInsert(int parentVal, int childVal) {
        runTransaction(() -> executor.executeInsert(parentVal, childVal));
    }

    public void startDelete(int val) {
        runTransaction(() -> executor.executeDelete(val));
    }

    public void startSearch(int val) {
        runTransaction(() -> executor.executeSearch(val));
    }

    public void startTraversal(TraversalType type) {
        runTransaction(() -> executor.executeTraversal(type));
    }

    // =================================================================================
    // 2. QUY TRÌNH VẬN HÀNH CHUẨN (ORCHESTRATION LOGIC)
    // =================================================================================

    /**
     * Template Method: Quy trình chuẩn để thực hiện một hành động.
     * B1: Dừng phim cũ -> B2: Ghi hình -> B3: Lưu kho -> B4: Phát sóng
     */
    private void runTransaction(Runnable algorithmTask) {
        // B1: Dừng mọi hoạt động đang chạy
        player.pause();

        // B2: Bắt đầu ghi hình và chạy thuật toán
        recorder.startRecording();
        algorithmTask.run(); // Chạy thuật toán cây (Insert/Delete/...)
        List<TreeSnapShot> rawFilm = recorder.stopRecording(); // Lấy cuộn phim về

        // B3: Nhập kho dữ liệu
        history.save(rawFilm);

        // B4: Quyết định hiển thị
        // [FIX CRASH] Kiểm tra xem phim có hợp lệ không (tránh lỗi 1 frame)
        if (!history.isValid()) {
            // Nếu phim chỉ có 1 frame (do lỗi hoặc không có gì thay đổi), chỉ hiện ảnh tĩnh
            player.renderInstant(history.getCurrent());
        } else {
            // Nếu phim tốt, reset về đầu và bắt đầu chiếu
            player.renderInstant(history.getCurrent());
            player.play();
        }
    }

    // =================================================================================
    // 3. KHU VỰC ĐIỀU KHIỂN MEDIA (MEDIA CONTROLS)
    // =================================================================================

    public void play() {
        // [Safety Guard] Nếu không có phim hoặc phim lỗi, từ chối chạy
        if (!history.isValid()) return;

        // Nếu đang ở cuối phim, tự động tua lại từ đầu
        if (!history.hasNext()) {
            replay();
        } else {
            player.play();
        }
    }

    public void pause() {
        player.pause();
    }

    public void replay() {
        player.pause();
        // Chỉ replay nếu kho không rỗng
        if (history.getCurrent() != null) {
            history.reset(); // Tua lịch sử về 0
            player.renderInstant(history.getCurrent()); // Vẽ frame đầu

            // Chỉ chạy nếu phim có nội dung (>1 frame)
            if (history.isValid()) {
                player.play();
            }
        }
    }

    public void stepBackward() { // Undo / Previous
        player.pause();
        if (history.hasPrev()) {
            // Lùi lại 1 bước và vẽ ngay lập tức (không hiệu ứng)
            player.renderInstant(history.prev());
        }
    }

    public void stepForward() { // Redo / Next
        player.pause();
        // Tiến 1 bước thủ công (có hiệu ứng chuyển cảnh)
        manualNextStep();
    }

    public void setSpeed(double speedMultiplier) {
        // Ủy quyền việc chỉnh tốc độ cho Player
        player.setSpeed(speedMultiplier);
    }

    // =================================================================================
    // 4. SỰ KIỆN NỘI BỘ (INTERNAL EVENTS)
    // =================================================================================

    /**
     * Được gọi tự động bởi Player (thông qua Ticker) khi đến nhịp tiếp theo.
     */
    private void autoNextStep() {
        if (history.hasNext()) {
            TreeSnapShot start = history.getCurrent();
            TreeSnapShot end = history.next(); // Tự động tăng index
            // Yêu cầu Player vẽ hiệu ứng chuyển từ Start -> End
            player.renderTransition(start, end);
        } else {
            // Hết phim -> Dừng máy chạy
            player.pause();
        }
    }

    /**
     * Được gọi khi người dùng bấm nút Next.
     * (Logic tương tự autoNextStep nhưng tách ra để dễ mở rộng xử lý UI nếu cần)
     */
    private void manualNextStep() {
        if (history.hasNext()) {
            TreeSnapShot start = history.getCurrent();
            TreeSnapShot end = history.next();
            player.renderTransition(start, end);
        }
    }

    public void startRandomBatch(int count) {
        // Gọi runTransaction đúng 1 lần duy nhất
        runTransaction(() -> {
            java.util.Random rand = new java.util.Random();

            // Logic kiểm tra loại cây để insert cho đúng
            // (Bạn có thể tinh chỉnh logic này tùy theo yêu cầu General Tree của bạn)
            boolean needsParent = (executor.getTree() instanceof GeneralTree);

            for (int i = 0; i < count; i++) {
                int val = rand.nextInt(99) + 1;

                if (needsParent) {
                    // Nếu là General Tree, mặc định insert vào root (-1) hoặc logic riêng
                    executor.executeInsert(-1, val);
                } else {
                    // Nếu là cây nhị phân (BST, AVL, Splay, RBT...)
                    executor.executeInsert(val);
                }
            }
        });
    }
}