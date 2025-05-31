import javax.swing.*;
import javax.swing.text.AbstractDocument;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private SudokuGame game;
    private int mistakeCount = 0; // đếm số lần sai
    private boolean gameOver = false; // kiểm tra trạng thái game

    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel controlPanel;

    private JLabel mistake;


    private JTextField selectedCell = null;

    public GamePanel(SudokuBoard board) {
        int WIDTH = 800;
        int HEIGHT = 600;
        this.game = new SudokuGame(board);
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setBounds(500, 125, WIDTH, HEIGHT);

        initComponents(board);
    }
    
    /**
     * @param board
     */
    public void initComponents(SudokuBoard board){

        Color darkcolor = new Color(5, 77, 120);
        Color lightColor = new Color(0, 0, 0, 60);

        


        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.setLayout(null);
        add(mainPanel);


        //leftPanel (chua Board sudoku)
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.white);
        leftPanel.setBounds(0, 0, 500, 600);
        leftPanel.setLayout(null);
        mainPanel.add(leftPanel);

        //controlPanel
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.white);
        controlPanel.setBounds(500, 0, 300, 600);
        controlPanel.setLayout(null);
        mainPanel.add(controlPanel);

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE)) ;
        
       
        // Tạo các ô nhập cho lưới Sudoku
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 20));
                ((AbstractDocument) cell.getDocument()).setDocumentFilter(new DigitFilters());
                int value = board.getValue(row, col);
                if (value != 0) {  // Ô cố định
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setBackground(Color.WHITE);
                } else {  // Ô trống, cho phép nhập
                    cell.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                checkAnswers();
                            }
                        }
                    });
                    cell.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusGained(FocusEvent e){
                            selectedCell = (JTextField) e.getSource(); // cập nhật ô đang chọn 
                        }
                    });
                }
                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        gridPanel.setBounds(0, 60, 500, 500);
        leftPanel.add(gridPanel);

        // chon độ khó
        JLabel label = new JLabel("Difficulty:");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBackground(Color.white);
        label.setForeground(darkcolor);
        label.setBorder(null);
        label.setBounds(5,5, 100,50);
        leftPanel.add(label);
       
        JButton easyBtn = new JButton("Easy");
        easyBtn.setFont(new Font("Arial", Font.BOLD, 16));
        easyBtn.setFocusPainted(false);
        easyBtn.setForeground(darkcolor);
        easyBtn.setBackground(Color.white);
        easyBtn.setBorder(null);
        easyBtn.setBounds(100,5,50,50);
        easyBtn.addActionListener(e -> startGame("easy"));
        leftPanel.add(easyBtn);

        JButton mediumBtn = new JButton("Medium");
        mediumBtn.setFont(new Font("Arial", Font.BOLD, 16));
        mediumBtn.setFocusPainted(false);
        mediumBtn.setForeground(darkcolor);
        mediumBtn.setBackground(Color.white);
        mediumBtn.setBorder(null);
        mediumBtn.setBounds(150,5,100,50);
        mediumBtn.addActionListener(e -> startGame("medium"));
        leftPanel.add(mediumBtn);

        JButton hardBtn = new JButton("Hard");
        hardBtn.setFont(new Font("Arial", Font.BOLD, 16));        
        hardBtn.setFocusPainted(false);
        hardBtn.setForeground(darkcolor);
        hardBtn.setBackground(Color.white);
        hardBtn.setBorder(null);
        hardBtn.setBounds(250,5,50,50);
        hardBtn.addActionListener(e -> startGame("hard"));
        leftPanel.add(hardBtn);


        // Nút "Check" để kiểm tra đáp án
        JButton checkButton = new RoundedButton("Check");
        checkButton.setFocusPainted(false);
        checkButton.setForeground(darkcolor);
        checkButton.setBackground(lightColor);
        checkButton.setBorder(null);
        checkButton.setBounds(20,60,50,50);
        checkButton.addActionListener(e -> checkAnswers());
        controlPanel.add(checkButton);

        // Nút "Hint" để hiển thị gợi ý
        JButton hintButton = new RoundedButton("Hint");
        hintButton.setFocusPainted(false);
        hintButton.setForeground(darkcolor);
        hintButton.setBackground(lightColor);
        hintButton.setBorder(null);
        hintButton.setBounds(110,60,50,50);
        hintButton.addActionListener(e -> showHint());
        controlPanel.add(hintButton);

        // nút xóa 
        JButton deleteButton = new RoundedButton("Delete");
        deleteButton.setFocusPainted(false);
        deleteButton.setForeground(darkcolor);
        deleteButton.setBackground(lightColor);
        deleteButton.setBorder(null);
        deleteButton.setBounds(200,60,50,50);
        deleteButton.addActionListener(e -> {
        if (selectedCell != null && selectedCell.isEditable()) {
            selectedCell.setText(""); // Xóa nội dung ô đang chọn
            selectedCell.setBackground(Color.WHITE); // Đặt lại màu nền
        }
    });
        controlPanel.add(deleteButton);
        
      

        //Nút newgame 
        JButton newgameBtn = new RoundedButton("New Game");
        newgameBtn.setFocusPainted(false);
        newgameBtn.setForeground(darkcolor);
        newgameBtn.setBackground(lightColor);
        newgameBtn.setBorder(null);
        newgameBtn.setBounds(5,500,275,50);
        newgameBtn.addActionListener(e -> startGame(getName()));

        controlPanel.add(newgameBtn);

        // 
        JPanel numberPad = new JPanel(new GridLayout(3, 3, 2, 2));
        for (int i = 1; i <= 9; i++) {
        JButton numButton = new RoundedButton(String.valueOf(i));
        numButton.setFont(new Font("Arial", Font.BOLD, 15));
        numButton.setBackground(lightColor);
        numButton.setForeground(darkcolor);
        numButton.addActionListener(e -> {
            if (selectedCell != null && selectedCell.isEditable()) {
                selectedCell.setText(numButton.getText());
            }
        });

        numberPad.add(numButton);
    }
       numberPad.setBounds(5,175,275,300);
       controlPanel.add(numberPad);


        //hien thi loi sai cua nguoi choiu
        mistake = new JLabel("Mistake:0/3");
        mistake.setFont(new Font("Tahoma", Font.BOLD, 15));
        mistake.setForeground(darkcolor);
        mistake.setBounds(0, 0, 100, 50);
        controlPanel.add(mistake);

        setVisible(true);
    }

    // Kiểm tra đáp án của người chơi
    private void checkAnswers() {
        if (gameOver) return;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!cells[row][col].isEditable()) continue;
                String text = cells[row][col].getText().trim();
                if (text.isEmpty()) continue;
                try {
                    int value = Integer.parseInt(text);
                    if (game.checkAnswers(row, col, value)) {
                        cells[row][col].setBackground(new Color(173, 216, 230));
                        cells[row][col].setEditable(false);
                    } else {
                        mistakeCount++;
                        mistake.setText("Mistake:" +mistakeCount +"/3");
                        cells[row][col].setBackground(new Color(255, 200, 200));
                        if (mistakeCount >= 3) {
                            endGame();
                            return;
                        }   
                    }
                    return;
                } catch (NumberFormatException e) {
                    cells[row][col].setBackground(Color.YELLOW);
                }
            }
        }
    }

    // Hiển thị gợi ý cho một ô trống
    private void showHint() {
        
        List<Point> hintCells = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!cells[row][col].isEditable()) continue;
                String text = cells[row][col].getText().trim();

                if (text.isEmpty() || Integer.parseInt(text) != game.getSolutionValue(row, col)) {
                    hintCells.add(new Point(row, col)); // chỉ thêm ô còn trống hoặc sai 
                }
            }
        }

        if (hintCells.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không còn ô trống để gợi ý", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Point hintCell = hintCells.get(new Random().nextInt(hintCells.size())); // chọn ngẫu nhiên ô trống 
        int row =hintCell.x;
        int col = hintCell.y;

        cells[row][col].setText(String.valueOf(game.getSolutionValue(row, col)));// điền đáp án đúng
        cells[row][col].setBackground(new Color(176, 196, 222));
        cells[row][col].setEditable(false);
    }

    

    // Xử lý khi người chơi sai quá 3 lần
    private void endGame() {
        gameOver = true;
        JOptionPane.showMessageDialog(this, "Bạn đã sai quá 3 lần", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        // Khoá tất cả các ô
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setEditable(false);
            }
        }
    }

    // Khởi động game
    private void startGame(String difficulty) {
        SudokuBoard sudokuBoard = new SudokuBoard(difficulty);
        new GamePanel(sudokuBoard);
        dispose();
    }


    class RoundedButton extends JButton {
        private int radius = 10;
        private Color normalColor = new Color(0, 0, 0,60);
        private Color hoverColor = new Color(0, 0, 0, 50);
        private Color borderColor = new Color(5, 77, 120);

        public RoundedButton(String text) {
            super(text);
            setOpaque(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverColor);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(normalColor);
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arc = radius * 2;

            Color currentColor = getBackground();
            g2.setColor(currentColor);
            g2.fillRoundRect(1, 1, width - 2, height - 2, arc, arc);
            g2.setColor(borderColor);
            g2.drawRoundRect(1, 1, width - 3, height - 3, arc, arc);

            g2.dispose();
            super.paintComponent(g);
        }
    }
    
    
}

