import java.awt.*;

/**
 * Created by jeanlee on 2017/11/2.
 */
public class Controller {
    private Board board;
    private Player player;
    MiniMaxResult miniMaxResult = null;
//    public static final int DEFAULT_DEPTH = 3;
//    private static int depth = DEFAULT_DEPTH;
//    private final int CANMOVE = 0;
//    private int canMove = CANMOVE;
    private Type type;

    public Controller(Board board,Player player,Type type) {
        this.board = board;
        this.player = player;
        this.type = type;
        miniMaxResult  = new MiniMaxResult(player,board,type);
    }

    public Point evaluatedMove(){

        return miniMaxResult.getBest().getBestMove();
    }

    public int evaluatedScore(){
        return miniMaxResult.getBest().getBestScore();
    }

    public Point[] makeMove(){
        Point[] points = new Point[2];
        Point end = evaluatedMove();
        System.out.println("Player is Moving, Please wait!");
        Point start = board.cellToMove(player,end);
        board.realMove(start,end);
        points[0] = start;
        points[1] = end;
        return points;
    }

    public Player getPlayer() {
        return player;
    }
}
