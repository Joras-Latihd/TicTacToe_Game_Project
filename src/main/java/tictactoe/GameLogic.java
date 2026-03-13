package tictactoe;

public class GameLogic {
    private char[][] board = new char[3][3];
    private boolean gameOver = false;
    private int scoreX = 0, scoreO = 0;

    public GameLogic() {
        resetBoard();
    }

    public void resetBoard() {
        gameOver = false;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '\0';
    }

    public void makeMove(int row, int col, char player) {
        if (board[row][col] == '\0') {
            board[row][col] = player;
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                gameOver = true;
                return true;
            }
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                gameOver = true;
                return true;
            }
        }
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            gameOver = true;
            return true;
        }
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            gameOver = true;
            return true;
        }
        return false;
    }

    public boolean checkDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '\0') return false;
        gameOver = true;
        return true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void incrementScore(char player) {
        if (player == 'X') scoreX++;
        else scoreO++;
    }

    public void resetScore() {
        scoreX = 0;
        scoreO = 0;
    }

    public int getScoreX() { return scoreX; }
    public int getScoreO() { return scoreO; }

    // ✅ NEW: Returns a copy of the board (used by AIPlayer)
    public char[][] getBoardCopy() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                copy[i][j] = board[i][j];
        return copy;
    }

    // ✅ NEW: Returns winning cell coordinates (used by Board to highlight)
    public int[][] getWinningCombination() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return new int[][]{{i,0},{i,1},{i,2}};
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != '\0' && board[0][j] == board[1][j] && board[1][j] == board[2][j])
                return new int[][]{{0,j},{1,j},{2,j}};
        }
        // Check diagonals
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return new int[][]{{0,0},{1,1},{2,2}};
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return new int[][]{{0,2},{1,1},{2,0}};

        return null;
    }
}