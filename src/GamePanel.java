import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private SudokuGame game;

    public GamePanel(SudokuBoard board) {
        int WIDTH = 550;
        int HEIGHT = 600;
        this.game = new SudokuGame(board);
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setBounds(500, 125, WIDTH, HEIGHT);

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 20));

                int value = board.getValue(row, col);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setBackground(Color.WHITE);
                }

                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> checkAnswers());

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> showHint());

        JButton backButton = new JButton("Back");
         backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DifficultySelector();
                setVisible(false);
            }
        });
        

        JPanel controlPanel = new JPanel();
        controlPanel.add(checkButton);
        controlPanel.add(hintButton);
        controlPanel.add(backButton);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void checkAnswers() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!cells[row][col].isEditable()) continue;
                String text = cells[row][col].getText().trim();
                if (text.isEmpty()) continue;
                try {
                    int value = Integer.parseInt(text);
                    if (game.checkAnswers(row, col, value)) {
                        cells[row][col].setBackground(new Color(200, 255, 200));
                        cells[row][col].setEditable(false);
                    } else {
                        cells[row][col].setBackground(new Color(255, 200, 200));
                    }
                } catch (NumberFormatException e) {
                    cells[row][col].setBackground(Color.YELLOW);
                }
            }
        }
    }

    private void showHint() {
        Point hintCell = game.getHint();
        if (hintCell == null) {
            JOptionPane.showMessageDialog(this, "Không còn ô trống để gợi ý!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int row = hintCell.x;
        int col = hintCell.y;
        cells[row][col].setText(String.valueOf(game.getSolutionValue(row, col)));
        cells[row][col].setBackground(new Color(255, 255, 180));
        cells[row][col].setEditable(false);
    }
}