import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by jeanlee on 2017/10/10.
 */
public class Board extends JFrame {
    private Cell[][] pieces = new Cell[5][9];

    public Cell[][] getPieces() {
        return pieces;
    }

    public Board clone(){
        return new Board(this.getPieces());
    }

    private Board(Cell[][] cells){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++){
                this.pieces[i][j] = new Cell();
                this.pieces[i][j].setPiece(cells[i][j].getPiece());
                this.pieces[i][j].setColor(cells[i][j].getColor());
                this.pieces[i][j].setBoardLocation(cells[i][j].getBoardLocation());
            }
        }
    }

    public Board() throws HeadlessException {
        init();
    }

    public void init(){
        setLayout(new GridLayout(5, 9));

        // init background
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 9; j++) {
                pieces[i][j] = new Cell();
                Point point = new Point(i,j);
                pieces[i][j].setBoardLocation(point);
                if(i % 2 == 0) {
                    if (j % 2 == 0){
                        pieces[i][j].setBackground(Color.BLACK);
                        pieces[i][j].setColor(Color.BLACK);
                    }else {
                        pieces[i][j].setBackground(Color.WHITE);
                        pieces[i][j].setColor(Color.WHITE);
                    }
                } else {
                    if (j % 2 == 0){
                        pieces[i][j].setBackground(Color.WHITE);
                        pieces[i][j].setColor(Color.WHITE);
                    }else {
                        pieces[i][j].setBackground(Color.BLACK);
                        pieces[i][j].setColor(Color.BLACK);
                    }
                }
                add(pieces[i][j]);
            }
        }

        // init piece
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 9; j++) {
                Point point = new Point(i,j);
                if (point.getX() < 2) {
                    pieces[i][j].setPiece(Piece.RED);
                } else if (point.getX() == 2) {
                    if (point.getY() < 4) {
                        pieces[i][j].setPiece(Piece.GREEN);
                    } else if (point.getY() > 4) {
                        pieces[i][j].setPiece(Piece.RED);
                    }
                } else {
                    pieces[i][j].setPiece(Piece.GREEN);
                }
            }
        }
        pieces[2][4].setPiece(Piece.EMPTY);

    }

    public boolean isFull(){
        for(int i = 0; i < pieces.length ; i++){
            for(int j = 0; j < pieces[i].length; j++){
                if (pieces[i][j].getPiece() == Piece.EMPTY){
                    return false;
                }
            }
        }
        return true;
    }
    public Cell getCell(Point point){
        return pieces[(int)point.getX()][(int)point.getY()];
    }

    public Set<Point> getCells(Piece piece){
        Set<Point> points = new HashSet<>();
        for (int i = 0 ; i < pieces.length; i ++){
            for (int j = 0; j < pieces[i].length; j++){
                if (pieces[i][j].getPiece() == piece){
                    points.add(pieces[i][j].getBoardLocation());
                }
            }
        }
        return points;
    }

    public int count(Piece piece){
        int count = 0;
        for (int i = 0 ; i < pieces.length; i ++){
            for (int j = 0; j < pieces[i].length; j++){
                if (pieces[i][j].getPiece() == piece){
                    count++;
                }
            }
        }
        return count;
    }
    public void remove(Point point){
        pieces[(int)point.getX()][(int)point.getY()].setPiece(Piece.EMPTY);
    }
    public boolean isValid(int x, int y){
        return y < 9 && x < 5 && x >= 0 && y >= 0;
    }

    public boolean transferMove(String line){
        String[] group = line.split(" ");
        String startPoint = group[0];
        String endPoint = group[1];
        char startRow = startPoint.charAt(0);
        int startRowNum = (int)startRow - 65;
        String startCol = startPoint.charAt(1) + "";
        int startColNum = Integer.parseInt(startCol) - 1;

        char endRow = endPoint.charAt(0);
        int endRowNum = (int)endRow - 65;
        String endCol = endPoint.charAt(1) + "";
        int endColNum = Integer.parseInt(endCol) - 1;
        return realMove(new Point(startRowNum,startColNum),new Point(endRowNum,endColNum));
    }

    public boolean realMove(Point begin, Point end) {
        boolean moveSuccess = true;
        Cell start = pieces[(int)begin.getX()][(int)begin.getY()];
        Direction actuallyDirection;
        Piece state = start.getPiece();
        if (MoveExplorer.moveIsValid(this, start, end)) {;
            pieces[(int) end.getX()][(int) end.getY()].setPiece(start.getPiece());
            remove(start.getBoardLocation());
            this.repaint();

        } else {
            System.out.println("can not do this!");
            moveSuccess = false;
        }
        if (moveSuccess) {
            for (Direction direction : Direction.values()) {
                if (direction.next(start).equals(end)) {
                    actuallyDirection = direction;
                    if (actuallyDirection == Direction.NORTH) {
                        int count = 0;
                        int i = (int) end.getX() - 1;
                        int j = (int) end.getY();
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            i--;
                        }
                        if (count >= 1) {
                            i = (int) end.getX() - 1;
                            j = (int) end.getY();
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                i--;
                            }
                        } else {
                            count = 0;
                            i = (int) start.getBoardLocation().getX() + 1;
                            j = (int) (int) start.getBoardLocation().getY();
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                i++;
                            }
                            if (count >= 1) {
                                i = (int) start.getBoardLocation().getX() + 1;
                                j = (int) (int) start.getBoardLocation().getY();
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    i++;
                                }
                            }
                        }
                    } else if (actuallyDirection == Direction.SOUTH) {
                        int count = 0;
                        int i = (int) end.getX() + 1;
                        int j = (int) end.getY();
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            i++;
                        }
                        if (count >= 1) {
                            i = (int) end.getX() + 1;
                            j = (int) end.getY();
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                i++;
                            }
                        } else {
                            count = 0;
                            i = (int) start.getBoardLocation().getX() - 1;
                            j = (int) (int) start.getBoardLocation().getY();
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                i--;
                            }
                            if (count >= 1) {
                                i = (int) start.getBoardLocation().getX() - 1;
                                j = (int) (int) start.getBoardLocation().getY();
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    i--;
                                }
                            }
                        }
                    } else if (actuallyDirection == Direction.WEST) {
                        int count = 0;
                        int i = (int) end.getX();
                        int j = (int) end.getY() - 1;
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            j--;
                        }
                        if (count >= 1) {
                            i = (int) end.getX();
                            j = (int) end.getY() - 1;
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                j--;
                            }
                        } else {
                            count = 0;
                            i = (int) (int) start.getBoardLocation().getX();
                            j = (int) start.getBoardLocation().getY() + 1;
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                j++;
                            }
                            if (count >= 1) {
                                i = (int) (int) start.getBoardLocation().getX();
                                j = (int) start.getBoardLocation().getY() + 1;
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    j++;
                                }
                            }
                        }
                    } else if (actuallyDirection == Direction.EAST) {
                        int count = 0;
                        int i = (int) end.getX();
                        int j = (int) end.getY() + 1;
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            j++;
                        }
                        if (count >= 1) {
                            i = (int) end.getX();
                            j = (int) end.getY() + 1;
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                j++;
                            }
                        } else {
                            count = 0;
                            i = (int) (int) start.getBoardLocation().getX();
                            j = (int) start.getBoardLocation().getY() - 1;
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                j--;
                            }
                            if (count >= 1) {
                                i = (int) (int) start.getBoardLocation().getX();
                                j = (int) start.getBoardLocation().getY() - 1;
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    j--;
                                }
                            }
                        }
                    } else if (actuallyDirection == Direction.NORTHWEST) {
                        int count = 0;
                        int i = (int) end.getX() - 1;
                        int j = (int) end.getY() - 1;
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            i--;
                            j--;
                        }
                        if (count >= 1) {
                            i = (int) end.getX() - 1;
                            j = (int) end.getY() - 1;
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                i--;
                                j--;
                            }
                        } else {
                            count = 0;
                            i = (int) (int) start.getBoardLocation().getX() + 1;
                            j = (int) (int) start.getBoardLocation().getY() + 1;
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                i++;
                                j++;
                            }
                            if (count >= 1) {
                                i = (int) (int) start.getBoardLocation().getX() + 1;
                                j = (int) (int) start.getBoardLocation().getY() + 1;
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    i++;
                                    j++;
                                }
                            }
                        }
                    } else if (actuallyDirection == Direction.SOUTHEAST) {
                        int count = 0;
                        int i = (int) end.getX() + 1;
                        int j = (int) end.getY() + 1;
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            i++;
                            j++;
                        }
                        if (count >= 1) {
                            i = (int) end.getX() + 1;
                            j = (int) end.getY() + 1;
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                i++;
                                j++;
                            }
                        } else {
                            count = 0;
                            i = (int) start.getBoardLocation().getX() - 1;
                            j = (int) start.getBoardLocation().getY() - 1;
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                i--;
                                j--;
                            }
                            if (count >= 1) {
                                i = (int) start.getBoardLocation().getX() - 1;
                                j = (int) start.getBoardLocation().getY() - 1;
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    i--;
                                    j--;
                                }

                            }

                        }
                    } else if (actuallyDirection == Direction.NORTHEAST) {
                        int count = 0;
                        int i = (int) end.getX() - 1;
                        int j = (int) end.getY() + 1;
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            i--;
                            j++;
                        }
                        if (count >= 1) {
                            i = (int) end.getX() - 1;
                            j = (int) end.getY() + 1;
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);
                                } else {
                                    break;
                                }
                                i--;
                                j++;
                            }
                        } else {
                            count = 0;
                            i = (int) start.getBoardLocation().getX() + 1;
                            j = (int) start.getBoardLocation().getY() - 1;
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                i++;
                                j--;
                            }
                            if (count >= 1) {
                                i = (int) start.getBoardLocation().getX() + 1;
                                j = (int) start.getBoardLocation().getY() - 1;
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    i++;
                                    j--;
                                }
                            }
                        }
                    } else if (actuallyDirection == Direction.SOUTHWEST) {
                        int count = 0;
                        int i = (int) end.getX() + 1;
                        int j = (int) end.getY() - 1;
                        while (isValid(i, j)) {
                            if (state.opposite().equals(pieces[i][j].getPiece())) {
                                count++;
                            } else {
                                break;
                            }
                            i++;
                            j--;
                        }
                        if (count >= 1) {
                            i = (int) end.getX() + 1;
                            j = (int) end.getY() - 1;
                            while (isValid(i, j)) {
                                if (pieces[i][j].getPiece() == state.opposite()) {
                                    pieces[i][j].setPiece(Piece.EMPTY);

                                } else {
                                    break;
                                }
                                i++;
                                j--;
                            }
                        } else {
                            count = 0;
                            i = (int) start.getBoardLocation().getX() - 1;
                            j = (int) start.getBoardLocation().getY() + 1;
                            while (isValid(i, j)) {
                                if (state.opposite().equals(pieces[i][j].getPiece())) {
                                    count++;
                                } else {
                                    break;
                                }
                                i--;
                                j++;
                            }
                            if (count >= 1) {
                                i = (int) start.getBoardLocation().getX() - 1;
                                j = (int) start.getBoardLocation().getY() + 1;
                                while (isValid(i, j)) {
                                    if (pieces[i][j].getPiece() == state.opposite()) {
                                        pieces[i][j].setPiece(Piece.EMPTY);

                                    } else {
                                        break;
                                    }
                                    i--;
                                    j++;
                                }
                            }
                        }
                    }
                }

            }
        }
        return moveSuccess;
    }
    public Player whoIsWin() {
        if (getCells(Piece.GREEN).isEmpty() && !getCells(Piece.RED).isEmpty()) {
            return Player.RED;
        } else if (!getCells(Piece.GREEN).isEmpty() && getCells(Piece.RED).isEmpty()) {
            return Player.GREEN;
        }
        return null;
    }
    public boolean isWin(){
        if (whoIsWin() != null){
            return true;
        }
        return false;
    }
    public boolean correctPlayer(Player player, String line){
        String[] group = line.split(" ");
        int indexX = Integer.parseInt((group[0].charAt(0) - 65)+"");
        int indexY = Integer.parseInt(group[0].charAt(1)+"");
        if (Player.GREEN.equals(player) && Piece.GREEN.equals(pieces[indexX][indexY].getPiece())){
            return true;
        }if(Player.RED.equals(player) && Piece.RED.equals(pieces[indexX][indexY].getPiece())){
            return true;
        }
        return false;

    }

    public void playGame(){
        Scanner scanner = new Scanner(System.in);

        while (!isWin()){
            System.out.println("Please Move the Green Pieces");
            String instructionGreen = scanner.nextLine();

            if (correctPlayer(Player.GREEN,instructionGreen)) {
                if (!transferMove(instructionGreen)){
                    System.out.println("Please Move the Correct Green Pieces");
                    instructionGreen = scanner.nextLine();
                    boolean correctLine = false;
                    if (transferMove(instructionGreen)){
                        correctLine = true;
                    }
                    while (!correctPlayer(Player.GREEN,instructionGreen) && !correctLine){
                        System.out.println("Please Move the Correct Green Pieces");
                        instructionGreen = scanner.nextLine();
                        if (transferMove(instructionGreen)){
                            correctLine = true;
                        }
                    }
                }
            }else {
                System.out.println("Please Move the Correct Green Pieces");
                instructionGreen = scanner.nextLine();
                boolean correctLine = false;
                if (transferMove(instructionGreen)){
                    correctLine = true;
                }
                while (!correctPlayer(Player.GREEN, instructionGreen) && !correctLine) {
                    System.out.println("Please Move the Correct Green Pieces");
                    instructionGreen = scanner.nextLine();
                    if (transferMove(instructionGreen)) {
                        correctLine = true;
                    }
                }
            }
            if (isWin()){
                if (whoIsWin().equals(Player.GREEN)){
                    System.out.println("Green is Winner!");
                    return;
                }else {
                    System.out.println("RED is Winner! ");
                    return;
                }
            }

            System.out.println("Please Move the Red Pieces");
            String instructionRED = scanner.nextLine();
            if (correctPlayer(Player.RED,instructionRED)) {
                if (!transferMove(instructionRED)) {
                    System.out.println("Please Move the Correct Red Pieces");
                    instructionRED = scanner.nextLine();
                    boolean correctLineRED = false;
                    if (transferMove(instructionRED)){
                        correctLineRED = true;
                    }
                    while (!correctPlayer(Player.RED, instructionRED) && !correctLineRED) {
                        System.out.println("Please Move the Correct RED Pieces");
                        instructionRED = scanner.nextLine();
                        if (transferMove(instructionRED)) {
                            correctLineRED = true;
                        }
                    }
                }
            } else {
                System.out.println("Please Move the Correct Red Pieces");
                instructionRED = scanner.nextLine();
                boolean correctLineRED = false;
                if (transferMove(instructionRED)){
                    correctLineRED = true;
                }
                while (!correctPlayer(Player.RED, instructionRED) && !correctLineRED) {
                    System.out.println("Please Move the Correct RED Pieces");
                    instructionRED = scanner.nextLine();
                    if (transferMove(instructionRED)) {
                        correctLineRED = true;
                    }
                }
            }
            if (isWin()){
                if (whoIsWin().equals(Player.GREEN)){
                    System.out.println("Green is Winner!");
                    return;
                }else {
                    System.out.println("RED is Winner! ");
                    return;
                }
            }
        }
    }

    public Point cellToMove(Player player,Point end){
        ArrayList<Point> moves = new ArrayList<>();
        if (getCell(end).getColor() == Color.BLACK){
            for (Direction direction : Direction.values()) {
                if (MoveExplorer.isOnBoard(direction.next(getCell(end)))) {
                    Piece possiblePiece = getCell(direction.next(getCell(end))).getPiece();
                    if (!possiblePiece.equals(Piece.EMPTY) && !possiblePiece.equals(null)
                            && possiblePiece.getColor().equals(player.getColor())) {
                        Point point;
                        point = direction.next(getCell(end));
                        moves.add(point);
                    }
                }
            }
        }else{
            Piece piece = null;
            if (MoveExplorer.isOnBoard(Direction.NORTH.next(getCell(end)))) {
                piece = getCell(Direction.NORTH.next(getCell(end))).getPiece();
                if (piece != null && !piece.equals(Piece.EMPTY)
                        && piece.getColor().equals(player.getColor())) {
                    Point point;
                    point = Direction.NORTH.next(getCell(end));
                    moves.add(point);
                }
            }
            if (MoveExplorer.isOnBoard(Direction.SOUTH.next(getCell(end)))) {
                piece = getCell(Direction.SOUTH.next(getCell(end))).getPiece();
                if (piece != null && !piece.equals(Piece.EMPTY)
                        && piece.getColor().equals(player.getColor())) {
                    Point point;
                    point = Direction.SOUTH.next(getCell(end));
                    moves.add(point);
                }
            }
            if (MoveExplorer.isOnBoard(Direction.WEST.next(getCell(end)))) {
                piece = getCell(Direction.WEST.next(getCell(end))).getPiece();
                if (piece != null && !piece.equals(Piece.EMPTY)
                        && piece.getColor().equals(player.getColor())) {
                    Point point;
                    point = Direction.WEST.next(getCell(end));
                    moves.add(point);
                }
            }
            if (MoveExplorer.isOnBoard(Direction.EAST.next(getCell(end)))) {
                piece = getCell(Direction.EAST.next(getCell(end))).getPiece();
                if (piece != null && !piece.equals(Piece.EMPTY)
                        && piece.getColor().equals(player.getColor())) {
                    Point point;
                    point = Direction.EAST.next(getCell(end));
                    moves.add(point);
                }
            }
        }
        Random random = new Random();
        int index = random.nextInt(moves.size());
        return moves.get(index);
    }

    public boolean printWinner(){
        boolean win = false;
        if (isWin()){
            win = true;
        }
        if (win){
            if (whoIsWin().equals(Player.GREEN)){
                System.out.println("Green is Winner!");
            }else {
                System.out.println("RED is Winner! ");
            }
        }
        return win;
    }
    public Cell pointToCell(Point point){
        return pieces[(int)point.getX()][(int)point.getY()];
    }

    public static void main(String[] args) {
        Board chessBoard = new Board();
        chessBoard.setTitle("Chess");
        chessBoard.setLocation(10, 10);
        chessBoard.setSize(700, 400);
        chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard.setVisible(true);
        chessBoard.playGame();
    }
}
