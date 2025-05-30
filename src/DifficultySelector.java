// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;

// public class DifficultySelector extends JFrame {
//     private JPanel mainPanel;
//     Color darkcolor = new Color(5, 77, 120);
//     Color lightColor = new Color(220, 238, 229);

//     public DifficultySelector() {
//         setTitle("Sudoku");
//         setSize(400, 400);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);
//         initComponents();
//     }
     
//     public void initComponents(){
        
//         mainPanel = new JPanel();
//         mainPanel.setBackground(Color.WHITE);
//         mainPanel.setPreferredSize(new Dimension(400, 400));
//         mainPanel.setLayout(null);
//         add(mainPanel);

         
//         JLabel label = new JLabel("Chọn độ khó:");
//         label.setFont(new Font("Arial", Font.BOLD, 16));
//         label.setHorizontalAlignment(JLabel.CENTER);

//         JButton easyBtn = new JButton("Dễ");
//         easyBtn.setFocusPainted(false);
//         easyBtn.setForeground(lightColor);
//         easyBtn.setBackground(darkcolor);
//         easyBtn.setBounds(0,0,50,50);
//         easyBtn.addActionListener(e -> startGame("easy"));

//         JButton mediumBtn = new JButton("Trung bình");
//         JButton hardBtn = new JButton("Khó");
//         JButton backButton = new JButton("Back");
//         backButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new StartScreen();
//                 setVisible(false);
//             }
//         });

//         mediumBtn.addActionListener(e -> startGame("medium"));
//         hardBtn.addActionListener(e -> startGame("hard"));


       
//         mainPanel.add(easyBtn);
//         mainPanel.add(mediumBtn);
//         mainPanel.add(hardBtn);
//         mainPanel.add(backButton);
//         setVisible(true);
//     }

//     private void startGame(String difficulty) {
//         SudokuBoard sudokuBoard = new SudokuBoard(difficulty);
//         new GamePanel(sudokuBoard);
//         dispose();
//     }
   

    
// }