import java.awt.*;

/**
 * Created by jeanlee on 2017/10/11.
 */
public enum Player {
    GREEN(Piece.GREEN),
    RED(Piece.RED);

    private Piece piece;

    Player(Piece piece) {
        this.piece = piece;
    }

    public Player opponent(){
        return this == GREEN ? RED : GREEN;
    }

    public Piece getPiece() {
        return piece;
    }

    public Color getColor(){
        return this == GREEN ? Color.GREEN : Color.RED;
    }
}
