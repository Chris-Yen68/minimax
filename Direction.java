import java.awt.*;
import java.util.LinkedList;

/**
 * Created by jeanlee on 2017/10/11.
 */
public enum Direction {

    NORTH(-1,0),
    SOUTH(+1,0),
    WEST(0,-1),
    EAST(0,+1),
    NORTHWEST(-1,-1),
    SOUTHEAST(+1,+1),
    SOUTHWEST(+1,-1),
    NORTHEAST(-1,+1);

    private int row;
    private int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Point next(Cell cell){
        return new Point((int)cell.getBoardLocation().getX() + row,(int)cell.getBoardLocation().getY() + col);
    }
}
