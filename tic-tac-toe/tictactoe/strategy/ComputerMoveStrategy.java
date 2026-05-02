package tictactoe.strategy;

import tictactoe.models.Board;
import tictactoe.models.Player;

public class ComputerMoveStrategy implements MoveStrategy {

    private final char opponentSymbol;

    public ComputerMoveStrategy(char opponentSymbol) {
        this.opponentSymbol = opponentSymbol;
    }

    @Override
    public void move(Board board, Player player) {
        System.out.println("\033[1;35m  [CPU] " + player.getName() + " (" + player.getSymbol() + ") is thinking...\033[0m");
        int[] best = findBestMove(board, player.getSymbol());
        board.setCell(best[0], best[1], player.getSymbol());
        System.out.println("\033[1;35m  [CPU] Played at " + best[0] + "," + best[1] + "\033[0m");
    }

    private int[] findBestMove(Board board, char mySymbol) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        int maxDepth = board.getSize() <= 3 ? board.getSize() * board.getSize() : 6;

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j) == '\0') {
                    board.setCell(i, j, mySymbol);
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE, mySymbol, maxDepth);
                    board.setCell(i, j, '\0');
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta, char mySymbol, int maxDepth) {
        int result = evaluate(board, mySymbol);
        if (result != 0) return result > 0 ? result - depth : result + depth;
        if (isBoardFull(board) || depth >= maxDepth) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            search:
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.getCell(i, j) == '\0') {
                        board.setCell(i, j, mySymbol);
                        best = Math.max(best, minimax(board, depth + 1, false, alpha, beta, mySymbol, maxDepth));
                        board.setCell(i, j, '\0');
                        alpha = Math.max(alpha, best);
                        if (beta <= alpha) break search;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            search:
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.getCell(i, j) == '\0') {
                        board.setCell(i, j, opponentSymbol);
                        best = Math.min(best, minimax(board, depth + 1, true, alpha, beta, mySymbol, maxDepth));
                        board.setCell(i, j, '\0');
                        beta = Math.min(beta, best);
                        if (beta <= alpha) break search;
                    }
                }
            }
            return best;
        }
    }

    private int evaluate(Board board, char mySymbol) {
        if (hasWon(board, mySymbol)) return 10;
        if (hasWon(board, opponentSymbol)) return -10;
        return 0;
    }

    private boolean hasWon(Board board, char symbol) {
        int n = board.getSize();
        for (int i = 0; i < n; i++) {
            boolean row = true, col = true;
            for (int j = 0; j < n; j++) {
                if (board.getCell(i, j) != symbol) row = false;
                if (board.getCell(j, i) != symbol) col = false;
            }
            if (row || col) return true;
        }
        boolean main = true, anti = true;
        for (int i = 0; i < n; i++) {
            if (board.getCell(i, i) != symbol) main = false;
            if (board.getCell(i, n - 1 - i) != symbol) anti = false;
        }
        return main || anti;
    }

    private boolean isBoardFull(Board board) {
        for (int i = 0; i < board.getSize(); i++)
            for (int j = 0; j < board.getSize(); j++)
                if (board.getCell(i, j) == '\0') return false;
        return true;
    }
}
