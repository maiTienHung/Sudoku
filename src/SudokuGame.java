import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGame {
    private int[][] board;
    private int[][] solution;
    private Random random = new Random();

    public SudokuGame(SudokuBoard sudokuBoard) {
        this.board = sudokuBoard.getBoard();
        this.solution = sudokuBoard.getSolution();
    }

    public boolean checkAnswers(int row, int col, int value) {
        return solution[row][col] == value;
    }

    public Point getHint() {
        List<Point> emptyCells = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    emptyCells.add(new Point(row, col));
                }
            }
        }

        if (emptyCells.isEmpty()) return null;
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    public int getSolutionValue(int row, int col) {
        return solution[row][col];
    }
}