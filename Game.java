import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * Created by jeanlee on 2017/11/4.
 */
public class Game {
    public static void main(String[] args) {
        new Game().starGmae();
    }
    public void starGmae() {
        Board board = new Board();
        board.setTitle("Chess");
        board.setLocation(10, 10);
        board.setSize(700, 400);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setVisible(true);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Play as Green or Red?");
        String initialHumanPlayer = scanner.nextLine();
        Controller controller = null;
        if (initialHumanPlayer.equalsIgnoreCase("GREEN")){
            controller = new Controller(board,Player.RED,Type.MIN);

        }else {
            controller = new Controller(board,Player.GREEN,Type.MAX);
        }
        if (controller.getPlayer().equals(Player.GREEN)){
            while (!board.isWin()){
                Point[] points = controller.makeMove();
                System.out.println("Moving from " + outPut(points[0]) + "to" + outPut(points[1]) + " and value is "+ Evaluation.score(board));

                if (board.printWinner()){
                    return;
                }
                System.out.println("Please Move the Red Pieces");
                String instructionRED = scanner.nextLine();
                if (!board.transferMove(instructionRED)) {
                    System.out.println("Please Move the Correct Red Pieces");
                    instructionRED = scanner.nextLine();
                    boolean correctLineRED = false;
                    if (board.transferMove(instructionRED)){
                        correctLineRED = true;
                    }
                    while (!correctLineRED) {
                        System.out.println("Please Move the Correct RED Pieces");
                        instructionRED = scanner.nextLine();
                        if (board.transferMove(instructionRED)) {
                            correctLineRED = true;
                        }
                    }
                }
                if (board.printWinner()){
                    return;
                }
            }
        }else {
            while (!board.isWin()){
                System.out.println("Please Move the Green Pieces");
                String instructionGreen = scanner.nextLine();
                if (!board.transferMove(instructionGreen)) {
                    System.out.println("Please Move the Correct Green Pieces");
                    instructionGreen = scanner.nextLine();
                    boolean correctLineGreen = false;
                    if (board.transferMove(instructionGreen)){
                        correctLineGreen = true;
                    }
                    while (!correctLineGreen) {
                        System.out.println("Please Move the Correct Green Pieces");
                        instructionGreen = scanner.nextLine();
                        if (board.transferMove(instructionGreen)) {
                            correctLineGreen = true;
                        }
                    }
                }
                if (board.printWinner()){
                    return;
                }
                Point[] points = controller.makeMove();
                System.out.println("Moving from " + outPut(points[0]) + "to" + outPut(points[1]) + " and value is "+ Evaluation.score(board));
                if (board.printWinner()){
                    return;
                }
            }

        }
    }
    public  String outPut(Point point){
        String result = "";
        int x = (int) point.getX();
        int y = (int) point.getY() + 1;
        result += "[" + (char)(x + 65) + "," + y + "]";
        return result;
    }
}
