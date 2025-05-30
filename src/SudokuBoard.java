import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SudokuBoard {
    private int[][] board;
    private int[][] solution;
    
    private Random random = new Random();
    private String difficulty;

    public SudokuBoard(String difficulty) {
        this.board = new int[9][9];
        this.solution = new int[9][9];
        generateBoard(difficulty);
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public boolean isFixedCell(int row, int col) {
        return board[row][col] != 0;
    }
    //chon so tu 1-> 9 neu hop le gan vao o
    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (solution[row][i] == num || solution[i][col] == num) {
                return false;
            }
        }
        int boxRow = row - row % 3, boxCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (solution[boxRow + i][boxCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    //backtracking algorithm
    private boolean solve(int row, int col) {
        if (row == 9) return true;
        if (col == 9) return solve(row + 1, 0);
        if (solution[row][col] != 0) return solve(row, col + 1);

        int[] nums = randomOrder();
        for (int num : nums) {
            if (isValid(row, col, num)) {
                solution[row][col] = num;
                if (solve(row, col + 1)) return true;
                solution[row][col] = 0;
            }
        }
        return false;
    }

    private int[] randomOrder() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = nums.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }
    // che do co ban
    private void generateBoard(String difficulty) {
        solve(0, 0);
        for (int row = 0; row < 9; row++) {
            System.arraycopy(solution[row], 0, board[row], 0, 9);
        }
        removeNumbers(difficulty);
    }
  
    private void removeNumbers(String difficulty) {
        int cellsToRemove;
        switch (difficulty) {
            case "easy": cellsToRemove = 30; break;
            case "medium": cellsToRemove = 40; break;
            case "hard": cellsToRemove = 60; break;
            default: cellsToRemove = 30;
        }

        for (int i = 0; i < cellsToRemove; i++) {
            int row, col;
            do {
                row = random.nextInt(9);
                col = random.nextInt(9);
            } while (board[row][col] == 0);

            board[row][col] = 0;
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getSolution() {
        return solution;
    }

    public String getDifficulty(){
        return difficulty;
    }


}