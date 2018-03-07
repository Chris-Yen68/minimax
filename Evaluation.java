import java.awt.*;

/**
 * Created by jeanlee on 2017/11/1.
 */
public class Evaluation {
    /*
    corner
    movility

     */
    public static int score(Board board){
        int result = 0;
        Cell[][] cells = board.getPieces();
        for (int i = 0; i < cells.length; i++){
            for (int j = 0; j < cells[i].length; j++) {
                if ( cells[i][j].getPiece() != null) {
                    if (cells[i][j].getPiece().getColor().equals(Color.GREEN)) {
                        if(cells[i][j].getColor().equals(Color.BLACK)){
                            result += 100 * (i + 1) + 50 * (j + 1) + 40 * (i + 1) + 40 * (j + 1) + cells[i][j].neigbourOfScore(board);

                        }else {
                            result += 100 * (i + 1) + 50 * (j + 1) + 20 * (i + 1) + 20 * (j + 1) + cells[i][j].neigbourOfScore(board);
                        }

                    } else if (cells[i][j].getPiece().getColor().equals(Color.RED)) {
                        if (cells[i][j].getColor().equals(Color.BLACK)){
                            result = result - 100 * (i + 1) - 50 * (j + 1) - 40 * (i + 1) - 40 * (j + 1);
                        }else {
                            result = result - 100 * (i + 1) - 50 * (j + 1) - 20 * (i + 1) - 20 * (j + 1);
                        }
                    }
                }
            }
        }
        return result;
    }

}
