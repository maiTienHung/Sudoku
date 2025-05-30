import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JFrame {
    private JPanel mainPanel;

    Color darkcolor = new Color(5, 77, 120);
    Color lightColor = new Color(220, 238, 229);


    public StartScreen() {
        setTitle("Sudoku");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initComponents();

    }

    private void initComponents(){

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(400, 400));
        mainPanel.setLayout(null);
        add(mainPanel);
        
    
        JButton startButton = new JButton("Bắt đầu ván mới");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(lightColor);
        startButton.setForeground(darkcolor);
        startButton.setBounds(100,300,200,25);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> startGame("easy"));

        mainPanel.add(startButton);

        setVisible(true);
    }

     private void startGame(String difficulty) {
        SudokuBoard sudokuBoard = new SudokuBoard(difficulty);
        new GamePanel(sudokuBoard);
        dispose();
    }

}