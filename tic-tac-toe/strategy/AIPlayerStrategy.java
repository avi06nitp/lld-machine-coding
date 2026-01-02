package strategy;

import model.Board;
import model.Cell;
import model.Move;
import model.Player;

import java.util.List;
import java.util.Random;

public class AIPlayerStrategy implements PlayerStrategy {
    private final Random random;

    public AIPlayerStrategy() {
        this.random = new Random();
    }

    @Override
    public Move makeMove(Player player, Board board) {
        System.out.printf("\n%s (AI) is thinking...\n", player.getName());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Cell bestCell = findBestMove(player, board);
        System.out.printf("%s chose position (%d, %d)\n",
            player.getName(), bestCell.getRow(), bestCell.getCol());

        return new Move(player, bestCell);
    }

    private Cell findBestMove(Player player, Board board) {
        Cell winningMove = findWinningMove(player, board);
        if (winningMove != null) {
            return winningMove;
        }

        Cell blockingMove = findBlockingMove(player, board);
        if (blockingMove != null) {
            return blockingMove;
        }

        Cell centerCell = getCenterCell(board);
        if (centerCell != null && centerCell.isEmpty()) {
            return centerCell;
        }

        Cell cornerCell = getRandomCorner(board);
        if (cornerCell != null) {
            return cornerCell;
        }

        return getRandomEmptyCell(board);
    }

    private Cell findWinningMove(Player player, Board board) {
        List<Cell> emptyCells = board.getEmptyCells();
        for (Cell cell : emptyCells) {
            cell.setSymbol(player.getSymbol());
            boolean wins = new DefaultWinningStrategy().checkWinner(board, new Move(player, cell));
            cell.reset();

            if (wins) {
                return cell;
            }
        }
        return null;
    }

    private Cell findBlockingMove(Player currentPlayer, Board board) {
        List<Cell> emptyCells = board.getEmptyCells();

        for (Cell cell : emptyCells) {
            for (Player opponent : getOpponents(currentPlayer, board)) {
                cell.setSymbol(opponent.getSymbol());
                boolean opponentWins = new DefaultWinningStrategy().checkWinner(board, new Move(opponent, cell));
                cell.reset();

                if (opponentWins) {
                    return cell;
                }
            }
        }
        return null;
    }

    private List<Player> getOpponents(Player currentPlayer, Board board) {
        return List.of();
    }

    private Cell getCenterCell(Board board) {
        int size = board.getSize();
        if (size % 2 == 1) {
            int center = size / 2;
            return board.getCell(center, center);
        }
        return null;
    }

    private Cell getRandomCorner(Board board) {
        int size = board.getSize();
        int[][] corners = {{0, 0}, {0, size - 1}, {size - 1, 0}, {size - 1, size - 1}};

        List<Cell> emptyCorners = new java.util.ArrayList<>();
        for (int[] corner : corners) {
            Cell cell = board.getCell(corner[0], corner[1]);
            if (cell.isEmpty()) {
                emptyCorners.add(cell);
            }
        }

        if (!emptyCorners.isEmpty()) {
            return emptyCorners.get(random.nextInt(emptyCorners.size()));
        }
        return null;
    }

    private Cell getRandomEmptyCell(Board board) {
        List<Cell> emptyCells = board.getEmptyCells();
        if (!emptyCells.isEmpty()) {
            return emptyCells.get(random.nextInt(emptyCells.size()));
        }
        return null;
    }
}