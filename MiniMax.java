import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.awt.*;
import java.util.*;


/**
 * Created by jeanlee on 2017/11/1.
 */
public class MiniMax {
    private Point maxBestMove = new Point();
    private Point minBestMove = new Point();

    public int valueMax(Board board, int depth,int a, int b, Player player) {
        int  score = Integer.MIN_VALUE;
        if (depth <= 0 || isEnd(board)) {
            return Evaluation.score(board);
        }else {
            Set<Point> possibleMoves = MoveExplorer.getPossibleMoves(board, player);
            Board subBoard;
            if (!possibleMoves.isEmpty()) {
                for (Point possibleMove : possibleMoves) {
                    subBoard = board.clone();
                    Point startPoint = subBoard.cellToMove(player, possibleMove);
                    subBoard.realMove(startPoint, possibleMove);
                    int result = valueMin(subBoard, depth - 1, a, b, player.opponent());
                    if (result > score) {
                        score = result;
                        maxBestMove = possibleMove;
                    }
                    a = Math.max(a,score);
                    if (b <= a){
                        break;
                    }
                }
            }
        }

        return score;
    }

    public int valueMin(Board board, int depth, int a, int b, Player player){
        int miniScore = Integer.MAX_VALUE;
        if (depth <= 0 || isEnd(board)) {
            return Evaluation.score(board);
        }else {
            Set<Point> possibleMoves = MoveExplorer.getPossibleMoves(board, player);
            Board subBoard;
            if (!possibleMoves.isEmpty()) {
                for (Point possibleMove : possibleMoves) {
                    subBoard = board.clone();
                    Point startPoint = subBoard.cellToMove(player, possibleMove);
                    subBoard.realMove(startPoint, possibleMove);
                    int result = valueMax(subBoard, depth - 1, a, b,player.opponent());
                    if (result < miniScore) {
                        miniScore = result;
                        minBestMove = possibleMove;
                    }
                    b = Math.min(b,miniScore);
                    if (b <= a){
                        break;
                    }
                }
            }
        }
        return miniScore;
    }

    public Point getMaxBestMove() {
        return maxBestMove;
    }

    public void setMaxBestMove(Point maxBestMove) {
        this.maxBestMove = maxBestMove;
    }

    public Point getMinBestMove() {
        return minBestMove;
    }

    public void setMinBestMove(Point minBestMove) {
        this.minBestMove = minBestMove;
    }

    private int max(int a, int b){
        return Math.max(a,b);
    }

    private int min(int a, int b){
        return Math.min(a,b);
    }

    public boolean isEnd(Board board) {
        return board.isWin();
    }

}
