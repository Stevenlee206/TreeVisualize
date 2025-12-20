package com.example.treevisualize.Controller.SubSystems.Playback;

import com.example.treevisualize.Controller.SubSystems.Playback.Renderer.*;
import com.example.treevisualize.Trees.Tree;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeSnapShot;
import com.example.treevisualize.Visualizer.TreeVisualizer;

public class PlaybackDirector implements PlaybackController {

    // --- CÁC NHÂN VIÊN CHUYÊN TRÁCH ---
    private final TempoManager tempoManager;
    private final Ticker ticker;

    private final StaticRenderer staticPainter;  // Vẽ tĩnh
    private final MotionRenderer motionArtist;   // Vẽ động

    // Callback gửi về CEO
    private Runnable onTickRequestToCEO;

    public PlaybackDirector(Tree tree, TreeVisualizer vis, PseudoCodeBlock pseudoCode) {
        // 1. Tuyển dụng nhân sự
        this.tempoManager = new TempoManager();
        this.ticker = new JavaFXTicker();

        this.staticPainter = new JavaFXStaticRenderer(tree, vis, pseudoCode);
        this.motionArtist = new JavaFXMotionRenderer(tree, vis, pseudoCode);

        // 2. Wiring (Kết nối nội bộ)
        // Khi Ticker gõ nhịp -> Kiểm tra xem Họa sĩ Motion có đang bận không
        this.ticker.setOnTick(() -> {
            if (!motionArtist.isBusy() && onTickRequestToCEO != null) {
                // Nếu rảnh tay thì xin CEO frame tiếp theo
                onTickRequestToCEO.run();
            }
        });
    }

    @Override
    public void setOnTickRequest(Runnable callback) {
        this.onTickRequestToCEO = callback;
    }

    // --- MEDIA CONTROL ---

    @Override
    public void play() {
        // Cập nhật nhịp độ trước khi chạy
        ticker.setInterval(tempoManager.getStepDelay());
        ticker.start();
    }

    @Override
    public void pause() {
        ticker.stop();
        // Option: motionArtist.stop(); // Nếu muốn dừng luôn cả hiệu ứng đang bay
    }

    @Override
    public void setSpeed(double multiplier) {
        tempoManager.setSpeedMultiplier(multiplier);
        // Nếu đang chạy thì cập nhật nóng interval
        if (ticker.isRunning()) {
            ticker.setInterval(tempoManager.getStepDelay());
        }
    }

    // --- RENDER DELEGATION ---

    @Override
    public void renderInstant(TreeSnapShot snapshot) {
        // Việc vẽ tĩnh -> Giao cho StaticRenderer
        staticPainter.render(snapshot);
    }

    @Override
    public void renderTransition(TreeSnapShot start, TreeSnapShot end) {
        // Việc chuyển cảnh -> Giao cho MotionRenderer

        // 1. Lấy thời lượng bay từ TempoManager
        javafx.util.Duration duration = tempoManager.getTransitionDuration();

        // 2. Ra lệnh diễn hoạt
        motionArtist.playTransition(start, end, duration, () -> {
            // Có thể thêm logic log hoặc debug tại đây khi xong hiệu ứng
        });
    }
}