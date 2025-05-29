import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DifficultySelector extends JFrame {

    public DifficultySelector() {
        setTitle("Sudoku");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Chọn độ khó:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton easyBtn = new JButton("Dễ");
        JButton mediumBtn = new JButton("Trung bình");
        JButton hardBtn = new JButton("Khó");

        easyBtn.addActionListener(e -> startGame("easy"));
        mediumBtn.addActionListener(e -> startGame("medium"));
        hardBtn.addActionListener(e -> startGame("hard"));

        JPanel btnPanel = new JPanel();
        btnPanel.add(easyBtn);
        btnPanel.add(mediumBtn);
        btnPanel.add(hardBtn);

        add(label, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void startGame(String difficulty) {
        // Lấy đề tương ứng từ SudokuBoard
        int[][] board = SudokuBoard.getBoardByDifficulty(difficulty);
        SudokuBoard sudokuBoard = new SudokuBoard(board);

        // Mở game chính
        new GamePanel(sudokuBoard);

        // Đóng menu chọn độ khó
        dispose();
    }

    public static void main(String[] args) {
        new DifficultySelector();
    }
}
