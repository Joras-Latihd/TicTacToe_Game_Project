package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Board extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private GameLogic logic = new GameLogic();
    private ScoreBoard scoreBoard = new ScoreBoard();
    private boolean playerXTurn = true;
    private boolean singlePlayerMode = false; // Set true to enable AI

    public Board() {
        setTitle("Tic Tac Toe by SAROJ DHITAL");
        setSize(400, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 60));
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);

                final int row = i, col = j;

                // Hover effect
                btn.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        if (btn.getText().equals("")) btn.setBackground(Color.LIGHT_GRAY);
                    }
                    public void mouseExited(MouseEvent evt) {
                        btn.setBackground(Color.WHITE);
                    }
                });

                // Button click
                btn.addActionListener(e -> {
                    if (btn.getText().equals("") && !logic.isGameOver()) {
                        animateMove(btn, playerXTurn ? "X" : "O", playerXTurn ? Color.RED : Color.BLUE);
                        logic.makeMove(row, col, playerXTurn ? 'X' : 'O');

                        if (logic.checkWin()) {
                            highlightWinningLine();
                            playSound("win.wav");
                            JOptionPane.showMessageDialog(this,
                                "Player " + (playerXTurn ? "X" : "O") + " Wins!");
                            logic.incrementScore(playerXTurn ? 'X' : 'O');
                            scoreBoard.updateScores(logic.getScoreX(), logic.getScoreO());
                            logic.resetBoard();
                            resetButtons();
                        } else if (logic.checkDraw()) {
                            playSound("draw.wav");
                            JOptionPane.showMessageDialog(this, "It's a Draw!");
                            logic.resetBoard();
                            resetButtons();
                        } else {
                            playSound("click.wav");
                            playerXTurn = !playerXTurn;

                            // Optional AI move
                            if (singlePlayerMode && !playerXTurn) {
                                AIPlayer.makeAIMove(logic, buttons);
                                playerXTurn = true;
                            }
                        }
                    }
                });
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        add(scoreBoard.getPanel(), BorderLayout.SOUTH);

        // Reset Scores Button
        JButton resetBtn = new JButton("Reset Scores");
        resetBtn.addActionListener(e -> {
            logic.resetScore();
            scoreBoard.updateScores(logic.getScoreX(), logic.getScoreO());
            resetButtons();
        });
        add(resetBtn, BorderLayout.NORTH);
    }

    private void resetButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        playerXTurn = true;
    }

    // Fade-in animation for X or O
    private void animateMove(JButton btn, String symbol, Color color) {
        Timer timer = new Timer(15, null);
        final int[] alpha = {0};
        btn.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha[0]));
        btn.setText(symbol);

        timer.addActionListener(e -> {
            alpha[0] += 15;
            if (alpha[0] >= 255) {
                alpha[0] = 255;
                timer.stop();
            }
            btn.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha[0]));
        });
        timer.start();
    }

    // Highlight winning cells
    private void highlightWinningLine() {
        int[][] winCombo = logic.getWinningCombination(); // Implement in GameLogic
        if (winCombo != null) {
            for (int[] cell : winCombo) {
                buttons[cell[0]][cell[1]].setBackground(Color.YELLOW);
            }
        }
    }

    // Play sound effects
    private void playSound(String soundFile) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("resources/sounds/" + soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
