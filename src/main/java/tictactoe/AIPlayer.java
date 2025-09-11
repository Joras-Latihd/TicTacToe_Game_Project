package tictactoe;

import javax.swing.*;
import java.awt.*;

public class AIPlayer {

    public static void makeAIMove(GameLogic logic, JButton[][] buttons) {
        int[] bestMove = minimax(logic, 'O'); // AI is O
        int row = bestMove[0];
        int col = bestMove[1];

        if (row != -1 && col != -1) {
            JButton btn = buttons[row][col];
            btn.doClick(); // Triggers same animation and logic
        }
    }

    private static int[] minimax(GameLogic logic, char aiSymbol) {
        char[][] board = logic.getBoardCopy();
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    board[i][j] = aiSymbol;
                    int score = minimaxScore(board, false, aiSymbol);
                    board[i][j] = '\0';
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    private static int minimaxScore(char[][] board, boolean isMaximizing, char aiSymbol) {
        char winner = checkWinner(board);
        if (winner == aiSymbol) return 1;
        if (winner != '\0' && winner != aiSymbol) return -1;
        if (isBoardFull(board)) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char currentSymbol = isMaximizing ? aiSymbol : (aiSymbol == 'X' ? 'O' : 'X');

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    board[i][j] = currentSymbol;
                    int score = minimaxScore(board, !isMaximizing, aiSymbol);
                    board[i][j] = '\0';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    private static boolean isBoardFull(char[][] board) {
        for (char[] row : board)
            for (char cell : row)
                if (cell == '\0') return false;
        return true;
    }

    private static char checkWinner(char[][] board) {
        // Similar logic as in GameLogic.checkWin()
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return board[i][0];
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return board[0][i];
        }
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return board[0][0];
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return board[0][2];
        return '\0';
    }
}
