import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by jeanlee on 2017/10/10.
 */
public class Cell extends JPanel {
    private Piece piece;
    private Point locationOnBoard;
    private JLabel image;
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Cell(Piece piece, Point locationOnBoard) {
        this.piece = piece;
        this.locationOnBoard = locationOnBoard;
    }

    public Cell(Piece piece) {
        this.piece = piece;
    }

    public Cell() {
    }

    {
        image = new JLabel();
        image.setVisible(true);
        add(image);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        setImageBaseOnPiece();
        this.repaint();
    }

    public Point getBoardLocation() {
        return locationOnBoard;
    }

    public void setBoardLocation(Point location) {
        this.locationOnBoard = location;
    }

    public JLabel getImage() {
        return image;

    }

    public void setImage(JLabel image) {
        this.image = image;
        add(this.image);
    }

    public void setImageBaseOnPiece(){
        ImageIcon imageIcon = null;

        if (piece != Piece.EMPTY) {
            String fileName = piece.name() + ".jpg";
            try {
                BufferedImage redImage = ImageIO.read(new File(fileName));
                imageIcon = new ImageIcon(redImage);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        image.setIcon(imageIcon);
    }

    public int neigbourOfScore(Board board){
        int result = 0;
        if (this.getColor().equals(Color.BLACK)){
            for (Direction direction : Direction.values()) {
                if (MoveExplorer.isOnBoard(direction.next(this))) {
                    if (board.getCell(direction.next(this)).getPiece().equals(Piece.EMPTY) || board.getCell(direction.next(this)).getPiece().equals(Piece.RED)) {
                        result += (int) (((Math.random() * 100) / 2) - 25);
                    } else if (board.getCell(direction.next(this)).getPiece().equals(Piece.GREEN)) {
                        result += 10;
                    }
                }
            }
        }else {

            for (Direction direction : Direction.values()) {
                if (MoveExplorer.isOnBoard(direction.next(this))) {
                    if (direction.equals(Direction.EAST) || direction.equals(Direction.WEST) || direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
                        if (board.getCell(direction.next(this)).getPiece().equals(Piece.EMPTY) || board.getCell(direction.next(this)).getPiece().equals(Piece.RED)) {
                            result += (int) (((Math.random() * 100) / 2) - 25);
                        } else if (board.getCell(direction.next(this)).getPiece().equals(Piece.GREEN)) {
                            result += 10;
                        }
                    }
                }
            }
        }
        return result;
    }


}
