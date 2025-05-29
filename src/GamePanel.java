import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.AbstractDocument;


public class GamePanel extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private SudokuBoard board;

    public GamePanel(SudokuBoard board) {
        this.board = board;

        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 600);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 20));
                cell.setBorder(BorderFactory.createMatteBorder(
                        row % 3 == 0 ? 2 : 1,
                        col % 3 == 0 ? 2 : 1,
                        row == 8 ? 2 : 1,
                        col == 8 ? 2 : 1,
                        Color.BLACK
                ));

                int value = board.getValue(row, col);
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setBackground(new Color(220, 220, 220));
                }else{
                    ((AbstractDocument) cell.getDocument()).setDocumentFilter(new DigitFilters());
                }

                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        // Nút kiểm tra
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswers();
            }
        });

       //Nút gợi ý 
        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> showHint());
      


        JPanel controlPanel = new JPanel();
        controlPanel.add(checkButton);
        controlPanel.add(hintButton);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    //Hàm kiểm tra kết quả
    private void checkAnswers() {
        int[][] solution = board.getSolution();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!cells[row][col].isEditable()) continue; // Bỏ qua ô đề

                String text = cells[row][col].getText().trim();
                if (text.isEmpty()) {
                    cells[row][col].setBackground(Color.WHITE);
                    continue;
                }

                try {
                    int value = Integer.parseInt(text);
                    if (value == solution[row][col]) {
                        cells[row][col].setBackground(new Color(200, 255, 200)); //  Đúng = xanh
                        cells[row][col].setEditable(false); // khóa lại
                    } else {
                        cells[row][col].setBackground(new Color(255, 200, 200)); //  Sai = đỏ
                    }
                } catch (NumberFormatException ex) {
                    cells[row][col].setBackground(Color.YELLOW); //  Không phải số
                }
            }
        }
    }

    // Hàm gợi ý 
    private void showHint(){
        int[] [] solution = board.getSolution();
        java.util.List<Point> emptyCells = new java.util.ArrayList<>();

         // Tìm tất cả ô trống có thể nhập
     for (int row = 0; row < SIZE; row++) {
        for (int col = 0; col < SIZE; col++) {
            JTextField cell = cells[row][col];
            if (cell.isEditable() && cell.getText().isEmpty()) {
                emptyCells.add(new Point(row, col));
            }
        }
    }

        if (emptyCells.isEmpty()) {
           JOptionPane.showMessageDialog(this, "Không còn ô trống để gợi ý!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Chọn 1 ô ngẫu nhiên từ danh sách
     Point randomCell = emptyCells.get(new java.util.Random().nextInt(emptyCells.size()));
     int row = randomCell.x;
     int col = randomCell.y;

    // Gợi ý đúng giá trị
     cells[row][col].setText(String.valueOf(solution[row][col]));
     cells[row][col].setBackground(new Color(255, 255, 180)); // vàng nhạt
     cells[row][col].setEditable(false); // khóa lại
    }
}
