import java.awt.*;

/**
 * Created by jeanlee on 2017/11/6.
 */
public class BestResult {
    private Point bestMove;
    private int bestScore;

    public BestResult(Point bestMove, int bestScore) {
        this.bestMove = bestMove;
        this.bestScore = bestScore;
    }

    public Point getBestMove() {
        return bestMove;
    }

    public void setBestMove(Point bestMove) {
        this.bestMove = bestMove;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}
