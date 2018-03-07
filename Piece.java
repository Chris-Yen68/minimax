import java.awt.*;

/**
 * Created by jeanlee on 2017/10/11.
 */
public enum Piece {
    GREEN(Color.GREEN),
    RED(Color.RED),
    EMPTY(Color.GRAY);

    private Color color;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer() {
        if(this == GREEN){
            this.player = Player.GREEN;
        }else if (this == RED){
            this.player = Player.RED;
        }else if (this == EMPTY){
            this.player = null;
        }

    }

    Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Piece opposite(){
        if (this == Piece.GREEN){
            return Piece.RED;
        }else if (this == Piece.RED){
            return Piece.GREEN;
        }
        return EMPTY;
//        return this == GREEN ? RED : GREEN;
    }

}
