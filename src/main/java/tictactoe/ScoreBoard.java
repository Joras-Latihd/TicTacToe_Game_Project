package tictactoe;

import javax.swing.*;
import java.awt.*;

public class ScoreBoard {
    private JPanel panel;
    private JLabel labelX, labelO;

    public ScoreBoard() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        labelX = new JLabel("Player X: 0", SwingConstants.CENTER);
        labelO = new JLabel("Player O: 0", SwingConstants.CENTER);
        labelX.setFont(new Font("Arial", Font.BOLD, 16));
        labelO.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(labelX);
        panel.add(labelO);
    }

    public void updateScores(int scoreX, int scoreO) {
        labelX.setText("Player X: " + scoreX);
        labelO.setText("Player O: " + scoreO);
    }

    public JPanel getPanel() {
        return panel;
    }
}
