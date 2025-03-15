package io.github.MoYuSOwO;

import java.util.Arrays;

public class GameLogic {
    public enum Pieces {
        EMPTY,
        WHITE,
        BLACK;
    }
    public enum GameStatus {
        PLAYING,
        BLACKWIN,
        WHITEWIN,
        DRAW;
    }
    public enum MoveStatus {
        BLACKMOVED,
        WHITEMOVED,
        ERROR;
    }
    private final int boardRow, boardCol;
    private final Pieces[][] board;
    private Pieces currentPlayer;
    private int lastMoveRow, lastMoveCol;
    public GameLogic(int boardRow, int boardCol) {
        this.board = new Pieces[boardRow][boardCol];
        this.boardRow = boardRow;
        this.boardCol = boardCol;
        this.lastMoveRow = -1;
        this.lastMoveCol = -1;
        for (int i = 0; i < boardRow; i++) {
            for (int j = 0; j < boardCol; j++) {
                this.board[i][j] = Pieces.EMPTY;
            }
        }
        this.currentPlayer = Pieces.BLACK;
    }
    public void reset() {
        for (int i = 0; i < boardRow; i++) {
            Arrays.fill(board[i], Pieces.EMPTY);
        }
        currentPlayer = Pieces.BLACK;
        lastMoveRow = -1;
        lastMoveCol = -1;
    }
    public MoveStatus move(int row, int col) {
        if (row < 0 || row >= this.boardRow || col < 0 || col >= this.boardCol) {
            return MoveStatus.ERROR;
        }
        else if (this.board[row][col] == Pieces.EMPTY) {
            this.board[row][col] = this.currentPlayer;
            this.lastMoveRow = row;
            this.lastMoveCol = col;
            this.changePlayer();
            if (this.currentPlayer == Pieces.BLACK) return MoveStatus.WHITEMOVED;
            else if (this.currentPlayer == Pieces.WHITE) return MoveStatus.BLACKMOVED;
        }
        return MoveStatus.ERROR;
    }
    public GameStatus checkResult() {
        if (this.lastMoveRow == -1 && this.lastMoveCol == -1) {
            return GameStatus.PLAYING;
        }
        else if (this.rowSearch(this.lastMoveRow, this.lastMoveCol) || this.colSearch(this.lastMoveRow, this.lastMoveCol) || this.leftDiagonalSearch(this.lastMoveRow, this.lastMoveCol) || this.rightDiagonalSearch(this.lastMoveRow, this.lastMoveCol)) {
            if (this.board[this.lastMoveRow][this.lastMoveCol] == Pieces.BLACK) {
                return GameStatus.BLACKWIN;
            }
            else {
                return GameStatus.WHITEWIN;
            }
        }
        else if (this.isDraw()) {
            return GameStatus.DRAW;
        }
        else {
            return GameStatus.PLAYING;
        }
    }
    public Pieces getCurrentPlayer() {
        return this.currentPlayer;
    }
    private boolean isDraw() {
        for (int i = 0; i < this.boardRow; i++) {
            for (int j = 0; j < this.boardCol; j++) {
                if (board[i][j] == Pieces.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    private void changePlayer() {
        if (this.currentPlayer == Pieces.WHITE) {
            this.currentPlayer = Pieces.BLACK;
        }
        else {
            this.currentPlayer = Pieces.WHITE;
        }
    }
    private boolean rowSearch(int row, int col) {
        int left = 0;
        for (int i = col; i >= 0 && this.board[row][i] == this.board[row][col]; i--) {
            left++;
        }
        int right = 0;
        for (int i = col + 1; i < this.boardCol && this.board[row][i] == this.board[row][col]; i++) {
            right++;
        }
        if (left + right >= 5) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean colSearch(int row, int col) {
        int up = 0;
        for (int i = row; i >= 0 && this.board[i][col] == this.board[row][col]; i--) {
            up++;
        }
        int down = 0;
        for (int i = row + 1; i < this.boardRow && this.board[i][col] == this.board[row][col]; i++) {
            down++;
        }
        return up + down >= 5;
    }
    private boolean leftDiagonalSearch(int row, int col) {
        int upleft = 0;
        for (int i = row, j = col; i >= 0 && j >= 0 && this.board[i][j] == this.board[row][col]; i--, j--) {
            upleft++;
        }
        int downright = 0;
        for (int i = row + 1, j = col + 1; i < this.boardRow && j < this.boardCol && this.board[i][j] == this.board[row][col]; i++, j++) {
            downright++;
        }
        return upleft + downright >= 5;
    }
    private boolean rightDiagonalSearch(int row, int col) {
        int upright = 0;
        for (int i = row, j = col; i >= 0 && j < this.boardCol && this.board[i][j] == this.board[row][col]; i--, j++) {
            upright++;
        }
        int downleft = 0;
        for (int i = row + 1, j = col - 1; i < this.boardRow && j >= 0 && this.board[i][j] == this.board[row][col]; i++, j--) {
            downleft++;
        }
        return upright + downleft >= 5;
    }
}
