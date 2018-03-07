import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jeanlee on 2017/10/11.
 */
public class MoveExplorer {
    public  static boolean isOnBoard(Point point){
        boolean result = point.getY() < 9 && point.getX() < 5 && point.getX() >= 0 && point.getY() >= 0;
        return result;
    }
    public static  boolean moveIsValid(Board board, Cell start, Point point){
        boolean result = false;
        if (!isOnBoard(point)){
            return result;
        }
        if (start.getColor() == Color.BLACK){
            for (Direction direction : Direction.values()){
                if (isOnBoard(direction.next(start))){
                    if (point.getX() == direction.next(start).getX() && point.getY() == direction.next(start).getY()){
                        if (board.getPieces()[point.x][point.y].getPiece() == Piece.EMPTY ||
                                board.getPieces()[point.x][point.y].getPiece() == null){
                            result = true;
                        }

                    }
                }
            }
        }else if (start.getColor() == Color.WHITE){
            if (isOnBoard(Direction.EAST.next(start))){
                if (point.getX() == Direction.EAST.next(start).getX() && point.getY() == Direction.EAST.next(start).getY()){
                    if (board.getPieces()[point.x][point.y].getPiece() == Piece.EMPTY ||
                            board.getPieces()[point.x][point.y].getPiece() == null){
                        result = true;
                    }
                }
            }
            if (isOnBoard(Direction.WEST.next(start))){
                if (point.getX() == Direction.WEST.next(start).getX() && point.getY() == Direction.WEST.next(start).getY()){
                    if (board.getPieces()[point.x][point.y].getPiece() == Piece.EMPTY ||
                            board.getPieces()[point.x][point.y].getPiece() == null){
                        result = true;
                    }
                }
            }
            if (isOnBoard(Direction.NORTH.next(start))){
                if (point.getX() == Direction.NORTH.next(start).getX() && point.getY() == Direction.NORTH.next(start).getY()){
                    if (board.getPieces()[point.x][point.y].getPiece() == Piece.EMPTY ||
                            board.getPieces()[point.x][point.y].getPiece() == null){
                        result = true;
                    }
                }
            }
            if (isOnBoard(Direction.SOUTH.next(start))){
                if (point.getX() == Direction.SOUTH.next(start).getX() && point.getY() == Direction.SOUTH.next(start).getY()){
                    if (board.getPieces()[point.x][point.y].getPiece() == Piece.EMPTY ||
                            board.getPieces()[point.x][point.y].getPiece() == null){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public static Set<Point> getPossibleMoves(Board board,Player player){
        Set<Point> moves = new HashSet<>();
        Cell[][] cells = board.getPieces();
        for (int i = 0; i < cells.length; i++){
            for (int j = 0; j < cells[i].length; j++){
                if (cells[i][j].getPiece().getColor().equals(player.getColor())){
                    if (cells[i][j].getColor().equals(Color.black)){
                        for (Direction direction : Direction.values()) {
                            int x = (int)direction.next(cells[i][j]).getX();
                            int y = (int)direction.next(cells[i][j]).getY();
                            if (isOnBoard(direction.next(cells[i][j]))){
                                if (cells[x][y].getPiece().equals(Piece.EMPTY) || cells[x][y].getPiece().equals(null)){
                                    moves.add(direction.next(cells[i][j]));
                                }
                            }

                        }
                    }else {
                        Piece piece;
                        if (isOnBoard(Direction.NORTH.next(cells[i][j]))){
                            piece = board.getCell(Direction.NORTH.next(cells[i][j])).getPiece();
                            if (piece.equals(Piece.EMPTY) || piece == null){
                                moves.add(Direction.NORTH.next(cells[i][j]));
                            }
                        }
                        if (isOnBoard(Direction.SOUTH.next(cells[i][j]))) {
                            piece = board.getCell(Direction.SOUTH.next(cells[i][j])).getPiece();
                            if (piece.equals(Piece.EMPTY) || piece == null) {
                                moves.add(Direction.SOUTH.next(cells[i][j]));
                            }
                        }
                        if (isOnBoard(Direction.WEST.next(cells[i][j]))) {
                            piece = board.getCell(Direction.WEST.next(cells[i][j])).getPiece();
                            if (piece.equals(Piece.EMPTY) || piece == null) {
                                moves.add(Direction.WEST.next(cells[i][j]));
                            }
                        }

                        if (isOnBoard(Direction.EAST.next(cells[i][j]))) {
                            piece = board.getCell(Direction.EAST.next(cells[i][j])).getPiece();
                            if (piece.equals(Piece.EMPTY) || piece == null) {
                                moves.add(Direction.EAST.next(cells[i][j]));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }
}

