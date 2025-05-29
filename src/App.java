public class App {
    public static void main(String[] args) {
        int[][] sample = SudokuBoard.getSampleBoard();
        SudokuBoard board = new SudokuBoard(sample);
        new GamePanel(board);
    }
}
