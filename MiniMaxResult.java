import java.awt.*;
import java.awt.print.Printable;

/**
 * Created by jeanlee on 2017/11/2.
 */
public class MiniMaxResult {
    private Player player;
    private MiniMax miniMax = new MiniMax();
    private Board board = new Board();
    private Type type;

    public MiniMaxResult(Player player,Board board,Type type) {
        this.player = player;
        this.board = board;
        this.type= type;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

//    public int getBestScore(){
//        return type.equals(Type.MAX) ? miniMax.valueMax(board,2,player) : miniMax.valueMin(board,3,player);
//    }

    public BestResult getBest(){
        Point move;
        int score;
        BestResult bestResult;
        if (type.equals(Type.MAX)){
            score = miniMax.valueMax(board,2,Integer.MIN_VALUE,Integer.MAX_VALUE,player);
            move = miniMax.getMaxBestMove();
        }else {
            score = miniMax.valueMin(board,2,Integer.MIN_VALUE,Integer.MAX_VALUE,player);
            move = miniMax.getMinBestMove();
        }
        bestResult = new BestResult(move,score);
        return bestResult;
    }
}
